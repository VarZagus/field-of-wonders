package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Данный класс представляет собой саму игру.
 */
public class Room{
    private Question question;
    private String currentAnswer;
    private List<ClientHandler> playerList;
    private ServerSocket serverSocket;

    public Room(Question question, ServerSocket serverSocket){

        this.question = question;
        this.serverSocket = serverSocket;
    }


    public void play(){

    }

    /**
     * Метод для ожидания необходимого кол-ва игроков. В нашем случае 4.
     */
    public void waitPlayers(){
        System.out.println("Ожидание игроков...");
        while (ClientHandler.getSize() < 4){
            try{
                Socket client = serverSocket.accept();
                System.out.println("Подключение нового игрока...");
                ClientHandler handler = new ClientHandler(client,this);
                Thread thread = new Thread(handler);
                thread.start();
            } catch (IOException e){
                System.out.println("Ошибка соединния с клиентом!");
            }
        }

    }
}
