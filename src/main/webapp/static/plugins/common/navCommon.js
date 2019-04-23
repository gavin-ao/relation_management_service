/**
 * Created by 12045 on 2018/10/22.
 */

(function ($) {

    $(document).ajaxComplete(function(event, xhr, settings) {
        //从http头信息取出 在filter定义的sessionstatus，判断是否是 timeout
        if(xhr.getResponseHeader("sessionstatus")=="timeout"){
            //从http头信息取出登录的url ＝ loginPath
            if(xhr.getResponseHeader("loginPath")){
                $.MsgBox.Alert("温馨提示", "会话过期，请重新登陆！");
                // //打会到登录页面
                // window.location.replace(xhr.getResponseHeader("loginPath"));
            }else{
                $.MsgBox.Alert("温馨提示", "请求超时请重新登陆！");
            }
        }
    });

//     $.ajax({
//         type: "post",
//         url: "/system/store/getStoreQrCode",
//         cache: false,  //禁用缓存
// //        headers: {"Content-type": "text/plain;charset=utf-8"},
//         dataType: "json",
//         success: function (result) {
//
//             if (result.success) {
//                 if(result.filePath){
//                     $("#main-menu li[name='activityManage']").show();
//                     $("#main-menu li[name='awardCancel']").show();
//                     $("#main-menu li[name='materialDownloading']").show();
//                 }else{
//                     $("#main-menu li[name='activityManage']").hide();
//                     $("#main-menu li[name='awardCancel']").hide();
//                     $("#main-menu li[name='materialDownloading']").hide();
//                 }
//
//             }
//         }
//     })
    navSelect();
    //增加默认点击事件
    $($("#main-menu").find("li>ul>li")[0]).trigger("click");

}(jQuery));

// 提示信息弹窗
(function () {
    $.MsgBox = {
        Alert: function (title, msg) {
            GenerateHtml("alert", title, msg);
            btnOk();  //alert只是弹出消息，因此没必要用到回调函数callback
            btnNo();
        },
        Confirm: function (title, msg, callback) {

            GenerateHtml("confirm", title, msg);
            btnOk(callback);
            btnNo();
        }
    };
    //生成Html
    var GenerateHtml = function (type, title, msg) {
        var _html = "";
        _html += '<div id="mb_box"></div><div id="mb_con"><span id="mb_tit">' + title + '</span>';
        _html += '<a id="mb_ico">✖</a><div id="mb_msg">' + msg + '</div><div id="mb_btnbox">';
        if (type == "alert") {
            _html += '<input id="mb_btn_ok" type="button" value="确定" />';
        }
        if (type == "confirm") {
            _html += '<input id="mb_btn_ok" type="button" value="确定" />';
            _html += '<input id="mb_btn_no" type="button" value="取消" />';
        }
        _html += '</div></div>';
        //必须先将_html添加到body，再设置Css样式
        $("body").append(_html);
        //生成Css
        GenerateCss();
    };

    //生成Css
    var GenerateCss = function () {
        $("#mb_box").css({ width: '100%', height: '100%', zIndex: '99999', position: 'fixed',
            filter: 'Alpha(opacity=60)', backgroundColor: 'black', top: '0', left: '0', opacity: '0.6'
        });
        $("#mb_con").css({ zIndex: '999999', width: '400px', position: 'fixed',
            backgroundColor: 'White', borderRadius: '15px'
        });
        $("#mb_tit").css({ display: 'block', fontSize: '14px', color: '#444', padding: '10px 15px',
            backgroundColor: '#DDD', borderRadius: '15px 15px 0 0',
            borderBottom: '3px solid #009BFE', fontWeight: 'bold',textAlign:"center",height:"43px"
        });
        $("#mb_msg").css({ padding: '20px', lineHeight: '20px',
            borderBottom: '1px dashed #DDD', fontSize: '13px',textAlign:"center"
        });
        $("#mb_ico").css({ display: 'block', position: 'absolute', right: '10px', top: '9px',
            border: '1px solid Gray', width: '18px', height: '18px', textAlign: 'center',
            lineHeight: '16px', cursor: 'pointer', borderRadius: '12px', fontFamily: '微软雅黑'
        });
        $("#mb_btnbox").css({ margin: '15px 0 10px 0', textAlign: 'center' });
        $("#mb_btn_ok,#mb_btn_no").css({ width: '85px', height: '30px', color: 'white', border: 'none' });
        $("#mb_btn_ok").css({ backgroundColor: '#168bbb' });
        $("#mb_btn_no").css({ backgroundColor: 'gray', marginLeft: '20px' });
        //右上角关闭按钮hover样式
        $("#mb_ico").hover(function () {
            $(this).css({ backgroundColor: '#3789DD', color: 'White' });
        }, function () {
            $(this).css({ backgroundColor: '#DDD', color: 'black' });
        });
        var _widht = document.documentElement.clientWidth;  //屏幕宽
        var _height = document.documentElement.clientHeight; //屏幕高
        var boxWidth = $("#mb_con").width();
        var boxHeight = $("#mb_con").height();
        //让提示框居中
        $("#mb_con").css({ top: (_height - boxHeight) / 2 + "px", left: (_widht - boxWidth) / 2 + "px" });
    };
    //确定按钮事件
    var btnOk = function (callback) {
        $("#mb_btn_ok").click(function () {
            $("#mb_box,#mb_con").remove();
            if (typeof (callback) == 'function') {
                callback();
            }
        });
    }
    //取消按钮事件
    var btnNo = function () {
        $("#mb_btn_no,#mb_ico").click(function () {
            $("#mb_box,#mb_con").remove();
        });
    }
})();

