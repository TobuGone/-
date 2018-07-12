var dataOptions = {
	baseUrl:"/systemuser/",
	basePath:"",
	addInit:function(){},// 添加弹窗初始化
	editInit:function(data){},// 修改数据加载完成初始化
	saveInit:function(data){}// 保存前执行操作
};

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

//添加
function gridAdd(){
	
	$('#add-data-form').form('clear');// 清除form表单数据

	$( "#add-dialog" ).dialog("open").dialog('setTitle','添加');
	$("#statusYes").prop("checked",true);
	
	$('#tr_pwd').show();
	
	$("#pwd").validatebox({
	    required: true
	});
		
};


//新增     2018-01-29 huzi
var saveUser = function(){
	//提交表单就是在这里提交的  你们用的js框架是easyui 表单都是通过js提交的  然后 
	//熟悉熟悉人家怎么提交的就好  一个jsp对应一个js 不难
	//这算什么复杂  要不你可以自己简化写  大致就是 easyui 做了按钮出来 ，然后按钮有对应事件 所以才会调用到这里  
	$('#add-data-form').form("submit",{  
		   url:dataOptions.baseUrl+'saveSystemUser.htm',
		   onSubmit:function(param){
			   var isValid = $(this).form('validate');
			   if(!isValid){
				    $.messager.progress('close');	// 如果表单是无效的则隐藏进度条
			    }
				return isValid;
		   },
	        success:function(response){  
	        	var data = $.parseJSON(response);
				if(data.status == "10001"){
					$.messager.show({
						title:'保存成功',
						msg:'消息将在5秒后关闭。',
						timeout:5000,
						showType:'slide'
					});

					$('#add-dialog').dialog('close');
					mygrid.datagrid('reload');
					
					$('#add-data-form').form('clear');// 清除form表单数据
					
					$.messager.progress('close');
				}else{
					parent.$.messager.alert("提示",data.msg,"error") ;
					$.messager.progress('close');
				}
	        }
	  });
	var check = function(){// 验证
		
		 	var isValid = $(this).form('validate');
		 	
		 	if(!isValid){
		 		parent.$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
		 	}
			
		   return isValid;
	};
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	var success = function(ret){
		
		if(ret && ret.status == 10001){
			
			$('#add-dialog').dialog('close');
			mygrid.datagrid('reload');
			$('#add-data-form').form('clear');
			
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

//删除
var gridDel = function(rowIndex){
	var rowData = getData(rowIndex);// 获取点击行的数据 一般不需要修改
	if(rowData.length == 0){
		parent.$.messager.alert("提示","请至少选择一行数据","info") ;
		return;
	}
	var array='';
	for(var i = 0;i<rowData.length;i++){
		array = rowData[i].id+","+array;
	}
	
	var delUrl = dataOptions.baseUrl + 'delSystemUser.htm';/* 配置删除的地址 */
	
	var postData = {'ids': array};/* 请求到服务器的数据内容 */
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	
	var success = function(ret){
		if(ret && ret.status == 10001){
			mygrid.datagrid('reload');
		}else parent.$.messager.alert("错误",ret.msg,"error");
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	
	// 调用此方法进行删除 
	parent.$.messager.confirm('确认','确认要永久删除所选数据?',function(r){  
       if (r) asynPAjaxMin(delUrl,postData,success,error);
    });
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
			
			// 初始化
			$('#add-data-form').form('myLoad',ret.data.data);
			$('#add-dialog').dialog('setTitle','修改');
			$('#add-dialog').dialog('open');
		}else parent.$.messager.alert("错误",ret.msg,"error");
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	// ajav异步查询数据
	asynPAjaxMin(url,data,success,error);	
};



//修改
function editUser(){

	$('#add-data-form').form("submit",{  
		   url:dataOptions.baseUrl+'editUser.htm',
		   onSubmit:function(param){
			   var isValid = $(this).form('validate');
			   if(!isValid){
				   parent.$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
			    }
				return isValid;
		   },
	        success:function(response){  
	        	var data = $.parseJSON(response);
				if(data.status == "10001"){
					$.messager.show({
						title:'保存成功',
						msg:'消息将在5秒后关闭。',
						timeout:5000,
						showType:'slide'
					});

					$('#add-dialog').dialog('close');
					mygrid.datagrid('reload');
					
					$('#add-data-form').form('clear');// 清除form表单数据
				}else{
					parent.$.messager.alert("提示",data.msg,"error") ;
				}
	        }
	  });
};


//修改密码弹窗
var gridUpdatePWD = function(rowIndex){
	mygrid.datagrid('selectRow', rowIndex); 
	
	var rowData = mygrid.datagrid('getSelected');// 取到选择的数据
	
	$('#update-pwd-form').form('clear');// 清除form表单数据

	$( "#update-pwd-dialog" ).dialog("open").dialog('setTitle','重置密码');
	
	$("#updateId").val(rowData.id);
};



//重置密码
var updatePwd = function(){
	
	$('#update-pwd-form').form("submit",{  
		   url:dataOptions.baseUrl+'updatePwd.htm',
		   onSubmit:function(param){
			   var isValid = $(this).form('validate');
			   
				
				if($("input[name='newPwd']").val() != $("input[name='confirmPwd']").val()){
					parent.$.messager.alert("提示","新密码与确认密码不一致","error");
					isValid = false;
				}
				
				
				if(!isValid){
				    $.messager.progress('close');	// 如果表单是无效的则隐藏进度条
			    }
				return isValid;
		   },
	        success:function(response){  
	        	var data = $.parseJSON(response);
				if(data.status == "10001"){
					$.messager.show({
						title:'重置密码成功',
						msg:'消息将在5秒后关闭。',
						timeout:5000,
						showType:'slide'
					});

					$('#update-pwd-dialog').dialog('close');
					mygrid.datagrid('reload');
					
					$('#update-pwd-form').form('clear');// 清除form表单数据
				}else{
					parent.$.messager.alert("提示",data.msg,"error");
				}
	        }
	  });
};



//暂停用户
var stopUse = function(rowIndex){
	var rowData = getData(rowIndex);// 获取点击行的数据 一般不需要修改
	if(rowData.length == 0){
		parent.$.messager.alert("提示","请至少选择一行数据","info") ;
		return;
	}
	var array='';
	for(var i = 0;i<rowData.length;i++){
		array = rowData[i].id+","+array;
	}
	
	var delUrl = dataOptions.baseUrl + 'changeStemUserStatus.htm';/* 配置删除的地址 */
	
	var postData = {'ids': array,'status':0};/* 请求到服务器的数据内容 */
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	
	var success = function(ret){
		if(ret && ret.status == 10001){
			mygrid.datagrid('reload');
		}else parent.$.messager.alert("错误",ret.msg,"error");
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	
	// 调用此方法进行删除 
	parent.$.messager.confirm('确认','确认要暂停所选用户吗?',function(r){  
       if (r) asynPAjaxMin(delUrl,postData,success,error);
    });
};

//使用用户
var startUse = function(rowIndex){
	var rowData = getData(rowIndex);// 获取点击行的数据 一般不需要修改
	if(rowData.length == 0){
		parent.$.messager.alert("提示","请至少选择一行数据","info") ;
		return;
	}
	var array='';
	for(var i = 0;i<rowData.length;i++){
		array = rowData[i].id+","+array;
	}
	
	var delUrl = dataOptions.baseUrl + 'changeStemUserStatus.htm';/* 配置删除的地址 */
	
	var postData = {'ids': array,'status':1};/* 请求到服务器的数据内容 */
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	
	var success = function(ret){
		if(ret && ret.status == 10001){
			mygrid.datagrid('reload');
		}else parent.$.messager.alert("错误",ret.msg,"error");
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	
	// 调用此方法进行删除 
	parent.$.messager.confirm('确认','确认要开启使用所选用户吗?',function(r){  
       if (r) asynPAjaxMin(delUrl,postData,success,error);
    });
};

