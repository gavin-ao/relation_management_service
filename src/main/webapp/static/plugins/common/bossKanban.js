/**
 * Created by 12045 on 2018/7/4.
 */
var wholeAppInfoId, wholeStartTime, wholeEndTime;
(function () {


    dateSelecteTime()

    // coreDataSel()


    // coreDataShow()
    // $("#navbarH").height($("#page-wrapper").height() - 80)

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
            changeTimeAfterDataChange()
        }
    });
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
        // tooltip: {
        //     trigger: 'item',
        //     formatter: "{a} <br/>{b}: {c} ({d}%)"
        // },
        series: [
            {
                name:'访问来源',
                type:'pie',
                radius: ['65%', '90%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '15',
                            fontWeight: 'bold'
                        },
                        formatter: "{b}\n {d}% "
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:[
                    {value:335, name:'直接访问'},
                    {value:310, name:'邮件营销'},
                    {value:234, name:'联盟广告'},
                    {value:135, name:'视频广告'},
                    {value:1548, name:'搜索引擎'}
                ]
            }
        ]
    };
    myChartPie.setOption(options);
    window.onresize = function () {
        myChartPie.resize();
    }

}


// 改变时间，数据及图表相继改变
function changeTimeAfterDataChange() {
    //平均客单价(元)
    averageUnitPrice()
    // 畅销系列 饼图展示
    salabilitySeries();
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
                // dateTimes = currentTime(new Date());
                // startTime = dateTimes
                // endTime = dateTimes
                laydateTime()
                break;
        }
        // $("#startTime").val(startTime);
        // $("#endTime").val(endTime);
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


//刷新数据
$("#refreshData").on("click", function () {
    changeTimeAfterDataChange();
})
