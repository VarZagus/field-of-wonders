package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Основной класс, который запускает сервер
 */
public class ServerMain {
    private static ServerSocket serverSocket;
    private static int port;
    private static Scanner in;
    private static boolean closed = false;


    /**
     * main-метод для запуска серверного приложения
     */
    public static void main(String[] args) {
        System.out.print("Введите порт: ");
        in = new Scanner(System.in);
        port = in.nextInt();
        in.nextLine();
        try {
            try {
                serverSocket = new ServerSocket(port, 5);
                System.out.println("Сервер был успешно запущен на порту: " + port);
                while (!closed) {
                    System.out.println("Хотите ли начать новую игру? Y/N");
                    String answer = in.nextLine();
                    if (answer.toLowerCase().equals("y")) {
                        System.out.println("Выберите вопрос или создайте свой:");
                        Question.QuestionList.printQuestionList();
                        answer = in.nextLine();
                        while (!answer.matches("\\d+") || Integer.parseInt(answer) < 0 || Integer.parseInt(answer) > Question.QuestionList.getSize()) {
                            System.out.println("Неккоректный ввод! Введите снова:");
                            answer = in.nextLine();
                        }
                        int number = Integer.parseInt(answer);
                        Question question;
                        if (number == 0) {
                            System.out.println("Введите вопрос: ");
                            String ques = in.nextLine();
                            System.out.println("Введите ответ: ");
                            String ans = in.nextLine();
                            question = new Question(ques, ans);
                        } else question = Question.QuestionList.getQuestion(number - 1);
                        Room room = new Room(question, serverSocket);
                        room.waitPlayers();
                        ClientHandler.sendMsgAll("Начала новой игры!");
                        room.play();


                    } else {
                        closed = true;
                    }
                }


            } catch (IOException exception) {
                System.out.println("Произошла ошибка при запуске сервера!");
            } finally {
                serverSocket.close();
                System.out.println("Сервер успешно завершил работу.");
            }
        } catch (IOException exception){
            System.out.println("Произошла ошибка при закрытии сервера!");
            exception.printStackTrace();
        }



    }




}
