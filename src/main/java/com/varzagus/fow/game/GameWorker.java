package com.varzagus.fow.game;

import com.varzagus.fow.domain.Question;
import com.varzagus.fow.domain.User;
import com.varzagus.fow.enums.DrumPosition;
import com.varzagus.fow.enums.GameMessageType;
import com.varzagus.fow.enums.PlayerAnswer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Класс, который содержит в себе логику игры
 * и предназначен для управления игровым процессом
 */

public class GameWorker {

    //Список раундов
    private final List<Round> rounds = new ArrayList<>();

    //Текущий раунд
    private Round currentRound;

    //Список пользователей
    private final List<User> userList;

    public GameWorker(List<User> userList) {
        this.userList = userList;
    }

    private void startNewRound(Question question){
        Round round = new Round(question, userList);
        rounds.add(round);
        currentRound = round;
    }


    public void deleteUser(User user) {
        userList.remove(user);
        currentRound.deleteByUser(user);
    }

    public GameStatusMessage sendGameStatus(GameStatusMessage gameStatusMessage) {
        GameMessageType gameMessageType = gameStatusMessage.getGameMessageType();
        if(gameMessageType == GameMessageType.NEW_ROUND) {
            //TODO генерация вопроса из бд
            startNewRound(new Question("aboba", "aboba"));
            return createNewRoundMessage();
        }
        if(gameMessageType == GameMessageType.PLAYER_ROLL) {
            DrumPosition drumPosition = currentRound.rollDrum();
            if(drumPosition == DrumPosition.BANKRUPT || drumPosition == DrumPosition.SECTOR_NULL) {
                currentRound.nextPlayer();
                return createNextPlayerMessage();

            }
            if(drumPosition == DrumPosition.SECTOR_PLUS) {
                return createPlayerAnswerMessage(PlayerAnswer.ANSWER_INDEX);
            }
            return createPlayerAnswerMessage(PlayerAnswer.ANSWER_CHAR);
        }

        //Если получаем ответ от игрока
        if(gameMessageType == GameMessageType.PLAYER_ANSWER) {
            PlayerAnswer playerAnswer = gameStatusMessage.getWantedAction();
            if(playerAnswer == PlayerAnswer.ANSWER_CHAR) {
                char answer = gameStatusMessage.getAnswer().toLowerCase(Locale.ROOT).charAt(0);
                boolean result = currentRound.setPlayerAnswer(answer);
                if(result) {
                    if(currentRound.isFinished()) {
                        return createFinishMessage();
                    }
                    return createPlayerAnswerMessage(PlayerAnswer.CHOICE);
                }
                return createNextPlayerMessage();
            }
            if(playerAnswer == PlayerAnswer.ANSWER_WORD) {
                boolean result = currentRound.setPlayerAnswer(gameStatusMessage.getAnswer());
                if(result) {
                    createFinishMessage();
                }
                else {
                    createNextPlayerMessage();
                }
            }
            if(playerAnswer == PlayerAnswer.ANSWER_INDEX) {
                currentRound.getBoard().openChar(Integer.parseInt(gameStatusMessage.getAnswer()));
            }

        }
        return createNextPlayerMessage();

    }

    private GameStatusMessage createNewRoundMessage() {
        GameStatusMessage statusMessage = new GameStatusMessage();
        statusMessage.setGameMessageType(GameMessageType.NEW_ROUND);
        statusMessage.setBoard(currentRound.getBoard());
        statusMessage.setCurrentPlayer(currentRound.getCurrentPlayer());
        return statusMessage;
    }

    private GameStatusMessage createNextPlayerMessage() {
        GameStatusMessage statusMessage = new GameStatusMessage();
        statusMessage.setGameMessageType(GameMessageType.NEXT_STEP);
        currentRound.nextPlayer();
        statusMessage.setCurrentPlayer(currentRound.getCurrentPlayer());
        statusMessage.setDrumPosition(currentRound.getCurrentDrumPosition());
        statusMessage.setBoard(currentRound.getBoard());
        statusMessage.setMutedPlayers(currentRound.getMutedPlayers());
        return statusMessage;
    }

    private GameStatusMessage createPlayerAnswerMessage(PlayerAnswer playerAnswer) {
        GameStatusMessage statusMessage = new GameStatusMessage();
        statusMessage.setWantedAction(playerAnswer);
        statusMessage.setCurrentPlayer(currentRound.getCurrentPlayer());
        statusMessage.setBoard(currentRound.getBoard());
        statusMessage.setDrumPosition(currentRound.getCurrentDrumPosition());
        return statusMessage;
    }

    private GameStatusMessage createFinishMessage() {
        GameStatusMessage statusMessage = new GameStatusMessage();
        statusMessage.setGameMessageType(GameMessageType.FINISH);
        statusMessage.setBoard(currentRound.getBoard());
        return statusMessage;
    }




}
