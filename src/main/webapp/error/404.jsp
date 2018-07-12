<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>404 error 该页面不存在</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		function toIndex(){
			parent.parent.location = '<%=basePath %>index.jsp';
		}
	</script>
  </head>
  
  <body>
    <div style="width:98%;text-align: center;padding-top: 150px;"><h1>404 error 该页面不存在！</h1><a href="#" onclick="toIndex()">去首页</a></div>
  </body>
</html>
