package com.varzagus.game;

import com.varzagus.api.Host;
import com.varzagus.domain.Question;
import com.varzagus.domain.User;

public class RoomWorker implements Host {
    private Room room;


    public Room getRoom() {
        return room;
    }

    public RoomWorker(User user, Room room){
        this.room = room;
    }

    @Override
    public void setQuestion(Question question) {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void pauseGame() {

    }

    @Override
    public void stopGame() {

    }


    @Override
    public void kickPlayer(Player player) {

    }


}
