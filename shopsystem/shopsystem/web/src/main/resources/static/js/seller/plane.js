

//查询平面模板
var getReferPlane=function () {
    $.ajax({
        url:"/getWorksByAccountId",
        type:"POST",
        contentType:'application/json;charset=utf-8',
        data:JSON.stringify({
            //"accountId":$("#accountId").val(),
            "status":"refer",
            "type":"3"
        }),
        success:function(data){
            $("#listPlane").empty();
            var works='';
            for(var index in data){

                works+="<div class='col-md-3'>";
                works+="<div class='mt-card-item'>";
                works+="<div class='mt-card-avatar mt-overlay-1 mt-scroll-down'>";
                if(data[index].previewUrl){
                    works+="<img style='height:200px;object-fit: cover; ' src='"+data[index].previewUrl+"'/>"
                }else{
                    works+="<img style='height:200px;object-fit: cover; ' src='/static/imges/defaultEdit.jpg'/>"
                }

                works+="</div>";

                works+="<div class='mt-card-content'>";
                works+="<div class='caption'>";
                works+="<a href='javascript:;' class='btn btn-sm blue' onclick='editPlaneById("+data[index].worksId+")'> 编辑 </a>";
                works+="<a href='javascript:;' onclick='delWorksById("+data[index].worksId+")' class='btn btn-sm red'> 删除 </a>"
                works+="</div>";
                works+="</div>";
                works+="</div>";
                works+="</div>";
            }
            $("#listPlane").append(works);
        },
        error:function(data){

        }
    });

}
//重置输入框
var resert_plane = function(){
    $("#planeUpForm")[0].reset();
    $("#plane_colorSytem").selectpicker('deselectAll');//重置下拉框
    $("#plane_colorSytem").val("");
    $("#plane_is-free").find("option").first().attr("selected", true);
    $("#plane_price").val("");
    $("#price").hide();
    $("#plane_material").find("option").first().attr("selected", true);
    $("#plane_material_psd").show();
    $("#plane_material_psd_val").find("option").first().attr("selected", true);
    $("#plane_material_psd_val_div").hide("");
    $("#plane_material_psd_val_div_val").val("");


    $("#plane_material_ai").hide();
    $("#plane_material_ai_val").find("option").first().attr("selected", true);
    $("#plane_material_ai_val_div").hide();
    $("#plane_material_ai_val_div_val").val("");

    $("#plane_material_eps").hide();
    $("#plane_material_eps_val").find("option").first().attr("selected", true);
    $("#plane_material_eps_val_div").hide();
    $("#plane_material_eps_val_div_val").val("");

    $("#plane_material_cdr").hide();
    $("#plane_material_cdr_val").find("option").first().attr("selected", true);
    $("#plane_material_cdr_val_div").hide();
    $("#plane_material_cdr_val_div_val").val("");
    $("[name='channel']").removeAttr("checked");//取消全选




}
//编辑作品
var editPlaneById=function(worksId){
    $("#plane_model_form").modal('show');
    resert_plane();
    $.ajax({
        url:"/getWorksById",
        type:"POST",
        data:{"worksIds":worksId},
        success:function(works){
            //console.info(works);
            $("#plane_worksId").val(works.worksId);
            $("#plane_worksTitle").val(works.title);
            $("#plane_keyWord").val(works.keyWord);
            $("#plane_worksType").val(works.type);
            $("#plane_price").val(works.price);
            $("#plane_worksDuration").val(works.duration);
            if(works.previewUrl){
                $("#plane_previewUrl_show").attr('src',works.previewUrl);
            }else{
                $("#plane_previewUrl_show").attr('src','/static/imges/defaultEdit.jpg');
            }

            // $("#synthesisUrl_show").attr('src',works.synthesisUrl);
            $("#saveWorks").bind("click",function () {
                saveWorks();
            });

        },
        error:function (data) {
            toastr.error('获取数据失败请刷新页面后重试.');
            getWorksByAccountId(1);
        }
    })

}
//




