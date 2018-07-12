/**
 * easyui扩展js
 */
// 扩展datebox的默认时间格式为按"-"分割 
$.fn.datebox.defaults.formatter = function(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	if(m < 10 && m.toString().length < 2) m = '0'+m;
	if(d < 10 && d.toString().length < 2) d = '0'+d;
	return y+'-'+m+'-'+d;
}

/****************************validatebox********************************/
//扩展easyui的validatebox验证配置
$.extend($.fn.validatebox.defaults.rules, {
	
	roleValidate : {
		validator : function(value) { 
			 if(value.indexOf("ROLE_")>=0){
				 return true;
			 }else{
				 return false;
			 }
		},
		message : '请输入以ROLE_开头格式的别名'
	},
	
	
	number : {
		validator : function(value) { return /^[0-9]*$/.test(value); },
		message : '请输入正确的数字.'
	},
	
	phone: {
		validator : function(value) { return /^(1[0-9])[0-9]{9}$/.test(value); },
		message : '请输入正确的手机号码.'
	},
	equals : {
		validator : function(value, param) { return value == $(param[0]).val(); },
		message : '输入{1}不匹配.'
	},
	minLength: {
		validator: function(value, param){ return value.length >= param[0]; },
		message: '至少输入 {0} 个字符.'
	},
	maxLength: {
		validator: function(value, param){ return value.length <= param[0]; },
		message: '最多输入 {0} 个字符.'
	},
	/**
	 * 时间验证 验证开始时间不能超过结束时间
	 * 开始时间输入框设置为data-options="validType:'time[\'#endTime\',\'填报\']'"
	 * #endTime = 结束时间输入框ID \'填报\' = 提示XX开始时间不能超过XX结束时间
	 */
	time: {
		validator: function(value, param){
			return value >  $(param[0]).timespinner('getValue');
		},
		message: '结束{1}不能小于开始{1}！'
	},
	
	/**
	 * 时间验证 验证开始时间不能超过结束时间
	 * 结束时间输入框设置为data-options="validType:'datebox[\'#startDay\',\'有效\']'"
	 * #endTime = 结束时间输入框ID \'填报\' = 提示XX开始时间不能超过XX结束时间
	 */
	date: {
		validator: function(value, param){
			return value >=  $(param[0]).datebox('getValue');
		},
		message: '结束{1}不能小于开始{1}！'
	},
	numbox: {
		validator: function(value, param){
			return value >=  $(param[0]).numberbox('getValue');
		},
		message: '结束{1}不能小于开始{1}！'
	},
	combobox: {
		validator: function(value, param){
			return value >=  $(param[0]).combobox('getValue');
		},
		message: '结束{1}不能小于开始{1}！'
	},
	empty: {
		validator: function(value){ console.log(value); return $.trim(value).length > 0 ? true : false; },
		message: '必须输入！' 
	} 
});


