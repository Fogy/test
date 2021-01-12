
$(function () {
    //个人签约
    $("#person_sign").click(function () {
        $("#sign_model").modal("show");
    });

    //点击身份证上传
    $("#idCardUrl").fileupload({
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
            $("#idCardUrl_show").attr("src", url);
        }
    });

    //点击护照上传
    $("#passPort").fileupload({
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
            $("#passPort_show").attr("src", url);
        }
    });

    //点击签合协议上传
    $("#protocol").fileupload({
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
            $("#protocol_show").attr("src", url);
        }
    });

    //点击申请
    $("#personal_sign_apply").click(function () {
        var signForm = new FormData($('#personal_sign_form')[0]);
        signForm.append("shoperType","personal");
        $.ajax({
            url:"/personalSign",
            type:"POST",
            data:signForm,
            processData:false,   //  告诉jquery不要处理发送的数据
            contentType:false,   // 告诉jquery不要设置content-Type请求头
            success:function(data){
                $("#sign_model").modal("hide");
                toastr.success('签约成功，敬请体验。');
            },
            error:function(data){
                toastr.error('删除失败请刷新页面后重试.');
            }
        });
    });

});