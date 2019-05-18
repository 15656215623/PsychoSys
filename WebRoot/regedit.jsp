<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html>
<head>
<title>手机获取校验码操作</title>
</head>
<body>
  <div  class="vueBox" >
    手机号: <input type="text" id="number"><br>
    <span class="login_title">短信验证码：</span>
    <input class="auth_input" type="text"  placeholder="输入验证码" id="jym"/>
    <span v-show="sendAuthCode" class="auth_text auth_text_blue"  @click="getAuthCode">获取验证码</span>
    <span v-show="!sendAuthCode" class="auth_text"> <span class="auth_text_blue">{{auth_time}} </span> 秒之重新发送验证码</span> <br>
    <input type="button" value="注册" onclick="check()"/>
</div>
  <script src="js/jquery-3.3.1.min.js"></script>
  <script src="https://cdn.staticfile.org/vue/2.2.2/vue.min.js"></script>
<script src="js/regedit.js"></script>
</body>
</html>