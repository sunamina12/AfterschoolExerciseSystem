<%--
  Created by IntelliJ IDEA.
  User: yangitano
  Date: 3/30/17
  Time: 8:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>登录</title>
    <link rel="stylesheet" href="css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="css/bootstrap-theme.css" type="text/css">
    <script type="text/javascript" src='<c:url value="/js/bootstrap.min.js"></c:url>'></script>
    <script type="text/javascript" src='<c:url value="/js/npm.js"></c:url>'></script>
    <script type="text/javascript" src='<c:url value="/js/jquery-1.10.2.min.js"></c:url>'></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../dist/js/sb-admin-2.js"></script>

    <!-- MetisMenu CSS -->
    <link href="../vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

</head>

<body>
<div class="container">
    <div class="row bg">
        <div class="col-md-4 col-md-offset-4">
            <br><br><br><br>
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">请登录</h3>
                </div>
                <div class="panel-body">
                    <form id="login" action="login/home/loginCon" method="post">
                        <input class="form-control" type="text" size="10" name="id" id="id" placeholder="用户名">
                        <br>
                        <input class="form-control" type="password" size="10" name="pwd" id="pwd"
                               placeholder="密码">
                        <br>
                        <select class="form-control" name="authority" id="authority">
                            <option value="stu">学生</option>
                            <option value="tch">教师</option>
                            <option value="admin">管理员</option>
                        </select>
                    </form>
                    <br>
                    <input type="button" name="submit" id="submit" value="登录" class="btn btn-lg btn-success btn-block">
                    <br>
                    <a href="<%=basePath%>login/register">还没有账号？去注册（仅支持学生）</a>
                    <br><br>
                    <center><span id="userspan">&nbsp</span></center>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script type="text/javascript">
    $(function () {
        var flag = false;
        $("#submit").click(
            function () {
                var id = $("#id").val();
                var pwd = $("#pwd").val();
                var authority = $("#authority").val();
                if ($.trim(id) == '' || $.trim(pwd) == '') {
                    $("#userspan").html("<font color='red'>用户名和密码不能为空</font>");
                    flag = false;
                } else {
                    var url = "<%=basePath%>login/home/validateLogin1";
                    var args = {"id": $.trim(id), "pwd": $.trim(pwd), "authority": $.trim(authority)};
                    $.post(url, args,
                        function (data) {
                            if ($.trim(data) == "error") {
                                //alert("用户名、密码或身份错误");
                                $("#userspan").html("<font color='red'>用户名、密码或身份错误</font>");
                                //window.location.href = "<%=basePath%>login/home";
                                flag = false;
                            }
                        })
                }
            });
    });
</script>
</html>