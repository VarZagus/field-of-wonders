package com.varzagus.game;

import com.varzagus.domain.Question;
import com.varzagus.domain.User;
import com.varzagus.enums.DrumPosition;

import java.util.*;

/**
 * В этом классе описывается сам процесс игры
 */
public class Round {

    private Question question;
    private List<Player> playerList;
    private boolean isFinished = false;
    private Board board;
    private Drum drum = new Drum();
    private DrumPosition currentDrumPosition;
    private Player winner;
    //какой игрок сейчас ходит
    private int currentPlayer;


    public Round(Question question, List<User> userList){
        playerList = new ArrayList<>();
        this.question = question;
        userList.forEach(user -> playerList.add(new Player(user, this)));
        board = new Board(question.getAnswer());
        currentPlayer = 0;
    }
    //переключение на следующего игрока
    public void nextPlayer(){
        if(currentPlayer < playerList.size()-1) currentPlayer++;
        else currentPlayer = 0;
    }


    public void currentPlayerRoll(){
       currentDrumPosition = drum.rollDrum(playerList.get(currentPlayer));
       currentDrumPosition.actionWithPlayer(playerList.get(currentPlayer));
    }

    public void deletePlayer(Player player) {
        playerList.remove(playerList.indexOf(player));
        if(currentPlayer >= playerList.size()) {
            currentPlayer = 0;
        }
    }

    public boolean playerMove(char playerAnswer){
        boolean res = board.checkAnswer(playerAnswer);
        if(board.isFull()) isFinished = true;
        return res;
    }

    public void finish() {
        isFinished = true;
        winner = playerList.stream().max(Comparator.comparingInt(Player::getScore)).get();
    }

    public Player getWinner(){
        return playerList.get(currentPlayer);

    }

    public Question getQuestion() {
        return question;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }


    public DrumPosition getCurrentDrumPosition() {
        return currentDrumPosition;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {

        return playerList.get(currentPlayer);
    }

}
