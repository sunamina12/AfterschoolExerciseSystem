<%--
  Created by IntelliJ IDEA.
  User: yangitano
  Date: 4/24/17
  Time: 8:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
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

<body onload="startTime();mindate()">

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
                <h1 class="page-header">作业列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <div class="container">
                <button type="button" class="btn btn-success" data-toggle="modal" data-target="#addModal" id="add">添加作业 <i class="fa fa-plus"></i></button>
                <br><br>
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th style="text-align:center;">编号</th>
                        <th style="text-align:center;">学科</th>
                        <th style="text-align:center;">知识点</th>
                        <th style="text-align:center;">面向班级</th>
                        <th style="text-align:center;">选择题数量</th>
                        <th style="text-align:center;">填空题数量</th>
                        <th style="text-align:center;">完成时间</th>
                        <th style="text-align:center;">截止时间</th>
                        <th style="text-align:center;" colspan="3">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="asgn">
                        <tr>
                            <td><div title="${asgn.asgnid}">${asgn.asgnid}</div></td>
                            <td><div title="${asgn.sbjt}">${asgn.sbjt}</div></td>
                            <td><div title="${asgn.topic}">${asgn.topic}</div></td>
                            <td><div title="${asgn.classid}">${asgn.classid}</div></td>
                            <td><div title="${asgn.chonum}">${asgn.chonum}</div></td>
                            <td><div title="${asgn.blknum}">${asgn.blknum}</div></td>
                            <td><div title="${asgn.asgntime}分钟">${asgn.asgntime}分钟</div></td>
                            <td><div title="<fmt:formatDate type="both" timeStyle="short" value="${asgn.deadline}" />">
                                <fmt:formatDate type="both" timeStyle="short" value="${asgn.deadline}" />
                            </div>
                            </td>
                            <td width="1px"><button class="btn btn-info" id="detail"
                                                    onclick="window.location.href=('<%=basePath%>assignment/TshowDetail/'+${asgn.asgnid});"/>查看详细 <i class="fa fa-search"></i>
                            </td>
                            <td width="1px"><button class="btn btn-danger" data-toggle="modal" data-target="#delModal" id="del"
                                                    onclick="opendel('${asgn.id}')"/>删除 <i class="fa fa-times"></i>
                            </td>
                            <td width="1px"><button class="btn btn-warning" data-toggle="modal" data-target="#updtModal" id="updt"
                                                    onclick="openupdt('${asgn.id}')"/>修改 <i class="fa fa-pencil"></i>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <br>
            </div>

            <!-- 删除模态框（Modal） -->
            <div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="delModalLabel">
                                删除单选题
                            </h4>
                        </div>
                        <div class="modal-body">
                            确认要删除吗？
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"/>取消
                            <button type="button" class="btn btn-primary" onclick="return deleteAsgnCon($('#hidden').val())"/>确认
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal -->
            </div>

            <!-- 添加模态框（Modal） -->
            <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="addModalLabel">
                                添加作业
                            </h4>
                        </div>
                        <div class="modal-body">
                            <table class="table table-bordered">
                                <form action="TaddAssignment/addAsgnCom" method="post" id="addform" name="addform">
                                    <tr>
                                        <td width="100">编号<font color='red'>* <br>(不可修改)</font></td>
                                        <td><input type="text" name="id1" id="id1" class="form-control" AUTOCOMPLETE="OFF"></td>
                                    </tr>
                                    <tr>
                                        <td width="100">学科<font color='red'>* <br>(不可修改)</font></td>
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
                                        <td width="100">知识点<font color='red'>* <br>(不可修改)</font></td>
                                        <td>
                                            <select name="topic1" id="topic1" class="form-control">
                                                <option value="/">---请选择---</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="100">面向班级<font color='red'>*</font></td>
                                        <td>
                                            <!--<input type="text" name="classid1" id="classid1" class="form-control"/>-->
                                            <select name="classid1" id="classid1" class="form-control">
                                                <option value="/" selected>---请选择---</option>
                                                <c:forEach items="${classlist}" var="classid" varStatus="status">
                                                    <option value="${classid}">${classid}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="100">选择题数量<font color='red'>* <br>(不可修改)</font></td>
                                        <td><input type="text" name="choNum1" id="choNum1" class="form-control" AUTOCOMPLETE="OFF"></td>
                                    </tr>
                                    <tr>
                                        <td width="100">填空题数量<font color='red'>* <br>(不可修改)</font></td>
                                        <td><input type="text" name="blkNum1" id="blkNum1" class="form-control" AUTOCOMPLETE="OFF"></td>
                                    </tr>
                                    <tr>
                                        <td width="100">完成时间<font color='red'>*</font></td>
                                        <td><input type="text" name="asgntime1" id="asgntime1" class="form-control" AUTOCOMPLETE="OFF"></td>
                                    </tr>
                                    <tr>
                                        <td width="100">截止时间<font color='red'>*</font></td>
                                        <td><input type="datetime-local" name="deadline1" id="deadline1" class="form-control"></td>
                                    </tr>
                                </form>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <span id="userspan1" style="float:left;"></span>
                            <button type="button" class="btn btn-default" data-dismiss="modal" onclick="
                                document.getElementById('addform').reset();
                                $('#userspan1').html('');
                                $('#topic1').append('<option value='/'>---请选择---</option>');
                                $('#choNum1').attr('placeholder','');
                                $('#blkNum1').attr('placeholder','');
                            "/>取消
                            <button type="button" class="btn btn-primary" id="subaddform" name="subaddform"/>确认
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal -->
            </div>

            <!-- 修改模态框（Modal） -->
            <div class="modal fade" id="updtModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="updtModalLabel">
                                修改作业
                            </h4>
                        </div>
                        <div class="modal-body">
                            <table class="table table-bordered">
                                <form action="TupdateAssignment/updateAsgnCom" method="post" id="updtform" name="updtform">
                                    <tr>
                                        <input type="hidden" name="ld2" id="ld2">
                                        <td width="100">编号<font color='red'>* <br>(不可修改)</font></td>
                                        <td><input type="text" name="id2" id="id2" class="form-control" readonly></td>
                                    </tr>
                                    <tr>
                                        <td width="100">学科<font color='red'>* <br>(不可修改)</font></td>
                                        <td><input type="text" name="sbjt2" id="sbjt2" class="form-control" readonly></td>
                                    </tr>
                                    <tr>
                                        <td width="100">知识点<font color='red'>* <br>(不可修改)</font></td>
                                        <td><input type="text" name="topic2" id="topic2" class="form-control" readonly></td>
                                    </tr>
                                    <tr>
                                        <td width="100">面向班级<font color='red'>*</font></td>
                                        <td>
                                            <select name="classid2" id="classid2" class="form-control">
                                                <c:forEach items="${classlist}" var="classid" varStatus="status">
                                                    <option value="${classid}">${classid}</option>
                                                </c:forEach>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td width="100">选择题数量<font color='red'>* <br>(不可修改)</font></td>
                                        <td><input type="text" name="choNum2" id="choNum2" class="form-control" readonly></td>
                                    </tr>
                                    <tr>
                                        <td width="100">填空题数量<font color='red'>* <br>(不可修改)</font></td>
                                        <td><input type="text" name="blkNum2" id="blkNum2" class="form-control" readonly></td>
                                    </tr>
                                    <tr>
                                        <td width="100">完成时间<font color='red'>*</font></td>
                                        <td><input type="text" name="asgntime2" id="asgntime2" class="form-control" AUTOCOMPLETE="OFF"></td>
                                    </tr>
                                    <tr>
                                        <td width="100">截止时间<font color='red'>*</font></td>
                                        <td><input type="datetime-local" name="deadline2" id="deadline2" class="form-control"></td>
                                    </tr>
                                </form>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <span id="userspan2" style="float:left;"></span>
                            <button type="button" class="btn btn-default" data-dismiss="modal" onclick="document.getElementById('updtform').reset();$('#userspan2').html('');"/>取消
                            <button type="button" class="btn btn-primary" id="subupdtform" name="subupdtform"/>确认
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal -->
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

