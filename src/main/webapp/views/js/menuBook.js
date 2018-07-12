var dataOptions = {
	baseUrl:"",
	basePath:"",
	addInit:function(){},// 添加弹窗初始化
	editInit:function(data){},// 修改数据加载完成初始化
	saveInit:function(data){}// 保存前执行操作
};

$.extend($.fn.validatebox.defaults.rules, {    
	time: {    
        validator: function(value,param){  
        	var ti = /^[1-9][0-9]{0,9}$/;
        	return ti.test(value);
        },    
        message: '必须是整数且第一位不能为0'   
    }    
});

$.extend($.fn.validatebox.defaults.rules, {    
	second: {    
        validator: function(value,param){  
        	var ti = /^[0-9]*$/;
        	return ti.test(value);
        },    
        message: '必须是整数'   
    }    
});

$.extend($.fn.validatebox.defaults.rules, {    
	weight: {    
        validator: function(value,param){  
        	var we = /^[1-9][0-9]{0,9}$/;
        	return we.test(value);
        },    
        message: '必须是整数且第一位不能为0'   
    } 
});



var getData = function(rowIndex){
	mygrid.datagrid('selectRow', rowIndex); 
	return mygrid.datagrid('getSelections');// 取到选择的数据
};


var mygrid = $('#mygrid').datagrid();// 定义一个全局的表格对象

//查询
var gridSearch = function(){
	var searchData = $('#search-form').form('getData');
	mygrid.datagrid('options').queryParams = searchData;
	mygrid.datagrid('reload');
};

/**
 * 重置并刷新列表
 */
var gridReload = function(){
	$('#search-form').form('clear');
	mygrid.datagrid('options').queryParams = new Object();
	mygrid.datagrid('reload');
};


/**
 * 弹出添加窗口
 */
function gridAdd(){
	$('#add-data-form').form('clear');// 清除form表单数据
	$( "#add-dialog" ).dialog("open").dialog('setTitle','添加');
	new uploadPreview({ UpBtn: "up_img", DivShow: "imgdiv", ImgShow: "imgShow" });
	$("#div_f2").remove();
	$("#div_f3").remove();
	$("#div_f4").remove();
	$("#div_f5").remove();
	$("#div_f6").remove();
	$("#imgShow").attr("src","");
	
	$("input[name='uid']").val(-1)
	
	$("input[name='addNumber']").val(1)
	
	$("input[name='categoryNumber']").val(1)
	
	$("input[name='foodCategoryNumber']").val(1)
	
	$("input[name='storeNumber']").val(1)
	
};


/**
 * 保存菜谱
 * 
 * 修改于2018/03/09  胡子
 */
