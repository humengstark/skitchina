<%@ page import="java.util.List" %>
<%@ page import="com.skitchina.model.User" %>
<%@ page import="com.skitchina.model.Station" %>
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
    List<User> users = (List<User>) session.getAttribute("users");
    List<Station> stations = (List<Station>) session.getAttribute("stations");
    int pagesAll = (Integer) session.getAttribute("pagesAll");
    int userNum = (Integer) session.getAttribute("userNum");
    int pagesNow = (Integer) session.getAttribute("pagesNow");
    int rows = (Integer) session.getAttribute("rows");
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
    <style>
        .changeColor {
            background-color:#F0F0F0;
        }
    </style>
    <title>用户管理</title>
</head>
<body>
<!--_header 作为公共模版分离出去-->
<header class="navbar-wrapper">
    <div class="navbar navbar-fixed-top">
        <div class="container-fluid cl"> <a class="logo navbar-logo f-l mr-10 hidden-xs" href="/aboutHui.shtml">H-ui.admin</a> <a class="logo navbar-logo-m f-l mr-10 visible-xs" href="/aboutHui.shtml">H-ui</a> <span class="logo navbar-slogan f-l mr-10 hidden-xs">3.0</span> <a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
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
                    <li><a href="${ctx}/management/getAllClients?pages=1" title="商家信息">商家信息</a></li>                </ul>
            </dd>
        </dl>
    </div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<!--/_menu 作为公共模版分离出去-->

<section class="Hui-article-box">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 物流管理<span class="c-gray en">&gt;</span> 订单管理 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>

    <div class="Hui-article">
        <article class="cl pd-20">
            <div class="text-c">
                <form class="Huiform" method="post" action="" target="_self">
                    <span class="btn-upload form-group">
					</span> <span class="select-box" style="width:150px">
					</span>
                </form>
            </div>
            <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><form action="${ctx}/management/getUserByCellphone" method="post" id="search"><input
                    type="text" name="cellphone" width="60" id="cellphone2" placeholder="请输入电话号码"></form><button id="click2" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 搜索</button></span> <span class="r">共有数据：<strong><%=userNum%></strong> 条</span> </div>
            <div class="mt-10">
                <table class="table table-border table-bordered table-bg table-sort" style="table-layout: fixed">
                    <thead>
                    <tr class="text-c">
                        <th width="30">ID</th>
                        <th width="50">名字</th>
                        <th width="65">电话</th>
                        <th width="60">密码</th>
                        <th width="70">公司名字</th>
                        <th width="70">公司地址</th>
                        <th width="65">公司电话</th>
                        <th width="50">总账</th>
                        <th width="70">网点</th>
                        <th width="90">权限</th>
                        <th width="30"></th>
                        <th width="30"></th>
                    </tr>
                    </thead>
                    <tbody>

                    <%for (User user:users){%>
                    <tr class="user">
                        <td class="user_id"><%=user.getId()%></td>
                        <td class="name"><%=user.getName()%></td>
                        <td class="cellphone"><%=user.getCellphone()%></td>
                        <td class="password"><%=user.getPassword()%></td>
                        <td class="company_name"><%=user.getCompany_name()%></td>
                        <td class="company_address"><%=user.getCompany_address()%></td>
                        <td class="company_tel"><%=user.getCompany_tel()%></td>
                        <td class="achievement"><%=user.getAchievement()%></td>
                        <form action="${ctx}/management/updateStation" method="post">
                            <input type="hidden" name="pages" value="<%=pagesNow%>">
                            <input type="hidden" name="id" value="<%=user.getId()%>">
                        <td><%=user.getStation()%>
                            <select name="station">
                                <%for (Station station:stations){%>
                                <option value="<%=station.getName()%>"><%=station.getName()%></option>
                                <%}%>
                            </select>
                            <button type="submit">修改</button>
                        </td>
                        </form>
                        <form action="${ctx}/management/updatePower" method="post">
                            <input type="hidden" name="pages" value="<%=pagesNow%>">
                            <input type="hidden" name="id" value="<%=user.getId()%>">
                        <%if (user.getPower()==0){%>
                        <td>普通
                            <select name="power">
                            <option value="0">普通</option>
                            <option value="1">业务员</option>
                            <option value="2">管理员</option>
                            <option value="3">高级管理员</option>
                        </select>
                            <button type="submit">修改</button></td>
                        <%}else if (user.getPower()==1){%>
                        <td>业务员<select name="power">
                            <option value="1">业务员</option>
                            <option value="0">普通</option>
                            <option value="2">管理员</option>
                            <option value="3">高级管理员</option>
                        </select>
                            <button type="submit">修改</button></td>
                        <%}else if (user.getPower()==2){%>
                        <td>管理员<select name="power">
                            <option value="2">管理员</option>
                            <option value="0">普通</option>
                            <option value="1">业务员</option>
                            <option value="3">高级管理员</option>
                        </select>
                            <button type="submit">修改</button></td>
                        <%}else if (user.getPower()==3){%>
                        <td>高级管理员<select name="power">
                            <option value="3">高级管理员</option>
                            <option value="0">普通</option>
                            <option value="1">业务员</option>
                            <option value="2">管理员</option>
                        </select>
                            <button type="submit">修改</button></td>
                        <%}%>
                        </form>
                        <td><a href="${ctx}/management/deleteUserById?pages=<%=pagesNow%>&id=<%=user.getId()%>">删除</a></td>
                        <td><button type="button" class="wy-set">修改</button> </td>
                    </tr>
                    <%}%>
                    </tbody>
                </table>
            </div>
        </article>
        <%if (pagesNow!=1){%>
        <a href="${ctx}/management/getAllUsers?pages=<%=pagesNow-1%>&rows=10" class="pages">上一页</a>
        <%}%>
        第<%=pagesNow%>页，共<%=pagesAll+1%>页
        <%if (pagesNow!=pagesAll+1){%>
        <a href="${ctx}/management/getAllUsers?pages=<%=pagesNow+1%>&rows=10" class="pages">下一页</a>
        <%}%>
    </div>
