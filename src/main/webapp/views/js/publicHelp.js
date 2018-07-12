var editor;

var dataOptions = {
	baseUrl:"",
	basePath:"",
	addInit:function(){},// 添加弹窗初始化
	editInit:function(data){},// 修改数据加载完成初始化
	saveInit:function(data){}// 保存前执行操作
};

var mygrid = $('#mygrid').datagrid();// 定义一个全局的表格对象


//修改
var gridEdit = function(rowIndex){
	mygrid.datagrid("unselectAll");
	
	mygrid.datagrid('selectRow', rowIndex); 
	
	var rowData = mygrid.datagrid('getSelected');// 取到选择的数据
	
	var url = dataOptions.baseUrl + 'data.htm';/* 配置查询要修改的数据查询地址 */
	
	var data = {id:rowData.id}; /* 请求的参数内容 */
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	var success = function(ret){
		if(ret && ret.status == 10001){
			$('#add-data-form').form('clear');// 清除form表单数据
			if(dataOptions.editInit)  dataOptions.editInit(ret.data.data);
			editor.html(ret.data.data.memo);
			
			var datas = ret.data.data;
			
			for(var key in datas){
				if(key == 'title'){
					$("#"+key+"").text(datas[key]+"");
				}
			}
			
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

var saveServiceAgreeMent = function(){
	
	var url = dataOptions.baseUrl+'saveServiceAgreeMent.htm';/* 配置保存数据地址 */
	
	var check = function(param){// 验证
	};
	/* 请求成功后回调函数success  data为请求服务器返回的参数*/
	var success = function(ret){
		if(ret && ret.status == 10001){
			$('#add-dialog').dialog('close');
			mygrid.datagrid('reload');
			$('#add-data-form').form('clear');
			
			$.messager.show({
				title:'提示',
				msg:'修改成功',
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


/**
 * 编辑器创建
 */
KindEditor.ready(function(K) {
	editor = K.create('textarea[id="memo"]', {
		cssPath : dataOptions.basePath+'/css/img.css',
		uploadJson : dataOptions.basePath+'/js/kindeditor-4.1.10/jsp/upload_json.jsp',
		extraFileUploadParams:{folder:'siteAccessories'},
		resizeType : 1,
		allowFileManager : true,
		allowPreviewEmoticons : false,
		urlType:'domain',
		items : [
	 		'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
	 		'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
	 		'insertunorderedlist', '|', 'emoticons', 'image', 'link'
 		],
		afterCreate : function(){ //kindeditor创建后，将编辑器的内容设置到原来的textarea控件里
            this.sync();  
	    },
	    afterChange: function(){ //编辑器内容发生变化后，将编辑器的内容设置到原来的textarea控件里
	            this.sync();  
	    },
	    afterBlur : function(){ //编辑器聚焦后，将编辑器的内容设置到原来的textarea控件里
	         this.sync();
	    }  
	});
});