var saveMenuBook = function(){
	var isValid = $("#add-data-form").form('validate');
	
	if(!isValid){
		return;
	}else{
		if($("#imgShow").attr("src") == null || $("#imgShow").attr("src") == ''){
			parent.$.messager.alert("提示","缩略图不能为空","info"); 
			return;
		}
		var length = $("#procedure").find(".step").length;
		var foodc = "";
		var foodv = "";
		var foody = "";
		//var nullCount = 0;//用于累计空字符出现次数
		$("#procedure").find(".step").each(function(){
			//你先读代码吧  ，我也没那么多时间给你看  整个写的一坨x一样的    给我接手的话  第一件事就是全都重构了  
			//牵一发动全身的代码  。用一万个parent去定位  写这个代码的 怕是 初中毕业。  这代码我改的好艰难啊  重构又不会
			//那你看吧  现在图省事走捷径  后面要改动还是要炸  。比如说这个procedure里的东西要变成多个procedure的话  这所有的代码就要重写了 。
			//你忙去吧  我自己搞 goodluck~
				var count = $(this).parent().parent().parent().parent().parent().attr("id");
				//var i = count.substring(count.length-1,count.length);
				var i = count.substring(count.indexOf("div")+3,count.length);
				var minute = $("#div"+i).find("input[name='minute"+i+"']").val();
				var second = $("#div"+i).find("input[name='second"+i+"']").val();
				if('' == minute){
					if(''==second || !(/^\+?[1-9][0-9]*$/).test(second)){
						$("#div"+i).find("#spsecond").text("必填且不能以0开头");
					}else{
						$("#div"+i).find("#spsecond").text("");
					}
				}else{
					$("#div"+i).find("#spsecond").text("");
				}
				
					//huzi
					$("#div"+i).find("input[name='div"+i+"storeId']").each(function(){ 
						console.log();
						if($(this).val() != null && $(this).val() != "" && $(this).val() != undefined){
							foodc +=$(this).val()+",";
						}
					});
					$("#div"+i).find("input[name='div"+i+"name']").each(function(){
						if($(this).val() != null && $(this).val() != "" && $(this).val() != undefined){
							foodv +=$(this).val()+",";
						}
					});
					$("#div"+i).find("input[name='div"+i+"yv']").each(function(){
						if($(this).val() != null && $(this).val() != "" && $(this).val() != undefined){
							foody +=$(this).val()+",";
						}
					});
				
					//huzi
					if($("#div"+i).find("input[name='div"+i+"storeId']").length != 0){	
						foodc = foodc.substring(0,foodc.length-1)+";"
					}
					else {
						foodc = foodc+" ;" ;
					}
					
					if($("#div"+i).find("input[name='div"+i+"name']").length != 0 ){
						foodv = foodv.substring(0,foodv.length-1)+";"
					}
					else {
						foodv = foodv+" ;" ;
					}
					
					if($("#div"+i).find("input[name='div"+i+"yv']").length != 0){
						foody = foody.substring(0,foody.length-1)+";";
					}
					else {
						foody = foody+" ;" ;
						//++nullCount;
					}
					
				//foodc = $("#div"+i).find("input[name='div"+i+"storeId']").length == 0 ? foodc+" ;" : foodc.substring(0,foodc.length-1)+";";
				//foodv = $("#div"+i).find("input[name='div"+i+"name']").length == 0 ? foodv+" ;" : foodv.substring(0,foodv.length-1)+";";
				//foody = $("#div"+i).find("input[name='div"+i+"yv']").length == 0 ? foody+" ;" : foody.substring(0,foody.length-1)+";";
					
		});
		var addNumber=$("input[name='addNumber']").val(length);
		$("#procedure").find(".step").each(function(i){
			i+=1;
			var count = $(this).parent().parent().parent().parent().parent().attr("id");
//			var id = count.substring(count.length-1,count.length);
			var id = count.substring(count.indexOf("div")+3,count.length);
			$("#div"+id).find("#minute"+id).attr("name","minute"+i);
			$("#div"+id).find("#second"+id).attr("name","second"+i);
			$("#div"+id).find("#describe"+id).attr("name","describe"+i);
		});
	}
	
	var url = dataOptions.baseUrl+'saveMenuBook.htm?foodc='+foodc+"&foodv="+foodv+"&foody=" + foody; //配置保存数据地址       //+"&nullCount="+nullCount
	var check = function(){return true};
	 //请求成功后回调函数success  data为请求服务器返回的参数
	var success = function(ret){
		if(ret && ret.status == 10001){
			$('#add-dialog').dialog('close');
			
			mygrid.datagrid('reload');
			
			$('#add-data-form').form('clear');
			
			
			$("#imgShow").removeAttr("src"); 
			
			$.messager.show({
				title:'提示',
				msg:'保存成功',
				timeout:5000,
				showType:'slide'
			});

		}else {
			parent.$.messager.alert("错误",ret.msg,"error");
		}
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	formSubmit($('#add-data-form'),url,check,success);
	
};



$(function () {
    $('#add-dialog').window({
    	onClose: function () { //当面板关闭之前触发的事件
    		$("#div_c1").nextAll().remove();
    		$('#add-data-form').form('clear');
    		
    		$("#div1").nextAll().remove();
    		
        }
    });
    
    
    $('#import-dialog').window({
    	onClose: function () { //当面板关闭之前触发的事件
    		$("#div_import_c1").nextAll().remove();
    		
    		$('#import--data-form').form('clear');
        }
    });
});

//取消
var cancle = function(){
	$("#div_c1").nextAll().remove();
	
	$('#add-data-form').form('clear');
	
	$('#add-dialog').dialog('close');
	
	$("#div1").nextAll().remove();
	
};




//修改
var gridEdit = function(rowIndex){
	mygrid.datagrid('selectRow', rowIndex); 
	
	var rowData = mygrid.datagrid('getSelected');// 取到选择的数据
	
	var url = dataOptions.baseUrl + 'data.htm';/* 配置查询要修改的数据查询地址 */
	
	var data = {id:rowData.id}; /* 请求的参数内容 */
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	var success = function(ret){
		if(ret && ret.status == 10001){
			
			$('#add-data-form').form('clear');// 清除form表单数据
			
			new uploadPreview({ UpBtn: "up_img", DivShow: "imgdiv", ImgShow: "imgShow" });
			
			$("#imgShow").attr("src",dataOptions.basePath+"/"+ret.data.data.logoPath);
			
			
			// 初始化
			$('#add-data-form').form('myLoad',ret.data.data);
			
			
			var clist = ret.data.data.list;
			
			
			if(clist.length>0){
				
				$('#firstId1').combobox('setValue',clist[0].firstId);
				
				$('#secondId1').combobox('setValue',clist[0].secondId);
				
				$("input[name='categoryNumber']").val(clist.length);
			}else{
				$("input[name='categoryNumber']").val(1);
			}
			
			
			
			for(var i=1;i<clist.length;i++){

				var value = i;
				
				value++;
				
				var html = '<div id="div_c'+value+'"><table><tr><td>一级：</td><td><input type="text" name="firstId'+value+'" id="firstId'+value+'" style="width: 150px" class="easyui-combobox" data-options="required:true"/></td>';
		   		html += '<td>&nbsp;&nbsp;&nbsp;二级：</td><td><input type="text" name="secondId'+value+'" id="secondId'+value+'" style="width: 150px" class="easyui-combobox" data-options="required:true"/></td></tr></table></div>';
		   		$("#menuCategory").append(html);
		   		
		   		$('#secondId'+value).combobox({
		   	        valueField:'id', //值字段
		   	        textField:'name', //显示的字段
		   	        url:dataOptions.basePath+'/menuCategory/getSecondCombox.htm?firstId='+clist[i].firstId,
		   	        panelHeight:300,
		   	        editable:true
		   	    });
		   		firstIdcombobox(value);
				
				$("#firstId"+value).combobox('setValue',clist[i].firstId);
				
				$('#secondId'+value).combobox('setValue',clist[i].secondId);
				
			}
			
			
			
			//步骤赋值
			var plist = ret.data.data.procedures;
			
			if(plist.length <1){
				$("input[name='addNumber']").val(1);
			}else{
				$("input[name='addNumber']").val(plist.length);
				
				$("#procedure").find("#div1").remove();
				for(var i = 0;i < plist.length; i++){
					var value = i;
					value++;
					var foods = plist[i].foods.split(";");
					var minute = '';
					if(plist[i].minute != 0){
						minute = plist[i].minute;
					}
					var html = '<div id="div'+value+'"><table><tr><td><span class="step">'+value+'</span>、时间：</td><td><input type="text" value="'+minute+'" name="minute'+value+'" data-options="validType:[\'time\']" ';
					html += 'id="minute'+value+'" class="easyui-validatebox" style="width: 40px;"/>分钟&nbsp;&nbsp;<input  type="text" value="'+plist[i].second+'" id="second'+value+'" ';
					html += 'name="second'+value+'" data-options="validType:[\'second\']" class="easyui-validatebox" style="width: 40px;"/>秒&nbsp;<span id="spsecond" style="color: red"></span></td></tr><tr>';
					html += '<td>步骤描述：</td><td><input type="text" value="'+plist[i].describes+'" name="describe'+value+'" id="describe'+value+'" style="width: 400px"/></td></tr>';
					html += '<tr class="mf-line"><td style="width:80px;background-color: #eee;">食材分类</td><td class="mf-right"><input type="hidden" name="foodCategoryNumber" value="'+(foods.length == 0 ? 0 : foods.length-1 ) +'"/>';
					html += '<input type="hidden" name ="storeNumber" value="'+value+'"/><div id="foodCategory">';
					var html2 ="";
					for(var j = 0;j <foods.length-1;j++){
						var foodv = foods[j].split("|");
						var required = "true";
						if(foodv[0]==0){required="false";}
						var index = j+1;
							html2 += '<div id="div_f'+index+'"><table><tr><td>一级:</td>';
							html2 += '<td><input type="text" name="foodId'+index+'" id="foodId'+index+'" style="width: 80px" class="easyui-combobox" data-options="required:'+required+'"/></td><td>&nbsp;二级:</td>';
							html2 += '<td><input type="text" name="secondFoodId" id="secondFoodId'+index+'" style="width: 80px" class="easyui-combobox" data-options="required:'+required+'"/></td>';
							html2 += '<td>&nbsp;食材:</td><td><input type="text" name="div'+value+'storeId" id="storeFoodId'+index+'" style="width: 80px" class="easyui-combobox" data-options="required:true"/></td>';
							html2 += '<td>&nbsp;重量:</td><td><input type="text" name="div'+value+'name" id="name'+index+'" value="'+foodv[2]+'" style="width: 50px" data-options="required:true,validType:[\'weight\']" class="easyui-validatebox"/></td>'
							html2 += '<td>&nbsp;预处理:</td><td><input type="text" name="div'+value+'yv" id="yv'+index+'" value="'+foodv[3]+'" style="width: 50px" data-options="required:true,validType:[\'yvDispose\']" class="easyui-validatebox"/></td>';//huzi
							html2 += '</tr></table></div>';
					}
					html += html2;
					html += '</div><div style="margin-left: 30px;"><input type="button" value="添加食材" onclick="addFoodCategory(this)"/>&nbsp;&nbsp;&nbsp;<input type="button" value="删除食材" onclick="delFoodCategory(this)"/>';
					html += ' <input type="button" value="添加步骤" onclick="addProcedure(this)" style="margin-left: 150px;"/>&nbsp;&nbsp;';
					html += ' <input type="button" value="删除步骤" onclick="delProcedure(this)"/></div></td></tr></table><br /></div>';	
			   		$("#procedure").append(html);
			   		

				}
			}	
			
			for(var i = 0;i < plist.length; i++){
				var value = i+1;
				var foods = plist[i].foods.split(";");
				for(var j = 0;j <foods.length-1;j++){
		   			var count = j+1;
			   			forcombobox(value,count);
			   			var foodv = foods[j].split("|");
			   			if(foodv[0] != 0){
			   				$.ajax({
								   type: "POST",
								   url: dataOptions.baseUrl+'getFoodStorCombox.htm',
								   data: "id="+foodv[0],
								   dataType: 'json',
								   async: false,
								   success: function(data){
									   $('#div'+value).find('#div_f'+count).find('#foodId'+count).combobox('setValue',data.one);
							   			$('#div'+value).find('#div_f'+count).find('#secondFoodId'+count).combobox({
							   				url:dataOptions.basePath+'/foodcategory/getSecondCombox.htm?firstId='+data.one,
							   				valueField:'id', //值字段
							   				textField:'name', //显示的字段
							   				panelHeight:300,
							   				editable:true
							   			}); 
							   			$('#div'+value).find('#div_f'+count).find('#secondFoodId'+count).combobox('setValue',data.two);
							   			
							   			$('#div'+value).find('#div_f'+count).find('#storeFoodId'+count).combobox({
							   				valueField:'id', //值字段
							   				textField:'name', //显示的字段
							   				url:dataOptions.basePath+'/foodstore/getSecondCombox.htm?foodId='+data.two,
							   				panelHeight:300,
							   				editable:true
							   			});
							   			$('#div'+value).find('#div_f'+count).find('#storeFoodId'+count).combobox('setValue',foodv[0]);
								   }
							});
			   			}else{
			   				$('#div'+value).find('#div_f'+count).find('#secondFoodId'+count).combobox();
			   				$('#div'+value).find('#div_f'+count).find('#name'+count).val(foodv[2])
			   				$('#div'+value).find('#div_f'+count).find('#storeFoodId'+count).combobox({
				   				valueField:'id', //值字段
				   				textField:'name', //显示的字段
				   				panelHeight:300,
				   				editable:true,
				   				data: [{    
				   				    "id":0,    
				   				    "name":foodv[1]   
				   				}] 
				   			});
			   				$('#div'+value).find('#div_f'+count).find('#storeFoodId'+count).combobox('setValue',0);
			   			}
			   			
		   		}
				$('#div'+value).find('input[name=\'minute'+value+'\']').validatebox();
				$('#div'+value).find('input[name=\'second'+value+'\']').validatebox();
			}
			
	   		
			$('#add-dialog').dialog('setTitle','修改');
			
			$('#add-dialog').dialog('open');
			$("#procedure").html();
			
		}else parent.$.messager.alert("错误",ret.msg,"error");
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	// ajav异步查询数据
	asynPAjaxMin(url,data,success,error);	
};


var firstIdcombobox = function(value){
	$("#firstId"+value).combobox({
	    url:dataOptions.basePath+'/menuCategory/getFirstCombox.htm',
	    valueField:'id',
	    textField:'name',
	    panelHeight:300,
        editable:true,//不可编辑，只能选择
        onChange:function(firstId){
        	if(firstId && firstId !='' && firstId !='undefined'){
       			 $('#secondId'+value).combobox({
	                valueField:'id', //值字段
	                textField:'name', //显示的字段
	                url:dataOptions.basePath+'/menuCategory/getSecondCombox.htm?firstId='+firstId,
	                panelHeight:300,
	                editable:true
	            });
       		}
               
        }
	});
	
}

var forcombobox = function(value,count){
	$('#div'+value).find('#div_f'+count).find('#foodId'+count).combobox({
	    url:dataOptions.basePath+'/foodcategory/getFirstCombox.htm',
	    valueField:'id',
	    textField:'name',
	    panelHeight:300,
        editable:true,//不可编辑，只能选择
        onChange:function(firstId){
        	if(firstId && firstId !='' && firstId !='undefined'){
        		$('#div'+value).find('#div_f'+count).find('#secondFoodId'+count).combobox({
	                valueField:'id', //值字段
	                textField:'name', //显示的字段
	                url:dataOptions.basePath+'/foodcategory/getSecondCombox.htm?firstId='+firstId,
	                panelHeight:300,
	                editable:true,
	                 onChange:function(foodId){
		            		if(foodId && foodId !='' && foodId !='undefined'){
		            			$('#div'+value).find('#div_f'+count).find('#storeFoodId'+count).combobox({
					                valueField:'id', //值字段
					                textField:'name', //显示的字段
					                url:dataOptions.basePath+'/foodstore/getSecondCombox.htm?foodId='+foodId,
					                panelHeight:300,
					                editable:true
				                });
		            		}
           				}
	            });
       		}
               
        }
	});
	$('#div'+value).find('#div_f'+count).find('#name'+count).validatebox();
}

var gridAllDel = function(rowIndex){
	var rowData = getData(rowIndex);// 获取点击行的数据 一般不需要修改
	
	if(rowData.length == 0){
		parent.$.messager.alert("提示","请至少选择一条数据","info");
		return;
	}
	var array='';
	for(var i = 0;i<rowData.length;i++){
		array = rowData[i].id+","+array;
	}
	
	var delUrl = dataOptions.baseUrl + 'delMenuBook.htm';/* 配置删除的地址 */
	
	var postData = {'ids': array};/* 请求到服务器的数据内容 */
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	
	var success = function(ret){
		if(ret && ret.status == 10001){
			mygrid.datagrid('reload');
			$('#import-data-form').form('clear');
			$.messager.show({
				title:'提示',
				msg: "删除成功",
				cout:8000,
				showType:'slide'
			});
		}else parent.$.messager.alert("错误",ret.msg,"error");
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	
	// 调用此方法进行删除 
	parent.$.messager.confirm('确认','确认要删除所选数据?',function(r){  
       if (r) asynPAjaxMin(delUrl,postData,success,error);
    });
};

////////////////////////////导入/////////////////////////////////////////////////////

var importCancle = function(){
	$("#div_import_c1").nextAll().remove();
	
	$('#import-data-form').form('clear');
	
	$('#import-dialog').dialog('close');

}


var gridImport = function(){
	
	
	$('#import-data-form').form('clear');// 清除form表单数据
	$( "#import-dialog" ).dialog("open").dialog('setTitle','导入');
	
	$("input[name='uid']").val(-1)
	$("#fileSpan").text('');
	$("input[name='importCategoryNumber']").val(1)
};


var importAddCategory = function(){
	var value = $("input[name='importCategoryNumber']").val(); 
		
	value++;
		
	var html = '<div id="div_import_c'+value+'"><table><tr><td>一级：</td><td><input type="text" name="import_firstId'+value+'" id="import_firstId'+value+'" style="width: 150px" class="easyui-combobox" data-options="required:true"/></td>';
		html += '<td>&nbsp;&nbsp;&nbsp;二级：</td><td><input type="text" name="import_secondId'+value+'" id="import_secondId'+value+'" style="width: 150px" class="easyui-combobox" data-options="required:true"/></td></tr></table></div>';
		$("#importCategory").append(html);
		
		
		$("#import_firstId"+value).combobox({
	    url:dataOptions.basePath+'/menuCategory/getFirstCombox.htm',
	    valueField:'id',
	    textField:'name',
	    panelHeight:300,
        editable:true,//不可编辑，只能选择
        onChange:function(firstId){
        	if(firstId && firstId !='' && firstId !='undefined'){
       			 $('#import_secondId'+value).combobox({
	                valueField:'id', //值字段
	                textField:'name', //显示的字段
	                url:dataOptions.basePath+'/menuCategory/getSecondCombox.htm?firstId='+firstId,
	                panelHeight:300,
	                editable:true
	            });
       		}
               
        }
	});
	
	
	$('#import_secondId'+value).combobox();
	
	$("input[name='importCategoryNumber']").val(value);
};


var importDelCategory = function(){
	var value = $("input[name='importCategoryNumber']").val();
	
	if(value > 1){
		$("#div_import_c"+value).remove();
		value--;
	}
	
	$("input[name='importCategoryNumber']").val(value);
};




//限制上传文件大小
function fileChange(target){
    var name=target.value;
    
    var fileName = name.substring(name.lastIndexOf(".")+1).toLowerCase();
    if( fileName !="pms"){
    	$("#fileSpan").text("文件格式错误,请选择pms后缀名文件");
        target.value="";
        return
    }else{
    	$("#fileSpan").text('');
    }
     
}


var saveImport = function(){
	
	var name = $("#fileText").val();
	var url = dataOptions.basePath+'/upload/pmsFileUploadByByte.aspf?type=1&userId=-1&fileName=';/* 配置保存数据地址 */
	url += name.substring(name.lastIndexOf("\\")+1,name.lastIndexOf("."));
	var isValid = $("#import-data-form").form('validate');
	if(!isValid){return;}
	var check = function(){// 验证
		
			parent.$.messager.progress('close');	// 隐藏进度条
	    	
	    	var fileName = name.substring(name.lastIndexOf(".")+1).toLowerCase();
	        if(fileName !="pms"){
	        	$("#fileSpan").text("文件格式错误,请选择pms后缀名文件");
	            target.value="";
	            isValid = false;
	        }else{
	        	$("#fileSpan").text('');
	        }
		return isValid;
	};
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	var success = function(ret){
		if(ret && ret.status == 10001){
			var url = dataOptions.baseUrl+'upFilePath.htm?id='+ret.data.id+'&filePath='+ret.data.path;
			var success = function(ret){
				if(ret && ret.status == 10001){
					$('#import-dialog').dialog('close');
					mygrid.datagrid('reload');
					$('#import-data-form').form('clear');
					$.messager.show({
						title:'提示',
						msg:'导入成功',
						timeout:5000,
						showType:'slide'
					});
				}else{
					parent.$.messager.alert("错误",baseData.msg,"error");
				}
			}		
			var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
			// ajav异步查询数据
			//asynPAjaxMin(dataOptions.baseUrl+'upFilePath.htm',data,success,error);
			formSubmit($('#import-data-form'),url,check,success);
		}else {
			parent.$.messager.alert("错误",ret.msg,"error");
		}
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	formSubmit($('#import-data-form'),url,check,success);
	
};



var recommend = function(rowIndex){
	
	mygrid.datagrid('selectRow', rowIndex); 
	
	var rowData = mygrid.datagrid('getSelected');// 取到选择的数据
	
	var url = dataOptions.baseUrl + 'recommendMenuBook.htm';/* 配置查询要修改的数据查询地址 */
	
	var data = {id:rowData.id,recommend:rowData.recommend}; /* 请求的参数内容 */
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	var success = function(ret){
		if(ret && ret.status == 10001){
			mygrid.datagrid('reload');
		}else parent.$.messager.alert("错误",ret.msg,"error");
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	// ajav异步查询数据
	asynPAjaxMin(url,data,success,error);
};


var setAdvert = function(rowIndex){
	mygrid.datagrid('selectRow', rowIndex); 
	
	var rowData = mygrid.datagrid('getSelected');// 取到选择的数据
	
	var url = dataOptions.baseUrl + 'setAdvert.htm';/* 配置查询要修改的数据查询地址 */
	
	var data = {id:rowData.id,isAdvert:rowData.isAdvert}; /* 请求的参数内容 */
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	var success = function(ret){
		if(ret && ret.status == 10001){
			mygrid.datagrid('reload');
		}else parent.$.messager.alert("错误",ret.msg,"error");
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	// ajav异步查询数据
	asynPAjaxMin(url,data,success,error);
};


/******************************************************************** 批量导出	2018-04-04  huzi *************************************************************************************/

var exportMenuBook = function(rowIndex){
	var rowData = getData(rowIndex);// 获取点击行的数据 一般不需要修改
	if(rowData.length == 0){
		parent.$.messager.alert("提示","请至少选择一个菜谱","info");
		return;
	}
	var array='';
	for(var i = 0;i<rowData.length;i++){
		array = rowData[i].id+","+array;
	}
	
	var exportUrl = dataOptions.baseUrl + 'exportMenuBook.htm';/* 配置批量导出的地址 */
	
//	var postData = {'ids': array};/* 请求到服务器的数据内容 */
	window.open(exportUrl+"?ids="+array,"yizhicaiji");
	return;
};	
	

















































