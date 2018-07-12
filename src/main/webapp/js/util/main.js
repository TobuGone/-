

//----------------huangzh-------------------

//角色权限
var authorityFormatter = function(value,row,index){
	if(row.roleValue != "ROLE_SUPER_ADMIN"){
		return '<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton cu-lbtn" data-options="iconCls:\'icon-tip\'" onClick="gridAuthority('+index+')">分配权限</a>';
	}else{
		return '--'
	}
	
};
//审核
var auditFormatter = function(value,row,index){
	return '<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton cu-lbtn" data-options="iconCls:\'icon-edit\'" onClick="gridAdopt('+index+')">通过 </a>'+
	'<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton cu-lbtn" data-options="iconCls:\'icon-edit\'" onClick="gridNoAdopt('+index+')">不通过</a>';
};


var userFormatter = function(value,row,index){
	return '<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton cu-lbtn" data-options="iconCls:\'icon-edit\'" onClick="gridEdit('+index+')">修    改 </a>'+
	'<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton cu-lbtn" data-options="iconCls:\'icon-edit\'" onClick="gridUpdatePWD('+index+')">重置密码</a>';
};

//编辑、暂停、启用
var editStopStartFormatter = function(value,row,index){
	if(row.status == '1'){
		return '<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton cu-lbtn" data-options="iconCls:\'icon-edit\'" onClick="gridEdit('+index+')"> 编 辑 </a>'
		+'<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton cu-lbtn" data-options="iconCls:\'icon-no\'" onClick="gridStop('+index+')"> 暂停使用 </a>';
	}else{
		return '<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton cu-lbtn" data-options="iconCls:\'icon-edit\'" onClick="gridEdit('+index+')"> 编 辑 </a>'
		+'<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton cu-lbtn" data-options="iconCls:\'icon-ok\'" onClick="gridEnable('+index+')"> 启    用 </a>';
	}
	
};

//修改
var EditFormatter = function(value,row,index){
	return '<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton cu-lbtn" data-options="iconCls:\'icon-edit\'" onClick="gridEdit('+index+')"> 修 改 </a>';
};


//菜谱与id合并
var menubookFormatter  = function(value,row,index){
	return row.id+row.name;
};

//查看
var searchFormatter  = function(value,row,index){
	return '<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton cu-lbtn" data-options="iconCls:\'icon-tip\'" onClick="gridSearchDeatil('+index+')"> 查看 </a>';
};


//编辑删除
var editDeleteFormatter = function(value,row,index){
	return '<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton cu-lbtn" data-options="iconCls:\'icon-edit\'" onClick="gridEdit('+index+')"> 编 辑 </a>'
	+'<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton cu-lbtn" data-options="iconCls:\'icon-remove\'" onClick="gridDel('+index+')"> 删 除</a>';
};


//是否推荐
var recommendFormatter  = function(value,row,index){
	if(row.recommend == '0'){
		return '<a id="" href="javascript:void(0)" onClick="recommend('+index+')"> 否 </a>';
	}
	if(row.recommend == '1'){
		return '<a id="" href="javascript:void(0)" onClick="recommend('+index+')"> 是 </a>';
	}
};

//设为盖头广告
var isAdvertFormatter  = function(value,row,index){
	if(row.isAdvert == '0'){
		return '<a id="" href="javascript:void(0)" onClick="setAdvert('+index+')"> 否 </a>';
	}
	if(row.isAdvert == '1'){
		return '<a id="" href="javascript:void(0)" onClick="setAdvert('+index+')"> 是 </a>';
	}
};


//------------------- zoneyu -----------------------------------------------------------------------------------
//图片格式化并点击查看大图
var htmlFormatter = function(value,row,index){
	var html ;
	if(value != null && value != "" && value != "null"){
		html = '<div style="width:120px;height:60px;">'+value+'</div>';
	}else{
		html = "--" ;
	}
	return html;
}

//图片格式化
var imageFormatter = function(value,row,index){
	var html ;
	if(value != null && value != "" && value != "null"){
		html = '<img width="110px;" height="65px;" title="无数据" src="'+base.basePath+value+'"></img>';
	}else{
		html = "--" ;
	}
	return html;
}
//图片格式化并点击查看大图
var imageFormatterWithClick = function(value,row,index){
	var html ;
	if(value != null && value != "" && value != "null"){
		var path = base.basePath+value ;
		html = '<img width="130px;" style="width:120px;height:60px;" title="点击查看大图" src="'+path+'" onclick="visiteLarge(this)"></img>';
	}else{
		html = "--" ;
	}
	return html;
}

