<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String thisPath = basePath + "/systemuser/";	
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
		<a href="javascript:void(0)" title="添加一条数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="gridAdd()">添加</a>
		<a href="javascript:void(0)" title="批量删除数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="gridDel()">删除</a>&nbsp;&nbsp;&nbsp;
		</form>
	</div>
	<!--  数据列表datagrid    一般使用只需要配置 标题:title 数据加载url地址:url  data-options第二行配置是datagrid的默认配置 -->
	<table id="mygrid" style="margin-top: 10px;display: none;width: 100%" class="myResize"
	      data-options="url:'<%=thisPath %>getSystemUserList.htm',loadFilter:loadFilter,
	      fitColumns:true,animate: true,showFooter: false,onLoadSuccess:gridLoadSuccess,pagination:true,loading:true,rownumbers:true">
	    <thead>
	        <tr><th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'userName',width:10,align:'center'">系统账号</th>
	        	<th data-options="field:'name',width:10,align:'center'">角色身份</th>
	        	<th data-options="field:'status',width:8,formatter: function(value,row,index){
	        			if(value != null){
		  					if(value == '1'){
		  						return '使用中' ;
		  					}else{
		  						return '暂停使用' ;
		  					}
		  				}
				},align:'center'">使用状态</th>
	        	<th data-options="field:'createTime',width:10,align:'center'">时间</th>
	        	<th data-options="field:'clicknum2',width:10,formatter:userFormatter,align:'center'">修改</th>
	        </tr>
	    </thead>
	</table>
</div>


<!-- 添加/修改弹出dialog buttons配置按钮默认是添加与取消按钮 -->
<div style="display: none;">

<div id="add-dialog" style="width: 600px;height: 550px;" class="easyui-dialog" class="easyui-dialog"  data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,title:'添加',buttons:[{text:'保存', iconCls:'icon-ok', handler:saveUser},{text:'取消',iconCls:'icon-remove',handler:function(){$('#add-dialog').dialog('close');}}]">
	<form id="add-data-form" enctype="multipart/form-data" target="uploadImgFrame" method="post" style="margin: 0px;padding: 0px;">
	<input type="hidden" name="id" />
	<table class="mf-table">
		<tr class="mf-line">
			<td class="mf-left">系统账号：</td>
			<td class="mf-right">
				 <input type="text" name="userName" class="easyui-validatebox" data-options="required:true" style="width: 360px"/>
			</td>
		</tr>
		<tr class="mf-line" id="tr_pwd" style="display: none;">
			<td class="mf-left">设置密码：</td>
			<td class="mf-right">
				<input type="password" name="pwd" id="pwd" class="easyui-validatebox" style="width: 360px"/>
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">角色身份：</td>
			<td class="mf-right">
				<input class="easyui-combobox" id="roleId" name="roleId" style="width: 360px" data-options="valueField:'id',textField:'name',url:'<%=basePath %>/role/getComboxList.htm',required:true" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">状态：</td>
			<td class="mf-right" colspan="3">
				<input type="radio" name="status" id ="statusYes" value="1" data-options="required:true"/>使用中
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="status" value="0" data-options="required:true"/>暂停使用
			</td>
		</tr>
	</table>
	</form>
</div>
</div>


<!-- 修改密码 -->
<div style="display: none;">

	<div id="update-pwd-dialog" style="width: 550px;height: 400px;" class="easyui-dialog"  data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,title:'添加',buttons:[{text:'保存', iconCls:'icon-ok', handler:updatePwd},{text:'取消',iconCls:'icon-remove',handler:function(){$('#update-pwd-dialog').dialog('close');}}]">
		<form id="update-pwd-form" enctype="multipart/form-data" target="uploadImgFrame" method="post" style="margin: 0px;padding: 0px;">
			<input type="hidden" name="id" id="updateId"/>
			<table class="mf-table">
				<tr class="mf-line">
					<td class="mf-left">原密码：</td>
					<td class="mf-right">
						 <input type="password" name="oldPwd" class="easyui-textbox" data-options="required:true" style="width: 360px"/>
					</td>
				</tr>
				<tr class="mf-line">
					<td class="mf-left">新密码：</td>
					<td class="mf-right">
						<input type="password" name="newPwd" class="easyui-textbox" data-options="required:true" style="width: 360px"/>
					</td>
				</tr>
				<tr class="mf-line">
					<td class="mf-left">确认密码：</td>
					<td class="mf-right">
						<input type="password" name="confirmPwd" class="easyui-textbox" data-options="required:true" style="width: 360px"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<script type="text/javascript" src="<%=basePath%>/views/js/systemUser.js"></script>

<script type="text/javascript">

	$(function(){
	
		dataOptions.baseUrl = '<%=thisPath%>';
		
		dataOptions.basePath = '<%=basePath%>';
		
		dataOptions.addInit = function(){
			
		};
		dataOptions.editInit = function(data){
		};
	});
   
   
   // 省级 
     $('#province').combobox({
            valueField:'id', //值字段
            textField:'name', //显示的字段
            url:'<%=basePath%>'+'/region/prolist.htm',
            panelHeight:300,
            editable:true,//不可编辑，只能选择
            onChange:function(province){
	                $('#city').combobox({
	                valueField:'id', //值字段
	                textField:'name', //显示的字段
	                url:'<%=basePath%>'+'/region/citylist.htm?province_id='+province,
	                panelHeight:300,
	                editable:true,
	                onChange:function(city){
		                $('#district').combobox({
			                valueField:'id', //值字段
			                textField:'name', //显示的字段
			                url:'<%=basePath%>'+'/region/districtlist.htm?city_id='+city,
			                panelHeight:300,
			                editable:true,
		                });
	                }
	            });
            }
   	});
</script>
    
</body>
</html>
