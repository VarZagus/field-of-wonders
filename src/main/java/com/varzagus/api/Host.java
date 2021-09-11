package com.varzagus.api;

import com.varzagus.domain.User;
import com.varzagus.game.Player;
import com.varzagus.domain.Question;

import java.util.List;


public interface Host {

    void startNewRound();
    List<User> getUserList();

}
