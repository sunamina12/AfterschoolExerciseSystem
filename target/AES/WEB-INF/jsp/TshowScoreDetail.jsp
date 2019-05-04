<%--
  Created by IntelliJ IDEA.
  User: yangitano
  Date: 5/8/17
  Time: 10:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: 板野洋洋
  Date: 2017/3/2
  Time: 13:46
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
        .stemautocut {
            width:300px;
            overflow:hidden;
            white-space:nowrap;
            text-overflow:ellipsis;
            -o-text-overflow:ellipsis;
            -icab-text-overflow: ellipsis;
            -khtml-text-overflow: ellipsis;
            -moz-text-overflow: ellipsis;
            -webkit-text-overflow: ellipsis;
        }
        .stemautocut1 {
            width:750px;
            overflow:hidden;
            white-space:nowrap;
            text-overflow:ellipsis;
            -o-text-overflow:ellipsis;
            -icab-text-overflow: ellipsis;
            -khtml-text-overflow: ellipsis;
            -moz-text-overflow: ellipsis;
            -webkit-text-overflow: ellipsis;
        }
        .optionautocut {
            width:100px;
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
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>教师主页 - 课后练习系统</title>

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
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand">教师主页 - 课后练习系统</a>
        </div>
        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <td><span id="txt"></span></td>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw"></i> ${sessionScope.teacher.tchname} <i class="fa fa-caret-down"></i>
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
                        <a href="<%=basePath%>teacher/home"><i class="fa fa-home"></i> 主页</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-sitemap fa-fw"></i> 试题管理<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="<%=basePath%>teacher/choManagement">单选题</a>
                            </li>
                            <li>
                                <a href="<%=basePath%>teacher/blkManagement">填空题</a>
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    <li>
                        <a href="<%=basePath%>assignment/TshowAllAssignment"><i class="fa fa-edit fa-fw"></i> 课后作业管理</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> 作业情况管理<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="<%=basePath%>Tstudent/showAllStudent">按学号查看</a>
                            </li>
                            <li>
                                <a href="<%=basePath%>Tstudent/showAllAsgn">按作业号查看</a>
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
                <h1 class="page-header">${stu.classid}班${stu.stuname}的作业情况</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <div class="container">
                <!--内容-->

                <button type="button" class="btn btn-default"
                        onclick="window.location.href=('<%=basePath%>teacher/home');">返回 <i class="fa fa-reply"></i></button>
                <br><br>
                <h4>
                    作业编号：${asgn.asgnid}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    学科：${asgn.sbjt}&nbsp;&nbsp;&nbsp;&nbsp;
                    知识点：${asgn.topic}<br><br>
                    完成时间：${asgn.asgntime}分钟&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    截止时间：<fmt:formatDate type="both" timeStyle="short" value="${asgn.deadline}" />
                </h4>
                <br>
                <div>选择题：</div>
                <table class="table table-striped table-bordered table-hover" id="table1">
                    <thead>
                    <tr>
                        <th style="text-align:center;">题干</th>
                        <th style="text-align:center;">选项A</th>
                        <th style="text-align:center;">选项B</th>
                        <th style="text-align:center;">选项C</th>
                        <th style="text-align:center;">选项D</th>
                        <th style="text-align:center;">正确答案</th>
                        <th style="text-align:center;">你的答案</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${choList}" var="cho" varStatus="status">
                        <tr>
                            <input type="hidden" id="id_cho${status.index}" value="${cho.id}">
                            <td width="300px"><div class="stemautocut" title="${cho.stem}">${cho.stem}</div></td>
                            <td width="150px"><div class="optionautocut" title="${cho.option1}">${cho.option1}</div></td>
                            <td width="150px"><div class="optionautocut" title="${cho.option2}">${cho.option2}</div></td>
                            <td width="150px"><div class="optionautocut" title="${cho.option3}">${cho.option3}</div></td>
                            <td width="150px"><div class="optionautocut" title="${cho.option4}">${cho.option4}</div></td>
                            <td width="50px"><div id="answer${status.index}" title="${cho.answer}">${cho.answer}</div></td>
                            <td width="50px"><div id="uranswer${status.index}" title="${cho.answer}"><font color='green'>${cho.answer}</font></div></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div>填空题：</div>
                <table class="table table-striped table-bordered table-hover"id="table2">
                    <thead>
                    <tr>
                        <th style="text-align:center;">题干</th>
                        <th style="text-align:center;">正确答案</th>
                        <th style="text-align:center;">你的答案</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${blkList}" var="blk" varStatus="status">
                        <tr>
                            <input type="hidden" id="id_blk${status.index}" value="${blk.id}">
                            <td width="400px"><div class="stemautocut1" title="${blk.stem}">${blk.stem}</div></td>
                            <td width="150px"><div id="1answer${status.index}" title="${blk.answer}">${blk.answer}</div></td>
                            <td width="150px"><div id="1uranswer${status.index}" title="${blk.answer}"><font color='green'>${blk.answer}</font></div></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div>注：本页显示的题目顺序与学生作业顺序不一致，作业顺序为随机生成</div>

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
                        <td>工号</td>
                        <td>${teacher.tchid}</td>
                    </tr>
                    <tr>
                        <td>姓名</td>
                        <td>${teacher.tchname}</td>
                    </tr>
                    <tr>
                        <td>所属专业</td>
                        <td>${teacher.major}</td>
                    </tr>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"/>返回
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

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

</body>

<script type="text/javascript">
    $().ready(function () {
        $.ajax({
            type:"post",
            url:"<%=basePath%>/score/getStuAnswer",
            data:"stuid=" + ${stuid} + "&asgnid=" + ${asgn.asgnid},
            cache:false,
            dataType:"json",
            success: function (data) {
                var length1 = $("#table1").find("tr").length-1;
                var length2 = $("#table2").find("tr").length-1;
                for (var i = 0;i<length1;i++) {
                    var id_cho = $("#id_cho"+i).val();
                    for (var j = 0;j<data.length;j++) {
                        var questype = data[j].questype;
                        if ($.trim(questype)=='cho') {
                            var id = data[j].quesid;
                            if ($.trim(id)==$.trim(id_cho)) {
                                var uranswer = data[j].uranswer;
                                $("#uranswer"+i).css('color','red');
                                $("#uranswer"+i).html(uranswer);
                                $("#uranswer"+i).attr("title",uranswer);
                            }
                        }
                    }
                }
                for (var i = 0;i<length2;i++) {
                    var id_blk = $("#id_blk"+i).val();
                    for (var j = 0;j<data.length;j++) {
                        var questype = data[j].questype;
                        if ($.trim(questype)=='blk') {
                            var id = data[j].quesid;
                            if ($.trim(id)==$.trim(id_blk)) {
                                var uranswer = data[j].uranswer;
                                $("#1uranswer"+i).css('color','red');
                                $("#1uranswer"+i).html(uranswer);
                                $("#1uranswer"+i).attr("title",uranswer);
                            }
                        }
                    }
                }
            }
        });
    })
</script>

<script type="text/javascript">
    $(function () {
        $("#updtpwdsubmit").click(
            function () {
                var pwd = '${teacher.tchpwd}';
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
