<%--
  Created by IntelliJ IDEA.
  User: LiXiaobin
  Date: 2018/1/29
  Time: 16:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>课程表</title>    <meta charset="utf-8">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <style>
        table td{
            width: 100px;
            height: 50px;
        }
    </style>
</head>
<body>
<c:if test="${empty teacher}">
    <script>
        alert("请先登陆再进行操作！")
        window.location.href="log.jsp";
    </script>
</c:if>
<nav class="navbar navbar-default" role="navigation" style="margin: 0 auto">
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
<c:if test="${!empty myClassInfo}">
    <div class="col-xs-12 col-md-10 col-sm-10 col-lg-10 col-md-offset-1 col-sm-offset-1" style="margin-top: 20px;margin-bottom: 50px">
        <ul id="myTab" class="nav nav-tabs">
            <li class="active"><a href="#home"  data-toggle="tab">课程信息</a></li>
            <c:forEach items="${myClassInfo}" var="myClassInfo">
                <li><a href="#${myClassInfo.class_name}" data-toggle="tab">${myClassInfo.class_name}</a></li>
            </c:forEach>
        </ul>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade in active" id="home">
                <div class="col-xs-12 col-md-12 col-sm-12 col-lg-12" style=";height:70%">
                    <div class="thumbnail" style="">
                        <img src="img/curseHome.jpg"
                             alt="curseHome">
                    </div>
                </div>
            </div>
            <c:forEach items="${myClassInfo}" var="myClassInfo">
                <div class="tab-pane fade" id="${myClassInfo.class_name}">
                    <table class="table table-bordered table-hover table-striped">
                        <thead>
                        <tr>
                            <td></td>
                            <td>星期一</td>
                            <td>星期二</td>
                            <td>星期三</td>
                            <td>星期四</td>
                            <td>星期五</td>
                            <td>星期六</td>
                            <td>星期日</td>
                        </tr>
                        </thead>
                        <tbody id="curseTable">
                        </tbody>
                    </table>
                </div>
            </c:forEach>
        </div>
    </div>
</c:if>

<script>
    $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
        // e.target // 激活的标签页

        var activeTab = $(e.target).text();

        $.ajax({
            type:"GET",
            url:encodeURI("lookClassCurse.html?activeTab="+activeTab+"&&major=${teacher.teacher_major}"),
            dataType:"json",
            contentType : "application/json;charset=UTF-8",
            success:function(data) {
                setCurseTable(data);
            }
        });
        var previousTab = $(e.relatedTarget).text();
        // e.relatedTarget // 前一个激活的标签页
    });
    function setCurseTable(data) {
        var temp = eval(data);
        $("#curseTable").html("");
        for (var i=0;i<10;i++){
            var tr = document.createElement("tr");
            document.getElementById("curseTable").appendChild(tr);
            var td = document.createElement("td");
            var t_index = document.createTextNode(i+1);
            td.appendChild(t_index);
            tr.appendChild(td);
            for(var j=0;j<7;j++){
                var td = document.createElement("td");
                tr.appendChild(td);
                for (var n = 0;n<temp.length;n++){
                    if ((temp[n].class_curse_day-1)==j&&(temp[n].class_curse_index-1)==i){
                        var curseText = document.createTextNode(temp[n].class_curse_name);
                        td.appendChild(curseText);
                    }else {
                        var curseText = document.createTextNode(" ");
                        td.appendChild(curseText);
                    }
                }
            }
        }
    }
</script>
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
