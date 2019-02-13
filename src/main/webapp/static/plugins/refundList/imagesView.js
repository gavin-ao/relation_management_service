function clickToZoomIn(imagesDom) {
    imagesDom.click(function(){
        var _this = $(this);//将当前的pimg元素作为_this传入函数
        imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
    });
}

function imgShow(outerdiv, innerdiv, bigimg, _this){
    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
    $(bigimg).attr("src", src);//设置#bigimg元素的src属性
   debugger;
    /*获取当前点击图片的真实大小，并显示弹出层及大图*/
    $("<img/>").attr("src", src).load(function(){
        var windowW = $(window).width();//获取当前窗口宽度
        var windowH = $(window).height();//获取当前窗口高度
        var realWidth = this.width;//获取图片真实宽度
        var realHeight = this.height;//获取图片真实高度
        var imgWidth, imgHeight;
        var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

        if(realHeight>windowH*scale) {//判断图片高度
            imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放
            imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度
            if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度
                imgWidth = windowW*scale;//再对宽度进行缩放
            }
        } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度
            imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放
            imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度
        } else {//如果图片真实高度和宽度都符合要求，高宽不变
            imgWidth = realWidth;
            imgHeight = realHeight;
        }
        $(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放

        var w = (windowW-imgWidth)/2;//计算图片与窗口左边距
        var h = (windowH-imgHeight)/2;//计算图片与窗口上边距
        $(innerdiv).css({"top":h, "left":w});//设置#innerdiv的top和left属性
        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
    });

    $(outerdiv).click(function(){//再次点击淡出消失弹出层
        $(this).fadeOut("fast");
    });
}

function createImagesBrowser(imagesDom){
    //给jquery添加调用函数 主要是为了满足用户对Jquery书写习惯
    $.fn.extend({
        createImgWin:function(){
            var ImgWin=new scrollWin(this);
        }

    })
    //调用方式
    imagesDom.createImgWin();
}


var scrollWin=function(target){
    this.picList=$(target).find("img");
    var that=this;
    //创建背景层
    function createBackPopup(){
        var height = $(window).height()
        //<!---黑色背景------->
        var divBack = $(".container");

        if(divBack.length < 1) {
            divBack = $("<div></div>");
            divBack.attr("class", "container");
            divBack.width("100%");
            divBack.css("position", "absolute").css("top", "0").css("z-index", "10").css("background-color", "black").css("width","100%")
            divBack.height(height);
            divBack.css("display", "none");
            var tip=$("<div style='width:100%;margin-top:10px'></div>")
            var topTip=$("<div class='topTip' style='background-color:#434343;color:#fff;text-align: center;' ></div>")
            var picNumber=that.picList.length;
            topTip.text("0/"+picNumber);
            topTip.height(20)
            topTip.width(40)
            topTip.css("border-radius","10px").css("margin","5px auto")
            tip.append(topTip)

            divBack.append(tip)
            var imgDiv = $("<div class='imgDiv' style='display: flex;justify-content: flex-start;width:100%;'></div>");
            divBack.append(imgDiv);
            $("body").append(divBack);
        }
        return divBack;
    }
    this.backPopup=createBackPopup();

    //缓存的图片索引
    this.picIndex=[];
    //绑定点击事件
    this.bindClick(this.picList);
    //绑定滑动事件
    that.bindMove();
}

