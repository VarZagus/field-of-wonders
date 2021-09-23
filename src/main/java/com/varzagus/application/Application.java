package com.varzagus.application;

import com.varzagus.domain.User;
import com.varzagus.game.Room;
import com.varzagus.game.RoomWorker;
import com.varzagus.game.Round;

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
        Round current = room.getCurrentRound();
        current.playerRoll(current.getCurrentPlayer());
        System.out.println("Текущее состояние барабана:" + current.getCurrentDrumPosition());
        if(current.getCurrentDrumPosition() != 7 && current.getCurrentDrumPosition() != 8){
            if(current.move('f')){
                System.out.println("это правильная буква!");
                if(current.isFinished()){
                    System.out.println("Раунд закончился!!!!!");
                }

            }
        }


    }
}
