//播放视频
function videoPlayback(object){
    var video =object;
    console.info(video);
    //获取视频标签
    /*var video = document.getElementById('video');*/
    //给视频标签添加缓存播放---video标签属性。
    video.setAttribute("autoplay","autoplay")
    //给视频标签添加循环播放---video标签属性。
    video.setAttribute("loop","loop")
    //播放视频
    video.play();
}

//停止播放
function videoStopped(object){
    var video =object;
    //获取视频标签
    //var video = document.getElementById('video');
    //停止播放
    video.pause();
}

//隐藏图片
var videoShow = function (object) {
    $(object).children(":first").hide();
    $(object).children(":last").show().css('display','block');

}
//展示图片
var videoHide=function (object) {
    $(object).children(":first").show();
    $(object).children(":last").hide();
}
//获取视频
var getVideo =function(){
    $.ajax({
        url:"/getWorksByCondition",
        type:"POST",
        data:{"worksStatus":"pass",
                "start":0,
                "length":4,
                "type":1,
                "status":"pass"
        },
        success:function(data){
            var videoList =$("#videoList");
            videoList.empty();
            var content ="";
            if(data.list){
                for(let i=0;i<data.list.length;i++){
                    let works = data.list[i];
                    var item=`
                        <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                            <div class="mt-card-item">
                                <a href="/getWorksDetail?worksIds=${works.worksId}">
                                    <div class="mt-card-avatar mt-overlay-1" onmouseover="videoShow(this)" onmouseout="videoHide(this)">
                                        <img style="height:147px;object-fit: cover; " src="${works.previewUrl}"/>
                                        <video style="height:147px;object-fit: cover; " class="video" muted  onmouseover="videoPlayback(this)" onmouseout="videoStopped(this)">
                                            <source  src="${works.compressUrl}">
                                        </video>
                                    </div>
                                </a>
            
                                <div class="mt-card-content">
                                    <h3 class="mt-card-name line-height-3">${works.title}<a href="${works.compressUrl}" class="fa fa-download"></a></h3>
                                </div>
                            </div>
                        </div>`;
                    content+=item;
                }
            }
            videoList.append(content);

        },
        error:function (data) {
            toastr.info("服务器异常,请联系客服.");
        }
    })
}


//获取模板
var getTemp =function(){
    $.ajax({
        url:"/getWorksByCondition",
        type:"POST",
        data:{"worksStatus":"pass",
            "start":0,
            "length":4,
            "type":2,
            "status":"pass"
        },
        success:function(data){
            var videoList =$("#tempList");
            videoList.empty();
            var content ="";
            if(data.list){
                for(let i=0;i<data.list.length;i++){
                    let works = data.list[i];
                    var item=`
                        <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                            <div class="mt-card-item">
                                <a href="/getWorksDetail?worksIds=${works.worksId}">
                                    <div class="mt-card-avatar mt-overlay-1" onmouseover="videoShow(this)" onmouseout="videoHide(this)">
                                        <img style="height:147px;object-fit: cover; " src="${works.previewUrl}"/>
                                        <video style="height:147px;object-fit: cover; " class="video" muted  onmouseover="videoPlayback(this)" onmouseout="videoStopped(this)">
                                            <source  src="${works.compressUrl}">
                                        </video>
                                    </div>
                                </a>
            
                                <div class="mt-card-content">
                                    <h3 class="mt-card-name line-height-3">${works.title}<a href="${works.compressUrl}" class="fa fa-download"></a></h3>
                                </div>
                            </div>
                        </div>`;
                    content+=item;
                }
            }
            videoList.append(content);

        },
        error:function (data) {
            toastr.info("服务器异常,请联系客服.");
        }
    })
}

//平面素材
var getPlane =function(){
    $.ajax({
        url:"/getWorksByCondition",
        type:"POST",
        data:{"worksStatus":"pass",
            "start":0,
            "length":4,
            "type":3,
            "status":"pass"
        },
        success:function(data){
            var videoList =$("#planeList");
            videoList.empty();
            var content ="";
            if(data.list){
                for(let i=0;i<data.list.length;i++){
                    let works = data.list[i];
                    var item=`
                        <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                            <div class="mt-card-item">
                                <a href="/getWorksDetail?worksIds=${works.worksId}">
                                    <div class="mt-card-avatar mt-overlay-1">
                                        <img style="height:147px;object-fit: cover; " src="${works.previewUrl}"/>
                                        <!-- <video style="height:147px;object-fit: cover; " class="video" muted  onmouseover="videoPlayback(this)" onmouseout="videoStopped(this)">
                                            <source  src="${works.compressUrl}">
                                        </video> -->
                                    </div>
                                </a>
            
                                <div class="mt-card-content">
                                    <h3 class="mt-card-name line-height-3">${works.title}<a href="${works.compressUrl}" class="fa fa-download"></a></h3>
                                </div>
                            </div>
                        </div>`;
                    content+=item;
                }
            }
            videoList.append(content);

        },
        error:function (data) {
            toastr.info("服务器异常,请联系客服.");
        }
    })
}



//改变轮播图高度
var changeItm=function(){
    let cliHeight=$(window).height();
    let divHeight = cliHeight-2;
    $(".item").each(function () {
        $(this).css("height",divHeight/3+"px");
    })
}
//ajax请求
$(function () {
    //轮播图片设置
    changeItm();
    $(window).resize(function () {
        changeItm();
        
    });

    //获取视频
    getVideo();

    //获取模板
    getTemp();

    //获取平面
    getPlane();


});
