package server;

public class RoomHost implements Host {
    private User user;
    private Room room;


    public User getUser() {
        return user;
    }

    public Room getRoom() {
        return room;
    }

    public RoomHost(User user, Room room){
        this.room = room;
        this.user = user;
    }

    @Override
    public void setQuestion(Question question) {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void pauseGame() {

    }

    @Override
    public void stopGame() {

    }

    @Override
    public void kickPlayer(Player player) {

    }
}
