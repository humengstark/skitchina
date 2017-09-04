<%@ page import="com.skitchina.model.Waybill" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: humen
  Date: 2017/4/27
  Time: 21:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    List<Waybill> waybills = (List<Waybill>) session.getAttribute("waybills");
    Map users = (Map) session.getAttribute("users");
    int waybillNum = (Integer) session.getAttribute("waybillNum");
    int pagesOnCheckSubmits = (Integer) session.getAttribute("pagesOnCheckSubmits");
    int pagesNow = (Integer) session.getAttribute("pagesNow");
    int id = (Integer) session.getAttribute("id");
    double pagesAll1 = (double) waybillNum / 10;
    int pagesAll;
    if (pagesAll1 > waybillNum / 10) {
        pagesAll = waybillNum / 10 + 1;
    } else {
        pagesAll = waybillNum / 10;
    }
%>
<!--_meta 作为公共模版分离出去-->
<HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="Bookmark" href="favicon.ico" >
    <link rel="Shortcut Icon" href="favicon.ico" />
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${ctx}/resources/lib/html5.js"></script>
    <script type="text/javascript" src="${ctx}/resources/lib/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/static/h-ui/css/H-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/static/h-ui.admin/css/H-ui.admin.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/lib/Hui-iconfont/1.0.8/iconfont.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/static/h-ui.admin/skin/default/skin.css" id="skin" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/static/h-ui.admin/css/style.css" />
    <link href="${ctx}/resources/lib/dist/css/bootstrap.css" rel="stylesheet" type="text/css">
    <!--[if IE 6]>
    <script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script><![endif]-->
    <!--/meta 作为公共模版分离出去-->

    <title>订单管理</title>
    <style>
        .humeng {
            float: left;
            margin-left: 10px;
        }

        .changeColor {
            background-color:#F0F0F0;
        }
    </style>
</head>
<body >
<!--_header 作为公共模版分离出去-->
<header class="navbar-wrapper">
    <input id="pagesAll" type="hidden" value="<%=pagesAll%>">
    <input type="hidden" id="pagesNow" value="<%=pagesNow%>">>
    <%--<input id="consignor_company2" type="hidden" value="<%=consignor_company%>">--%>
    <%--<input id="consignee_company2" type="hidden" value="<%=consignee_company%>">--%>
    <div class="navbar navbar-fixed-top">
        <div class="container-fluid cl"> <a class="logo navbar-logo f-l mr-10 hidden-xs" href="#">晟开快线</a> <a class="logo navbar-logo-m f-l mr-10 visible-xs" href="/aboutHui.shtml">H-ui</a> <span class="logo navbar-slogan f-l mr-10 hidden-xs">3.0</span> <a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
            <nav class="nav navbar-nav">
                <ul class="cl">
                    <li class="dropDown dropDown_hover"><a href="javascript:;" class="dropDown_A"><i class="Hui-iconfont">&#xe600;</i> 新增 <i class="Hui-iconfont">&#xe6d5;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="javascript:;" onclick="article_add('添加资讯','article-add.html')"><i class="Hui-iconfont">&#xe616;</i> 资讯</a></li>
                            <li><a href="javascript:;" onclick="picture_add('添加资讯','picture-add.html')"><i class="Hui-iconfont">&#xe613;</i> 图片</a></li>
                            <li><a href="javascript:;" onclick="product_add('添加资讯','product-add.html')"><i class="Hui-iconfont">&#xe620;</i> 产品</a></li>
                            <li><a href="javascript:;" onclick="member_add('添加用户','member-add.html','','510')"><i class="Hui-iconfont">&#xe60d;</i> 用户</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
            <nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
                <ul class="cl">
                    <li>超级管理员</li>
                    <li class="dropDown dropDown_hover"> <a href="#" class="dropDown_A">admin <i class="Hui-iconfont">&#xe6d5;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="javascript:;" onClick="myselfinfo()">个人信息</a></li>
                            <li><a href="#">切换账户</a></li>
                            <li><a href="#">退出</a></li>
                        </ul>
                    </li>
                    <li id="Hui-msg"> <a href="#" title="消息"><span class="badge badge-danger">1</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
                    <li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
                            <li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
                            <li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
                            <li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
                            <li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
                            <li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</header>
<!--/_header 作为公共模版分离出去-->

<!--_menu 作为公共模版分离出去-->
<aside class="Hui-aside">

    <div class="menu_dropdown bk_2">
        <dl id="menu-product">
            <dt class="selected"><i class="Hui-iconfont">&#xe620;</i> 物流管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd style="display: block;">
                <ul>
                    <li><a href="${ctx}/management/getWaybills?pages=1&rows=10" title="订单管理">订单管理</a></li>
                    <li><a href="${ctx}/management/getAllUsers?pages=1&rows=10" title="用户管理">用户管理</a></li>
                    <li><a href="${ctx}/management/getAllStations" title="网点管理">网点管理</a></li>
                    <li><a href="${ctx}/management/getAllSubmits?pages=1" title="财务管理">财务管理</a></li>
                    <li><a href="${ctx}/version/upload" title="文件上传">文件上传</a></li>
                    <li><a href="${ctx}/management/getCheckSubmits?pages=1" title="对账管理">对账管理</a></li>
                    <li><a href="${ctx}/drawMoney/getDrawMoneys?pages=1" title="提现管理">提现管理</a></li>
                    <li><a href="${ctx}/management/getAllNotices?pages=1" title="公告管理">公告管理</a></li>
                    <li><a href="${ctx}/management/getAllClients?pages=1" title="商家信息">商家信息</a></li>
                </ul>
            </dd>
        </dl>
    </div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<!--/_menu 作为公共模版分离出去-->