// 扩展easyui的form
$.extend($.fn.form.methods, {
	/* 获取form表单数据
	 */
    getData: function(jq, params){
        var formArray = jq.eq(0).serializeArray();
        var oRet = {};
        for (var i in formArray) {
            if (typeof(oRet[formArray[i].name]) == 'undefined') {
                if (params) {
                    oRet[formArray[i].name] = (formArray[i].value == "true" || formArray[i].value == "false") ? formArray[i].value == "true" : formArray[i].value;
                }
                else {
                    oRet[formArray[i].name] = formArray[i].value;
                }
            }
            else {
                if (params) {
                    oRet[formArray[i].name] = (formArray[i].value == "true" || formArray[i].value == "false") ? formArray[i].value == "true" : formArray[i].value;
                }
                else {
                    oRet[formArray[i].name] += "," + formArray[i].value;
                }
            }
        }
        
        // 获取 无法普通获取到的easyui控件datebox
        var dates = jq.find('.easyui-datebox');
    	$.each(dates,function(i,v){
    		$.extend(oRet, eval('({"'+$(v).attr('comboname')+'":"'+$(v).datebox('getValue')+'"})'));
    	});
    	
        return oRet;
    },
    // 自定义clean 清除表单后不做validate操作
    clear: function(target, param){
    	$('.upImg', target).attr('src','')// 清除上传图片
    	$("input,select,textarea", target).each(function() {
			var t = this.type, tag = this.tagName.toLowerCase();
			if (t == "text" || t == "hidden" || t == "password"
					|| tag == "textarea") {
				this.value = "";
			} else {
				if (t == "file") {
					var node = $(this);
					node.after(node.clone().val(""));
					node.remove();
				} else {
					if (t == "checkbox" || t == "radio") {
						this.checked = false;
					} else {
						if (tag == "select") {
							this.selectedIndex = -1;
						}
					}
				}
			}
		});
		
		if($.fn.linkbutton){
			$.each($(".l-btn", target),function(i,linkbutton){
				var opts = $(linkbutton).linkbutton('options');
				if(opts && opts.group && opts.value) {// 分组 tag
					var dValue = 1;
					if(opts.value) dValue = opts.value;
					$(linkbutton).linkbutton('select');
					if(tagButtonClick) tagButtonClick(dValue,$(linkbutton))// 回调选择事件
				}
			});
		}
		if ($.fn.combo) {
			$(".combo-f", target).combo("clear");
		}
		if ($.fn.combobox) {// combobox 默认数据
			$.each($(".combobox-f", target),function(i,combobox){
				var opts = $(combobox).combobox('options');
				if(opts.url) $(combobox).combobox('reload',opts.url);
				
				if(opts.value == -1){
					var cdata = $(combobox).combobox('getData');
					if(cdata && cdata[0]) $(combobox).combobox('select', cdata[0].id);
				} else if(opts.value) $(combobox).combobox('select', opts.value);
				else $(combobox).combobox("clear");
			});
		}
		if($.fn.datebox){// combodate 默认数据
			$.each($(".datebox-f", target),function(i,datebox){
				var v = $(datebox).datebox('options').value;
				var value = '';
				if(v == 'y'){// 默认一年以后
					value = $.cus.getNextData(1,0,0);
				}else if(v == 'm'){// 默认当前时间一个月以后
					value = $.cus.getNextData(0,1,0);
				}else if(v == 'd'){// 不处理 直接清空
					value = $.cus.getNextData(0,0,0);
				}else value = '';
				
				var state = $.data(datebox, 'datebox');
				var opts = state.options;
				$(datebox).combo('setValue', value).combo('setText', value);
				state.calendar.calendar('moveTo', opts.parser(value));
			});
		}
		if ($.fn.combotree) {
			$(".combotree-f", target).combotree("clear");
		}
		if ($.fn.combogrid) {
			$(".combogrid-f", target).combogrid("clear");
		}
    },
	myLoad : function (jq, param) {
		return jq.each(function () {
			load(this, param);
		});

		function load(target, param) {
			if (!$.data(target, "form")) {
				$.data(target, "form", {
					options : $.extend({}, $.fn.form.defaults)
				});
			}
			var options = $.data(target, "form").options;
			if (typeof param == "string") {
				var params = {};
				if (options.onBeforeLoad.call(target, params) == false) {
					return;
				}
				$.ajax({
					url : param,
					data : params,
					dataType : "json",
					success : function (rsp) {
						loadData(rsp);
					},
					error : function () {
						options.onLoadError.apply(target, arguments);
					}
				});
			} else {
				loadData(param);
			}
			function loadData(dd) {
				var form = $(target);
				var formFields = form.find("input[name],select[name],textarea[name]");
				formFields.each(function(){
					var name = this.name;
					var value = jQuery.proxy(function(){try{return eval('this.'+name);}catch(e){return "";}},dd)();
					var rr = setNormalVal(name,value);
					if (!rr.length) {
						var f = form.find("input[numberboxName=\"" + name + "\"]");
						if (f.length) {
							f.numberbox("setValue", value);
						} else {
							$("input[name=\"" + name + "\"]", form).val(value);
							$("textarea[name=\"" + name + "\"]", form).val(value);
							$("select[name=\"" + name + "\"]", form).val(value);
						}
					}
					setPlugsVal(name,value);
				});
				options.onLoadSuccess.call(target, dd);
				$(target).form("validate");
			};
			function setNormalVal(key, val) {
				var rr = $(target).find("input[name=\"" + key + "\"][type=radio], input[name=\"" + key + "\"][type=checkbox]");
				rr._propAttr("checked", false);
				rr.each(function () {
					var f = $(this);
					if (f.val() == String(val) || $.inArray(f.val(), val) >= 0) {
						f._propAttr("checked", true);
					}
				});
				return rr;
			};
			function setPlugsVal(key, val) {
				var form = $(target);
				var cc = ["combobox", "combotree", "combogrid", "datetimebox", "datebox", "combo"];
				var c = form.find("[comboName=\"" + key + "\"]");
				if (c.length) {
					for (var i = 0; i < cc.length; i++) {
						var combo = cc[i];
						if (c.hasClass(combo + "-f")) {
							if (c[combo]("options").multiple) {
								c[combo]("setValues", val);
							} else {
								c[combo]("setValue", val);
							}
							return;
						}
					}
				}
			};
		};
	}
});
/**
 * 日期选择扩展
 * 增加清除操作 用法data-options中添加 buttons:bclear
 */
var bclear = $.extend([], $.fn.datebox.defaults.buttons);
bclear.splice(1, 0, {
	text: '清除',
	handler: function(target){
		var value = '';
		var state = $.data(target, 'datebox');
		var opts = state.options;
		$(target).combo('setValue', value).combo('setText', value);
		state.calendar.calendar('moveTo', opts.parser(value));
		$(target).combo('hidePanel');
	}
});

