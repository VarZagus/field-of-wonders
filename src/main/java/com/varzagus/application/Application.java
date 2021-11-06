package com.varzagus.application;

import com.varzagus.domain.Question;
import com.varzagus.domain.User;
import com.varzagus.enums.DrumPosition;
import com.varzagus.game.Room;
import com.varzagus.game.RoomWorker;
import com.varzagus.game.Round;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        logger.info("Тестируем логгер");

        Scanner in = new Scanner(System.in);
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
        //это всё будет в цикле
        if(roomWorker.getUserQueue().size() >= 3) {
            List<User> userList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                userList.add(roomWorker.getUserQueue().remove());
            }
            Room room = new Room(userList);
            while (room.getUserList().size() > 1){
                System.out.println("Введите вопрос:");
                String question  = in.nextLine();
                System.out.println("Введите ответ:");
                String answer = in.nextLine();
                Question question1 = new Question(question, answer);
                room.startNewRound(question1);
                Round round = room.getCurrentRound();
                System.out.println("Игра началась!");
                System.out.println("Вопрос: " + question);
                System.out.println("Доска: " + round.getBoard().getCurrentBoard());
                while(!room.getCurrentRound().isFinished()){
                    if(round.getPlayerList().size() == 1) {
                        System.out.println("Побеждает игрок " );
                    }
                    System.out.println("Ход игрока: " + round.getCurrentPlayer().getName());
                    System.out.println("Вращение барабана...");
                    room.getCurrentRound().currentPlayerRoll();
                    DrumPosition drumPosition = round.getCurrentDrumPosition();
                    System.out.println("На барабане выпало " + drumPosition);
                    if(drumPosition.equals(DrumPosition.BANKRUPT)) {
                        System.out.println("Ваши очки сгорели! Ход переходит к следующему игроку...");
                        round.nextPlayer();
                        continue;
                    }
                    if(drumPosition.equals(DrumPosition.SECTOR_NULL)){
                        System.out.println("Вы пропускаете ход...");
                        round.nextPlayer();
                        continue;
                    }
                    if(drumPosition.equals(DrumPosition.SECTOR_PLUS)){
                        System.out.println("Выберите позицию от 0 до " + (round.getBoard().getCurrentBoard().size()-1));
                        int pos = Integer.parseInt(in.nextLine());
                        while(pos < 0 || pos >= round.getBoard().getCurrentBoard().size() || !round.getBoard().getCurrentBoard().get(pos).equals('*')){
                            System.out.println("Неккоректная позиция");
                            pos = in.nextInt();
                        }
                        in.nextLine();
                        round.getBoard().openChar(pos);
                        System.out.println("Доска: " + round.getBoard().getCurrentBoard());
                    }
                    else {
                        System.out.println("Введите букву: ");
                        char ans = in.nextLine().toLowerCase(Locale.ROOT).charAt(0);
                        if (round.getBoard().checkAnswer(ans)) {
                            System.out.println("Есть такая буква! Откройте букву " + ans + "!");
                            System.out.println("Доска: " + round.getBoard().getCurrentBoard());
                            System.out.println("Делаете следующий ход или угадываете слово? n/w?");
                            ans = in.nextLine().toLowerCase(Locale.ROOT).charAt(0);
                            if (ans == 'n') {
                                continue;
                            }
                            else {
                                System.out.println("Введите слово: ");
                                String word = in.nextLine();
                                if(word.equals(round.getQuestion().getAnswer())){
                                    System.out.println("Поздравляем! Вы угалади слово!");
                                    round.getBoard().openAllChars();
                                }
                                else {
                                    //В принуипе как-то так можно будет обрабатывать если игрок выйдет из сети
                                    System.out.println("К сожалению это неправильное слово! Вы больше не учавствуете в раунде!");
                                    round.deletePlayer(round.getCurrentPlayer());
                                    continue;
                                }
                            }
                        }
                        else {
                            System.out.println("Такой буквы нет!");
                        }

                    }
                    if(round.getBoard().isFull()){
                        System.out.println("Раунд окончен! Ответ:" + round.getQuestion().getAnswer());
                        round.finish();
                        System.out.println("Победитель: " + round.getWinner().getName());
                        continue;
                    }
                    round.nextPlayer();
                }
            }
        }

    }
}
