<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String thisPath = basePath + "/menuBookShare/";	
%>
<jsp:include page="../include.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<script src="<%=basePath%>/js/uploadPreview/uploadPreview.js" type="text/javascript"></script>
</head>
<body>
	<!-- 查询条件panel 一般使用只需修改 标题:title -->
	<div id="gridTools" data-options="border:false"
		style="padding-top: 10px;">
		<form id="search-form" style="margin-left: 10px;">
			<a href="javascript:void(0)" title="批量删除数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="gridDel()">删除</a>&nbsp;&nbsp;&nbsp; 
			用户名：<input class="easyui-textBox" type="text" name="userName" style="width: 150px" />&nbsp;&nbsp;&nbsp;&nbsp; 
			菜谱名：<input class="easyui-textBox" type="text" name="shareName" style="width: 150px" />&nbsp;&nbsp; 
			<!-- <select name="parenttitle" id="kind">
				<option value="1">排序</option>
				<option value="orderby" name="">升序</option>
				<option value="3">降序</option>
			</select>&nbsp;&nbsp; -->
			<a href="javascript:void(0)" title="查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onClick="gridSearch()">查询</a>&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0)" title="重置当前查询条件重新查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onClick="gridReload()">重置</a>
		</form>
	</div>
	
	<!--  数据列表datagrid    一般使用只需要配置 标题:title 数据加载url地址:url  data-options第三行配置是datagrid的默认配置 -->
	<table id="mygrid" style="margin-top: 10px; display: none; width: 100%" class="myResize" data-options="url:'<%=thisPath %>getMenuBookShareList.htm',loadFilter:loadFilter,
	      fitColumns:true,animate: true,showFooter: false,onLoadSuccess:gridLoadSuccess,pagination:true,loading:true,rownumbers:true">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'userName',width:10,align:'center'">用户名</th>
				<th data-options="field:'shareName',width:10,align:'center'">菜谱名</th>
				<th data-options="field:'foods',width:10,align:'center'">食材</th>
				<th data-options="field:'logoPath',width:8,align:'center',formatter:imageFormatter">封面图片</th>
				<th data-options="field:'describes',width:10,align:'center'">菜谱描述</th>
				<th data-options="field:'createTime',width:10,align:'center',sortable:'true',
					sorter:function(a,b){
						a = a.split('/');
						b = b.split('/');
						if (a[2] == b[2]){
							if (a[0] == b[0]){
								return (a[1]>b[1]?1:-1);
							} else {
								return (a[0]>b[0]?1:-1);
							}
						} else {
							return (a[2]>b[2]?1:-1);
						}
					}">更新时间</th>
			</tr>
		</thead>
	</table>
	</div>

	<script type="text/javascript"
		src="<%=basePath%>/views/js/menuBookShare.js"></script>
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