<%--
  Created by IntelliJ IDEA.
  User: LiXiaobin
  Date: 2018/1/4
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
    <title>首页</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,
                                     initial-scale=1.0,
                                     maximum-scale=1.0,
                                     user-scalable=no">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script>
        $('.carousel').carousel({
            interval: 1000
        });
    </script>
    <style>
        .MyMeau{
            margin-top: 35px;
        }
        .MyMeau img{
            display: inline-block;
            width: 60px;
        }
        .MyMeau .thumbnail{
            margin-bottom: 35px;
            margin-left: 30px;
            cursor:pointer;
        }
        .MyMeau .menu span{
            font-size: 18px;
            padding-left: 20px;
        }
        .MyMeau .menu .btn{
            width: 150px;
        }
    </style>
    <script>
        $(function() {
            if (window.history && window.history.pushState) {
                $(window).on('popstate', function () {
                    window.history.pushState('forward', null, '#');
                    window.history.forward(1);
                });
            }
            window.history.pushState('forward', null, '#'); //在IE中必须得有这两行
            window.history.forward(1);
        });
    </script>
</head>
<body style="overflow-x: hidden">
<nav class="navbar navbar-default" role="navigation" style="margin: 0 auto;display: none" id="top2" >
    <div class="container-fluid" style="float: right">
        <a class="navbar-brand" href="log.jsp"><span class="glyphicon glyphicon-log-in" style="margin-right: 10px"></span>登录</a>
    </div>
</nav>
<nav class="navbar navbar-default" role="navigation" style="margin: 0 auto " id="top1">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="success.jsp">${teacher.teacher_name}</a>
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#example-navbar-collapse1">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div class="collapse navbar-collapse navbar-right" id="example-navbar-collapse1">
            <ul class="nav navbar-nav">
                <li><a href="/MyMes.html"><span class="glyphicon glyphicon-user"></span>个人信息</a></li>
                <li><a href="/ChangeUser.html"><span class="glyphicon glyphicon-refresh"></span>切换用户</a></li>
                <li><a id="logout"><span class="glyphicon glyphicon-log-out"></span>退出登陆</a></li>
            </ul>
        </div>
    </div>
    <c:if test="${cookie.keyboo.value!=null}">
        <script>
            $("#logout").click(function () {
                var r = confirm("请先结束签到！");
                if (r==true){
                    $("#logout").attr("href","makenumkey.jsp");
                }
            });
        </script>
    </c:if>
    <c:if test="${cookie.imgFlag.value!=null}">
        <script>
            $("#logout").click(function () {
                var r = confirm("请先结束签到！");
                if (r==true){
                    $("#logout").attr("href","makeimgkey.jsp");
                }
            });
        </script>
    </c:if>
    <c:if test="${cookie.keyboo.value==null&&cookie.imgFlag.value==null}">
        <script>
            $("#logout").attr("href","LogOut.html");
        </script>
    </c:if>
</nav>
<c:if test="${empty teacher}">
    <script>
        $("#top1").hide();
        $("#top2").show();
    </script>
</c:if>
<div style="width: 100%;height: 250px;" >
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
        <div class="carousel-inner">
            <div class="item active">
                <img src="img/timg.jpg" alt="First slide" style="width: 330px;height: 250px;margin: 0px auto">
            </div>
            <div class="item">
                <img src="img/图像 001.png" alt="First slide" style="width: 300px;height: 250px;margin: 0px auto">
            </div>
            <div class="item">
                <img src="img/图像 002.png" alt="First slide" style="width: 300px;height: 250px;margin: 0px auto">
            </div>
            <div class="item">
                <img src="img/图像 003.png" alt="First slide" style="width: 300px;height: 250px;margin: 0px auto">
            </div>
            <div class="item">
                <img src="img/图像 004.png" alt="First slide" style="width: 300px;height: 250px;margin: 0px auto">
            </div>
            <div class="item">
                <img src="img/图像 005.png" alt="First slide" style="width: 300px;height: 250px;margin: 0px auto">
            </div>
            <div class="item">
                <img src="img/图像 006.png" alt="First slide" style="width: 300px;height: 250px;margin: 0px auto">
            </div>
            <div class="item">
                <img src="img/图像 007.png" alt="First slide" style="width: 300px;height: 250px;margin: 0px auto">
            </div>
            <div class="item">
                <img src="img/图像 008.png" alt="First slide" style="width: 300px;height: 250px;margin: 0px auto">
            </div>
            <div class="item">
                <img src="img/图像 009.png" alt="First slide" style="width: 300px;height: 250px;margin: 0px auto">
            </div>
        </div>
        <!-- 轮播（Carousel）导航 -->
        <a class="carousel-control left" href="#myCarousel"
           data-slide="prev"><span  class="glyphicon glyphicon-chevron-left" ></span>
        </a>
        <a class="carousel-control right" href="#myCarousel"
           data-slide="next"><span  class="glyphicon glyphicon-chevron-right" ></span>
        </a>
    </div>
</div>
<div>
    <div style="width: 100%;"class="MyMeau">
        <div class="col-xs-11 col-sm-6 col-md-3 menu">
            <div class="thumbnail" onclick="location.href='/MyMes.html'">
                <img src="img/info.png"
                     alt="个人信息">
                <span>查看个人信息</span>
            </div>
        </div>
        <div class="col-xs-11 col-sm-6 col-md-3 menu" >
            <div class="thumbnail" onclick="location.href='makenumkey.jsp'">
                <img src="img/makekey.png"
                     alt="生成口令">
                <span>生成数字口令</span>
            </div>
        </div>
        <div class="col-xs-11 col-sm-6 col-md-3 menu">
            <div class="thumbnail" onclick="location.href='makeimgkey.jsp'">
                <img src="img/phonekey.png"
                     alt="生成二维码">
                <span>生成二维码</span>
            </div>
        </div>
        <div class="col-xs-11 col-sm-6 col-md-3 menu" style="margin-bottom: 30px">
            <div class="thumbnail" onclick="location.href='lookend.jsp'">
                <img src="img/search.png"
                     alt="信息查询">
                <span>信息查询功能</span>
            </div>
        </div>
    </div>
</div>
<div style="height: 70px"></div>
<nav class="navbar navbar-fixed-bottom navbar-inverse" role="navigation" style="width: 100%">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#example-navbar-collapse">
                <span class="sr-only">切换导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">在线课堂</a>
        </div>
        <div class="collapse navbar-collapse" id="example-navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/forSuccess.html">主页</a></li>
                <li><a href="curseinfo.jsp">课程表</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        我的签到 <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="makenumkey.jsp">口令签到</a></li>
                        <li><a href="makeimgkey.jsp">二维码签到</a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        班级信息 <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu" id="parentClassInfo">
                        <c:if test="${!empty myClassInfo}">
                            <c:forEach items="${myClassInfo}" var="myClassInfo">
                                <li><a href="/detailedInf.html?detailedMajor=${teacher.teacher_major}&detailedName=${myClassInfo.class_name}" >
                                    ${teacher.teacher_major}---${myClassInfo.class_name}班
                                </a></li>
                            </c:forEach>
                        </c:if>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>





