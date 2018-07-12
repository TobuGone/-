<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	String contextPath = request.getContextPath();
%>
<div id="cc" class="easyui-layout myResizelayout" data-optins="fit:true,border:false" style="width:100%;height:100%;">
	<div data-options="region:'west',title:'功能菜单',border:1" style="width:200px;height:120%;">
		<ul id="tt" class="easyui-tree" url="<%=basePath%>resource/findResourceTree.htm" 
			data-options="fit:true,animate:true,cascadeCheck:false,
		        	onBeforeLoad:function(node){
		        		if(null != node){
		        		var opts = $(this).tree('options');
		        			if(node.children && node.children.length < 1){
		       					return true;
							}else{
								$(this).tree('toggle', node.target);
							}
		        	}},onLoadSuccess:function(node, data){
		        		if(data && data.status == 10001){
		        			data = data.data.treeData;
		        			$(this).tree('loadData',data);
		        		}else{
		        			if(data.status == 21001) parent.window.location = '<%=basePath%>login/login.jsp';
		        		}
		        	},onSelect:function(node){
		        		if(null == node) return;
		        		if(node.url){
		        			var tempUrl = '<%=basePath%>views/'+node.url;
		        			var tv = $('#tabs').tabs('getTab',node.text);
			        		if(tv){
			        			$('#tabs').tabs('select',node.text);
			        			return;  
			        		}else{
			        			$('#tabs').tabs('add',{
							    	title: node.text,
							    	content: getContents(tempUrl),
							    	closable: true,
							    	onUnselect:function(title,index){
							    		$(this).tabs('getTab',title).panel({content:' '});
							    	},
							    	tools:[{
							    		iconCls:'icon-mini-refresh',
							    		handler:function(){
							    			$('#tabs').tabs('getSelected').panel('clear');
							    			$('#tabs').tabs('getSelected').html(getContents(tempUrl));
							    		}
							    	}]
							    });
		        			}
		        		}
		        	}"></ul>
	</div>
	<div data-options="region:'center',border:false,onResize:myResize1" style="">
		<div id="tabs" class="easyui-tabs" data-options="fit:true,
			onSelect:function(title,index){
					myResize1(-1,-1);
			}" style="width:100%;height:100%;">
			<div title="主页" data-options="closable:false,href:'<%=basePath%>views/welcome.jsp'" style="">
				
			</div>
    	</div>
	</div>
</div>