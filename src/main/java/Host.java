/**
 * Тот, кто создаёт лобби (Room).
 */
public interface Host {
    void setQuestion(Question question);
    void startGame();
    void pauseGame();
    void stopGame();
    void kickPlayer(Player player);


}
