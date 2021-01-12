




$(function (){

    //点击预览大图；
    $("#changeHeaderImage").fileupload({
        replaceFileInput:false,
        singleFileUploads:false,//允许选择多个
        sequentialUploads:true,//允许多个File name同时上传
        autoUpload:true,
        processData : false, //必须false才会避开jQuery对 formdata 的默认处理
        multipart:true,
        showDone: false,                     //是否显示"Done"(完成)按钮
        showDelete: false,                  //是否显示"Delete"(删除)按钮
        dataType:'json',
        add:function(e,data){
            //获取图片路径并显示
            var url = getPicUrl(data.files[0]);
            $("#headerImage").attr("src", url);
            $("#customer-header-image").attr("src", url);
            data.submit();
        }
    });
});