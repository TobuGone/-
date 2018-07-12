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


//查看
var gridSearchDeatil = function(rowIndex ){
	mygrid.datagrid('selectRow', rowIndex); 
	
	var rowData = mygrid.datagrid('getSelected');// 取到选择的数据
	
	var url = dataOptions.baseUrl + 'data.htm';/* 配置查询要修改的数据查询地址 */
	
	var data = {id:rowData.id}; /* 请求的参数内容 */
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	var success = function(ret){
		if(ret && ret.status == '10001'){
			$('#search-form').form('clear');// 清除form表单数据
			
			$("#category").children('div').remove();
			
			$("#procedure").children('div').remove();
			
			var datas = ret.data.data;
			
			//分类遍历
			var categorys = ret.data.data.list;
			
			if(categorys.length >0){
				var html ="";
				
				for(var i = 0;i<categorys.length ;i++){
					
					html = '<div><label>一级分类：'+categorys[i].firstName+'</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label>二级分类：'+categorys[i].secondName+'</label></div>';
					
					$("#category").append(html);
				}
			}
			
			
			var procedures = ret.data.data.procedures;
			
			if(procedures.length >0){
				var html ="";
				
				for(var i = 0;i<procedures.length ;i++){
					var value ="";
					var foods = procedures[i].foods.split(";");
					for(var j = 0;j<foods.length-1; j++){
						food = foods[j].split("|");
						value += food[1]+":"+food[2]+"g; ";
					}
						html = '<div><table><tr><td style="width: 100px">'+(i+1)+'、时间：</td><td>'+procedures[i].minute+'分钟'+procedures[i].second;
						html += '秒</td></tr><tr><td style="width: 100px">步骤描述：</td><td>';
						html += procedures[i].describes+'</td></tr><tr><td>食材：</td><td>'+value+'</td></tr></table></div>';
					
					$("#procedure").append(html);
				}
			}
			
			for(var key in datas){
				
				$("#"+key+"").text(datas[key]+"");
				
				if(key == "logoPath"){
					$("#imgShow").attr("src",dataOptions.basePath+"/"+datas[key]);
				}
				
			}
			
			$( "#search-dialog" ).dialog("open").dialog('setTitle','查看');
			
		}else parent.$.messager.alert("错误",ret.msg,"error");
	};
	var error = null; // 请求服务器失败回调函数error 传递null使用默认error处理
	// ajav异步查询数据
	asynPAjaxMin(url,data,success,error);
	
	
	
};