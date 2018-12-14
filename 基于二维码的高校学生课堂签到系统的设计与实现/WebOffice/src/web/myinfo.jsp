<%--
  Created by IntelliJ IDEA.
  User: LiXiaobin
  Date: 2018/1/5
  Time: 17:13
  To change this template use File | Settings | File Templates.
  ${teacher.teacher_account}
${teacher.teacher_password}
${teacher.teacher_name}
${teacher.teacher_sex}
${teacher.teacher_age}
${teacher.teacher_job}
${teacher.teacher_phone}
${teacher.teacher_phoneex}
${teacher.teacher_emile}}
${teacher.teacher_extra}
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
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

    </style>
</head>
<body>
<%
    String mes = (String) request.getAttribute("message");
    if (mes!=null){
%>
<script>
    alert("<%=mes%>")
</script>
<%
    }
%>
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
<div class="col-xs-12 col-sm-6 col-md-6 col-sm-offset-3 col-md-offset-3" style="margin-top: 40px;margin-bottom: 50px">
    <form action="/ChangeMyMes.html" method="post">
        <table class="table table-bordered table-hover table-striped">
            <thead style="height:100px">
            <p class="text-center">个人信息</p>
            </thead>
            <tbody class="text-left">
            <tr style="display: none">
                <td><p>编号</p></td>
                <td><input type="text" value="${teacher.teacher_id}" name="teacherId"/></td>
            </tr>
            <tr>
                <td><p>姓名</p></td>
                <td><p class="nameChange">${teacher.teacher_name}</p><input class="form-control" id="newName" name="teacherName" value="${teacher.teacher_name}" type="text" style="display: none;"></td>
            </tr>
            <tr>
                <td><p>工号</p></td>
                <td><p class="accountChange" >${teacher.teacher_account}</p><input class="form-control" id="newAccount" name="teacherAccount"  value="${teacher.teacher_account}" type="number" style="display: none;"></td>
            </tr>
            <tr>
                <td><p>密码</p></td>
                <td><p class="passwordChange" >${teacher.teacher_password}</p><input class="form-control" id="newPassword" name="teacherPassword"  value="${teacher.teacher_password}" type="text" style="display: none;"></td>
            </tr>
            <tr>
                <td><p>性别</p></td>
                <td><p class="sexChange" >${teacher.teacher_sex}</p>
                    <div class="input-group" style="display: none">
                        <div class="input-group-btn">
                            <button type="button" class="btn btn-default
                            dropdown-toggle" data-toggle="dropdown">选择性别
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li>
                                    <a onclick="SetTextMan()">男</a>
                                </li>
                                <li>
                                    <a onclick="SetTextWoMen()">女</a>
                                </li>
                            </ul>
                        </div><!-- /btn-group -->
                        <script>
                            function SetTextMan() {
                                document.getElementById("newSex").value = "男";
                            }
                            function SetTextWoMen() {
                                document.getElementById("newSex").value = "女";
                            }
                        </script>
                        <input type="text" class="form-control" id="newSex" value="${teacher.teacher_sex}" name="teacherSex" >
                    </div><!-- /input-group -->
                </td>
            </tr>
            <tr>
                <td><p>年龄</p></td>
                <td><p class="ageChange" >${teacher.teacher_age}</p><input class="form-control" id="newAge" name="teacherAge" value="${teacher.teacher_age}" type="text" style="display: none;"></td>
            </tr>
            <tr>
                <td><p>专业</p></td>
                <td><p class="jobChange" >${teacher.teacher_major}</p><input type="text" id="newMajor" name="teacherMajor" value="${teacher.teacher_major}" style="display: none">
                    <select class="form-control" id="selectMajor" style="display: none">
                        <option selected>${teacher.teacher_major}</option>
                        <c:if test="${!empty major}">
                            <c:forEach items="${major}" var="major">
                                <option>${major.major_name}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <script>
                        $("#selectMajor").change(function () {
                            $("#newMajor").val($("#selectMajor").val());
                        });
                    </script>
                </td>
            </tr>
            <tr>
                <td><p>联系方式</p></td>
                <td><p class="phoneChange" >${teacher.teacher_phone}</p><input class="form-control" id="newPhone" name="teacherPhone" value="${teacher.teacher_phone}" type="text" style="display: none;"></td>
            </tr>
            <tr>
                <td><p>紧急联系方式</p></td>
                <td><p class="phone1Change"  >${teacher.teacher_phoneex}</p><input class="form-control" id="newPhone1"  name="teacherPhoneex" value="${teacher.teacher_phoneex}" type="text" style="display: none;"></td>
            </tr>
            <tr>
                <td><p>邮箱</p></td>
                <td><p class="mailChange" >${teacher.teacher_emile}</p><input class="form-control" id="newMail" name="teacherEmile" value="${teacher.teacher_emile}" type="text" style="display: none;"></td>
            </tr>
            <tr>
                <td><p>其他描述</p></td>
                <td><p class="extrChange"  >${teacher.teacher_extra}</p><input class="form-control" id="newExt"  name="teacherExtr" value="${teacher.teacher_extra}" type="text" style="display: none;"></td>
            </tr>
            </tbody>
            <tfoot style="border: 0">
            <tr>
                <td></td>
                <td class="text-right">
                    <p class="btn bg-primary " style="margin-right: 20px" id="changeInfo">修改</p>
                    <p class="btn bg-primary"  style="margin-right: 20px" id="makesureInfo">确认</p>
                    <input class="btn bg-primary" type="submit" value="保存" id="saveInfo" disabled>
                    <script>
                        $("#changeInfo").click(function () {
                            $(".nameChange").hide();
                            $(".accountChange").hide();
                            $(".passwordChange").hide();
                            $(".sexChange").hide();
                            $(".ageChange").hide();
                            $(".jobChange").hide();
                            $(".phoneChange").hide();
                            $(".phone1Change").hide();
                            $(".mailChange").hide();
                            $(".extrChange").hide();
                            $("#newName").show();
                            $("#newAccount").show();
                            $("#newPassword").show();
                            $("#newAge").show();
                            $("#selectMajor").show();
                            $("#newPhone").show();
                            $("#newPhone1").show();
                            $("#newMail").show();
                            $("#newExt").show();
                            $(".input-group").show();
                            $("#saveInfo").attr("disabled",true);
                        });
                        $("#makesureInfo").click(function () {
                            //alert($(".nameChange").html());
                            // alert($("#newName").val());
                            $(".nameChange").html($("#newName").val()) ;
                            $(".accountChange").html($("#newAccount").val());
                            $(".passwordChange").html($("#newPassword").val());
                            $(".sexChange").html($("#newSex").val());
                            $(".ageChange").html($("#newAge").val());
                            $(".jobChange").html($("#selectMajor").val());
                            $(".phoneChange").html($("#newPhone").val());
                            $(".phone1Change").html($("#newPhone1").val());
                            $(".mailChange").html($("#newMail").val());
                            $(".extrChange").html($("#newExt").val());
                            $(".nameChange").show();
                            $(".accountChange").show();
                            $(".passwordChange").show();
                            $(".sexChange").show();
                            $(".ageChange").show();
                            $(".jobChange").show();
                            $(".phoneChange").show();
                            $(".phone1Change").show();
                            $(".mailChange").show();
                            $(".extrChange").show();
                            $("#newName").hide();
                            $("#newAccount").hide();
                            $("#newPassword").hide();
                            $("#newAge").hide();
                            $("#selectMajor").hide();
                            $("#newPhone").hide();
                            $("#newPhone1").hide();
                            $("#newMail").hide();
                            $("#newExt").hide();
                            $(".input-group").hide();
                            $("#saveInfo").attr('disabled',false)
                        });
                    </script>
                </td>
            </tr>
            </tfoot>
        </table>
    </form>
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
                    <ul class="dropdown-menu">
                        <c:if test="${!empty myClassInfo}">
                            <c:forEach items="${myClassInfo}" var="myClassInfo">
                                <li><a href="/detailedInf.html?detailedMajor=${teacher.teacher_major}&detailedName=${myClassInfo.class_name}">
                                        ${teacher.teacher_major}${myClassInfo.class_name}
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
