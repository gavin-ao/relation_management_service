/**
 * Created by 12045 on 2018/7/4.
 */
var wholeAppInfoId, wholeStartTime, wholeEndTime;
(function () {

    laydateTime();
    dateSelecteTime();

    // coreDataSel()


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
            $("#startTime").val(wholeStartTime);
            $("#endTime").val(wholeEndTime);
            changeTimeAfterDataChange();
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
            wholeEndTime = value;
            $("#startTime").val(wholeStartTime);
            $("#endTime").val(wholeEndTime);
            changeTimeAfterDataChange()
        }
    });
}

// 核心数据选择
function coreDataSel() {
    $("#contain_main_data").off('click', "div");
    $("#contain_main_data").on('click', "div", function () {
        $(this).siblings().attr("class", "");
        $(this).attr("class", "selectData");
        var urlName = $(this).attr("data-iden");
        var title = $(this).attr("data-num");
        dataTrendDiagram(urlName, title);
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
            $($("#contain_main_data div")[0]).trigger("click");
        }
    })
}
//线形图 日活趋势
function chartLineShow(data,title) {
    var myChartLine = echarts.init(document.getElementById('main_Lines'));
    var xData = [],showData=[];
    for(var i=0;i<data.length;i++){
        xData.push(data[i].groupTime);
        showData.push(data[i].countNum);
    }
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
            data:xData
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
                name:title,
                type:'line',
                data:showData,
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
function chartFunnelShow(data) {
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
                name:'用户转化漏斗',
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
                            return parm.name+'：'+ parm.value;
                        }
                    }
                },
                data: data
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
    if ((data.newUserNum + data.oldUserNum) == 0) {
        var ratio = "0.00%";
        // $("#main_pie").html("无用户信息")
    } else {
        var ratio = (data.newUserNum / (data.newUserNum + data.oldUserNum) * 100).toFixed(2) + "%";


        var myChartPie = echarts.init(document.getElementById('main_pie'));
        var options = {
            title: {
                text: ratio,
                x: 'center',
                y: 'center',
                textStyle: {
                    fontWeight: 'normal',
                    color: '#0580f2',
                    fontSize: '30'
                }
            },
            color: ['rgba(176, 212, 251, 1)'],
            legend: {
                show: true,
                itemGap: 12,
                data: ['新用户', '老用户'],
                bottom: 20
            },

            series: [{
                name: 'Line 1',
                type: 'pie',
                clockWise: true,
                radius: ['50%', '66%'],
                itemStyle: {
                    normal: {
                        label: {
                            show: false
                        },
                        labelLine: {
                            show: false
                        }
                    }
                },
                hoverAnimation: false,
                data: [{
                    value: data.newUserNum,
                    name: '新用户',
                    itemStyle: {
                        normal: {
                            color: { // 完成的圆环的颜色
                                colorStops: [{
                                    offset: 0,
                                    color: '#00cefc' // 0% 处的颜色
                                }, {
                                    offset: 1,
                                    color: '#367bec' // 100% 处的颜色
                                }]
                            },
                            label: {
                                show: false
                            },
                            labelLine: {
                                show: false
                            }
                        }
                    }
                }, {
                    name: '老用户',
                    value: data.oldUserNum
                }]
            }]
        }
        myChartPie.setOption(options);
        window.onresize = function () {
            myChartPie.resize();
        }
    }

}

// 条形图展示  用户邀请排行
function chartLineBarShow(data) {
    var   color=['#F35352', '#F7CB4A', '#3FB27E', '#5182E4', '#9BCC66','#F6904F'];
    // var data = [
    //     {name:'云南省', value:10},
    //     {name:'湖北省', value:22},
    //     {name:'湖南省', value:33},
    //     {name:'河南省', value:44},
    //     {name:'河北省', value:50},
    //     {name:'安徽省', value:55},
    //     {name:'浙江省', value:66},
    //     {name:'山东省', value:77},
    //     {name:'广东省', value:88},
    //     {name:'北京市', value:100}
    // ];
    var yAxisData = [],seriesData=[];
    var myChartLineBar = echarts.init(document.getElementById('main_Lbar'));
    for(var i=0;i<data.length;i++){
        yAxisData.push(data[i].nickName);
        if(i>(color.length-1)){
            data[i].itemStyle = {color:color[color.length-1]}
        }else{
            data[i].itemStyle = {color:color[i]}
        }
        data[i].name=data[i].nickName;
        data[i].value=data[i].count_num;
        seriesData.unshift(data[i])
    }
    var option = {
        // title: {
        //     text: '世界人口总量',
        //     subtext: '数据来自网络'
        // },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'none'
            }
        },
        legend: {
            data: ['人数']
        },
        grid: {
            left: '2%',
            right: '3%',
            bottom: '2%',
            top: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01],
            minInterval: 1, //设置成1保证坐标轴分割刻度显示成整数。
            axisLabel: {
                color: "#666"
            },
            axisTick: {
                lineStyle: {
                    color: "#666"
                }
            },
            axisLine: {
                lineStyle: {
                    color: "#666"
                }
            }
        },
        yAxis: {
            type: 'category',
            data: yAxisData,
            axisLabel: {
                color: "#666"
            },
            axisTick: {
                lineStyle: {
                    color: "#666"
                }
            },
            axisLine: {
                lineStyle: {
                    color: "#666"
                }
            }
        },
        series: [
            {
                name: '人数',
                type: 'bar',
                data: seriesData,
                barMaxWidth: 40,
                barGap: "5%",
                label: {
                    show: true,
                    position: 'insideRight'
                }
            }
        ]
    };
    myChartLineBar.setOption(option);
    window.onresize = function () {
        myChartLineBar.resize();
    }
}

