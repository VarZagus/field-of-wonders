package com.varzagus.game;

import com.varzagus.domain.Question;
import com.varzagus.domain.User;

import java.util.*;

/**
 * В этом классе описывается сам процесс игры
 */
public class Round {

    private Question question;
    private List<Player> playerList;
    private boolean isFinished = false;
    private Board board;

    public int getCurrentDrumPosition() {
        return currentDrumPosition;
    }

    private int currentDrumPosition = 0;
    //какой игрок сейчас ходит
    private int currentPlayer=0;

    public Round(Question question, List<User> userList){
        playerList = new ArrayList<>();
        this.question = question;
        userList.forEach(user -> playerList.add(new Player(user, this)));
        board = new Board(question.getAnswer());
        currentPlayer = 0;
    }
    //переключение на следующего игрока
    public void nextPlayer(){
        if(currentPlayer < playerList.size()-1) currentPlayer++;
        else currentPlayer = 0;
    }

    public void playerRoll(Player rollerPlayer){
       currentDrumPosition = Drum.rollDrum(rollerPlayer);
    }

    public boolean move(char playerAnswer){
        boolean res = board.checkAnswer(playerAnswer);
        if(board.isFull()) isFinished = true;
        return res;
    }

    public Question getQuestion() {
        return question;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }


    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return playerList.get(currentPlayer);
    }


    /**
     * Барабан будет изменён, так как не очень удобно пользоваться из-за большого кол-во цифр, которые отвечают за одну и ту же позицию
     * что не имеет никакого смысла и только усложняет код. Возможно я его полностью переделаю. Думаю создать сначала отделньый интерфейс
     * Сначала будут идти номера "выигрышных" позиций, а затем "проигрышных" (где игрок пропускает ход)
     */

    public static class Drum {
        private static Random random = new Random();
        /**
         * Имитирует вращение барабана
         * Список значения ячеек и их порядковых номеров:
         * X2: 1
         * X4: 14
         * СЕКТОР ПЛЮС: 5
         * БАНКРОТ: 7 и 13
         * СЕКТОР НОЛЬ: 3
         * 5 ОЧКОВ: 6, 11, 16
         * 10 ОЧКОВ: 9, 12
         * 15 ОЧКОВ: 2,10
         * 20 ОЧКОВ: 4 и 15
         * 25 ОЧКОВ: 8
         * Номера не по порядку, потому что в теории если бы пришлось
         * отрисовывать барабан, то именно на таких позициях и были бы данные значения
         */
        public static int rollDrum(Player player){
            int act = random.nextInt(16) + 1;
            switch(act){
                case 1 :
                    multiTwo(player);
                    return 1;

                case 2,10 :
                    fiveTeenPoint(player);
                    return 2;

                case 3 :
                    sectorNull(player);
                    return 3;

                case 4, 15 :
                    twentyPoint(player);
                    return 4;

                case 5 :
                    plus(player);
                    return 5;

                case 6, 11, 16 :
                    fivePoint(player);
                    return 6;

                case 7, 13 :
                    bankrupt(player);
                    return 7;

                case 8 :
                    twentyFivePoint(player);
                    return 8;

                case 9,12 :
                    tenPoint(player);
                    return 9;

                case 14 :
                    multiFour(player);
                    return 14;

            }
            return 0;
        }
        private static void multiTwo(Player player) {
            player.setScore(player.getScore()*2);

        }

        private static void multiFour(Player player) {
            player.setScore(player.getScore()*4);
        }

        private static void plus(Player player) {}

        private static void bankrupt(Player player) {
            player.setScore(0);

        }

        private static void sectorNull(Player player) {}

        private static void fivePoint(Player player) {
            player.setScore(player.getScore()+5);

        }

        private static void tenPoint(Player player) {
            player.setScore(player.getScore()+10);

        }

        private static void fiveTeenPoint(Player player) {
            player.setScore(player.getScore()+15);

        }

        private static void twentyPoint(Player player) {
            player.setScore(player.getScore()+20);

        }

        private static void twentyFivePoint(Player player) {
            player.setScore(player.getScore()+25);

        }


    }
}
