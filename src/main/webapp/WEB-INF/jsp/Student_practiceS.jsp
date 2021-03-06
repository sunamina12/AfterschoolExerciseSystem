<%--
  Created by IntelliJ IDEA.
  User: yangitano
  Date: 5/14/17
  Time: 11:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
<head>

    <style type="text/css">
        td {text-align:center;}
    </style>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>学生主页 - 课后练习系统</title>

    <!-- Bootstrap Core CSS -->
    <link href="<%=basePath%>vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="<%=basePath%>vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<%=basePath%>dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="<%=basePath%>vendor/morrisjs/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="<%=basePath%>vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">


</head>

<body onload="startTime()">

<div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <a class="navbar-brand">学生主页 - 课后练习系统</a>
        </div>
        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <td><span id="txt"></span></td>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw"></i> ${sessionScope.student.stuname} <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li><a data-toggle="modal" data-target="#updtprofileModal"><i class="fa fa-user fa-fw"></i> 个人资料</a>
                    </li>
                    <li><a data-toggle="modal" data-target="#updtpwdModal"><i class="fa fa-gear fa-fw"></i> 修改密码</a>
                    </li>
                    <li class="divider"></li>
                    <li><a onclick="window.location.href='<%=basePath%>login/logout'"><i class="fa fa-sign-out fa-fw"></i> 注销</a>
                    </li>
                </ul>
                <!-- /.dropdown-user -->
            </li>
            <!-- /.dropdown -->
        </ul>
        <!-- /.navbar-top-links -->

        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <li>
                        <a href="<%=basePath%>student/home"><i class="fa fa-home"></i> 主页</a>
                    </li>
                    <li>
                        <a href="<%=basePath%>student/assignment"><i class="fa fa-edit fa-fw"></i> 课后作业</a>
                    </li>
                    <li>
                        <a href="<%=basePath%>student/point"><i class="fa fa-bar-chart-o fa-fw"></i> 成绩查看</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-sitemap fa-fw"></i> 自主练习<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="<%=basePath%>student/practiceT">按题型练习</a>
                            </li>
                            <li>
                                <a href="<%=basePath%>student/practiceS">按科目及章节练习</a>
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-table fa-fw"></i> 错题练习<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="<%=basePath%>student/errorT">按题型练习</a>
                            </li>
                            <li>
                                <a href="<%=basePath%>wrong/errorS">按科目及章节练习</a>
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                </ul>
            </div>
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->
    </nav>

    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">请选择章节</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <div class="container">
                <!--内容-->
                <br><br>
                <center>
                    <form action="<%=basePath%>practice/startBySbjt" method="post" id="startform" name="startform">
                    <table class="table table-bordered">
                            <tr>
                                <td width="100">学科</td>
                                <td>
                                    <!--<input type="text" name="classid1" id="classid1" class="form-control"/>-->
                                    <select name="sbjt1" id="sbjt1" class="form-control">
                                        <option value="/" selected>---请选择---</option>
                                        <c:forEach items="${sbjtlist}" var="sbjt" varStatus="status">
                                            <option value="${sbjt}">${sbjt}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td width="100">知识点</td>
                                <td>
                                    <select name="topic1" id="topic1" class="form-control">
                                        <option value="/">---请选择---</option>
                                    </select>
                                </td>
                            </tr>
                    </table>
                    </form>
                    <button type="button" class="btn btn-success" disabled="disabled"
                            data-toggle="modal" data-target="#startModal"
                            id="start">开始练习 <i class="fa fa-pencil"></i></button>
                </center>
            </div>
        </div>

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<!-- 修改密码模态窗 -->
<div class="modal fade" id="updtpwdModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="addModalLabel">
                    修改密码
                </h4>
            </div>
            <div class="modal-body">
                <table class="table table-bordered">
                    <form action="<%=basePath%>login/updtpwdCon" method="post" id="updtpwd" name="updtpwd">
                        <tr>
                            <td width="100">原密码</td>
                            <td><input class="form-control" type="password" name="formerpwd" id="formerpwd"></td>
                        </tr>
                        <tr>
                            <td width="100">新密码</td>
                            <td>
                                <input class="form-control" type="password" name="newpwd" id="newpwd">
                            </td>
                        </tr>
                        <tr>
                            <td width="100">确认密码</td>
                            <td>
                                <input class="form-control" type="password" name="newpwdCon" id="newpwdCon">
                            </td>
                        </tr>
                    </form>
                </table>
            </div>
            <div class="modal-footer">
                <span id="pwdspan" style="float:left;"></span>
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="
                                document.getElementById('updtpwd').reset();
                                $('#pwdspan').html('');
                            "/>取消
                <button type="button" class="btn btn-primary" id="updtpwdsubmit" name="updtpwdsubmit"/>确认
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!-- 开始模态框（Modal） -->
<div class="modal fade" id="startModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="startModalLabel">
                    开始练习
                </h4>
            </div>
            <div class="modal-body">
                确认要开始吗？（练习成绩将不会被记录）
            </div>
            <div class="modal-footer">
                <span id="span" style="float:left;"></span>
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="$('#span').html('');"/>取消
                <button type="button" id="startconfirm" class="btn btn-primary"/>确认
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!-- 个人信息模态框（Modal） -->
<div class="modal fade" id="updtprofileModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="updtprofileModalLabel">
                    个人信息
                </h4>
            </div>
            <div class="modal-body">
                <table class="table table-bordered">
                    <tr>
                        <input type="hidden" name="id2" id="id2">
                        <td>学号</td>
                        <td>${student.stuid}</td>
                    </tr>
                    <tr>
                        <td>姓名</td>
                        <td>${student.stuname}</td>
                    </tr>
                    <tr>
                        <td>班级</td>
                        <td>${student.classid}</td>
                    </tr>
                    <tr>
                        <td class="last">所属专业</td>
                        <td class="last">${student.major}</td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <span id="userspan2" style="float:left;"></span>
                <button type="button" class="btn btn-default" data-dismiss="modal"/>返回
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

