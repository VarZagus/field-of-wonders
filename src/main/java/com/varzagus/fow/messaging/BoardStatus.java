package com.varzagus.fow.messaging;

import java.util.Set;

public class BoardStatus {

    private String board;
    private Set<Character> usedChars;

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public Set<Character> getUsedChars() {
        return usedChars;
    }

    public void setUsedChars(Set<Character> usedChars) {
        this.usedChars = usedChars;
    }

}
