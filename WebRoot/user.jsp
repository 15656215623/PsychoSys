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
<title>在线心理咨询系统-注册登录页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/user.css" />
<body>
<div class="mylogo">
<img src="img/logo.png"/>
</div>
<!--头部  -->
<div class="heads">
<div id="ltab" onclick="showornone('regedit')" >注册</div>
<div id="rtab" onclick="showornone('login')">登录</div>
</div>
<!--  注册-->
<div id="regedit">
<form class="form-horizontal" role="form" action="regedit.action">
	<div class="form-group">
		<div class="col-sm-10">
			<input type="text" class="form-control" name="username" 
				   placeholder="请输入手机号码">
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-10">
			<input type="password" class="form-control" name="password" id="password" 
				   placeholder="请输入密码">
		</div>
	</div>
<div class="form-group">
		<div class="col-sm-8">
			<input type="password" class="form-control" id="rpassword" 
				   placeholder="再次输入密码">
		</div>
		<div class="col-sm-4">
			<span id="indect"></span>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			  <button type="button" class="btn btn-success btn-lg" onclick="comparepass()" id="rbutton">注册</button>
		</div>
	</div>
</form>
</div>
<!--  注册-->
<!--  登录-->
<div id="login">
<form class="form-horizontal mymess" role="form" >
	<div class="form-group">
		<div class="col-sm-10">
			<input type="text" class="form-control" name="username" id="name"
				   placeholder="请输入手机号码">
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-8">
			<input type="password" class="form-control" name="password" id="pass"
				   placeholder="请输入密码">
		</div>
		<div class="col-sm-4">
			<span><a href="forgetpass.jsp">忘记密码?</a></span>
		</div>
	</div>

	<div class="form-group">
		<div class="col-sm-5">
		  <button type="button" class="btn btn-success btn-lg"  @click="login">登录</button>
		</div>
		<div class="col-sm-7">
			<span v-if="ok">{{mess}}</span>
		</div>
	</div>
</form>
</div>
<!--  登录-->
<div id="tell">
<span><a href="tell.jsp">手机号快捷登录</a></span>
</div>
<!--手机号快捷登录  -->

<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/vue.js"></script>
<script src="js/user.js"></script>

</body>
</html>
