<%--
  Created by IntelliJ IDEA.
  User: yangitano
  Date: 3/29/17
  Time: 2:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <base href="<%=basePath%>">

    <title>注册</title>
    <link rel="stylesheet" href="css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="css/bootstrap-theme.css" type="text/css">
    <script type="text/javascript" src='<c:url value="/js/bootstrap.min.js"></c:url>'></script>
    <script type="text/javascript" src='<c:url value="/js/npm.js"></c:url>'></script>
    <script type="text/javascript" src='<c:url value="/js/jquery-1.10.2.min.js"></c:url>'></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="dist/js/sb-admin-2.js"></script>

    <!-- MetisMenu CSS -->
    <link href="vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

</head>

<body>
<div class="container">
    <div class="row bg">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">注册</h3>
                </div>
                <div class="panel-body">
                    <form action="<%=basePath%>login/register/registerCon" method="post" id="regiform" name="regiform">
                        <input class="form-control" type="text" size="10" name="stuid" id="stuid" placeholder="学号" AUTOCOMPLETE="OFF">
                        <center><span id="userspan" >&nbsp</span></center>
                        <input class="form-control" type="password" size="10" name="pwd1" id="pwd1" placeholder="密码" AUTOCOMPLETE="OFF">
                        <br>
                        <input class="form-control" type="password" size="10" name="pwd2" id="pwd2" placeholder="确认密码" AUTOCOMPLETE="OFF">
                        <center><span id="pwdspan">&nbsp</span></center>
                        <input class="form-control" type="text" size="10" name="stuname" id="stuname" placeholder="姓名" AUTOCOMPLETE="OFF">
                        <center><span id="stunamespan">&nbsp</span></center>
                        <input class="form-control" type="text" size="10" name="classid" id="classid" placeholder="班级" AUTOCOMPLETE="OFF">
                        <center><span id="classidspan">&nbsp</span></center>
                        <select name="major" id="major" class="form-control">
                            <option value="/" selected="selected">---请选择---</option>
                            <option value="计算机科学与技术">计算机科学与技术</option>
                            <option value="计算机科学与技术（电气方向）">计算机科学与技术（电气方向）</option>
                            <option value="计算机科学与技术（创新创业）">计算机科学与技术（创新创业）</option>
                            <option value="软件工程">软件工程</option>
                            <option value="信息安全">信息安全</option>
                        </select>
                        <br>
                        <input class="btn btn-lg btn-success btn-block" type="button" name="register" id="register" value="注册">
                        <button class="btn btn-lg btn-default btn-block" type="reset">重置</button>
                    </form>
                    <br>
                    <a href="<%=basePath%>login/home">返回</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $(function () {
        var flag1 = false;
        $("#stuid").blur(
            function () {
                var id = $("#stuid").val();
                if ($.trim(id).length != 8) {
                    $("#userspan").html("<font color='red'>学号应位8位数字</font>");
                    flag1 = false;
                } else {
                    var url = "<%=basePath%>login/register/validateUser";
                    var args = { "name": $.trim(id) };
                    $.post(url, args,
                        function (data) {
                            if ($.trim(data) == "error") {
                                $("#userspan").html("<font color='red'>学号已注册</font>");
                                flag1 = false;
                            } else if ($.trim(data) == "success") {
                                $("#userspan").html("学号未注册").css({color: 'green'});
                                flag1 = true;
                            }
                        })
                }
            });
    });
</script>
<script language="javascript">
    function regisubmit(){
        var a=document.getElementById("userspan").innerText;
        var b=document.getElementById("pwdspan").innerText;
        var c=document.getElementById("stunamespan").innerText;
        var d=document.getElementById("classidspan").innerText;
        var f=document.getElementById("major").value();
        if(a!="&nbsp"||b!="&nbsp"||c!="&nbsp"||d!="&nbsp"||f=="/"){
            alert("信息不完整或不正确，请重新填写");
            //window.location.href="<%=basePath%>login/register";
        }else {
            alert("注册成功");
            document.getElementById("regiform").submit();
        }
    }
</script>
<script language="JavaScript">
    $(function () {
        $("#register").click(
            function () {
                var stuid = $("#stuid").val();
                var pwd1 = $("#pwd1").val();
                var pwd2 = $("#pwd2").val();
                var stuname = $("#stuname").val();
                var classid = $("#classid").val();
                var major = $("#major").val();
                var userspan = $("#userspan").html();
                if($.trim(userspan)!="学号未注册" || $.trim(stuid).length!=8 || $.trim(pwd1)!=$.trim(pwd2)
                    || $.trim(stuname).length==0 || $.trim(classid).length!=7 || $.trim(major).length==1){
                    alert("信息不完整或不正确，请重新填写 \n所有项目都为必填项，学号应位8位数字,班级应为7位数字");
                }else {
                    alert("注册成功");
                    document.getElementById("regiform").submit();
                }
            }
        )
    })
</script>
<script language="JavaScript">
    $(function () {
        var flag = false;
        $("#pwd2").blur(
            function () {
                var pwd1 = $("#pwd1").val();
                var pwd2 = $("#pwd2").val();
                if($.trim(pwd1)!=$.trim(pwd2)){
                    $("#pwdspan").html("<font color='red'>两次密码不一致</font>");
                } else {
                    $("#pwdspan").html("&nbsp");
                }
            }
        )
    })
</script>
</html>
