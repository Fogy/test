



$(function () {
//作品加入购物车
    $("#addWorks").click(function () {
        var worksId = $("#works-id").text();
        $.ajax({
            url:"/addWorks",
            type:"POST",
            data:{"worksIds":worksId},
            success:function(works){
                alert(1);
            },
            error:function (data) {
                alert(2)
                toastr.error('购买失败，请刷新页面后再试');
                //getWorksByAccountId(1);
            }
        })

    });


});