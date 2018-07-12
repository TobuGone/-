<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="cn">
<head>
	<title>电磁炉后台管理系统</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- The styles -->
	<link id="bs-css" href="<%=basePath %>login/css/bootstrap-cerulean.css" rel="stylesheet">
	<style type="text/css">
	  body {
		padding-bottom: 40px;
	  }
	  .sidebar-nav {
		padding: 9px 0;
	  }
	</style>
	
	<link href="<%=basePath %>login/css/charisma-app.css" rel="stylesheet">
	<link href='<%=basePath %>login/css/uniform.default.css' rel='stylesheet'>

	<link rel="shortcut icon" href="img/favicon.ico">
	<!-- jQuery -->
	<script src="<%=basePath %>login/js/jquery-1.7.2.min.js"></script>
	<script src="<%=basePath %>/js/public/md5.js"></script>
	<script type="text/javascript">
	var base = base || {};
	base.basePath = '<%=basePath%>';
	base.tSpace = '';
	</script>
	<script type="text/javascript">

	/** 通用异步请求简单封装 */
	function asynPAjaxMin(url,data,success,error,type,getType){
		if(type){}else type = 'Post';
		if(getType){}else getType = 'json';
		asynAjax(type,url,data,getType,success,function(){
			if(error) error();
			else $('#msgTag').html('<font color="red">请求服务器失败!</font>');
		});
	}
	function asynAjax(type,url,data,dataType,success,error){
		$.ajax({type:type,url:url,data:data,dataType:dataType,success:function(data){
				if(success) success(data);
			},error:function(){
				if(error) error();
			}
		});
	} 
	var login = function(){
		var userName = $('#loginForm').find('#username').val();
		var password = $('#loginForm').find('#password').val();
		password = md5(password) ;
		var url = '<%=basePath %>login/login.htm';
		$.post(url,{userName:userName,password:password},function(data){
			if(data != null){
				data = eval("("+data+")") ;
				if(data.rtCode){
					window.location.href = '<%=basePath %>views/index.jsp' ;
				}else{
					$('#msgTag').html('<font color="red">'+data.msg+'</font>') ;
				}
			}else{
				$('#msgTag').html('<font color="red">登录失败!</font>') ;
			}
		}) ;
	} ;
	</script>	
</head>
<body style="">
		<div style="display: none;">
			<!-- <img id="showCode" style="width:300px;height:300px;" src="<%=basePath%>/img/gxt.png"></img> -->
		</div>
		<div class="container-fluid" >
		<div class="row-fluid">
		
			<div class="row-fluid" style="margin-top: 10%;">
				<div class="span12 center login-header">
					<h2 style="font-size: 32px;">欢迎进入后台管理系统</h2>
				</div><!--/span-->
			</div><!--/row-->
			
			<div class="row-fluid">
				<div class="well span5 center login-box" style="min-width: 350px;">
					<div id="msgTag" class="alert alert-info">
						请输入帐号与密码登录系统
					</div>
					<form class="form-horizontal" id="loginForm" method="post">
						<fieldset>
							<div class="input-prepend" title="请输入用户名" data-rel="tooltip">
								<span class="add-on">用户名：<i class="icon-user"></i></span><input autofocus class="input-large span10" name="username" id="username" type="text" value="root" />
							</div>
							<div class="clearfix"></div>

							<div class="input-prepend" title="请输入密码" data-rel="tooltip">
								<span class="add-on">密&nbsp;&nbsp;&nbsp;&nbsp;码：<i class="icon-lock"></i></span><input class="input-large span10" name="password" id="password" type="password" value="123456" />
							</div>
							
							<p class="center span5">
							<button type="button" onclick="login()" class="btn btn-primary" >登录</button>
							</p>
						</fieldset>
					</form>
				</div><!--/span-->
			</div><!--/row-->
				</div><!--/fluid-row-->
		
	</div>

	
	<!-- jQuery UI -->
	<script src="<%=basePath %>login/js/jquery-ui-1.8.21.custom.min.js"></script>
	
	<!-- library for creating tabs -->
	<script src="<%=basePath %>login/js/bootstrap-tab.js"></script>
	<!-- library for advanced tooltip -->
	<script src="<%=basePath %>login/js/bootstrap-tooltip.js"></script>
	<script src="<%=basePath %>login/js/bootstrap-popover.js"></script>
	<!-- library for cookie management -->
	<script src="<%=basePath %>login/js/jquery.cookie.js"></script>

	<!-- select or dropdown enhancer -->
	<script src="<%=basePath %>login/js/jquery.chosen.min.js"></script>
	<!-- checkbox, radio, and file input styler -->
	<script src="<%=basePath %>login/js/jquery.uniform.min.js"></script>
	<!-- rich text editor library -->
	<script src="<%=basePath %>login/js/jquery.cleditor.min.js"></script>
	<!-- application script for Charisma demo -->
	<script src="<%=basePath %>login/js/charisma.js"></script>
		
</body>
</html>