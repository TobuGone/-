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
</style>
</head>
<body>

<!-- 查询条件panel 一般使用只需修改 标题:title -->
<div id="gridTools" data-options="border:false" style="padding-top: 10px;">
	<form id="search-form" style="margin-left: 10px;">
	<a href="javascript:void(0)" title="添加一条数据" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="gridAdd()">添加</a>&nbsp;&nbsp;
	<a href="javascript:void(0)" title="批量删除" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onClick="gridAllDel()">删除</a>&nbsp;&nbsp;
	<a href="javascript:void(0)" title="添加一条数据" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="gridImport()">导入菜谱</a>&nbsp;&nbsp;
	<a href="javascript:void(0)" title="导出菜谱数据" style="margin-right: 10px;" class="easyui-linkbutton" onClick="exportMenuBook()">导出菜谱</a>
	          菜谱名：<input class="easyui-textBox" type="text" name="searchName" style="width: 150px"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:void(0)" title="查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onClick="gridSearch()">查询</a>
		<a href="javascript:void(0)" title="重置当前查询条件重新查询数据" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onClick="gridReload()">重置</a>&nbsp;&nbsp;&nbsp;
	</form>
</div>
	<!--  数据列表datagrid    一般使用只需要配置 标题:title 数据加载url地址:url  data-options第二行配置是datagrid的默认配置 -->
	<table id="mygrid" style="margin-top: 10px;display: none;" class="myResize"
	      data-options="url:'<%=thisPath %>searchMenuBookList.htm',loadFilter:loadFilter,
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
		        <th data-options="field:'click_number',width:2,align:'center'">点击量</th>
		        <th data-options="field:'recommend',width:2,formatter:recommendFormatter,align:'center'">推荐</th>
	        	<th data-options="field:'isAdvert',width:4,formatter:isAdvertFormatter,align:'center'">设为盖头广告</th>
	        	<th data-options="field:'createTime',width:6,align:'center'">添加时间</th>
	        	<th data-options="field:'clicknum',width:4,formatter:EditFormatter,align:'center'">修改</th>
	        </tr>
	    </thead>
	</table>
	
<!-- 添加/修改弹出dialog buttons配置按钮默认是添加与取消按钮 -->
<div style="display: none;">

<div id="add-dialog" style="width: 80%;height:850px;" class="easyui-dialog" class="easyui-dialog"  data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
							title:'添加',buttons:[{text:'保存', iconCls:'icon-ok', handler:saveMenuBook},{text:'取消',iconCls:'icon-remove',handler:cancle}]">
	<form id="add-data-form" enctype="multipart/form-data" target="uploadImgFrame" method="post" style="margin: 0px;padding: 0px;">
	<input type="hidden" name="id" />
	<input type="hidden" name ="addNumber"/>
	<input type="hidden" name ="categoryNumber"/>
	<input type="hidden" name ="uid"/>
	<table class="mf-table">
		<tr class="mf-line">
			<td class="mf-left">菜谱名：</td>
			<td class="mf-right">
				<input type="text" name="name" style="width: 485px" data-options="required:true" class="easyui-validatebox"/>
			</td>
		</tr>
		<tr class="mf-line" style="height: 200px">
			<td class="mf-left">菜谱封面图：</td>
			<td class="mf-right">
				 <div id="imgdiv"><img id="imgShow" width="300" height="150" /></div>
   				 <input type="file" id="up_img" name="file"/>
			</td>
		</tr>
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
		<tr class="mf-line">
			<td class="mf-left">菜谱描述：</td>
			<td class="mf-right">
				<input type="text" name="describes" style="width: 485px" data-options="required:true" class="easyui-validatebox"/>
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">总食材：</td>
			<td class="mf-right">
				<textarea type="text" name="foods" style="width: 485px" data-options="required:true" class="easyui-validatebox"></textarea>
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">步骤：</td>
			<td class="mf-right">
				<div id="procedure">
					<div id="div1">
						<table>
							<tr>
								<td>
									<span class="step">1</span>、时间：</td><td><input type="text" name="minute1" id="minute1" data-options="validType:['time']" class="easyui-validatebox" style="width: 40px;"/>分钟&nbsp;&nbsp;
									<input type="text" name="second1" class="easyui-validatebox" style="width: 40px;" data-options="validType:['second']"/>秒&nbsp;<span id="spsecond" style="color: red"></span>
								</td>
							</tr>
							<tr>
								<td>步骤描述：</td><td><input type="text" name="describe1" style="width: 400px"/></td>
							</tr>
							<tr class="mf-line">
								<td style="width:80px;background-color: #eee;">食材分类:</td>
								<td class="mf-right">
									<input type="hidden" name="foodCategoryNumber"/>
									<input type="hidden" name ="storeNumber" />
									<div id="foodCategory">
										<div id="div_f1">
											<table>
												<tr>
													<td>一级:</td>
													<td><input type="text" name="foodId1" id="foodId1" style="width: 80px" class="easyui-combobox" data-options="required:true"/></td>
													<td>&nbsp;二级:</td>
													<td><input type="text" name="secondfoodId1" id="secondfoodId1" style="width: 80px" class="easyui-combobox" data-options="required:true"/></td>
													<td>&nbsp;食材:</td>
													<td><input type="text" name="div1storeId" id="storeFoodId1" style="width: 80px" class="easyui-combobox" data-options="required:true"/></td>
													<td>&nbsp;重量:</td>
													<td><input type="text" name="div1name" id="name1" style="width: 50px" data-options="required:true,validType:['weight']" class="easyui-validatebox"/></td>
													<td>&nbsp;预处理:</td>
													<td><input type="text" name="div1yv" id="yv1"  style="width: 50px" data-options="required:true,validType:['yvDispose']" class="easyui-validatebox"/></td>
												</tr>
											</table>
										</div>
									</div>
									<br />
									<div style="margin-left: 30px;">
									<input type="button" value="添加食材" onclick="addFoodCategory(this)"/>&nbsp;&nbsp;&nbsp;<input type="button" value="删除食材" onclick="delFoodCategory(this)"/>
									<input type="button" value="添加步骤" onclick="addProcedure(this)" style="margin-left: 150px;"/>&nbsp;&nbsp;
									<input type="button" value="删除步骤" onclick="delProcedure(this)"/></div>
								</td>
							</tr>
						</table>
						<br />
					</div>
				</div>
			</td>
		</tr>
	</table>
	</form>
