package com.varzagus.fow;

import com.varzagus.fow.game.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardTests {

    @Test
    void checkAnswerCharTest(){
        Board board = new Board("test");
        Assertions.assertTrue(board.checkAnswer('t'));
        Assertions.assertEquals("t**t", board.getCurrentBoard());
        Assertions.assertFalse(board.checkAnswer('x'));
        Assertions.assertFalse(board.checkAnswer('t'));
    }

    @Test
    void checkAnswerStringTest(){
        Board board = new Board("test");
        Assertions.assertFalse(board.checkAnswer("test1"));
        Assertions.assertTrue(board.checkAnswer("test"));
        Assertions.assertEquals("test", board.getCurrentBoard());
    }

    @Test
    void checkUsedCharsTest(){
        Board board = new Board("test");
        board.checkAnswer('a');
        board.checkAnswer('b');
        Assertions.assertTrue(board.getUsedChars().contains('a'));
        Assertions.assertTrue(board.getUsedChars().contains('b'));
    }

    @Test
    void isFullTest(){
        Board board = new Board("test");
        board.checkAnswer("test");
        Assertions.assertTrue(board.isFull());
    }

    @Test
    void openCharTest(){
        Board board = new Board("test");
        board.openChar(0);
        Assertions.assertEquals("t***", board.getCurrentBoard());
    }

    @Test
    void openALLCharsTest(){
        Board board = new Board("test");
        board.openAllChars();
        Assertions.assertEquals("test",board.getCurrentBoard());
    }


}
