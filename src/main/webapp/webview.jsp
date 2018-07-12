<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<!-- 手机网站自适应  meta    开始 -->
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<!-- 在web app应用下状态条（屏幕顶部条）的颜色 -->
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<!-- 强制让文档与设备的宽度保持1：1    网页宽度默认等于屏幕宽度（width=device-width）    初始缩放比例（initial-scale=1）为1.0，即网页初始大小占屏幕面积的100%-->
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<!-- 禁止了把数字转化为拨号链接 -->
<meta name="format-detection" content="telephone=no">
<!-- 删除默认的苹果工具栏和菜单栏 -->
<meta name="apple-mobile-web-app-capable" content="yes">
<!-- 手机网站自适应  meta    结束 -->
<style type="text/css">
/*  img{
  width: 100%;
 } */
 body{
  background-color: #FFFFFF;
  line-height: 1.8;
 }
</style>
  <script type="text/javascript" src="<%=basePath%>/js/jquery/jquery-1.11.1.min.js"></script>
  </head>
 <script type="text/javascript">
    $.ajax(
		    { 
		    url: "<%=basePath%>/appuser/getDocument.aspf", 
		    data: {id:<%=request.getParameter("id")%>,type:<%=request.getParameter("type")%>,errorName:"<%=request.getParameter("errorName")%>"},
		    dataType:"text",
		    success: function(response){
			    var data = $.parseJSON(response);
			    $("body").html(data.data.content);
   				var t=setTimeout("img()",200);
	         }
   }); 
   
 </script>
  <body>
 <script type="text/javascript">
 
 	var img = function(){
 		var width = screen.width;
 		$("img").each(function(){
   			var img = new Image();
			img.src =$(this).attr("src") ;
			var w = img.width;
			if(w > width){
				$(this).attr("width",width-10+'px;');
			}
   		});
 	}
 </script>
  </body>
</html>
