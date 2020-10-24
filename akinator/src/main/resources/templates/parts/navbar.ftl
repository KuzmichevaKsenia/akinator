<#include "security.ftl">
<#import "login.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/main">В игру</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <button class="btn btn-info collapsed ml-5" data-toggle="collapse" data-target="#top-players" aria-expanded="false">Топ игроков</button>
    <button class="btn btn-info collapsed ml-5" data-toggle="collapse" data-target="#top-bands" aria-expanded="false">Топ компаний</button>

    <div class="collapse navbar-collapse ml-400" id="navbarSupportedContent">
        <#if isPlayer>
            <a href="/player/${user_id}"><img class="nav-avatar ml-5 mr-4" src="/img/${avatar}"/></a>
        </#if>
        <div>
            <#if isPlayer><a href="/player/${user_id}"></#if>
                <u id="login">${name}</u>
            <#if isPlayer></a></#if>
        </div>
        <@l.logout />
    </div>
</nav>

<div id="top-players" class="collapse">
    <table class="table">
        <thead>
        <tr>
            <th>Игрок</th>
            <th>Рейтинг</th>
        </tr>
        </thead>
        <tbody>
        <#if topPlayers??>
            <#list topPlayers as player>
                <tr>
                    <td><a href="/player/${player.id}">${player.username}</a></td>
                    <td>${player.score}</td>
                </tr>
            </#list>
        <#else>
            No players
        </#if>
        </tbody>
    </table>
</div>

<div id="top-bands" class="collapse">
    <table class="table">
        <thead>
        <tr>
            <th>Компания</th>
            <th>Рейтинг</th>
        </tr>
        </thead>
        <tbody>
        <#if topBands??>
            <#list topBands as band>
                <tr>
                    <td><a href="/band/${band.id}">${band.bandname}</a></td>
                    <td>${band.score}</td>
                </tr>
            </#list>
        <#else>
            No bands
        </#if>
        </tbody>
    </table>
</div>