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

function clickImgPng(flag) {
    $(".downloadPng").off("click");
    $(".downloadPng").on("click", function (event) {
        console.log(this)
        var pngName = $(this).attr("data-name");
        var that = this;
        var targetDom = $(that).parent().parent().find(".graphs")[0];
        var targetDom1 = $(that).parent().parent().find(".graphs");
        if(flag){
            var height = targetDom1.outerHeight()+20;
        }else{
            var height = targetDom1.outerHeight()*2+20;
        }
        html2canvas(targetDom, {
            useCORS: true,
            height: height,
            backgroundColor: '#ffffff',
            allowTaint: true,
            taintTest: false
        }).then(function (canvas) {
            //生成base64图片数据
            var dataUrl = canvas.toDataURL("image/png");
            //以下代码为下载此图片功能
            var triggerDownload = $("<a>").attr("href", dataUrl).attr("download", pngName + ".png").appendTo("body");
            triggerDownload[0].click();
            triggerDownload.remove();
        });

    });

    $(".downloadGraphPng").off("click");
    $(".downloadGraphPng").on("click", function (event) {
        var pngName = $(this).attr("data-name");
        var canvas = $(this).parent().find("svg")[0];
        var that = this;
        //调用方法转换即可，转换结果就是uri,
        svgAsPngUri(canvas, null, function (uri) {
            var image = document.createElement("img");
            image.src = uri;
            var widths = $($(that).parent().find("div")[0]).outerWidth();
            if(flag){
                var height = $($(that).parent().find("div")[0]).outerHeight()+20;
            }else{
                var height = $($(that).parent().find("div")[0]).outerHeight()*2+20;
            }
            var heights = $($(that).parent().find("div")[0]).outerHeight();
            html2canvas($(that).parent().find("div")[0], {
                useCORS: true,
                height: height,
                backgroundColor: "#ffffff",
                allowTaint: true,
                taintTest: false
            }).then(function (canvasss) {
                //生成base64图片数据
                var dataUrl = canvasss.toDataURL("image/png");
                var newImg = document.createElement("img");
                newImg.src = dataUrl;
                var context = canvasss.getContext('2d');  //取得画布的2d绘图上下文
                context.drawImage(newImg, 0, 0);
                context.drawImage(image, 0, 30);
                //以下代码为下载此图片功能
                var triggerDownload = $("<a>").attr("href", dataUrl).attr("download", pngName + ".png").appendTo("body");
                triggerDownload[0].click();
                triggerDownload.remove();
            });
            // html2canvas($(that).parent().find("div")[0], {
            //     allowTaint: true,
            //     taintTest: false,
            //     onrendered: function(canvasss) {
            //         //生成base64图片数据
            //         var dataUrl = canvasss.toDataURL();
            //         var newImg = document.createElement("img");
            //         newImg.src =  dataUrl;
            //         var context = canvasss.getContext('2d');  //取得画布的2d绘图上下文
            //         context.drawImage(newImg, 0, 0);
            //         context.drawImage(image, 0, 30);
            //         var a = document.createElement('a');
            //         a.href = canvasss.toDataURL('image/png');  //将画布内的信息导出为png图片数据
            //         a.download = pngName;  //设定下载名称
            //         a.click(); //点击触发下载
            //     }
            // });
        });
    });
}