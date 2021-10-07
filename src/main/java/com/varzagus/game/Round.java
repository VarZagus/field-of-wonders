package com.varzagus.game;

import com.varzagus.api.Rollable;
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
    private Rollable drum = new Drum();
    private DrumPosition currentDrumPosition;
    //какой игрок сейчас ходит
    private int currentPlayer=0;


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

    public DrumPosition getCurrentDrumPosition() {
        return currentDrumPosition;
    }

    public void playerRoll(Player rollerPlayer){
       currentDrumPosition = drum.rollDrum(rollerPlayer);
    }

    public boolean playerMove(char playerAnswer){
        boolean res = board.checkAnswer(playerAnswer);
        if(board.isFull()) isFinished = true;
        return res;
    }

    public Question getQuestion() {
        return question;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }


    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return playerList.get(currentPlayer);
    }

}
