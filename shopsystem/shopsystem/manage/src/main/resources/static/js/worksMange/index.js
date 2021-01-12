var dataTable=$('#table1').DataTable({
    scrollCollapse:true,
    serverSide: true,
    lengthMenu: [5, 10, 20, 30],
    ordering: false,
    language: {
        "url": "/assets/global/plugins/datatables/datatables_language.json"
    },
    "ajax": {
        type: "post",
        url: "/worksManage/getWorks",
        data: function (d) {
            d.title=$("#worksTitle").val();
            d.keyWord=$("#keyWord").val();
            d.status=$("#worksStatus").val();
            return d;
        }
    },
    "columns": [
        {
            "data": "title",
            "sortable": false
        },
        /*{ "data": "worksDesc" },*/
        { "data": "keyWord" },
        { "data": "status",
            "render":function (data, type, row) {
            if (data =="auditing") {
                return "审核中"
            }else if (data =="reject"){
                return "审核不通过"
            } else if (data =="pass"){
                return "审核通过"
            }

            }
        },
        { "data": "worksId" ,
            "render":function(data, type, row){
                return "<button type='button' onClick='manageWorks("+data+")' class='btn btn-link'>审核</button>"
            }}
    ],
    "dom": "tr" +
        "<'row' <'col-md-2' l><'col-md-4' i><'col-md-6' p>>",
    paging: true,
    pagingType: "full_numbers",
});
//查询作品
var getWorksById=function (worksId) {
    $.ajax({
        url:"/worksManage/getWorksById",
        type:"POST",
        data:{
            "worksId":worksId,
        },
        success:function(works){
            $("#mod-worksId").html(works.worksId)
            $("#mod-worksTitle").html(works.title);
            $("#mod-worksDuration").html(works.duration);
            $("#mod-keyWord").html(works.keyWord);
            $("#mod-resolution").html(works.resolution);
            if(works.price){
                $("#mod-price").html(works.price);
            }else {
                $("#mod-price").html("免费");
            }
            $("#mod-version").html(works.version);

            $("#mod-channel").html(works.channel);
            //$("#mod-colorSytem").html(works.colorSytem);
            var content ='';
            if(works.colorsytem){
                var colors = works.colorsytem.split(",");
                for(var i=0;i<colors.length;i++){
                    content += "<span style='color:dodgerblue;background: "+colors[i]+"'  class='label lable-sm label-success'>"+colors[i]+"</span>&nbsp;"
                    if (0==(i+1)%5){
                        content +="<br/><br/>"
                    }
                }

            }
            $("#mod-colorSytem").html(content);



            $("#mod-previewUrl_show").attr('src',works.previewUrl);
            $("#mod-synthesisUrl_show").attr('src',works.synthesisUrl);
            if(works.status=='alpha'){
                $("input[name='mod-channel']:eq(0)").attr("checked",'checked');
            }else{
                $("input[name='mod-channel']:eq(1)").attr("checked",'checked');
            }
            $("#mod-worksStatus").val(works.status);
            $("#mod-compressUrl").attr('href',works.compressUrl);
            if(works.type==2){
                $("#mod-worksUrl").attr('href',works.zipUrl);
            }else{
                $("#mod-worksUrl").attr('href',works.worksUrl);
            }


        },
        error:function(data){


        }
    });

}

/**
 * 审核作品
 * @param worksId
 */
var manageWorks=function(worksId){
    $("#video_model").modal('show');
    //查询作品
    getWorksById(worksId);
}

$(function(){
    //点击菜单搜索功能
    $("#searchWorks").click(function () {
        dataTable.ajax.reload();
    });
    //点击审核
    $("#saveUpWorks").click(function () {
        $.ajax({
            url:"/worksManage/reviewWorks",
            type:"POST",
            data:{
                "worksId":$("#mod-worksId").html(),
                "status":$("#mod-worksStatus").val()
            },
            success:function (data) {
                console.log(data);
               if (data !=null && data.errorCode !=400){
                    toastr.success("审核成功");
                    dataTable.ajax.reload();
                }else{
                   toastr.error("服务器异常")
               };
                $("#video_model").modal('hide');
            },
            error:function (data) {
                toastr.error("服务器异常")
            }
        })
    });
});