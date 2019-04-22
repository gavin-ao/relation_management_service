/**
 * Created by 12045 on 2018/7/4.
 */
var wholeAppInfoId;
(function () {

// 获取用户所有小程序
//     allSmallProgram();
    selecyCondition();
    $($($("#user_manage .dropdown-menu")[0]).find("li")[0]).trigger("click");

}())

// 获取用户所有小程序
function allSmallProgram() {
    $.ajax({
        url: "/wechat/appinfo/findAppInfoListByUser",
        dataType: "json",
        success: function (data) {

            if(data.success){
                var arrData = data.wechatAppInfoEntityList;
                var html='';
                for(var i=0;i<arrData.length;i++){

                    html += '<li data-appInfoId="'+ arrData[i].appInfoId+'" data-appid="'+ arrData[i].appid +'" data-secret="'+ arrData[i].secret +'"><a href="javascript:void(0)">'+arrData[i].appName+ '</a></li>';
                }
                $("#contain_head_left_ul").html(html);

                $($($("#user_manage .dropdown-menu")[0]).find("li")[0]).trigger("click");
            }
        }
    })
}


// 筛选条件的选择
function selecyCondition() {
    $("#user_manage .dropdown-menu").off('click',"li");
    $("#user_manage .dropdown-menu").on('click', 'li', function () {
        console.log(this)
        var that = this;
        var content = $.trim($(this).text());
        var dropMenu;
        var target = $(this).parents(".dropdown").find("button .selectIndex");
        switch ($(this.parentNode).attr("aria-labelledby")) {
            case "dropdownMenu1":
                $(target).html(content)
                var appInfoId = $(that).attr("data-appInfoId");
                if(appInfoId==1){
                    bossKanban();
                }else if(appInfoId==2){
                    salesPer();
                }else if(appInfoId==3){
                    smallProgram();
                }
                break;
            case "dropdownMenu2":
                $(target).html(content)
                break;
        }
    });
}


function bossKanban() {
    $.ajax({
        url: "/wechat/total/bossKanban",
        type: "post",
        data: {appInfoId: wholeAppInfoId},
        dataType: "html",
        success: function (data) {
            $("#contain_main").html("");
            console.log(data)
            $("#contain_main").html(data);
        }
    })
}

function salesPer() {
    $.ajax({
        url: "/wechat/total/salesPer",
        type: "post",
        data: {appInfoId: wholeAppInfoId},
        dataType: "html",
        success: function (data) {
            $("#contain_main").html("");
            console.log(data)
            $("#contain_main").html(data);
        }
    })
}

function smallProgram() {
    $.ajax({
        url: "/wechat/total/smallProgram",
        type: "post",
        data: {appInfoId: wholeAppInfoId},
        dataType: "html",
        success: function (data) {
            $("#contain_main").html("");
            console.log(data)
            $("#contain_main").html(data);
        }
    })
}