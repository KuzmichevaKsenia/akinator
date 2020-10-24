<#import "parts/common.ftl" as c>

<@c.page>
    <#include "parts/navbar.ftl">
    <div class="back-user back-height pt-10 overx">
        <div class="row">
            <div class="col-sm-2 left-player-profile">
                <p>Логин: <b class="fz-25">${profile.getUsername()}</b></p>
                <p>Рейтинг: <b class="fz-25">${profile.getScore()}</b></p>
                <p>Сайт: <a href="https://www.${profile.getSnlink()}">${profile.getSnlink()}</a></p>
                <p><img src="/img/${profile.getAvatar()}" width="200" height="305" /></p>
            </div>
            <div class="col-sm-6">
                <#if isPlayer>
                    <#if user.isParent()>
                        <#if profile.getId() != user_id>
                            <form action="/player/invitePlayerToBand/${profile.getId()}" class="input-group mb-3" method="post">
                                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                                <div class="input-group-prepend">
                                    <button class="btn btn-outline-secondary" type="submit">Пригласить в </button>
                                </div>
                                <select name="parentBandname" class="col-sm-4 custom-select" id="inputGroupSelect03">
                                    <#assign selected=true>
                                    <#list parentBands as parentBand>
                                        <option <#if selected>selected="selected" </#if>value="${parentBand.getBandname()}">${parentBand.getBandname()}</option>
                                        <#assign selected=false>
                                    </#list>
                                </select>
                            </form>
                        </#if>
                    </#if>
                    <#if profile.getId() = user_id>
                        <form action="/player/addNewBand" method="post" class="input-group mb-3">
                            <div class="col-sm-6"></div>
                            <input type="text" name="bandname" class="form-control col-sm-3" placeholder="Название" aria-label="Recipient's username" aria-describedby="basic-addon2">
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <div class="input-group-append">
                                <button class="btn btn-outline-secondary" type="submit">Создать компанию</button>
                            </div>
                        </form>
                        <div class="row">
                            <div class="col">
                                <ul class="list-group">
                                    <li class="list-group-item bg-secondary">
                                        Предложения на вступление в компании
                                    </li>
                                    <#list profile.getBandsInvs() as invBand>
                                        <li class="list-group-item">
                                            <div class="row">
                                                <div class="col"><a href="/band/${invBand.getId()}">${invBand.getBandname()}</a></div>
                                                <div class="col"><a href="/player/joinTheBand/${invBand.getId()}" class="text-success">Принять</a></div>
                                            </div>
                                            <div class="row">
                                                <div class="col">Рейтинг: ${invBand.getScore()}</div>
                                                <div class="col"><a href="/player/removeInvToJoinTheBand/${invBand.getId()}" class="text-danger">Отклонить</a></div>
                                            </div>
                                        </li>
                                    </#list>
                                </ul>
                            </div>
                            <div class="col">
                                <ul class="list-group">
                                    <li class="list-group-item bg-secondary">
                                        Приглашения на сеанс
                                    </li>
                                    <#list profile.getSeanceInvs() as invBand>
                                        <li class="list-group-item">
                                            <div class="row">
                                                <div class="col"><a href="/band/${invBand.getId()}">${invBand.getBandname()}</a></div>
                                                <div class="col"><a href="/meeting/${invBand.getId()}" class="text-success">Принять</a></div>
                                            </div>
                                        </li>
                                    </#list>
                                </ul>
                            </div>
                        </div>
                    </#if>
                </#if>
            </div>
            <div class="col-sm-4">
                <ul class="list-group">
                    <li class="list-group-item bg-secondary">
                        Компании
                    </li>
                    <#list profile.getBands() as band>
                        <li class="list-group-item">
                            <a href="/band/${band.getId()}">${band.getBandname()}</a>
                        </li>
                    </#list>
                </ul>
            </div>
        </div>
    </div>
 </@c.page>
