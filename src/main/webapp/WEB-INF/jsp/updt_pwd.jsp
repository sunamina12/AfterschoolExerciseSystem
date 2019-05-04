<%--
  Created by IntelliJ IDEA.
  User: yangitano
  Date: 5/18/17
  Time: 3:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" import="com.sy.pojo.*" pageEncoding="UTF-8" %>
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

    <title>修改密码</title>
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
                    <h3 class="panel-title">修改密码</h3>
                </div>
                <div class="panel-body">
                    <form id="updtpwd" action="login/updtpwdCon" method="post">
                        <input class="form-control" type="password" size="10" name="formerpwd" id="formerpwd" placeholder="原密码" >
                        <br>
                        <input class="form-control" type="password" size="10" name="newpwd" id="newpwd"
                               placeholder="新密码">
                        <br>
                        <input class="form-control" type="password" size="10" name="newpwdCon" id="newpwdCon"
                               placeholder="确认密码">
                        <br>
                    </form>
                    <input type="button" name="submit" id="submit" value="确认" class="btn btn-lg btn-primary btn-block">
                    <br>
                    <center><span id="pwdspan">&nbsp</span></center>
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
                var pwd = ${pwd};
                var formerpwd = $("#formerpwd").val();
                var newpwd = $("#newpwd").val();
                var newpwdCon = $("#newpwdCon").val();
                if ($.trim(formerpwd)==''||$.trim(newpwd)==''||$.trim(newpwdCon)=='') {
                    $("#pwdspan").html("<font color='red'>不能提交空信息</font>");
                } else if ($.trim(pwd)!=$.trim(formerpwd)) {
                    $("#pwdspan").html("<font color='red'>原密码错误</font>");
                } else if ($.trim(newpwd)!=$.trim(newpwdCon)) {
                    $("#pwdspan").html("<font color='red'>两次新密码不一致</font>");
                } else if ($.trim(formerpwd)==$.trim(newpwd)) {
                    $("#pwdspan").html("<font color='red'>新密码与原密码重复</font>");
                } else {
                    $("#updtpwd").submit();
                    alert('请重新登录');
                }
            });
    });
</script>
</html>
