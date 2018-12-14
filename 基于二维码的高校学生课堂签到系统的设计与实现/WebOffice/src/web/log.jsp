<%--
  Created by IntelliJ IDEA.
  User: LiXiaobin
  Date: 2018/1/4
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,
                                     initial-scale=1.0,
                                     maximum-scale=1.0,
                                     user-scalable=no">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <style>
        body {
            background: url("/img/111.jpg") no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
            font-family: 'microsoft yahei' ,Arial,sans-serif;
        }
        .form-signin .form-control {
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
            font-size: 16px;
        }
        .form-signin .form-control:focus {
            z-index: 2;
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
<body>
<!--<div class="container">-->
<!--<div class="row">-->
<!--<div class="col-sm-12" >-->
<!--<img src="ncu.jpg" alt="" class="img-responsive" style="margin-left: 33%">-->
<!--</div>-->
<!--</div>-->
<form action="/login.html" class="form-signin" style="max-width: 300px;padding: 15px;margin: 35px auto;" method="post">
    <h2 class="form-signin-heading" style="">Please sign in</h2>
    <div class="row" style="">
        <label for="firstname" class="sr-only">账号</label>
        <div class="col-sm-12">
            <c:if test="${!empty error}">
                <p style="color:red; margin-left: 9%;font-size: 15px"><c:out value="${error}"/></p>
            </c:if>
            <input type="text" class="form-control" id="firstname" name="account" placeholder="请输入学号" style="height: 50px;max-width: 330px;margin:0 auto" required>
        </div>
    </div>
    <div class="row" style="margin-top: 20px">
        <label for="name" class="sr-only">密码</label>
        <div class="col-sm-12">
            <input type="password" class="form-control" id="name" name="password" placeholder="请输入密码" style="height: 50px;max-width: 300px;margin:0 auto" required>
        </div>
    </div>
    <div class="row" style=" max-width: 300px;margin: 10px auto">
        <div class="col-sm-12" style="padding-left: 85px">
            <label class="radio-inline">
                <input type="radio" name="person" id="optionsRadios1" value="teacher" checked> 教师
            </label>
            <label class="radio-inline">
                <input type="radio" name="person" id="optionsRadios2"  value="student"> 学生
            </label>
        </div>
    </div>
    <div class="row" style="margin-top: 20px;padding-bottom: 10px">
        <div class="col-sm-12">
            <button class="btn btn-lg btn-primary btn-block" type="submit" style="max-width: 300px;;margin-left: 7%;margin-bottom: 5%;margin: 0 auto">Sign in</button>
        </div>
    </div>
</form>
<div class="row" style=" max-width: 330px;margin: 0px auto;">
    <div class="col-sm-12">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">公告栏</h3>
            </div>
            <div class="panel-body" >
                    <textarea class="form-control" style="max-width: 270px" disabled>
                        aaaaa
                        ssss
                    </textarea>
            </div>
        </div>
    </div>

</div>
</div>

</body>
</html>
