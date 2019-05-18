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
<title>在线心理咨询系统-公共的头部</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/head.css" />
<body>
	<nav class="navbar navbar-default myvue" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#"> <img src="img/logo.png"
					class="logo" />
				</a>
			</div>
			<div>
				<ul class="nav navbar-nav h3 head-li" id="menu">
					<li class="active"><a href="#"><span class="noneclick">首页</span></a></li>
					<li><a href="#"><span class="noneclick">测试</span></a></li>
					<li><a href="#"><span class="noneclick">咨询</span></a></li>
					<li><a href="#">
							<form target="_blank" action="" method="get">
								<input type="text" class="searchpart" name="q"
									placeholder="高三，如何处理考前焦虑？"> <img class="search_a"
									src="img/index.png"> <input type="submit" class="submit"
									style="display: none;">
							</form>
					</a></li>
					<li v-if="user==null"><a href="user.jsp"><span
							class="noneclick">注册/登录</span></a></li>
					<li class="dropdown" v-else @mouseenter="enter"
						@mouseleave="leave"><a> {{user}} </a>
			<ul v-show="seen" id="others">
			<li><a href="#" class="option"><img src="icon/person.png"/>个人主页</a></li>
			<li ><a  class="option" @click="exitu"><img src="icon/exit.png"/>退出</a></li>
			</ul>
						</li>
				</ul>
				
			</div>
			
		</div>

	</nav>

</body>
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/vue.js"></script>
<script>
  var  u=localStorage.getItem("user");
  $('#menu li a span').click(function () {
    var f = this;
    $('#menu li a span').each(function () {
      this.className = this == f ? 'click' : 'noneclick';
    });
  });
  var ve=new Vue({
  el:".myvue",
  data:{
  user:u,
  seen:false
  },
  methods:{
      enter:function(){
        this.seen = true;
      },
      leave:function(){
        this.seen = false;
      },
      exitu:function(){
      //清除数据
      localStorage.removeItem("user");
      this.user=null;
      //更新一下导航栏数据
      }
    }
  });

</script>
</html>
