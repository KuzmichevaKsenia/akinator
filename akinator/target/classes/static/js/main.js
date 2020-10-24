'use strict';

var login = $("#login").text();
var stompClient = null;
var playerType = '';

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect(
        {"user" : login},
        function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/user/queue/reply', onMessageReceived);
        });
}

function joinQueue(type) {
    playerType = type;
    stompClient.send("/app/add", {
    }, JSON.stringify({
        'senderName': login,
        'playerType': type
    }));
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    if (message.type === 'GAME_CREATED') {
        document.location.href = '/game/' + message.gameType + '/' + playerType + '/' + message.gameId;
    }
}

$(function () {
    $("#playAsSphinx1").click(function() { joinQueue('SPHINX1'); });
    $("#playAsSphinx2").click(function() { joinQueue('SPHINX2'); });
    $("#playAsAkinator").click(function() { joinQueue('AKINATOR'); });
    connect();
});