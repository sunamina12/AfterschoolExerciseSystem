<%--
  Created by IntelliJ IDEA.
  User: yangitano
  Date: 3/29/17
  Time: 11:15 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <title>管理员主页 - 课后练习系统</title>

    <!-- Time -->
    <script type="text/javascript" src='<c:url value="/js/time.js"></c:url>'></script>

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
            <a class="navbar-brand">管理员主页 - 课后练习系统</a>
        </div>
        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <td><span id="txt"></span></td>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw"></i> ${sessionScope.admin.adminname} <i class="fa fa-caret-down"></i>
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
                        <a href="<%=basePath%>admin/home"><i class="fa fa-home"></i> 主页</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-table fa-fw"></i> 用户管理<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="<%=basePath%>admin/stuManagement">学生</a>
                            </li>
                            <li>
                                <a href="<%=basePath%>admin/tchManagement">教师</a>
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-sitemap fa-fw"></i> 试题管理<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a href="<%=basePath%>admin/choManagement">单选题</a>
                            </li>
                            <li>
                                <a href="<%=basePath%>admin/blkManagement">填空题</a>
                            </li>
                        </ul>
                        <!-- /.nav-second-level -->
                    </li>
                    <li>
                        <a href="<%=basePath%>assignment/AshowAllAssignment"><i class="fa fa-edit fa-fw"></i> 课后作业管理</a>
                    </li>
                    <li>
                        <a href="<%=basePath%>score/AshowAllScore"><i class="fa fa-bar-chart-o fa-fw"></i> 作业情况管理</a>
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
                <h1 class="page-header">教师列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->

        <div class="row">
            <div class="container">
                <button type="button" class="btn btn-success" data-toggle="modal" data-target="#addModal" id="add">添加教师 <i class="fa fa-plus"></i></button>
                <br><br>
                <table class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th style="text-align:center;">工号</th>
                        <th style="text-align:center;">姓名</th>
                        <th style="text-align:center;">所属专业</th>
                        <th style="text-align:center;" colspan="2">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="tch" varStatus="status">
                        <tr>
                            <td width="100">${tch.tchid}</td>
                            <td>${tch.tchname}</td>
                            <td>${tch.major}</td>
                            <td width="20"><button class="btn btn-danger" data-toggle="modal" data-target="#delModal" id="del"
                                        onclick="opendel('${tch.id}')"/>删除 <i class="fa fa-times"></i>
                            </td>
                            <td width="20">
                                <button class="btn btn-warning" data-toggle="modal" data-target="#updtModal" id="updt"
                                        onclick="openupdt('${tch.id}')"/>修改 <i class="fa fa-pencil"></i>
                            </td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <br>
                <!--<a href="<%=basePath%>admin/home">返回</a>-->
            </div>

            <!-- 删除模态框（Modal） -->
            <div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="delModalLabel">
                                删除教师
                            </h4>
                        </div>
                        <div class="modal-body">
                            确认要删除吗？
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"/>取消
                            <button type="button" class="btn btn-primary" onclick="return deleteTchCon($('#hidden').val())"/>确认
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
                                添加教师
                            </h4>
                        </div>
                        <div class="modal-body">
                            <table class="table table-bordered">
                                <form action="addTeacher/addTchCom" method="post" id="addform" name="addform">
                                    <tr>
                                        <td>工号<font color='red'>* (不可修改)</font></td>
                                        <td><input type="text" size="20" name="tchid1" id="tchid1" class="form-control" AUTOCOMPLETE="OFF"></td>
                                    </tr>
                                    <tr>
                                        <td>姓名<font color='red'>*</font></td>
                                        <td><input type="text" size="20" name="tchname1" id="tchname1" class="form-control" AUTOCOMPLETE="OFF"></td>
                                    </tr>
                                    <tr>
                                        <td class="last">所属专业<font color='red'>*</font></td>
                                        <td class="last"><select name="major1" id="major1" class="form-control">
                                            <option value="/" selected="selected">---请选择---</option>
                                            <option value="计算机科学与技术">计算机科学与技术</option>
                                            <option value="软件工程">软件工程</option>
                                            <option value="信息安全">信息安全</option>
                                        </select></td>
                                    </tr>
                                </form>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <span id="userspan1" style="float:left;"></span>
                            <button type="button" class="btn btn-default" data-dismiss="modal" onclick="document.getElementById('addform').reset();$('#userspan1').html('');"/>取消
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
                                修改教师
                            </h4>
                        </div>
                        <div class="modal-body">
                            <table class="table table-bordered">
                                <form action="updateTeacher/updateTchCom1" method="post" id="updtform" name="updtform">
                                    <tr>
                                        <input type="hidden" name="id2" id="id2">
                                        <td>工号<font color='red'>* (不可修改)</font></td>
                                        <td><input type="text" size="20" name="tchid2" id="tchid2" class="form-control" readonly></td>
                                    </tr>
                                    <tr>
                                        <td>姓名<font color='red'>*</font></td>
                                        <td><input type="text" size="20" name="tchname2" id="tchname2" class="form-control" AUTOCOMPLETE="OFF"></td>
                                    </tr>
                                    <tr>
                                        <td class="last">所属专业<font color='red'>*</font></td>
                                        <td class="last"><select name="major2" id="major2" class="form-control">
                                            <option value="/" selected="selected">---请选择---</option>
                                            <option value="计算机科学与技术">计算机科学与技术</option>
                                            <option value="软件工程">软件工程</option>
                                            <option value="信息安全">信息安全</option>
                                        </select></td>
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
                <h4 class="modal-title" id="updtpwdModalLabel">
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
                        <td>${admin.adminid}</td>
                    </tr>
                    <tr>
                        <td>姓名</td>
                        <td>${admin.adminname}</td>
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



