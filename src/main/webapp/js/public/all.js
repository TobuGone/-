var webRoot ;

$(function(){
    var pathName=window.document.location.pathname;
    webRoot = pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    
    $("#status").combobox({
    	valueField:'id',
    	textField:'text',
    	data:[{
    		"id":"",
    		"text":"请选择"
    	},{
    		"id":"0",
    		"text":"有效"
    	},{
    		"id":"1",
    		"text":"无效"
    	}]
    }) ;

    $("#status1").combobox({
    	valueField:'id',
    	textField:'text',
    	data:[{
    		"id":"",
    		"text":"请选择"
    	},{
    		"id":"0",
    		"text":"有效"
    	},{
    		"id":"1",
    		"text":"无效"
    	}]
    }) ;
    
}) ;

var operationFormatter = function(value,row,index){
	return '<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton" onClick="gridEdit('+index+')">修 改 </a>'
	       +'<a id="" href="javascript:void(0)" style="margin-left: 10px;" class="easyui-linkbutton" onClick="gridDel('+index+')">删 除 </a>';
}

/**打开窗口*/
function openDIV(id){
	$('#'+id).dialog({
		closed:false
	}) ;
}

/**关闭窗口*/
function closeDIV(id){
	$('#'+id).dialog({
		closed:true
	}) ;
}

var changeTwoDecimal= function changeTwoDecimal(floatvar)
{
	var f_x = parseFloat(floatvar);
	var f_x = Math.round(floatvar*10000)/10000;
	return f_x;
}


//开始   此段代码是用于自适应浏览器大小
function initDataGrid(){
	$('#mygrid').datagrid("resize") ;
}

function initDataGrid1(){
	setTimeout("initWindow()",800) ;
}

function initWindow(){
	$('#mygrid').datagrid("resize") ;
}

window.onresize = function(){
	setTimeout("initWindow()",800);
} ;
//结束

var isCheck ;
var checkForm = function(formNames){
	isCheck = true ;
	var nodes = $('#'+formNames+' input[required="required"]') ;
	$.each(nodes,function(i,domEle){
		var val = domEle.value ;
		console.info(val) ;
		if(val == null || val == ''){
			$(domEle).focus(); 
			isCheck = false ;
			return false ;
		}else{
			isCheck = true ;
		}
	}) ;
	return isCheck ;
}
