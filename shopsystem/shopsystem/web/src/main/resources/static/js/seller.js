
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
    $("#vido_model").modal('show');
    resert();
    $.ajax({
        url:"/getWorksById",
        type:"POST",
        data:{"worksIds":worksId},
        success:function(works){
            //console.info(works);
            $("#worksId").val(works.worksId);
            $("#worksTitle").val(works.title);
            $("#keyWord").val(works.keyWord);
            $("#worksType").val(works.type);
            $("#price").val(works.price);
            $("#worksDuration").val(works.duration);
            $("#previewUrl_show").attr('src',works.previewUrl);
            $("#synthesisUrl_show").attr('src',works.synthesisUrl);
            $("#saveWorks").bind("click",function () {
                saveWorks();
            });

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
            getWorksByAccountId($("#accountId").val());
            getReferTemps();
            getReferPlane();
        },
        error:function (data) {
            toastr.error('删除失败请刷新页面后重试.');
            getWorksByAccountId($("#accountId").val());
            getReferTemps();
            getReferPlane();

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
            "accountId":$("#accountId").val(),
            "status":"refer",
            "type":"1"
        }),
        success:function(data){
            $("#listWorks").empty();
            var works='';
            for(var index in data){
                works+="<div class='col-md-3'>";
                works+="<div class='mt-card-item'>";
                works+="<div class='mt-card-avatar mt-overlay-1 mt-scroll-down'>";
                works+="<img style='height:200px;object-fit: cover; ' src='"+data[index].previewUrl+"'/>"
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


//查询查询
var getReferTemps=function () {
    $.ajax({
        url:"/getWorksByAccountId",
        type:"POST",
        contentType:'application/json;charset=utf-8',
        data:JSON.stringify({
            "accountId":$("#accountId").val(),
            "status":"refer",
            "type":"2"
        }),
        success:function(data){
            $("#listTemps").empty();
            var works='';
            for(var index in data){
                works+="<div class='col-md-3'>";
                works+="<div class='mt-card-item'>";
                works+="<div class='mt-card-avatar mt-overlay-1 mt-scroll-down'>";
                works+="<img style='height:200px;object-fit: cover; ' src='"+data[index].previewUrl+"'/>"
                works+="</div>";

                works+="<div class='mt-card-content'>";
                works+="<div class='caption'>";
                works+="<a href='javascript:;' class='btn btn-sm blue' onclick='editTemplatesId("+data[index].worksId+")'> 编辑 </a>";
                works+="<a href='javascript:;' onclick='delWorksById("+data[index].worksId+")' class='btn btn-sm red'> 删除 </a>"
                works+="</div>";
                works+="</div>";
                works+="</div>";
                works+="</div>";
            }
            $("#listTemps").append(works);
        },
        error:function(data){

        }
    });

}




//重置输入框

var resert = function(){
    $("#colorSytem").selectpicker('deselectAll');//重置下拉框
    $("#worksUpForm")[0].reset();
    $("#price").val('');
    $("#price").hide();

}
$(function () {
    //点击取消按钮
    $("#canelWorks").click(function () {
        resert();
    });
    //点击售价按钮
    $("#is-free").val(0);
    $("#price").hide();
    $("#is-free").change(function(){
        var chart = $(this).val();
        if(chart ==0){
            $("#price").val('');
            $("#price").hide();
        }else{
            $("#price").show();
            $("#price").val('');
        }
    });


    //自定义分辨率
    $("#resolution").change(function () {
        var vau = $(this).val();
        if(vau=='自定义'){
            $("#resolution-div2").show();
        }else{
            $("#resolution-div2").hide();
        }

    });



    //保存更新内容
    $("#saveUpWorks").click(function () {

        var resolution=$("#resolution").val();
        var resolution_w=$("#resolution_width").val();
        var resolution_h=$("#resolution_height").val();
        if(resolution=='自定义'){
            if(resolution_w && resolution_h){
                resolution = resolution_w+"×"+resolution_h;
            }else{
                resolution = '';
            }
        }

        var cols = "";
        var chans = "";
        $("#colorSytem option:selected").each(function () {
            cols +=$(this).val()+",";
        })
        if(cols.length>0){
            cols =cols.substring(0,cols.length-1);
        }

        $("input[name='channel']:checked").each(function () {
            chans +=$(this).val()+",";
        })
        if(chans.length>0){
            chans =chans.substring(0,chans.length-1);
        }

        var worksUpData = new FormData($('#worksUpForm')[0]);
        worksUpData.delete("colorSytem");
        worksUpData.delete("channel");
        worksUpData.delete("resolution");
        worksUpData.append("colorSytem",cols);
        worksUpData.append("channel",chans);
        worksUpData.append("resolution",resolution);
        $.ajax({
            url:"/saveUpWorks",
            type:"POST",
            data:worksUpData,
            processData:false,   //  告诉jquery不要处理发送的数据
            contentType:false,   // 告诉jquery不要设置content-Type请求头
            success:function(data){
                $("#vido_model").modal('hide')
                toastr.success('提交审核成功.');
                getWorksByAccountId($("#accountId").val());
                PersonMangePageStart();
            },
            error:function(data){
                toastr.error('提交审核失败,请联系管理人员处理异常。');
            }
        });
        resert();
    });

    //点击预览大图；
    $("#template_previewUrl").fileupload({
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
            var url = getPicUrl(data.files[0]);
            $("#template_previewUrl_show").attr("src", url);
        }
    });

    //点击合成大图；
    $("#template_synthesisUrl").fileupload({
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
            var url = getPicUrl(data.files[0]);
            $("#template_synthesisUrl_show").attr("src", url);
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
            debugger
            //获取图片路径并显示
            var url = getPicUrl(data.files[0]);
            $("#synthesisUrl_show").attr("src", url);
        }
    });
    //上传视频文件
    $("#video").fileupload({
        url:'/uploadVideo',
        Type : 'POST',//请求方式 ，可以选择POST，PUT或者PATCH,默认POST
        //dataType : 'json',//服务器返回的数据类型
        autoUpload : false,
        singleFileUploads: false,
        fileElementId: 'video',
        //acceptFileTypes : /(gif|jpe?g|png)$/i,//验证图片格式
        maxNumberOfFiles : 5,//最大上传文件数目
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
            if(data.result.code==500){
                toastr.error(data.result.errorMsg);
            };
            toastr.success("文件上传成功.");
            $('#progress').hide();
            getWorksByAccountId($("#accountId").val());
        },
        fail:function (e, data) {
            toastr.error("服务器异常,请联系客服.");
            $('#progress').hide();
        },
        add:function(e,data){
            var flag=true;
            //获取图片路径并显示
            //var url = getUrl(data.files[0]);
            var files = data.originalFiles;
            if(files.length>5){
                flag = false;
                toastr.warning('提交审核失败,请检查文件个数不能超过5个。');
                return;
            }
            for(var i=0;i<files.length;i++){
                if(files[i].size>1000*1000*1000){
                    toastr.warning('提交审核失败,请检查文件大小,单个文件不能超过1G。');
                    flag = false;
                    return;
                }
            }
            if(flag){
                data.submit();
            }

            //$("#synthesisUrl_show").attr("src", url);
        }
    });

    //获取未审核的作品
    getWorksByAccountId();
    getReferTemps();
    /*getReferPlane();*/

    //多选初始化；
    $('.bs-select').selectpicker({
        iconBase: 'fa',
        tickIcon: 'fa-check'
    });

    //上传模板

    $("#temp").click(function () {
        $("#temp_model").modal('show');
    });

    /**
     *
     */
    $("#temp-sub").click(function () {
        var data = new FormData($('#tempForm')[0]);
        $.ajax({
            url: "/uploadTemp",
            type: "POST",
            data: data,
            processData: false,   //  告诉jquery不要处理发送的数据
            contentType: false,   // 告诉jquery不要设置content-Type请求头
            xhr:xhrOnProgress(function(e){
                $("#temp_model").modal('hide');
                var percent=e.loaded / e.total;//计算百分比
                $('#progress').show();
                $('#progress').addClass("active");
                var progress = parseInt(percent * 100, 10);
                $('#progress .progress-bar').css(
                    'width',progress+'%'
                );
                $('#progress .progress-bar').html(
                    progress + '%'
                );

            }),
            success: function (data) {
                toastr.success("文件上传成功.");
                $('#progress').hide();
                getReferTemps();
            },
            error: function (data) {
                toastr.error("服务器异常,请联系客服.");
                $('#progress').hide();
            }
        });
    });



});