<section class="Hui-article-box container-fluid" onclick="show1();" >
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 物流管理<span class="c-gray en">&gt;</span> 订单管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>

    <div class="Hui-article" >
        <article class="cl pd-20">
            <div class="text-c">
                <form class="Huiform" method="post" action="" target="_self">
                    <span class="btn-upload form-group">
					</span> <span class="select-box" style="width:150px">
					</span>
                </form>
            </div>
            <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l">
                <div>
                    <form action="${ctx}/management/getCheckSubmits">
                        <input type="hidden" name="pages" value="<%=pagesOnCheckSubmits%>">
                        <button type="submit">返回</button>
                    </form>
                </div>
            </span> <span class="r">共有数据：<strong><%=waybillNum%></strong> 条</span> </div>
            <div class="mt-10">
                <table class="table table-border table-bordered table-bg table-sort" style="table-layout: fixed">
                    <thead>
                    <tr class="text-c">
                        <th width="65">订单号</th>
                        <th width="50">发货人</th>
                        <th width="60">收货人</th>
                        <th width="50">代收款</th>
                        <th width="40">备注</th>
                        <th width="55">开单时间</th>
                        <th width="50">审核状态</th>
                        <th width="30"></th>
                    </tr>
                    </thead>
                    <tbody>

                    <%for (Waybill waybill:waybills){%>
                    <tr class="waybill">
                        <td><%=waybill.getWaybill_id()%>
                        </td>
                        <td><%=waybill.getConsignor_company()%>
                        </td>
                        <td><%=waybill.getConsignee_company()%>
                        </td>
                        <td><%=waybill.getPrice()%></td>
                        <td><%=waybill.getRemark()%>
                        </td>
                        <td><%=waybill.getTime().substring(0,19)%>
                        </td>
                        <%if (waybill.getCheck_condition()!=3){%>
                            <td style="color: red">未审核</td>
                        <%}else {%>
                            <td style="color: #00B83F">已审核</td>
                        <%}%>
                        <td>
                            <%if (waybill.getCheck_condition()!=3){%>
                            <form action="${ctx}/management/checkPass">
                                <input type="hidden" name="waybillId" value="<%=waybill.getId()%>">
                                <input type="hidden" name="pages" value="<%=pagesNow%>">
                                <input type="hidden" name="id" value="<%=id%>">
                                <input type="hidden" name="pagesOnCheckSubmits" value="<%=pagesOnCheckSubmits%>">
                                <button>审核通过</button>
                            </form></td>
                        <%}%>
                    </tr>
                    <%}%>
                    </tbody>
                </table>
            </div>
        </article>
        <div style="margin-left: 50px;">
            <%if (pagesNow!=1){%>
            <%--<a href="${ctx}/management/getWaybills?pages=<%=pagesNow-1%>&rows=10" class="pages">上一页</a>--%>
            <div class="humeng">
                <form id="last_form">
                    <input type="hidden" name="pages" value="<%=pagesNow-1%>">
                    <input type="hidden" name="pagesOnCheckSubmits" value="<%=pagesOnCheckSubmits%>">
                    <input type="hidden" name="id" value="<%=id%>">
                </form>
            </div>
            <div class="humeng">
                <button id="last_page">上一页</button>
            </div>
            <%}%>
            <div class="humeng">
                第<%=pagesNow%>页，共<%=pagesAll%>页
            </div>
            <%if (pagesNow!=pagesAll){%>
            <div class="humeng">
                <form id="next_form">
                    <input type="hidden" name="pages" value="<%=pagesNow+1%>">
                    <input type="hidden" name="pagesOnCheckSubmits" value="<%=pagesOnCheckSubmits%>">
                    <input type="hidden" name="id" value="<%=id%>">
                </form>
            </div>
            <div class="humeng">
                <button id="next_page">下一页</button>
            </div>
            <%}%>
            <div class="humeng">
                <form id="choosePage">
                    <div class="humeng">
                        <input type="number" style="width: 50px;" name="pages" id="pagesNum">
                        <input type="hidden" name="pagesOnCheckSubmits" value="<%=pagesOnCheckSubmits%>">
                        <input type="hidden" name="id" value="<%=id%>">
                    </div>
                    <div class="humeng">
                        <button id="choosePageButton">跳转</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <%--模态框--%>


</section>




