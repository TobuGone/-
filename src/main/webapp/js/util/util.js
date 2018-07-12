function refreshThis(){
	var id = $('#main_layout').data('myopenid');
	$('#tt').tree('select',$('#tt').tree('find',id).target);
}

function loadFilter(data){
  	if(data && data.status == 10001) return data.data;
  	else{
  		$.messager.alert('错误', data.msg,'error');
  		if(data.status == 21001) parent.window.location=base.basePath+'login/login.jsp';
  		var v = {total:0,rows:[]};
  		return v;
  	}
}

function formatedit(val, row){
	var rowIndex = $('#myGrid').datagrid('getRowIndex', row);
   	var retStr = '';
   	retStr += '<a href="javascript:void(0)" style="color:#00008B;" onclick="rowEdit(' + rowIndex + ')">修改</a> ';
	return retStr;
}
/**
 * js非空判断
 * return 空:true 非空:false
 */
function isEmpty(s){
	return typeof(s)=="undefined"||s==null||s==NaN||$.trim(s).length<1;
}

var errorData = function(data){
	if(data == -1){}
	else if(data == -2) $.messager.alert('错误！','操作失败！','error');
	else if(data == -3) $.messager.alert('错误！','上传图片失败,请选择正确的图片再试！','error');
	else if(data == -4) $.messager.alert('错误！','上传文件失败,请选择正确的文件再试！','error');
	else if(data == -6) $.messager.alert('提示！','连接超时,请重新登录！','error',function(){
		if(parent.window) parent.window.location = base.basePath;
	});
	else if(data < -10){}// 自定义错误处理
	else if(data < 0) $.messager.alert('错误！', data,'error');
}
/** 通用异步请求简单封装 */
function asynPAjaxMin(url,data,success,error,type,getType){
	if(type){}else type = 'Post';
	if(getType){}else getType = 'json';
	asynAjax(type,url,data,getType,function(data){
		if(success) success(data);
	},function(){
		if(error) error();
		else $.messager.alert('错误','请求服务器失败!','error');
	});
}

/**
 * 通用异步ajax请求
 * type 请求类型 Get/Post
 * url 请求的url地址
 * data 请求到服务器的数据内容
 * dataType 请求到服务器的数据内容的格式 json/xml等
 * success 请求成功后的回调函数 返回服务器返回的数据data
 * error 请求服务器失败回调函数
 */
function asynAjax(type,url,data,dataType,success,error){
//	console.log("type:"+type+"*url:"+url+"*data:");
//	console.log(data);
//	console.log("*success:"+success+"*error:"+error);
//	console.log("type:"+type+"*url:"+url+"*data:"+data+"*dataType:"+dataType+"*success:"+success+"*error:"+error);
	$.messager.progress();
	$.ajax({type:type,url:url,data:data,dataType:dataType,success:function(data){
			$.messager.progress('close');
			if(success) success(data);
		},error:function(){
			$.messager.progress('close');
			if(error) error();
		}
	});
}

/**
	jquery 对象转字符串
*/
jQuery.extend({
    stringify  : function stringify(obj) {         
        if ("JSON" in window) {
            return JSON.stringify(obj);
        }

        var t = typeof (obj);
        if (t != "object" || obj === null) {
            // simple data type
            if (t == "string") obj = '"' + obj + '"';

            return String(obj);
        } else {
            // recurse array or object
            var n, v, json = [], arr = (obj && obj.constructor == Array);

            for (n in obj) {
                v = obj[n];
                t = typeof(v);
                if (obj.hasOwnProperty(n)) {
                    if (t == "string") {
                        v = '"' + v + '"';
                    } else if (t == "object" && v !== null){
                        v = jQuery.stringify(v);
                    }

                    json.push((arr ? "" : '"' + n + '":') + String(v));
                }
            }

            return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
        }
    }
});
/**
 * 用于调节左边菜单大小时 中间部分控件可以自适应
 * 一.dagagrid自适应
 * 1.需要自动调节的datagrid加上class="myResize"
 * 2.如果dagagrid可以收缩，为处理收缩后调节左边菜单大小后 展开datagrid变形 可以在data-options加上 onBeforeExpand: myResize
 * 二.panel自适应
 * 1.需要自动调节的panel加上myResizePanel
 * @param w
 * @param h
 * @returns
 */
var myResize = function(w, h){
	var mylayouts = $('.myResizelayout');
	$.each(mylayouts,function(i,layout){
		var id = $(layout).attr('id');
		$('#'+id+'').layout('resize');
	});
	
	var mypanels = $('.myResizePanel');
	$.each(mypanels,function(i,panel){
		var id = $(panel).attr('id');
		$('#'+id+'.panel-body').panel('resize');
	});
	
	var mygrids = $('.myResize');
	$.each(mygrids,function(i,grid){
		var id = $(grid).attr('id');
		$('#'+id+'').datagrid('resize');
	});
	
};
var myResize1 = function(w, h){
	//if(base.resizeGrid) base.resizeGrid.datagrid('resize');
	/*try{
		if($('#tabs').tabs('select')){
			var iframe = $('#tabs').tabs('select').find('.panel:visible').find('iframe');
			if(h == -1){// 解决滚动条占用
				var isAdd = $(iframe).data('addHeight');
				if(isAdd){
					$(iframe).height($(iframe).height()+1);
					$(iframe).data('addHeight', false);
				}else{
					$(iframe).height($(iframe).height()-1);
					$(iframe).data('addHeight', true);
				}
			}
			else $(iframe).height(h-100);
		}
	}catch(err){
		//console.log(err);
	}*/
};
 var  onIMove = function(left,top){
	 if(left < 0){  
       $(this).dialog('move',{left:0,top:top});  
	 }if(top < 0){  
       $(this).dialog('move',{left:left,top:5});  
 	 } 
 };
 $.fn.dialog.defaults.onMove = onIMove;
 $.fn.window.defaults.onMove = onIMove;