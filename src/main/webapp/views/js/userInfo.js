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
 * 查询
 */
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
 * 查看
 */
var gridSearchDeatil = function(rowIndex ){
	mygrid.datagrid('selectRow', rowIndex); 
	
	var rowData = mygrid.datagrid('getSelected');// 取到选择的数据
	
	var url = dataOptions.baseUrl + 'data.htm';/* 配置查询要修改的数据查询地址 */
	
	var data = {id:rowData.id}; /* 请求的参数内容 */
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	var success = function(ret){
		if(ret && ret.status == '10001'){
			$('#search-form').form('clear');// 清除form表单数据
			
			var datas = ret.data.data;
			
			for(var key in datas){
				
				$("#"+key+"").text(datas[key]+"");
			}
			
			$( "#search-dialog" ).dialog("open").dialog('setTitle','查看');
			
		}else parent.$.messager.alert("错误",ret.msg,"error");
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	// ajav异步查询数据
	asynPAjaxMin(url,data,success,error);
		
};


/******************************************************************** 批量导出	2018-04-04  huzi *************************************************************************************/
 
var exportExcel = function(rowIndex){
	var rowData = getData(rowIndex);// 获取点击行的数据 一般不需要修改
	
	if(rowData.length == 0){
		parent.$.messager.alert("提示","请至少选择一条数据","info");
		return;
	}
	var array='';
	for(var i = 0;i<rowData.length;i++){
		array = rowData[i].id+","+array;
	}
	
	var exportExcelUrl = dataOptions.baseUrl + 'exportExcel.htm';/* 配置批量导出的地址 */
	
	var postData = {'ids': array};/* 请求到服务器的数据内容 */
	window.open(exportExcelUrl+"?ids="+array,"yizhicaiji");
	return;
};	
	
	
/*******************************************************************  批量导入 2018-04-04  huzi ***************************************************************************************/

var importCancle = function(){
		$("#div_import_c1").nextAll().remove();
		
		$('#import-data-form').form('clear');
		
		$('#import-dialog').dialog('close');
};	
	


var importExcel = function(){
		$('#import-data-form').form('clear');// 清除form表单数据
		$( "#import-dialog" ).dialog("open").dialog('setTitle','用户数据导入');
		$("#fileSpan").text('');
		
};



//限制上传文件格式
function fileChange(target){
    var name=target.value;
    
    var fileName = name.substring(name.lastIndexOf(".")+1).toLowerCase();
    if( fileName !="xls"){
    	$("#fileSpan").text("文件格式错误,请选择xls后缀名文件");
        target.value="";
        return;
    }else{
    	$("#fileSpan").text('');
    }
     
}



var saveImport = function(){
	
	var name = $("#fileText").val();
	var importExcelUrl = dataOptions.baseUrl + 'importExcel.htm';/* 配置批量导入的地址 */
	var isValid = $("#import-data-form").form('validate');
	if(!isValid){return;}
	
	var check = function(){//验证
			parent.$.messager.progress('close');//隐藏进度条
	    	
	    	var fileName = name.substring(name.lastIndexOf(".")+1).toLowerCase();
	        if(fileName !="xls"){
	        	$("#fileSpan").text("文件格式错误,请选择xls后缀名文件");
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
			$('#import-dialog').dialog('close');
			mygrid.datagrid('reload');
			$('#import-data-form').form('clear');
			$.messager.show({
				title:'提示',
				msg: ret.msg,
				cout:8000,
				showType:'slide'
			});
		}else if(ret && ret.status == 10007){
			parent.$.messager.alert("导入失败",ret.msg);
		}
		else{
			parent.$.messager.alert("错误",ret.msg,"error");
		}
	}			

	var error = null; //请求服务器失败回调函数error 传递null使用默认error处理
	formSubmit($('#import-data-form'),importExcelUrl,check,success);
	
};


/*******************************************************************  批量删除 2018-04-23  huzi ***************************************************************************************/


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
	
	var delUrl = dataOptions.baseUrl + 'delUsers.htm';/* 配置删除的地址 */
	
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


/*******************************************************************  批量禁用或使用用户 2018-04-23  huzi ***************************************************************************************/


function upStatus(statusName){
	var formDate = $("#mygrid");
	var sele = formDate.datagrid("getSelections");
	if(sele.length==0){
		$.messager.alert("确认","至少选择一条数据?")
		return 
	}
	var type = "0";
	if(statusName=="使用"){
		type = "1";
	}
	if(statusName=="禁用"){
		type = "2";
	}
	var ids = [];
	var names = [];
	for(var i in sele){
		ids.push(sele[i].id);
		names.push(sele[i].nickName);
	}
	ids = ids.join(",");
	$.messager.confirm('确认','确定'+statusName+'昵称为[ '+names+' ]的用户吗？',function(r){
		if(r){
			var data = {"ids":ids,"type":type};
			var url = dataOptions.baseUrl+'upUserStatus.htm';
			var success = function(ret){
				if(ret && ret.status == 10001){
					formDate.datagrid('reload');
					$.messager.show({
						title:'提示',
						msg:statusName+'成功',
						timeout:8000,
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












