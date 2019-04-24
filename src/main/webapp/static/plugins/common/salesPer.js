/**
 * Created by 12045 on 2018/7/4.
 */
var wholeAppInfoId, wholeStartTime, wholeEndTime;
(function () {

    laydateTime();
    dateSelecteTime();

    // coreDataSel()


    // coreDataShow()
    // $("#navbarH").height($("#page-wrapper").height() - 80)
// 下载图片
    saveImage();
    $($("#contain_main_head > div")[0]).find('input').trigger("click");
}())

function laydateTime() {
    lay('#version').html('-v' + laydate.v);
    var myDate = new Date();
    var time = currentTime(myDate);
//执行一个laydate实例
//     开始时间
    laydate.render({
        elem: '#startTime' //指定元素
        , value: time
        , done: function (value, date) {
            $(".contain_main_title .time1").html(value)
            wholeStartTime = value;
            if (!wholeEndTime) {
                wholeEndTime = value;
            }
            $("#startTime").val(wholeStartTime);
            $("#endTime").val(wholeEndTime);
            changeTimeAfterDataChange()
        }

    });

    // 结束时间
    laydate.render({
        elem: '#endTime' //指定元素
        , value: time
        , done: function (value, date) {
            $(".contain_main_title .time2").html(value)
            if (!wholeStartTime) {
                wholeStartTime = value;
            }
            wholeEndTime = value
            $("#startTime").val(wholeStartTime);
            $("#endTime").val(wholeEndTime);
            changeTimeAfterDataChange()
        }
    });
}
// 核心数据展示
function coreDataShow() {
    $.ajax({
        url: "/wechat/total/saleCoreData",
        type: "post",
        data: { startDate: wholeStartTime, endDate: wholeEndTime},
        dataType: "html",
        success: function (data) {
            $("#contain_main_data").html(data);
        }
    })
}
// 时间选择
function dateSelecteTime() {
    var startTime, endTime;
    $("#contain_main_head").off('click', "div>input");
    $("#contain_main_head").on('click', "div>input", function () {
        var inputs = $(this).parent().siblings().find("input");
        for (var i = 0; i < inputs.length; i++) {
            inputs[i].removeAttribute("checked");
        }
        $(this).attr("checked", true)
        className = $(this).attr("class");
        // $(".datePicker").css("display", "none");
        var dateTimes;

        switch (className) {
            case "todayTime":
                dateTimes = currentTime(new Date());
                startTime = dateTimes
                endTime = dateTimes
                break;
            case "yesterdayTime":
                dateTimes = currentTime(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
                endTime = dateTimes
                startTime = dateTimes
                break;
            case "Nearly7days":
                dateTimes = currentTime(new Date(new Date().getTime() - 7 * 24 * 60 * 60 * 1000));
                startTime = dateTimes
                var curdateTimes = currentTime(new Date());
                endTime = curdateTimes
                break;
            case "userdefined":
                // $(".datePicker").css("display", "block");
                dateTimes = currentTime(new Date());
                startTime = dateTimes
                endTime = dateTimes
                break;
        }
        $("#startTime").val(startTime);
        $("#endTime").val(endTime);
        $(".contain_main_title .time1").html(startTime)
        $(".contain_main_title .time2").html(endTime)
        wholeStartTime = startTime;
        wholeEndTime = endTime
        changeTimeAfterDataChange()

    })
}

// 获取当前时间
function currentTime(myDate) {
    var year = myDate.getFullYear();
    var mounth = (myDate.getMonth() + 1) > 9 ? (myDate.getMonth() + 1) : "0" + (myDate.getMonth() + 1);
    var date = myDate.getDate() > 9 ? myDate.getDate() : "0" + myDate.getDate();
    return year + "-" + mounth + "-" + date;
}

// 改变时间，数据及图表相继改变
function changeTimeAfterDataChange() {
    coreDataShow();
    //平均客单价(元)
    averageUnitPrice();
    // 畅销系列 饼图展示
    salabilitySeries();
    // 地图展示  订单分布
    orderDistribution();
    // 条形图展示  返利排行 TOP10
    rankings();

    // 传播图
    propagationTrajectory();
    // 下载图片
    saveImage();
}


//平均客单价(元)
function averageUnitPrice(urlName) {
    $.ajax({
        url: "/wechat/total/totalAverageUnitPrice",
        dataType: "json",
        type:"post",
        data: {startDate: wholeStartTime, endDate: wholeEndTime},
        success: function (data) {

            if(data.success){
                chartVerBarShow(data.data)

            }
        }
    })
}
//平均客单价(元) 柱状图
function chartVerBarShow(data) {
    var myChartLine = echarts.init(document.getElementById('main_bar'));
    var xData = [],showData=[];
    for(var i=0;i<data.length;i++){
        xData.push(data[i].groupTime);
        showData.push(data[i].averageNum.toFixed(2))
    }
    var option = {
        color: ['#3398DB'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '35',
            right: '4%',
            bottom: '3%',
            top: '15',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data:xData,
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '平均客单价(元)',
                nameLocation: 'center',
                nameGap: 47,
                nameTextStyle: {
                    color: "#8CA0B3",
                    fontSize: 10
                }
            }
        ],
        series: [
            {
                name: '直接访问',
                type: 'bar',
                barWidth: '60%',
                data: showData,
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                }
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChartLine.setOption(option);

    window.onresize = function () {
        myChartLine.resize();
    }
}

// 畅销系列 饼图展示
function salabilitySeries() {

    $.ajax({
        url: "/wechat/total/totalSalableCatg",
        dataType: "json",
        type:"post",
        data: { startDate: wholeStartTime, endDate: wholeEndTime},
        success: function (data) {

            if(data.success){
                chartPieShow(data.data)
            }
        }
    })
}
// 饼图展示  畅销系列
function chartPieShow(data) {
    var myChartPie = echarts.init(document.getElementById('main_pie'));
    var showData=[];
    for(var i=0;i<data.length;i++){
        showData.unshift({name:data[i].groupTime,value:data[i].countNum})
    }
    var options = {
        tooltip: {
            trigger: 'item',
            // formatter: "{a} <br/>{b}: {c} ({d}%)",
            formatter: function (parm) {
                var html = '';
                console.log(parm)
                html += '<div style="text-align: center;"><span style="font-size: 12px;display: inline-block;width: 100%; color:' + parm.color + '">' + parm.name + '</span><br/><span style="display: inline-block;width: 100%;font-size: 24px;color: #000;">' + parm.percent.toFixed(2) + '%</span></div>';
                return html;
            },
            backgroundColor: "#fff",
            position: ['42%', '40%']
        },
        series: [
            {
                name: '畅销系列',
                type: 'pie',
                radius: ['60%', '90%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: true,
                        position: 'inner',
                        formatter: "{d}% "
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:showData
            }
        ]
    };
    myChartPie.setOption(options);
    window.onresize = function () {
        myChartPie.resize();
    }

}

// 地图展示  订单分布
function orderDistribution() {
    $.ajax({
        url: "/wechat/total/totalAreaDistribution",
        dataType: "json",
        type:"post",
        data: {appInfoId: wholeAppInfoId, startDate: wholeStartTime, endDate: wholeEndTime},
        success: function (data) {

            if(data.success){
                chartMapShow(data.data)
            }
        }
    })
}
// 地图展示  订单分布
function chartMapShow(data) {
    var myChartPie = echarts.init(document.getElementById('main_map'));
    var showData=[];
    for(var i=0;i<data.length;i++){
        showData.unshift({name:data[i].groupTime,value:data[i].countNum})
    }
    var options = {
        tooltip: {
            show: true,
            formatter: function (params) {
                return params.name + '：' + params.data['value'] + '%'
            },
        },
        visualMap: {
            type: 'continuous',
            text: ['高', '低'],
            showLabel: true,
            seriesIndex: [0],
            orient: 'horizontal',
            min: 0,
            max: 10,
            inRange: {
                color: ['#edfbfb', '#b7d6f3', '#40a9ed', '#3598c1', '#215096']
            },
            textStyle: {
                color: '#000'
            },
            bottom: "bottom",
            left: 'left',
        },
        geo: {
            roam: true,
            map: 'china',
            label: {
                normal: {
                    show: true,
                    fontSize: 8,
                    position: "inside"
                }
            },
            itemStyle: {
                emphasis: {
                    areaColor: '#fff464'
                }
            },
            regions: [{
                name: '南海诸岛',
                value: 0,
                itemStyle: {
                    normal: {
                        opacity: 0,
                        label: {
                            show: true
                        }
                    }
                }
            }],
        },
        series: [{
            name: 'mapSer',
            type: 'map',
            roam: false,
            geoIndex: 0,
            data: showData
        }]
    };
    myChartPie.setOption(options);
    window.onresize = function () {
        myChartPie.resize();
    }
}


// 条形图展示  返利排行 TOP10
function rankings() {
    $.ajax({
        url: "/wechat/total/totalRebateBanking",
        dataType: "json",
        type:"post",
        data: { startDate: wholeStartTime, endDate: wholeEndTime},
        success: function (data) {

            if(data.success){
                chartLineBarShow(data.data)

            }
        }
    })
}
// 条形图展示  返利排行 TOP10
function chartLineBarShow(data) {
    var myChartPie = echarts.init(document.getElementById('main_Lbar'));
    var xData=[],showData=[];
    for(var i=0;i<data.length;i++){
        xData.unshift(data[i].groupTime);
        showData.unshift(data[i].averageNum);
    }
    var options = option = {
        color: ['#9BCC66'],
        tooltip: {
            trigger: 'axis'

        },
        grid: {
            // left: '4%',
            // right: '40',
            bottom: '2%',
            top: '5',
            containLabel: true
        },
        xAxis: {
            type: 'value'
        },
        yAxis: {
            type: 'category',
            data:xData,
            axisLabel: {
                show: true,
                interval: 0,
                rotate: 0,
                margin: 10,
                inside: false,
                textStyle: {
                    //color: '#fff',
                    fontWeight: '50'
                }
            },
            axisTick: {
                show: false
            }
        },
        series: [{
            name: '返利',
            type: 'bar',
            barMaxWidth: 40,
            barGap: "5%",
            label: {
                show: true,
                position: 'right'
            },
            data: showData
        }]
    };
    myChartPie.setOption(options);
    window.onresize = function () {
        myChartPie.resize();
    }
}




// 关系图  传播轨迹
function chartGraphShow(list,id) {
    console.log("2222--------       "+ Date.parse( new Date()))
//    传播轨迹图
    var links = [];
    var nodes = [];
    const ORDNUME = "order"
    var childArr = [];
    var num = 0;
    // console.log(JSON.stringify( list))
    // console.log(list)
    function xunhuans(list) {
        for (var i = 0; i < list.length; i++) {
            var strId = list[i].totalId + list[i].toUserId;
            var indexArr = $.inArray(strId,childArr)
            if(indexArr>=0){
                list[i]["id"] = indexArr;
            }else{
                childArr.push(strId)
                list[i]["id"] = childArr.length-1;
            }
            num++;
            if (list[i].childList && list[i].childList.length) {
                xunhuans(list[i].childList);
            }

        }
    }
    xunhuans(list);
    function xunhuan(list, num) {
        var number = num;
        for (var i = 0; i < list.length; i++) {
            nodes.push( {
                id:list[i].id,
                name: list[i].toUser,
                rela: list[i].rela,
                class: ORDNUME + number
            });
            num++;
            if (list[i].childList && list[i].childList.length) {
                for (var j = 0; j < list[i].childList.length; j++) {
                    links.push({source: list[i].id, target: list[i].childList[j].id});
                    // nodes.push({
                    //     id:list[i].childList[j].id,
                    //     name: list[i].childList[j].toUser,
                    //     rela: list[i].childList[j].rela,
                    //     class: ORDNUME + num
                    // });
                }
            }
            if (list[i].childList && list[i].childList.length) {
                xunhuan(list[i].childList, num);
                num--;
            } else {
                num--;
            }

        }
    }

    xunhuan(list, 0);


    var markerArr = []
    links.forEach(function (link) {
        for(var i=0;i<nodes.length;i++){
            if (!contains(markerArr, nodes[i].class)) {
                markerArr.push(nodes[i].class)
            }
            if(nodes[i].id == link.source){
                link.source = nodes[i].id;
            }
            if(nodes[i].id == link.target){
                link.target = nodes[i].id ;
            }

        }


    });

    function contains(arr, obj) {
        var i = arr.length;
        while (i--) {
            if (arr[i] === obj) {
                return true;
            }
        }
        return false;
    }

    var dataObj = {};
    dataObj.links = links;
    dataObj.nodes = nodes;

    var zoom = d3.behavior.zoom()
        .scaleExtent([0, 10])
        .on("zoom", zoomed);
    var width = $(id).width(),
        height = $(id).height();

    var force = d3.layout.force()//layout将json格式转化为力学图可用的格式
        .nodes(d3.values(dataObj.nodes))//设定节点数组
        .links(dataObj.links)//设定连线数组
        .size([width, height])//作用域的大小
        .linkDistance(150)//连接线长度
        // .linkStrength(0)   //连接线的强硬度  [0, 1]  值越大 强度越大
        .charge(-1500)//顶点的电荷数。该参数决定是排斥还是吸引，数值越小越互相排斥
        // .theta(0.5)  //设置限制值  值越小 限制越紧
        // .gravity(0.2)    //设置重力    值越大点越集中
        // .friction(0.5)   //摩擦系数[ 0, 1]    数值越小 速度损耗越大
        .on("tick", tick)//指时间间隔，隔一段时间刷新一次画面
        .start();//开始转换

    var svg = d3.select(id).append("svg")
        .attr("width", width)
        .attr("height", height)
        .call(zoom)

    function zoomed() {
        svg.selectAll("g").attr("transform",//svg下的g标签移动大小
            "translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
    }

//箭头
    var marker =
        svg.append("svg:defs").selectAll("marker")
            .data(markerArr)
            .enter().append("svg:marker")
            .attr("id", String)
            .attr("markerUnits", "userSpaceOnUse")
            .attr("viewBox", "0 -5 10 10")//坐标系的区域
            // .attr("refX",39)//箭头坐标
            .attr("refX", function (d) {
                return setArrowPlace(d)
            })//箭头坐标
            .attr("refY", 0)
            .attr("markerWidth", 12)//标识的大小
            // .attr("markerWidth", function (d) {
            //     return setArrowPlace(d)
            // })//标识的大小
            .attr("markerHeight", 12)
            // .attr("markerHeight", function (d) {
            //     return setArrowPlace(d)
            // })
            .attr("orient", "auto")//绘制方向，可设定为：auto（自动确认方向）和 角度值
            .attr("stroke-width", 1)//箭头宽度
            .append("path")
            .attr("d", "M0,-5L10,0L0,5")//箭头的路径
            // .attr('fill','#000000');//箭头颜色
            .style("fill", function (d) {

                return setColor(d);
            })


//设置连接线
    var edges_line = svg.append("g").selectAll(".edgepath")
        .data(dataObj.links)
        .enter()
        .append("path")
        .attr({
            'd': function (d) {
                return 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y
            },
            'class': 'edgepath',
            'id': function (d, i) {
                return 'edgepath' + i;
            }
        })
        .style("stroke", function (d) {
            // return setColor(d.type);
            return setColor(d.source.class);
        })

        .style("pointer-events", "none")
        .style("stroke-width", 0.5)//线条粗细
        // .attr("marker-end", "url(#resolved)" );//根据箭头标记的id号标记箭头
        .attr("marker-end", function (d) {
            // return "url(#" + d.type + ")";
            return "url(#" + d.source.class + ")";
        });//根据箭头标记的id号标记箭头

    var drag = force.drag()
        .on("dragstart", function (d, i) {
            d3.event.sourceEvent.stopPropagation(); //取消默认事件
            d.fixed = true;    //拖拽开始后设定被拖拽对象为固定
        })
        .on("dragend", function (d, i) {


        })
        .on("drag", function (d, i) {

        });
//圆圈
    var circle = svg.append("g").selectAll("circle")
        .data(force.nodes())//表示使用force.nodes数据
        .enter().append("circle")
        .style("fill", function (node) {
            // return setColor(node.type);
            return setColor(node.class);
        })
        .style('stroke', function (node) {
            // return setColor(node.type);
            return setColor(node.class);
        })
        .attr("r", function (node) {
            return setRadius(node.class);
        })//设置圆圈半径
        .on("click", function (node) {
            //单击时让连接线加粗
            edges_line.style("stroke-width", function (line) {
                if (line.source.name == node.name || line.target.name == node.name) {
                    return 2;
                } else {
                    return 0.5;
                }
            });
        })
        .on("mouseover", function (d, i) {

        })
        .on("mouseout", function (d, i) {

        })
        .on("dblclick", function (d, i) {
            d.fixed = false;
        })
        .call(drag)//将当前选中的元素传到drag函数中，使顶点可以被拖动


//圆圈的提示文字
    circle.append("svg:title")
        .text(function (node) {
            return node.name
        });
    var text = svg.append("g").selectAll("text")
        .data(force.nodes())
        //返回缺失元素的占位对象（placeholder），指向绑定的数据中比选定元素集多出的一部分元素。
        .enter()
        .append("text")
        .attr("dy", ".35em")
        .attr("text-anchor", "middle")//在圆圈中加上数据
        .style('fill', function (node) {
            return "#fff";

        })
        .style("font-size", function (d) {
            return setSize(d.class);
        })
        .attr('x', function (d) {
            var re_en = /[a-zA-Z]+/g;
            //如果是全英文，不换行
            if (d.name.match(re_en)) {
                if (d.name.length <= 4) {
                    d3.select(this).append('tspan')
                        .attr('x', 0)
                        .attr('y', 2)
                        .text(function () {
                            return d.name;
                        });
                } else {
                    var top = d.name.substring(0, 4);
                    var bot = d.name.substring(4, d.name.length);

                    d3.select(this).text(function () {
                        return '';
                    });

                    d3.select(this).append('tspan')
                        .attr('x', 0)
                        .attr('y', -7)
                        .text(function () {
                            return top;
                        });

                    d3.select(this).append('tspan')
                        .attr('x', 0)
                        .attr('y', 12)
                        .text(function () {
                            return bot;
                        });
                }
            }
            // //如果小于四个字符，不换行
            else if (d.name.length <= 4) {
                d3.select(this).append('tspan')
                    .attr('x', 0)
                    .attr('y', 2)
                    .text(function () {
                        return d.name;
                    });
            } else {
                var top = d.name.substring(0, 4);
                var bot = d.name.substring(4, d.name.length);

                d3.select(this).text(function () {
                    return '';
                });

                d3.select(this).append('tspan')
                    .attr('x', 0)
                    .attr('y', -7)
                    .text(function () {
                        return top;
                    });

                d3.select(this).append('tspan')
                    .attr('x', 0)
                    .attr('y', 12)
                    .text(function () {
                        return bot;
                    });
            }
        });


    function tick() {
        //path.attr("d", linkArc);//连接线
        circle.attr("transform", transform1);//圆圈
        text.attr("transform", transform2);//顶点文字


        edges_line.attr('d', function (d) {
            var path = 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y;
            return path;
        });


    }

//设置连接线的坐标,使用椭圆弧路径段双向编码
    function linkArc(d) {
        return 'M ' + d.source.x + ' ' + d.source.y + ' L ' + d.target.x + ' ' + d.target.y
    }

//设置圆圈和文字的坐标
    function transform1(d) {
        return "translate(" + d.x + "," + d.y + ")";
    }

    function transform2(d) {
        return "translate(" + (d.x) + "," + d.y + ")";
    }


// 保存为图片
    $("#imgSave").click(function () {
        // downloadImage();
        var canvas = $("#continer").find('svg')[0];
        saveSvgAsPng(canvas, 'test.png');
    });

    function downloadImage() {
        var serializer = new XMLSerializer();
        var source = serializer.serializeToString(svg.node());

        source = '<?xml version="1.0" standalone="no"?>\r\n' + source;
        var url = "data:image/svg+xml;charset=utf-8," + encodeURIComponent(source);
        document.write('<img src="' + url + '"/>');

        var canvas = document.createElement("canvas");
        canvas.width = width;
        canvas.height = height;

        var context = canvas.getContext("2d");
        var image = new Image;
        image.src = document.getElementsByTagName('img')[0].src;
        image.onload = function () {
            context.drawImage(image, 0, 0);

            var a = document.createElement("a");
            a.download = "fallback.png";
            a.href = canvas.toDataURL("image/png");
            a.click();
        };
    }

// 设置颜色
    function setColor(d) {
        var lineColor;
        var color = ["#FD485E", "#70AD47", "#FF9E00", "#4472C4", "#4EA2F0", "#E066FF"];
        // var color = ['#2ca4bf','#aacf44', '#ff9945', '#3ad1c5', '#f7cb4a','#f5a855','#a6d0e4','#DAEAF6','#0D8ABC','#124E96','#FF008E', '#749F83', '#CA8622', '#BDA29A', '#3fb27e','#97124b','#dc4444'];
        //根据关系的不同设置线条颜色
        for (var i = 0; i < markerArr.length; i++) {
            if (d == ("order" + i)) {
                if (i >= color.length) {
                    lineColor = color[color.length - 1]
                } else {
                    lineColor = color[i];
                }
                break
            } else {
                lineColor = color[color.length - 1]
            }
        }
        return lineColor;
    }

// 设置圆圈大小
    function setRadius(d) {
        var radius;
        for (var i = 0; i < markerArr.length; i++) {
            if (d == ("order" + i)) {
                radius = (40 - i * 4) >= 28 ? (40 - i * 4) : 28
                break;
            } else {
                radius = 28;
            }
        }
        return radius;
    }

// 设置字体大小
    function setSize(d) {
        var size;
        for (var i = 0; i < markerArr.length; i++) {
            if (d == ("order" + i)) {
                size = (20 - i * 4) >= 14 ? (20 - i * 4) : 14;
                break;
            } else {
                size = 14;
            }
        }
        return size;
    }

// 设置箭头大小
    function setArrowSize(d) {
        var lines = 12;
        for (var i = 0; i < markerArr.length; i++) {
            if (d == ("order" + i)) {
                lines = (16 - i * 2) >= 12 ? (16 - i * 2) : 12;
                break;
            } else {
                lines = 12;
            }
        }
        return lines;
    }

// 设置箭头位置
    function setArrowPlace(d) {
        var lines = 32;
        for (var i = 0; i < markerArr.length; i++) {
            if (d == ("order" + i)) {
                lines = (39 - i * 4) >= 32 ? (39 - i * 4) : 32;
                break
            } else {
                lines = 32;
            }
        }
        return lines;
    }
    console.log("3333--------       "+ Date.parse( new Date()))
}

// 关系图  传播轨迹
function propagationTrajectory() {
    var data = [{"totalId":"5bbfff1be6022c7d8cea6b1b","fromUserId":null,"fromUser":"0","toUserId":"5ba461f73903b84a1bf5fd82","toUser":"144","totalDate":1539309384000,"frequency":null,"childList":[{"totalId":"5bbfff1be6022c7d8cea6b1b","fromUserId":"5ba461f73903b84a1bf5fd82","fromUser":"144","toUserId":"5ba456cbe6022c493497d4d8","toUser":"病态者&lt;","totalDate":1539309384000,"frequency":null,"childList":[]},{"totalId":"5bbfff1be6022c7d8cea6b1b","fromUserId":"5ba461f73903b84a1bf5fd82","fromUser":"144","toUserId":"5bb81fa7e6022c7d8cea4f32","toUser":"Ll。","totalDate":1539309414000,"frequency":null,"childList":[{"totalId":"5bbfff6ae6022c7d8cea6b5c","fromUserId":"5bb81fa7e6022c7d8cea4f32","fromUser":"Ll。","toUserId":"5bc002743903b87d572a794e","toUser":"百合花开piapia","totalDate":1539310197000,"frequency":null,"childList":[]},{"totalId":"5bbfff6ae6022c7d8cea6b5c","fromUserId":"5bb81fa7e6022c7d8cea4f32","fromUser":"Ll。","toUserId":"5ba4721f3903b84a1bf627b5","toUser":"蜡笔小鑫?","totalDate":1539312956000,"frequency":null,"childList":[{"totalId":"5bc00d4e3903b87d572a8644","fromUserId":"5ba4721f3903b84a1bf627b5","fromUser":"蜡笔小鑫?","toUserId":"5ba45b073903b84a1bf5d6c4","toUser":"@?✨?杨静??@","totalDate":1539314922000,"frequency":null,"childList":[{"totalId":"5bc010c73903b87d572a8d6d","fromUserId":"5ba45b073903b84a1bf5d6c4","fromUser":"@?✨?杨静??@","toUserId":"5bc011de3903b87d572a8f24","toUser":"李鹏","totalDate":1539314235000,"frequency":null,"childList":[{"totalId":"5bc011de3903b87d572a8f27","fromUserId":"5bc011de3903b87d572a8f24","fromUser":"李鹏","toUserId":"5ba476ea3903b84a1bf62c5b","toUser":"洛倩","totalDate":1539314312000,"frequency":null,"childList":[{"totalId":"5bc026c4e6022c7d8cea9e3e","fromUserId":"5ba476ea3903b84a1bf62c5b","fromUser":"洛倩","toUserId":"5ba4811f3903b84a1bf634fc","toUser":"彦孑","totalDate":1539319626000,"frequency":null,"childList":[]},{"totalId":"5bc026c4e6022c7d8cea9e3e","fromUserId":"5ba476ea3903b84a1bf62c5b","fromUser":"洛倩","toUserId":"5bc0288ce6022c7d8cea9f4f","toUser":"?Yellow?","totalDate":1539319949000,"frequency":null,"childList":[]},{"totalId":"5bc026c4e6022c7d8cea9e3e","fromUserId":"5ba476ea3903b84a1bf62c5b","fromUser":"洛倩","toUserId":"5ba47c393903b84a1bf63152","toUser":"?Wendy?","totalDate":1539320529000,"frequency":null,"childList":[]}]},{"totalId":"5bc011de3903b87d572a8f27","fromUserId":"5bc011de3903b87d572a8f24","fromUser":"李鹏","toUserId":"5bc012cb3903b87d572a9093","toUser":"小?。","totalDate":1539314380000,"frequency":null,"childList":[]},{"totalId":"5bc011de3903b87d572a8f27","fromUserId":"5bc011de3903b87d572a8f24","fromUser":"李鹏","toUserId":"5bc021343903b87d572aa631","toUser":"艳子?","totalDate":1539318068000,"frequency":null,"childList":[]}]}]}]}]},{"totalId":"5bbfff1be6022c7d8cea6b1b","fromUserId":"5ba461f73903b84a1bf5fd82","fromUser":"144","toUserId":"5bbfff953903b87d572a764b","toUser":"Ice .C","totalDate":1539309462000,"frequency":null,"childList":[]}]},{"totalId":"5bbfff18e6022c7d8cea6b1a","fromUserId":null,"fromUser":"0","toUserId":"5bbfff17e6022c7d8cea6b17","toUser":"Parvus","totalDate":1539309402000,"frequency":null,"childList":[{"totalId":"5bbfff18e6022c7d8cea6b1a","fromUserId":"5bbfff17e6022c7d8cea6b17","fromUser":"Parvus","toUserId":"5bbfff593903b87d572a7616","toUser":"?","totalDate":1539309402000,"frequency":null,"childList":[]},{"totalId":"5bbfff18e6022c7d8cea6b1a","fromUserId":"5bbfff17e6022c7d8cea6b17","fromUser":"Parvus","toUserId":"5bc002903903b87d572a7960","toUser":"Liar","totalDate":1539310225000,"frequency":null,"childList":[]},{"totalId":"5bbfff18e6022c7d8cea6b1a","fromUserId":"5bbfff17e6022c7d8cea6b17","fromUser":"Parvus","toUserId":"5bc003c33903b87d572a7a23","toUser":"After Journey","totalDate":1539310531000,"frequency":null,"childList":[]}]}];

    chartGraphShow(data,"#main_graphs")
    // $.ajax({
    //     url: "/wechat/total/"+urlName,
    //     dataType: "json",
    //     type:"post",
    //     data: {appInfoId: wholeAppInfoId, startDate: wholeStartTime, endDate: wholeEndTime},
    //     success: function (data) {
    //
    //         if(data.success){
    //             chartLineShow(data.data)
    //
    //         }
    //     }
    // })
}

//刷新数据
$("#refreshData").on("click", function () {
    changeTimeAfterDataChange();
})