<!-- 确认删除 -->
<script>
    function deleteTchCon(id) {
        window.location.href=('<%=basePath%>Ateacher/deleteTeacher/'+id);
        return true;
    }
</script>

<!-- 检查添加的信息 -->
<script type="text/javascript">
    $(function () {
        var flag = false;
        $("#subaddform").click(
            function () {
                var id = $("#tchid1").val();
                var tchname = $("#tchname1").val();
                var major = $("#major1").val();
                if ($.trim(id) == ''||$.trim(tchname) == ''||$.trim(major) == '') {
                    $("#userspan1").html("<font color='red'>不能提交空信息</font>");
                    flag = false;
                } else if($.trim(id).length != 8){
                    $("#userspan1").html("<font color='red'>工号应为8位数字</font>");
                    flag = false;
                } else {
                    var url = "<%=basePath%>Ateacher/addTeacher/tchIdExist";
                    var args = { "id": $.trim(id) };
                    $.post(url, args,
                        function (data) {
                            if ($.trim(data) == "error") {
                                $("#userspan1").html("<font color='red'>工号已存在</font>");
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

<!-- 找到删除id -->
<script>
    function opendel(param) {
        var a = param;
        $("#hidden").val(a);
    }
</script>

<!-- 找到修改信息 -->
<script>
    function openupdt(param) {
        var a = param;
        $.ajax({
            type: "POST",
            url: "<%=basePath%>/Ateacher/updtTeacher/getid",
            cache: false,
            dataType: "json",
            data: "tchid=" + a,
            success: function (data) {
                $("#id2").val(data.id);
                $("#tchid2").val(data.tchid);
                $("#tchname2").val(data.tchname);
                var  ss = document.getElementById('major2');
                var item=0;
                var itemed=data.major;
                if(itemed=="计算机科学与技术"){
                    item=1;
                }else if(itemed=="软件工程"){
                    item=2;
                }else if(itemed=="信息安全"){
                    item=3;
                }else {
                    item=0;
                }
                ss[item].selected = true;//选中
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
                var tchname = $("#tchname2").val();
                var major = $("#major2").val();
                if ($.trim(tchname) == ""||$.trim(major) == "") {
                    $("#userspan2").html("<font color='red'>不能提交空信息</font>");
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
                var pwd = '${admin.adminpwd}';
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
</html>
