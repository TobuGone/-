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
	          菜谱名称：<input class="easyui-textBox" type="text" name="searchMenuBookName" style="width: 150px"/>&nbsp;&nbsp;&nbsp;&nbsp;    
		<a href="javascript:void(0)" title="查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onClick="gridSearch()">查询</a>
		<a href="javascript:void(0)" title="重置当前查询条件重新查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onClick="gridReload()">重置</a>&nbsp;&nbsp;&nbsp;
	</form>
</div>
	<!--  数据列表datagrid    一般使用只需要配置 标题:title 数据加载url地址:url  data-options第二行配置是datagrid的默认配置 -->
	<table id="mygrid" style="margin-top: 10px;display: none;" class="myResize"
	      data-options="url:'<%=thisPath %>getMenuBookListByAuditStatus.htm?AuditStatus=2',loadFilter:loadFilter,
	      fitColumns:true,animate: true,showFooter: false,onLoadSuccess:gridLoadSuccess,pagination:true,loading:true,rownumbers:true">
	    <thead>
	        <tr>
	        	<th data-options="field:'ck',checkbox:true"></th>
		        <th data-options="field:'nickName',width:5,align:'center'">用户昵称</th>
		        <th data-options="field:'name',width:5,align:'center'">菜谱名</th>
		        <th data-options="field:'category',width:5,align:'center'">菜谱类别</th>
		        <th data-options="field:'logoPath',width:8,align:'center',formatter:imageFormatter">封面图片</th>
		        <th data-options="field:'describes',width:9,align:'center'">菜谱描述</th>
		        <th data-options="field:'foods',width:9,align:'center'">食材</th>
		        <th data-options="field:'menuProcedure',width:8,align:'center'">步骤</th>
	        	<th data-options="field:'createTime',width:6,align:'center'">添加时间</th>
	        	<th data-options="field:'clicknum1',width:8,formatter:auditFormatter,align:'center'">审核</th>
	        	<th data-options="field:'clicknum2',width:5,formatter:searchFormatter,align:'center'">查看</th>
	        </tr>
	    </thead>
	</table>
	
<!-- 添加/修改弹出dialog buttons配置按钮默认是添加与取消按钮 -->
<div style="display: none;">

<div id="search-dialog" style="width: 700px;height: 720px;" class="easyui-dialog" class="easyui-dialog"  data-options="iconCls:'icon-tip',resizable:true,modal:true,closed:true,title:'添加',buttons:[{text:'取消',iconCls:'icon-remove',handler:function(){$('#search-dialog').dialog('close');}}]">
	<table class="mf-table">
		<tr class="mf-line">
			<td class="mf-left">菜谱名：</td>
			<td class="mf-right">
   				 <label id="name" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">菜谱封面图：</td>
			<td class="mf-right">
   				<div><img id="imgShow" width="350" height="175" /></div>
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">菜谱类别：</td>
			<td class="mf-right">
				<div id="category">
				</div>
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">食材：</td>
			<td class="mf-right">
				<label id="foods" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">菜谱描述：</td>
			<td class="mf-right">
				<label id="describes" />
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">步骤：</td>
			<td class="mf-right">
				<div id="procedure">
				</div>
			</td>
		</tr>
		
	</table>
</div>
</div>


<!-- 审核通过窗口-->
<div style="display: none;">

<div id="adopt-dialog" style="width: 600px;height: 450px;" class="easyui-dialog" class="easyui-dialog"  data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,title:'添加',buttons:[{text:'保存', iconCls:'icon-ok', handler:saveAdopt},{text:'取消',iconCls:'icon-remove',handler:function(){$('#adopt-dialog').dialog('close');}}]">
	<form id="adopt-data-form" enctype="multipart/form-data" target="uploadImgFrame" method="post" style="margin: 0px;padding: 0px;">
	<input type="hidden" name="id" id="adoptId"/>
	<input type="hidden" name ="categoryNumber"/>
	<table class="mf-table">
		<tr class="mf-line">
			<td class="mf-left">菜谱类别：</td>
			<td class="mf-right">
				<div id="menuCategory">
					<div id="div_c1">
						<table>
							<tr>
								<td>一级：</td>
								<td><input type="text" name="firstId1" id="firstId1" style="width: 150px" class="easyui-combobox" data-options="required:true"/></td>
								<td>&nbsp;&nbsp;&nbsp;二级：</td>
								<td><input type="text" name="secondId1" id="secondId1" style="width: 150px" class="easyui-combobox" data-options="required:true"/></td>
							</tr>
						</table>
					</div>
				</div>
				<br />
				<div style="margin-left: 180px"><input type="button" value="添加" onclick="addCategory()"/>&nbsp;&nbsp;<input type="button" value="删除" onclick="delCategory()"/></div>
			</td>
		</tr>
	</table>
	</form>
