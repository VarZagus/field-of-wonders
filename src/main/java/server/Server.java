package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class Server {
    public static final int PORT = 9090;
    private ServerSocket serverSocket;
    private static volatile boolean isServerStart = false;
    private ArrayList<Player> serverList;
    private HashSet<String> names;

    public void startServer(){
        try{
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер успешно запущен!");
            isServerStart = true;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addPlayer(){
        try{
            Socket socket = serverSocket.accept();
            Connection connection = new Connection(socket);
            PlayerAdder playerAdder = new PlayerAdder(connection);
            playerAdder.run();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Player> getServerList(){
        return this.serverList;
    }


    public static void main(String[] args) {


    }


    private class PlayerAdder implements Runnable{

        private Connection connection;

        public PlayerAdder(Connection connection){
            this.connection = connection;
        }


        @Override
        public void run() {
            try {
                String name;
                Message message = new Message(Message.MessageType.NAME_REQUEST);
                connection.sendMsg(message);
                while(true){
                    message = connection.receiveMsg();
                    if(message.getMessageType() == Message.MessageType.NAME_MESSAGE && message.getText() != null || !message.getText().isEmpty()){
                        name = message.getText();
                        if(!names.contains(name)){
                            Player player = new Player(connection, name);
                            serverList.add(player);
                            names.add(name);
                            break;
                        }
                        else{
                            message = new Message(Message.MessageType.USED_NAME);
                            connection.sendMsg(message);
                        }
                    }
                    else{
                        message = new Message(Message.MessageType.WRONG_NAME);
                        connection.sendMsg(message);
                    }
                }

            }catch(Exception e){
                System.out.println("Ошибка получения имени пользователя");
            }

        }
    }
}
