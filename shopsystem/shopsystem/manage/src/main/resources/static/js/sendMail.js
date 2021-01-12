$(function () {
    /**
     * 邮件发送
     */
    $("#email_send").click(function () {
        $("#login_model").modal('hide');
        $("#email_model").modal('show');
    });

    /**
     * 点击发送邮件地址
     */
    $("#send_email_auth").click(function () {
        //发送邮箱
        $.ajax({
            type:"post",
            url:"/login/email",
            data:{
                "email":$("#email_adress").val()
            },
            dataType:"json",
            success:function(data){
                console.info(data);
                alert(data)

            }
        })

    });

});