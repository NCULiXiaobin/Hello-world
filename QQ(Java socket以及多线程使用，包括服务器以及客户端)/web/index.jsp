<%--
  Created by IntelliJ IDEA.
  User: LiXiaobin
  Date: 2017/12/20
  Time: 14:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <title>聊天室注册</title>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="css/bootstrap.min.css" >
  <script src="js/jquery.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <style>
    body{
      overflow-x: hidden;
      overflow-y: hidden;
    }
    .form-group{
      margin-top: 30px;
    }
    .pic{
      z-index: 99;
      display: none;
    }
  </style>
  <script>
    $(window).ready(function() {
      $(".pic").eq(0).show();
      var i = 0;
      function fad() {
        var m = i % 3;
        var n = (i + 1) % 3;
        $(".pic").eq(m).fadeOut(0);
        $(".pic").eq(n).fadeIn(1000);
        i++;
      };
      setInterval(fad, 3000);
    });
  </script>
</head>
<body>
<div>
  <img src="img/图像%201.png" alt=""style="width: 570px;height: 760px;" class="pic">
  <img src="img/图像%202.png" alt=""style="width: 570px;height: 760px;" class="pic">
  <img src="img/图像%203.png" alt=""style="width: 570px;height: 760px;" class="pic">
</div>
<div id="righttext" style="margin-top: -800px">
  <form action="register.action" method="post" class="form-horizontal" role="form" style="padding-left: 750px;padding-top: 200px">
    <div class="form-group">
      <h2 style="margin-top: -80px">欢迎使用XXX聊天室</h2>
    </div>
    <div class="form-group">
      <label for="user_name" class="col-sm-2 control-label">昵称</label>
      <div class="col-sm-6">
        <input type="text" class="form-control" id="user_name" name="user_name" placeholder="请输入昵称" required>
      </div>
    </div>
    <div class="form-group">
      <label for="user_account" class="col-sm-2 control-label">账号</label>
      <div class="col-sm-6">
        <input type="text" class="form-control" id="user_account" name="account" placeholder="请输入账号" required>
      </div>
    </div>
    <div class="form-group">
      <label for="password" class="col-sm-2 control-label">密码</label>
      <div class="col-sm-6">
        <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码" required>
      </div>
    </div>
    <div class="form-group">
      <label for="user_sex" class="col-sm-2 control-label">性别</label>
      <div class="col-sm-6">
        <select class="form-control" id="user_sex" name="user_sex" required>
          <option disabled selected value></option>
          <option>男</option>
          <option>女</option>
        </select>
      </div>
    </div>
    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
        <button type="submit" class="btn btn-default">注册</button>
      </div>
    </div>
    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10"style="position: relative; left: 500px;top: 170px;">
        @NCU.LiXiaobin
      </div>
    </div>
  </form>
</div>
</body>
</html>