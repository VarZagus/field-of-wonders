package com.varzagus.fow.api;

import com.varzagus.fow.domain.User;
import com.varzagus.fow.domain.Question;

import java.util.List;


public interface Host {

    void startNewRound(Question question);
    List<User> getUserList();

}
