package com.varzagus.domain;

public class Question {
    private final String question;
    private final String answer;

    public Question(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() { return this.question; }

    public String getAnswer() { return this.answer; }


}
