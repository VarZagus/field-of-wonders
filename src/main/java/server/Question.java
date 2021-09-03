package server;

import java.util.*;

public class Question {
    private String question;
    private String answer;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public static class QuestionList {
        /**
         * Словарь вопросов. Ключ - вопрос, значение - ответ
         */
        private static List<Question> questions;

        /**
         * Блок инициализации вопросов
         */

        static {
            questions = new ArrayList<>();
            questions.add(new Question("Что мексиканцы изготовляли из волокнистой древесины кактусов", "Воротник"));
            questions.add(new Question("Какое животное дало название распространенному в Древнем Риме способу боевого построения?", "Черепаха"));
            
        }

        public static Question getQuestion(int number) {
            return questions.get(number);
        }

        public static void printQuestionList(){
            System.out.println(0 + ") Создать свой вопрос\n");
            for(int i = 0; i < questions.size(); i++){
                System.out.print((i+1) + ")\n" +"Q: " + questions.get(i).question + "\n" +
                                                        "A: " + questions.get(i).answer+"\n");
                if(i < questions.size() - 1) System.out.println();
            }
        }

        public static int getSize(){
            return questions.size();
        }

        


    }
}
