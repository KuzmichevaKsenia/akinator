<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
    <div class="back-game-1 back-height pt-10 overx">
        <input type="hidden" id="role" value="${role}" />
        <input type="hidden" id="gameId" value="${gameId}" />
        <input type="hidden" id="name" value="${name}" />
        <input type="hidden" id="avatar" value="${avatar}" />
        <input type="hidden" id="id" value="${user_id}" />
        <input type="hidden" id="isPlayer" value="${isPlayer?string}" />
        <div class="connecting">
            Connecting...
        </div>
        <div class="row">
            <div class="col center-align">
                <img src="/static/repoimages/spinx.png" width="130" />
                <p>Сфинкс</p>
            </div>
            <div class="col"><p class="bg-info" id="help"></p></div>
            <div class="col center-align"><h2 id="timer"></h2></div>
            <div class="col center-align">
                <img src="/static/repoimages/akinator.png" width="100" />
                <p class="center-align">Акинатор</p>
            </div>
        </div>
        <div class="row">
            <div class="col center-align">
                <a id="sphinxProfile" target="_blank">
                    <img id="sphinxAvatar" width="200" height="270" />
                    <p id="sphinxName"></p>
                </a>
            </div>
            <div class="col <#if role=2>hidden</#if>">
                <form id="characterForm" class="mb-3">
                    <input type="text" id="characterInput" class="form-control" placeholder="Введите персонажа" aria-label="Recipient's username" aria-describedby="basic-addon2">
                    <div class="input-group-append">
                        <button class="btn btn-primary" type="submit">Загадать</button>
                    </div>
                </form>
                <p>Персонаж: <span id="characterName" class="hidden"></span></p>
                <form id="answer" class="hidden text-center">
                    <div class="row">
                        <div class="col-sm-2">
                            <button type="button" id="btn1" disabled="disabled" class="btn btn-success ansbtn mb-3" value="Да">Да</button>
                        </div>
                        <div class="col-sm-3">
                            <button type="button" id="btn3" disabled="disabled" class="btn btn-warning ansbtn mb-3" value="Не знаю">Не знаю</button><br />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-3">
                            <button type="button" id="btn4" disabled="disabled" class="btn btn-dark ansbtn mb-3" value="Вряд ли">Вряд ли</button><br />
                        </div>
                        <div class="col-sm-2">
                            <button type="button" id="btn5" disabled="disabled" class="btn btn-danger ansbtn mb-3 ml-2" value="Нет">Нет</button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-5">
                            <button type="button" id="btn2" disabled="disabled" class="btn btn-info ansbtn mb-3 ml-4" value="Возможно">Возможно</button><br />
                        </div>
                    </div>
                </form>
            </div>
            <div class="col center-align">
                <div id="akinatorMessage" class="p-2 bg-primary text-white mb-4 text-right hidden"></div>
                <div id="sphinxMessage" class="p-2 bg-dark text-white mt-4 text-left hidden"></div>
            </div>
            <div class="col <#if role=1>hidden</#if>">
                <form id="question" class="form-group">
                    <label for="questionInput">Задайте вопрос Сфинксу и/или впишите отгаданного вами персонажа:</label>
                    <textarea class="form-control" rows="3" id="questionInput"></textarea>
                    <button id="questionButton" class="btn btn-primary" type="submit" disabled="disabled">Отправить</button>
                </form>
            </div>
            <div class="col center-align">
                <a id="akinatorProfile" target="_blank">
                    <img id="akinatorAvatar" width="200" height="270" />
                    <p id="akinatorName"></p>
                </a>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="myModal" data-backdrop="static" role="dialog">
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Игра закончена!</h4>
                    </div>
                    <div class="modal-body">
                        <table class="table table-bordered">
                            <tbody>
                            <tr>
                                <th scope="row">Персонаж</th>
                                <td id="result-character-name"></td>
                            </tr>
                            <tr>
                                <th scope="row">Сколько раз уже был отгадан</th>
                                <td id="result-character-repeat"></td>
                            </tr>
                            <tr>
                                <th scope="row">Угадан сейчас</th>
                                <td id="result-guessed"></td>
                            </tr>
                            <tr <#if !isPlayer>class="hidden"</#if>>
                                <th scope="row">Очки Сфинксу</th>
                                <td id="result-sphinx-score"></td>
                            </tr>
                            <tr <#if !isPlayer>class="hidden"</#if>>
                                <th scope="row">Очки Акинатору</th>
                                <td id="result-akinator-score"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="location.href='/main'">Ок</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="/js/game1.js"></script>
</@c.page>