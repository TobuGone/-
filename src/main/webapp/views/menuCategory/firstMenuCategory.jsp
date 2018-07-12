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
	<form id="search-form" style="margin-left: 10px;">
	<a href="javascript:void(0)" title="添加一条数据" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="gridAdd()">添加</a>&nbsp;&nbsp;
	<a href="javascript:void(0)" title="暂停使用" class="easyui-linkbutton" data-options="iconCls:'icon-stopuse'" onclick="upStatus('停用')">停用</a>&nbsp;&nbsp;&nbsp;
		 <a href="javascript:void(0)" title="使用" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="upStatus('使用')">使用</a>&nbsp;&nbsp;&nbsp;
	          名称：<input class="easyui-textBox" type="text" name="searchName" style="width: 150px"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:void(0)" title="查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onClick="gridSearch()">查询</a>
		<a href="javascript:void(0)" title="重置当前查询条件重新查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onClick="gridReload()">重置</a>&nbsp;&nbsp;&nbsp;
	</form>
</div>
	<!--  数据列表datagrid    一般使用只需要配置 标题:title 数据加载url地址:url  data-options第二行配置是datagrid的默认配置 -->
	<table id="mygrid" style="margin-top: 10px;display: none;" class="myResize"
	      data-options="url:'<%=thisPath %>getFirstCategoryList.htm',loadFilter:loadFilter,
	      fitColumns:true,animate: true,showFooter: false,onLoadSuccess:gridLoadSuccess,pagination:true,loading:true,rownumbers:true">
	    <thead>
	        <tr>
		        <th data-options="field:'ck',checkbox:true"></th>
		        <th data-options="field:'name',width:8,align:'center'">菜谱一级分类</th>
	        	<th data-options="field:'logoPath',width:8,align:'center',formatter:imageFormatter">一级分类缩略图</th>
	        	<th data-options="field:'cType',width:5,formatter: function(value,row,index){
	        			if(value != null){
		  					if(value == '1'){
		  						return '菜系' ;
		  					}else{
		  						return '口味' ;
		  					}
		  				}
				},align:'center'">菜谱类别</th>
	        	<th data-options="field:'status',width:5,formatter: function(value,row,index){
	        			if(value != null){
		  					if(value == '1'){
		  						return '使用中' ;
		  					}else{
		  						return '暂停使用' ;
		  					}
		  				}
				},align:'center'">状态</th>
	        	<th data-options="field:'createTime',width:8,align:'center'">添加时间</th>
	        	<th data-options="field:'clicknum',width:5,formatter:EditFormatter,align:'center'">修改</th>
	        </tr>
	    </thead>
	</table>
	
<!-- 添加/修改弹出dialog buttons配置按钮默认是添加与取消按钮 -->
<div style="display: none;">

<div id="add-dialog" style="width: 480px;height: 320px;" class="easyui-dialog" class="easyui-dialog"  data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,title:'添加',buttons:[{text:'保存', iconCls:'icon-ok', handler:saveFirstCategory},{text:'取消',iconCls:'icon-remove',handler:function(){$('#add-dialog').dialog('close');}}]">
	<form id="add-data-form" enctype="multipart/form-data" target="uploadImgFrame" method="post" style="margin: 0px;padding: 0px;">
	<input type="hidden" name="id" />
	<table class="mf-table">
		<tr class="mf-line">
			<td class="mf-left">菜谱一级分类：</td>
			<td class="mf-right">
				<input type="text" name="name" style="width: 280px" data-options="required:true" class="easyui-validatebox"/>
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">菜谱类别：</td>
			<td class="mf-right">
				<input type="text" name="cType" id="cType" style="width: 100px" class="easyui-combobox" data-options="
								valueField:'id',
								textField:'name',
								required:true,
								data: [{
										id: '1',
										name: '菜系'
								},{
									    id: '2',
										name: '口味'
								}]"/>
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">分类缩略图：</td>
			<td class="mf-right">
				 <div id="imgdiv"><img id="imgShow" width="280" height="120" /></div>
   				 <input type="file" id="up_img" name="file"/>
			</td>
		</tr>
	</table>
	</form>
</div>
</div>

<script type="text/javascript" src="<%=basePath%>/views/js/firstMenuCategory.js"></script>

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
