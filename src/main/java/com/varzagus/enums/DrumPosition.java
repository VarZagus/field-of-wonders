package com.varzagus.enums;

import com.varzagus.game.Player;

public enum DrumPosition {
    X2 {
        @Override
        public void actionWithPlayer(Player player) {
            player.setScore(player.getScore()*2);
        }
    },
    X4 {
        @Override
        public void actionWithPlayer(Player player) {
            player.setScore(player.getScore()*4);
        }
    },
    SECTOR_PLUS {
        @Override
        public void actionWithPlayer(Player player) {

        }
    },
    BANKRUPT {
        @Override
        public void actionWithPlayer(Player player) {
            player.setScore(0);
        }
    },
    SECTOR_NULL {
        @Override
        public void actionWithPlayer(Player player) {

        }
    },
    FIVE_POINTS {
        @Override
        public void actionWithPlayer(Player player) {
            player.setScore(player.getScore()+5);
        }
    },
    TEN_POINTS {
        @Override
        public void actionWithPlayer(Player player) {
            player.setScore(player.getScore()+10);
        }
    },
    FIFTEEN_POINTS {
        @Override
        public void actionWithPlayer(Player player) {
            player.setScore(player.getScore() + 15);
        }
    },
    TWENTY_POINTS {
        @Override
        public void actionWithPlayer(Player player) {
            player.setScore(player.getScore()+20);
        }
    },
    TWENTY_FIVE_POINTS {
        @Override
        public void actionWithPlayer(Player player) {
            player.setScore(player.getScore()+25);
        }
    };

    public abstract void actionWithPlayer(Player player);

}
