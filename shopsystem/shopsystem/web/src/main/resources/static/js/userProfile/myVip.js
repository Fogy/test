
var getVip = function (vipType,tableId) {
    $.ajax({
        url:"/getVipDetails",
        type:"POST",
        data:{"vipType":vipType},
        success:function(data){
            var tableVale='';
            var vTable=$("#"+tableId);
            vTable.empty();
            if (data){
                var vipTable = data.value;
                var i;
                for(i=0;i<vipTable.length;i++){
                    var vipDetail = vipTable[i].vipDetailList;
                    if (vipDetail && vipDetail.length>0){
                        var j=0;
                        for (j;j<vipDetail.length;j++){
                            var vpDe=vipDetail[j];
                            if(j==0){
                                tableVale+=`<tr>
                                <td rowspan="${vipDetail.length}" style="vertical-align: middle">${vipTable[i].typeName}</td>
                                <td> ${vpDe.timeLong}${vpDe.timeUnit}</td>
                                <td> ${vpDe.prices}</td>
                                <td> ${vpDe.comMaterial}年</td>
                                <td>${vpDe.customDiscount}</td>
                                <td>每天下载${vpDe.material}次</td>
                                <td>${vpDe.customDiscount}</td>
                                <td>${vpDe.invoiceType}</td>
                                <th><a onclick="oper_person_vip(${vpDe.vipId})">开通</a></th>
                            </tr>`;
                            }else{
                                tableVale+=`<tr>
                                <td> ${vpDe.timeLong}${vpDe.timeUnit}</td>
                                <td> ${vpDe.prices}</td>
                                <td> ${vpDe.comMaterial}年</td>
                                <td>${vpDe.customDiscount}</td>
                                <td>每天下载${vpDe.material}次</td>
                                <td>${vpDe.customDiscount}</td>
                                <td>${vpDe.invoiceType}</td>
                                <th><a onclick="oper_person_vip(${vpDe.vipId})">开通</a></th>
                            </tr>`;
                            }
                        }
                    }
                }
            }
            if(tableVale){
                vTable.append(tableVale) ;
            }
        },
        error:function (payAmount) {
            toastr.error('服务器异常，请刷新页面后再试');
        }
    })
}
$(function () {

    $("#person-nav li").each(
        function (index,element) {
            if($(this).hasClass("active")){
                $(this).click();
            }

        }
    )

    if ($("#vip-enterprise").hasClass("active")){
        $("#vip-enterprise").click();

    }
});