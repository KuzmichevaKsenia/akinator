'use strict';

var userId = document.querySelector('#userId').value;
var login = document.querySelector('#login').value;
var bandId = document.querySelector('#bandId').value;
var bandname = document.querySelector("#bandname").textContent;
var connectingElement = document.querySelector('.connecting');
var place = document.querySelector('#place-' + userId).textContent;
var stompClient = null;

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    stompClient.subscribe('/topic/public/' + bandId, onMessageReceived);

    var joinMessage = {
        senderId: userId,
        type: 'JOIN'
    };
    stompClient.send("/app/meeting/" + bandId + "/addUser", {}, JSON.stringify(joinMessage));
    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    if (message.type === 'JOIN') {
        document.querySelector("#ready-" + message.senderId).innerHTML = "есть";
        $(".ready").each(function() {
            if ($(this).text() === 'есть') {
                var id = $(this).attr('id').substring(6);
                var joinMessage = {
                    senderId: id,
                    type: 'JOIN2'
                };
                stompClient.send("/app/meeting/" + bandId + "/addUser", {}, JSON.stringify(joinMessage));
            }
        });
    } else if (message.type === 'JOIN2') {
        document.querySelector("#ready-" + message.senderId).innerHTML = "есть";
    } else if (message.type === 'LEAVE') {
        document.querySelector("#ready-" + message.senderId).innerHTML = "нет";
    } else if (message.type === 'START') {
        connectToQueue();
    } else if (message.type === 'GAME_CREATED') {
        document.location.href = '/game/2/PARTY/' + bandname + '/' + message.gameId + '/' + place;
    }
}

function startTheGame () {
    var joinMessage = {
        type: 'START',
        bandname: bandname
    };
    stompClient.send("/app/meeting/" + bandId + "/startTheGame", {}, JSON.stringify(joinMessage));

    var senderNames = '';
    $('.ready').each(function(index) {
        var num = index + 1;
        if ($(this).text() !== 'нет') {
            senderNames += $('.readyName' + num).text() + ', ';
        }
    });
    connectToQueue(function () {
        stompClient.send("/app/add", {}, JSON.stringify({
            'senderName': senderNames,
            'playerType': 'PARTY'
        }));
    });
}

function goOut () {
    var outMessage = {
        type: 'LEAVE',
        senderId: userId
    };
    stompClient.send("/app/meeting/" + bandId + "/removeUser", {}, JSON.stringify(outMessage));
    document.location.href = '/main';
}

function connectToQueue(afterConnectionCallback = function () {}) {
    disconnect();
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect(
        {"user" : login},
        function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/user/queue/reply', onMessageReceived);
            afterConnectionCallback()
        });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
}

document.querySelector('#start-btn').addEventListener('click', startTheGame, true);
document.querySelector('#goOut-btn').addEventListener('click', goOut, true);
connect();