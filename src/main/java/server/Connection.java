package server;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection implements Closeable, Runnable {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;


    public Connection(Socket socket) throws IOException{
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public void sendMsg(Message message) throws IOException{
        synchronized (this.out){
            out.writeObject(message);
        }
    }

    public Message receiveMsg() throws IOException, ClassNotFoundException{
        synchronized (this.in){
            Message message = (Message) in.readObject();
            return message;
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
        out.close();
        in.close();
    }

    @Override
    public void run() {

    }



}
