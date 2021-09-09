package com.varzagus.game;

import java.util.HashSet;
import java.util.Set;

public class Board {
    private final String word;
    private Set<Character> usedChars;
    private boolean[] openedChars;

    public Board(String word){
        this.word = word;
        usedChars = new HashSet<>();
        openedChars = new boolean[word.length()];
    }


}
