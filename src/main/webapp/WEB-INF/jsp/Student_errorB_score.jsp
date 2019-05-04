<%--
  Created by IntelliJ IDEA.
  User: yangitano
  Date: 5/14/17
  Time: 11:33 PM
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

    <style type="text/css">
        td {text-align:center;}
        .stemautocut {
            width:500px;
            overflow:hidden;
            white-space:nowrap;
            text-overflow:ellipsis;
            -o-text-overflow:ellipsis;
            -icab-text-overflow: ellipsis;
            -khtml-text-overflow: ellipsis;
            -moz-text-overflow: ellipsis;
            -webkit-text-overflow: ellipsis;
        }
        .answerautocut {
            width:250px;
            overflow:hidden;
            white-space:nowrap;
            text-overflow:ellipsis;
            -o-text-overflow:ellipsis;
            -icab-text-overflow: ellipsis;
            -khtml-text-overflow: ellipsis;
            -moz-text-overflow: ellipsis;
            -webkit-text-overflow: ellipsis;
        }
    </style>

    <title>本次练习情况 - 课后练习系统</title>

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
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0; width: 100%">
    <div class="navbar-header">
        <a class="navbar-brand">错题练习结果 - 课后练习系统</a>
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
                    <a href="<%=basePath%>student/home"><i class="fa  fa-reply fa-fw"></i> 返回主页</a>
                </li>
            </ul>
            <!-- /.dropdown-user -->
        </li>
        <!-- /.dropdown -->
    </ul>
    <!-- /.navbar-top-links -->
</nav>

<div class="row">
    <div class="container">
        <center>
            <h1>本次练习共 ${blkTNum} 题</h1><br>
            <h1>其中回答了 ${blkANum} 题，${blkCNum} 题回答正确</h1><br>
            <h3>回答正确的题目将不会再出现在错题练习中，直至再次在作业中做错该题目</h3><br>
            <h3>如下为正确的题目</h3>
            <table class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th style="text-align:center;">题干</th>
                    <th style="text-align:center;">答案</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${blkCorrectList}" var="blk">
                    <tr>
                        <td width="250px"><div class="stemautocut" title="${blk.stem}">${blk.stem}</div></td>
                        <td width="200px"><div class="answerautocut" title="${blk.answer}">${blk.answer}</div></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </center>
    </div>
</div>
</body>

<!-- Time -->
<script type="text/javascript" src='<c:url value="/js/time.js"></c:url>'></script>

<!--if_answered-->
<script type="text/javascript" src='<c:url value="/js/cho_if_answered.js"></c:url>'></script>

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
