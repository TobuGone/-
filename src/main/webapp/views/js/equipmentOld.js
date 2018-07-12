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
	$("#spMsg").text('');
	$( "#add-dialog" ).dialog("open").dialog('setTitle','添加');
	
	var name = $("#fileText").val();
	//先择文件后触发更新文件名
	$("#fileText").change(function(){
		var b = $('#fileText').val();
		$('#fileText1').val(b);
	})
	name = $("#fileText1").val();
		
};


//新增
var saveUser = function(){
	var name = $("#fileText").val();

	if (name == '') {
		name = $("#fileText1").val();
	}
	
	var fileName = name.substring(name.lastIndexOf(".")+1,name.length);
	  	if(fileName == ""){
	  		$("#spMsg").text('请选择文件');
	  		return
	  	}
	var url = dataOptions.baseUrl+'saveEquipment.htm';/* 配置保存数据地址 */
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
			$('#add-dialog').dialog('close');
			parent.$.messager.alert("错误",ret.msg,"error");
		}
	};
	
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	
	formSubmit($('#add-data-form'),url,check,success);
	
}


//点击按钮关联原始input
$('#fButton').on("click",function(){
	$("#fileText").trigger("click");
});

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
			var path = ret.data.data.path;//ret.data.data.path 截取程序名称   substring方法
			var a = path.substring(path.lastIndexOf("/")+1)
			ret.data.data.dir = path;
			ret.data.data.filename = a;
			
			// 初始化
			$('#add-data-form').form('myLoad',ret.data.data);
			$('#fileText1').val(a);0
			
			//先择文件后触发更新文件名
			$("#fileText").change(function(){
				var b = $('#fileText').val();
				$('#fileText1').val(b);
			})
			
			//显示修改页面
			$('#add-dialog').dialog('setTitle','修改');
			$('#add-dialog').dialog('open');
			
		}else parent.$.messager.alert("错误",ret.msg,"error");
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	// ajav异步查询数据
	asynPAjaxMin(url,data,success,error);	
};




//删除
var gridDel = function(rowIndex){
	
	var sele = mygrid.datagrid("getSelections");
	if(sele.length==0){
		$.messager.alert("确认","至少选择一条数据?")
		return 
	}
	
	var ids = [];
	for(var i in sele){
		ids.push(sele[i].id);
	}
	ids = ids.join(",");
	
	var delUrl = dataOptions.baseUrl + 'delEquipment.htm';/* 配置删除的地址 */
	
	var postData = {'ids': ids};/* 请求到服务器的数据内容 */
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	
	var success = function(ret){
		if(ret && ret.status == 10001){
			mygrid.datagrid('reload');
			$.messager.show({
				title:'提示',
				msg:'删除成功',
				timeout:5000,
				showType:'slide'
			});
		}else parent.$.messager.alert("错误",ret.msg,"error");
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	
	// 调用此方法进行删除 
	parent.$.messager.confirm('确认','确认要永久删除所选数据?',function(r){  
       if (r) asynPAjaxMin(delUrl,postData,success,error);
    });
};



