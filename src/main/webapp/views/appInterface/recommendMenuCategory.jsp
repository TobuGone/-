<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String thisPath = basePath + "/menuCategory/";	
%>
<jsp:include page="../include.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script src="<%=basePath%>/js/uploadPreview/uploadPreview.js" type="text/javascript"></script>
</head>
<body>

<!-- 查询条件panel 一般使用只需修改 标题:title -->
<div id="gridTools" data-options="border:false" style="padding-top: 10px;">
	<!-- <form id="search-form" style="margin-left: 10px;">
	<a href="javascript:void(0)" title="添加一条数据" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="gridAdd()">添加</a>&nbsp;&nbsp;
	<a href="javascript:void(0)" title="删除" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="gridAllDel()">删除</a>&nbsp;&nbsp;
	          二级分类名称：<input class="easyui-textBox" type="text" name="searchName" style="width: 150px"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:void(0)" title="查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onClick="gridSearch()">查询</a>
		<a href="javascript:void(0)" title="重置当前查询条件重新查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onClick="gridReload()">重置</a>&nbsp;&nbsp;&nbsp;
	</form> -->
</div>
	<!--  数据列表datagrid    一般使用只需要配置 标题:title 数据加载url地址:url  data-options第二行配置是datagrid的默认配置 -->
	<table id="mygrid" style="margin-top: 10px;display: none;" class="myResize"
	      data-options="url:'<%=thisPath %>getSecondCategoryList.htm?recommend=1',loadFilter:loadFilter,
	      fitColumns:true,animate: true,showFooter: false,onLoadSuccess:gridLoadSuccess,pagination:true,loading:true,rownumbers:true">
	    <thead>
	        <tr>
	        	<th data-options="field:'ck',checkbox:true"></th>
		        <th data-options="field:'firstName',width:8,align:'center'">菜谱一级分类</th>
		        <th data-options="field:'name',width:8,align:'center'">菜谱二级分类</th>
	        	<th data-options="field:'logoPath',width:8,align:'center',formatter:imageFormatter">二级分类缩略图</th>
	        	<th data-options="field:'recommend',width:5,formatter:recommendformatter,align:'center'">推荐</th>
	        	<th data-options="field:'createTime',width:8,align:'center'">添加时间</th>
	        </tr>
	    </thead>
	</table>

<script type="text/javascript" src="<%=basePath%>/views/js/recommendMenuCategory.js"></script>

<script type="text/javascript">

	$(function(){
	
		dataOptions.baseUrl = '<%=thisPath%>';
		
		dataOptions.basePath = '<%=basePath%>';
		
		dataOptions.addInit = function(){
			
		};
		dataOptions.editInit = function(data){
		};
		
	});
   
</script>
    
</body>
</html>