function myloader(param,success,error){
	console.log(param);
	console.log(success);
	console.log(error);
	var that = $(this);
	var opts = that.datagrid("options");
	if (!opts.url) {
		return false;
	}
	
}
$.extend($.fn.datagrid.methods, {
	fixRownumber : function (jq) {
		return jq.each(function () {
			var panel = $(this).datagrid("getPanel");
			//获取最后一行的number容器,并拷贝一份
			var clone = $(".datagrid-cell-rownumber", panel).last().clone();
			//由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
			clone.css({
				"position" : "absolute",
				left : -1000
			}).appendTo("body");
			var width = clone.width("auto").width();
			//默认宽度是25,所以只有大于25的时候才进行fix
			if (width > 25) {
				//多加5个像素,保持一点边距
				$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
				//修改了宽度之后,需要对容器进行重新计算,所以调用resize
				$(this).datagrid("resize");
				//一些清理工作
				clone.remove();
				clone = null;
			} else {
				//还原成默认状态
				$(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
			}
		});
	}
});
//本js使用方法，只需要在easyui.min.js之后导入便可。基于1.3.3的版本修改。没有测试向前的兼容性。
$.extend($.fn.datagrid.defaults.view, {
	render : function (target, container, frozen) {
		var state = $.data(target, "datagrid");
		var opts = state.options;
		var rows = state.data.rows;
		var fields = $(target).datagrid("getColumnFields", frozen);
		if (frozen) {
			if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))) {
				return;
			}
		}
		var table = ["<table class=\"datagrid-btable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"];
		if(!rows) return;
		for (var i = 0; i < rows.length; i++) {
			var cls = (i % 2 && opts.striped) ? "class=\"datagrid-row datagrid-row-alt\"" : "class=\"datagrid-row\"";
			var styleValue = opts.rowStyler ? opts.rowStyler.call(target, i, rows[i]) : "";
			var style = styleValue ? "style=\"" + styleValue + "\"" : "";
			var rowId = state.rowIdPrefix + "-" + (frozen ? 1 : 2) + "-" + i;
			table.push("<tr id=\"" + rowId + "\" datagrid-row-index=\"" + i + "\" " + cls + " " + style + ">");
			table.push(this.renderRow.call(this, target, fields, frozen, i, rows[i]));
			table.push("</tr>");
		}
		table.push("</tbody></table>");
		$(container).html(table.join(""));
		//增加此句以实现,formatter里面可以返回easyui的组件，以便实例化。例如：formatter:function(){ return "<a href='javascript:void(0)' class='easyui-linkbutton'>按钮</a>" }}
		$.parser.parse(container);
	},
	renderRow : function (target, fields, frozen, rowIndex, rowData) {
		var opts = $.data(target, "datagrid").options;
		var cc = [];
		if (frozen && opts.rownumbers) {
			var rownumber = rowIndex + 1;
			if (opts.pagination) {
				rownumber += (opts.pageNumber - 1) * opts.pageSize;
			}
			cc.push("<td class=\"datagrid-td-rownumber\"><div class=\"datagrid-cell-rownumber\">" + rownumber + "</div></td>");
		}
		for (var i = 0; i < fields.length; i++) {
			var field = fields[i];
			var col = $(target).datagrid("getColumnOption", field);
			if (col) {
				//修改默认的value取值，改了此句之后field就可以用关联对象了。如：people.name
				var value = jQuery.proxy(function(){try{return eval('this.'+field);}catch(e){return "";}},rowData)();
				var styleValue = col.styler ? (col.styler(value, rowData, rowIndex) || "") : "";
				var style = col.hidden ? "style=\"display:none;" + styleValue + "\"" : (styleValue ? "style=\"" + styleValue + "\"" : "");
				cc.push("<td field=\"" + field + "\" " + style + ">");
				if (col.checkbox) {
					var style = "";
				} else {
					var style = styleValue;
					if (col.align) {
						style += ";text-align:" + col.align + ";";
					}
					if (!opts.nowrap) {
						style += ";white-space:normal;height:auto;";
					} else {
						if (opts.autoRowHeight) {
							style += ";height:auto;";
						}
					}
				}
				cc.push("<div style=\"" + style + "\" ");
				if (col.checkbox) {
					cc.push("class=\"datagrid-cell-check ");
				} else {
					cc.push("class=\"datagrid-cell " + col.cellClass);
				}
				cc.push("\">");
				if (col.checkbox) {
					cc.push("<input type=\"checkbox\" name=\"" + field + "\" value=\"" + (value != undefined ? value : "") + "\"/>");
				} else {
					if (col.formatter) {
						cc.push(col.formatter(value, rowData, rowIndex));
					} else {
						cc.push(value);
					}
				}
				cc.push("</div>");
				cc.push("</td>");
			}
		}
		return cc.join("");
	}
});
/*panel关闭时回收内存，主要用于layout使用iframe嵌入网页时的内存泄漏问题*/
/*$.extend($.fn.panel.defaults, {
	onBeforeDestroy : function() {
		var frame = $('iframe', this);
		try {
			if (frame.length > 0) {
				for ( var i = 0; i < frame.length; i++) {
					frame[i].src = '';
					frame[i].contentWindow.document.write('');
					frame[i].contentWindow.close();
				}
				frame.remove();
				if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
					try {
						CollectGarbage();
					} catch (e) {
					}
				}
			}
		} catch (e) {
		}
	}
});*/

