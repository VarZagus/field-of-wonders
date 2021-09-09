package com.varzagus.api;

import com.varzagus.game.Player;
import com.varzagus.domain.Question;


public interface Host {
    void setQuestion(Question question);
    void startGame();
    void pauseGame();
    void stopGame();
    void kickPlayer(Player player);


}
