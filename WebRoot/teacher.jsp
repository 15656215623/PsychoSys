<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<base>
<title>在线心理咨询系统-心理咨询师入驻页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/user.css" />
<link rel="stylesheet" href="css/common.css" />
<link rel="stylesheet" href="css/teacher.css" />
<style>
.border{
background-color: #4d7704;
width: 30%;
margin-left: 33%;
margin-top: 7%;	
padding-left: 3%;
padding-top: 1%;
}
</style>
<body>
<div class="mylogo">
<img src="img/logo.png"/>
</div>
<!--头部  -->
<div class="heads">
<div id="ltab">心理咨询师入驻页面</div>
</div>
<!--  注册-->
<div class="form-group">
<div class="col-sm-10">
<el-upload class="avatar-uploader"
  action="https://jsonplaceholder.typicode.com/posts/":show-file-list="false":on-success="handleAvatarSuccess":before-upload="beforeAvatarUpload">
  <img v-if="imageUrl" :src="imageUrl" class="avatar">
  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
</el-upload>
		</div>
	</div>
<div class="border">
<form class="form-horizontal vueBox" role="form" action=".action">

<div class="form-group">
		<div class="col-sm-10">
			<span v-if="mess!=''" style="color:red">{{mess}}</span>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-10">
			<input type="text" class="form-control" name="username" id="name"
				   placeholder="真实姓名">
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-10">
			<input type="text" class="form-control" name="username" id="name"
				   placeholder="手机号码">
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-6">
			<input type="password" class="form-control" name="password" id="jym" 
				   placeholder="验证码">
		</div>
		<div class="col-sm-6">
			 <span v-show="sendAuthCode" class="auth_text auth_text_blue"  @click="getAuthCode">获取验证码</span>
             <span v-show="!sendAuthCode" class="auth_text"><span class="auth_text_blue">{{auth_time}} </span>s</span>
		</div>
	</div>
<div class="form-group">
		<div class="col-sm-10">
							<div id="city_1">
					<select class="prov"></select> 
					<select class="city" disabled="disabled"></select>
				</div>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-10">
			<input type="text" class="form-control" 
				   placeholder="简介">
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-10">
		    <select class="selected">
    				<option>选择从业资质</option>
    				<option>国家二级咨询师</option>
    				<option>国家三级咨询师</option>
    				<option>注册系统咨询师</option>
    				<option>注册系统督导师</option>
    				<option>其他</option>
    			</select>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-10">
			<input type="text" class="form-control" 
				   placeholder="证书编号">
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-10">
		<input id="bfile"  type="file" name="file" style="display: none;"/>
			<button type="button" class="btn btn-default"  onclick="$('#bfile').click();">上传证书</button>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-10">
			专长领域：
			<template>
      <el-checkbox v-model="checked">备选项</el-checkbox>
         </template>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			  <button type="button" class="btn btn-success btn-lg" onclick="comparepass()" id="rbutton">快捷登录</button>
		</div>
	</div>
</form>
</div>
<!--手机号快捷登录  -->
</body>
<script src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="js/jquery.cityselect.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/vue.js"></script>
<!-- <script src="js/regedit.js"></script> -->
<script src="js/teacher.js"></script>
<script type="text/javascript">
			$(function(){
				$("#city_1").citySelect(); 
			})
		</script>
</html>
