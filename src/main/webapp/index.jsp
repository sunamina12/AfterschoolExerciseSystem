<html>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<body>
<h2>Hello World!</h2>
<% response.sendRedirect("login/home"); %>
</body>
</html>
