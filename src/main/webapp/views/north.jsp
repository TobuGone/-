<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/";%>
<%String contextPath = request.getContextPath();%>

<link rel="stylesheet" href="<%=basePath%>login/css/fonts.css" type="text/css"></link>
<link rel="stylesheet" href="<%=basePath%>login/css/bootstrap-cerulean.css" type="text/css"></link>
<link rel="stylesheet" href="<%=basePath%>login/css/charisma-app.css" type="text/css"></link>
<jsp:include page="include.jsp"></jsp:include>
<style type="text/css">
.navbar {
	overflow: visible;
	margin-bottom: 0px;
}
.navbar .navbar-inner {
	background-color: rgb(102, 102, 102);
	padding-top: 15px;

}
/*background: linear-gradient(to bottom,#EFF5FF 0,#95B8E7 100%);
#background:none;*/
</style>
<script type="text/javascript">
var logout = function(value){
	if(value == '1' ){
		 parent.window.location.href = '<%=basePath%>/login/login.jsp';
	}else{
		parent.$.messager.confirm('警告', '亲，您确定要退出系统吗？', function(r){
			if(r){
			     parent.window.location.href = '<%=basePath%>login/loginout.htm';
	
			}
		}) ;
	}
	
} ;

var showEditPwd = function(){
	parent.showEditPwd();
} ;

$(function(){
	var now = new Date();
	var year = now.getFullYear();       //年
	var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
    var ss = now.getSeconds();          //秒
    var time = year+"-"+month+"-"+day+" "+hh+":"+mm+":"+ss ;
    $("#time").html(time) ;
});
</script>
<div class="navbar">
	<div class="navbar-inner" >
		<div class="container-fluid">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</a>
			<a class="brand" href="#" onClick=""> <!-- <img alt="后台管理系统" src="<%=basePath%>img/logo20.png">  --><span>后台管理系统</span></a>
			<div style="float: right;padding-bottom: 10px;">
				<a  href="#" onClick="logout(1)" style="margin-top:20px;">切换用户</a>
				<div style="margin-top: 5px;"></div>
				<a  href="#" onClick="showEditPwd()">修改密码</a><br/>
				<div style="margin-top: 5px;"></div>
				<a  href="#" onClick="logout()" style="margin-top:20px;">退出系统</a>
			</div>
			<div href="#" style="width:300px;float: right;margin-top: 10px;">
				<lable>当前登录用户：${user.userName} ( ${user.name} )</lable><br/>
				<lable>登录时间：<lable id="time"></lable></lable>
			</div>
		</div>
	</div>
</div>