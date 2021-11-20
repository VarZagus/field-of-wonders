'use strict';
var usernamePage = document.querySelector('#username-page');
var gamePage = document.querySelector('#game-page');
var waitingPage = document.querySelector("#waiting-page");
var usernameForm = document.querySelector('#usernameForm');
var gameForm = document.querySelector('#gameForm');
var charInput = document.querySelector('#answer-char');
var wordInput = document.querySelector('#answer-word');
var indexInput = document.querySelector('#num-answer');
var answerWordElement = document.querySelector('#word-answer');
var answerCharElement = document.querySelector('#char-answer');
var answerPosElement = document.querySelector('#pos-answer');
var rollElement = document.querySelector('#roll-submit');
var stepWaitingElement = document.querySelector('#step-wait');
var stepPlayerElement = document.querySelector('#step-player');
var connectingElement = document.querySelector('.connecting');
var boardElement = document.querySelector('#board');
var playersElement = document.querySelector('#players');
var drumElement = document.querySelector('#drumPos');
var currentPlayerElement = document.querySelector('#current-player');
var questionElement = document.querySelector('#question');
var usedCharsElement = document.querySelector("#used-chars");
var stompClient = null;
var username = null;
var roomId = null;
//параметры сообщения
var drumPosition = null;
var playerList = null;
var board = null;
var mutedPlayers = null;
var currentPlayer = null;
var usedChars = null;
var gameMessageType = null;
var previousAction = null;
var wantedAction = null;
var question = null;
var lastMessage;
var previousPlayer = null;
var isSubscribed = false;




