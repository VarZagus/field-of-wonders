package com.varzagus.fow.game;

import com.varzagus.fow.domain.Question;
import com.varzagus.fow.domain.User;
import com.varzagus.fow.enums.DrumPosition;
import com.varzagus.fow.enums.GameMessageType;
import com.varzagus.fow.enums.PlayerActionType;
import com.varzagus.fow.messaging.BoardStatus;
import com.varzagus.fow.messaging.UserReceiveMessage;
import com.varzagus.fow.messaging.UserResponseMessage;
import com.varzagus.fow.repository.QuestionRepository;
import com.varzagus.fow.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Это класс, который управляет самой игрой. Он  хранит в себе
 * пользователей, которые учасвствуют в игре, управляет раундами игры
 * и хранит их историю
 */
public class Room {

    private static int count = 0;

    //Список пользователей
    private List<User> userList = new ArrayList<>();
    private int id;
    //Список раундов
    private final List<Round> rounds;
    private Round currentRound;
    //Заполнена ли комната
    private boolean isFull = false;
    //Запущена ли уже комната для игры
    private boolean started = false;


    public Round getCurrentRound() {
        return currentRound;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public Room(){
        id = count++;
        rounds = new ArrayList<>();
    }

    public boolean isFull() {
        return isFull;
    }

    public void startNewRound(Question question){
        Round round = new Round(question, userList);
        rounds.add(round);
        currentRound = round;
    }


    public List<User> getUserList() {
        return userList;
    }



    /**
     * Добавление пользователя в комнату
     * @param user
     */
    public void addUser(User user) {
        if(!isFull) {
            userList.add(user);
            if(userList.size() == 3) {
                isFull = true;
            }
        }

    }



    public int getId() {
        return id;
    }

    public boolean isStarted() {
        return started;
    }

    public UserResponseMessage sendUserAddMessage(User user, QuestionService questionService) {
        addUser(user);
        if(isFull) {
            startNewRound(questionService.getRandomQuestion());
            return createStartMessage();
        }
        return createWaitingMessage();
    }


    public UserResponseMessage sendUserAnswerMessage(UserReceiveMessage message) {
        PlayerActionType playerActionType = message.getPlayerActionType();
        if(playerActionType == PlayerActionType.ANSWER_CHAR) {
            char answer = message.getAnswer().toLowerCase(Locale.ROOT).charAt(0);
            boolean result = currentRound.setPlayerAnswer(answer);
            if(result) {
                if(currentRound.isFinished()) {
                    return createFinishMessage(PlayerActionType.ANSWER_CHAR);
                }
                return createPlayerAnswerMessage(PlayerActionType.CHOICE);
            }
            return createNextPlayerMessage(message.getPlayerActionType(), message.getAnswer());
        }
        if(playerActionType == PlayerActionType.ANSWER_WORD) {
            boolean result = currentRound.setPlayerAnswer(message.getAnswer());
            if(result) {
                return createFinishMessage(PlayerActionType.ANSWER_WORD);
            }
            else {
                return createNextPlayerMessage(message.getPlayerActionType(), message.getAnswer());
            }
        }
        if(playerActionType == PlayerActionType.ANSWER_INDEX) {
            currentRound.setPlayerAnswer(Integer.parseInt(message.getAnswer()));
            if(currentRound.isFinished()) {
               return createFinishMessage(PlayerActionType.ANSWER_INDEX);
            }
            return createNextPlayerMessage(message.getPlayerActionType(), message.getAnswer());
        }
        if(playerActionType == PlayerActionType.ROLL) {
            DrumPosition drumPosition = currentRound.rollDrum();
            if(drumPosition == DrumPosition.BANKRUPT || drumPosition == DrumPosition.SECTOR_NULL) {
                return createNextPlayerMessage(PlayerActionType.ROLL, message.getAnswer());

            }
            if(drumPosition == DrumPosition.SECTOR_PLUS) {
                return createPlayerAnswerMessage(PlayerActionType.ANSWER_INDEX);
            }
            return createPlayerAnswerMessage(PlayerActionType.ANSWER_CHAR);
        }
        return null;
    }

    public void deleteUser(String username){
        User user = userList.stream().filter(user1 -> user1.getLogin().equals(username)).findFirst().orElse(null);
        if(user == null) return;
        if(userList.contains(user)){
            userList.remove(user);
            isFull = false;
        }
    }

    public UserResponseMessage createStartMessage() {
        UserResponseMessage responseMessage = createDefaultMessage();
        started = true;
        responseMessage.setWantedAction(PlayerActionType.ROLL);
        responseMessage.setGameMessageType(GameMessageType.NEW_ROUND);
        responseMessage.setCurrentPlayer(currentRound.getCurrentPlayer());
        responseMessage.setPlayerList(currentRound.getPlayerList());
        return responseMessage;
    }

    public UserResponseMessage createWaitingMessage() {
        UserResponseMessage responseMessage = new UserResponseMessage();
        responseMessage.setRoomId(id);
        responseMessage.setGameMessageType(GameMessageType.WAITING);
        return responseMessage;
    }

    public UserResponseMessage createFinishMessage(PlayerActionType previousAction) {
        UserResponseMessage responseMessage = createDefaultMessage();
        responseMessage.setGameMessageType(GameMessageType.FINISH);
        responseMessage.setPreviousAction(previousAction);
        responseMessage.setCurrentPlayer(currentRound.getCurrentPlayer());
        return responseMessage;
    }

    public UserResponseMessage createPlayerAnswerMessage(PlayerActionType actionType) {
        UserResponseMessage responseMessage = createDefaultMessage();
        if(actionType == PlayerActionType.ANSWER_CHAR || actionType == PlayerActionType.ANSWER_INDEX){
            responseMessage.setPreviousAction(PlayerActionType.ROLL);
        }
        else if(actionType == PlayerActionType.CHOICE) {
            responseMessage.setPreviousAction(PlayerActionType.ANSWER_CHAR);

        }
        responseMessage.setGameMessageType(GameMessageType.PLAYER_STEP);;
        responseMessage.setWantedAction(actionType);
        responseMessage.setCurrentPlayer(currentRound.getCurrentPlayer());
        return responseMessage;
    }

    public UserResponseMessage createNextPlayerMessage(PlayerActionType previousAction, String answer) {
        UserResponseMessage responseMessage = createDefaultMessage();
        responseMessage.setPreviousPlayer(currentRound.getCurrentPlayer());
        currentRound.nextPlayer();
        responseMessage.setLastAnswer(answer);
        responseMessage.setCurrentPlayer(currentRound.getCurrentPlayer());
        responseMessage.setPreviousAction(previousAction);
        responseMessage.setGameMessageType(GameMessageType.NEXT_STEP);
        return responseMessage;
    }

    public UserResponseMessage createUserLeaveMessage(String username){
        UserResponseMessage responseMessage = new UserResponseMessage();
        responseMessage.setGameMessageType(GameMessageType.LEAVE);
        User user = userList.stream().filter(user1 -> user1.getLogin().equals(username)).findFirst().orElse(null);
        if(user == null) {
            return null;
        }
        Player player = new Player(user);
        responseMessage.setCurrentPlayer(player);
        return responseMessage;
    }

    public UserResponseMessage createDefaultMessage() {
        UserResponseMessage responseMessage = new UserResponseMessage();
        responseMessage.setQuestion(currentRound.getQuestion().getQuestion());
        responseMessage.setRoomId(id);
        BoardStatus boardStatus = new BoardStatus();
        boardStatus.setBoard(currentRound.getBoard().getCurrentBoard());
        boardStatus.setUsedChars(currentRound.getBoard().getUsedChars());
        responseMessage.setCurrentDrumPosition(currentRound.getCurrentDrumPosition());
        responseMessage.setBoard(boardStatus);
        responseMessage.setMutedPlayers(currentRound.getMutedPlayers());
        responseMessage.setPlayerList(currentRound.getPlayerList());
        return  responseMessage;
    }

}