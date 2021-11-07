package com.varzagus.application;

import com.varzagus.domain.Question;
import com.varzagus.domain.User;
import com.varzagus.enums.DrumPosition;
import com.varzagus.game.GameWorker;
import com.varzagus.game.Room;
import com.varzagus.game.RoomWorker;
import com.varzagus.game.Round;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

       RoomWorker roomWorker = initializeRoomWorker();
       List<Room> roomList = new ArrayList<>();
       Room room = initializeRoom(roomWorker);
       GameWorker gameWorker = new GameWorker(room);
       gameWorker.newRound(new Question("Кто мычит?", "Корова"));
       System.out.println(gameWorker.getQuestion());
       System.out.println();
       gameWorker.start();
       while(!gameWorker.isClosed()) {
           while(!gameWorker.isCurrentRoundFinished()) {
               System.out.println(gameWorker.getBoard());
               System.out.println("Ход игрока " + gameWorker.getCurrentPlayer().getName());
               gameWorker.rollDrum();
               if(!gameWorker.canPlayerAnswering()) {
                   continue;
               }
               if(gameWorker.getDrumPosition() == DrumPosition.SECTOR_PLUS) {
                   System.out.print("Введите одну из позиций на барабане: ");
                   gameWorker.getUnopenedPositions().forEach(pos -> System.out.print(pos + " "));
                   System.out.println();
                   int position = in.nextInt();
                   in.nextLine();
                   gameWorker.playerAnswer(position);
                   gameWorker.nextStep();
                   continue;
               }
               System.out.println("Введите букву:");
               String ansStr = in.nextLine();
               while(ansStr.length() == 0) {
                   ansStr = in.nextLine();
               }
               char ans = ansStr.toLowerCase(Locale.ROOT).charAt(0);
               if (gameWorker.playerAnswer(ans)) {
                   System.out.println("Следующий ход или слово? n/w");
                   ans = in.nextLine().toLowerCase(Locale.ROOT).charAt(0);
                   if(ans == 'w') {
                       String word = in.nextLine();
                       gameWorker.playerAnswer(word);
                       break;
                   }
               }
               gameWorker.nextStep();
           }
           gameWorker.newRound(new Question("Кто мычит?", "Корова"));
        }

    }

    public static RoomWorker initializeRoomWorker() {
        System.out.println("Добавляем пользователей...");
        System.out.println("Запускаем обработчик комнат...");
        RoomWorker roomWorker = new RoomWorker();
        System.out.println("Введите имя пользователя 1");
        User user1 = new User(in.nextLine(),"d");
        System.out.println("Введите имя пользователя 2");
        User user2 = new User(in.nextLine(),"d");
        System.out.println("Введите имя пользователя 3");
        User user3 = new User(in.nextLine(), "d");
        //добавление в очередь
        roomWorker.addUser(user1);
        roomWorker.addUser(user2);
        roomWorker.addUser(user3);
        return roomWorker;
    }

    public static Room initializeRoom(RoomWorker roomWorker) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {

            userList.add(roomWorker.getUserQueue().remove());

        }

        Room room = new Room(userList);
        return room;
    }

}
