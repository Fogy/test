//编辑模板
var editTemplatesId=function(worksId){
    $("#template_model").modal('show');
    resert();
    $.ajax({
        url:"/getWorksById",
        type:"POST",
        data:{"worksIds":worksId},
        success:function(works){
            $("#template_worksId").val(works.worksId);
            $("#template_worksTitle").val(works.title);
            $("#template_keyWord").val(works.keyWord);
            $("#template_worksType").val(works.type);
            $("#template_price").val(works.price);
            $("#template_worksDuration").val(works.duration);
            $("#template_previewUrl_show").attr('src',works.previewUrl);
            $("#template_synthesisUrl_show").attr('src',works.synthesisUrl);
            $("#template_saveWorks").bind("click",function () {
                saveWorks();
            });
        },
        error:function (data) {
            toastr.error('获取数据失败请刷新页面后重试.');
            getWorksByAccountId(1);
        }
    })
}


//重置输入框

var resert_temp = function(){
    $("#template_colorSytem").selectpicker('deselectAll');//重置下拉框
    $("#templateUpForm")[0].reset();
    $("#keyWord").val('');
    $("#template_price").val('');
    $("#template_price_div").hide();

    $("#template_resolution").val('');
    $("#template_resolution_div").hide();

    /*$("#template_material")[0].reset();*/

    $("#template_material_ae").hide();
    $("#template_material_ae_val_div").hide();
    $("#template_material_pr").hide();
    $("#template_material_pr_val_div").hide();
    $("#template_material_ppt").hide();
    $("#template_material_ppt_val_div").hide();

}

/************************************************************************/
$(function () {
    //售价点击
    $("#template_is-free").change(function () {
        var chart= $(this).val();
        if(chart ==0){
            $("#template_price").val('');
            $("#template_price_div").hide();
        }else{
            $("#template_price_div").show();
            $("#template_price").val('');
        }

    });

    //自定义分辨率
    $("#template_resolution").change(function () {
        var resolution = $(this).val();
        if(resolution !="自定义"){
            $("#template_resolution_width").val('');
            $("#template_resolution_height").val('');
            $("#template_resolution_div").hide();
        }else{
            $("#template_resolution_div").show();
            $("#template_resolution_width").val('');
            $("#template_resolution_height").val('');
        }
    });

    //素材类别
    $("#template_material").change(function () {
        var material = $(this).val();
        var listMetal = ["template_material_video","template_material_ae",
            "template_material_pr","template_material_ppt"];
        var defMet = ["","template_material_ae_val_div","template_material_pr_val_div","template_material_ppt_val_div"];
        for(index in listMetal){
            if(index != 0 ){
                $("#"+defMet[index]).hide();
                $("#"+defMet[index]).find("input").val("");
            }
            if(material == index){
                $("#"+listMetal[index]).show();
                $("#"+listMetal[index]+"_val option:first").prop("selected", 'selected');
            }else{
                $("#"+listMetal[index]).hide();
            }
        }
    });

    //点击AE模板种类

    $("#template_material_ae_val").change(function () {
        var temIndex = $("#template_material").val();
        var is_mater = temIndex == 1?true:false;
        defMaterial("template_material_ae_val",is_mater);
    });

    $("#template_material_pr_val").change(function () {
        var temIndex = $("#template_material").val();
        var is_mater = temIndex == 2?true:false;
        defMaterial("template_material_pr_val",is_mater);
    });

    $("#template_material_ppt_val").change(function () {
        var temIndex = $("#template_material").val();
        var is_mater = temIndex == 3?true:false;
        defMaterial("template_material_ppt_val",is_mater);
    });

    //是否模板自定义值
    var defMaterial = function(defId,is_mater){
        var va = $("#"+defId).val();
        if(is_mater && va == "自定义"){
            $("#"+defId+"_div").show();
            $("#"+defId+"_div_val").val("");
        }else{
            $("#"+defId+"_div").hide();
            $("#"+defId+"_div_val").val("");
        }
    }

    //点击提交作品

    $("#template_saveUpWorks").click(function () {
        var templateData = new FormData($('#templateUpForm')[0]);
        templateData.delete("material");
        templateData.delete("material_type");
        templateData.delete("template_resolution");


        var cols = "";
        var chans = "";
        $("#template_colorSytem option:selected").each(function () {
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
        var resolution=$("#template_resolution").val();
        var resolution_w=$("#template_resolution_width").val();
        var resolution_h=$("#template_resolution_height").val();
        if(resolution=='自定义'){
            if(resolution_w && resolution_h){
                resolution = resolution_w+"×"+resolution_h;
            }else {
                resolution = '';
            }
        }

        templateData.delete("resolution");
        templateData.delete("colorSytem");
        templateData.delete("channel");
        templateData.append("colorSytem",cols);
        templateData.append("channel",chans);
        templateData.append("resolution",resolution);

        var mater=$("#template_material").val();

        var materList = ['视频素材','AE模板','PR模板','PPT模板'];
        var listMetal = ["template_material_video","template_material_ae",
            "template_material_pr","template_material_ppt"];

        var materVal = materList[mater];
        var materType = '';

        for(index in listMetal){
            if(index === mater){
                materType = $("#"+listMetal[index]+"_val").val();
                if("自定义" == materType){
                    materType = $("#"+listMetal[index]+"_val_div_val").val();
                }
            }
        }

        templateData.append("material",materVal);
        templateData.append("materialType",materType);


        $.ajax({
            url:"/saveUpTemplates",
            type:"POST",
            data:templateData,
            processData:false,   //  告诉jquery不要处理发送的数据
            contentType:false,   // 告诉jquery不要设置content-Type请求头
            success:function(data){
                $("#template_model").modal('hide')
                toastr.success('提交审核成功.');
                getReferTemps();
                PersonMangePageStart();
            },
            error:function(data){
                toastr.error('删除失败请刷新页面后重试.');
            }
        });
        resert_temp();

    });


    //点击预览大图；
    $("#previewUrl").fileupload({
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
            $("#previewUrl_show").attr("src", url);
        }
    });











});