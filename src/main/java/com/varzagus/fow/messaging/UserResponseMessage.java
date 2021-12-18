package com.varzagus.fow.messaging;

import com.varzagus.fow.enums.DrumPosition;
import com.varzagus.fow.enums.GameMessageType;
import com.varzagus.fow.enums.PlayerActionType;
import com.varzagus.fow.game.Board;
import com.varzagus.fow.game.Player;

import java.util.List;
import java.util.Set;

public class UserResponseMessage {

    private int roomId;
    private PlayerActionType wantedAction;
    private List<Player> playerList;
    private Set<Integer> mutedPlayers;
    private Player currentPlayer;
    private PlayerActionType previousAction;
    private BoardStatus board;
    private GameMessageType gameMessageType;
    private DrumPosition currentDrumPosition;
    private String lastAnswer;
    private Player previousPlayer;
    private String question;
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public DrumPosition getCurrentDrumPosition() {
        return currentDrumPosition;
    }

    public void setCurrentDrumPosition(DrumPosition currentDrumPosition) {
        this.currentDrumPosition = currentDrumPosition;
    }

    public String getLastAnswer() {
        return lastAnswer;
    }

    public void setLastAnswer(String lastAnswer) {
        this.lastAnswer = lastAnswer;
    }

    public Player getPreviousPlayer() {
        return previousPlayer;
    }

    public void setPreviousPlayer(Player previousPlayer) {
        this.previousPlayer = previousPlayer;
    }

    public BoardStatus getBoard() {
        return board;
    }

    public void setBoard(BoardStatus board) {
        this.board = board;
    }

    public PlayerActionType getPreviousAction() {
        return previousAction;
    }

    public void setPreviousAction(PlayerActionType previousAction) {
        this.previousAction = previousAction;
    }

    public GameMessageType getGameMessageType() {
        return gameMessageType;
    }

    public void setGameMessageType(GameMessageType gameMessageType) {
        this.gameMessageType = gameMessageType;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public PlayerActionType getWantedAction() {
        return wantedAction;
    }

    public void setWantedAction(PlayerActionType wantedAction) {
        this.wantedAction = wantedAction;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public Set<Integer> getMutedPlayers() {
        return mutedPlayers;
    }

    public void setMutedPlayers(Set<Integer> mutedPlayers) {
        this.mutedPlayers = mutedPlayers;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }





}
