
//分页函数
function customerPageStart() {//分页函数
    $.ajax({ //去后台查询第一页数据
        type : "Post",
        url : "/shopMange/getPageCustomer",
        data : {
            "page" : '1',
            "pageSize" : '10',

        }, //参数：当前页为1
        success : function(data) {
            appendHtml(data['data'])//处理第一页的数据
            var options = {//根据后台返回的分页相关信息，设置插件参数
                bootstrapMajorVersion : 3, //如果是bootstrap3版本需要加此标识，并且设置包含分页内容的DOM元素为UL,如果是bootstrap2版本，则DOM包含元素是DIV
                currentPage : data.pageNum, //当前页数
                totalPages : data.pages, //总页数
                numberOfPages : data.pageSize,//每页记录数
                shouldShowPage:function(type, page, current){
                    if(type == "page" && Math.abs(page-current)>3){
                        return false
                    }else{
                        return true
                    }
                },
                itemTexts : function(type, page, current) {//设置分页按钮显示字体样式
                    switch (type) {
                        case "first":
                            return "首页";
                        case "prev":
                            return "上一页";
                        case "next":
                            return "下一页";
                        case "last":
                            return "末页";
                        case "page":
                            return page;
                    }
                },
                onPageClicked : function(event, originalEvent, type, page) {//分页按钮点击事件
                    $.ajax({//根据page去后台加载数据
                        url : "/shopMange/getPageCustomer",
                        type : "Post",
                        dataType : "json",
                        data : {
                            "page" : page,
                            "pageSize" : '10',
                        },
                        success : function(data) {
                            appendHtml(data["data"]);//处理数据
                        }
                    });
                }
            };
            $('#customerTableFoot').bootstrapPaginator(options);//设置分页
        }
    });
}

function appendHtml(list) {//此函数用于处理后台返回的数据，根据自己需求来实现页面拼接
    $('#customerBody').empty();
    var tbval ='';
    var tableShow = '';
    for (var i = 0; i < list.length; i++) {
        var type =list[i]["shoperType"];
        if(type=='personal'){
            type='个体'
        }else if(type=='merchant'){
            type='企业'
        }else if(type=='vip'){
            type='VIP'
        }else{
            type='其他'
        }
        tbval+=`<tr>
                    <td>${list[i]["accountId"]}</td>
                    <td>${list[i]["name"]}</td>
                    <td>${type}</td>
                    <td><a onclick="reviewSign(${list[i]["accountId"]})">操作</a></td>
                </tr>`;
    }
    $('#customerBody').append(tbval);

}

//获取商户
var reviewSign=function(accountId){
    $("#sign_review_model").modal('show');
    $.ajax({
        url:"/shopMange/getCustomerById",
        type:"POST",
        data:{
            "accountId":accountId,
        },
        success:function(cusomer){
            $("#accountId").val(cusomer.accountId);
            $("#name").text(cusomer.name);
            $("#account").text(cusomer.account);
            $("#account").text(cusomer.account);
            $("#phone").text(cusomer.phone);
            $("#idCardUrl_show").attr("src",cusomer.idCardUrl);
            $("#account").attr("src",cusomer.account);
            $("#phone").attr("src",cusomer.phone);
            $("#passPort_show").attr("src",cusomer.passPort);
            $("#protocol_show").attr("src",cusomer.protocol);

        },
        error:function(data){


        }
    });

}

$(function(){

    customerPageStart();
    $("#submit_review_apply").click(function () {
        var shoperStatus = $("#shoperStatus").val();
        var accountId = $("#accountId").val();
        $.ajax({
            url: "/shopMange/submitApply",
            type: "POST",
            data: {
                "shoperStatus": shoperStatus,
                "accountId": accountId,
            },
            success: function (data) {
                //$("#name").text(cusomer.name);
            },
            error: function (data) {


            }
        });
        $("#sign_review_model").modal('hide');

    });


});



/* $("#exam-id").popover({
     html :true,
     title:'<h2>年后</h2>'
 });*/
