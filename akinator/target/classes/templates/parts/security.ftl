<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    avatar = user.getAvatar()!"error"
    user_id = user.getId()
    isPlayer = user.isPlayer()
    isParent = user.isParent()
    isMember = user.isMember()
    >
<#else>
    <#assign
    name = "unknown"
    isPlayer = false
    isMember = false
    isParent = false
    >
</#if>