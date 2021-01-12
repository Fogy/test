//获取图片地址
function getUrl(file) {
    var url = null;
    if (window.createObjectURL != undefined) {
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) {
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) {
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}
var up_add =function(e,data){
    $("#sub").unbind("click");
    $("#sub").click(function(){
        /*data.formData={
            typesId:"typesId",
            worksName:"worksName",
            picPreview:$("#picPreview")[0].files[0],
            works:$("#works")[0].files[0],
        };
        /!*var myForm = document.getElementById('worksUp');
        data.formData=new FormData(myForm);*!/
        data.submit();*/
        var formData = new FormData(document.getElementById("worksUp"));
        $.ajax({
            url:"/fileUp",
            type:"POST",
            data:new FormData($('#worksUp')[0]),
            processData:false,   //  告诉jquery不要处理发送的数据
            contentType:false,   // 告诉jquery不要设置content-Type请求头
            success:function(data){
                if(data == "ok"){
                    window.parent.splitpageform.submit();
                }
            },
            error:function(data){

            }
        });
    })
    //获取图片路径并显示
    debugger;
    var pic_table=$("#pic_table");
    var works_table=$("#works_table");
    var files=data.files;
    var rows="";
    if (data.paramName.indexOf("picPreview")>-1){
        pic_table.empty();
        for(var i=0;i<files.length;i++){
            rows+="<tr>";
            rows+="<th> "+(i+1)+"</th>";
            rows+="<td><img style=\"max-height: 20px\" src=\""+getUrl(files[i])+"\"></td>";
            rows+="<th> "+files[i].name+"</th>";
            rows+="<th> "+files[i].size+"</th>";
            rows+="</tr>";
        }
        pic_table.append(rows);
    }else if(data.paramName.indexOf("works")>-1){
        works_table.empty();
        for(var i=0;i<files.length;i++){
            rows+="<tr>";
            rows+="<th> "+(i+1)+"</th>";
            rows+="<td><i class=\"fa fa-file-archive-o\"></i></td>";
            rows+="<th> "+files[i].name+"</th>";
            rows+="<th> "+files[i].size+"</th>";
            rows+="</tr>";
        }
        works_table.append(rows);
    }
    /*var url = getUrl(data.files[0]);
    $("#name").append(data.files[0].name)
    $("#image").attr("src", url);*/
};
var up_done=function(e,data){
    //console.info(data.result);
}
var up_progressall=function(e, data){
    var press = parseInt(data.loaded / data.total * 100, 10);
    $("#asda").html("全量包正在上传:" + press + '%');
    $("#asda").append("<span id='asd' style='background-color:#117bed;display:block;height:20px;'></span>");
    $('#asd').css('width', press + '%');
    if (press == 100) {
        $("#asda").html("全量包上传完成!");
        $('#asd').remove();
    }
}
var up_always=function(e, data){

}
$(function () {
    //worksUp
    $("#worksUp").fileupload({
        formData:{
            typesId:"typesId",
            worksName:"worksName",
            picPreview:$("#picPreview")[0].files[0],
            works:$("#works")[0].files[0],
        },
        replaceFileInput:false,
        singleFileUploads:false,//允许选择多个
        sequentialUploads:true,//允许多个File name同时上传
        //dataType:'json',
        autoUpload:false,
        processData : false, //必须false才会避开jQuery对 formdata 的默认处理
        multipart:true,
        showDone: false,                     //是否显示"Done"(完成)按钮
        showDelete: true,                  //是否显示"Delete"(删除)按钮
        add:function(e,data){
            up_add(e,data)
        },
        /*change:function(e,data){
            this.formData=$("#worksUp").serializeArray();
        },*/
        /*submit:function(e,data){
            data.formData={
                typesId:"typesId",
                worksName:"worksName",
                picPreview:$("#picPreview").files,
                works:$("#works").files
            };
        },*/
        done:function (e,data) {
            up_done(e,data)
        },
        progressall: function (e,data) {
            up_progressall(e,data);
        },
        always:function(e,data){
            up_progressall(e,data);
        }
    });



});