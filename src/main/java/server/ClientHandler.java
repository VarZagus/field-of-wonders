package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private static List<ClientHandler> clients = new ArrayList<>();
    private String name = "defaultName";
    private Socket client;
    private Scanner in;
    private PrintWriter out;
    private Room room;

    public ClientHandler(Socket socket, Room room){
        client = socket;
        this.room = room;
    }

    @Override
    public void run() {
        try{
            try{
                InputStream inputStream = client.getInputStream();
                OutputStream outputStream = client.getOutputStream();
                in = new Scanner(inputStream);
                out = new PrintWriter(outputStream);
                out.println("Введите ваше имя: ");
                out.flush();
                name = in.nextLine();
                System.out.println("Игрок " + name + " присоединился к игре");
                clients.add(this);
                while (in.hasNextLine()){
                }

            }finally {
                client.close();
                System.out.println("Игрок " + name + " отключился от игры");
                clients.remove(this);

            }
        }catch (IOException e){
            System.out.println("Ошибка закрытия клиента!");
        }

    }


    public static int getSize(){
        return clients.size();
    }

    public static void sendMsgAll(String s){
        System.out.println(s);
        clients.forEach(o->{
            o.out.println(s);
            o.out.flush();
        });
    }

}