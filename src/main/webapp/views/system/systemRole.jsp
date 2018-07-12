<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String thisPath = basePath + "/role/";	
%>
<jsp:include page="../include.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script src="<%=basePath%>/js/uploadPreview/uploadPreview.js" type="text/javascript"></script>
</head>
<body>
<div id="rolelayout" class="easyui-layout myResizelayout" style="width:100%;height:99.5%;" data-option="fit:true,split:true">
<div data-options="region:'center'">
	<!-- 查询条件panel 一般使用只需修改 标题:title -->
	<div id="gridTools" data-options="border:false" style="padding-top: 10px;">
	    <form id="search-form" style="margin-left: 10px;">
		<a href="javascript:void(0)" title="添加一条数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="gridAdd()">添加</a>
	    <a href="javascript:void(0)" title="暂停使用" class="easyui-linkbutton" data-options="iconCls:'icon-stopuse'" onclick="stopUse()">暂停</a>&nbsp;&nbsp;&nbsp;
		 <a href="javascript:void(0)" title="使用" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="startUse()">使用</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  角色名称：<input class="easyui-textBox" type="text" name="roleName" style="width: 250px"/>
		<a href="javascript:void(0)" title="查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onClick="gridSearch()">查询</a>
		<a href="javascript:void(0)" title="重置当前查询条件重新查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onClick="gridReload()">重置</a>&nbsp;&nbsp;&nbsp;
		</form>
	</div>
	<!--  数据列表datagrid    一般使用只需要配置 标题:title 数据加载url地址:url  data-options第二行配置是datagrid的默认配置 -->
	<table id="mygrid" style="margin-top: 10px;display: none;width: 100%" class="myResize"
	      data-options="url:'<%=thisPath %>getSystemRoleList.htm',loadFilter:loadFilter,
	      fitColumns:true,animate: true,showFooter: false,onLoadSuccess:gridLoadSuccess,pagination:true,loading:true,rownumbers:true">
	    <thead>
	        <tr><th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'roleName',width:8,align:'center'">角色名称</th>
	        	<th data-options="field:'roleValue',width:8,align:'center'">别名</th>
	        	<th data-options="field:'status',width:5,formatter: function(value,row,index){
	        			if(value != null){
		  					if(value == '1'){
		  						return '使用中' ;
		  					}else{
		  						return '暂停使用' ;
		  					}
		  				}
				},align:'center'">状态</th>
	        	<th data-options="field:'createTime',width:8,align:'center'">创建时间</th>
	        	<th data-options="field:'clicknum1',width:8,formatter:authorityFormatter,align:'center'">分配权限</th>
	        	<th data-options="field:'clicknum',width:10,formatter:EditFormatter,align:'center'">修改</th>
	        </tr>
	    </thead>
	</table>
</div>

<div  data-options="region:'east',collapsible:true,title:'分配权限',collapsed:true,onStopResize:function(e){roleDataGrid.datagrid('resize');}" style="width:350px;" class="myResizePanel">
		<div class="easyui-layout" data-options="fit:true" >
			<div data-options="region:'center'">
			    <input type="hidden" id="menuRoleId" name="menuRoleId">
				<ul id="resource" class="zTree"></ul>
			</div>
			<div data-options="region:'north'" style="height: 40px;">
<!-- 				<a href="#" style="margin-left: 25px;margin-top:2px;" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onClick="#">保存分配权限</a>insertRoleResource() -->
				<input type="button" value="保存分配权限" onclick="insure()" style="margin-top: 10px;margin-left: 10px;"/>
			</div>
		</div>
</div>

</div>

<!-- 添加/修改弹出dialog buttons配置按钮默认是添加与取消按钮 -->
<div style="display: none;">

<div id="add-dialog" style="width: 600px;height: 550px;" class="easyui-dialog" class="easyui-dialog"  data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,title:'添加',buttons:[{text:'保存', iconCls:'icon-ok', handler:saveRole},{text:'取消',iconCls:'icon-remove',handler:function(){$('#add-dialog').dialog('close');}}]">
	<form id="add-data-form" enctype="multipart/form-data" target="uploadImgFrame" method="post" style="margin: 0px;padding: 0px;">
	<input type="hidden" name="id" />
	<table class="mf-table">
		<tr class="mf-line">
			<td class="mf-left">角色名称：</td>
			<td class="mf-right">
				 <input type="text" name="roleName" class="easyui-validatebox" data-options="required:true" style="width: 320px"/>
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">角色别名：</td>
			<td class="mf-right">
				 <input type="text" name="roleValue" class="easyui-validatebox" data-options="required:true,validType:'roleValidate'" style="width: 320px"/>&nbsp;&nbsp;
				 <span style="font-size: 12px;color: red;">格式："ROLE_"</span>
			</td>
		</tr>
	</table>
	</form>
</div>
</div>

<script type="text/javascript" src="<%=basePath%>/views/js/systemRole.js"></script>

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
