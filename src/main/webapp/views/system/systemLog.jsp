<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<% 
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String thisPath = basePath + "/log/";	
%>
<jsp:include page="../include.jsp"></jsp:include>

<!-- 查询条件panel 一般使用只需修改 标题:title -->
<div id="gridTools" data-options="border:false" style="padding-top: 10px;">
	<form id="search-form" style="padding-left: 10px">
		<a href="javascript:void(0)" title="批量删除" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onClick="gridAllDel()">批量删除</a>
	</form>
</div>
<!--  数据列表datagrid    一般使用只需要配置 标题:title 数据加载url地址:url  data-options第二行配置是datagrid的默认配置 -->
<table id="mygrid" style="margin-top: 10px;display: none;" class="myResize"
      data-options="url:'<%=thisPath %>getloglist.htm',loadFilter:loadFilter,
      fitColumns:true,animate: true,showFooter: false,onLoadSuccess:gridLoadSuccess,pagination:true,loading:true,nowrap:true,rownumbers:true">
    <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<th data-options="field:'name',width:10,align:'center'">用户名</th>
        	<th data-options="field:'operationType',width:15,align:'center'">操作类型</th>
        	<th data-options="field:'evenName',width:25,align:'center'">事件</th>
        	<th data-options="field:'ipAddress',width:15,align:'center'">登录IP</th>
            <th data-options="field:'createTime',width:15,align:'center'">操作时间</th>
        </tr>
    </thead>
</table>

<script type="text/javascript" src="<%=basePath%>/views/js/systemLog.js"></script>
<script type="text/javascript">

	$(function(){
		dataOptions.baseUrl = '<%=thisPath%>';
		dataOptions.addInit = function(){
			console.log('add');
		};
		dataOptions.editInit = function(data){
			console.log(data);
		};
	});
	
</script>