
var recharge=function (payAmount) {
    $.ajax({
        url:"/balance/recharge",
        type:"POST",
        data:{"payAmount":payAmount},
        success:function(data){
            if(undefined != data || data != null){
                const newTab = window.open();
                const div = document.createElement('div');
                div.innerHTML = data; // html code
                newTab.document.body.appendChild(div);
                /*newTab.document.forms.alipaysubmit.submit();*/
                newTab.document.forms[0].submit();
            }else{
                toastr.error('充值异常，请刷新页面后再试');
            }

        },
        error:function (data) {
            toastr.error('请输入正确充值金额');
        }
    })
};

/**
 * 获取充值记录
 * @type {jQuery}
 */
var balanceTable=$('#user-balance-table').DataTable({
    scrollCollapse:true,
    serverSide: true,
    lengthMenu: [5, 10, 20, 30],
    ordering: false,
    language: {
        "url": "/assets/global/plugins/datatables/datatables_language.json"
    },
    "ajax": {
        type: "post",
        url: "/balance/getRechargeRecords",
    },
    "columns": [
        {
            "data": "rechargeMoney",
            "width":"50%",
            "sortable": false
        },
        { "data": "rechargeTime","width":"50%"}
    ],
    "dom": "tr" +
        "<'row' <'col-md-6' p>>",
    paging: true,
    pagingType: "simple_numbers",
});


var getUserBalance=function(){
    $.ajax({
        url:"/balance/getUserBalance",
        type:"POST",
        //data:{"worksIds":worksId},
        success:function(data){
            if(undefined != data || data != null){
                $("#user-balance").text(data);
            }else{
                $("#user-balance").text(0);
            }

        },
        error:function (payAmount) {
            toastr.error('服务器异常，请刷新页面后再试');
        }
    })
}

$(function () {
    //更多充值
    $("#char-more").click(function () {
        if($(this).hasClass("fa-angle-double-down")){
            $(this).removeClass("fa-angle-double-down");
            $(this).addClass("fa-angle-double-up");
            $("#pay-val-more").show();
        }else{
            $(this).removeClass("fa-angle-double-up");
            $(this).addClass("fa-angle-double-down");
            $("#pay-val-more").hide();
        }
    });
    //账户余额
    $("#my-balance").click(function () {
        getUserBalance();
        balanceTable.ajax.reload();
    });
    //账户自定义充值
    $("#self-recharge-btn").click(function () {
        var charge = $("#self-recharge-val").val();
        if(charge){
            recharge(charge);
        }else{
            toastr.warning('请填写充值金额，请刷新页面后再试');
        }


    })
});
