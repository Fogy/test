



$(function () {

    //登录
    $("#sub").click(function () {

        if(!$("#manageCount").val()){
            toastr.warning("请输入用户名");
            return
        }
        if(!$("#managePassword").val()){
            toastr.warning("请输入密码");
            return
        }

        $.ajax({
            type:"post",
            url:"/subLogin",
            data:{
                "manageCount":$("#manageCount").val(),
                "managePassword":$("#managePassword").val(),
            },
            //dataType:"json",
            success:function(data){
                if("success" != data){
                    toastr.warning(data)
                }else {
                    window.location.href="/";
                }

                console.log(data)

            },
            error:function (data) {
                toastr.warning("服务器异常请联系客服");
            }
        })
    });

});