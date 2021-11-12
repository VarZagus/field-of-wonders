package com.varzagus.fow.game;

import com.varzagus.fow.domain.Question;
import com.varzagus.fow.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Это класс, который управляет самой игрой. Он  хранит в себе
 * пользователей, которые учасвствуют в игре, управляет раундами игры
 * и хранит их историю
 */
public class Room {

    //Список пользователей
    private final List<User> userList;

    private GameWorker gameWorker;
    //Список раундов
    private final List<Round> rounds;
    private Round currentRound;
    //Заполнена ли комната
    private boolean isFull = false;
    //Запущена ли уже комната для игры
    private boolean started = false;


    public Round getCurrentRound() {
        return currentRound;
    }

    public List<Round> getRounds() {
        return rounds;
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


    public List<User> getUserList() {
        return userList;
    }


    /**
     * Добавление пользователя в комнату
     * @param user
     */
    public void addUser(User user) {
        if(!isFull) {
            userList.add(user);
            if(userList.size() == 3) {
                isFull = true;
            }
        }

    }

    /**
     * Удаление пользователя из комнаты и текущего раунда, если игра начата
     * @param user
     */
    public void deleteUser(User user) {
        userList.remove(user);
        if(started) {
            currentRound.deleteByUser(user);
        }
    }


}