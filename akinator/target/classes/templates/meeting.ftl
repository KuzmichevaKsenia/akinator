<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
    <div class="back-meeting back-height pt-10 text-center overx overy">
        <input type="hidden" id="userId" value="${user_id}" />
        <input type="hidden" id="bandId" value="${band.getId()}" />
        <input type="hidden" id="login" value="${name}" />
        <h3 id="bandname">${band.getBandname()}</h3>
        <div class="connecting">
            Connecting...
        </div>
        <div class="row">
            <div class="col-sm-3"></div>
            <div class="col-sm-6">
                <table class="table table-hover table-dark">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Акинатор</th>
                        <th scope="col">Готовность</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#assign n=1>
                    <#list band.getMembers() as member>
                        <tr>
                            <th id="place-${member.getId()}" scope="row">${n}</th>
                            <td class="readyName${n}">${member.getUsername()}</td>
                            <td class="ready" id="ready-${member.getId()}">нет</td>
                        </tr>
                        <#assign n=n+1>
                    </#list>
                    </tbody>
                </table>
            </div>
            <div class="col-sm-3"></div>
        </div>
        <div class="row">
            <div class="col-sm-3"></div>
            <div class="col-sm-2 text-left">
                <button id="goOut-btn" type="button" class="btn btn-secondary btn-lg">&larr; Уйти</button>
            </div>
            <div class="col-sm-4 text-right">
                <button id="start-btn" type="button" class="btn btn-primary btn-lg">Начать сеанс текущим составом &rarr;</button>
            </div>
            <div class="col-sm-3"></div>
        </div>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="/js/meeting.js"></script>
</@c.page>