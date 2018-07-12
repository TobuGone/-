<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String username = (String)request.getSession().getAttribute("username") ;
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 		<meta charset="utf-8">
        <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
        <meta content="yes" name="apple-mobile-web-app-capable">
        <meta content="black" name="apple-mobile-web-app-status-bar-style">
        <meta content="telephone=no" name="format-detection">
        <meta content="email=no" name="format-detection">
        <meta http-equiv="Cache-Control" CONTENT="no-cache">
        <meta http-equiv="Pragma" CONTENT="no-cache">
<script type="text/javascript" src="<%=path%>/js/jquery/jquery-1.11.1.min.js"></script>

<script type="text/javascript">
	$(function(){
	    var url = '<%= path%>/public/findPublicHelpDetails.do?title=分享下载' ;
		$.getJSON(url,function(json){
			$('#small').prepend(json.data.memo) ;
			$('#small').find("img").attr("width","100%");
		}) ;
	}) ;
</script>
<title>分享页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<div >
	<div  id="small" ></div>
	
	<div >
		<a href="http://fir.im/gxlz" ><img src="http://121.41.86.29:8888/pms/attached/image/20160509/20160509160949_676.png" alt="" border="0" width="49%"/></a>
		<a href="http://fir.im/5xl6" ><img src="http://121.41.86.29:8888/pms/attached/image/20160509/20160509161027_549.png" alt="" border="0" width="49%"/></a>
	</div>
</div>	
</body>
</html>