</div>
</div>



<div style="display: none;">

<div id="import-dialog" style="width: 600px;height: 420px;" class="easyui-dialog" class="easyui-dialog"  data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true,
					title:'添加',buttons:[{text:'保存', iconCls:'icon-ok', handler:saveImport},{text:'取消',iconCls:'icon-remove',handler:importCancle}]">
	<form id="import-data-form" accept-charset="UTF-8" enctype="multipart/form-data" target="uploadImgFrame" method="post" style="margin: 0px;padding: 0px;">
	<input type="hidden" name ="importCategoryNumber"/>
	<table class="mf-table">
		<tr class="mf-line">
			<td class="mf-left">菜谱类别：</td>
			<td class="mf-right">
				<div id="importCategory">
					<div id="div_import_c1">
						<table>
							<tr>
								<td>一级：</td>
								<td><input type="text" name="import_firstId1" id="import_firstId1" style="width: 150px" class="easyui-combobox" data-options="required:true"/></td>
								<td>&nbsp;&nbsp;&nbsp;二级：</td>
								<td><input type="text" name="import_secondId1" id="import_secondId1" style="width: 150px" class="easyui-combobox" data-options="required:true"/></td>
							</tr>
						</table>
					</div>
				</div>
				<br />
				<div style="margin-left: 160px"><input type="button" value="添加" onclick="importAddCategory()"/>&nbsp;&nbsp;<input type="button" value="删除" onclick="importDelCategory()"/></div>
			</td>
		</tr>
		<tr class="mf-line">
			<td class="mf-left">菜谱文件名：</td>
			<td class="mf-right">
				<input type="file" id="fileText" name="file" onchange="fileChange(this)"  multiple="multiple" />
				<p style="color: red;" id="fileSpan"></p>
			</td>
		</tr>
	</table>
	</form>
</div>
</div>