var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];
function connect(event) {
    username = document.querySelector('#name').value.trim();
    if(username) {
        usernamePage.classList.add('hidden');
        var socket = new SockJS('http://localhost:8080/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}
function onConnected() {
    usernamePage.classList.add('hidden');
    waitingPage.classList.remove('hidden');
    // Subscribe to the Public Topic
    stompClient.subscribe('/game/'+username + '/game', onMessageReceived);
    // Tell your username to the server
    stompClient.send("/app/game",
        {},
        JSON.stringify({
            userName: username,
            messageType: 'SEARCH',
            answer: null,
            roomId: null,
            playerActionType: null

        })
    )

}
function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}
function sendAnswer(event) {
    if(lastMessage.wantedAction === 'ROLL' || lastMessage.gameMessageType === 'NEXT_STEP') {
        if(stompClient) {
            var message = {
                messageType: 'ANSWER',
                userName: username,
                answer: null,
                roomId: roomId,
                playerActionType: 'ROLL',
            };
            stompClient.send("/app/game", {}, JSON.stringify(message));
        }
    }
    if(lastMessage.wantedAction === 'CHOICE') {
        var playerAnswerWord = wordInput.value.trim();
        var action;
        if(playerAnswerWord !== "") {
            answer = playerAnswerWord;
            action = 'ANSWER_WORD'
        }
        else {
            action = 'ROLL';
        }
        if(stompClient) {
            var message = {
                messageType: 'ANSWER',
                userName: username,
                answer: answer,
                roomId: roomId,
                playerActionType: action
            };
            stompClient.send("/app/game", {}, JSON.stringify(message));
        }
    }
    if(lastMessage.wantedAction === 'ANSWER_CHAR') {
        var answer = charInput.value.trim();
        if(stompClient && answer !== "") {
            var message = {
                messageType: 'ANSWER',
                userName: username,
                answer: answer,
                roomId: roomId,
                playerActionType: 'ANSWER_CHAR'
            };
            stompClient.send("/app/game", {}, JSON.stringify(message));
        }
    }
    if(lastMessage.wantedAction === 'ANSWER_INDEX') {
        var answer = indexInput.value;
        var isOpened = false;
        if(board.charAt(answer-1) != '*') {
            isOpened = true;
            alert("Буква на такой позиции уже открыта!");
            return;
        }
        if(!isOpened && stompClient){
            var message = {
                messageType: 'ANSWER',
                userName: username,
                answer: answer,
                roomId: roomId,
                playerActionType: 'ANSWER_INDEX'
            };
            stompClient.send("/app/game", {}, JSON.stringify(message));
        }

    }
    hideAllAnswers();
    event.preventDefault();
}
function onMessageReceived(payload) {
    hideAllAnswers();
    lastMessage = JSON.parse(payload.body);
    getAllParamsFromMessage(lastMessage);
    gameMessageType = lastMessage.gameMessageType;
    if(lastMessage.gameMessageType === 'NEW_ROUND') {
        alert("НАЧАЛО НОВОГО РАУНДА!")
        stepWaitingElement.classList.add('hidden');
        if(!isSubscribed) {
            roomId = lastMessage.roomId;
            stompClient.subscribe('/game/room/' + lastMessage.roomId, onMessageReceived);
            isSubscribed = true;
        }
        waitingPage.classList.add('hidden');
        connectingElement.classList.add('hidden');
        gamePage.classList.remove('hidden');
        drumPosition = 'PLUS';
        drumElement.innerHTML = drumPosition;
        board = lastMessage.board;
        playerList = lastMessage.playerList;
        currentPlayer = lastMessage.currentPlayer.user.login;
        boardElement.innerHTML=board.board;
        currentPlayerElement.innerHTML=currentPlayer;
        if(currentPlayer === username){
            stepWaitingElement.classList.add('hidden');
            alert("Сейчас ваш ход! Крутите барабан!");
            rollElement.classList.remove('hidden');
        }
        else {
            alert("Сейчас ходит игрок: " + currentPlayer);
            stepWaitingElement.classList.remove('hidden');
            stepPlayerElement.innerHTML='Ожидание хода: ' + currentPlayer;
        }
    }
    if(lastMessage.gameMessageType === 'WAITING') {
        roomId = lastMessage.roomId;
        stompClient.subscribe('/game/room/' + lastMessage.roomId, onMessageReceived);
        isSubscribed = true;
    }
    if(lastMessage.gameMessageType === 'PLAYER_STEP') {

        if(lastMessage.currentPlayer.user.login !== username) {

            if(lastMessage.previousAction === 'ANSWER_CHAR') {
                alert("Игрок " + currentPlayer + " угадал букву!");
            }
           else if(wantedAction === 'ANSWER_INDEX') {
                alert("Игроку " + currentPlayer + " выпал сектор плюс, поэтому он открывает любую букву!");
            }
            else if(lastMessage.previousAction === 'ROLL'){
                alert("Игроку " + currentPlayer + " выпало: " + drumPosition);
            }
            alert("Сейчас ходит игрок: " + currentPlayer);
            stepWaitingElement.classList.remove('hidden');
            stepPlayerElement.innerHTML='Ожидание хода: ' + currentPlayer;
        }
        else {
            stepWaitingElement.classList.add('hidden');
            wantedAction = lastMessage.wantedAction;
            if(wantedAction === 'ROLL') {
                alert("Сейчас ваш ход! Крутите барабан!");
                rollElement.classList.remove('hidden');
            }
            if(wantedAction === 'CHOICE') {
                alert(" Вы угадали букву! Угадывайте слово или крутите барабан!");
                rollElement.classList.remove('hidden');
                answerWordElement.classList.remove('hidden');
            }
            if(wantedAction === 'ANSWER_INDEX') {
                alert("Вам выпал сектор плюс! Выберите букву, которую хотите открыть!");
                answerPosElement.classList.remove('hidden');
            }
            if(wantedAction === 'ANSWER_CHAR') {
                alert("Вам выпало " + drumPosition + "! Выберите букву!");
                answerCharElement.classList.remove('hidden');
            }
        }

    }
    if(lastMessage.gameMessageType === 'NEXT_STEP') {
        if(currentPlayer !== username) {
            stepWaitingElement.classList.remove('hidden');
            stepPlayerElement.innerHTML= 'Ожидание хода: ' + currentPlayer;
        }
        else {
            stepWaitingElement.classList.add('hidden');
        }
        if(previousAction === 'ANSWER_CHAR') {
            if(previousPlayer === username) {
                alert("К сожалению, вы не угадали букву и ход переходит к следующему игроку!");
            }
            else {
                alert("Игрок " + previousPlayer + "не угадал букву, ход переходит к следующему игроку!");
            }
        }
        if(previousAction === 'ANSWER_WORD') {
            if(previousPlayer !== username) {
                alert("Игрок " + previousPlayer + " не угадал слово и выхоидт из игры!");
            }
            else {
                alert("К сожалению, вы не угадалю букву и выбываете из игры!");
            }

        }
        if(previousAction === 'ROLL') {
            if(previousPlayer === username) {
                alert("К сожалению вам выпало " + drumPosition + ", поэтому вы пропускаете ход!");
            }
            else alert("Игроку " + previousPlayer + " выпало " + drumPosition + ", поэтому он пропускает ход!");
        }
        if(currentPlayer == username) {
            rollElement.classList.remove('hidden');
            alert("Сейчас ваш ход, крутите барабан!");
        }
        else {
            alert("Сейчас ходит игрок: " + currentPlayer);
        }
    }

    if(lastMessage.gameMessageType === 'FINISH') {
        alert("Игра окончена! Ответ: " + board + "!" +" Победитель: " + currentPlayer + "!");
    }

}
function hideAllAnswers(){
    rollElement.classList.add('hidden');
    answerCharElement.classList.add('hidden');
    answerPosElement.classList.add('hidden');
    answerWordElement.classList.add('hidden');
}
function clearAllAnswers(){

}

function getAllParamsFromMessage(message) {
    if(message.currentDrumPosition != null){
        drumPosition = message.currentDrumPosition.toString();
    }
    if(drumPosition == null) {
        drumPosition = "";
    }

    playerList = message.playerList;
    mutedPlayers = message.mutedPlayers;
    if(message.currentPlayer != null) {
        currentPlayer = message.currentPlayer.user.login;
    }
    previousAction = message.previousAction;
    usedChars = message.usedChars;
    if(message.board != null) {
        board = message.board.board;
    }
    boardElement.innerHTML = board;
    if(message.question != null){
        question = message.question;
    }
    gameMessageType = message.gameMessageType;
    if(message.previousPlayer != null) {
        previousPlayer = message.previousPlayer.user.login;
    }
    drumElement.innerHTML=drumPosition;
    if(playerList != null) {
        while(playersElement.firstChild) {
            playersElement.removeChild(playersElement.firstChild);
        }
        for (let i = 0; i < playerList.length; i++) {
            var playerElement = document.createElement('li');
            if(mutedPlayers != null) {
                if(mutedPlayers.includes(i)) {
                    playerElement.classList.add("striked");
                }
            }
            var player = document.createTextNode(playerList[i].user.login + ' : ' + playerList[i].score);
            playerElement.appendChild(player);
            playersElement.appendChild(playerElement);
        }
    }
    questionElement.innerHTML=question + "?";
    if(message.board != null){
        usedChars = message.board.usedChars;
        usedCharsElement.innerHTML = usedChars;

    }


}
usernameForm.addEventListener('submit', connect, true)
gameForm.addEventListener('submit', sendAnswer, true)