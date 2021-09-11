package com.varzagus.game;

import com.varzagus.api.Host;
import com.varzagus.domain.Question;
import com.varzagus.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Это класс, который управляет самой игрой. Он  хранит в себе
 * пользователей, которые учасвствуют в игре, управляет раундами игры
 * и хранит их историю
 */
public class Room implements Host {

    private String name;
    private List<User> userList;
    private List<Round> rounds;

    public Room(List<User> userList){
        this.userList = userList;
    }

    public void startNewRound(){
        Round round = new Round(new Question("s", "f"), userList);
        rounds.add(round);
    }

    @Override
    public List<User> getUserList() {
        return userList;
    }


}