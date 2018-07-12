<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String thisPath = basePath + "/feedback/";	
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
	<form id="search-form" style="margin-left: 10px;text-align: center;">
		用户账号：<input type="text" class="easyui-textbox" name="searchUserName" />&nbsp;&nbsp;
		反馈内容：<input type="text" class="easyui-textbox" name="searchContent" style="width: 250px"/>&nbsp;&nbsp;
		<a href="javascript:void(0)" title="查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onClick="gridSearch()">查询</a>
		<a href="javascript:void(0)" title="重置当前查询条件重新查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onClick="gridReload()">重置</a>
	</form>
</div>
	<!--  数据列表datagrid    一般使用只需要配置 标题:title 数据加载url地址:url  data-options第二行配置是datagrid的默认配置 -->
	<table id="mygrid" style="margin-top: 10px;display: none;" class="myResize"
	      data-options="url:'<%=thisPath %>getFeedBackList.htm',loadFilter:loadFilter,
	      fitColumns:true,animate: true,showFooter: false,onLoadSuccess:gridLoadSuccess,pagination:true,loading:true,rownumbers:true">
	    <thead>
	        <tr><th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'userName',width:5,align:'center'">用户账号</th>
	        	<th data-options="field:'name',width:5,align:'center'">用户名称</th>
	        	<th data-options="field:'content',width:15,align:'center'">内容</th>
	        	<th data-options="field:'createTime',width:5,align:'center'">反馈时间</th>
	        	<th data-options="field:'clicknum',width:5,formatter:searchFormatter,align:'center'">操作</th>
	        </tr>
	    </thead>
	</table>
	
<!-- 添加/修改弹出dialog buttons配置按钮默认是添加与取消按钮 -->
<div style="display: none;">

<div id="details-dialog" style="width: 650px;height: 550px;" class="easyui-dialog" class="easyui-dialog"  data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,title:'添加',buttons:[{text:'取消',iconCls:'icon-remove',handler:function(){$('#details-dialog').dialog('close');}}]">
	<form id="details-data-form" enctype="multipart/form-data" target="uploadImgFrame" method="post" style="margin: 0px;padding: 0px;">
	<input type="hidden" name="id" />
	<table class="mf-table">
		<tr class="mf-line" style="height: 30px">
			<td class="mf-left">用户账号：</td>
			<td class="mf-right">
				<label id="userName"></label>
			</td>
		</tr>
		<tr class="mf-line" style="height: 30px">
			<td class="mf-left">用户名称：</td>
			<td class="mf-right"">
				<label id="name"></label>
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">反馈内容：</td>
			<td class="mf-right">
				<label id="content"></label>
			</td>
		</tr>
		<tr class="mf-line" style="height: 30px">
			<td class="mf-left">反馈时间：</td>
			<td class="mf-right">
				<label id="createTime"></label>
			</td>
		</tr>
	</table>
	</form>
</div>
</div>

<script type="text/javascript" src="<%=basePath%>/views/js/feedBack.js"></script>

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
