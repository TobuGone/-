<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String thisPath = basePath + "/userinfo/";	
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
	<form id="search-form" style="margin-left: 	px;">
	          用户账号：<input class="easyui-textBox" type="text" name="searchUserName" style="width: 140px"/>&nbsp;&nbsp;&nbsp;&nbsp;
	          用户昵称：<input class="easyui-textBox" type="text" name="searchNickName" style="width: 140px"/>&nbsp;&nbsp;&nbsp;&nbsp;  
	              设备号：<input class="easyui-textBox" type="text" name="searchNumber" style="width: 140px"/>&nbsp;&nbsp;&nbsp;&nbsp;   
		<a href="javascript:void(0)" title="查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onClick="gridSearch()">查询</a>
		<a href="javascript:void(0)" title="重置当前查询条件重新查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onClick="gridReload()">重置</a>&nbsp;&nbsp;&nbsp;
		
		<!-- 禁用或使用  2018-4-23  huzi -->
		<a href="javascript:void(0)" title="禁用用户" class="easyui-linkbutton" data-options="iconCls:'icon-stopuse'" onclick="upStatus('禁用')">禁用</a>&nbsp;&nbsp;&nbsp;
		<a href="javascript:void(0)" title="使用用户" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="upStatus('使用')">使用</a>&nbsp;&nbsp;&nbsp;
		
		<!-- 批量删除  2018-4-23  huzi -->
		<a href="javascript:void(0)" title="删除" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onClick="gridAllDel()">删除</a>&nbsp;&nbsp;
		
		<!-- 批量导入导出  2018-3-23  huzi -->
		<a href="javascript:void(0)" title="导入用户数据" style="margin-right: 10px;" class="easyui-linkbutton" onClick="importExcel()">导入</a>
		<a href="javascript:void(0)" title="导出用户数据" style="margin-right: 10px;" class="easyui-linkbutton" onClick="exportExcel()">导出</a>
		
	</form>
</div>
	<!--  数据列表datagrid    一般使用只需要配置 标题:title 数据加载url地址:url  data-options第二行配置是datagrid的默认配置 -->
	<table id="mygrid" style="margin-top: 10px;display: none;" class="myResize"
	      data-options="url:'<%=thisPath %>getUserInfoList.htm',loadFilter:loadFilter,
	      fitColumns:true,animate: true,showFooter: false,onLoadSuccess:gridLoadSuccess,pagination:true,loading:true,rownumbers:true">
	    <thead>
	        <tr><th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'userName',width:5,align:'center'">用户账号</th>
	        	<th data-options="field:'nickName',width:5">用户昵称</th>
	        	<th data-options="field:'eqNumber',width:15">设备号</th>
	        	<th data-options="field:'cuisine',width:5">菜系</th>
	        	<th data-options="field:'taste',width:5">口味</th>
	        	<th data-options="field:'status',width:5,formatter: function(value,row,index){
	        			if(value != null){
		  					if(value == '1'){
		  						return '使用中' ;
		  					}else{
		  						return '暂停使用' ;
		  					}
		  				}
				},align:'center'">状态</th>
				<th data-options="field:'createTime',width:10,align:'center'">添加时间</th>
				<th data-options="field:'lastTime',width:10,align:'center'">最后登录时间</th>
	        	<th data-options="field:'clicknum',width:5,formatter:searchFormatter,align:'center'">操作</th>	
	        </tr>
	    </thead>
	</table>
	
<!-- 添加/修改弹出dialog buttons配置按钮默认是添加与取消按钮 -->
<div style="display: none;">

<div id="search-dialog" style="width: 650px;height: 600px;" class="easyui-dialog" class="easyui-dialog"  
		data-options="iconCls:'icon-tip',resizable:true,modal:true,closed:true,title:'添加',
		buttons:[{text:'取消',iconCls:'icon-remove',handler:function(){$('#search-dialog').dialog('close');}}]">
		
	<form id="batch-data-form" enctype="multipart/form-data" target="uploadImgFrame" method="post" style="margin: 0px;padding: 0px;">	
	<input type="hidden" name="id" />
	
	<table class="mf-table">
		<tr class="mf-line">
			<td class="mf-left">用户账号：</td>
			<td class="mf-right">
   				 <label id="userName" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">用户昵称：</td>
			<td class="mf-right">
   				<label id="nickName" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">性别：</td>
			<td class="mf-right">
				<label id="sex" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">邮箱：</td>
			<td class="mf-right">
				<label id="email" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">个性签名：</td>
			<td class="mf-right">
				<label id="autograph" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">设备号：</td>
			<td class="mf-right">
				<label id="eqNumber" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">菜系：</td>
			<td class="mf-right">
				<label id="cuisine" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">口味：</td>
			<td class="mf-right">
				<label id="taste" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">联系人：</td>
			<td class="mf-right">
				<label id="contacter" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">手机号：</td>
			<td class="mf-right">
				<label id="mobilePhone" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">居住地：</td>
			<td class="mf-right">
				<label id="livePlace" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">详细地址：</td>
			<td class="mf-right">
				<label id="address" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">出生地：</td>
			<td class="mf-right">
				<label id="birthPlace" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">添加时间：</td>
			<td class="mf-right">
				<label id="createTime" />
			</td>
		</tr>
	</table>
	</form>
</div>
</div>

<!-- 批量导入  2018-03-29  huzi -->
<div style="display: none;">
<div id="import-dialog" style="width: 400px;height: 200px;" class="easyui-dialog" class="easyui-dialog"  data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
					title:'添加',buttons:[{text:'保存', iconCls:'icon-ok', handler:saveImport},{text:'取消',iconCls:'icon-remove',handler:importCancle}]">
	<form id="import-data-form" accept-charset="UTF-8" enctype="multipart/form-data" target="uploadImgFrame" method="post" style="margin: 0px;padding: 0px;">
	<table class="mf-table">
		<tr class="mf-line">
			<td class="mf-left">文件名：</td>
			<td class="mf-right">
				<input type="file" id="fileText" name="myfile" onchange="fileChange(this)" /><p style="color: red;" id="fileSpan"></p>
			</td>
		</tr>
	</table>
	</form>
</div>
</div>

<script type="text/javascript" src="<%=basePath%>/views/js/userInfo.js"></script>

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
