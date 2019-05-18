<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>">
<title>在线心理咨询系统-公共的尾部</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.min.css" />
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<style>
.footer{
margin-left: 0px;
width: 100%;
}
</style>
<body>
<img src="logo/footer.png" class="footer"/>
</body>
</html>