function navSelect() {
    $("#main-menu").off("click","li>ul>li");
    $("#main-menu").on("click","li>ul>li",function(){
        var navName = $.trim($(this).attr("name"));
        $(this).siblings().removeClass("bgStyle");
        $(this).parent().parent().siblings().find("li").removeClass("bgStyle");
        $(this).addClass("bgStyle");
        $("#contain_head_left").hide();
        // debugger;
        if(navName == "bossKanBan"){
            $("#contain_head_left").show();
            $.ajax({
                url: "/wechat/total/bossKanban",
                type: "post",
                dataType: "html",
                success: function (data) {
                    $("#contain_main").html("");
                    $("#contain_main").html(data);
                }
            })
        }else if(navName == "salesPer"){

            $.ajax({
                url: "/wechat/total/salesPer",
                type: "post",
                dataType: "html",
                success: function (data) {
                    $("#contain_main").html("");
                    $("#contain_main").html(data);
                }
            })
        }else if(navName == "smallProgram"){

            $.ajax({
                url: "/wechat/total/smallProgram",
                type: "post",
                dataType: "html",
                success: function (data) {
                    $("#contain_main").html("");
                    $("#contain_main").html(data);
                }
            })
        }else if(navName == "refundList"){

            $.ajax({
                url: "/refund/index",
                type: "post",
                dataType: "html",
                success: function (data) {
                    $("#contain_main").html("");
                    $("#contain_main").html(data);
                }
            })
        }

    });
}

function requestError(result) {
    if(result.success === false){
        if(result.code=="-999"){
            $.MsgBox.Alert("温馨提示", "请重新登录");
        }else{
            $.MsgBox.Alert("温馨提示",result.msg);
        }
    }
}

// 获取用户所有小程序
// function allSmallProgram() {
//     $.ajax({
//         url: "/system/store/findStoreList",
//         type:"post",
//         dataType: "json",
//         success: function (data) {
//             if(data.success){
//                 var arrData = data.storeList;
//                 if(arrData.length){
//                     $(".statistics").show();
//                 }else{
//                     $(".statistics").hide();
//                 }
//             }else{
//                 requestError(data);
//             }
//         }
//     })
// }

function timestampToTime(timestamp) {
    var date = new Date(timestamp * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = (date.getDate()<10?"0"+  date.getDate(): date.getDate())+ ' ';
    var h = (date.getHours()<10?"0"+  date.getHours(): date.getHours()) + ':';
    var m = (date.getMinutes()<10?"0"+  date.getMinutes(): date.getMinutes())  + ':';
    var s = date.getSeconds()<10?"0"+  date.getSeconds(): date.getSeconds() ;
    return Y+M+D+h+m+s;
}

function myDates(timestamp) {
    var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    var D = (date.getDate()<10?"0"+  date.getDate(): date.getDate());
    return Y+M+D;
}


//传入图片路径，返回base64
function getBase64(img){
    function getBase64Image(img,width,height) {//width、height调用时传入具体像素值，控制大小 ,不传则默认图像大小
        var canvas = document.createElement("canvas");
        canvas.width = width ? width : img.width;
        canvas.height = height ? height : img.height;

        var ctx = canvas.getContext("2d");
        ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
        var dataURL = canvas.toDataURL();
        return dataURL;
    }
    var image = new Image();
    image.crossOrigin = '';
    image.src = img;
    var deferred=$.Deferred();
    if(img){
        image.onload =function (){
            deferred.resolve(getBase64Image(image));//将base64传给done上传处理
        };
        return deferred.promise();//问题要让onload完成后再return sessionStorage['imgTest']
    }
}


//实现将项目的图片转化成base64
function convertImgToBase64(url, callback, outputFormat){
    var canvas = document.createElement('CANVAS'),
        ctx = canvas.getContext('2d'),
        img = new Image;
    img.crossOrigin = 'Anonymous';
    img.onload = function(){
        canvas.height = img.height;
        canvas.width = img.width;
        ctx.drawImage(img,0,0);
        var dataURL = canvas.toDataURL(outputFormat || 'image/png');
        callback.call(this, dataURL);
        canvas = null;
    };
    img.src = url;
}


//下载二维码
function downloadImg() {
    var img = document.getElementById('QRCodeImg');   // 获取要下载的图片
    var url = img.src;                              // 获取图片地址
    var a = document.createElement('a');            // 创建一个a节点插入的document
    var event = new MouseEvent('click');             // 模拟鼠标click点击事件
    a.download = 'storeQRCode' ;                   // 设置a节点的download属性值
    a.href = url;                                   // 将图片的src赋值给a节点的href
    a.dispatchEvent(event);                       // 触发鼠标点击事件
}


function returnIsNotInArray(arr, obj) {
    var arry = [];
    for (var key in obj) {
        if (key == "text") {
            obj[key] = obj[key] + "...";
        } else if (obj[key] === "") {
            obj[key] = "--";
        }
        arry.push(key);
    };
    for (var i = 0; i < arr.length; i++) {
        if (!isInArray(arry, arr[i])) {
            // if(contains(arrName,arr[i])){
            //     obj[arr[i]]=0;
            // }else{
            obj[arr[i]] = "--";
            // }

        }
    }
    return obj;
}

function isInArray(arr, value) {
    for (var i = 0; i < arr.length; i++) {
        if (value === arr[i]) {
            return true;
        }
    }
    return false;
}

function saveImage() {
    var userAgent = navigator.userAgent.toLowerCase();
    var flag =userAgent.match(/version\/([\d.]+).*safari/)?1:0;
    if (flag) {
        $.getScript("/static/plugins/html2canvas.min.js",function(){
            clickImgPng(true);
        });
    }else {
        $.getScript("/static/plugins/html2canvas.js", function () {
            clickImgPng(false);
        });
    }
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

}