</body>

<!-- Time -->
<script type="text/javascript" src='<c:url value="/js/time.js"></c:url>'></script>

<!-- jQuery -->
<script type="text/javascript" src='<c:url value="/vendor/jquery/jquery.min.js"></c:url>'></script>

<!-- Bootstrap Core JavaScript -->
<script type="text/javascript" src='<c:url value="/vendor/bootstrap/js/bootstrap.min.js"></c:url>'></script>

<!-- Metis Menu Plugin JavaScript -->
<script type="text/javascript" src='<c:url value="/vendor/metisMenu/metisMenu.min.js"></c:url>'></script>

<!-- Morris Charts JavaScript -->
<script type="text/javascript" src='<c:url value="/vendor/raphael/raphael.min.js"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/vendor/morrisjs/morris.min.js"></c:url>'></script>
<script type="text/javascript" src='<c:url value="/data/morris-data.js"></c:url>'></script>

<!-- Custom Theme JavaScript -->
<script type="text/javascript" src='<c:url value="/dist/js/sb-admin-2.js"></c:url>'></script>


<script type="text/javascript">
    $().ready(function () {
        $("#sbjt1").change (
            function () {
                if($("#sbjt1").find("option:selected").val()=='/' || $("#topic1").find("option:selected").val()=='/'){
                    $("#start").attr({"disabled":true});
                }
                $.ajax({
                    type:"post",
                    url:"<%=basePath%>/assignment/getTopicBySbjt",
                    data:"sbjt=" + $("#sbjt1").val(),
                    cache:false,
                    dataType:"json",
                    success: function (json) {
                        var str = "<option value='/'>---请选择---</option>";
                        $("#topic1").html("");
                        for (var i = 0; i<json.length; i++) {
                            str +="<option value='" + json[i] + "'>"
                                + json[i] + "</option>";
                        }
                        $("#topic1").append(str);
                    }
                });
            }
        );
        $("#topic1").change (
            function () {
                if($("#sbjt1").find("option:selected").val()=='/' || $("#topic1").find("option:selected").val()=='/'){
                    $("#start").attr({"disabled":true});
                }else {
                    $("#start").attr({"disabled":false});
                }
            }
        );
    })
</script>

<script type="text/javascript">
    $().ready(function () {
        $("#startconfirm").click (
            function () {
                if ($("#sbjt1").find("option:selected").val()=='/' || $("#topic1").find("option:selected").val()=='/'){
                    $("#span").html("<font color='red'>请选择科目和章节</font>");
                } else {
                    $("#startform").submit();
                }
            }
        )
    })
</script>

<script type="text/javascript">
    $(function () {
        $("#updtpwdsubmit").click(
            function () {
                var pwd = '${student.stupwd}';
                var formerpwd = $("#formerpwd").val();
                var newpwd = $("#newpwd").val();
                var newpwdCon = $("#newpwdCon").val();
                if ($.trim(formerpwd)==''||$.trim(newpwd)==''||$.trim(newpwdCon)=='') {
                    $("#pwdspan").html("<font color='red'>不能提交空信息</font>");
                } else if ($.trim(pwd)!=$.trim(formerpwd)) {
                    $("#pwdspan").html("<font color='red'>原密码错误</font>");
                } else if ($.trim(newpwd)!=$.trim(newpwdCon)) {
                    $("#pwdspan").html("<font color='red'>两次新密码不一致</font>");
                } else if ($.trim(pwd)==$.trim(newpwd)) {
                    $("#pwdspan").html("<font color='red'>新密码与原密码重复</font>");
                } else {
                    $("#updtpwd").submit();
                    alert('修改成功，请重新登录');
                }
            });
    });
</script>

</html>
