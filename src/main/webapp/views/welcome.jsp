<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	String contextPath = request.getContextPath();
%>
<script type="text/javascript">
	var toLogin = '${toLogin}';
	if(1 == toLogin){
		var loginValue = alert("登录超时，请重新登录！");
		parent.window.location='<%=basePath%>/login/login.jsp';
	}
</script>
<div style="width:98%;text-align: center;padding-top: 150px;"><h1>欢迎进入后台管理系统！</h1></div>