scrollWin.prototype={
    //点击事件
    bindClick:function(list){
        var that=this;
        $.each(list, function(index, item) {
            //点击事件
            $(item).on("click", function() {

                var src = $(this).attr("src")
                //创建图片层
                that.createImgPopup(src,index);
            })
        });

    },
    //滑动事件
    bindMove:function(){
        //绑定滑动事件
        var startX,startY, that=this;
        //开始触摸
        this.backPopup.on("touchstart", function(e) {
            e.preventDefault();
            startX = e.originalEvent.changedTouches[0].pageX, startY = e.originalEvent.changedTouches[0].pageY;
        });
        //滑动结束
        this.backPopup.on("touchend", function(e) {

            e.preventDefault();
            var moveEndX,X,index,picNumber;
            moveEndX = e.originalEvent.changedTouches[0].pageX,
                index=$(".imgDiv").find("img[class=current]").attr("index");
            index=parseInt(index);
            if(isNaN(index))
                return;
            X = moveEndX - startX;
            picNumber=that.picList.length;
            //滑动事件判断
            if(X > 0) {
                //索引加1 right to left
                index--;
                if(index<1)
                    index=picNumber
                $(".topTip").text(index+"/"+picNumber);
                //判断之前是否已经加载该图片
                var nextimg= $(".imgDiv").find("img[index="+index+"]")
                if(!that.isInArray(that.picIndex,index)){
                    var imgEle=that.picList[index-1]
                    //获取下一张图片的路径
                    var src= $(imgEle).attr("src")
                    nextimg =new Image(); //创建一个Image对象，实现图片的预下载
                    nextimg.src = src;
                    // nextimg = $("<img index="+index+" />")
                    $(nextimg).attr("index", index);
                    //当前标识
                    $(nextimg).attr("class", "current");
                    that.picIndex.push(index);

                    nextimg.onload=function(){

                        //之前的图片隐藏
                        that.hiddenImg(index)
                        //添加元素
                        $(".imgDiv").append(nextimg)
                        //元素调整
                        that.resizeImg($(nextimg));
                    }
                }//已经存在
                else{

                    //之前的图片隐藏
                    that.hiddenImg(index)
                    nextimg.css("display","block")
                    nextimg.attr("class", "current");
                }

            } else if(X < 0) {
                //索引加1 right to left
                index++;
                if(index>picNumber)
                    index=1;

                $(".topTip").text(index+"/"+picNumber);
                //判断之前是否已经加载该图片
                var nextimg= $(".imgDiv").find("img[index="+index+"]")
                if(!that.isInArray(that.picIndex,index)){

                    var imgEle=that.picList[index-1]
                    //获取下一张图片的路径
                    var src= $(imgEle).attr("src")
                    nextimg =new Image(); //创建一个Image对象，实现图片的预下载
                    nextimg.src = src;
                    // nextimg = $("<img index="+index+" />")
                    $(nextimg).attr("index", index);
                    //当前标识
                    $(nextimg).attr("class", "current");
                    that.picIndex.push(index);

                    nextimg.onload=function(){

                        //之前的图片隐藏
                        that.hiddenImg(index)
                        //添加元素
                        $(".imgDiv").append(nextimg)
                        //元素调整
                        that.resizeImg($(nextimg));
                    }
                }
                //已经存在
                else{
                    //之前的图片隐藏
                    that.hiddenImg()
                    nextimg.css("display","block")
                    nextimg.attr("class", "current");
                }

            }else{
                that.hiddenPop()
            }

        });
    },
    //工具方法 判断是否已经存在在索引列表中
    isInArray:function(arr,value){
        for(var i = 0; i < arr.length; i++){
            if(value === arr[i]){
                return true;
            }
        }
        return false;
    },
    //隐藏指定图片
    hiddenImg:function(index){
        //图片隐藏
        var otherimg= $(".imgDiv img[index!="+index+"]")
        $.each(otherimg, function(index ,item) {
            $(item).css("display","none")
            $(item).attr("class", "other");
        });
    },
    //创建图片元素
    createImgPopup:function(src,index){
        var that=this;
        var height = $(window).height()
        var divBack= $(".container")
        divBack.css("display", "block")
        //索引
        var picNumber=that.picList.length;

        index=index+1;
        //顶部编号
        $(".topTip").text(index+"/"+picNumber);
        //判断是否已经有该元素
        if(!that.isInArray(that.picIndex,index)){
            //<!----图片层----->
            var img = $("<img index="+index+" />")
            img.attr("src", src);
            img.attr("class", "current");

            that.picIndex.push(index);
            img[0].onload=function(){
                //添加元素
                $(".imgDiv").append(img)
                that.hiddenImg(index)
                //元素调整
                that.resizeImg(img);
            }
        }
        //如果已经存在
        else{
            that.hiddenImg(index);
            //只显示该元素
            var curimg= $(".imgDiv").find("img[index="+index+"]")
            $(curimg).css("display","block")
            $(curimg).attr("class", "current");

        }

    },
    //隐藏整个框架
    hiddenPop:function(){
        this.backPopup.css("display", "none")
    },
    //图片大小位置按比例调整
    resizeImg:function(img){
        var height = $(window).height()

        var rate = img.height() / img.width();
        var imgH, imgW;


        if(img.width() > $(window).width()) {
            imgW = $(window).width();
        } else {
            imgW = img.width()
        }
        imgH = imgW * rate;
        if(imgH>height-20){
            imgH=height-20;
            imgW=imgH/rate;
            if(imgW> $(window).width())
                imgW= $(window).width()
        }
        img.height(imgH)
        img.width(imgW)
        //垂直居中
        img.css("margin-top", (height-40- imgH) / 2)

    }
}
