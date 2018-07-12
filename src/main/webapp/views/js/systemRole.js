var dataOptions = {
	baseUrl:"",
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

};


//新增
var saveRole = function(){
	
	$.messager.progress();
	
	$('#add-data-form').form("submit",{  
		   url:dataOptions.baseUrl+'saveRole.htm',
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



//分配权限
var gridAuthority = function(rowIndex){
	
	mygrid.datagrid('selectRow', rowIndex); 
	
	var rowData = mygrid.datagrid('getSelected');// 取到选择的数据
	
	var id = rowData.id;
	
	$("#resource").empty();
	
	$.ajax({
		url:dataOptions.basePath+"/role/loadResourceByUserId.htm",
		
		type:"POST",
		data:{id:id},
		success:function(response){
			$("#menuRoleId").val(id);
				var data = $.parseJSON(response);
				var resources = data.resources;
				var resourceIds = data.resourceIds;
			    //加载资源
			    var treeResource = $("#resource");
			    treeResource.tree({
			    	data:resources,
			    	cascadeCheck:false,
			    	checkbox:true,
			    	onCheck:function(node,checked){
				    		   //默认选中所有父节点 (借助冒泡事件实现)
					    		var parentNode = $("#resource").tree("getParent",node.target);
					    		if(parentNode && checked){
					    			$("#resource").tree("check",parentNode.target);
					    		 }
					    		//取消父节点 、则取消全部选中的子节点
					    		var childrenNode = $("#resource").tree("getChildren",node.target);
					    		if(childrenNode && !checked){
						    		$.each(childrenNode,function(index,node){
						    			$("#resource").tree("uncheck",node.target);
						    		});
					    		};
			    	   }
			    	});
			    //选中资源
			    $.each(resourceIds,function(index,value){
			  	  var node = treeResource.tree('find', value);
			  	  treeResource.tree('check', node.target);
			    });
				 $('#rolelayout').layout('expand','east'); 
			}
		});
};

var insure = function(){
	insertRoleResource() ;
}

//保存权限
var insertRoleResource = function(){
	   var nodes = $("#resource").tree('getChecked');
	   var idsArray = [];
	   $.each(nodes,function(index,obj){
		   obj.id||obj.id!=0?idsArray.push(obj.id):"";
	   });
	   var resourceIds =  idsArray.join(",");
	   var roleId = $("#menuRoleId").val();
		$.ajax({
			url:dataOptions.basePath+"/role/insertRoleResource.htm",
			type:"POST",
			data:{id:roleId,resourceIds:resourceIds},
			success:function(response){
				var data = $.parseJSON(response);
				if(data.status == "success"){
					$('#rolelayout').layout('collapse','east');
					$.messager.show({
						title:'权限分配成功',
						msg:'消息将在5秒后关闭。',
						timeout:5000,
						showType:'slide'
					});
				}else{
					parent.$.messager.alert("提示",data.message,"error") ;
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
	
	var delUrl = dataOptions.baseUrl + 'changeStemRoleStatus.htm';/* 配置删除的地址 */
	
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
	
	var delUrl = dataOptions.baseUrl + 'changeStemRoleStatus.htm';/* 配置删除的地址 */
	
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

