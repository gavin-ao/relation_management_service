var tab = '';
(function () {

    //获取表格数据
    tab = tablesData(tab);


    //搜索
    $("#selectBtn").off("click");
    $("#selectBtn").on("click", function () {
        var selectStatus = $("#selectStatus").val();
        var inputKeyword = $("#inputKeyword").val();
        var condition = {
            selectStatus: selectStatus,
            inputKeyword: inputKeyword.trim()
        };
        tab = tablesData(tab, condition)
    })
}());



function tablesData(datatable, condition) {

    if (datatable != '') {
        datatable.fnDestroy();         //销毁datatable
    }
    $("#example tbody").html("");
    var table = $('#example').dataTable({
        searching: false, //去掉搜索框方法一：百度上的方法，但是我用这没管用
        bLengthChange: false,   //去掉每页显示多少条数据方法
        processing: true,  //隐藏加载提示,自行处理
        serverSide: true,  //启用服务器端分页
        ordering: true,
        bDestory: true,
        aLengthMenu: [5, 10, 20, 50], //更改显示记录数选项
        iDisplayLength: 20,
        oLanguage: {    // 汉化
            sLengthMenu: "每页显示 _MENU_ 条",
            sZeroRecords: "没有找到符合条件的数据",
            sProcessing: "加载中...",
            sInfo: "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
            sInfoEmpty: "没有记录",
            sInfoFiltered: "(从 _MAX_ 条记录中过滤)",
            sSearch: "搜索：",
            oPaginate: {
                "sFirst": "首页",
                "sPrevious": "前一页",
                "sNext": "后一页",
                "sLast": "尾页"
            }
        },
        ajax: function (data, callback, settings) {
            //封装请求参数

            var param;
            param = {
                "pageNo": Math.floor(data.start / data.length) + 1,
                "pageSize": data.length
            };
            if (condition) {
                // param.stats = condition.selectStatus;
                param.keyword = condition.inputKeyword;
            }
            $.ajax({
                type: "post",
                url: "/refund/findRefundPage",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数?
                // headers: {"Content-type": "text/plain;charset=utf-8"},
                dataType: "json",
                success: function (result) {
                    if (result.success) {
                        var arry = ["appid", "storeId","outTradeNo", "outRefundNo", "commodityName", "totalFee", "refundFee", "refundFee","orderState","mobilePhone","createAt"];
                        var tabledata = [];
                        for (var i = 0; i < result.page.result.length; i++) {
                            result.page.result[i]["cid"] = data.start + i + 1;
                            result.page.result[i]["createAt"] = timestampToTime(result.page.result[i]["createAt"] / 1000);
                            tabledata.push(returnIsNotInArray(arry, result.page.result[i]));
                        }
                        setTimeout(function () {
                            //封装返回数据
                            var returnData = {};
                            returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                            returnData.recordsTotal = result.page.pageBean.totalNum;//返回数据全部记录
                            returnData.recordsFiltered = result.page.pageBean.totalNum;//后台不实现过滤功能，每次查询均视作全部结果
                            returnData.data = tabledata;//返回的数据列表
                            callback(returnData);
                            $("#tableNum").attr("data-tablenum", returnData.recordsTotal);
                            $('table tr td:not(:last-child)').mouseover(function () {
                                var val = $(this).text().trim();
                                $(this).attr({title: val});
                            });
                            // var html = '<span style="display: inline-block;margin-left: 20px;">共计 '+ result.hit +' 条</span>';
                            //
                            // $("#dataTables-example_length").find("span").remove();
                            // $("#dataTables-example_length").append(html)
                        }, 200);
                    } else {
                        requestError(result);
                    }
                    // downloaddata.search.pg.lmt = result.hit;
                }
            });
        },
        aoColumns: [
            {"data": "appid", "sClass": "hidden"},
            {"data": "storeId", "sClass": "hidden"},
            {"data": "cid"},
            {"data": "outTradeNo"},
            {"data": "outRefundNo"},
            {"data": "commodityName"},
            {"data": "totalFee"},
            {"data": "refundFee"},
            {"data": "mobilePhone"},
            {"data": "orderState"},
        ],
        aoColumnDefs: [
            // {
            //     "aTargets": [10],
            //     "mRender": function (data, type, full, meta) {
            //         // return "<button class='modify_btn btn btn-primary' style='padding: 2px 4px;margin-left: 8px;' >修改</button><button class='see_btn btn btn-primary' style='padding: 2px 4px;margin-left: 8px;' >查看</button>";
            //
            //         return "<button class='modify_btn btn btn-primary' style='padding: 2px 4px;margin-left: 8px;' >编辑</button>" +
            //             "<button class='del_btn btn btn-primary' style='padding: 2px 4px;margin-left: 8px;' >删除</button>"+
            //             "<button class='deliver_btn btn btn-primary' style='padding: 2px 4px;margin-left: 8px;' >派发</button>";
            //     }
            // },
            // {
            //     "bSortable": true,
            //     "aTargets": [4,5]
            // }

        ],
        'fnDrawCallback': function (table) {
            $("#example_paginate").append("<div style='display: inline-block; position: relative;top: -2px;'>到第 <input type='text' id='changePages' class='input-text' style='width:50px;height:27px'> 页 <a class='btn btn-default shiny' href='javascript:void(0);' id='dataTables-btn' style='text-align:center'>确认</a></div>");
            var oTable = $("#example").dataTable();
            $('#dataTables-btn').click(function (e) {
                if ($("#changePages").val() && $("#changePages").val() > 0) {
                    var redirectpage = $("#changePages").val() - 1;
                } else {
                    var redirectpage = 0;
                }
                oTable.fnPageChange(redirectpage);
            });

            $('#changePages').keyup(function (e) {
                if (e.keyCode == 13) {
                    if ($(this).val() && $(this).val() > 0) {
                        var redirectpage = $(this).val() - 1;
                    } else {
                        var redirectpage = 0;
                    }
                    oTable.fnPageChange(redirectpage);
                }
            })

        }
    });

    return table;
}
