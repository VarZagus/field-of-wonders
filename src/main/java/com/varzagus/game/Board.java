package com.varzagus.game;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Board {
    private final String word;
    private Set<Character> usedChars;
    private boolean[] openedChars;

    public Set<Character> getUsedChars() {
        return usedChars;
    }

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

    //возвращаем текующее состоянее доски
    public char[] getCurrentBoard(){
        char[] currBoard = new char[word.length()];
        for(int i = 0; i < currBoard.length; i++){
            if(openedChars[i]) currBoard[i] = word.charAt(i);
            else currBoard[i] = '*';
        }
        return currBoard;
    }

    public boolean isFull(){
        if(Arrays.asList(openedChars).contains(false)) return false;
        return true;
    }

}
