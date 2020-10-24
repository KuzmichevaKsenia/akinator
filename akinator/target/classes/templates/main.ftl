<#import "parts/common.ftl" as c>

<@c.page>
    <#include "parts/navbar.ftl">
    <div class="back-games back-height pt-10 overx">
        <div class="row">
            <div class="col">
                <h1 class="center-align">ОДИН НА ОДИН</h1>
                <div class="row back-lightning">
                    <a id="playAsSphinx1" class="col sphinx-ico ml-3 mr-3"></a>
                    <a id="playAsAkinator" class="col akinator-ico ml-3 mr-3 mb-4"></a>
                </div>
            </div>
            <div class="col">
                <h1 class="center-align">СЕАНС</h1>
                <div class="row back-lightning">
                    <a id="playAsSphinx2" class="col sphinx-ico ml-3 mr-3"></a>
                    <#if user.isMember()>
                        <a href="#" data-toggle="modal" data-target="#exampleModal" class="col band-ico ml-3 mr-3 mb-4"></a>
                    <#else>
                        <a class="col band-ico ml-3 mr-3 mb-4" onclick="alert('Доступно только для членов компаний!')"></a>
                    </#if>
                </div>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="exampleModal" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        Собери Акинаторов на сеанс
                    </div>
                    <div class="modal-body">
                        <form action="/main/invBandToSeance" class="input-group mb-3" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <div class="input-group-prepend">
                                <button class="btn btn-outline-secondary" type="submit">Призвать</button>
                            </div>
                            <select name="bandname" class="col-sm-4 custom-select" id="inputGroupSelect03">
                                <#assign selected=true>
                                <#list user.getBands() as band>
                                    <option <#if selected>selected="selected" </#if>value="${band.getBandname()}">${band.getBandname()}</option>
                                    <#assign selected=false>
                                </#list>
                            </select>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Отмена</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="/js/main.js"></script>
</@c.page>