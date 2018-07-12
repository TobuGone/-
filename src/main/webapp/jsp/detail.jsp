<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String username = (String)request.getSession().getAttribute("username") ;
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta name="format-detection" content="telephone=no" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<style text="text/css">
body { font: normal 100% Helvetica, Arial, sans-serif; }
p {text-indent: 0em;}
small { font-size: 0.875em; } 
.main { float: left; width: 98%; } 
.leftBar { float: left; width: 25%; }
img { max-width: 100%; }
object { max-width: 100%;}
img { -ms-interpolation-mode: bicubic; }
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<div id="small" class="main">
		${requestScope.memo}
	</div>
</body>
</html>
