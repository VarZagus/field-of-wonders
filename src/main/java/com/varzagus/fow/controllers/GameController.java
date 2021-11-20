package com.varzagus.fow.controllers;

import com.varzagus.fow.domain.Question;
import com.varzagus.fow.domain.User;
import com.varzagus.fow.enums.GameMessageType;
import com.varzagus.fow.enums.UserMessageType;
import com.varzagus.fow.game.Room;
import com.varzagus.fow.game.RoomProvider;
import com.varzagus.fow.messaging.UserReceiveMessage;
import com.varzagus.fow.messaging.UserResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GameController {

    @Autowired
    RoomProvider roomProvider;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/game")
    public void gameMessage(@Payload UserReceiveMessage userGameMessage, SimpMessageHeaderAccessor headerAccessor) throws InterruptedException {
        UserResponseMessage responseMessage;
        List<Room> rooms = roomProvider.getRooms();
        if(userGameMessage.getMessageType() == UserMessageType.SEARCH) {
            User user = new User(userGameMessage.getUserName(), "123");
            System.out.println(userGameMessage.getUserName());
            if(rooms.size() == 0 || rooms.get(rooms.size()-1).isFull()) {
               Room room = new Room();
               //room.addUser(user);
               rooms.add(room);
               responseMessage = room.sendUserAddMessage(user);
            }
            else {
                responseMessage = rooms.get(rooms.size() - 1).sendUserAddMessage(user);
            }
            messagingTemplate.convertAndSend("/" + userGameMessage.getUserName() + "/game", responseMessage);
            messagingTemplate.convertAndSendToUser(userGameMessage.getUserName(),"/game",responseMessage);
            if(responseMessage.getGameMessageType() == GameMessageType.NEW_ROUND) {
                messagingTemplate.convertAndSend("/game/room/" + responseMessage.getRoomId(), responseMessage);
            }
            if(headerAccessor != null) {
                headerAccessor.getSessionAttributes().put("username",userGameMessage.getUserName());
                headerAccessor.getSessionAttributes().put("room",responseMessage.getRoomId());
            }
        }
        else {
            int roomId = userGameMessage.getRoomId();
            Room room = rooms.stream().filter(room1 -> room1.getId() == roomId).findFirst().orElse(null);
            if(room != null) {
                responseMessage = room.sendUserAnswerMessage(userGameMessage);
                messagingTemplate.convertAndSend("/game/room/" + roomId, responseMessage);
                if(responseMessage.getGameMessageType() == GameMessageType.FINISH){
                    room.startNewRound(new Question("aboba", "aboba"));
                    messagingTemplate.convertAndSend("/game/room/" + roomId, room.createStartMessage());
                }
            }
        }
    }



}