</section>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="height: auto">
<div class="modal-dialog" role="document">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">修改订单</h4>
        </div>
        <div class="modal-body">
            <form class="form-horizontal" id="update_form" action="${ctx}/management/updateUserById" method="get">
                <input type="hidden" id="user_id" name="user_id" class="user_id">
                <input type="hidden" name="pagesNow" value="<%=pagesNow%>">
                <!--这里加输入框-->
                <div class="form-group col-lg-12">
                    <label for="name" class='col-lg-4  control-label'>名字</label>
                    <div class='col-lg-8'>
                        <input type="text" id='name' class='form-control name' name="name">
                    </div>
                </div>
                <!--这里是第二个输入框-->
                <div class="form-group col-lg-12">
                    <label for="cellphone"  class='col-lg-4 control-label'>电话</label>
                    <div class='col-lg-8'>
                        <input type="text" class="form-control cellphone" id="cellphone" name="cellphone">
                    </div>
                </div>
                <!--这里你可以继续加-->
                <div class="form-group col-lg-12">
                    <label for="password"  class='col-lg-4 control-label'>密码</label>
                    <div class='col-lg-8'>
                        <input type="text" class="form-control password" id="password" name="password">
                    </div>
                </div>

                <div class="form-group col-lg-12">
                    <label for="company_name"  class='col-lg-4 control-label'>公司名字</label>
                    <div class='col-lg-8'>
                        <input type="text" class="form-control company_name" id="company_name" name="company_name">
                    </div>
                </div>

                <div class="form-group col-lg-12">
                    <label for="company_address"  class='col-lg-4 control-label'>公司地址</label>
                    <div class='col-lg-8'>
                        <input type="text" class="form-control company_address" id="company_address" name="company_address">
                    </div>
                </div>

                <div class="form-group col-lg-12">
                    <label for="company_tel"  class='col-lg-4 control-label'>公司电话</label>
                    <div class='col-lg-8'>
                        <input type="text" class="form-control company_tel" id="company_tel" name="company_tel">
                    </div>
                </div>

                <div class="form-group col-lg-12">
                    <label for="achievement"  class='col-lg-4 control-label'>总账</label>
                    <div class='col-lg-8'>
                        <input type="text" class="form-control achievement" id="achievement" name="achievement">
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

    $("#click_button").on("click",function () {
        var submit_form = $("#update_form");
        submit_form.submit();
    });

    $("table tr").hover(function () {
        $(this).addClass("changeColor");
    },function () {
        $(this).removeClass("changeColor");
    })

    $('#click2').on('click', function () {
        var cellphone = $('#cellphone2').val();
        if (cellphone=="") {
            alert("电话号码不能为空");
        }else {
            var form = $('#search');
            form.submit();
        }
    });

    $('.wy-set').click(function () {
        $('#myModal').modal({
            keyboard: true,
            backdrop:true
        });

        $('.user_id').attr('value', $(this).parent().parent().children('.user_id').text().trim());
        $('.name').attr('value', $(this).parent().parent().children('.name').text().trim());
        $('.cellphone').attr('value', $(this).parent().parent().children('.cellphone').text().trim());
        $('.password').attr('value', $(this).parent().parent().children('.password').text().trim());
        $('.company_name').attr('value', $(this).parent().parent().children('.company_name').text().trim());
        $('.company_tel').attr('value', $(this).parent().parent().children('.company_tel').text().trim());
        $('.company_address').attr('value', $(this).parent().parent().children('.company_address').text().trim());
        $('.achievement').attr('value', $(this).parent().parent().children('.achievement').text().trim());

    })

})
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
