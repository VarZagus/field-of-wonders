package com.varzagus.fow.game;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Board {
    private final String word;
    private Set<Character> usedChars;
    private boolean[] openedChars;


    public Board(String word){
        this.word = word.toLowerCase(Locale.ROOT);
        usedChars = new HashSet<>();
        openedChars = new boolean[word.length()];
    }

    //возвращаемый тип булена говорит о правильности ответа
    public boolean checkAnswer(char answer){
        if(!usedChars.contains(answer)){
            usedChars.add(answer);
            if(word.contains(Character.toString(answer).toLowerCase(Locale.ROOT) )){
                int currPos = word.indexOf(answer);
                while(currPos != -1) {
                    openChar(currPos);
                    currPos = word.indexOf(answer, currPos+1);
                }
                return true;
            }

        }
        return false;

    }

    public boolean checkAnswer(String answer) {
        if(word.equals(answer)) {
            openAllChars();
            return true;
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
    public String getCurrentBoard(){
        char[] currBoard = new char[openedChars.length];
        for(int i = 0; i < openedChars.length; i++){
            if(openedChars[i]) currBoard[i] = word.charAt(i);
            else currBoard[i] = '*';
        }
        return new String(currBoard);
    }

    public boolean isFull(){
        for(int i = 0; i < openedChars.length; i++){
            if(!openedChars[i]) return false;
        }
        return true;
    }

    public Set<Character> getUsedChars() {
        return usedChars;
    }


}
