<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String thisPath = basePath + "/equipment/";	
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
	    <form id="search-form" style="margin-left: 10px;">
		<a href="javascript:void(0)" title="添加一条数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="gridAdd()">导入</a>
		<a href="javascript:void(0)" title="批量删除数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="gridDel()">删除</a>&nbsp;&nbsp;&nbsp;
		 版本号：<input class="easyui-textBox" type="text" name="versionCode" style="width: 150px"/>&nbsp;&nbsp;&nbsp;&nbsp;
		 更新时间：<input type="text" id="startTime" name="startTime" class="easyui-datetimebox" style="width: 150px;"/>
		 	至：<input type="text" id="endTime" name="endTime" class="easyui-datetimebox" style="width: 150px;"/>
		<a href="javascript:void(0)" title="查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onClick="gridSearch()">查询</a>&nbsp;&nbsp;&nbsp;	
		<a href="javascript:void(0)" title="重置当前查询条件重新查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onClick="gridReload()">重置</a>
		</form>
	</div>
	<!--  数据列表datagrid    一般使用只需要配置 标题:title 数据加载url地址:url  data-options第二行配置是datagrid的默认配置 -->
	<table id="mygrid" style="margin-top: 10px;display: none;width: 100%" class="myResize"
	      data-options="url:'<%=thisPath %>getEquipmentList.htm',loadFilter:loadFilter,
	      fitColumns:true,animate: true,showFooter: false,onLoadSuccess:gridLoadSuccess,pagination:true,loading:true,rownumbers:true">
	    <thead>
	        <tr><th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'name',width:10,align:'center'">固件名称</th>
	        	<th data-options="field:'hardwareVersion',width:10,align:'center'">硬件型号</th>
	        	<th data-options="field:'softwareVersion',width:10,align:'center'">软件型号</th>
	        	<th data-options="field:'versionCode',width:10,align:'center'">版本号</th>
	        	<th data-options="field:'remark',width:10,align:'center'">更新说明</th>
	        	<th data-options="field:'updatePersonnel',width:10,align:'center'">更新人员</th>
	        	<th data-options="field:'createTime',width:10,align:'center'">更新时间</th>
	        	<th data-options="field:'clicknum',width:10,formatter:EditFormatter,align:'center'">操作</th>
	        </tr>
	    </thead>
	</table>
</div>


<!-- 添加/修改弹出dialog buttons配置按钮默认是添加与取消按钮 -->
<div style="display: none;">

<div id="add-dialog" style="width: 500px;height: 400px;" class="easyui-dialog" class="easyui-dialog"  data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,title:'添加',buttons:[{text:'保存', iconCls:'icon-ok', handler:saveUser},{text:'取消',iconCls:'icon-remove',handler:function(){$('#add-dialog').dialog('close');}}]">
	<form id="add-data-form" enctype="multipart/form-data" target="uploadImgFrame" method="post" style="margin: 0px;padding: 0px;">
	<input type="hidden" name="id" />
	<table class="mf-table">
		<tr class="mf-line">
			<td class="mf-left">固件名称：</td>
			<td class="mf-right"><input type="text" name="name"
				style="width: 250px" data-options="required:true"
				class="easyui-validatebox" /></td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">硬件型号：</td>
			<td class="mf-right"><input type="text" name="hardwareVersion"
				style="width: 250px" data-options="required:true"
				class="easyui-validatebox" /></td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">软件型号：</td>
			<td class="mf-right"><input type="text" name="softwareVersion"
				style="width: 250px" data-options="required:true"
				class="easyui-validatebox" /></td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">版本号：</td>
			<td class="mf-right"><input type="text" name="versionCode"
				style="width: 250px" data-options="required:true"
				class="easyui-validatebox" /></td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">版本文件：</td>
			<td class="mf-right">
				<input type="file" id="fileText" name="file" data-options="required:true" onchange="change()" style="display:none" />
				<input type="text" id="fileText1" name="filename" readonly="readonly" />
				<input type="button" id="fButton" value="选择文件" onclick="fileClick"/>
				<p id="spMsg" style="color: red; font-size: 10"></p>
		    </td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">更新说明：</td>
			<td class="mf-right"><input type="text" name="remark"
				style="width: 250px" data-options="required:false"
				class="easyui-validatebox" /></td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">更新人员：</td>
			<td class="mf-right"><input type="text" name="updatePersonnel"
				style="width: 250px" data-options="required:false"
				class="easyui-validatebox" /></td>
		</tr>
	</table>
	</form>
</div>
</div>
<script type="text/javascript" src="<%=basePath%>/views/js/equipment.js"></script>
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