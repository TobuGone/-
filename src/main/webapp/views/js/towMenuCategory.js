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
};

//暂停或者使用用户
function upStatus(statusName){
	var formDate = $("#mygrid");
	var sele = formDate.datagrid("getSelections");
	if(sele.length==0){
		$.messager.alert("确认","至少选择一条数据?")
		return 
	}
	var ids = [];
	var names = [];
	var pids = [];
	for(var i in sele){
		ids.push(sele[i].id);
		names.push(sele[i].name);
		pids.push(sele[i].pid);
	}
	ids = ids.join(",");
	pids = pids.join(",");
	var type = "0";
	var data ;
	var url ;
	if(statusName=="使用"){
		type = "1";
		data = {"pids":pids};
		url = dataOptions.baseUrl+'getCategoryStatus.htm';
		var success = function(ret1){
			if(ret1 && ret1.status == 10001){
				$.messager.confirm('确认','确定'+statusName+'菜谱名为[ '+names+' ]吗？',function(r){
					if(r){
						data = {"ids":ids,"type":type};
						url = dataOptions.baseUrl+'upCategoryStatus.htm';
						var success = function(ret){
							if(ret && ret.status == 10001){
								formDate.datagrid('reload');
								$.messager.show({
									title:'提示',
									msg:statusName+'成功',
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
			}else parent.$.messager.alert("错误",ret1.msg,"error");
		};
		var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
		// ajav异步查询数据
		asynPAjaxMin(url,data,success,error);
	}else{
		$.messager.confirm('确认','确定'+statusName+'菜谱名为[ '+names+' ]吗？',function(r){
			if(r){
				data = {"ids":ids,"type":type};
				url = dataOptions.baseUrl+'upCategoryStatus.htm';
				var success = function(ret){
					if(ret && ret.status == 10001){
						formDate.datagrid('reload');
						$.messager.show({
							title:'提示',
							msg:statusName+'成功',
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
	}
};

var saveTowCategory = function(){
	    var url = dataOptions.baseUrl+'saveTowCategory.htm';/* 配置保存数据地址 */
		
		var check = function(){// 验证
			
			var isValid = $(this).form('validate');
			
			
			if(isValid){
				if($("#imgShow").attr("src") == null || $("#imgShow").attr("src") == ''){
					
					parent.$.messager.alert("提示","缩略图不能为空","info"); 
					
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
		if(ret && ret.status == 10001){
			
			$('#add-data-form').form('clear');// 清除form表单数据
			
			if(dataOptions.editInit)  dataOptions.editInit(ret.data.data);
			
			new uploadPreview({ UpBtn: "up_img", DivShow: "imgdiv", ImgShow: "imgShow" });
			
			$("#imgShow").attr("src",dataOptions.basePath+"/"+ret.data.data.logoPath);
			
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


var recommend = function(rowIndex){
	
	mygrid.datagrid('selectRow', rowIndex); 
	
	var rowData = mygrid.datagrid('getSelected');// 取到选择的数据
	
	var url = dataOptions.baseUrl + 'recommend.htm';/* 配置查询要修改的数据查询地址 */
	
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