<%@ page import="java.util.List" %>
<%@ page import="com.skitchina.model.Submit" %>
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
    List<Submit> submits = (List<Submit>) session.getAttribute("submits");
    int pagesNow = (Integer) session.getAttribute("pagesNow");
    int submitNum = (Integer) session.getAttribute("submitsNum");
    double pagesAll1 = (double) submitNum / 10;
    int pagesAll;
    if (pagesAll1 > submitNum / 10) {
        pagesAll = submitNum / 10 + 1;
    } else {
        pagesAll = submitNum / 10;
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
    <style>
        .humeng {
            float: left;
            margin-left: 10px;
        }

        .changeColor {
            background-color:#F0F0F0;
        }
    </style>
    <title>用户管理</title>
</head>
<body>
<!--_header 作为公共模版分离出去-->
<header class="navbar-wrapper">
    <input type="hidden" id="pagesAll" value="<%=pagesAll%>">
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
                </ul>
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
            <div class="cl pd-5 bg-1 bk-gray mt-20">
                <span class="l"></span> <span class="r">共有数据：<strong><%=submitNum%></strong> 条</span> </div>
            <div class="mt-10">
                <table class="table table-border table-bordered table-bg table-sort" style="table-layout: fixed">
                    <thead>
                    <tr class="text-c">
                        <th width="30" class="submit_id">ID</th>
                        <th width="50">名字</th>
                        <th width="65">时间</th>
                        <th width="60">金额</th>
                        <th width="70"></th>

                    </tr>
                    </thead>
                    <tbody>

                    <%for (Submit submit:submits){%>
                    <tr class="submits">
                        <th><%=submit.getId()%></th>
                        <th><%=submit.getName()%></th>
                        <th><%=submit.getTime4().substring(0,19)%></th>
                        <th><%=submit.getAchievement()%></th>
                        <form action="${ctx}/management/getWaybillsBySubmit">
                            <input type="hidden" name="pagesOnSubmit" value="<%=pagesNow%>">
                            <input type="hidden" name="pages" value="1">
                            <input type="hidden" name="id" value="<%=submit.getId()%>" >
                            <th><button type="submit">查看详情</button></th>
                        </form>

                    </tr>
                    <%}%>
                    </tbody>
                </table>
            </div>
        </article>
        <div class="humeng" style="margin-left: 50px">

        <%if (pagesNow!=1){%>
            <div class="humeng">
        <a href="${ctx}/management/getAllSubmits?pages=<%=pagesNow-1%>&rows=10" class="pages">上一页</a>
            </div>
        <%}%>
            <div class="humeng">
        第<%=pagesNow%>页，共<%=pagesAll%>页
            </div>
        <%if (pagesNow!=pagesAll){%>
            <div class="humeng">
        <a href="${ctx}/management/getAllSubmits?pages=<%=pagesNow+1%>&rows=10" class="pages">下一页</a>
            </div>
        <%}%>
            <div class="humeng">
                <form action="${ctx}/management/getAllSubmits" id="choosePage">
                    <input type="number" name="pages" style="width: 50px" id="pages">
                </form>
                </div>
            <div class="humeng">
                <button id="button2">跳转</button>
            </div>
            </div>
        </div>
    </div>
</section>

<

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

        $("table tr").hover(function () {
            $(this).addClass("changeColor");
        },function () {
            $(this).removeClass("changeColor");
        })

        $("#click_button").on("click",function () {
            var submit_form = $("#update_form");
            submit_form.submit();
        });

        $('#click2').on('click', function () {
            var cellphone = $('#cellphone2').val();
            if (cellphone=="") {
                alert("电话号码不能为空");
            }else {
                var form = $('#search');
                form.submit();
            }
        });

        $("#button1").on("click",function () {
            var form = $("#form1");
            form.submit();
        })

        $('#button2').on("click",function () {
            var pages = $("#pages").val();
            var pagesAll = $("#pagesAll").val();
            if (pages<1) {
                alert("页数不能小于1");
            }else if (pages>pagesAll) {
                alert("页数不能大于总页数")
            }else {
                var form = $("#choosePage");
                form.submit();
            }
        })

        $('.wy-set').click(function () {
            $('#myModal').modal({
                keyboard: true,
                backdrop:true
            });

//            $('.user_id').attr('value', $(this).parent().parent().children('.user_id').text());
//            $('.name').attr('value', $(this).parent().parent().children('.name').text());
//            $('.cellphone').attr('value', $(this).parent().parent().children('.cellphone').text());
//            $('.password').attr('value', $(this).parent().parent().children('.password').text());
//            $('.company_name').attr('value', $(this).parent().parent().children('.company_name').text());
//            $('.company_tel').attr('value', $(this).parent().parent().children('.company_tel').text());
//            $('.company_address').attr('value', $(this).parent().parent().children('.company_address').text());
//            $('.achievement').attr('value', $(this).parent().parent().children('.achievement').text());

        })

    })
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>