$(function () {

    //第一个视频类别点击
    $("#plane_material_psd_val").change(function () {
        var materialType = $(this).val();
        if(materialType == "自定义"){
            $("#plane_material_psd_val_div").show();
            $("#plane_material_psd_val_div").find("input").val("");
        }else{
            $("#plane_material_psd_val_div").hide();
            $("#plane_material_psd_val_div").find("input").val("");
        }

    });
    //素材类别
    $("#plane_material").change(function () {
        var material = $(this).val();
        var listMetal = ["plane_material_psd","plane_material_ai",
            "plane_material_eps","plane_material_cdr"];
        var defMet = ["plane_material_psd_val_div","plane_material_ai_val_div",
            "plane_material_eps_val_div","plane_material_cdr_val_div"];
        for(index in listMetal){
            if(material == index){
                $("#"+listMetal[material]).show();
                $("#"+listMetal[material]+"_val option:first").prop("selected", 'selected');
                $("#"+listMetal[material]+"_val").change(function(){
                    var materialType = $(this).val();
                    if(materialType == "自定义"){
                        $("#"+defMet[material]).show();
                        $("#"+defMet[material]).find("input").val("");
                    }else{
                        $("#"+defMet[material]).hide();
                        $("#"+defMet[material]).find("input").val("");
                    }
                });
            }else{
                $("#"+listMetal[index]).hide();
                $("#"+defMet[index]).hide();
                $("#"+defMet[index]).find("input").val("");
            }
        }
    });

    //点击提交作品

    $("#plane_saveUpWorks").click(function () {
        var planeData = new FormData($('#planeUpForm')[0]);
        planeData.delete("material");
        planeData.delete("material_type");
        planeData.delete("plane_resolution");


        var cols = "";
        var chans = "";
        $("#plane_colorSytem option:selected").each(function () {
            cols +=$(this).val()+",";
        })
        if(cols.length>0){
            cols =cols.substring(0,cols.length-1);
        }

        $("input[name='channel']:checked").each(function () {
            chans +=$(this).val()+",";
        })
        if(chans.length>0){
            chans =chans.substring(0,chans.length-1);
        }
        var resolution=$("#plane_resolution").val();
        var resolution_w=$("#plane_resolution_width").val();
        var resolution_h=$("#plane_resolution_height").val();
        if(resolution=='自定义'){
            if(resolution_w && resolution_h){
                resolution = resolution_w+"×"+resolution_h;
            }else {
                resolution = '';
            }
        }

        planeData.delete("resolution");
        planeData.delete("colorSytem");
        planeData.delete("channel");
        planeData.append("colorSytem",cols);
        planeData.append("channel",chans);
        planeData.append("resolution",resolution);

        var mater=$("#plane_material").val();

        var materList = ['PSD','AI','EPS','CDR','TIF','JPG','PNG'];
        var listMetal = ["plane_material_psd","plane_material_ai",
            "plane_material_eps","plane_material_cdr"];

        var materVal = materList[mater];
        var materType = '';

        if(mater<4){
            materType = $("#"+listMetal[mater]+"_val").val();
            if("自定义" == materType){
                materType = $("#"+listMetal[mater]+"_val_div_val").val();
            }
        }

        planeData.append("material",materVal);
        planeData.append("materialType",materType);


        $.ajax({
            url:"/saveUpPlane",
            type:"POST",
            data:planeData,
            processData:false,   //  告诉jquery不要处理发送的数据
            contentType:false,   // 告诉jquery不要设置content-Type请求头
            success:function(data){
                $("#plane_model_form").modal('hide')
                toastr.success('提交审核成功.');
                getReferPlane();
                PersonMangePageStart()
            },
            error:function(data){
                toastr.error('删除失败请刷新页面后重试.');
            }
        });

    });

    //加载平面
    getReferPlane();
    //展示模型
    $("#plane").click(function () {
        $("#plane_model").modal('show');
    });

    /**
     *上传平面素材
     */
    $("#plane-sub").click(function () {
        var data = new FormData($('#planeForm')[0]);
        $.ajax({
            url: "/uploadPlane",
            type: "POST",
            data: data,
            processData: false,   //  告诉jquery不要处理发送的数据
            contentType: false,   // 告诉jquery不要设置content-Type请求头
            xhr:xhrOnProgress(function(e){
                $("#plane_model").modal('hide');
                var percent=e.loaded / e.total;//计算百分比
                $('#progress').show();
                $('#progress').addClass("active");
                var progress = parseInt(percent * 100, 10);
                $('#progress .progress-bar').css(
                    'width',progress+'%'
                );
                $('#progress .progress-bar').html(
                    progress + '%'
                );
            }),
            success: function (data) {
                toastr.success("文件上传成功.");
                $('#progress').hide();
                getReferPlane();
            },
            error: function (data) {
                toastr.error("服务器异常,请联系客服.");
                $('#progress').hide();
            }
        });
    });

    /**
     * 是否免费
     */
    $("#plane_is-free").change(function () {
        var isfree=$("#plane_is-free").val();
        if(isfree == 1){
            $("#plane_price_div").show();
            $("#plane_price").val("");
        }else{
            $("#plane_price_div").hide();
            $("#plane_price").val("");
        }

    })

    /**
     * 自定义分辨率
     */
    $("#plane_resolution").click(function () {
        var resolution = $("#plane_resolution").val();
        if (resolution == "自定义"){
            $("#plane_resolution_div").show();
            $("#plane_resolution_width").val("");
            $("#plane_resolution_height").val("");
        }else{
            $("#plane_resolution_div").hide();
            $("#plane_resolution_width").val("");
            $("#plane_resolution_height").val("");
        }

    });
    //点击预览大图；
    $("#plane_previewUrl").fileupload({
        replaceFileInput:false,
        singleFileUploads:false,//允许选择多个
        sequentialUploads:true,//允许多个File name同时上传
        autoUpload:false,
        processData : false, //必须false才会避开jQuery对 formdata 的默认处理
        multipart:true,
        showDone: false,                     //是否显示"Done"(完成)按钮
        showDelete: true,                  //是否显示"Delete"(删除)按钮
        add:function(e,data){
            //获取图片路径并显示
            var url = getPicUrl(data.files[0]);
            $("#plane_previewUrl_show").attr("src", url);
        }
    });




});