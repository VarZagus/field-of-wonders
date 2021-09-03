package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *Основной класс, который запускает клиента
 */
public class ClientMain {
    private static Socket socket;
    private static Scanner in;
    private static Scanner serverInput;
    private static PrintWriter serverOutput;


    /**
     * main-метод для запуска клиентского приложения
     */
    public static void main(String[] args) {
        in = new Scanner(System.in);
        System.out.print("Введите адрес сервера: ");
        String address = in.nextLine();
        System.out.print("Введите порт: ");
        int port = in.nextInt();
        in.nextLine();
        try{
            try{
                socket = new Socket(address,port);
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                serverInput = new Scanner(inputStream);
                serverOutput = new PrintWriter(outputStream);
                System.out.println("Вы успешно подключились к серверу!");
                while (serverInput.hasNextLine()){
                    System.out.println(serverInput.nextLine());
                    serverOutput.println(in.nextLine());
                    serverOutput.flush();
                }

            }catch (IOException e){
                System.out.println("Ошибка подключения к хосту!");
            }
            finally {
                socket.close();
                System.out.println("Вы успешно отключились от сервера!");
            }
        }catch (IOException e){
            System.out.println("Ошибка отключения от сервера!");
        }

    }

}
