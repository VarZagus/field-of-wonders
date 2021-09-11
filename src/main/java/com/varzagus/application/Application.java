package com.varzagus.application;

import com.varzagus.domain.User;
import com.varzagus.game.Room;
import com.varzagus.game.RoomWorker;

public class Application {
    public static void main(String[] args) {
        User user1 = new User("d","d");
        User user2 = new User("b","b");
        User user3 = new User("f","f");
        User user4 = new User("e", "n");
        RoomWorker roomWorker = new RoomWorker();
        roomWorker.addUser(user1);
        roomWorker.addUser(user2);
        roomWorker.addUser(user3);
        roomWorker.addUser(user4);
        Room room = roomWorker.createRoom();
        room.startNewRound();


    }
}
