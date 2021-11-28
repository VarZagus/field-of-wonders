package com.varzagus.fow;

import com.varzagus.fow.domain.Question;
import com.varzagus.fow.domain.User;
import com.varzagus.fow.game.Round;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class RoundTests {

    public Round createRound(){
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin("user1");
        user2.setLogin("user2");
        user3.setLogin("user3");
        List<User> users = Arrays.asList(user1,user2,user3);
        Question question = new Question();
        question.setQuestion("test");
        question.setAnswer("test");
        Round round = new Round(question,users);
        return round;
    }

    @Test
    void getCurrentPlayerTest(){
        Round round = createRound();
        Assertions.assertEquals("user1",round.getCurrentPlayer().getName());
        round.nextPlayer();
        Assertions.assertEquals("user2", round.getCurrentPlayer().getName());
    }

    @Test
    void setPlayerAnswerGoodTest(){
        Round round = createRound();
        Assertions.assertTrue(round.setPlayerAnswer('t'));
        Assertions.assertTrue(round.setPlayerAnswer("test"));
        Assertions.assertTrue(round.isFinished());
    }

    @Test
    void setPlayerAnswerBadTest(){
        Round round = createRound();
        Assertions.assertFalse(round.setPlayerAnswer("testBad"));
        Assertions.assertTrue(round.getMutedPlayers().contains(0));
        Assertions.assertFalse(round.setPlayerAnswer('o'));
    }

    @Test
    void finishTest(){
        Round round = createRound();
        round.finish();
        Assertions.assertTrue(round.isFinished());
        Assertions.assertEquals("user1",round.getWinner().getName());
    }

}
