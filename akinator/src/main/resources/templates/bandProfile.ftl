<#import "parts/common.ftl" as c>

<@c.page>
    <#include "parts/navbar.ftl">
    <div class="back-band back-height pt-10 overx">
        <div class="row">
            <div class="col">
                <table class="table member-td">
                    <tbody>
                        <tr>
                            <td>
                                <#if user=profile.getParent() && user!=member1>
                                    <a href="/band/removeFromBand/${profile.getId()}/${member1.getId()}">
                                        <img src="../static/repoimages/x.png" width="20px" />
                                    </a>
                                </#if>
                                <a href="/player/${member1.getId()}">
                                    <img src="/img/${member1.getAvatar()}" width="70px" height="95" /><br/>
                                    ${member1.getUsername()}
                                </a>
                            </td>
                            <td>
                                <#if (profile.getNumber()>1)>
                                    <#if user=profile.getParent() && user!=member2>
                                        <a href="/band/removeFromBand/${profile.getId()}/${member2.getId()}">
                                            <img src="../static/repoimages/x.png" width="20px" />
                                        </a>
                                    </#if>
                                    <a href="/player/${member2.getId()}">
                                        <img src="/img/${member2.getAvatar()}" width="70px" height="95" /><br/>
                                        ${member2.getUsername()}
                                    </a>
                                </#if>
                            </td>
                            <td>
                                <#if (profile.getNumber()>2)>
                                    <#if user=profile.getParent() && user!=member3>
                                        <a href="/band/removeFromBand/${profile.getId()}/${member3.getId()}">
                                            <img src="../static/repoimages/x.png" width="20px" />
                                        </a>
                                    </#if>
                                    <a href="/player/${member3.getId()}">
                                        <img src="/img/${member3.getAvatar()}" width="70px" height="95" /><br/>
                                        ${member3.getUsername()}
                                    </a>
                                </#if>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <#if (profile.getNumber()>3)>
                                    <#if user=profile.getParent() && user!=member4>
                                        <a href="/band/removeFromBand/${profile.getId()}/${member4.getId()}">
                                            <img src="../static/repoimages/x.png" width="20px" />
                                        </a>
                                    </#if>
                                    <a href="/player/${member4.getId()}">
                                        <img src="/img/${member4.getAvatar()}" width="70px" height="95" /><br/>
                                        ${member4.getUsername()}
                                    </a>
                                </#if>
                            </td>
                            <td>
                                <#if (profile.getNumber()>4)>
                                    <#if user=profile.getParent() && user!=member5>
                                        <a href="/band/removeFromBand/${profile.getId()}/${member5.getId()}">
                                            <img src="../static/repoimages/x.png" width="20px" />
                                        </a>
                                    </#if>
                                    <a href="/player/${member5.getId()}">
                                        <img src="/img/${member5.getAvatar()}" width="70px" height="95" /><br/>
                                        ${member5.getUsername()}
                                    </a>
                                </#if>
                            </td>
                            <td>
                                <#if (profile.getNumber()>5)>
                                    <#if user=profile.getParent() && user!=member6>
                                        <a href="/band/removeFromBand/${profile.getId()}/${member6.getId()}">
                                            <img src="../static/repoimages/x.png" width="20px" />
                                        </a>
                                    </#if>
                                    <a href="/player/${member6.getId()}">
                                        <img src="/img/${member6.getAvatar()}" width="70px" height="95" /><br/>
                                        ${member6.getUsername()}
                                    </a>
                                </#if>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <#if (profile.getNumber()>6)>
                                    <#if user=profile.getParent() && user!=member7>
                                        <a href="/band/removeFromBand/${profile.getId()}/${member7.getId()}">
                                            <img src="../static/repoimages/x.png" width="20px" />
                                        </a>
                                    </#if>
                                    <a href="/player/${member7.getId()}">
                                        <img src="/img/${member7.getAvatar()}" width="70px" height="95" /><br/>
                                        ${member7.getUsername()}
                                    </a>
                                </#if>
                            </td>
                            <td>
                                <#if (profile.getNumber()>7)>
                                    <#if user=profile.getParent() && user!=member8>
                                        <a href="/band/removeFromBand/${profile.getId()}/${member8.getId()}">
                                            <img src="../static/repoimages/x.png" width="20px" />
                                        </a>
                                    </#if>
                                    <a href="/player/${member8.getId()}">
                                        <img src="/img/${member8.getAvatar()}" width="70px" height="95" /><br/>
                                        ${member8.getUsername()}
                                    </a>
                                </#if>
                            </td>
                            <td>
                                <#if (profile.getNumber()>8)>
                                    <#if user=profile.getParent() && user!=member9>
                                        <a href="/band/removeFromBand/${profile.getId()}/${member9.getId()}">
                                            <img src="../static/repoimages/x.png" width="20px" />
                                        </a>
                                    </#if>
                                    <a href="/player/${member9.getId()}">
                                        <img src="/img/${member9.getAvatar()}" width="70px" height="95" /><br/>
                                        ${member9.getUsername()}
                                    </a>
                                </#if>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="col-sm-2">
                <div class="mb-2 center-align"><b class="fz-25">${band.bandname}</b></div>
                <div class="mb-2 center-align">Рейтинг: <b class="fz-25">${band.score}</b></div></div>
            <div class="col">
                <#if profile.getMembers()?seq_contains(user)>
                    <#if user!=profile.getParent()>
                        <form action="/band/removeFromBand/${profile.getId()}/${user_id}" method="get" class="mb-2">
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <button class="btn btn-primary" type="submit">Покинуть компанию</button>
                        </form>
                    </#if>
                <#else>
                    <form action="/band/sendReqToTheBand/${user_id}/${profile.getId()}" method="post" class="mb-2">
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        <button class="btn btn-primary" type="submit">Присоединиться к компании</button>
                    </form>
                </#if>
                <#--<#if user=profile.getParent()>
                    <form action="/band/removeBand/${profile.getId()}" method="post" class="mb-2">
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        <button class="btn btn-primary" type="submit">Удалить компанию</button>
                    </form>
                </#if>-->
            </div>
            <#if user=profile.getParent()>
                <div class="col">
                    <ul class="list-group">
                        <li class="list-group-item disabled">
                            Заявки на присоединение к компании
                        </li>
                        <#list profile.getUsersReqs() as reqUser>
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col"><a href="/player/${reqUser.getId()}">${reqUser.getUsername()}</a></div>
                                    <div class="col"><a href="/band/takeToTheBand/${reqUser.getId()}/${profile.getId()}" class="text-success">Принять</a></div>
                                </div>
                                <div class="row">
                                    <div class="col">Рейтинг: ${reqUser.getScore()}</div>
                                    <div class="col"><a href="/band/removeReqToTheBand/${reqUser.getId()}/${profile.getId()}" class="text-danger">Отклонить</a></div>
                                </div>
                            </li>
                        </#list>
                    </ul>
                </div>
            </#if>
        </div>
    </div>
</@c.page>
