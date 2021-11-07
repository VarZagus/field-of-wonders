package com.varzagus.game;

import com.varzagus.domain.Question;
import com.varzagus.domain.User;
import com.varzagus.enums.DrumPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * Класс, который содержит в себе логику игры
 * и предназначен для управления игровым процессом
 */

public class GameWorker {

    private static final Logger logger = LoggerFactory.getLogger(GameWorker.class);

    private Room room;

    private Round currentRound;

    private DrumPosition drumPosition;

    //является ли ответ буквы последнего игрока правильным
    private boolean isCorrectCurrentAnswer;

    //Закрыт, если недостаточно игроков
    private boolean isClosed = false;

    public GameWorker(Room room) {
        this.room = room;
    }



    private boolean isPlayerCountCorrect() {
        return room.getUserList().size() > 1;
    }

    /**
     * Создание нового раунда
     * при достаточном количестве игроков
     * @param question
     * @return успех
     */
    public boolean newRound(Question question) {
        if(isPlayerCountCorrect()) {
            room.startNewRound(question);
            currentRound = room.getCurrentRound();
            logger.info("Начало нового раунда");
            return  true;
        }
        logger.info("Недостаточно игроков");
        return  false;
    }

    public void start() {
        logger.info(String.format("Ход игрока: %s",currentRound.getCurrentPlayer().getName()));
    }

    /**
     * Вызов хода для следующего игрока
     * Если игрок остался 1, то он победитель
     */
    public void nextStep() {
        if(currentRound.isFinished()) return;
        if(currentRound.getPlayerList().size() == 1) {
            currentRound.finish();
        }
        else {
            if(!isCorrectCurrentAnswer) {
                currentRound.nextPlayer();
            }
            logger.info(String.format("Ход игрока: %s",currentRound.getCurrentPlayer().getName()));
        }

    }

    /**
     * Вызов вращения барабана для текущего игрока
     * @return
     */
    public DrumPosition rollDrum() {
        drumPosition = currentRound.rollDrum();
        logger.info(String.format("Игроку %s выпало: %s",
                currentRound.getCurrentPlayer().getName(),
                currentRound.getCurrentDrumPosition()));
        if(drumPosition == DrumPosition.BANKRUPT) {
            currentRound.deletePlayer(getCurrentPlayer());
        }
        else {
            doActionWithPlayer();
        }
        return drumPosition;
    }

    /**
     * Здесь производятся манипуляции с очками игрока
     */
    private void doActionWithPlayer() {
        drumPosition.actionWithPlayer(currentRound.getCurrentPlayer());
    }

    /**
     * Получаем ответ-слово от игрока из контроллера
     * @param answer
     * @return правильность ответа
     */
    public boolean playerAnswer(String answer) {
        if(answer.toLowerCase(Locale.ROOT).equals(currentRound.getQuestion().getAnswer().toLowerCase(Locale.ROOT))) {
            logger.info(String.format("Игрок %s угалад слово и становится победителем",currentRound.getCurrentPlayer().getName()));
            currentRound.finish();
            return true;
        }
        logger.info(String.format("Игрок %s не угадал слово и выбывает из игры", currentRound.getCurrentPlayer().getName()));
        currentRound.deletePlayer(currentRound.getCurrentPlayer());

        return false;
    }


    /**
     * Получаем ответ-букву от игрока из контроллера
     * @param answer
     * @return правильность ответа
     */
    public boolean playerAnswer(char answer) {
        if(currentRound.getBoard().checkAnswer(answer)) {
            logger.info(String.format("Игрок %s угадал букву", currentRound.getCurrentPlayer().getName()));
            isCorrectCurrentAnswer = true;
            if(currentRound.getBoard().isFull()) {
                currentRound.finish();
            }
            return true;
        }
        logger.info(String.format("Игрок %s не угадал букву", currentRound.getCurrentPlayer().getName()));
        isCorrectCurrentAnswer = false;
        return false;
    }

    public String getBoard() {
        return room.getCurrentRound().getBoard().getCurrentBoard();
    }

    public DrumPosition getDrumPosition() {
        return drumPosition;
    }

    public String getQuestion() {
        return currentRound.getQuestion().getQuestion();
    }

    /**
     * Удаление пользователя из комнаты,
     * например если он вышел
     * @param user
     */
    public void deleteUser(User user) {
        room.deleteUser(user);
        if(!isPlayerCountCorrect()) isClosed = true;
        logger.info("Комната закрыта, так как недостаточно игроков");
    }

    public boolean isClosed() {
        return isClosed;
    }

    public boolean isCurrentRoundFinished() {
        return currentRound.isFinished();
    }

    public boolean canPlayerAnswering() {
        return drumPosition != DrumPosition.BANKRUPT && drumPosition != DrumPosition.SECTOR_NULL;
    }

    public boolean wasPlayerDeleted() {
        return drumPosition == DrumPosition.BANKRUPT;
    }
    public Player getCurrentPlayer() {
        return currentRound.getCurrentPlayer();
    }



}
