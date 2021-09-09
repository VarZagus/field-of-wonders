package com.varzagus.game;

import com.varzagus.api.Host;
import com.varzagus.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Room {
    private String name;
    private List<Player> players;
    private Question question;
    private Board board;
    private Host host;

    public Question getQuestion() {return question; }

    public Room(){
        players = new ArrayList<>();
    }




    public static class Drum {
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
        public static void rollDrum(Player player){
            Random random = new Random();
            int act = random.nextInt(16) + 1;
            switch(act){
                case 1 -> multiTwo(player);

                case 2,10 -> fiveTeenPoint(player);

                case 3 -> sectorNull(player);

                case 4, 15 -> twentyPoint(player);

                case 5 ->plus(player);

                case 6, 11, 16 -> fivePoint(player);

                case 7, 13 ->bankrupt(player);

                case 8 -> twentyFivePoint(player);

                case 9,12 -> tenPoint(player);

                case 14 -> multiFour(player);

            }
        }
        private static void multiTwo(Player player) {}

        private static void multiFour(Player player) {}

        private static void plus(Player player) {}

        private static void bankrupt(Player player) {}

        private static void sectorNull(Player player) {}

        private static void fivePoint(Player player) {}

        private static void tenPoint(Player player) {}

        private static void fiveTeenPoint(Player player) {}

        private static void twentyPoint(Player player) {}

        private static void twentyFivePoint(Player player) {}


    }

}