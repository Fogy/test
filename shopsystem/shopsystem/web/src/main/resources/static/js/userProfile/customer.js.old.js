// save user
function saveUser() {
    // all the input in form
    var data ={
        "name":$("input[name='name']").val(),
        "sex":$('input:radio[name="sex"]:checked').val(),
        "email":$("input[name='email']").val(),
        "phone":$("input[name='phone']").val()
    };
    $.ajax({
        type:"post",
        url:"/editCustomer",
        data:JSON.stringify(data),
        contentType:"application/json",
        dataType:"String",
        success:function(data){
            window.location.reload();
        },
        error : function(e){
            window.location.reload();
        },
        complete:function (data) {
            window.location.reload();
        }
    })
};
// 修改头像

function checkForm() {
    var _input = $('#head_portrait_file');
    var filepath = _input.val();
    if(filepath == ""){
        alert("请选择文件");
        return false;
    }
    var extStart = filepath.lastIndexOf(".");
    var ext = filepath.substring(extStart, filepath.length).toUpperCase();
    if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
        alert("图片限于bmp,png,gif,jpeg,jpg格式");
        return false;
    }
    var file = _input[0].files[0];
    if (file.size > 3 * 1024 * 1024) {
        alert("上传图片不能大于3M");
        return false;
    }
    return true;
}