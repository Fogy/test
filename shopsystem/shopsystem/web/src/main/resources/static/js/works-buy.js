
var downFunction= function () {
    var worksId = $("#works-id").text();
    var form = $("<form>");
    form.attr("style","display:none");
    form.attr("target","");
    form.attr("method","post");
    form.attr("action","/downWorks");
    var input1 = $("<input>");
    input1.attr("type","hidden");
    input1.attr("name","worksIds");
    input1.attr("value",worksId);
    $("body").append(form);
    form.append(input1);
    form.submit();
    form.remove()
}

/**
 * 点击作评下载
 */
var downWorks = function(userInfo){
    var worksId = $("#works-id").text();
    $.ajax({
        url:"/judgeWorks",
        type:"POST",
        data:{"worksIds":worksId},
        success:function(data){
            if("success" == data){
                //如果购买过此商品，直接下载
                downFunction();
            }else{
                //如果没有购买，则打开付款模态模态
                console.info(userInfo);
                $("#works-buy-model").modal('show');
                $("#customer-balance").text(userInfo.value.remain);
            }
        },
        error:function (data) {
            toastr.error('请刷新页面后再试');
        }
    })
}




$(function () {

    //购买视频
    $("#buyWorks").click(function () {
        var userInfo = getUserInfo();
        downWorks(userInfo);
    });
    //works-id
    //支付作品
    $("#works-pay").click(function () {
        //购买之前查询客户时候已经购买商品;
        var worksId = $("#works-id").text();
        var userInfo = getUserInfo();
        if($("#buy-confirm").prop("checked")==true){
            $.ajax({
                url:"/buyWorks",
                type:"POST",
                data:{"worksIds":worksId},
                success:function(data){
                    if(undefined==data || null==data){
                        toastr.error('购买失败，请刷新页面后再试');
                    }else if ("success" == data){
                        toastr.success("恭喜你购买成功")
                        downWorks(userInfo);
                    }else{
                        const newTab = window.open();
                        const div = document.createElement('div');
                        div.innerHTML = data; // html code
                        newTab.document.body.appendChild(div);
                        /*newTab.document.forms.alipaysubmit.submit();*/
                        newTab.document.forms[0].submit();
                    };
                    //关闭购买模态框
                    $("#works-buy-model").modal('hide');
                },
                error:function (data) {
                    toastr.error('购买失败，请刷新页面后再试');
                    //getWorksByAccountId(1);
                }
            })

        }else{
            toastr.warning('请阅读并同意本站协议后再购买');
        }

    });

    //点击更多作品
    $("#works-more-control").click(function () {
        var em = $(this).find("span").eq(1);
        if(em.hasClass("fa-angle-double-up")){
            em.removeClass("fa-angle-double-up");
            em.addClass("fa-angle-double-down");
            $("#works-more").hide();
        }else{
            em.removeClass("fa-angle-double-down");
            em.addClass("fa-angle-double-up");
            $("#works-more").show();
        }

    });

});