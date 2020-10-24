<#macro login path isRegisterForm>
    <form action="${path}" method="post" <#if isRegisterForm>enctype="multipart/form-data"</#if>>
        <#if isRegisterForm>Регистрация<#else>Вход</#if>
        <div class="col-sm-8 form-group row mt-3">
            <input type="text" name="username" class="form-control" placeholder="Логин"/>
        </div>
        <div class="col-sm-8 form-group row">
            <input type="password" name="password" class="form-control" placeholder="Пароль"/>
        </div>
        <#if isRegisterForm>
            <div class="col-sm-8 form-group row">
                <input type="text" name="snlink" class="form-control" placeholder="Сайт"/>
            </div>
        <div class="form-group">
            <div class="custom-file">
                <input type="file" name="file" id="customFile">
                <label class="custom-file-label" for="customFile">Фото профиля</label>
            </div>
        </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary" type="submit"><#if isRegisterForm>Зарегистрироваться<#else>Войти</#if></button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post" class="ml-5">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button class="btn btn-primary" type="submit">На страницу авторизации</button>
    </form>
</#macro>

<#macro loginLikeGuest>
<form action="/loginAsGuest" method="post">
<input type="hidden" name="_csrf" value="${_csrf.token}" />
<button class="btn btn-primary" id="btn-login-as-guest" type="submit">Войти как гость</button>
</form>
</#macro>
