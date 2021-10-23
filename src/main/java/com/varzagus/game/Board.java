package com.varzagus.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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

    public void openChar(int pos){
        openedChars[pos] = true;
    }

    public void openAllChars() {
        for(int i = 0; i < openedChars.length; i++){
            openedChars[i] = true;
        }
    }

    //возвращаем текующее состоянее доски
    public List<Character> getCurrentBoard(){
        List<Character> currBoard = new ArrayList<>();
        for(int i = 0; i < openedChars.length; i++){
            if(openedChars[i]) currBoard.add(word.charAt(i));
            else currBoard.add('*');
        }
        return currBoard;
    }

    public boolean isFull(){
        for(int i = 0; i < openedChars.length; i++){
            if(openedChars[i] == false) return false;
        }
        return true;
    }

}
