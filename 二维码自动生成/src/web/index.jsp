<%--
  Created by IntelliJ IDEA.
  User: LiXiaobin
  Date: 2018/1/23
  Time: 14:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>二维码签到</title>
    <script src="/jquery.min.js"></script>
    <link rel="stylesheet" href="/bootstrap.min.css">
    <script src="/bootstrap.min.js"></script>
</head>
<body>
<input type="button" value="aa" id="aaa" data-toggle="modal" data-target="#myModal">
<script>
    $("#aaa").click(function () {
        $("#erweima").show();
        $("#erweima").attr("src","code.jspx");
    });
</script>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    模态框（Modal）标题
                </h4>
            </div>
            <div class="modal-body">
                <p class="center-block" style="height:200px;width:200px;"><img  id="erweima"/></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary">
                    提交更改
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>
