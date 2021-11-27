package com.varzagus.fow.service;

import com.varzagus.fow.domain.Question;
import com.varzagus.fow.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    public Question getRandomQuestion(){
        List<Question> questionList = questionRepository.findAll();
        if(questionList.isEmpty()){
            Question question = new Question();
            question.setQuestion("Test");
            question.setAnswer("test");
            return question;
        }
        Random random = new Random();
        return questionList.get(random.nextInt(questionList.size()));
    }
}
