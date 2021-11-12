package com.varzagus.fow.game;

import com.varzagus.fow.enums.DrumPosition;
import com.varzagus.fow.enums.GameMessageType;
import com.varzagus.fow.enums.PlayerAnswer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameStatusMessage {

    private GameMessageType gameMessageType;

    private DrumPosition drumPosition;

    private Player currentPlayer;

    private Board board;

    private PlayerAnswer wantedAction;

    public void setMutedPlayers(Set<Integer> mutedPlayers) {
        this.mutedPlayers = mutedPlayers;
    }

    private Set<Integer> mutedPlayers;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private String answer;

    public GameMessageType getGameMessageType() {
        return gameMessageType;
    }

    public void setGameMessageType(GameMessageType gameMessageType) {
        this.gameMessageType = gameMessageType;
    }

    public DrumPosition getDrumPosition() {
        return drumPosition;
    }

    public void setDrumPosition(DrumPosition drumPosition) {
        this.drumPosition = drumPosition;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public PlayerAnswer getWantedAction() {
        return wantedAction;
    }

    public void setWantedAction(PlayerAnswer wantedAction) {
        this.wantedAction = wantedAction;
    }
}
