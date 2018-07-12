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
	new uploadPreview({ UpBtn: "up_img", DivShow: "imgdiv", ImgShow: "imgShow" });
	$("#imgShow").attr("src","");
	
	$('#menuId').combobox({
	    valueField:'id', //值字段
	    textField:'name', //显示的字段
	    url:dataOptions.basePath+'/menuBook/getMenuBookComboxByName.htm',
	    panelHeight:300,
	    editable:true,
	    multiple:true,
	    formatter: function (row) {
	        var opts = $(this).combobox('options');
	        return row[opts.textField];
	    }
	});
	
};


var saveFoodStore = function(){
	var url = dataOptions.baseUrl+'saveFoodStore.htm';/* 配置保存数据地址 */
	
	var check = function(){// 验证
		var isValid = $(this).form('validate');
		
		if(isValid){
			if($("#imgShow").attr("src") == null || $("#imgShow").attr("src") == ''){
				
				parent.$.messager.alert("提示","图片不能为空","info"); 
				
				isValid = false;
			}
		}
		
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


//修改
var gridEdit = function(rowIndex){
	mygrid.datagrid('selectRow', rowIndex); 
	
	var rowData = mygrid.datagrid('getSelected');// 取到选择的数据
	
	var url = dataOptions.baseUrl + 'data.htm';/* 配置查询要修改的数据查询地址 */
	
	var data = {id:rowData.id}; /* 请求的参数内容 */
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	var success = function(ret){
		if(ret && ret.status == '10001'){
			
			$('#add-data-form').form('clear');// 清除form表单数据
			
			new uploadPreview({ UpBtn: "up_img", DivShow: "imgdiv", ImgShow: "imgShow" });
			
			$("#imgShow").attr("src",dataOptions.basePath+"/"+ret.data.data.logoPath);
			
			
			$('#secondId').combobox({
                valueField:'id', //值字段
                textField:'name', //显示的字段
                url:dataOptions.basePath+'/foodcategory/getSecondCombox.htm?firstId='+ret.data.data.firstId,
                panelHeight:300,
                editable:true
            });
		            		
			$("#secondId").combobox('setValue',ret.data.data.secondId);
			
			// 初始化
			$('#add-data-form').form('myLoad',ret.data.data);
			
			
			if(ret.data.data.name){
				$('#menuId').combobox({
	                valueField:'id', //值字段
	                textField:'name', //显示的字段
	                url:dataOptions.basePath+'/menuBook/getMenuBookComboxByName.htm',
	                panelHeight:300,
	                editable:true,
	                multiple:true,
	                formatter: function (row) {
	                    var opts = $(this).combobox('options');
	                    return row[opts.textField];
	                }
	            });
				
				if(ret.data.data.menuId != '' && ret.data.data.menuId != null){
					$('#menuId').combobox('setValues',ret.data.data.menuId.split(','));
				}
			}
			
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
	
	var delUrl = dataOptions.baseUrl + 'delFoodStore.htm';/* 配置删除的地址 */
	
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
		$('#import-data-form').form('clear');
		$('#import-dialog').dialog('close');
};	
	


var importExcel = function(){
		$('#import-data-form').form('clear');// 清除form表单数据
		$( "#import-dialog" ).dialog("open").dialog('setTitle','食材数据导入');
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
				cout:5000,
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




























