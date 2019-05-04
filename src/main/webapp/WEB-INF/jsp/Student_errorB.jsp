<%--
  Created by IntelliJ IDEA.
  User: yangitano
  Date: 5/14/17
  Time: 11:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>

    <title>错题练习（填空题部分） - 课后练习系统</title>

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
<!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0; width: 100%" id="nav">
    <div class="navbar-header">
        <a class="navbar-brand">错题练习（填空题部分） - 课后练习系统</a>
    </div>
    <!-- /.navbar-header -->

    <ul class="nav navbar-top-links navbar-right">
        <span id="txt"></span>
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-user fa-fw"></i> ${sessionScope.student.stuname} <i class="fa fa-caret-down"></i>
            </a>
            <ul class="dropdown-menu dropdown-user">
                <li>
                    <a data-toggle="modal" data-target="#sbmtModal" href=""><i class="fa  fa-check-square-o fa-fw"></i> 提交</a>
                </li>
                <li>
                    <a data-toggle="modal" data-target="#exitModal"><i class="fa fa-power-off fa-fw"></i> 退出练习</a>
                </li>
            </ul>
            <!-- /.dropdown-user -->
        </li>
        <!-- /.dropdown -->
    </ul>
    <!-- /.navbar-top-links -->
</nav>

<table class="table table-striped table-bordered">
    <form action="<%=basePath%>wrong/submitB" method="post" id="sbmtform" name="sbmtform">
        <c:forEach items="${blkList}" var="blk" varStatus="status">
            <tr>
                <td id="num${status.index+1}" bgcolor="red" width="50px"><div>No.${status.index+1}</div></td>
                <td><div title="${blk.stem}">${blk.stem}</div></td>
                <td><div><input type="text" name="ans${status.index}" id="ans${status.index}" AUTOCOMPLETE="OFF"
                                class="form-control" onchange="chred1(this,'num${status.index+1}')"></div></td>
            </tr>
        </c:forEach>
    </form>
</table>
<br><br>


<!-- 提交模态框（Modal） -->
<div class="modal fade" id="sbmtModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="sbmtModalLabel">
                    提交
                </h4>
            </div>
            <div class="modal-body">
                确认要提交吗？
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"/>取消
                <button type="button" class="btn btn-primary" onclick="document.getElementById('sbmtform').submit();"/>确认
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!-- 退出模态框（Modal） -->
<div class="modal fade" id="exitModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="exitModalLabel">
                    退出练习
                </h4>
            </div>
            <div class="modal-body">
                确认要退出吗？
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"/>取消
                <button type="button" class="btn btn-primary" onclick="window.location.href=('<%=basePath%>student/errorT')"/>确认
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>

<!-- Time -->
<script type="text/javascript" src='<c:url value="/js/time.js"></c:url>'></script>

<!--Navi Fix-->
<script type="text/javascript" src='<c:url value="/js/navi_fix.js"></c:url>'></script>

<!--if_answered-->
<script type="text/javascript" src='<c:url value="/js/blk_if_answered.js"></c:url>'></script>

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

</html>
