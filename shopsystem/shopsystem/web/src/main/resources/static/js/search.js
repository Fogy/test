
//分页函数
function pageStart() {//分页函数
    var key = getUrlParam("keyWord");
    $.ajax({ //去后台查询第一页数据
        type : "get",
        url : "/getWorksSearch",
        data : {

            "page" : '1',
            "pageSize" : '10',
            "type":$("#works-type").val(),
            "version":$("#works-version").val(),
            "resolution":$("#works-resolution").val(),
            "channel":$("#works-channel").val(),
            "keyWord":key,

        }, //参数：当前页为1
        success : function(data) {
            appendHtml(data.list)//处理第一页的数据
            var options = {//根据后台返回的分页相关信息，设置插件参数
                bootstrapMajorVersion : 3, //如果是bootstrap3版本需要加此标识，并且设置包含分页内容的DOM元素为UL,如果是bootstrap2版本，则DOM包含元素是DIV
                currentPage : data.page, //当前页数
                totalPages : data.recordsTotal, //总页数
                numberOfPages : data.pageSize,//每页记录数
                shouldShowPage:function(type, page, current){
                    if(type == "page" && Math.abs(page-current)>3){
                        return false
                    }else{
                        return true
                    }

                },
                itemTexts : function(type, page, current) {//设置分页按钮显示字体样式
                    switch (type) {
                        case "first":
                            return "首页";
                        case "prev":
                            return "上一页";
                        case "next":
                            return "下一页";
                        case "last":
                            return "末页";
                        case "page":
                            return page;
                    }
                },
                onPageClicked : function(event, originalEvent, type, page) {//分页按钮点击事件
                    var key = getUrlParam("key");
                    $.ajax({//根据page去后台加载数据
                        url : "/getWorksSearch",
                        type : "get",
                        dataType : "json",
                        data : {
                            "key":key,
                            "page" : page,
                            "pageSize" : '10',
                            "type":$("#works-type").val(),
                            "version":$("#works-version").val(),
                            "resolution":$("#works-resolution").val(),
                            "channel":$("#works-channel").val(),
                            "keyWord":key,

                        },
                        success : function(data) {
                            appendHtml(data.list);//处理数据
                        }
                    });
                }
            };
            $('#mypage').bootstrapPaginator(options);//设置分页
        }
    });
}

function appendHtml(list) {//此函数用于处理后台返回的数据，根据自己需求来实现页面拼接
    var tableShow = '';
    for (var i = 0; i < list.length; i++) {
        //tableShow += "<div class='col-md-4'><img src="+list[i].worksUrls.previewUrl+"/></div>";
        tableShow +=`
                    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                        <div class="mt-card-item">
                            <a href="/getWorksDetail?worksIds=${list[i].worksId}">
                                <div class="mt-card-avatar mt-overlay-1"
                                     onmouseover="videoShow(this)" onmouseout="videoHide(this)">
                                    <img style="height:147px;object-fit: cover; " src="${list[i].previewUrl}"/>
                                    <video style="height:147px;object-fit: cover; " class="video" muted onmouseover="videoPlayback(this)"
                                           onmouseout="videoStopped(this)">
                                        <source src="../../static/video/video.mp4">
                                    </video>
                                </div>
                            </a>
                            <div class="mt-card-content">
                                <h3 data-container="body" data-toggle="popover"
                                    data-placement="bottom" data-content="
                                    <ul class = 'list-unstyled '>
                                        <li><b>视频作者:</b>打消怪兽</li>
                                        <li><b>视频类型:</b>打消怪兽</li>
                                        <li><b>分辨率:</b>打消怪兽</li>
                                        <li><b>时长:</b>打消怪兽</li>
                                        <li><b>售价:</b>打消怪兽</li>
                                        <li><b>下载:</b>打消怪兽</li>
                                        <li><b>时间:</b>打消怪兽</li>
                                    </ul>"
                                    class="mt-card-name line-height-3">${list[i].title}
                                    <a class="fa fa-user"></a>
                                </h3>
                            </div>
                        </div>
                    </div>` }
    $('#htmlDiv').empty();
    $('#htmlDiv').append(tableShow);

}

$(function(){

    pageStart();
    $("#works-type").change(function () {
        pageStart();
    });
    $("#works-version").change(function () {
        pageStart();
    });
    $("#works-resolution").change(function () {
        pageStart();
    });
    $("#works-channel").change(function () {
        pageStart();
    });
    $('[data-toggle="popover"]').popover({
        animation:true,
        trigger:'hover',
        html:true
    })





});



   /* $("#exam-id").popover({
        html :true,
        title:'<h2>年后</h2>'
    });*/