//状态
var statusFormatter = function(value,row,index){
	if(value == 0){
		html = '<font color="blue">'+statusTypeData[0].text+'</font>' ;
	}else if(value == 1){
		html = '<font color="red">'+statusTypeData[1].text+'</font>' ;
	}else{
		html = '<font color="red">数据错误</font>' ;
	}
	return html;
}

/**判断是否为空*/
var isBlank = function(str){
	if(str != null && str != '' && str != 'null' && str != 'NULL'){
		return false ;
	}else{
		return true ;
	}
}
//--------------------- zoneyu --------------------------------------------------------------------------------------

// 表格加载成功后执行事件
var gridLoadSuccess = function(data){
	$(".cu-lbtn").linkbutton({});
    $(this).datagrid("fixRownumber");
}
//去除html中的标签
function myextractor(data) {
	var bodyRegEx = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
	var found = bodyRegEx.exec(data);
	if (found) {
		return found[1];
	} else {
		return data;
	}
}
var controlFormatter = function(value,row,index){
	return '<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton" onClick="gridEdit('+index+')"> 修 改 </a>'
	+'<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton" onClick="gridDel('+index+')"> 删 除 </a>';
}
var getData = function(rowIndex){
	mygrid.datagrid('selectRow', rowIndex); 
	return mygrid.datagrid('getSelected');// 取到选择的数据
}
var timeFormatter = function(value,row,index){
	if(value) return $.cus.datelongToStr(value);
	else return '';
}
var cmbSelect = function(record){
	if(record.id == 0) $(this).next().find('.combo-text').css('color','green');
	else $(this).next().find('.combo-text').css('color','red');
}
var derror = function(){
	$.messager.alert('错误','请求服务器失败!','error');
}
shipStatusData=[{id:0,text:'待发货'},{id:1,text:'已发货'}];
var shipStatusFormatter = function(value,row,index){
	if(value == 0 || (value && value.id == 0 )) return '<font color="green">'+shipStatusData[0].text+'</font>';
    else if(value == 1 || (value && value.id == 1 )) return '<font color="red">'+shipStatusData[1].text+'</font>';
    else return value.text;
}

var payStatusData=[{id:0,text:'未付款'},{id:1,text:'已付款'},{id:2,text:'支付失败'}];

var payStatusFormatter = function(value,row,index){
if(value == 0 || (value && value.id == 0 )) return '<font color="blue">'+payStatusData[0].text+'</font>';
    else if(value == 1 || (value && value.id == 1 )) return '<font color="green">'+payStatusData[1].text+'</font>';
    else if(value == 2 || (value && value.id == 2 )) return '<font color="red">'+payStatusData[2].text+'</font>';
    else return value.text;
}
var frameSize = 0;
var getContents = function(tempUrl){
	frameSize ++;
	var html = '<iframe src="{0}" id="itemFrames'+frameSize+'" name="itemFrames'+frameSize+'" allowTransparency="true" style="border:0;width:100%;height:86%" frameBorder="0"></iframe>';
	return $.cus.formatString(html,tempUrl);
}
var formSubmit = function(form,url,submit,success){
	parent.$.messager.progress();
	$(form).form('submit',{
		url: url,
		onSubmit:submit,
		type:"POST",
	    success:function(data){
	    	parent.$.messager.progress('close');
	    	data = $.parseJSON(data);
	    	if(data){
	    		if(data == -1){}// 正确
	    		else errorData(data);
	    	}
	    	if(success) success(data);
	    }
	});
}
/* 导入模版
   1:学校
*/ 
var exportExcel = function(type){
	if(type) window.location = base.basePath + 'ExcelC/exportExcel.do?type='+type;
}
// 导入数据
var excelType = 1;
function dlgUploadExcel(loadcb, type){
	if(type) excelType = type;
	if($('#dlgUploadExcel').length < 1)$('body').append('<div id="dlgUploadExcel"></div>');
	$('#dlgUploadExcel').dialog({  
    	title: '上传文件',  
    	width: 380,  
    	height: 200,  
    	cache: false,  
    	href: base.basePath+'views/dialog/ExcelDialog.jsp',  
    	modal: true,
    	onLoad: function(){
    		if(loadcb) loadcb();
    	},
		buttons:[
			{
				text:'关闭窗口',
				iconCls:'icon-cancel',
				handler:function(){
					$('#dlgUploadExcel').dialog('close');
				}
			}/* ,{
				text:'放弃',
				iconCls:'icon-cancel',
				handler:function(){
					$('#dlgUploadExcel').dialog('close');
				}
			} */]
	});
	$('#dlgUploadExcel').dialog('open');
}