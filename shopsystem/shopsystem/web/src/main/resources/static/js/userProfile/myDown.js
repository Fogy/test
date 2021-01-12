//分页函数
function pageStart() {//分页函数
    var key = getUrlParam("keyWord");
    $.ajax({ //去后台查询第一页数据
        type : "post",
        url : "/getWorksDownload",
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
                        url : "/getWorksDownload",
                        type : "post",
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
            $('#my-down-page').bootstrapPaginator(options);//设置分页
        }
    });
}

function appendMydown(list) {//此函数用于处理后台返回的数据，根据自己需求来实现页面拼接
    var tableShow = '';

    for (var i = 0; i < list.length; i++) {
        tableShow +=`<tr>
        <td><img style="max-height: 60px" src="${list[i].previewUrl}"></td>
        <td>${list[i].price}元</td>
        <td>${list[i].downTime}</td>
        <td>
            <a type="button" href="/getWorksDetail?worksIds=${list[i].worksId}" class="btn btn-link">详情</a>
        </td>
    </tr>`
    }


    $('#mydown-works-table-body').empty();
    $('#mydown-works-table-body').append(tableShow);

}

$(function () {
    pageStart();
    //加载我的下载内容
    $("#my-down-page").click(function () {
        pageStart();
    });
});