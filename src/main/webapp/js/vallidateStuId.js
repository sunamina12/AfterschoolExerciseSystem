$(function () {
        var flag1 = false;
        $("#stuid").blur(
            function () {
                var id = $("#stuid").val();
                if ($.trim(id) == '') {
                    $("#userspan").html("<font color='red'>�û�������Ϊ��</font>");
                    flag1 = false;
                } else {
                    var url = "<%=basePath%>login/register/validateUser";
                    var args = { "name": $.trim(id) };
                    $.post(url, args,
                        function (data) {
                            if ($.trim(data) == "error") {
                                $("#userspan").html("<font color='red'>ѧ���Ѵ���</font>");
                                flag1 = false;
                            } else if ($.trim(data) == "success") {
                                $("#userspan").html("ѧ��δע��").css({color: 'green'});
                                flag1 = true;
                            }
                        })
                }
            });
    });