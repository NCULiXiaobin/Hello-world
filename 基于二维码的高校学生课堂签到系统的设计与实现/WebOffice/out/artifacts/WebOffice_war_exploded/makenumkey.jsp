<%--
  Created by IntelliJ IDEA.
  User: LiXiaobin
  Date: 2018/1/15
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta name="viewport" content="width=device-width,
                                     initial-scale=1.0,
                                     maximum-scale=1.0,
                                     user-scalable=no">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <style>
        #makeUseInfo,#showUseKeybord{
            border-radius: 10px;
            border:1px solid #999;
            box-shadow: 10px 10px 15px rgba(50,50,50,0.5);
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
    <c:if test="${cookie.keyboo.value==null&&cookie.imgFlag.value==null}">
        <script>
            $("#logout").attr("href","LogOut.html");
        </script>
    </c:if>
</nav>
<div class="col-lg-12 visible-lg visible-sm visible-md" style="height: 30px"></div>
<div class="visible-xs" style="height: 8%"></div>
<div class="col-xs-12 col-lg-6 col-sm-8 col-md-8 col-sm-offset-2 col-md-offset-2 col-lg-offset-3" style="padding-top: 30px;" id="makeUseInfo">
    <div class="row">
        <div class="col-xs-12 col-lg-8 col-sm-8 col-md-8 col-md-offset-2 col-sm-offset-2">
            <form action="/makeNumKey.html" class="form-group" method="post">
                <lable for="makeUseAccount" class="sr-only">工号</lable>
                <input type="text" name="makeUseAccount" value="${teacher.teacher_account}" hidden>
                <lable for="makeUseName"><p class="text-info lead">教师</p></lable>
                <input type="text" id="makeUseName" name="makeUseName" class="form-control" value="${teacher.teacher_name}" readonly>
                <label for="makeUseMajor"><p class="text-info lead">班级</p></label>
                <div class="row">
                    <div class="col-xs-6 col-lg-6 col-sm-6 col-md-6">
                        <input type="text" id="makeUseMajor" name="makeUseMajor" class="form-control" value="${teacher.teacher_major}" readonly>
                    </div>
                    <div class="col-xs-6  col-lg-6 col-sm-6 col-md-6">
                        <select class="form-control" id="selectClass" name="selectClass">
                            <c:if test="${!empty myClassInfo}">
                                <c:forEach items="${myClassInfo}" var="myClassInfo">
                                    <option>${myClassInfo.class_name}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </div>
                </div>
                <label for="makeUseCurse"><p class="text-info lead">课程</p></label>
                <select class="form-control" id="makeUseCurse" name="makeUseCurse">
                    <c:if test="${!empty curses}">
                        <c:forEach items="${curses}" var="curses">
                            <option>${curses.curse_name}</option>
                        </c:forEach>
                    </c:if>
                </select>
                <label for="makeUseTime"><p class="text-info lead" >时间</p></label>
                <div class="row"  id="makeUseTime">
                    <div class="col-xs-6 col-lg-6 col-sm-6 col-md-6">
                        <select name="week" id="week" class="form-control" >
                            <c:forEach var="i" begin="1" end="7" step="1">
                                <option value="${i}">星期${i}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-xs-6 col-lg-6 col-sm-6 col-md-6">
                        <select name="indexCurse" id="indexCurse" class="form-control">
                            <c:forEach var="i" begin="1" end="10" step="1">
                                <option value="${i}">第${i}节课</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <lable for="makeNumSignTime"><p class="text-info lead">签到时间</p></lable>
                <input type="text" class="form-control" name="makeNumSignTime" placeholder="请输入0-120以内的数字，默认时间为30分钟">
                <input type="submit" value="提交" id="makeSureNumUseInfo" class="btn btn-success btn-block" style="margin-top: 40px;margin-bottom: 50px">
            </form>
        </div>
    </div>
</div>
<c:if test="${cookie.imgFlag.value!=null}">
    <script>
        $("#logout").click(function () {
            var r = confirm("请先结束签到！");
            if (r==true){
                $("#logout").attr("href","makeimgkey.jsp");
            }
        });
        $("#makeSureNumUseInfo").val("请先结束正在进行的签到....");
        $("#makeSureNumUseInfo").attr("disabled",true);
    </script>