<script>
    function mindate() {
        var nTime = new Date();
        format = "";
        format += nTime.getFullYear();
        format += "-";
        format += (nTime.getMonth()+1)<10?"0"+(nTime.getMonth()+1):(nTime.getMonth()+1);
        format += "-";
        format += nTime.getDate()<10?"0"+(nTime.getDate()):(nTime.getDate());
        format += "T00:00";
        $("#deadline1").attr("min",format);
        $("#deadline2").attr("min",format);
    }
</script>

<!-- 找到删除id -->
<script>
    function opendel(param) {
        var a = param;
        $("#hidden").val(a);
    }
</script>

<!-- 确认删除 -->
<script>
    function deleteAsgnCon(id) {
        window.location.href=('<%=basePath%>assignment/TdeleteAssignment/'+id);
        return true;
    }
</script>

<!-- 检查添加的信息 -->
<script type="text/javascript">
    $(function () {
        var flag = false;
        $("#subaddform").click(
            function () {
                var id = $("#id1").val();
                var sbjt = $("#sbjt1").val();
                var topic = $("#topic1").val();
                var classid = $("#classid1").val();
                var chonum = $("#choNum1").val();
                var blknum = $("#blkNum1").val();
                var asgntime = $("#asgntime1").val();
                var deadline = $("#deadline1").val();
                deadline += ":00+08:00"
                var date = new Date(deadline);
                var now = new Date();
                if ($.trim(id) == ''||$.trim(sbjt) == '/'||$.trim(topic) == '/'||$.trim(classid) == '/'
                    ||$.trim(chonum) == ''||$.trim(blknum) == ''||$.trim(asgntime) == ''||$.trim(deadline) == ':00+08:00') {
                    $("#userspan1").html("<font color='red'>不能提交空信息</font>");
                    flag = false;
                } else if (isNaN($.trim(id))||Number($.trim(id))<=0) {
                    $("#userspan1").html("<font color='red'>编号须为大于零的数字</font>");
                    flag = false;
                } else if (isNaN($.trim(chonum))||isNaN($.trim(blknum))) {
                    $("#userspan1").html("<font color='red'>题目数量须为数字</font>");
                    flag = false;
                } else if (Number($.trim(chonum)) > Number($("#cho1").val())) {
                    $("#userspan1").html("<font color='red'>选择题数量过大</font>");
                    flag = false;
                } else if (Number($.trim(blknum)) > Number($("#blk1").val())) {
                    $("#userspan1").html("<font color='red'>填空题数量过大</font>");
                    flag = false;
                } else if (Number($.trim(chonum)) < 0) {
                    $("#userspan1").html("<font color='red'>选择题数量不得小于零</font>");
                    flag = false;
                } else if (Number($.trim(blknum)) < 0) {
                    $("#userspan1").html("<font color='red'>填空题数量不得小于零</font>");
                    flag = false;
                } else if ((date-now)/3600<0) {
                    $("#userspan1").html("<font color='red'>截止时间不得早于当前时间</font>");
                    flag = false;
                } else if (Number($.trim(chonum))==0 && Number($.trim(blknum))==0) {
                    $("#userspan1").html("<font color='red'>至少一种题目数量不得为零</font>");
                    flag = false;
                } else if (isNaN($.trim(asgntime))||Number($.trim(asgntime))<=0) {
                    $("#userspan1").html("<font color='red'>完成时间须为大于零的数字</font>");
                    flag = false;
                } else {
                    var url = "<%=basePath%>assignment/addAsgn/asgnIdExist";
                    var args = { "asgnid": $.trim(id) };
                    $.post(url, args,
                        function (data) {
                            if ($.trim(data) == "error") {
                                $("#userspan1").html("<font color='red'>编号已存在</font>");
                                flag = false;
                            } else if ($.trim(data) == "success") {
                                $("#addform").submit();
                                flag = true;
                            }
                        })
                }
            });
    });
