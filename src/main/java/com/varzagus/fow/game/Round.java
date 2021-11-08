package com.varzagus.fow.game;

import com.varzagus.fow.domain.Question;
import com.varzagus.fow.domain.User;
import com.varzagus.fow.enums.DrumPosition;

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


    public DrumPosition rollDrum(){
       currentDrumPosition = drum.rollDrum(getCurrentPlayer());
       return currentDrumPosition;
    }

    public void deletePlayer(Player player) {
        playerList.remove(playerList.indexOf(player));
        if(currentPlayer >= playerList.size()) {
            currentPlayer = 0;
        }
    }

    public void deleteByUser(User user) {
        Player player = playerList.stream().filter(p -> p.getUser() == user).findFirst().orElse(null);
        if(player != null) {
            deletePlayer(player);
        }
    }

    public boolean playerMove(char playerAnswer){
        boolean res = board.checkAnswer(playerAnswer);
        if(board.isFull()) isFinished = true;
        return res;
    }



    public void finish() {
        isFinished = true;
        winner = getCurrentPlayer();
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
        if(currentPlayer >= playerList.size()) {
            currentPlayer = 0;
        }
        return playerList.get(currentPlayer);
    }

}
