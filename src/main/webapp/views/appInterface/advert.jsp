<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String thisPath = basePath + "/menuBook/";	
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
	<a href="javascript:void(0)" title="删除" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="gridAllDel()">删除</a>&nbsp;&nbsp;
	</form>
</div>
	<!--  数据列表datagrid    一般使用只需要配置 标题:title 数据加载url地址:url  data-options第二行配置是datagrid的默认配置 -->
	<table id="mygrid" style="margin-top: 10px;display: none;" class="myResize"
	      data-options="url:'<%=thisPath %>getAdvertList.htm',loadFilter:loadFilter,
	      fitColumns:true,animate: true,showFooter: false,onLoadSuccess:gridLoadSuccess,pagination:true,loading:true,rownumbers:true">
	    <thead>
	        <tr><th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'clicknum1',width:8,align:'center',formatter:menubookFormatter">菜谱</th>
	        	<th data-options="field:'status',width:5,formatter: function(value,row,index){
	        			if(value != null){
		  					if(value == '1'){
		  						return '使用中' ;
		  					}else{
		  						return '暂停使用' ;
		  					}
		  				}
				},align:'center'">使用状态</th>
	        	<th data-options="field:'createTime',width:5,align:'center'">添加时间</th>
	        	<th data-options="field:'clicknum',width:5,formatter:EditFormatter,align:'center'">操作</th>
	        </tr>
	    </thead>
	</table>
	
<!-- 添加/修改弹出dialog buttons配置按钮默认是添加与取消按钮 -->
<div style="display: none;">

<div id="add-dialog" style="width: 550px;height: 450px;" class="easyui-dialog" class="easyui-dialog"  data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,title:'添加',buttons:[{text:'保存', iconCls:'icon-ok', handler:saveAdvert},{text:'取消',iconCls:'icon-remove',handler:function(){$('#add-dialog').dialog('close');}}]">
	<form id="add-data-form" enctype="multipart/form-data" target="uploadImgFrame" method="post" style="margin: 0px;padding: 0px;">
	<input type="hidden" name="id" />
	<table class="mf-table">
		<tr class="mf-line">
			<td class="mf-left">菜谱选择：</td>
			<td class="mf-right"><input type="text" style="width: 280px" name="menuId" id="menuId" class="easyui-combobox" data-options="required:true"/></td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">状态：</td>
			<td class="mf-right">
				<input type="radio" name="status" id ="statusYes" value="1" data-options="required:true"/>使用中
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="status" value="0" data-options="required:true"/>暂停使用
			</td>
		</tr>
	</table>
	</form>
</div>
</div>


<script type="text/javascript" src="<%=basePath%>/views/js/advert.js"></script>

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
