<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String thisPath = basePath + "/foodstore/";	
%>
<jsp:include page="../include.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script src="<%=basePath%>/js/uploadPreview/uploadPreview.js" type="text/javascript"></script>
    
<style type="text/css">
	.inputWitdh{
		width: 40px
	}

</style>
</head>
<body>

<!-- 查询条件panel 一般使用只需修改 标题:title -->
<div id="gridTools" data-options="border:false" style="padding-top: 10px;">
	<form id="search-form" style="margin-left: 10px;">
	<a href="javascript:void(0)" title="添加一条数据" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="gridAdd()">添加</a>&nbsp;&nbsp;
	<a href="javascript:void(0)" title="批量删除" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onClick="gridAllDel()">删除</a>&nbsp;&nbsp;
	          食材学名：<input class="easyui-textBox" type="text" name="searchName" style="width: 150px"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:void(0)" title="查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onClick="gridSearch()">查询</a>
		<a href="javascript:void(0)" title="重置当前查询条件重新查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onClick="gridReload()">重置</a>&nbsp;&nbsp;&nbsp;
		
		<!-- 批量导入导出  2018-04-09  huzi -->
		<a href="javascript:void(0)" title="导入用户数据" style="margin-right: 10px;" class="easyui-linkbutton" onClick="importExcel()">导入</a>
		<a href="javascript:void(0)" title="导出用户数据" style="margin-right: 10px;" class="easyui-linkbutton" onClick="exportExcel()">导出</a>
	</form>
</div>
	<!--  数据列表datagrid    一般使用只需要配置 标题:title 数据加载url地址:url  data-options第二行配置是datagrid的默认配置 -->
	<table id="mygrid" style="margin-top: 10px;display: none;" class="myResize"
	      data-options="url:'<%=thisPath %>searchFoodStoreList.htm',loadFilter:loadFilter,
	      fitColumns:true,animate: true,showFooter: false,onLoadSuccess:gridLoadSuccess,pagination:true,loading:true,rownumbers:true">
	    <thead>
	        <tr>
		        <th data-options="field:'ck',checkbox:true"></th>
		        <th data-options="field:'uid',width:4,align:'center'">食材uid</th>
		        <th data-options="field:'name',width:4,align:'center'">食材学名</th>
		        <th data-options="field:'alias',width:5,align:'center'">别名</th>
		        <th data-options="field:'firstName',width:3,align:'center'">一级分类</th>
		        <th data-options="field:'secondName',width:3,align:'center'">二级分类</th>
		        <th data-options="field:'dosage',width:3,align:'center'">建议用量</th>
		        <th data-options="field:'productPlace',width:5,align:'center'">产地</th>
		        <th data-options="field:'sellingPlace',width:5,align:'center'">热销地</th>
		        <th data-options="field:'logoPath',width:8,align:'center',formatter:imageFormatter">封面图片</th>
		        <th data-options="field:'frequence',width:2,align:'center'">使用频率</th>
	        	<th data-options="field:'menuName',width:6,align:'center'">常见菜谱</th>
	        	<th data-options="field:'remark',width:5,align:'center'">备注</th>
	        	<th data-options="field:'createTime',width:6,align:'center'">添加时间</th>
	        	<th data-options="field:'clicknum',width:4,formatter:EditFormatter,align:'center'">修改</th>
	        </tr>
	    </thead>
	</table>
	
<!-- 添加/修改弹出dialog buttons配置按钮默认是添加与取消按钮 -->
<div style="display: none;">

<div id="add-dialog" style="width: 850px;height: 550px;" class="easyui-dialog" class="easyui-dialog"  data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,title:'添加',buttons:[{text:'保存', iconCls:'icon-ok', handler:saveFoodStore},{text:'取消',iconCls:'icon-remove',handler:function(){$('#add-dialog').dialog('close');}}]">
	<form id="add-data-form" enctype="multipart/form-data" target="uploadImgFrame" method="post" style="margin: 0px;padding: 0px;">
	<input type="hidden" name="id" />
	<table class="mf-table">
		<tr class="mf-line">
			<td class="mf-left">食材UID：</td>
			<td class="mf-right">
				<input type="text" name="uid" style="width: 250px" data-options="required:true" class="easyui-validatebox"/>
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">食材学名：</td>
			<td class="mf-right">
				<input type="text" name="name" style="width: 250px" data-options="required:true" class="easyui-validatebox"/>
			</td>
			<td class="mf-left">别名：</td>
			<td class="mf-right">
				<input type="text" name="alias" style="width: 250px" data-options="required:true" class="easyui-validatebox"/>
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">一级类别：</td>
			<td class="mf-right">
				<input type="text" name="firstId" id="firstId" style="width: 250px" class="easyui-combobox" data-options="required:true"/>
			</td>
			<td class="mf-left">二级类别：</td>
			<td class="mf-right">
				<input type="text" name="secondId" id="secondId" style="width: 250px" class="easyui-combobox" data-options="required:true"/>
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">产地：</td>
			<td class="mf-right">
				<input type="text" name="productPlace" style="width: 250px"  data-options="required:true" class="easyui-validatebox"/>
			</td>
			<td class="mf-left">热销地：</td>
			<td class="mf-right">
				<input type="text" name="sellingPlace" style="width: 250px" data-options="required:true" class="easyui-validatebox"/>
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">使用频率：</td>
			<td class="mf-right">
				<input type="text" name="useFrequence" style="width: 250px" data-options="required:true" class="easyui-validatebox"/>%
			</td>
			<td class="mf-left">建议用量：</td>
			<td class="mf-right">
				<input type="text" name="dosage" style="width: 250px" data-options="required:true" class="easyui-validatebox"/>g
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">图片：</td>
			<td class="mf-right" colspan="3">
				 <div id="imgdiv"><img id="imgShow" width="275" height="140" /></div>
   				 <input type="file" id="up_img" name="file"/>
			</td>
		</tr>
		<tr class="mf-line">
				<td class="mf-left">常见菜谱：</td>
				<td class="mf-right" colspan="3">
					<input type="text" name="menuId" id="menuId" style="width: 275px"  class="easyui-combobox"/>
				</td>
		</tr>
		<tr class="mf-line">
				<td class="mf-left">备注：</td>
				<td class="mf-right" colspan="3">
					<textarea type="text" name="remark" style="width: 100%;height: 80px" data-options="required:true" class="easyui-validatebox"></textarea>
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


<script type="text/javascript" src="<%=basePath%>/views/js/foodStore.js"></script>

<script type="text/javascript">

	
	$(function(){
		dataOptions.baseUrl = '<%=thisPath%>';
		
		dataOptions.basePath = '<%=basePath%>';
		
		dataOptions.addInit = function(){
			
		};
		dataOptions.editInit = function(data){
		};
		
	});
	
	
		// 一级分类
	   $('#firstId').combobox({
	            valueField:'id', //值字段
	            textField:'name', //显示的字段
	            url:'<%=basePath%>'+'/foodcategory/getFirstCombox.htm',
	            panelHeight:300,
	            editable:true,//不可编辑，只能选择
	            onChange:function(firstId){
            		if(firstId && firstId !='' && firstId !='undefined'){
            			$('#secondId').combobox({
			                valueField:'id', //值字段
			                textField:'name', //显示的字段
			                url:'<%=basePath%>'+'/foodcategory/getSecondCombox.htm?firstId='+firstId,
			                panelHeight:300,
			                editable:true
		                });
            		}
	            }
	   	});
	

   
</script>
</body>
</html>
