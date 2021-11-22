package com.varzagus.fow.listeners;

import com.varzagus.fow.enums.GameMessageType;
import com.varzagus.fow.game.Room;
import com.varzagus.fow.game.RoomProvider;
import com.varzagus.fow.messaging.UserResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * Класс для прослушивания ивентов отключения пользователей
 *
 */
@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    RoomProvider roomProvider;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        int roomId = (Integer) headerAccessor.getSessionAttributes().get("room");
        if(username != null) {
            logger.info("User Disconnected : " + username);
            Room room = roomProvider.getRooms().get(roomId);
            if(!room.isStarted()){
                room.deleteUser(username);
            }
            else{
                messagingTemplate.convertAndSend("/game/room/" + roomId,room.createUserLeaveMessage(username));
            }
        }
    }
}
