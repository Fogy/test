//分页函数
function PersonMangePageStart() {//分页函数
    var key = getUrlParam("keyWord");
    $.ajax({ //去后台查询第一页数据
        type : "post",
        url : "/getPersonManage",
        async: false,
        data : {
            "page" : '1',
            "pageSize" : '10',

        }, //参数：当前页为1
        success : function(data) {
            appendMydown(data.data)//处理第一页的数据
            var options = {//根据后台返回的分页相关信息，设置插件参数
                bootstrapMajorVersion : 3, //如果是bootstrap3版本需要加此标识，并且设置包含分页内容的DOM元素为UL,如果是bootstrap2版本，则DOM包含元素是DIV
                currentPage : data.page+1, //当前页数
                totalPages : data.totalPages, //总页数
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
                    //var key = getUrlParam("key");
                    $.ajax({//根据page去后台加载数据
                        url : "/getPersonManage",
                        type : "post",
                        async: false,
                        dataType : "json",
                        data : {
                            "key":key,
                            "page" : page,
                            "pageSize" : '10',

                        },
                        success : function(data) {
                            console.info(data);
                            appendMydown(data.data);//处理数据
                        }
                    });
                }
            };
            $('#person-manage-table-page').bootstrapPaginator(options);//设置分页
        }
    });
}
//内容拼接
function appendMydown(list) {//此函数用于处理后台返回的数据，根据自己需求来实现页面拼接
    var tableShow = '';

    for (var i = 0; i < list.length; i++) {
        var status = list[i].status;
        if(status == "refer"){
            status = "编辑";
        }else if (status == "auditing"){
            status = "审核中";
        }else if (status == "reject"){
            status = "审核不通过";
        }
        else if (status == "pass"){
            status = "审核通过";
        };
        var keyWord='';
        if(undefined !=list[i].keyWord &&  null != list[i].keyWord){
            keyWord = list[i].keyWord;
        }
        tableShow +=`<tr>
        <td>${list[i].title}</td>
        <td><img style="max-height: 60px" src="${list[i].previewUrl}"></td>
        <td>${keyWord}</td>
        <td>${status}</td>
        <td>
            <a type="button" onclick="editPersonManage(${list[i].worksId},${list[i].type})" class="btn green btn-sm">编辑</a>
        </td>
        <td>
            <a type="button" onclick="delPersonMangeWorks(${list[i].worksId})" class="btn btn-danger btn-sm">删除</a>
        </td>
    </tr>`
    }
    $('#person-manage-table-body').empty();
    $('#person-manage-table-body').append(tableShow);
}
var editPersonManage = function (worksId,type) {
    var workEdit="editWorksById(worksId)"
    if(type==1){
        editWorksById(worksId)

    }else if(type==2){
        editTemplatesId(worksId)
    }else{
        editPlaneById(worksId)
    }




}
//删除函数
var delPersonMangeWorks=function(worksId){
    bootbox.confirm({
        title:"警告!",
        message: "作品删除后将不可恢复,确认是否删除",
        locale: 'zh_CN',
        buttons: {
            cancel: {
                label: '取消',
                className: 'btn'
            },
            confirm: {
                label: '确定',
                className: 'btn-danger'
            }
        },
        callback: function (res) {
            if(!res){
                return
            }
            delPersonMangeWorks(worksId);
        }
    });
}
//删除作品
var delPersonMangeWorks = function(worksId){
    $.ajax({
        url:"/delPersonMangeWorks",
        type:"POST",
        data:{"worksId":worksId,
                "isDel":'1'},
        success:function(data){
            toastr.success('删除成功.');
            PersonMangePageStart();
        },
        error:function (data) {
            toastr.error('删除失败请刷新页面后重试.');
            PersonMangePageStart();
        }
    })

}
$(function () {
    PersonMangePageStart();
});