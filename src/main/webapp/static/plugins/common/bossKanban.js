/**
 * Created by 12045 on 2018/7/4.
 */
var wholeAppInfoId, wholeStartTime, wholeEndTime;
(function () {

    laydateTime();
    dateSelecteTime()

    // coreDataSel()

    // coreDataShow()
    // $("#navbarH").height($("#page-wrapper").height() - 80)
// 下载图片
    clickImgPng();
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
        url: "/wechat/total/bossCoreData",
        type: "post",
        data: { startDate: wholeStartTime, endDate: wholeEndTime},
        dataType: "html",
        success: function (data) {
            $("#contain_main_data").html(data);
            // 下载图片
            saveImage();
            // changeTimeAfterDataChange();
        }
    })
}
//平均客单价(元) 柱状图
function chartVerBarShow() {
    var myChartLine = echarts.init(document.getElementById('main_bar'));
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
                data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
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
                data: [100, 520, 2000, 3340, 3900, 3300, 2200],
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                },
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChartLine.setOption(option);

    window.onresize = function () {
        myChartLine.resize();
    }
}


// 饼图展示  畅销系列
function chartPieShow(data) {

    var myChartPie = echarts.init(document.getElementById('main_pie'));
    var options = {
        tooltip: {
            trigger: 'item',
            // formatter: "{a} <br/>{b}: {c} ({d}%)",
            formatter: function (parm) {
                var html = '';
                console.log(parm)
                html += '<div style="text-align: center;"><span style="font-size: 12px;display: inline-block;width: 100%; color:' + parm.color + '">' + parm.name + '</span><br/><span style="display: inline-block;width: 100%;font-size: 24px;color: #000;">' + parm.percent + '%</span></div>';
                return html;
            },
            backgroundColor: "#fff",
            position: ['42%', '40%']
        },
        series: [
            {
                name: '访问来源',
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
                data: [
                    {value: 335, name: '直接访问'},
                    {value: 310, name: '邮件营销'},
                    {value: 234, name: '联盟广告'},
                    {value: 135, name: '视频广告'},
                    {value: 1548, name: '搜索引擎'}
                ]
            }
        ]
    };
    myChartPie.setOption(options);
    window.onresize = function () {
        myChartPie.resize();
    }

}

// 地图展示  订单分布
function chartMapShow() {
    var myChartPie = echarts.init(document.getElementById('main_map'));
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
            data: [
                {
                    name: '北京',
                    value: 5.3
                },
                {
                    name: '天津',
                    value: 3.8
                },
                {
                    name: '上海',
                    value: 4.6
                },
                {
                    name: '重庆',
                    value: 3.6
                },
                {
                    name: '河北',
                    value: 3.4
                },
                {
                    name: '河南',
                    value: 3.2
                },
                {
                    name: '云南',
                    value: 1.6
                },
                {
                    name: '辽宁',
                    value: 4.3
                },
                {
                    name: '黑龙江',
                    value: 4.1
                },
                {
                    name: '湖南',
                    value: 2.4
                },
                {
                    name: '安徽',
                    value: 3.3
                },
                {
                    name: '山东',
                    value: 3.0
                },
                {
                    name: '新疆',
                    value: 1
                },
                {
                    name: '江苏',
                    value: 3.9
                },
                {
                    name: '浙江',
                    value: 3.5
                },
                {
                    name: '江西',
                    value: 2.0
                },
                {
                    name: '湖北',
                    value: 2.1
                },
                {
                    name: '广西',
                    value: 3.0
                },
                {
                    name: '甘肃',
                    value: 1.2
                },
                {
                    name: '山西',
                    value: 3.2
                },
                {
                    name: '内蒙古',
                    value: 3.5
                },
                {
                    name: '陕西',
                    value: 2.5
                },
                {
                    name: '吉林',
                    value: 4.5
                },
                {
                    name: '福建',
                    value: 2.8
                },
                {
                    name: '贵州',
                    value: 1.8
                },
                {
                    name: '广东',
                    value: 3.7
                },
                {
                    name: '青海',
                    value: 0.6
                },
                {
                    name: '西藏',
                    value: 0.4
                },
                {
                    name: '四川',
                    value: 3.3
                },
                {
                    name: '宁夏',
                    value: 0.8
                },
                {
                    name: '海南',
                    value: 1.9
                },
                {
                    name: '台湾',
                    value: 0.1
                },
                {
                    name: '香港',
                    value: 0.1
                },
                {
                    name: '澳门',
                    value: 0.1
                }
            ]
        }]
    };
    myChartPie.setOption(options);
    window.onresize = function () {
        myChartPie.resize();
    }
}

// 条形图展示  返利排行 TOP10
function chartLineBarShow() {
    var myChartPie = echarts.init(document.getElementById('main_Lbar'));
    var options = option = {
        color: ['#9BCC66'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            },
            formatter: "{b} <br> 合格率: {c}%"
        },
        /*legend: {
         data: [date]
         },*/
        grid: {
            // left: '4%',
            // right: '40',
            bottom: '2%',
            top: '5',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01],
            min: 0,
            max: 100,
            interval: 20,
            axisLabel: {
                formatter: '{value}%',
                textStyle: {
                    //color: '#fff',
                    fontWeight: '80'
                }
            }
        },
        yAxis: {
            type: 'category',
            data: ['云南省', '湖北省', '湖南省', '河南省', '河北省', '安徽省', '浙江省', '山东省', '广东省', '北京市'],
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
            type: 'bar',
            label: {
                normal: {
                    show: true,
                    position: "right",
                    // formatter: '{c,}',
                    formatter: function (v) {
                        var val = v.data;
                        if (val == 0) {
                            return '';
                        }
                        return val;
                    },
                    color: "#000"
                }
            },
            data: [10, 22, 33, 44, 50, 55, 66, 77, 88, 100]
        }]
    };
    myChartPie.setOption(options);
    window.onresize = function () {
        myChartPie.resize();
    }
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
    //平均客单价(元)
    averageUnitPrice()
    // 畅销系列 饼图展示
    salabilitySeries();
    // 地图展示  订单分布
    orderDistribution();
    // 条形图展示  返利排行 TOP10
    rankings();
}

//平均客单价(元)
function averageUnitPrice(urlName) {
    chartVerBarShow()
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
// 畅销系列 饼图展示
function salabilitySeries() {
    chartPieShow()
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
// 地图展示  订单分布
function orderDistribution() {
    chartMapShow()
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

// 条形图展示  返利排行 TOP10
function rankings() {
    chartLineBarShow()
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