</div>
</div>


<!-- 审核不通过窗口-->
<div style="display: none;">

<div id="noadopt-dialog" style="width: 600px;height: 450px;" class="easyui-dialog" class="easyui-dialog"  data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,title:'添加',buttons:[{text:'保存', iconCls:'icon-ok', handler:saveNoAdopt},{text:'取消',iconCls:'icon-remove',handler:function(){$('#noadopt-dialog').dialog('close');}}]">
	<form id="noadopt-data-form" enctype="multipart/form-data" target="uploadImgFrame" method="post" style="margin: 0px;padding: 0px;">
	<input type="hidden" name="id" id="noadoptId"/>
	<table class="mf-table">
		<tr class="mf-line">
			<td class="mf-left">提示：</td>
			<td class="mf-right">
				<textarea style="width: 100%;height: 320" name="remark" data-options="required:true" class="easyui-validatebox"></textarea>
			</td>
		</tr>
	</table>
	</form>
</div>
</div>


<script type="text/javascript" src="<%=basePath%>/views/js/auditMenuBook.js"></script>

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
	   $('#firstId1').combobox({
	            valueField:'id', //值字段
	            textField:'name', //显示的字段
	            url:'<%=basePath%>'+'/menuCategory/getFirstCombox.htm',
	            panelHeight:300,
	            editable:true,//不可编辑，只能选择
	            onChange:function(firstId){
            		if(firstId && firstId !='' && firstId !='undefined'){
            			$('#secondId1').combobox({
			                valueField:'id', //值字段
			                textField:'name', //显示的字段
			                url:'<%=basePath%>'+'/menuCategory/getSecondCombox.htm?firstId='+firstId,
			                panelHeight:300,
			                editable:true
		                });
            		}
	            }
	   	});
	   	
	   	
	   function addCategory(){
   
	   		var value = $("input[name='categoryNumber']").val(); 
	   		
	   		value++;
	   		
			var html = '<div id="div_c'+value+'"><table><tr><td>一级：</td><td><input type="text" name="firstId'+value+'" id="firstId'+value+'" style="width: 150px" class="easyui-combobox" data-options="required:true"/></td><td>&nbsp;&nbsp;&nbsp;二级：</td><td><input type="text" name="secondId'+value+'" id="secondId'+value+'" style="width: 150px" class="easyui-combobox" data-options="required:true"/></td></tr></table></div>';
	   		
	   		$("#menuCategory").append(html);
	   		
	   		
	   		$("#firstId"+value).combobox({
			    url:'<%=basePath%>'+'/menuCategory/getFirstCombox.htm',
			    valueField:'id',
			    textField:'name',
			    panelHeight:300,
	            editable:true,//不可编辑，只能选择
	            onChange:function(firstId){
	            	if(firstId && firstId !='' && firstId !='undefined'){
	           			 $('#secondId'+value).combobox({
			                valueField:'id', //值字段
			                textField:'name', //显示的字段
			                url:'<%=basePath%>'+'/menuCategory/getSecondCombox.htm?firstId='+firstId,
			                panelHeight:300,
			                editable:true
			            });
	           		}
		               
	            }
			});
			
		
		$('#secondId'+value).combobox();
		
   		$("input[name='categoryNumber']").val(value);
   		
   };
   
   
   
   
   	 function delCategory(){
   		var value = $("input[name='categoryNumber']").val();
   		
   		if(value > 1){
   			$("#div_c"+value).remove();
   			value--;
   		}
   		
   		$("input[name='categoryNumber']").val(value);
   		
   }
</script>
    
</body>
</html>
