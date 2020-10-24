'use strict';

var userRole = document.querySelector('#role').value;
var userName = document.querySelector('#name').value;
var userAvatar = document.querySelector('#avatar').value;
var userId = document.querySelector('#id').value;
var isPlayer = document.querySelector('#isPlayer').value;
var place = '';
//var bandName = '';

var sphinxName = document.querySelector('#sphinxName');
var sphinxAvatar = document.querySelector('#sphinxAvatar');
var sphinxProfile = document.querySelector('#sphinxProfile');

var characterFrom = document.querySelector('#characterForm');
var characterInput = document.querySelector('#characterInput');
var characterName = document.querySelector('#characterName');

var answer = document.querySelector('#answer');
var ansbtns = document.querySelectorAll('.ansbtn');
var btn1 = document.querySelector('#btn1');
var btn2 = document.querySelector('#btn2');
var btn3 = document.querySelector('#btn3');
var btn4 = document.querySelector('#btn4');
var btn5 = document.querySelector('#btn5');

var question = document.querySelector('#question');
var questionInput = document.querySelector('#questionInput');
var questionButton = document.querySelector('#questionButton');

var akinatorMessage = document.querySelector('#akinatorMessage');
var sphinxMessage = document.querySelector('#sphinxMessage');

var resultCharacterName = document.querySelector('#result-character-name');
var resultCharacterRepeat = document.querySelector('#result-character-repeat');
var resultGuessed = document.querySelector('#result-guessed');
var resultSphinxScore = document.querySelector('#result-sphinx-score');
var resultAkinatorsScore = document.querySelector('#result-akinators-score');
var resultWinnerAkinatorScore = document.querySelector('#result-winner-akinator-score');
var resultBandScore = document.querySelector('#result-band-score');

var connectingElement = document.querySelector('.connecting');

//var numberOfAkinators = 0;
var currentAkinator = 1;
var stompClient = null;

class Timer {
    constructor(initialTime, timer, onTimerExpired, timeout = 1000) {
        this.initialTime = initialTime;
        this.timer = timer;
        this.onTimerExpired = onTimerExpired;
        this.timeout = timeout;
        this.active = false;
        this.curTime = this.initialTime
    }

    tick() {
        var c = this.curTime--;
        var m = (c / 60) >> 0;
        var s = (c - m * 60) + '';
        this.timer.innerHTML = m + ':' + (s.length > 1 ? '' : '0') + s;
        if (!this.active) {
// ignore
        } else if (this.curTime < 0) {
            this.onTimerExpired();
        }else {
            setTimeout(this.tick.bind(this), this.timeout);
        }
    }

    start() {
        this.active = true;
        setTimeout(this.tick.bind(this), 1);
    }

    stop() {
        this.active = false;
    }

    reset() {
        this.curTime = this.initialTime;
    }
}

var time = new Timer(
    300,
    document.querySelector('#timer'),
    function () {
        sendEndData(false)
    });

