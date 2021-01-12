
//删除作品
var delWorksById=function(worksId){
    confirm(worksId);
}
//保存作品
var saveWorks =function () {
    console.info("保存成功");
};
//编辑作品
var editWorksById=function(worksId){
    $("#vido_model").modal('show')
    $.ajax({
        url:"/getWorksById",
        type:"POST",
        data:{"worksIds":worksId},
        success:function(works){
            console.info(works);
            $("#worksId").val(works.worksId);
            $("#worksTitle").val(works.worksTitle);
            $("#worksDesc").val(works.worksDesc);
            $("#price").val(works.price);
            $("#worksName").val(works.worksUrls.worksName);
            $("#previewUrl_show").attr('src',works.worksUrls.previewUrl);
            //$("#previewUrl").val(works.worksUrls.previewUrl);
            $("#synthesisUrl_show").attr('src',works.worksUrls.synthesisUrl);
            //$("#synthesisUrl").val(works.worksUrls.synthesisUrl);
            console.info($("#synthesisUrl_show").attr('src'));
            $("#saveWorks").bind("click",function () {
                saveWorks();
            })
        },
        error:function (data) {
            toastr.error('获取数据失败请刷新页面后重试.');
            getWorksByAccountId(1);
        }
    })

}

//删除作品ajax
var del=function (worksIds) {
    $.ajax({
        url:"/delWorksById",
        type:"POST",
        data:{"worksIds":worksIds},
        success:function(data){
            toastr.success('删除成功.');
            getWorksByAccountId(1);
        },
        error:function (data) {
            toastr.error('删除失败请刷新页面后重试.');
            getWorksByAccountId(1);
        }
    })

}

//弹框删除确认
var confirm=function (worksId) {
    bootbox.confirm({
        title:"警告!",
        message: "作品删除后将不可恢复,确认是否删除",
        locale: 'zh_CN',
        buttons: {
            cancel: {
                label: '取消',
                className: 'btn'
            },
            confirm: {
                label: '确定',
                className: 'btn-danger'
            }
        },
        callback: function (res) {
            if(!res){
                return
            }
            del(worksId);
            //console.log('This was logged in the callback: ' + result);
        }
    });
}

//查询作品
var getWorksByAccountId=function () {
    $.ajax({
        url:"/getWorksByAccountId",
        type:"POST",
        contentType:'application/json;charset=utf-8',
        data:JSON.stringify({
            "accountId":1,
            "worksStatus":"refer"
        }),
        success:function(data){
            $("#listWorks").empty();
            var works='';
            for(let index in data){
                works+="<div class='col-md-2'>";
                works+="<div class='mt-card-item'>";
                works+="<div class='mt-card-avatar mt-overlay-1 mt-scroll-down'>";
                works+="<img src='"+data[index].worksUrls.previewUrl+"'/>"
                works+="</div>";

                works+="<div class='mt-card-content'>";
                works+="<div class='caption'>";
                works+="<a href='javascript:;' class='btn btn-sm blue' onclick='editWorksById("+data[index].worksId+")'> 编辑 </a>";
                works+="<a href='javascript:;' onclick='delWorksById("+data[index].worksId+")' class='btn btn-sm red'> 删除 </a>"
                works+="</div>";
                works+="</div>";
                works+="</div>";
                works+="</div>";
            }
            $("#listWorks").append(works);
        },
        error:function(data){

        }
    });

}
$(function () {
    //保存更新内容
    $("#saveUpWorks").click(function () {
        var worksUpData = new FormData($('#worksUpForm')[0])
        $.ajax({
            url:"/saveUpWorks",
            type:"POST",
            data:worksUpData,
            processData:false,   //  告诉jquery不要处理发送的数据
            contentType:false,   // 告诉jquery不要设置content-Type请求头
            success:function(data){
                $("#vido_model").modal('hide')
                toastr.success('提交审核成功.');
                getWorksByAccountId(1);
            },
            error:function(data){
                toastr.error('提交审核失败,请联系管理人员处理异常。');
            }
        });
    });

    //点击预览大图；
    $("#previewUrl").fileupload({
        replaceFileInput:false,
        singleFileUploads:false,//允许选择多个
        sequentialUploads:true,//允许多个File name同时上传
        autoUpload:false,
        processData : false, //必须false才会避开jQuery对 formdata 的默认处理
        multipart:true,
        showDone: false,                     //是否显示"Done"(完成)按钮
        showDelete: true,                  //是否显示"Delete"(删除)按钮
        add:function(e,data){
            //获取图片路径并显示
            var url = getUrl(data.files[0]);
            $("#previewUrl_show").attr("src", url);
        }
    });
    //点击合成大图；
    $("#synthesisUrl").fileupload({
        replaceFileInput:false,
        singleFileUploads:false,//允许选择多个
        sequentialUploads:true,//允许多个File name同时上传
        autoUpload:false,
        processData : false, //必须false才会避开jQuery对 formdata 的默认处理
        multipart:true,
        showDone: false,                     //是否显示"Done"(完成)按钮
        showDelete: true,                  //是否显示"Delete"(删除)按钮
        add:function(e,data){
            //获取图片路径并显示
            var url = getUrl(data.files[0]);
            $("#synthesisUrl_show").attr("src", url);
        }
    });

    //上传视频文件
    $("#video").fileupload({
        url:'/uploadVideo',
        Type : 'POST',//请求方式 ，可以选择POST，PUT或者PATCH,默认POST
        //dataType : 'json',//服务器返回的数据类型
        autoUpload : true,
        //acceptFileTypes : /(gif|jpe?g|png)$/i,//验证图片格式
        maxNumberOfFiles : 1,//最大上传文件数目
        // maxFileSize : 1000000, // 文件上限1MB
        // minFileSize : 100,//文件下限  100b
        messages : {//文件错误信息
            acceptFileTypes : '文件类型不匹配',
            maxFileSize : '文件过大',
            minFileSize : '文件过小'
        },
        progressall:function(e, data){
            $('#progress').show();
            $('#progress').addClass("active");
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .progress-bar').css(
                'width',progress+'%'
            );
            $('#progress .progress-bar').html(
                progress + '%'
            );
        },
        done: function (e, data) {
            $('#progress').hide();
            getWorksByAccountId(1);
        }
    });
    //获取未审核的作品
    getWorksByAccountId();


});