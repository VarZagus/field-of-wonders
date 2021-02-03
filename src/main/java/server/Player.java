package server;

public class Player {
    private String name;
    private int score = 0;
    private Connection connection;

    public Player(Connection connection, String name){
        this.name = name;
        this.connection = connection;
    }

}