<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="${ctx}/resources/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="${ctx}/resources/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/resources/static/h-ui.admin/js/H-ui.admin.page.js"></script>
<script src='${ctx}/resources/lib/dist/js/bootstrap.js'></script>
<!--/_footer /作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${ctx}/resources/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/resources/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/lib/laypage/1.2/laypage.js"></script>
<%--<script type="text/javascript">--%>
<%--$('.table-sort').dataTable({--%>
<%--"aaSorting": [[ 1, "desc" ]],//默认第几个排序--%>
<%--"bStateSave": true,//状态保存--%>
<%--"aoColumnDefs": [--%>
<%--//{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示--%>
<%--{"orderable":false,"aTargets":[0,6]}// 制定列不参与排序--%>
<%--]--%>
<%--});--%>
<%--</script>--%>
<script type="text/javascript">



    $(function () {

//        function submit() {
//            var form = document.getElementById("search");
//            form.submit();
//        }

        $("table tr").hover(function () {
            $(this).addClass("changeColor");
        },function () {
            $(this).removeClass("changeColor");
        })

        $('#click2').on("click", function () {
            var waybill_id2 = $('#waybill_id2').val();
            var consignorCompany = $('#consignorCompany').val();
            var consigneeCompany = $('#consigneeCompany').val();
            var form = $("#search");
            if (waybill_id2 == "" && consignorCompany == "" && consigneeCompany == "") {
                alert("不能为空");
            } else if (waybill_id2 != "") {
                if (waybill_id2.length==9) {
                    form.attr("action", "${ctx}/management/getWaybillByWaybillId")
                    form.attr("method", "get");
                    form.submit();
                }else {
                    alert("单号格式错误");
                }

            } else if (waybill_id2 == "" && consignorCompany != "" && consigneeCompany == "") {
                form.attr("action", "${ctx}/management/getWaybillsByConsignorCompany")
                form.attr("method", "get");
                form.submit();
            } else if (waybill_id2 == "" && consigneeCompany != "" && consignorCompany == "") {
                form.attr("action", "${ctx}/management/getWaybillsByConsigneeCompany")
                form.attr("method", "get");
                form.submit();
            } else if (waybill_id2 == "" && consignorCompany != "" && consigneeCompany != "") {
                form.attr("action", "${ctx}/management/getWaybillsByConsignorAndConsignee")
                form.attr("method", "get");
                form.submit();
            }
        });

        $('#getWaybillsNotSubmit').on("click",function () {
            var form = $('#getWaybillsNotSubmit2');
            form.submit();
        })

        $('#getWaybillsReceiveAndNotSubmit').on("click",function () {
            var form = $("#getWaybillsReceiveAndNotSubmit2");
            form.submit();
        })

        $('#last_page').on("click",function () {
            var last_form = $('#last_form');
            last_form.attr("action", "${ctx}/management/getWaybillsByCheckSubmit");
            last_form.attr("method", "get");
            last_form.submit();
        })

        $('#next_page').on("click",function () {
            var next_form = $('#next_form');
            next_form.attr("action", "${ctx}/management/getWaybillsByCheckSubmit");
            next_form.attr("method", "get");
            next_form.submit();
        })

        $('#choosePageButton').on("click", function () {
            var choosePage = $('#choosePage');
            var pagesAll = $('#pagesAll').val();
            var pagesNow = $('#pagesNum').val();
            if (pagesNow<1) {
                alert("页数不能小于1");
            }else if (pagesNow>pagesAll) {
                alert("页数不能大于总页数");
            }else {
                choosePage.attr("action", "${ctx}/management/getWaybillsByCheckSubmit");
                choosePage.attr("method", "get");
                choosePage.submit();
            }
        });


        $("#click_button").on("click",function () {
            var submit_form = $("#update_form");
            submit_form.submit();
        });

        $('.wy-set').click(function () {
            $('#myModal').modal({
                keyboard: true,
                backdrop:true
            });


            $('.waybill_id').attr('value', $(this).parent().parent().children('.waybill_id').text().trim());
            $('.consignor_company').attr('value', $(this).parent().parent().children('.consignor_company').text().trim());
            $('.consignor_tel').attr('value', $(this).parent().parent().children('.consignor_tel').text().trim());
            $('.origin').attr('value', $(this).parent().parent().children('.origin').text().trim());
            $('.consignor_address').attr('value', $(this).parent().parent().children('.consignor_address').text().trim());
            $('.consignee_tel').attr('value', $(this).parent().parent().children('.consignee_tel').text().trim());
            $('.consignee_company').attr('value', $(this).parent().parent().children('.consignee_company').text().trim());
            $('.destination').attr('value', $(this).parent().parent().children('.destination').text().trim());
            $('.consignee_address').attr('value', $(this).parent().parent().children('.consignee_address').text().trim());
            $('.price').attr('value', $(this).parent().parent().children('.price').text().trim());
            $('.freight').attr('value', $(this).parent().parent().children('.freight').text().trim());
            $('.number').attr('value', $(this).parent().parent().children('.number').text().trim());
            $('.remark').attr('value', $(this).parent().parent().children('.remark').text().trim());

        })
    })
</script>
<!--/请在上方写此页面业务相关的脚本-->

</body>
</html>
