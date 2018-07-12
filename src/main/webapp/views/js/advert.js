
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

/**
 * 弹出添加窗口
 */
function gridAdd(){
	$('#add-data-form').form('clear');// 清除form表单数据
	$( "#add-dialog" ).dialog("open").dialog('setTitle','添加');
	$("#statusYes").prop("checked",true);
	
	$('#menuId').combobox({
        valueField:'id', //值字段
        textField:'name', //显示的字段
        url:dataOptions.baseUrl+'getMenuBookComboxByName.htm?isAdvert=0',
        panelHeight:300,
        editable:true,
        loadFilter:function(data){
        	return data;
        }
    });
}

var saveAdvert = function(){
	var url = dataOptions.baseUrl+'saveAdvert.htm';/* 配置保存数据地址 */
	
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
			
			$('#menuId').combobox({
                valueField:'id', //值字段
                textField:'name', //显示的字段
                url:dataOptions.baseUrl+'getMenuBookComboxByName.htm?isAdvert=0',
                panelHeight:300,
                editable:true,
                loadFilter:function(data){
	        		if(data){
		        		data.push({'id':ret.data.data.id,'name':ret.data.data.id+ret.data.data.name});
		        	}
		        	
		        	return data;
		        }
            });
			
			$('#menuId').combobox('setValue',ret.data.data.id);
			
			$('#add-dialog').dialog('setTitle','修改');
			$('#add-dialog').dialog('open');
		}else parent.$.messager.alert("错误",ret.msg,"error");
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	// ajav异步查询数据
	asynPAjaxMin(url,data,success,error);	
};


var gridAllDel = function(rowIndex){
	
	var rowData = getData(rowIndex);// 获取点击行的数据 一般不需要修改
	if(rowData.length == 0){
		parent.$.messager.alert("提示","请至少选择一行数据","info");
		return;
	}
	
	var array='';
	
	for(var i = 0;i<rowData.length;i++){
		array = rowData[i].id+","+array;
	}
	
	var delUrl = dataOptions.baseUrl + 'delAdvert.htm';/* 配置删除的地址 */
	
	var postData = {'ids': array};/* 请求到服务器的数据内容 */
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	
	var success = function(ret){
		if(ret && ret.status == 10001){
			mygrid.datagrid('reload');
		}else parent.$.messager.alert("错误",ret.msg,"error");
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	
	// 调用此方法进行删除 
	parent.$.messager.confirm('确认','确认要删除所选数据?',function(r){  
       if (r) asynPAjaxMin(delUrl,postData,success,error);
    });
};


