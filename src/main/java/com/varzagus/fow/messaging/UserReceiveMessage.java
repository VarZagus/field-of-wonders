package com.varzagus.fow.messaging;

import com.varzagus.fow.enums.PlayerActionType;
import com.varzagus.fow.enums.UserMessageType;

public class UserReceiveMessage {

    private UserMessageType messageType;
    private String userName;
    private String answer;
    private int roomId;
    private PlayerActionType playerActionType;

    public UserMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(UserMessageType messageType) {
        this.messageType = messageType;
    }

    public PlayerActionType getPlayerActionType() {
        return playerActionType;
    }

    public void setPlayerActionType(PlayerActionType playerActionType) {
        this.playerActionType = playerActionType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }


}
