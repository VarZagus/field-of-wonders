package com.varzagus.fow.application;

import com.varzagus.fow.domain.Question;
import com.varzagus.fow.domain.User;
import com.varzagus.fow.enums.DrumPosition;
import com.varzagus.fow.game.GameWorker;
import com.varzagus.fow.game.Room;
import com.varzagus.fow.game.RoomWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

       RoomWorker roomWorker = initializeRoomWorker();
       Room room = initializeRoom(roomWorker);
       GameWorker gameWorker = new GameWorker(room);
       gameWorker.newRound(new Question("Кто мычит?", "Корова"));
       printLoggerInfo(String.format("Вопрос: %s", gameWorker.getQuestion()));
       gameWorker.start();
       while(!gameWorker.isClosed()) {
           while(!gameWorker.isCurrentRoundFinished()) {
               printLoggerInfo(String.format("Текущее состояние доски: %s", gameWorker.getBoard()));
               gameWorker.rollDrum();
               if(!gameWorker.canPlayerAnswering()) {
                   continue;
               }
               if(gameWorker.getDrumPosition() == DrumPosition.SECTOR_PLUS) {
                  printLoggerInfo(String.format("Игрок %s выбирает позицию для барабана", gameWorker.getCurrentPlayer().getName()));
                  List<Integer> positions = gameWorker.getUnopenedPositions();
                  int position = getPosition(positions, gameWorker.getCurrentPlayer().getName());
                  gameWorker.playerAnswer(position);
                  gameWorker.nextStep();
                  continue;
               }
               printLoggerInfo(String.format("Игрок %s вводит букву", gameWorker.getCurrentPlayer().getName()));
               String ansStr = in.nextLine();
               while(ansStr.length() == 0) {
                   ansStr = in.nextLine();
               }
               char ans = ansStr.toLowerCase(Locale.ROOT).charAt(0);
               if (gameWorker.playerAnswer(ans)) {
                   printLoggerInfo(String.format("Игрок %s выбирает делать ли следующий ход или угадывать слово n/w",
                           gameWorker.getCurrentPlayer().getName()));
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
        RoomWorker roomWorker = new RoomWorker();
        if(logger.isDebugEnabled()) logger.info("Ввод игрока 1");
        User user1 = new User(in.nextLine(),"d");
        if(logger.isDebugEnabled()) logger.info("Ввод игрока 2");
        User user2 = new User(in.nextLine(),"d");
        if(logger.isDebugEnabled()) logger.info("Ввод игрока 3");
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

        return new Room(userList);
    }

    public static void printLoggerInfo(String msg) {
        if(logger.isDebugEnabled()) logger.info(msg);
    }

    public static int getPosition(List<Integer> positions, String name) {
        int position = in.nextInt();
        while(!positions.contains(position)) {
            printLoggerInfo(String.format("Игрок %s выбрал неправильную позицию", name));
            position = in.nextInt();
        }
        in.nextLine();
        return position;
    }

}
