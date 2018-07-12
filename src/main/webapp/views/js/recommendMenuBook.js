//是否推荐
var recommendformatter  = function(value,row,index){
	if(row.recommend == '0'){
		return '<a id="" href="javascript:void(0)" onClick="recommend('+index+')"> 不推荐 </a>';
	}
	if(row.recommend == '1'){
		return '<a id="" href="javascript:void(0)" onClick="recommend('+index+')"> 推荐 </a>';
	}
};


//是否推荐
var newformatter  = function(value,row,index){
	if(row.isNew == '0'){
		return '<a id="" href="javascript:void(0)" onClick="setNewTime('+index+')"> 否 </a>';
	}
	if(row.isNew == '1'){
		return '<a id="" href="javascript:void(0)" onClick="setNewTime('+index+')"> 是 </a>';
	}
};


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
        url:dataOptions.baseUrl+'getMenuBookComboxByName.htm?recommend=0',
        panelHeight:300,
        editable:true,
        loadFilter:function(data){
        	return data;
        }
    });
}

/**
 * 删除
 */
function gridAllDel(){
	var formDate = $("#mygrid");
	var sele = formDate.datagrid("getSelections");
	if(sele.length==0){
		$.messager.alert("确认","至少选择一条数据?")
		return 
	}
	var ids = [];
	var names = [];
	for(var i in sele){
		ids.push(sele[i].id);
		names.push(sele[i].name);
	}
	ids = ids.join(",");
	$.messager.confirm('确认','确定取消菜谱名为【 '+names+' 】的推荐吗？',function(r){
		if(r){
			var data = {"ids":ids}
			var url = dataOptions.baseUrl+'delMenuBook.htm';
			var success = function(ret){
				if(ret && ret.status == 10001){
					formDate.datagrid('reload');
					$.messager.show({
						title:'提示',
						msg:'取消成功',
						timeout:5000,
						showType:'slide'
					});
				}else parent.$.messager.alert("错误",ret.msg,"error");
			};
			var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
			// ajav异步查询数据
			asynPAjaxMin(url,data,success,error);
		}
	});
};


var saveRecommend = function(){
	var url = dataOptions.baseUrl+'saveRecommend.htm';/* 配置保存数据地址 */
	
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
}



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


var setNewTime = function(rowIndex){
	mygrid.datagrid('selectRow', rowIndex); 
	
	var rowData = mygrid.datagrid('getSelected');// 取到选择的数据
	
	var url = dataOptions.baseUrl + 'setNewTime.htm';/* 配置查询要修改的数据查询地址 */
	
	var data = {id:rowData.id,isNew:rowData.isNew}; /* 请求的参数内容 */
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	var success = function(ret){
		if(ret && ret.status == 10001){
			mygrid.datagrid('reload');
		}else parent.$.messager.alert("错误",ret.msg,"error");
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	// ajav异步查询数据
	asynPAjaxMin(url,data,success,error);
}