function connect() {
    time.start();
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    stompClient.subscribe('/topic/public', onMessageReceived);

    var src;
    if (isPlayer === "true") {
        src = "/img/" + userAvatar;
    } else src = "../" + userAvatar;

    if (userRole === '2') {
        place =  document.querySelector('#place').value;
        //bandName = document.querySelector('#bandName').textContent;
    }

    var joinMessage = {
        sender: userRole,
        senderName: userName,
        senderAvatar: src,
        senderId: userId,
        number: place,
        //bandName: bandName,
        type: 'JOIN'
    };
    stompClient.send("/app/chat.addUser", {}, JSON.stringify(joinMessage));
    connectingElement.classList.add('hidden');
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function sendCharacter(event) {
    var character = characterInput.value.trim();

    if (character && stompClient) {
        var characterMessage = {
            content: character,
            type: 'CHARACTER'
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(characterMessage));
        characterInput.value = '';
    }
    event.preventDefault();
}

function sendQuestion(event) {
    var questionContent = questionInput.value.trim();

    if (questionContent && stompClient) {
        var questionMessage = {
            content: questionContent,
            type: 'QUESTION'
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(questionMessage));
        questionInput.value = '';
    }
    event.preventDefault();
}

function sendAnswer(event) {
    var answerContent = event.target.value;

    if (answerContent && stompClient) {
        var answerMessage = {
            content: answerContent,
            currentAkinator: currentAkinator,
            type: 'ANSWER'
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(answerMessage));
    }
    event.preventDefault();
}

function sendEndData (guessed) {
    time.stop();
    var causer = '2';
    if (!guessed) {
        if (sphinxMessage.textContent === '' && characterName.textContent !== '' && akinatorMessage.textContent !== '') {
            causer = '1';
        } else if (sphinxMessage.textContent === '' && characterName.textContent === '') {
            causer = '0';
        }
        /*if (causer === '2') {
            document.querySelector('#akinatorProfile' + currentAkinator).classList.remove('.bg-danger');
            document.querySelector('#akinatorProfile' + currentAkinator).classList.add('.bg-dark');
            if (document.querySelector('#place').value == currentAkinator) {
                $('#myModal2').modal('show');
            }
            var aliveAkinator = false;
            $('.akinator-profile').each(function(index) {
                var num = index + 1;
                if (num <= 9) {
                    if (!$(this).classList.contains('.bg-dark') && document.querySelector('#akinatorName' + currentAkinator).innerHTML !== '') {
                        aliveAkinator = true;
                    }
                }
            });
            if (aliveAkinator) {
                do {
                    if (++currentAkinator > 9) {
                        currentAkinator = 1;
                    }
                    if (document.querySelector('#akinatorName' + currentAkinator).innerHTML === '') {
                        ++currentAkinator;
                    }
                } while (document.querySelector('#akinatorProfile' + currentAkinator).classList.contains('.bg-dark'));
                document.querySelector('#akinatorProfile' + currentAkinator).classList.add('.bg-danger');
                questionButton.disabled = true;
                if (document.querySelector('#number').value == currentAkinator) {
                    questionButton.disabled = false;
                }
                time.reset();
                time.start();
                return;
            }
        }*/
    } else {
        causer = document.querySelector('#akinatorName' + currentAkinator).textContent;
    }
    var akinators = document.getElementsByClassName('akinator-profile');
    var akinatorNames = '';
    for (var i = 0; i < 9; i++) {
        if (!akinators[i].classList.contains('.bg-dark')) {
            var names = akinators[i].getElementsByClassName('akinator-name');
            akinatorNames += names[0].textContent + ',';
        }
    }
    if (userRole === '1') {
        if (stompClient) {
            var endData = {
                type: 'END',
                characterName: characterName.textContent,
                guessed: guessed,
                sphinxName: sphinxName.textContent,
                causer: causer,
                akinatorsName: akinatorNames,
                bandname: "Sherlock"
            };

            stompClient.send("/app/chat.sendEndData2", {}, JSON.stringify(endData));
        }
    }
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    if (message.type === 'JOIN') {
        time.stop();
        var url = "/player/" + message.senderId;
        if (message.sender === '1') {
            sphinxName.innerHTML = message.senderName;
            sphinxAvatar.src = message.senderAvatar;
            sphinxProfile.href = url;
            $('.akinator-name').each(function (index) {
                var num = index + 1;
                if ($(this).text() !== '') {
                    var joinMessage = {
                        sender: '2',
                        senderName: document.querySelector('#akinatorName' + num).textContent,
                        senderAvatar: document.querySelector('#akinatorAvatar' + num).src,
                        senderId: document.querySelector('#akinatorProfile' + num).href,
                        number: $(this).attr('id').slice(-1),
                       // bandName: document.querySelector('#bandName').textContent,
                        type: 'JOIN2'
                    };
                    stompClient.send("/app/chat.addUser", {}, JSON.stringify(joinMessage));
                }
            });

        } else {
            document.querySelector('#akinatorName' + message.number).innerHTML = message.senderName;
            document.querySelector('#akinatorAvatar' + message.number).src = message.senderAvatar;
            document.querySelector('#akinatorProfile' + message.number).href = url;
           // document.querySelector('#bandName').innerHTML = message.bandName;
            place = message.number;
            if (sphinxName.textContent !== '') {
                var joinMessage = {
                    sender: '1',
                    senderName: sphinxName.textContent,
                    senderAvatar: sphinxAvatar.src,
                    senderId: sphinxProfile.href,
                    type: 'JOIN2'
                };
                stompClient.send("/app/chat.addUser", {}, JSON.stringify(joinMessage));
            }
            $(".akinatorName").each(function (index) {
                var num = index + 1;
                if ($(this).text() !== '') {
                    var joinMessage = {
                        sender: '2',
                        senderName: document.querySelector('#akinatorName' + num).textContent,
                        senderAvatar: document.querySelector('#akinatorAvatar' + num).src,
                        senderId: document.querySelector('#akinatorProfile' + num).href,
                        number: $(this).attr('id').slice(-1),
                      //  bandName: document.querySelector('#bandName').textContent,
                        type: 'JOIN2'
                    };
                    stompClient.send("/app/chat.addUser", {}, JSON.stringify(joinMessage));
                }
            });
        }
        time.reset();
        time.start();

    } else if (message.type === 'JOIN2') {
        if (message.sender === '1') {
            sphinxName.innerHTML = message.senderName;
            sphinxAvatar.src = message.senderAvatar;
            sphinxProfile.href = message.senderId;
        } else {
            document.querySelector('#akinatorName' + message.number).innerHTML = message.senderName;
            document.querySelector('#akinatorAvatar' + message.number).src = message.senderAvatar;
            document.querySelector('#akinatorProfile' + message.number).href = message.senderId;
          //  document.querySelector('#bandName').innerHTML = message.bandName;
        }

    } else if (message.type === 'CHARACTER') {
        time.stop();
        characterName.classList.remove('hidden');
        characterName.innerHTML = message.content;
        characterFrom.classList.add('hidden');
        characterFrom.removeEventListener('submit', sendCharacter, true);
        answer.classList.remove('hidden');
        questionButton.disabled = false;
        //document.querySelector('#akinatorProfile' + currentAkinator).classList.add('.bg-danger');
        time.reset();
        time.start();

    } else if (message.type === 'QUESTION') {
        time.stop();
        questionButton.disabled = true;
        sphinxMessage.classList.add('hidden');
        sphinxMessage.innerHTML = '';
        akinatorMessage.classList.remove('hidden');
        akinatorMessage.innerHTML = message.content;
        if (message.content.includes(characterName.textContent)) {
            sendEndData(true);
        } else {
            for (var i = 0; i < ansbtns.length; i++) {
                ansbtns[i].disabled = false;
            }
        }
        time.reset();
        time.start();

    } else if (message.type === 'ANSWER') {
        time.stop();
        for (var i = 0; i < ansbtns.length; i++) {
            ansbtns[i].disabled = true;
        }
        sphinxMessage.classList.remove('hidden');
        sphinxMessage.innerHTML = message.content;
       /*if (message.content === 'Нет' && message.content === 'Вряд ли' && message.content === 'Не знаю') {
            document.querySelector('#akinatorProfile' + currentAkinator).classList.remove('.bg-danger');
            do {
                if (++currentAkinator > 9) {
                    currentAkinator = 1;
                }
                if (document.querySelector('#akinatorName' + currentAkinator).innerHTML === '') {
                    ++currentAkinator;
                }
            } while (document.querySelector('#akinatorProfile' + currentAkinator).classList.contains('.bg-dark'));
            if (stompClient) {
                var changeAkinator = {
                    type: 'CHANGE',
                    currentAkinator: currentAkinator
                };
                stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(changeAkinator));
                document.querySelector('#akinatorProfile' + currentAkinator).classList.add('.bg-danger');
            }
            if (document.querySelector('#place').value == currentAkinator) {*/
                questionButton.disabled = false;
            //}
            time.reset();
            time.start();

        /*} else if (message.type === 'CHANGE') {
            currentAkinator = message.currentAkinator;*/
        } else if (message.type === 'END') {
            time.stop();
            resultCharacterName.innerHTML = message.characterName;
            resultCharacterRepeat.innerHTML = message.characterRepeat;
            resultGuessed.innerHTML = message.guessed;
            resultSphinxScore.innerHTML = message.sphinxScore;
            resultAkinatorsScore.innerHTML = message.akinatorsScore;
            resultWinnerAkinatorScore.innerHTML = message.winnerAkinatorScore;
            resultBandScore.innerHTML = message.bandScore;
            $('#myModal').modal('show');
        }
}

characterFrom.addEventListener('submit', sendCharacter, true);
question.addEventListener('submit', sendQuestion, true);
btn1.addEventListener('click', sendAnswer, true);
btn2.addEventListener('click', sendAnswer, true);
btn3.addEventListener('click', sendAnswer, true);
btn4.addEventListener('click', sendAnswer, true);
btn5.addEventListener('click', sendAnswer, true);
connect();