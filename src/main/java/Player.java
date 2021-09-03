public class Player {
    private Room room;
    private User user;
    private int score;


    public Player(User user, Room room) {
        this.room = room;
        this.user = user;
        score = 0;
    }

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public void getAnswer(){}




}
