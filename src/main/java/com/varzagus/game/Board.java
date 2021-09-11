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

    //возвращаемый тип булена говорит о правильности ответа
    public boolean checkAnswer(char answer){
        if(!usedChars.contains(answer)){
            usedChars.add(answer);
            if(word.contains(Character.toString(answer) )){
                for(int i = word.indexOf(answer); i <= word.lastIndexOf(answer); i++){
                    if(word.charAt(i) == answer){
                        openedChars[i] = true;
                    }
                }
                return true;
            }

        }
        return false;

    }

}
