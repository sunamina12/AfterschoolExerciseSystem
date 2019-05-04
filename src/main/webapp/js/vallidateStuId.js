$(function () {
        var flag1 = false;
        $("#stuid").blur(
            function () {
                var id = $("#stuid").val();
                if ($.trim(id) == '') {
                    $("#userspan").html("<font color='red'>用户名不能为空</font>");
                    flag1 = false;
                } else {
                    var url = "<%=basePath%>login/register/validateUser";
                    var args = { "name": $.trim(id) };
                    $.post(url, args,
                        function (data) {
                            if ($.trim(data) == "error") {
                                $("#userspan").html("<font color='red'>学号已存在</font>");
                                flag1 = false;
                            } else if ($.trim(data) == "success") {
                                $("#userspan").html("学号未注册").css({color: 'green'});
                                flag1 = true;
                            }
                        })
                }
            });
    });