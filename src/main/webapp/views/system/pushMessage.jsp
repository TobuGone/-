<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String thisPath = basePath + "/push/";	
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
	      data-options="url:'<%=thisPath %>getPushMessageList.htm',loadFilter:loadFilter,
	      fitColumns:true,animate: true,showFooter: false,onLoadSuccess:gridLoadSuccess,pagination:true,loading:true,rownumbers:true">
	    <thead>
	        <tr><th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'title',width:8,align:'center'">标题</th>
	        	<th data-options="field:'createTime',width:5,align:'center'">添加时间</th>
	        	<th data-options="field:'clicknum',width:5,formatter:searchFormatter,align:'center'">操作</th>
	        </tr>
	    </thead>
	</table>
	
<!-- 添加/修改弹出dialog buttons配置按钮默认是添加与取消按钮 -->
<div style="display: none;">

<div id="add-dialog" style="width: 650px;height: 540px;" class="easyui-dialog" class="easyui-dialog"  data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,title:'添加',buttons:[{text:'保存', iconCls:'icon-ok', handler:savePushMessage},{text:'取消',iconCls:'icon-remove',handler:function(){$('#add-dialog').dialog('close');}}]">
	<form id="add-data-form" enctype="multipart/form-data" target="uploadImgFrame" method="post" style="margin: 0px;padding: 0px;">
	<input type="hidden" name="id" />
	<table class="mf-table">
		<tr class="mf-line">
			<td class="mf-left">标题：</td>
			<td class="mf-right">
   				 <input type="text" name="title" style="width: 98%"data-options="required:true,validType:'maxLength[20]'" class="easyui-textBox"/>
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">推送内容：</td>
			<td class="mf-right""><textarea style="width:98%;height:380px;" id="messagecontent" name="content"></textarea></td>
		</tr>
	</table>
	</form>
</div>
</div>


<div style="display: none;">

<div id="details-dialog" style="width: 650px;height: 550px;" class="easyui-dialog" class="easyui-dialog"  data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,title:'添加',buttons:[{text:'取消',iconCls:'icon-remove',handler:function(){$('#details-dialog').dialog('close');}}]">
	<form id="details-data-form" enctype="multipart/form-data" target="uploadImgFrame" method="post" style="margin: 0px;padding: 0px;">
	<input type="hidden" name="id" />
	<table class="mf-table">
		<tr class="mf-line" style="height: 30px">
			<td class="mf-left">标题：</td>
			<td class="mf-right">
				<label id="title"></label>
			</td>
		</tr>
		<tr class="mf-line" style="width:98%;height:380px;">
			<td class="mf-left">推送内容：</td>
			<td class="mf-right"">
				<textarea style="width:98%;height:360px;" id="messageDetails" name="message"></textarea>
			</td>
		</tr>
		<tr class="mf-line" style="height: 30px">
			<td class="mf-left">推送时间：</td>
			<td class="mf-right">
				<label id="createTime"></label>
			</td>
		</tr>
	</table>
	</form>
</div>
</div>

<script type="text/javascript" src="<%=basePath%>/views/js/pushMessage.js"></script>

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