</c:if>
<c:if test="${cookie.keyboo.value!=null}">
    <div class="col-lg-4 col-sm-4 col-md-4 col-md-offset-4 col-sm-offset-4" style="margin-top: 60px;">
        <div id="showUseKeybord" hidden>
            <h1 class="text-center" id="numKeybord1" style="line-height: 200px"><c:out value="${cookie.keyboo.value}"/></h1>
        </div>
        <div class="panel panel-warning" id="showEndTime" style="margin-top: 20px" hidden>
            <div class="panel-heading">
                <h4>签到结束倒计时
                </h4>
                <input type="button" class="btn bg-info" id="lookLeftTime" value="查看剩余时间">
                <input type="button" class="btn bg-danger" id="endSign" onclick="disp_confirm()" value="结束签到" >
            </div>
            <div class="panel-body">
                <h2 id="endTime"></h2>
            </div>
        </div>
    </div>
    <script>
        var InterValObj;
        var s;
        var kkb = $("#numKeybord1").html();
        $("#lookLeftTime").click(function () {
            $("#lookLeftTime").attr("disabled",true);
            $.ajax({
                type:"GET",
                url:encodeURI("lookLeftTime.html?kkb="+kkb),
                dataType:"text",
                contentType : "application/json;charset=UTF-8",
                success:function(data) {
                    s = data;
                    InterValObj = window.setInterval(CountTime, 1000);
                }
            });
        });

        function CountTime() {
            if (s>=0){
                $("#endTime").html(s);
                s--;
            }else {
               alert("签到结束！");
                $("#new").modal("show");
                window.clearInterval(InterValObj);
            }
        }
        $("#lookLeftTime").trigger("click");
    </script>
    <script>
        $ ('#makeUseInfo').hide(1000);
        $ ('#showUseKeybord').show(2000);
        $('#showEndTime').show(2000);
        $("#makeSureNumUseInfo").attr("disabled", true);
        $("#makeUseName").attr("disabled", true);
        $("#selectClass").attr("disabled", true);
        $("#makeUseCurse").attr("disabled", true);
        $("#week").attr("disabled", true);
        $("#indexCurse").attr("disabled", true);
    </script>
</c:if>
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
<div class="modal fade" id="new">
    <div class="modal-dialog" style="margin-top: 50px; ">
        <div class="modal-content">
            <div class="modal-header"> 本节课签到情况统计
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
            </div>
            <div class="modal-body">
                <input type="button" value="查看" id="lookNumSignInfo" hidden>
                <table class="table table-bordered table-hover table-striped">
                    <tr><td colspan="2" id="NumSignInfoHead" class="text-center"style="height: 50px;line-height: 50px"></td></tr>
                    <tr>
                        <td style="width: 120px;">应到人数</td>
                        <td id="NumSignInfoCount"></td>
                    </tr>
                    <tr>
                        <td>实到人数</td>
                        <td id="NumSignNowCount"></td>
                    </tr>
                    <tr>
                        <td style="height: 100px;">已签到学生签</td>
                        <td>
                            <div id="NumSignLeftStudent">
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" type="submit" data-dismiss="modal">关闭</button>
                <button class="btn btn-success" type="submit">保存(功能未完成)</button>
            </div>
        </div>
    </div>
</div>
<script>
    $('#new').on('hidden.bs.modal', function () {
        $(location).attr('href', 'returnSuccess.html');
    });
    function disp_confirm()
    {
        var r=confirm("是否提前结束签到？")
        if (r==true)
        {
            $("#endTime").html(0);
            window.clearInterval(InterValObj);
            $("#new").modal("show");
        }
    }
</script>
<script>
    $('#new').on('show.bs.modal', function () {
        $.ajax({
            type: "GET",
            url: encodeURI("serchNumSignInfo.html"),
            dataType: "json",
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                var c = eval(data);
                alert(c);
                $("#NumSignInfoHead").html(c[0].numSignMajor + c[0].numSignClassName);
                $("#NumSignInfoCount").html(c[0].numSignInfoCount);
                $("#NumSignNowCount").html(c[0].NumSignNowCount);
                $("#NumSignLeftStudent").html(c[0].numSignLeftStudent);
            }
        });
    });
</script>
</body>
</html>