</script>

<script type="text/javascript">
    $().ready(function () {
        $("#sbjt1").change (
            function () {
                $('#choNum1').attr('placeholder','');
                $('#blkNum1').attr('placeholder','');
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
                $('#choNum1').attr('placeholder','');
                $('#blkNum1').attr('placeholder','');
                $.ajax({
                    type:"post",
                    url:"<%=basePath%>/assignment/getQuanByTopic",
                    data:"topic=" + $("#topic1").val() + "&sbjt=" + $("#sbjt1").val(),
                    cache:false,
                    dataType:"json",
                    success: function (json) {
                        $("#choNum1").attr("placeholder","此类题目共有" + json[0] + "道");
                        $("#blkNum1").attr("placeholder","此类题目共有" + json[1] + "道");
                        $("#cho1").val(json[0]);
                        $("#blk1").val(json[1]);
                    }
                });
            }
        );
    })
</script>

<!-- 找到修改信息 -->
<script>
    function openupdt(param) {
        var a = param;
        $.ajax({
            type: "POST",
            url: "<%=basePath%>/assignment/AupdtAsgn/getid",
            cache: false,
            dataType: "json",
            data: "asgnid=" + a,
            success: function (data) {
                $("#ld2").val(data.id);
                $("#id2").val(data.asgnid);
                $("#sbjt2").val(data.sbjt);
                $("#topic2").val(data.topic);
                $("#classid2").val(data.classid);
                $("#choNum2").val(data.chonum);
                $("#blkNum2").val(data.blknum);
                $("#asgntime2").val(data.asgntime);
                var nTime = new Date(data.deadline);
                format = "";
                format += nTime.getFullYear();
                format += "-";
                format += (nTime.getMonth()+1)<10?"0"+(nTime.getMonth()+1):(nTime.getMonth()+1);
                format += "-";
                format += nTime.getDate()<10?"0"+(nTime.getDate()):(nTime.getDate());
                format += "T";
                format += nTime.getHours()<10?"0"+(nTime.getHours()):(nTime.getHours());
                format += ":";
                format += nTime.getMinutes()<10?"0"+(nTime.getMinutes()):(nTime.getMinutes());
                format += ":00";
                $("#deadline2").val(format);
            }
        })
    }
</script>

<!-- 检查修改信息 -->
<script type="text/javascript">
    $(function () {
        var flag = false;
        $("#subupdtform").click(
            function () {
                var classid = $("#classid2").val();
                var asgntime = $("#asgntime2").val();
                var deadline = $("#deadline2").val();
                deadline += "+08:00"
                var date = new Date(deadline);
                var now = new Date();
                if ($.trim(classid) == ''||$.trim(asgntime) == ''||$.trim(deadline) == '+08:00') {
                    $("#userspan2").html("<font color='red'>不能提交空信息</font>");
                    flag = false;
                } else if ((date-now)/3600<0) {
                    $("#userspan2").html("<font color='red'>截止时间不得早于当前时间</font>");
                    flag = false;
                } else if (isNaN($.trim(asgntime))||Number($.trim(asgntime))<=0) {
                    $("#userspan2").html("<font color='red'>完成时间须为大于零的数字</font>");
                    flag = false;
                } else {
                    $("#updtform").submit();
                    return true;
                }
            }
        )
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

<input type="hidden" id="hidden">
<input type="hidden" id="cho1">
<input type="hidden" id="blk1">
</html>
