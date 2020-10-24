<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <div class="back-main back-height padding-top">
        <h1 class="login-title">ЯКинатор</h1>
        <div class="container center-align">
            <div class="row">${message?ifExists}</div>
            <div class="row">
                <div class="col mr-3">
                    <img src="../static/repoimages/spinx.png" width="250"/>
                </div>
                <div class="col bg-secondary mr-3">
                    <@l.login "/registration" true />
                </div>
                <div class="col bg-secondary mr-3">
                    <@l.login "/login" false/>
                    <hr/>
                    <@l.loginLikeGuest/>
                </div>
                <div class="col">
                    <img src="../static/repoimages/akinator.png" width="200">
                </div>
            </div>
        </div>
    </div>
</@c.page>