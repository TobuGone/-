var dataOptions = {
	baseUrl:"",
	basePath:"",
	addInit:function(){},	  // 添加弹窗初始化
	editInit:function(data){},// 修改数据加载完成初始化
	saveInit:function(data){} // 保存前执行操作
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


//删除
var gridDel = function(rowIndex){
	
	var sele = mygrid.datagrid("getSelections");
	if(sele.length==0){
		$.messager.alert("确认","至少选择一条数据")
		return 
	}
	
	var ids = [];
	for(var i in sele){
		ids.push(sele[i].id);
	}
	ids = ids.join(",");
	
	var delUrl = dataOptions.baseUrl + 'delMenuBookShare.htm';/* 配置删除的地址 */
	
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








