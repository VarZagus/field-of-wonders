package com.varzagus.game;

import com.varzagus.api.Host;
import com.varzagus.domain.Question;
import com.varzagus.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Это класс, который управляет самой игрой. Он  хранит в себе
 * пользователей, которые учасвствуют в игре, управляет раундами игры
 * и хранит их историю
 */
public class Room implements Host {

    private String name;
    private List<User> userList;
    private List<Round> rounds;
    private Round currentRound;

    public Round getCurrentRound() {
        return currentRound;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public Room(List<User> userList){

        this.userList = userList;
        rounds = new ArrayList<>();
    }

    public void startNewRound(Question question){
        Round round = new Round(question, userList);
        rounds.add(round);
        currentRound = round;
    }

    @Override
    public List<User> getUserList() {
        return userList;
    }

    /**
     * Удаление пользователя из комнаты и текущего раунда
     * @param user
     */
    public void deleteUser(User user) {
        userList.remove(user);
        currentRound.deleteByUser(user);
    }


}