<script type="text/javascript" src="<%=basePath%>/views/js/menuBook.js"></script>

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
	   	
	   	//食材一级分类
	   $('#foodId1').combobox({
	            valueField:'id', //值字段
	            textField:'name', //显示的字段
	            url:'<%=basePath%>'+'/foodcategory/getFirstCombox.htm',
	            panelHeight:300,
	            editable:true,//不可编辑，只能选择
	            onChange:function(firstId){
            		if(firstId && firstId !='' && firstId !='undefined'){
            			$('#secondfoodId1').combobox({
			                valueField:'id', //值字段
			                textField:'name', //显示的字段
			                url:'<%=basePath%>'+'/foodcategory/getSecondCombox.htm?firstId='+firstId,
			                panelHeight:300,
			                editable:true,
			                onChange:function(foodId){
			            		if(foodId && foodId !='' && foodId !='undefined'){
			            			$('#storeFoodId1').combobox({
						                valueField:'id', //值字段
						                textField:'name', //显示的字段
						                url:'<%=basePath%>'+'/foodstore/getSecondCombox.htm?foodId='+foodId,
						                panelHeight:300,
						                editable:true
					                });
			            		}
	           				}
		                });
            		}
	            }
	   	});
	
	
	// 一级分类
	   $('#import_firstId1').combobox({
	            valueField:'id', //值字段
	            textField:'name', //显示的字段
	            url:'<%=basePath%>'+'/menuCategory/getFirstCombox.htm',
	            panelHeight:300,
	            editable:true,//不可编辑，只能选择
	            onChange:function(firstId){
            		if(firstId && firstId !='' && firstId !='undefined'){
            			$('#import_secondId1').combobox({
			                valueField:'id', //值字段
			                textField:'name', //显示的字段
			                url:'<%=basePath%>'+'/menuCategory/getSecondCombox.htm?firstId='+firstId,
			                panelHeight:300,
			                editable:true
		                });
            		}
	            }
	   	});
	
   
   var addProcedure = function(obj){
   		var value = $("input[name='addNumber']").val(); 
   		value++;
   		var step = $("#procedure").find(".step").length+1;
		var html = '<div id="div'+value+'"><table><tr><td><span class="step">'+step+'</span>、时间：</td>';
		html += '<td><input type="text" name="minute'+value+'" id="minute'+value+'" class="easyui-validatebox" style="width: 40px;" data-options="validType:[\'time\']"/>分钟&nbsp;&nbsp;';
		html += '<input name="second'+value+'" id="second'+value+'" class="easyui-validatebox" style="width: 40px;"  data-options="validType:[\'second\']"/>秒&nbsp;';
		html += '<span id="spsecond" style="color: red"></span></td></tr><tr><td>步骤描述：</td><td><input type="text" name="describe'+value+'" id="describe'+value+'" style="width: 400px"/></td></tr><tr><td>';
		html += '<tr class="mf-line"><td style="width:80px;background-color: #eee;">食材分类:</td><td class="mf-right"><input type="hidden" name="foodCategoryNumber" value="1"/>';
		html += '<input type="hidden" name="storeNumber" value="'+value+'"/><div id="foodCategory"><div id="div_f1"><table>';
		html += '<tr><td>一级:</td><td><input type="text" name="foodId1" id="foodId1" style="width: 80px" class="easyui-combobox" data-options="required:true"/></td>';
		html += '<td>&nbsp;二级:</td><td><input type="text" name="secondfoodId" id="secondfoodId1" style="width: 80px" class="easyui-combobox" data-options="required:true"/></td>';
		html += '<td>&nbsp;食材:</td><td><input type="text" name="div'+value+'storeId" id="storeFoodId1" style="width: 80px" class="easyui-combobox" data-options="required:true"/></td>';	
		html += '<td>&nbsp;重量:</td><td><input type="text" name="div'+value+'name" id="name1" style="width: 50px" data-options="required:true,validType:[\'weight\']" class="easyui-validatebox" /></td>';														
		html += '<td>&nbsp;预处理:</td><td><input type="text" name="div'+value+'yv" id="yv1" style="width: 50px" data-options="required:true,validType:[\'yvDispose\']" class="easyui-validatebox" /></td>';  //huzi	
		html += '</tr></table></div></div><br/><div style="margin-left: 30px;"><input type="button" value="添加食材" onclick="addFoodCategory(this)"/>&nbsp;&nbsp;&nbsp;<input type="button" value="删除食材" onclick="delFoodCategory(this)"/>';
		html += ' <input type="button" value="添加步骤" onclick="addProcedure(this)" style="margin-left: 150px;"/>&nbsp;&nbsp;';
		html += ' <input type="button" value="删除步骤" onclick="delProcedure(this)"/></div></td></tr></table><br /></div>';							
   		$(obj).parent().parent().parent().parent().parent().parent().after(html);
   		
   		$("#procedure").find('#div'+value).find("#foodId1").combobox({
		    url:'<%=basePath%>'+'/foodcategory/getFirstCombox.htm',
		    valueField:'id',
		    textField:'name',
		    panelHeight:300,
            editable:true,//不可编辑，只能选择
            onChange:function(firstId){
            	if(firstId && firstId !='' && firstId !='undefined'){
           			 $("#procedure").find('#div'+value).find('#secondFoodId1').combobox({
		                valueField:'id', //值字段
		                textField:'name', //显示的字段
		                url:'<%=basePath%>'+'/foodcategory/getSecondCombox.htm?firstId='+firstId,
		                panelHeight:300,
		                editable:true,
		                 onChange:function(foodId){
			            		if(foodId && foodId !='' && foodId !='undefined'){
			            			 $("#procedure").find('#div'+value).find('#storeFoodId1').combobox({
						                valueField:'id', //值字段
						                textField:'name', //显示的字段
						                url:'<%=basePath%>'+'/foodstore/getSecondCombox.htm?foodId='+foodId,
						                panelHeight:300,
						                editable:true
					                });
			            		}
	           				}
		            });
           		}
	               
            }
		});
   		$("#procedure").find('#div'+value).find('#storeFoodId1').combobox();
   		$("#procedure").find('#div'+value).find('#secondFoodId1').combobox();
		$("#procedure").find('#div'+value).find('#name1').validatebox();
		$("#procedure").find('#div'+value).find('#yv1').validatebox();	//huzi
		$("#procedure").find('#div'+value).find('input[name=\'minute'+value+'\']').validatebox();
		$("#procedure").find('#div'+value).find('input[name=\'second'+value+'\']').validatebox();
   		$("input[name='addNumber']").val(value);
   		$("#procedure").find(".step").each(function(i){
	   			$(this).text(i+1);
	   	});
   		
   };
   
   
   function delProcedure(obj){
   		var value = $("input[name='addNumber']").val();
   		var step = $("#procedure").find(".step").length;
   		if(step > 1){
   			$(obj).parent().parent().parent().parent().parent().parent().remove();
	   		$("#procedure").find(".step").each(function(i){
	   			$(this).text(i+1);
	   		})
   		}
   }

   
   	 var addCategory = function(){
   
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
   
    var addFoodCategory = function(obj){
    	var object = $(obj).parent().parent('td');
   		var value = object.find("input[name='foodCategoryNumber']").val();
   		var storeNumber = object.find("input[name='storeNumber']").val();
   		if(value == 5){return;} 
   		value++;
		var html = '<div id="div_f'+value+'"><table><tr><td>一级:</td><td><input type="text" name="foodId'+value+'" id="foodId'+value+'" style="width: 80px" class="easyui-combobox" data-options="required:true"/></td><td>&nbsp;二级:</td><td><input type="text" name="secondFoodId" id="secondFoodId'+value+'" style="width: 80px" class="easyui-combobox" data-options="required:true"/></td>';
		html += '<td>&nbsp;食材:</td><td><input type="text" name="div'+storeNumber+'storeId" id="storeFoodId'+value+'" style="width: 80px" class="easyui-combobox" data-options="required:true"/></td><td>&nbsp;重量:</td><td><input type="text" name="div'+storeNumber+'name" id="name'+value+'" style="width: 50px" data-options="required:true,validType:[\'weight\']" class="easyui-validatebox"/></td><td>&nbsp;预处理:</td><td><input type="text" name="div'+storeNumber+'yv" id="yv'+value+'" style="width: 50px" data-options="required:true,validType:[\'yvDispose\']" class="easyui-validatebox"/></td></tr></table></div>';
   		$(object).find("#foodCategory").append(html);
   		$(object).find("#foodId"+value).combobox({
		    url:'<%=basePath%>'+'/foodcategory/getFirstCombox.htm',
		    valueField:'id',
		    textField:'name',
		    panelHeight:300,
            editable:true,//不可编辑，只能选择
            onChange:function(firstId){
            	if(firstId && firstId !='' && firstId !='undefined'){
           			 $(object).find('#secondFoodId'+value).combobox({
		                valueField:'id', //值字段
		                textField:'name', //显示的字段
		                url:'<%=basePath%>'+'/foodcategory/getSecondCombox.htm?firstId='+firstId,
		                panelHeight:300,
		                editable:true,
		                onChange:function(foodId){
			            		if(foodId && foodId !='' && foodId !='undefined'){
			            			$(object).find('#storeFoodId'+value).combobox({
						                valueField:'id', //值字段
						                textField:'name', //显示的字段
						                url:'<%=basePath%>'+'/foodstore/getSecondCombox.htm?foodId='+foodId,
						                panelHeight:300,
						                editable:true
					                });
			            		}
	           				}
		            });
           		}
	               
            }
		});
		
		$(object).find('#secondFoodId'+value).combobox();
		$(object).find('#storeFoodId'+value).combobox();
		$(object).find('#name'+value).validatebox();
		$(object).find('#yv'+value).validatebox();  //huzi
		
   		$(object).find("input[name='foodCategoryNumber']").val(value);
   };
   
   function delFoodCategory(obj){
   		var object = $(obj).parent().parent('td');
   		var value = $(object).find("input[name='foodCategoryNumber']").val();
   		
   		if(value > 1){
   			$(object).find("#div_f"+value).remove();
   			value--;
   		}
   		
   		$(object).find("input[name='foodCategoryNumber']").val(value);
   		
   };
   
</script>
</body>
</html>
