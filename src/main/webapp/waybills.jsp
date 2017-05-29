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
    double pagesAll1 = (double) waybillNum / 10;
    int pagesAll;
    if (pagesAll1 > waybillNum / 10) {
        pagesAll = waybillNum / 10 + 1;
    } else {
        pagesAll = waybillNum / 10;
    }
    int pagesNow = (Integer) session.getAttribute("pagesNow");
    int rows = (Integer) session.getAttribute("rows");
    int type = (Integer) session.getAttribute("type");
    String consignor_company3 = "no", consignee_company3 = "no";
    if (!session.getAttribute("consignor_company").equals("no")) {
        consignor_company3 = (String) session.getAttribute("consignor_company");
    }
    if (!session.getAttribute("consignee_company").equals("no")) {
        consignee_company3 = (String) session.getAttribute("consignee_company");
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
    <input id="type" type="hidden" value="<%=type%>">
    <input id="pagesAll" type="hidden" value="<%=pagesAll%>">
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
                <form id="search">
                <input type="hidden" name="rows" value="10">
                <input type="hidden" name="pages" value="1">
                <input type="text" name="waybill_id2" width="60" id="waybill_id2" placeholder="请输入订单号">
                <input type="text" name="consignorCompany" width="60" id="consignorCompany" placeholder="请输入发货人">
                <input type="text" name="consigneeCompany" width="60" id="consigneeCompany" placeholder="请输入收货人">
                </form><button class="btn btn-danger radius" id="click2"><i class="Hui-iconfont">&#xe6e2;</i> 搜索</button>
                <button type="button" id="getWaybillsNotSubmit">查询未交账订单</button>
                <button type="button" id="getWaybillsReceiveAndNotSubmit">查询已收款未交账订单</button>
                <form action="${ctx}/management/getWaybillsReceiveAndNotSubmit" id="getWaybillsReceiveAndNotSubmit2" method="get">
                    <input type="hidden" name="pages" value="1">
                </form>
                <form id="getWaybillsNotSubmit2" action="${ctx}/management/getWaybillsNotSubmit" method="get">
                    <input type="hidden" name="pages" value="1"><input type="hidden" name="rows" value="10">
                </form>
            </span> <span class="r">共有数据：<strong><%=waybillNum%></strong> 条</span> </div>
            <div class="mt-10">
                <table class="table table-border table-bordered table-bg table-sort" style="table-layout: fixed">
                    <thead>
                    <tr class="text-c">
                        <th width="65">订单号</th>
                        <th width="50">发货人</th>
                        <th width="65">发货人电话</th>
                        <th width="60">发货地</th>
                        <th width="60" id="consignor">发货地址</th>
                        <th width="60">收货人</th>
                        <th width="65">收货人电话</th>
                        <th width="60">收货地</th>
                        <th width="60" id="consignee">收货地址</th>
                        <th width="50">代收款</th>
                        <th width="40">运费</th>
                        <th width="30">件数</th>
                        <th width="90">状态</th>
                        <th width="50">支付方式</th>
                        <th width="40">备注</th>
                        <th width="50">开单员</th>
                        <th width="55">开单时间</th>
                        <th width="30"></th>
                        <th width="30"></th>
                    </tr>
                    </thead>
                    <tbody>

                    <%for (Waybill waybill:waybills){%>
                        <%if (waybill.getWaybill_id()==0){%>
                            <td>该订单不存在</td>
                        <%}else {%>
                    <tr class="waybill">
                        <td class="waybill_id"><%=waybill.getWaybill_id()%></td>
                        <td class="consignor_company"><%=waybill.getConsignor_company()%></td>
                        <td class="consignor_tel"><%=waybill.getConsignor_tel()%></td>
                        <td class="origin"><%=waybill.getOrigin()%></td>
                        <td class="consignor_address"><%=waybill.getConsignor_address()%></td>
                        <td class="consignee_company"><%=waybill.getConsignee_company()%></td>
                        <td class="consignee_tel"><%=waybill.getConsignee_tel()%></td>
                        <td class="destination"><%=waybill.getDestination()%></td>
                        <td class="consignee_address"><%=waybill.getConsignee_address()%></td>
                    <%--<form action="${ctx}/management/updateFreightOrPrice" method="get">--%>
                        <%--<input type="hidden" name="type" value="<%=type%>">--%>
                        <%--<input type="hidden" name="id" value="<%=waybill.getId()%>">--%>
                        <%--<input type="hidden" name="consignor_company" value="<%=consignor_company3%>">--%>
                        <%--<input type="hidden" name="consignee_company" value="<%=consignee_company3%>">--%>
                        <%--<input type="hidden" name="pages" value="<%=pagesNow%>">--%>

                        <%if (waybill.getPayway()==0){%>
                            <%if (waybill.getCondition()<4){%>
                                <td class="price" style="color:red;"><%=waybill.getPrice()%></td>
                                <td class="freight" style="color: red"><%=waybill.getFreight()%></td>
                                <%}else {%>
                                <td class="price"><%=waybill.getPrice()%></td>
                                <td class="freight"><%=waybill.getFreight()%></td>
                            <%}%>
                        <%}else {%>

                        <%if (waybill.getConsignee_mark()!=2&&waybill.getConsignee_mark()!=3){%>
                            <td class="price" style="color: red"><%=waybill.getPrice()%></td>
                        <%}else {%>
                            <td class="price"><%=waybill.getPrice()%></td>
                        <%}%>
                        <%if (waybill.getConsignor_mark()!=2&&waybill.getConsignor_mark()!=3){%>
                            <td class="freight" style="color: red"><%=waybill.getFreight()%></td>
                        <%}else {%>
                            <td class="freight"><%=waybill.getFreight()%></td>
                        <%}%>
                        <%}%>
                    <%--</form>--%>
                        <td class="number"><%=waybill.getNumber()%></td>
                        <input type="hidden" name="condition2" class="condition2" value="<%=waybill.getCondition()%>">
                        <%if (waybill.getInvalid()==1){%>
                        <td style="color: red">作废</td>
                        <%}else {%>
                        <form id="update_condition" action="${ctx}/management/updateCondition" method="post">
                            <input type="hidden" name="pages" value="<%=pagesNow%>">
                            <input type="hidden" name="id" value="<%=waybill.getId()%>">
                            <%if (waybill.getCondition()!=6){%>
                            <td>下单<select name="condition" id=<%=waybill.getId()%>>
                                <option value="0">下单</option>
                                <option value="1">运输</option>
                                <option value="2">到达</option>
                                <option value="3">收款</option>
                                <option value="4">交账</option>
                                <option value="5">完成</option>
                            </select>
                                <button type="submit">修改</button>
                            </td>
                            <%} else if (waybill.getCondition()==6){%>
                            <td style="color: green">结束</td>
                            <%}%>
                        </form>
                        <%}%>
                        <input type="hidden" class="payway" value="<%=waybill.getPayway()%>">
                        <%if (waybill.getPayway()==0){%>
                        <td>到付
                            <a href="${ctx}/management/updatePayway?waybill_id=<%=waybill.getWaybill_id()%>&pagesNow=<%=pagesNow%>&type=<%=type%>&consignor_company=<%=consignor_company3%>&consignee_company=<%=consignee_company3%>">修改</a>
                        </td>
                        <%} else if (waybill.getPayway() == 1) {%>
                        <td>寄付
                            <a href="${ctx}/management/updatePayway?waybill_id=<%=waybill.getWaybill_id()%>&pagesNow=<%=pagesNow%>&type=<%=type%>&consignor_company=<%=consignor_company3%>&consignee_company=<%=consignee_company3%>">修改</a>
                        </td>
                        <%}%>
                        <td class="remark"><%=waybill.getRemark()%></td>
                        <td class="user_name"><%=users.get(waybill.getUser_id())%></td>
                        <td class="waybill_time"><%=waybill.getTime().substring(0,19)%></td>
                        <td>
                            <a href="${ctx}/management/deleteWaybillById?pages=<%=pagesNow%>&id=<%=waybill.getId()%>&type=<%=type%>&consignor_company=<%=consignor_company3%>&consignee_company=<%=consignee_company3%>">删除</a>
                            <a href="${ctx}/management/invalidWaybillById?pages=<%=pagesNow%>&id=<%=waybill.getId()%>&type=<%=type%>&consignor_company=<%=consignor_company3%>&consignee_company=<%=consignee_company3%>" style="color: red">作废</a>
                        </td>
                        <td>
                            <button  type="button" class="wy-set">
                                修改
                            </button>

                        </td>
                    </tr>
                            <%}%>
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
            <input type="hidden" name="rows" value="<%=rows%>">
            <%if (!consignor_company3.equals("no")) {%>
            <input type="hidden" name="consignorCompany" value="<%=consignor_company3%>">
            <%}%>
            <%if (!consignee_company3.equals("no")){%>
            <input type="hidden" name="consigneeCompany" value="<%=consignee_company3%>">
            <%}%>
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
        <%--<a href="${ctx}/management/getWaybills?pages=<%=pagesNow+1%>&rows=10" class="pages">下一页</a>--%>
            <div class="humeng">
        <form id="next_form">
            <input type="hidden" name="pages" value="<%=pagesNow+1%>">
            <input type="hidden" name="rows" value="<%=rows%>">
            <%if (!consignor_company3.equals("no")) {%>
            <input type="hidden" name="consignorCompany" value="<%=consignor_company3%>">
            <%}%>
            <%if (!consignee_company3.equals("no")){%>
            <input type="hidden" name="consigneeCompany" value="<%=consignee_company3%>">
            <%}%>
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
            <input type="hidden" name="rows" value="10">
            <%if (!consignor_company3.equals("no")) {%>
            <input type="hidden" name="consignorCompany" value="<%=consignor_company3%>">
            <%}%>
            <%if (!consignee_company3.equals("no")){%>
            <input type="hidden" name="consigneeCompany" value="<%=consignee_company3%>">
            <%}%>
            </div>
            <div class="humeng">
            <button id="choosePageButton">跳转</button>
            </div>
        </form>
            </div>
        </div>
    </div>


    <%--模态框--%>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="height: auto">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">修改订单</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" id="update_form" action="${ctx}/management/updateWaybillByWaybillId" method="post">
                        <input type="hidden" name="type" value="<%=type%>">
                        <input type="hidden" name="consignor_company" value="<%=consignor_company3%>">
                        <input type="hidden" name="consignee_company" value="<%=consignee_company3%>">
                        <input type="hidden" id="waybill_id" name="waybill_id" class="waybill_id">
                        <input type="hidden" name="pagesNow" value="<%=pagesNow%>">
                        <!--这里加输入框-->
                        <div class="form-group col-lg-12">
                            <label for="consignor_company" class='col-lg-4  control-label'>发货人</label>
                            <div class='col-lg-8'>
                                <input type="text" id='consignor_company' class='form-control consignor_company' name="consignor_company1">
                            </div>
                        </div>
                        <!--这里是第二个输入框-->
                        <div class="form-group col-lg-12">
                            <label for="consignor_tel"  class='col-lg-4 control-label'>发货人电话</label>
                            <div class='col-lg-8'>
                                <input type="text" class="form-control consignor_tel" id="consignor_tel" name="consignor_tel">
                            </div>
                        </div>
                        <!--这里你可以继续加-->
                        <div class="form-group col-lg-12">
                            <label for="origin"  class='col-lg-4 control-label'>始发地</label>
                            <div class='col-lg-8'>
                                <input type="text" class="form-control origin" id="origin" name="origin">
                            </div>
                       </div>

                        <div class="form-group col-lg-12">
                            <label for="consignor_address"  class='col-lg-4 control-label'>发货地址</label>
                            <div class='col-lg-8'>
                                <input type="text" class="form-control consignor_address" id="consignor_address" name="consignor_address">
                            </div>
                        </div>

                        <div class="form-group col-lg-12">
                            <label for="consignee_company"  class='col-lg-4 control-label'>收货人</label>
                            <div class='col-lg-8'>
                                <input type="text" class="form-control consignee_company" id="consignee_company" name="consignee_company1">
                            </div>
                        </div>

                        <div class="form-group col-lg-12">
                            <label for="consignee_tel"  class='col-lg-4 control-label'>收货人电话</label>
                            <div class='col-lg-8'>
                                <input type="text" class="form-control consignee_tel" id="consignee_tel" name="consignee_tel">
                            </div>
                        </div>

                        <div class="form-group col-lg-12">
                            <label for="destination"  class='col-lg-4 control-label'>收货地</label>
                            <div class='col-lg-8'>
                                <input type="text" class="form-control destination" id="destination" name="destination">
                            </div>
                        </div>

                        <div class="form-group col-lg-12">
                            <label for="consignee_address"  class='col-lg-4 control-label'>收货地址</label>
                            <div class='col-lg-8'>
                                <input type="text" class="form-control consignee_address" id="consignee_address" name="consignee_address">
                            </div>
                        </div>

                        <div class="form-group col-lg-12">
                            <label for="price"  class='col-lg-4 control-label'>代收款</label>
                            <div class='col-lg-8'>
                                <input type="text" class="form-control price" id="price" name="price">
                            </div>
                        </div>

                        <div class="form-group col-lg-12">
                            <label for="freight"  class='col-lg-4 control-label'>运费</label>
                            <div class='col-lg-8'>
                                <input type="text" class="form-control freight" id="freight" name="freight">
                            </div>
                        </div>

                        <div class="form-group col-lg-12">
                            <label for="number"  class='col-lg-4 control-label'>件数</label>
                            <div class='col-lg-8'>
                                <input type="text" class="form-control number" id="number" name="number">
                            </div>
                        </div>

                        <div class="form-group col-lg-12">
                            <label for="remark"  class='col-lg-4 control-label'>备注</label>
                            <div class='col-lg-8'>
                                <input type="text" class="form-control remark" id="remark" name="remark">
                            </div>
                        </div>

                    </form>
                </div>
                <div class="modal-footer">
                    <!--这是取消按钮-->
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <!--这是确定，可以加点击事件，用ajax传数据,用id去取input里面的值value-->
                    <button type="button" class="btn btn-primary"  id="click_button">保存</button>

                </div>
            </div>
        </div>
    </div>
    </div>


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
            var type = $('#type').val();
            if (type==0) {
                last_form.attr("action", "${ctx}/management/getWaybills");
                last_form.attr("method", "get");
                last_form.submit();
            }else if (type==1) {
                last_form.attr("action", "${ctx}/management/getWaybillsByConsignorCompany");
                last_form.attr("method", "get");
                last_form.submit();
            }else if (type==2) {
                last_form.attr("action", "${ctx}/management/getWaybillsByConsigneeCompany");
                last_form.attr("method", "get");
                last_form.submit();
            }else if (type==3) {
                last_form.attr("action", "${ctx}/management/getWaybillsByConsignorAndConsignee");
                last_form.attr("method", "get");
                last_form.submit();
            }else if (type==5) {
                last_form.attr("action", "${ctx}/management/getWaybillsNotSubmit");
                last_form.attr("method", "get");
                last_form.submit();
            }else if (type==6) {
                last_form.attr("action", "${ctx}/management/getWaybillsReceiveAndNotSubmit");
                last_form.attr("method", "get");
                last_form.submit();
            }
        })

        $('#next_page').on("click",function () {
            var next_form = $('#next_form');
            var type = $('#type').val();
            var page = $('#choosePage').val();
            if (type==0) {
                next_form.attr("action", "${ctx}/management/getWaybills");
                next_form.attr("method", "get");
                next_form.submit();
            }else if (type==1) {
                next_form.attr("action", "${ctx}/management/getWaybillsByConsignorCompany");
                next_form.attr("method", "get");
                next_form.submit();
            }else if (type==2) {
                next_form.attr("action", "${ctx}/management/getWaybillsByConsigneeCompany");
                next_form.attr("method", "get");
                next_form.submit();
            }else if (type==3) {
                next_form.attr("action", "${ctx}/management/getWaybillsByConsignorAndConsignee");
                next_form.attr("method", "get");
                next_form.submit();
            }else if (type==5) {
                next_form.attr("action", "${ctx}/management/getWaybillsNotSubmit");
                next_form.attr("method", "get");
                next_form.submit();
            }else if (type==6) {
                next_form.attr("action", "${ctx}/management/getWaybillsReceiveAndNotSubmit");
                next_form.attr("method", "get");
                next_form.submit();
            }
        })

        $('#choosePageButton').on("click", function () {
            var choosePage = $('#choosePage');
            var type = $('#type').val();
            var pagesAll = $('#pagesAll').val();
            var pagesNow = $('#pagesNum').val();
            if (pagesNow<1) {
                alert("页数不能小于1");
            }else if (pagesNow>pagesAll) {
                alert("页数不能大于总页数");
            }else {
                if (type == 0) {
                    choosePage.attr("action", "${ctx}/management/getWaybills");
                    choosePage.attr("method", "get");
                    choosePage.submit();
                } else if (type == 1) {
                    choosePage.attr("action", "${ctx}/management/getWaybillsByConsignorCompany");
                    choosePage.attr("method", "get");
                    choosePage.submit();
                } else if (type == 2) {
                    choosePage.attr("action", "${ctx}/management/getWaybillsByConsigneeCompany");
                    choosePage.attr("method", "get");
                    choosePage.submit();
                } else if (type == 3) {
                    choosePage.attr("action", "${ctx}/management/getWaybillsByConsignorAndConsignee");
                    choosePage.attr("method", "get");
                    choosePage.submit();
                } else if (type == 5) {
                    choosePage.attr("action", "${ctx}/management/getWaybillsNotSubmit");
                    choosePage.attr("method", "get");
                    choosePage.submit();
                }else if (type==6) {
                    choosePage.attr("action", "${ctx}/management/getWaybillsReceiveAndNotSubmit");
                    choosePage.attr("method", "get");
                    choosePage.submit();
                }
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


            $('.waybill_id').attr('value', $(this).parent().parent().children('.waybill_id').text());
            $('.consignor_company').attr('value', $(this).parent().parent().children('.consignor_company').text());
            $('.consignor_tel').attr('value', $(this).parent().parent().children('.consignor_tel').text());
            $('.origin').attr('value', $(this).parent().parent().children('.origin').text());
            $('.consignor_address').attr('value', $(this).parent().parent().children('.consignor_address').text());
            $('.consignee_tel').attr('value', $(this).parent().parent().children('.consignee_tel').text());
            $('.consignee_company').attr('value', $(this).parent().parent().children('.consignee_company').text());
            $('.destination').attr('value', $(this).parent().parent().children('.destination').text());
            $('.consignee_address').attr('value', $(this).parent().parent().children('.consignee_address').text());
            $('.price').attr('value', $(this).parent().parent().children('.price').text());
            $('.freight').attr('value', $(this).parent().parent().children('.freight').text());
            $('.number').attr('value', $(this).parent().parent().children('.number').text());
            $('.remark').attr('value', $(this).parent().parent().children('.remark').text());

        })
    })
</script>
<!--/请在上方写此页面业务相关的脚本-->

</body>
</html>