// 表格展示  用户留存率
function chartTableShow(setData) {
    // if(setData.length){
    //     var table = '<table><thead><tr><th>访问日期</th><th>新增用户</th><th>次日留存</th><th>7日留存</th><th>30日留存</th></tr></thead><tbody>';
    //
    //     console.log(setData)
    //     for(var i=0;i<setData.length;i++){
    //         var tempTd = '<tr>';
    //         for(var j=0;j<setData[i].length;j++){
    //             if(setData[i][j].flag&&j!=0){
    //                 tempTd += "<td class='sele'>"+setData[i][j].value+"</td>";
    //             }else{
    //                 tempTd += "<td>"+setData[i][j].value+"</td>";
    //             }
    //
    //         }
    //         tempTd+="</tr>";
    //         table += tempTd
    //     }
    //     table += "</tbody></table>";
    // }
    // $("#main_Table").html(table);
    Table().init({
        id:'main_Table',
        header:['访问日期','新增用户','次日留存','7日留存','30日留存'],
        data:setData
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
        // $(".datePicker").hide();
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

                // $(".datePicker").show();
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
        wholeEndTime = endTime;
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
    //线形图 日活趋势
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
function dataTrendDiagram(urlName, title) {
    $.ajax({
        url: "/wechat/total/" + urlName,
        dataType: "json",
        type: "post",
        data: {startDate: wholeStartTime, endDate: wholeEndTime},
        success: function (data) {
            $("#main_line").html("");
            if (data.success) {
                if (data.data.length) {
                    $("#main_line").attr("_echarts_instance_", "");
                    chartLineShow(data.data, title)
                }
                // else{
                //     var html = "<div style='text-align: center;font-size: 20px;color:red;margin-top: 20px;'>无数据</div>";
                //     $("#main_line").html(html);
                // }

            } else {
                requestError(data);
            }
        }
    })
}


// 漏斗图展示 用户转化漏斗
function userTransformation() {
    $.ajax({
        url: "/wechat/total/totalFunnelView",
        dataType: "json",
        type:"post",
        data: {startDate: wholeStartTime, endDate: wholeEndTime},
        success: function (data) {
            if(data.success){
                chartFunnelShow(data.data)

            }
        }
    })
}
// 新老用户成交占比 饼图展示
function salabilitySeries() {
    $.ajax({
        url: "/wechat/total/totalOldAndNewUser",
        dataType: "json",
        type:"post",
        data: {startDate: wholeStartTime, endDate: wholeEndTime},
        success: function (data) {

            if(data.success){
                chartPieShow(data)
            }
        }
    })
}

// 条形图展示  用户邀请排行
function rankings() {
    $.ajax({
        url: "/wechat/total/totalInviteRankView",
        dataType: "json",
        type:"post",
        data: {startDate: wholeStartTime, endDate: wholeEndTime},
        success: function (data) {

            if(data.success){
                chartLineBarShow(data.data)
            }
        }
    })
}
// 表格展示  用户留存率
function userRetentionRate() {
    // chartTableShow()
    $.ajax({
        url: "/wechat/total/totalUserRetainView",
        dataType: "json",
        type:"post",
        data: {startDate: wholeStartTime, endDate: wholeEndTime},
        success: function (data) {

            if(data.success){
                var tempData = data.data;
                var setData = [];
                for(var i=0;i<tempData.length;i++){
                    setData[i] = [];
                    setData[i][0] = tempData[i].groupTime;
                    setData[i][1] = tempData[i].nowGroup?tempData[i].nowGroup:0;
                    setData[i][2] = tempData[i].nextGroup?tempData[i].nextGroup:0;
                    setData[i][3] = tempData[i].sevenGroup?tempData[i].sevenGroup:0;
                    setData[i][4] = tempData[i].thirtyGroup?tempData[i].thirtyGroup:0;
                }
                // for(var i=0;i<setData.length;i++){
                //     var min1 = Infinity;
                //     var min2 = min1;
                //     var index1 = -1;
                //     var index2 = -1;
                //     for(var j=0;j<setData[i].length;j++){
                //         var item = setData[i][j];
                //         if(item < min1){
                //             min2 = min1;     //min2始终保持第二小的地位
                //             index2 = index1;
                //             min1 = item;
                //             index1 = j;
                //         }else if(item < min2){
                //             min2 = item;
                //             index2 = j;
                //         }
                //         setData[i][j] = {value: setData[i][j],flag:false}
                //     }
                //     setData[i][index1].flag = true;
                //     setData[i][index2].flag = true;
                // }
                chartTableShow(setData)
            }
        }
    })
}



//刷新数据
$("#refreshData").on("click", function () {
    changeTimeAfterDataChange();
})
