



//展示评论模态
var addComment = function (parId =0) {
    $("#works-comment-model").modal('show');
    $("#comment").val('');
    $("#fatherCommentId").val(parId);
}

//查询评论
var selComments = function (){
    var accound = $("#accountId").val();
    var worksId = getUrlParam("worksIds");
    $.ajax({
        url: "/selectBYWorksId",
        type: "POST",
        data: {
            "worksId": worksId
        },
        success: function (data) {
            $("#media-list").empty();
            var media_list = '';
            for(var i in data){
                var btn=''
                var commentDate= new Date(data[i].commentTime)

                media_list +=`
                <li class="media">
                    <a class="pull-left" href="javascript:;">
                        <img class="todo-userpic" src="${data[i].headerImageUrl}" width="27px" height="27px">
                    </a>
                    <div class="media-body todo-comment">
                        <p class="todo-comment-head">
                            <span class="todo-comment-username">${data[i].accountName}</span>
                            <span class="todo-comment-date" onclick="addComment(${data[i].commentId})">${dateFormat("YYYY-mm-dd HH:MM", commentDate)}
                                   <b style="cursor: pointer">
                                       <i class="fa fa-comments"></i>回复
                                   </b>
                            </span>
                        </p>`;

                if(accound ==data[i].accountId ){
                    media_list +=`<button type="button" onclick="delComments(${data[i].commentId})" style="float: right" class=" btn btn-circle red btn-sm">删除</button>`;
                }
                media_list +=`<p class="todo-text-color">${data[i].comment}</p>`;

                var child  = data[i]["commentsList"];
                for(var j in child){
                    var chi = child[j];
                    var btn='';
                    if(accound ==chi.accountId ){
                        btn=`<button type="button" onclick="delComments(${chi.commentId})" style="float: right" class=" btn btn-circle red btn-sm">删除</button>`;
                    }
                    var tims = new Date(chi.commentTime);
                    media_list +=`
                    <div class="media">
                        <a class="pull-left" href="javascript:;">
                            <img class="todo-userpic" src="${chi.headerImageUrl}"
                                 width="27px" height="27px"> </a>
                        <div class="media-body">
                            <p class="todo-comment-head">
                                <span class="todo-comment-username">${chi.accountName}</span>
                                <span class="todo-comment-date">${dateFormat("YYYY-mm-dd HH:MM", tims)}</span>
                            </p>
                            <p class="todo-text-color">${chi.comment}</p>
                            ${btn}
                        </div>
                    </div>`
                }
                media_list +="</div></li>"
            }
            $("#media-list").append(media_list);
        },
        error: function (data) {
            toastr.error('获取数据失败请刷新页面后重试.');
        }
    })
}
//删除评论
var delComments= function(commentId){
    $.ajax({
        url:"/delComments",
        type:"POST",
        data:{"commentId":commentId},
        success:function(data){
            selComments();
            toastr.success('评论删除成功.');
        },
        error:function (data) {
            toastr.error('获取数据失败请刷新页面后重试.');
        }
    })
}


$(function () {
    selComments();
    $("#save-comment").click(function (){
        var worksId = $("#works-id").text();
        var fatherCommentId = $("#fatherCommentId").val();
        var comment = $("#comment").val();
        $.ajax({
            url:"/addComments",
            type:"POST",
            data:{"worksId":worksId,
                "fatherCommentId":fatherCommentId,
                "comment":comment
            },
            success:function(data){
                selComments();
                $("#works-comment-model").modal('hide');
            },
            error:function (data) {
                toastr.warning('请登录后再评论.');
            }
        })
    });


});
