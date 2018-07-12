<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String username = (String)request.getSession().getAttribute("username") ;
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String menuBookId = request.getParameter("menuBookId");
String userId = request.getParameter("userId");
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
        <link rel="stylesheet" href="<%=path%>/css/style.css">
<script type="text/javascript" src="<%=path%>/js/jquery/jquery-1.11.1.min.js"></script>

<style type="text/css">
	#img5{ margin:10px auto} 
     #img5 img{ border-radius:50%;width:50px;height:50px;} 
}
</style>
<script type="text/javascript">
	$(function(){
	    var url = '<%= path%>/menubook/findMenuBookDetails.aspf?menuBookId=<%= menuBookId%>&userId=<%= userId%>' ;
		$.post(url,function(json){
			var menuBook = json.data.menuBook;
			var img = '<img  src="http://121.41.86.29:8888/pms/'+menuBook.logoPath+'">';
			$('#img').prepend(img) ;
			$("#name").text(menuBook.name);
			$("#describes").text(menuBook.describes);
			$("#foods").text(menuBook.foods);
			var menubooksteps = json.data.menubooksteps;
			var html = '';
			$.each(menubooksteps,function (index, domEle) {
				var index = index+1;
				var foods = domEle.foods.split("|");
					html += '<div style="height:120px;"><div style="margin-left:10px;line-height:10px;height:13px;">';
					html += index+'<span style="margin-left:30px;">'+domEle.minute+'分'+domEle.second+'秒</span></div>';
					html += '<div style="margin-top:10px;background: #fff;margin-left:50px;"><div style="line-height:50px;height:40px;">';
					html += '步骤描述:&nbsp;'+domEle.describes+'</div><div style="line-height:40px;height:40px;">';
					html += '食材:&nbsp;'+foods[1]+' '+foods[2]+'</div></div></div>';
			});
					html += '<div style="height:30px;line-height:30px;">评论</div><div style="height:2px;background:#fff;"></div>';
			var menubookcomments = json.data.menubookcomments;
			$.each(menubookcomments,function (index, domEle) {
					html += '<div style="height:70px;background: #f0f0f0;">';
					html += '<div style="margin-left:50px;float:left;" id="img5">';
					html += '<img src="http://121.41.86.29:8888/pms/'+domEle.userLogoPath+'"></div>';
					html += '<dir style="margin-left:100px;line-height:30px;">'+domEle.userNickName+'</dir>';
					html += '<dir style="margin-left:100px;line-height:10px;">'+domEle.userNickName+'</dir>';
			});
				$("#box").append(html);
		},"json");
	});
</script>
<title>分享页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<div style="background: #f0f0f0;" id="box">
	<div align="center" id="img"></div>
	<div style="height:40px;background:#fff;line-height:35px;">
		菜谱名: <span id="name"></span>
	</div>
	<div style="height:2px;"></div>
	<div style="height:40px;background:#fff;line-height:35px;">
		描  述: <span id="describes"></span>
	</div>
	<div style="height:2px;"></div>
	<div style="height:40px;background:#fff;line-height:35px;">
		食  材: <span id="foods"></span>
	</div>
	<div style="height:2px;"></div>
	<div style="line-height:30px;height:35px;">步骤</div>
</div>	
</body>
</html>
