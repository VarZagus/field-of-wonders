package com.varzagus.fow.controllers;

import com.varzagus.fow.domain.GameResult;
import com.varzagus.fow.domain.Question;
import com.varzagus.fow.domain.User;
import com.varzagus.fow.enums.GameMessageType;
import com.varzagus.fow.enums.UserMessageType;
import com.varzagus.fow.game.Player;
import com.varzagus.fow.game.Room;
import com.varzagus.fow.game.RoomProvider;
import com.varzagus.fow.messaging.UserReceiveMessage;
import com.varzagus.fow.messaging.UserResponseMessage;
import com.varzagus.fow.repository.GameResultRepository;
import com.varzagus.fow.repository.QuestionRepository;
import com.varzagus.fow.repository.UserRepository;
import com.varzagus.fow.service.AuthService;
import com.varzagus.fow.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class GameController {

    @Autowired
    private RoomProvider roomProvider;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    GameResultRepository gameResultRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;


    @MessageMapping("/game")
    public void gameMessage(@Payload UserReceiveMessage userGameMessage, SimpMessageHeaderAccessor headerAccessor) throws InterruptedException {
        UserResponseMessage responseMessage;
        List<Room> rooms = roomProvider.getRooms();
        if(userGameMessage.getMessageType() == UserMessageType.SEARCH) {
           User user = userRepository.findByLogin(userGameMessage.getUserName());
            if(rooms.size() == 0 || rooms.get(rooms.size()-1).isFull()) {
               Room room = new Room();
               rooms.add(room);
               responseMessage = room.sendUserAddMessage(user, questionService);
                responseMessage.setCount(1);
            }
            else {
                responseMessage = rooms.get(rooms.size() - 1).sendUserAddMessage(user, questionService);
                responseMessage.setCount(rooms.get(rooms.size()-1).getUserList().size());
            }
            messagingTemplate.convertAndSend("/" + userGameMessage.getUserName() + "/game", responseMessage);
            messagingTemplate.convertAndSendToUser(userGameMessage.getUserName(),"/game",responseMessage);
            messagingTemplate.convertAndSend("/game/room/" + responseMessage.getRoomId(), responseMessage);
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
                    GameResult gameResult = new GameResult();
                    gameResult.setDate(LocalDate.now());
                    gameResult.setWinner(room.getCurrentRound().getWinner().getUser());
                    List<Player> players = room.getCurrentRound().getPlayerList();
                    players.forEach(p->{
                        gameResult.getScores().put(p.getUser(), p.getScore());
                    });
                    GameResult newGameResult = gameResultRepository.save(gameResult);
                    List<User> users = room.getUserList();
                    List<User> newUsers = new ArrayList<>();
                    users.forEach(u->{
                        u.getResults().add(newGameResult);
                        newUsers.add(userRepository.save(u));
                    });
                    room.setUserList(newUsers);
                    room.startNewRound(questionService.getRandomQuestion());
                    messagingTemplate.convertAndSend("/game/room/" + roomId, room.createStartMessage());
                }
                if(responseMessage.getGameMessageType() == GameMessageType.LEAVE){
                    rooms.remove(room);
                }
            }
        }
    }

    @GetMapping("/index")
    public String index(Map<String, Object> model){
        model.put("user", authService.getLoggedInUser());
        model.put("userCount", sessionRegistry.getAllPrincipals().size());
        return "index";
    }

    @GetMapping("/")
    public String description(Map<String, Object> model) {
        model.put("user", authService.getLoggedInUser());
        model.put("count", questionRepository.findAll().size());
        return "description";
    }

    public int getLoggedInUsersCount() {
        int count = 0;
        final List<Object> allPrincipals = sessionRegistry.getAllPrincipals();

        for (final Object principal : allPrincipals) {
            if (principal instanceof UserDetails) {
                List<SessionInformation> activeUserSessions =
                        sessionRegistry.getAllSessions(principal,
                                /* includeExpiredSessions */ false); // Should not return null;
                if (!activeUserSessions.isEmpty()) {
                    count++;
                }
            }
        }
        return count;
    }




}
