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
<title>在线心理咨询系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.min.css" />
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/index.css" />
<body>
	<!-- 头部 -->
	<jsp:include page="head.jsp"></jsp:include>
	<!--轮播图  -->
	<jsp:include page="lbt.jsp"></jsp:include>
	<!--轮播图  -->
	<!--心理咨询师  -->
	<div id="enter">
	<a href="teacher.jsp"><img src="icon/xl.png"/></a>
	</div>
	
	<!--心理咨询师  -->
	<!-- 测试模块 -->
	<div id="test">
	<h2 class="area">心理测试<small class="more">更多</small></h2>
	<div class="part">	
<table class="table">
    <tr>
      <td colspan="2"><img src="text/t0.jpg" class="pimg"/></td>
    </tr>
    <tr>
      <td colspan="2">你被埋没了哪种才华？</td>
    </tr>
      <tr>
      <td><button type="button" class="btn btn-success">测试</button></td>
        <td>52478人测试过</td>
    </tr>
</table>
	</div>
	<div class="part">	
<table class="table">
    <tr>
      <td colspan="2"><img src="text/t0.jpg" class="pimg"/></td>
    </tr>
    <tr>
      <td colspan="2">你被埋没了哪种才华？</td>
    </tr>
      <tr>
      <td><button type="button" class="btn btn-success">测试</button></td>
        <td>52478人测试过</td>
    </tr>
</table>
	</div>
	
	<div class="part">	
<table class="table">
    <tr>
      <td colspan="2"><img src="text/t0.jpg" class="pimg"/></td>
    </tr>
    <tr>
      <td colspan="2">你被埋没了哪种才华？</td>
    </tr>
      <tr>
      <td><button type="button" class="btn btn-success">测试</button></td>
        <td>52478人测试过</td>
    </tr>
</table>
	</div><div class="part">	
<table class="table">
    <tr>
      <td colspan="2"><img src="text/t0.jpg" class="pimg"/></td>
    </tr>
    <tr>
      <td colspan="2">你被埋没了哪种才华？</td>
    </tr>
      <tr>
      <td><button type="button" class="btn btn-success">测试</button></td>
        <td>52478人测试过</td>
    </tr>
</table>
	</div><div class="part">	
<table class="table">
    <tr>
      <td colspan="2"><img src="text/t0.jpg" class="pimg"/></td>
    </tr>
    <tr>
      <td colspan="2">你被埋没了哪种才华？</td>
    </tr>
      <tr>
      <td><button type="button" class="btn btn-success">测试</button></td>
        <td>52478人测试过</td>
    </tr>
</table>
	</div><div class="part">	
<table class="table">
    <tr>
      <td colspan="2"><img src="text/t0.jpg" class="pimg"/></td>
    </tr>
    <tr>
      <td colspan="2">你被埋没了哪种才华？</td>
    </tr>
      <tr>
      <td><button type="button" class="btn btn-success">测试</button></td>
        <td>52478人测试过</td>
    </tr>
</table>
	</div><div class="part">	
<table class="table">
    <tr>
      <td colspan="2"><img src="text/t0.jpg" class="pimg"/></td>
    </tr>
    <tr>
      <td colspan="2">你被埋没了哪种才华？</td>
    </tr>
      <tr>
      <td><button type="button" class="btn btn-success">测试</button></td>
        <td>52478人测试过</td>
    </tr>
</table>
	</div><div class="part">	
<table class="table">
    <tr>
      <td colspan="2"><img src="text/t0.jpg" class="pimg"/></td>
    </tr>
    <tr>
      <td colspan="2">你被埋没了哪种才华？</td>
    </tr>
      <tr>
      <td><button type="button" class="btn btn-success">测试</button></td>
        <td>52478人测试过</td>
    </tr>
</table>
	</div>
	</div>
	<!-- 咨询模块 -->
	<div id="chart">
	<h2 class="area">心理咨询<small class="more">更多</small></h2>
	<div class="cpart">
	<img src="img/head.png"/>
	<img src="logo/teacher.jpg" class="user_img"/>
	<p class="time">累计帮助了：288人</p>
	<p>台湾临床心理师（台湾考试院高考通过证明）</p>
	<p>擅长：个人成长、职场心理、性心理</p><br><br>
	<button type="button" class="btn btn-primary" >预约</button>
	</div>
	</div>
	<!-- 咨询模块 -->
	<!-- 底部-->
		<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
