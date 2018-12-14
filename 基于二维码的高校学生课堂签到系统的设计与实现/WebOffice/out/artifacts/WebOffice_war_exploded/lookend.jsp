<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>查看到课情况</title>
    <meta name="viewport" content="width=device-width,
                                     initial-scale=1.0,
                                     maximum-scale=1.0,
                                     user-scalable=no">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <style>
    </style>
</head>
<body>
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
    <c:if test="${cookie.keyboo.value==null&&cookie.imgFlag.value==null}">
        <script>
            $("#logout").attr("href","LogOut.html");
        </script>
    </c:if>
</nav>
<div class="col-lg-12 visible-lg visible-sm visible-md" style="height: 30px"></div>
<div class="visible-xs" style="height: 8%"></div>
<div class="col-xs-12 col-lg-6 col-sm-8 col-md-8 col-sm-offset-2 col-md-offset-2 col-lg-offset-3" style="padding-top: 30px;">
    <div class="row" style="border-bottom: 1px solid black">
        <div class="col-xs-12">
            <div class="col-xs-5">
                <div class="form-group">
                    <label>开始时间</label>
                    <select   class="form-control">
                        <option>2014</option>
                        <option>2015</option>
                        <option>2016</option>
                        <option selected>2017</option>
                        <option>2018</option>
                    </select>
                </div>
            </div>
            <div class="col-xs-5">
                <div class="form-group">
                    <label>结束时间</label>
                    <select   class="form-control">
                        <option>2014</option>
                        <option>2015</option>
                        <option>2016</option>
                        <option>2017/<option>
                        <option selected>2018</option>
                    </select>
                </div>
            </div>
            <div class="col-xs-2" style="margin-top: 3.2%">
                <button class="btn btn-info" id="searchEnd">查询</button>
            </div>
        </div>
    </div>
    <div class="row" style="margin-top: 5%;margin-bottom: 5%">
        <table class="table table-striped table-bordered table-hover">
            <tr style="text-align: center">
                <td>教师</td>
                <td>专业</td>
                <td>班级</td>
                <td>签到时间</td>
                <td>应到人数</td>
                <td>实到人数</td>
                <td>辅导员</td>
                <td>查看未签到学生</td>
            </tr>
            <tbody id="actt">

            </tbody>
        </table>
    </div>
    <script>
        $('#searchEnd').click(function () {
            $.ajax({
                type: "GET",
                url: encodeURI("lookEnd.html?teacherAccount=${teacher.teacher_account}"),
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                success: function (data) {
                    var c = eval(data);
                    console.log(c);
                    for (var i =0;i<c.length;i++){
                        var temp = c[i];
                        var str1 = "<tr style=\"text-align: center\">";
                        var str2 =  ""
                        for (var j =0;j<temp.length;j++){
                            str1+="<td>"+temp[j]+"</td>";
                        }
                        str1+="</tr>";
                        $('#actt').append(str1);
                    }
                }
            });
        });
    </script>
</div>
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
</body>
</html>