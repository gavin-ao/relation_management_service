/**
 * Created by 12045 on 2018/7/4.
 */
var wholeAppInfoId, wholeStartTime, wholeEndTime;
(function () {


    dateSelecteTime()

    // coreDataSel()


    coreDataShow();
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

// 核心数据选择
function coreDataSel() {
    $("#contain_main_data").off('click', "div");
    $("#contain_main_data").on('click', "div", function () {
        console.log(2222)
        $(this).siblings().attr("class", "");
        $(this).attr("class", "selectData");
        var urlName = $(this).attr("data-iden");
        var title = $(this).attr("data-num");
        // dataTrendDiagram(urlName, title);
    })
}
// 核心数据展示
function coreDataShow(appInfoId) {
    $.ajax({
        url: "/wechat/total/coreData",
        type: "post",
        data: { startDate: wholeStartTime, endDate: wholeEndTime},
        dataType: "html",
        success: function (data) {
            $("#contain_main_data").html(data);
            coreDataSel();
            // 下载图片
            saveImage();
            // changeTimeAfterDataChange();
        }
    })
}
//线形图 日活趋势
function chartLineShow() {
    var myChartLine = echarts.init(document.getElementById('main_Lines'));
    var option =  {
        tooltip: {
            trigger: 'item'
        },
        grid: {
            left: '3%',
            right: '5%',
            bottom: '3%',
            top:'10',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['2016-06月','2016-07月','2016-08月','2016-09月','2016-10月','2016-11月','2016-12月']
        },
        yAxis: {
            type: 'value',
            name: 'kg',
            axisTick: {
                show: false
            }

        },
        series: [
            {
                name:'采收量',
                type:'line',
                data:[120, 132, 101, 134, 90, 230, 210],
                symbolSize:10,
                itemStyle: {
                    normal: {
                        color: '#fd744e',
                    }
                },
                markLine : {
                    data : [
                        {type : 'average', name : '平均值'}
                    ]
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

// 漏斗图展示 用户转化漏斗
function chartFunnelShow() {
    var myChartLine = echarts.init(document.getElementById('main_funnel'));
    var option =  {
        color:['#A4CF77', '#3C72DD', '#F2C132', '#F37D30', '#3BA072'],
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c}%"
        },
        calculable: true,
        series: [
            {
                name:'漏斗图',
                type:'funnel',
                top: 10,
                //x2: 80,
                bottom: 10,
                left:10,
                right:120,
                label: {
                    normal: {
                        show: true,
                        position: 'right',
                        formatter: function (parm) {
                            console.log(parm)
                            return parm.name+'人数：'+ parm.value +'\n转化率：'+parm.value +'%';
                        }
                    }
                },
                data: [
                    {value: 60, name: '访问'},
                    {value: 40, name: '咨询'},
                    {value: 20, name: '订单'},
                    {value: 80, name: '点击'},
                    {value: 100, name: '展现'}
                ]
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChartLine.setOption(option);

    window.onresize = function () {
        myChartLine.resize();
    }
}

// 饼图展示  新老用户成交占比
function chartPieShow(data) {
    var color = ['#A4CF77', '#3C72DD', '#F2C132', '#F37D30', '#3BA072'];
    var myChartPie = echarts.init(document.getElementById('main_pie'));
    var options = {
        color:['#A4CF77', '#3C72DD', '#F2C132', '#F37D30', '#3BA072'],
        tooltip: {
            trigger: 'item',
            formatter:function (parm) {
                return parm.name +': '+parm.percent+"%"
            }
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                hoverAnimation: false,
                label: {
                    normal: {
                       formatter:function (parm) {
                           return parm.name +': '+parm.percent+"%"
                       }
                    }
                },
                data: [
                    {name:'新用户',value:24},
                    {name:'老用户',value:40}
                ]
            }
        ]
    };
    myChartPie.setOption(options);
    window.onresize = function () {
        myChartPie.resize();
    }

}

// 条形图展示  用户邀请排行
function chartLineBarShow() {
    var   color=['#F35352', '#F7CB4A', '#3FB27E', '#5182E4', '#9BCC66','#F6904F'];
    var data = [
        {name:'云南省', value:10},
        {name:'湖北省', value:22},
        {name:'湖南省', value:33},
        {name:'河南省', value:44},
        {name:'河北省', value:50},
        {name:'安徽省', value:55},
        {name:'浙江省', value:66},
        {name:'山东省', value:77},
        {name:'广东省', value:88},
        {name:'北京市', value:100}
    ];
    var yAxisData = [],seriesData=[];
    var myChartPie = echarts.init(document.getElementById('main_Lbar'));
    for(var i=0;i<data.length;i++){
        yAxisData.push(data[i].name);
        if(i>(color.length-1)){
            data[i].itemStyle = {color:color[color.length-1]}
        }else{
            data[i].itemStyle = {color:color[i]}
        }
        seriesData.push(data[i])
    }
    var options = option = {
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
            top:'5',
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
            data: yAxisData,
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
                    formatter: function (v) {
                        console.log(v)
                        return v.value;
                    },
                    color: "#000"
                }
            },
            data:seriesData
        }]
    };
    myChartPie.setOption(options);
    window.onresize = function () {
        myChartPie.resize();
    }
}

// 表格展示  用户留存率
function chartTableShow() {
    Table().init({
        id:'main_Table',
        header:['访问日期','新增用户','次日留存','7日留存','30日留存'],
        data:[
            ['2018年09月01日',1200,0.60,0.25,0.12],
            ['2018年09月02日',2400,0.60,0.25,0.12],
            ['2018年09月03日',1000,0.60,0.25,0.12],
            ['2018年09月04日',1500,0.60,0.25,0.12],
            ['2018年09月05日',600,0.60,0.25,0.12],
            ['2018年09月06日',800,0.60,0.25,0.12],
            ['2018年09月07日',1200,0.60,0.25,0.12],
        ]
    });
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

// 改变时间，数据及图表相继改变
function changeTimeAfterDataChange() {
    //线形图 日活趋势
    dailyLiveTrend();
    // 漏斗图展示 用户转化漏斗
    userTransformation();
    // 新老用户成交占比 饼图展示
    salabilitySeries();
    // 条形图展示  用户邀请排行
    rankings();
    // 表格展示  用户留存率
    userRetentionRate();

}

//线形图 日活趋势
function dailyLiveTrend(urlName) {
    chartLineShow()
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

// 漏斗图展示 用户转化漏斗
function userTransformation() {
    // chartFunnelShow()
    $.ajax({
        url: "/wechat/total/totalUserRetainView ",
        dataType: "json",
        type:"post",
        data: {startDate: wholeStartTime, endDate: wholeEndTime},
        success: function (data) {
            console.log(data);
            if(data.success){
                chartLineShow(data.data)

            }
        }
    })
}
// 新老用户成交占比 饼图展示
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

// 条形图展示  用户邀请排行
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
// 表格展示  用户留存率
function userRetentionRate() {
    chartTableShow()
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
