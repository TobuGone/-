<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/";%>
<%String contextPath = request.getContextPath();%>

<script type="text/javascript">
var base = base || {};
base.basePath = '<%=basePath%>';
base.tSpace = '';
window.onerror=function(sMessage, sUrl, sLine){
	//$.messager.progress('close');
	//$.messager.alert('错误!',sMessage +"\n"+sUrl+"\n请刷新再试!",'error');
	//return true;
}
</script>
<style>
	behavior: url(<%=basePath%>js/pie/PIE.htc);
</style>
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache"> 
<meta http-equiv="expires" content="0">   
<link rel="stylesheet" href="<%=basePath%>js/jquery-easyui-1.4/themes/bootstrap/easyui.css" type="text/css"></link><!-- easyui样式文件 -->
<link rel="stylesheet" href="<%=basePath%>js/jquery-easyui-1.4/themes/icon.css" type="text/css"></link><!-- easyui图标样式 -->
<link rel="stylesheet" href="<%=basePath%>css/custom.css" type="text/css"></link>
<script type="text/javascript" src="<%=basePath%>js/jquery/jquery-1.11.1.min.js"></script><!-- jquery主js-->
<script type="text/javascript" src="<%=basePath%>js/jquery/jquery-migrate-1.2.1.min.js"></script><!-- jquery向下兼容-->
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui-1.4/jquery.easyui.min.js"></script><!-- easyui主js-->
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script><!-- easyui中文js-->
<script type="text/javascript" src="<%=basePath%>js/kindeditor-4.1.10/kindeditor-min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/kindeditor-4.1.10/lang/zh_CN.js"></script>

<script type="text/javascript" src="<%=basePath%>js/util/main.js"></script><!-- 针对当前项目定义的各种公用方法/属性 -->
<script type="text/javascript" src="<%=basePath%>js/util/util.js"></script><!-- 不分项目定义的工具类 -->
<script type="text/javascript" src="<%=basePath%>js/util/customTools.js"></script><!-- 自定义的jq模式数据处理工具类 -->
<script type="text/javascript" src="<%=basePath%>js/util/easyuiExpand.js"></script><!-- easyui扩展js-->

<%-- <script type="text/javascript" src="<%=basePath%>js/public/all.js"></script> --%>
<script type="text/javascript" src="<%=basePath%>js/datapicker/WdatePicker.js"></script> <!-- 时间插件 -->

