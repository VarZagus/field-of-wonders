package com.varzagus.game;

import com.varzagus.domain.User;

public class Player {
    private User user;
    private int score;
    private Round round;


    public Player(User user, Round round) {
        this.user = user;
        this.round = round;
        score = 0;
    }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public void getAnswer(){}
}
