<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String thisPath = basePath + "/comment/";	
%>
<jsp:include page="../include.jsp"></jsp:include>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script src="<%=basePath%>/js/uploadPreview/uploadPreview.js" type="text/javascript"></script>
<script type="text/javascript">
var functionFormatter = function(value,row,index){
		var status = row.isShow ;
		var title = '' ;
		if(status == 0){
			title = '显示' ;
			status = 1 ;
		}else{
			title = '不显示' ;
			status = 0 ;
		}
		return '<a id="" href="javascript:void(0)" style="margin-left: 10px;color: blue;width:50px;" '+
		   'class="easyui-linkbutton cu-lbtn" onClick="operater('+row.id+','+status+')">'+title+'</a>';
};
</script>
</head>
<body>

<!-- 查询条件panel 一般使用只需修改 标题:title -->
<div id="gridTools" data-options="border:false" style="padding-top: 10px;" align="center">
	<form id="search-form" style="margin-left: 10px;">
	菜谱名称：<input class="easyui-textBox" type="text" name="name" style="width: 150px"/>
	状态：<input class="easyui-textBox" type="text" name="isShow" id="show" style="width: 150px"/>
	<a href="javascript:void(0)" title="删除" class="easyui-linkbutton" style="margin-left: 10px;" data-options="iconCls:'icon-search'" onclick="gridSearch()">查询</a>&nbsp;&nbsp;
	</form>
</div>
	<!--  数据列表datagrid    一般使用只需要配置 标题:title 数据加载url地址:url  data-options第二行配置是datagrid的默认配置 -->
	<table id="mygrid" style="margin-top: 10px;display: none;" class="myResize"
	      data-options="url:'<%=thisPath %>getList.htm',loadFilter:loadFilter,
	      fitColumns:true,animate: true,showFooter: false,onLoadSuccess:gridLoadSuccess,pagination:true,loading:true,rownumbers:true">
	    <thead>
	        <tr>
	        	<th data-options="field:'ck',checkbox:true"></th>
	        	<th data-options="field:'id',align:'center',width:5">序号</th>
	        	<th data-options="field:'name',width:10,align:'center'">菜谱名称</th>
	        	<th data-options="field:'userName',width:8,align:'center'">手机号</th>
	        	<th data-options="field:'nickName',width:8,align:'center'">昵称</th>
	        	<th data-options="field:'isShow',width:8,align:'center',formatter: function(value,row,index){
	        			if(value != null){
		  					if(value == '1'){
		  						return '显示' ;
		  					}else{
		  						return '不显示' ;
		  					}
		  				}
				},align:'center'"">状态</th>
	        	<th data-options="field:'content',width:8,align:'center'">内容</th>
	        	<th data-options="field:'createTime',width:8,align:'center'">创建时间</th>
	        	<th data-options="field:'clicknum',width:5,formatter:functionFormatter,align:'center'">操作</th>
	        </tr>
	    </thead>
	</table>
	
<script type="text/javascript" src="<%=basePath%>/views/js/comment.js"></script>
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