var myOnLoadError = function(XMLHttpRequest) {
	if (parent.$ && parent.$.messager) {
		parent.$.messager.progress('close');
		parent.$.messager.alert('错误', XMLHttpRequest.responseText);
	} else {
		$.messager.progress('close');
		$.messager.alert('错误', XMLHttpRequest.responseText);
	}
}

var myOnLoadSuccess = function(data){
	if(data && data.viewName){
		parent.window.location = base.basePath + data.viewName;
		return false;
	}return true;
}

$.extend($.fn.datagrid.defaults, {onLoadSuccess:myOnLoadSuccess});
$.extend($.fn.treegrid.defaults, {onLoadSuccess:myOnLoadSuccess});
$.extend($.fn.tree.defaults, {onLoadSuccess:myOnLoadSuccess});
$.extend($.fn.combogrid.defaults, {onLoadSuccess:myOnLoadSuccess});
$.extend($.fn.combobox.defaults, {onLoadSuccess:myOnLoadSuccess});
$.extend($.fn.form.defaults, {onLoadSuccess:myOnLoadSuccess});

$.extend($.fn.datagrid.defaults, {
	pageList:[20,40,80,100],
	pageSize: 20,
	onLoadSuccess:function(data){
		if(data && data.viewName) parent.window.location = base.basePath + data.viewName;
		return false;
	}
});
/**
 * Author : ____′↘夏悸
 * Easyui KindEditor的简单扩展.
 * 有了这个之后,你就可以像使用Easyui组件的方式使用KindEditor了
 * 前提是你需要导入KindEditor的核心js和相关样式. 本插件也需要在Easyui.min和KindEditor之后导入.
 * 呵呵..没做深入扩展了,简单实现了一下功能,架子已经搭好.有需要的筒子可以在这基础上扩展.
 **/
(function ($, K) {
	if (!K)
		throw "KindEditor未定义!";

	function create(target) {
		var opts = $.data(target, 'kindeditor').options;
		var editor = K.create(target, opts);
		$.data(target, 'kindeditor').options.editor = editor;
	}

	$.fn.kindeditor = function (options, param) {
		if (typeof options == 'string') {
			var method = $.fn.kindeditor.methods[options];
			if (method) {
				return method(this, param);
			}
		}
		options = options || {};
		return this.each(function () {
			var state = $.data(this, 'kindeditor');
			if (state) {
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'kindeditor', {
						options : $.extend({}, $.fn.kindeditor.defaults, $.fn.kindeditor.parseOptions(this), options)
					});
			}
			create(this);
		});
	}

	$.fn.kindeditor.parseOptions = function (target) {
		return $.extend({}, $.parser.parseOptions(target, []));
	};

	$.fn.kindeditor.methods = {
		editor : function (jq) {
			return $.data(jq[0], 'kindeditor').options.editor.editor;
		},
		ehtml : function(jq,val){
			if(val) $.data(jq[0], 'kindeditor').options.editor.editor.html(val);
			else return $.data(jq[0], 'kindeditor').options.editor.editor.html();
		},
		reCreate : function(target,newOpts){
			K.remove(target);
			var opts = $.data(target, 'kindeditor').options;
			opts = K.extend(opts, newOpts);
			var editor = K.create(target, opts);
			$.data(target, 'kindeditor').options.editor = editor;
		},
		remove : function(target){
			K.remove(target);
		},
		newEditor : function (jq) {
			return $.data(jq[0], 'kindeditor').options.editor;
		}
	};

	$.fn.kindeditor.defaults = {
		resizeType : 1,
		allowPreviewEmoticons : false,
		allowImageUpload : true,
		urlType:'domain',
		items : [
			'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
			'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
			'insertunorderedlist', '|', 'emoticons', 'image','link','baidumap'],
		afterChange:function(){
			this.sync();//这个是必须的,如果你要覆盖afterChange事件的话,请记得最好把这句加上.
		},
		uploadJson : base.basePath + 'js/kindeditor-4.1.10/jsp/upload_json.jsp'
	};
	$.parser.plugins.push("kindeditor");
})(jQuery, KindEditor);