<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<base>
<title>在线心理咨询系统-手机验证码设置密码页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/user.css" />
<body>
<div class="mylogo">
<img src="img/logo.png"/>
</div>
<!--头部  -->
<div class="heads">
<div id="ltab">找回密码</div>
</div>
<!--  注册-->
<div id="regedit">
<form class="form-horizontal vueBox" role="form" action="forget.action">
<div class="form-group">
		<div class="col-sm-10">
			<span v-if="mess!=''" style="color:red">{{mess}}</span>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-10">
			<input type="text" class="form-control" name="username" id="name"
				   placeholder="手机号码" @blur.prevent="mytell">
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-10">
			<input type="password" class="form-control" name="password" id="pass"
				   placeholder="新密码">
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-6">
			<input type="password" class="form-control" name="rpassword" id="repass"
				   placeholder="确认密码" @blur.prevent="fun">
		</div>
		<div class="col-sm-6">
			<span v-if="pmess!=''">{{pmess}}</span>
		</div>
		
	</div>
	<div class="form-group">
		<div class="col-sm-6">
			<input type="text" class="form-control"  id="jym" 
				   placeholder="验证码">
		</div>
		<div class="col-sm-6">
			 <span v-show="sendAuthCode" class="auth_text auth_text_blue"  @click="getAuthCode">获取验证码</span>
             <span v-show="!sendAuthCode" class="auth_text"><span class="auth_text_blue">{{auth_time}} </span>s</span>
		</div>
	</div>

	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			  <button type="button" class="btn btn-success btn-lg" @click="comparepass" id="rbutton">快捷登录</button>
		</div>
	</div>
</form>
</div>
<!--  登录-->
<div id="tell">
<span><a href="user.jsp">注册/普通登录</a></span>
</div>
<!--手机号快捷登录  -->
</body>
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/vue.js"></script>
<script src="js/forget.js"></script>
</html>
