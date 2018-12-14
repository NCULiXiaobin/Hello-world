<%--
  Created by IntelliJ IDEA.
  User: LiXiaobin
  Date: 2017/11/8
  Time: 16:52
  To change this template use File | Settings | File Templates.
  !!!!!!!!!!!!!!!!!如果要通过url传输中文的话要使用encodeURI("search?keyword="+$("#keybord").val())来解决！！！！！！！！
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <style>
      #myDiv{
        position: absolute;
        left: 35%;
        top:30%;
      }
      input{
        height: 30px;
      }
      .mouseOver{
        background-color:#708090;
        color:#FFFAFA;
      }
      .mouseOut{
        background-color:#FFFAFA;
        color:#000000;
      }
    </style>
    <script src="http://libs.baidu.com/jquery/2.1.1/jquery.min.js"></script>
    <title>ajax智能提示搜索框</title>
  </head>
  <body>
    <div id="myDiv">
      <input type="text" id="keybord" size="50">
      <input type="button" value="百度一下" width="50px" id="cl">
      <div id="popDiv">
        <table id="content_table" bgcolor="#FFFAFA" border="0" cellpadding="0" cellspacing="0">
          <tbody id="content_table_body">
          </tbody>
        </table>
      </div>
    </div>
  <script>
    $("#keybord").keyup(function(){
      $.ajax({
        type:"GET",
        url:encodeURI("search?keyword="+$("#keybord").val()),
        dataType:"text",
        success:function(data){
          setContent(data);
        }
      });
    });
    $("#keybord").focus(function(){
      if($("#keybord").val()==""){
        clearContent();
        return;
      }
      $.ajax({
        type:"GET",
        url:encodeURI("search?keyword="+$("#keybord").val()),
        dataType:"text",
        success:function(data){
          setContent(data);
        }
      });
    });
    function setContent(data){
        clearContent();
        setLocation();
      if(data!="[]") {
        var ss = data.replace("[",",");
        var sss = ss.replace("]",",");
        var s = sss.trim().split(",");
        var size = s.length;
        //alert(size);
        for(var i=0;i<size;i++){
          var nextNode = s[i];
            if(nextNode!=null&&nextNode.length>0){
                var tr = document.createElement("tr");
                var td = document.createElement("td");
                td.setAttribute("border","0");
                td.setAttribute("bgcolor","#FFFAFA");
                td.setAttribute("height","25px");
                var text = document.createTextNode(nextNode);
                //alert(nextNode);
                td.appendChild(text);
                tr.appendChild(td);
                td.onmouseover=function(){
                    this.className='mouseOver';
                };
                td.onmouseout=function(){
                    this.className='mouseOut';
                };
                td.onmousedown=function(){
                    document.getElementById("keybord").value=this.innerText;
                };
                document.getElementById("content_table_body").appendChild(tr);
            }
        }
      }
      else {
        clearContent();
      }
    }
    function clearContent(){
      var contentTableBody=document.getElementById("content_table_body");
      var size=contentTableBody.childNodes.length;
      for(var i=size-1;i>0;i--){
        contentTableBody.removeChild(contentTableBody.childNodes[i]);
      }
      document.getElementById("popDiv").style.border="none";
    }
    $("#keybord").blur(function(){
      clearContent();
    });
    function setLocation(){
      var content = document.getElementById("keybord");
      var width=content.offsetWidth;//获得输入框的宽度
      var left=content["offsetLeft"];//到左边框的距离
      var top=content["offsetTop"]+content.offsetHeight;//到顶部的距离
      var popDiv = document.getElementById("popDiv");
      popDiv.style.border="solid 1px black";
      popDiv.style.left=left+"px";
      popDiv.style.top=top+"px";
      popDiv.style.width=width+"px";
      document.getElementById("content_table").style.width=width+"px";
    }
  </script>
  </body>
</html>
