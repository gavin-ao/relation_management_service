var tab = '';
(function () {

    //获取表格数据
    tab = tablesData(tab,"","");


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

    // $('#example tbody').on('click','td', function (e){
    //     e.preventDefault();
    //     //todo:获取详情
    //     // var storeId = $(this).parents('tr').find("td")[0].innerHTML.trim();
    //     // var outRefundNo = $(this).parents('tr').find("td")[1].innerHTML.trim();
    //     getDetaiInfo(storeId,outRefundNo);
    // })


    // var table = $('#example').DataTable();
    // $('#example tbody').on('click', 'tr', function () {
    //     debugger;
    //     var data = table.row( this ).data();
    //     getDetaiInfo(data);
    // } );
}());


function getDetaiInfo(storeId,outRefundNo) {
    $.ajax({
        url: "/refund/detail/"+storeId+"/"+outRefundNo,
        type: "post",
        dataType: "html",
        success: function (data) {
            $("#detailContainer").html("");
            $("#detailContainer").html(data);
            // var imageDom = $('#picList');
            clickToZoomIn($(".pimg"));
        }
    })

}
function tablesData(datatable, condition, parentCode) {

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
        iDisplayLength: 12,
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
                url: "/catalog/findCatalogPage/"+parentCode,//如果parentCode是空，则是第一级的目录
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数?
                // headers: {"Content-type": "text/plain;charset=utf-8"},
                dataType: "json",
                success: function (result) {
                    if (result.success) {
                        var arry = ["catgCode","catgName","updateAt","createAt","state","stateDesc","fmtCreateTime","fmtUpdateTime"];
                        var tabledata = [];
                        for (var i = 0; i < result.page.result.length; i++) {
                            result.page.result[i]["cid"] = data.start + i + 1;
                            result.page.result[i]["createAt"] = timestampToTime(result.page.result[i]["createAt"] / 1000);
                            switch (result.page.result[i]["state"]) {
                                case 0:result.page.result[i]["stateDesc"] = '无效';
                                       break;
                                case 1:result.page.result[i]["stateDesc"] = '有效';
                                       break;
                                default:'有效';
                            }
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
                        }, 200);
                    } else {
                        requestError(result);
                    }
                }
            });
        },
        aoColumns: [
            {"data": "catgCode"},
            {"data": "catgName"},
            {"data": "fmtCreateTime"},
            {"data": "fmtUpdateTime"},
            {"data": "stateDesc"},
        ],
        aoColumnDefs: [
            {
                "aTargets": [5],
                "mRender": function (data, type, full, meta) {

                    return "<span><a style='padding: 2px 4px;margin-left: 8px;'>编辑</a></span>" +
                           "<span><a style='padding: 2px 4px;margin-left: 8px;'>设为无效</a></span>"+
                           "<span><a style='padding: 2px 4px;margin-left: 8px;'>描述属性</a></span>"+
                           "<span><a style='padding: 2px 4px;margin-left: 8px;'>规格属性</a></span>";
                }
            },
            {
                "bSortable": false,
                "aTargets": [1, 2, 3, 4,5]
            }

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
