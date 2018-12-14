<%--
  Created by IntelliJ IDEA.
  User: LiXiaobin
  Date: 2018/1/12
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,
                                     initial-scale=1.0,
                                     maximum-scale=1.0,
                                     user-scalable=no">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <style>
        table p{
            text-align: center;
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
<div class="visible-lg" style="height: 40px"></div>
<div class="container">
    <div class="row">
        <div class="col-md-3 visible-lg" >
            <div style="margin-bottom: 20px;">
                <input type="button" value="查看缩略信息" class="btn btn-default" id="smallButton" style="margin-right: 30px">
                <input type="button" value="查看详细信息" class="btn btn-default" id="detailButton">
            </div>
            <div class="panel panel-success">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" href="#collapseFirst">
                            <p>专业：${major}</p>
                            <p>班级：${className}</p>
                        </a>
                    </h4>
                </div>
                <div id="collapseFirst" class="panel-collapse collapse ">
                    <div class="panel-body">
                        <p>人数：${count}</p>
                        <p>请假人数：0</p>
                        <p>辅导员：刘辉良</p>
                        <p>辅导员电话：17512580902</p>
                    </div>
                </div>
            </div>
            <div class="row">
                <p class="col-md-4"><a class="btn bg-info" id="prepage">上一页</a></p>
                <p class="col-md-4" style="padding-left: 30px"><input type="text" value="1" id="nowPage" style="width: 20px" disabled> / ${page}</p>
                <p class="col-md-4"><a class="btn bg-info" id="nextPage">下一页</a></p>
            </div>
        </div>
        <div class="col-xs-12 visible-xs visible-md visible-sm" style="margin:10px 0;">
            <div class="row" style="margin-left: 3px">
                <div  class="col-xs-4">
                    <input type="button" value="缩略信息" class="btn btn-default" id="smallButtonxs">
                </div>
                <div class="col-xs-4">
                    <button type="button" class="btn btn-success" title="${major} ${className}"
                            data-container="body" data-toggle="popover" data-placement="bottom"
                            data-content="人数:${count}||请假人数：0 ||辅导员：刘辉良 ||辅导员电话：17512580902"> 班级信息
                    </button>
                </div>
                <div  class="col-xs-4">
                    <input type="button" value="详细信息" class="btn btn-default" id="detailButtonxs">
                </div>
            </div>
        </div>
        <script>
            $(function (){
                $("[data-toggle='popover']").popover();
            });
            $("#smallButton").click(function () {
                $("#smallTable").show();
                $("#detailTable").hide();
            });
            $("#detailButton").click(function () {
                $("#smallTable").hide();
                $("#detailTable").show();
            });
            $("#smallButtonxs").click(function () {
                $("#smallTable").show();
                $("#detailTable").hide();
            });
            $("#detailButtonxs").click(function () {
                $("#smallTable").hide();
                $("#detailTable").show();
            });
        </script>
        <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9" style="margin-bottom: 50px;" id="detailTable">
            <table class="table table-bordered table-hover table-striped">
                <thead>
                <td><p>学号</p></td>
                <td><p>姓名</p></td>
                <td><p>性别</p></td>
                <td><p>联系方式</p></td>
                </thead>
                <tbody id="tableBody">
                    <c:forEach items="${student}" var="student" begin="0" end="9" step="1">
                        <tr>
                            <td><p>${student.student_account}</p></td>
                            <td><p>${student.student_name}</p></td>
                            <td><p>${student.student_sex}</p></td>
                            <td><p>${student.student_phone}</p></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="col-xs-12 visible-xs visible-md visible-sm">
                <table class="table ">
                    <tr>
                        <td>
                            <p><a class="btn bg-info" id="prepage1">上一页</a></p>
                        </td>
                        <td>
                            <p><input type="text" value="1" id="nowPage1" style="width: 20px" disabled> / ${page}</p>
                        </td>
                        <td>
                            <p><a class="btn bg-info" id="nextPage1">下一页</a></p>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9" style="display: none" id="smallTable">
            <table class="table table-bordered table-hover table-striped" >
                <c:if test="${!empty student}">
                    <c:forEach items="${student}" var="student" varStatus="i" begin="0" step="1" >
                        <td><p>${student.student_name}</p></td>
                        <c:if test="${i.count%3==0}">
                            <tr></tr>
                        </c:if>
                    </c:forEach>
                </c:if>
            </table>
        </div>
    </div>
</div>
<nav class="navbar navbar-fixed-bottom navbar-inverse" role="navigation">
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
                <li class="active"><a href="success.jsp">主页</a></li>
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
<script>
    $("#prepage").click(function () {
        var temp =  parseInt($("#nowPage").val());
        $("#nowPage").val(temp-1);
        if (temp-1<=0)
        {
            $("#nowPage").val(temp);
            alert("已经是第一页了")
        }
        else {
            $("#tableBody").html("");
            $.ajax({
                type:"GET",
                url:encodeURI("next.html?nowPage="+$("#nowPage").val()),
                dataType:"json",
                contentType : "application/json;charset=UTF-8",
                success:function(data) {
                    setTable(data);
                }
            });
        }
    });
    $("#nextPage").click(function () {
        var temp =  parseInt($("#nowPage").val());
        $("#nowPage").val(temp+1);
        if (temp+1>${page})
        {
            $("#nowPage").val(temp);
            alert("已经是最后一页了")
        }
        else {
            $("#tableBody").html("");
            $.ajax({
                type:"GET",
                url:encodeURI("next.html?nowPage="+$("#nowPage").val()),
                dataType:"json",
                contentType : "application/json;charset=UTF-8",
                success:function(data) {
                    setTable(data);
                }
            });
        }
    });
    $("#prepage1").click(function () {
        var temp =  parseInt($("#nowPage1").val());
        $("#nowPage1").val(temp-1);
        if (temp-1<=0)
        {
            $("#nowPage1").val(temp);
            alert("已经是第一页了")
        }
        else {
            $("#tableBody").html("");
            $.ajax({
                type:"GET",
                url:encodeURI("next.html?nowPage="+$("#nowPage1").val()),
                dataType:"json",
                contentType : "application/json;charset=UTF-8",
                success:function(data) {
                    setTable(data);
                }
            });
        }
    });
    $("#nextPage1").click(function () {
        var temp =  parseInt($("#nowPage1").val());
        $("#nowPage1").val(temp+1);
        if (temp+1>${page})
        {
            $("#nowPage1").val(temp);
            alert("已经是最后一页了")
        }
        else {
            $("#tableBody").html("");
            $.ajax({
                type:"GET",
                url:encodeURI("next.html?nowPage="+$("#nowPage1").val()),
                dataType:"json",
                contentType : "application/json;charset=UTF-8",
                success:function(data) {
                    setTable(data);
                }
            });
        }
    });
    function setTable(data) {
        var temp = eval(data);
        for(var i=0;i<temp.length;i++) {
            var nextNode = temp[i];//代表的是json数据格式的第i个元素
            //动态生成tr td
            //alert(nextNode);
            var tr = document.createElement("tr");
            var account = document.createElement("td");
            var name = document.createElement("td");
            var sex = document.createElement("td");
            var phone = document.createElement("td");
            var account1 = document.createElement("p");
            var name1 = document.createElement("p");
            var sex1 = document.createElement("p");
            var phone1 = document.createElement("p");
            //设置td格式
            var accounttext = document.createTextNode(nextNode.student_account);
            var nametext = document.createTextNode(nextNode.student_name);
            var sextext = document.createTextNode(nextNode.student_sex);
            var phonetext = document.createTextNode(nextNode.student_phone);
            //alert(text);
            account1.appendChild(accounttext);
            name1.appendChild(nametext);
            sex1.appendChild(sextext);
            phone1.appendChild(phonetext);
            account.appendChild(account1);
            name.appendChild(name1);
            sex.appendChild(sex1);
            phone.appendChild(phone1);
            tr.appendChild(account);
            tr.appendChild(name);
            tr.appendChild(sex);
            tr.appendChild(phone);
            document.getElementById("tableBody").appendChild(tr);
        }
    }
</script>
</body>
</html>
