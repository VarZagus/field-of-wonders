package com.varzagus.fow.game;

import com.varzagus.fow.domain.User;

public class Player {

    private int score;
    private Round round;
    private User user;

    public Player(User user, Round round) {
        this.user = user;
        this.round = round;
        score = 0;
    }

    public User getUser() {
        return user;
    }

    public Round getRound() {
        return round;
    }

    public String getName(){
        return user.getLogin();
    }




    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

}
