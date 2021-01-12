//开通个人vip
var oper_person_vip=function (vipId) {
    $("#user_profile_open_vip_model").modal('show');
    $("#vip-id").val(vipId);
    setBalance();
    setPrice(vipId);

}

var setBalance=function () {
    $.ajax({
        url:"/getCustomer",
        type:"POST",
        success:function(data){
            $("#customer-balance").text(data.value.remain);
        },
        error:function (data) {
            $("#customer-balance").val(0);
            toastr.error('请刷新页面后再试');
        }
    })
}

var setPrice = function (vipId){
    $.ajax({
        url:"/getVipById",
        data:{
            "vipId":vipId
        },
        type:"POST",
        success:function(data){
            $("#vip-price").text(data.value.prices);
        },
        error:function (data) {
            $("#vip-price").val(0);
            toastr.error('请刷新页面后再试');
        }
    })
}

//vip付款
var sellVip=function () {
    var vipId = $("#vip-id").val();
    $.ajax({
        url:"/sellVip",
        data:{
            "vipId":vipId
        },
        type:"POST",
        success:function(data){
            if(data.code==200){
                toastr.success("恭喜你购买成功")
            }else if(data.code==201){
                const newTab = window.open();
                const div = document.createElement('div');
                div.innerHTML = data.value; // html code
                newTab.document.body.appendChild(div);
                newTab.document.forms[0].submit();
            }
        },
        error:function (data) {
            $("#vip-price").val(0);
            toastr.error('请刷新页面后再试');
        }
    })
}