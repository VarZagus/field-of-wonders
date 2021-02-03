package server;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;

public class Message implements Serializable {
    private String text;
    private Map<String, Integer> playerList;
    private MessageType messageType;
    private String currentWord;
    private char currentLetter;

    public Message(MessageType messageType){
        this.messageType = messageType;
        this.playerList = null;
    }

    public Message(MessageType messageType, String text){
        this.messageType = messageType;
        this.text = text;
        this.playerList = null;
    }

    public Message(MessageType messageType, String text, Map<String, Integer> playerList, String currentWord, char currentLetter){
        this.messageType = messageType;
        this.text = text;
        this.playerList = playerList;
        this.currentLetter = currentLetter;
    }

    public static enum MessageType{
        USED_NAME,
        WRONG_NAME,
        NAME_REQUEST,
        NAME_MESSAGE,
        TEXT_MESSAGE,
        DATA_MESSAGE;
    }

    public String getText(){
        return text;
    }

    public MessageType getMessageType(){
        return messageType;
    }
    public Map<String, Integer> getPlayerList(){
        return playerList;
    }

}


