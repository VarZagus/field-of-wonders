package com.varzagus.fow.game;

import com.varzagus.fow.domain.Question;
import com.varzagus.fow.domain.User;
import com.varzagus.fow.enums.DrumPosition;

import java.util.*;

/**
 * В этом классе описывается сам процесс игры
 */
public class Round {

    private final Question question;
    private final List<Player> playerList;
    private boolean isFinished = false;
    private Board board;
    private Drum drum = new Drum();
    private DrumPosition currentDrumPosition;
    private Player winner;
    //какой игрок сейчас ходит
    private int currentPlayerIndex;

    public Set<Integer> getMutedPlayers() {
        return mutedPlayers;
    }

    //Игроки, которые не угадали слово и выбыли из раунда
    private final Set<Integer> mutedPlayers = new HashSet<>();


    public Round(Question question, List<User> userList){
        playerList = new ArrayList<>();
        this.question = question;
        userList.forEach(user -> playerList.add(new Player(user, this)));
        board = new Board(question.getAnswer());
        currentPlayerIndex = 0;
    }

    //переключение на следующего игрока
    public void nextPlayer(){
        if(isAllPlayersMuted()) {
            return;
        }
        if(currentPlayerIndex < playerList.size()-1) currentPlayerIndex++;
        else currentPlayerIndex = 0;
        if(mutedPlayers.contains(currentPlayerIndex)) {
            nextPlayer();
        }
    }


    public DrumPosition rollDrum(){
       currentDrumPosition = drum.rollDrum(getCurrentPlayer());
       return currentDrumPosition;
    }

    public void mutePlayer(Player player) {
        mutedPlayers.add(playerList.indexOf(player));

    }

    public void deleteByUser(User user) {
        Player player = playerList.stream().filter(p -> p.getUser() == user).findFirst().orElse(null);
        if(player != null) {
            playerList.remove(playerList.indexOf(player));
            if(currentPlayerIndex >= playerList.size()) {
                currentPlayerIndex = 0;
            }
        }
    }

    public boolean setPlayerAnswer(char playerAnswer){
        boolean res = board.checkAnswer(playerAnswer);
        if(board.isFull()) isFinished = true;
        return res;
    }
    
    public boolean setPlayerAnswer(String answer) {
        boolean result = board.checkAnswer(answer);
        if(result) {
            finish();
        }
        else {
            mutedPlayers.add(currentPlayerIndex);
        }
        return result;
    }
    
    public void setPlayerAnswer(int answer) {
        board.openChar(answer);
    }


    public void finish() {
        isFinished = true;
        winner = getCurrentPlayer();
    }

    public Player getWinner(){
        return playerList.get(currentPlayerIndex);

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

    //Получить игрока, который делает ход
    public Player getCurrentPlayer() {
        if(currentPlayerIndex >= playerList.size()) {
            currentPlayerIndex = 0;
        }
        return playerList.get(currentPlayerIndex);
    }

    private boolean isAllPlayersMuted() {
        for(int i = 0; i < playerList.size(); i++) {
            if(!mutedPlayers.contains(i)) {
                return false;
            }
        }
        return true;
    }

}
