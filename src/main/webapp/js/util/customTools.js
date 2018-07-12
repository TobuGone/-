/**
 *	添加自定义工具js
 */
jQuery.cus = {
	/* long类型时间转字符串时间
	 * 参数 
	 *	longTime long类型的时间
	 *  fmtStr 转换后用fmtStr传递过来的字符串分割时间
	 *  格式 不传递默认是"-"  
	 *  例如： fmtStr="*"  那么返回的数据会是： 2000*00*00  
	 */
	datelongToStr:function(longTime,fmtStr){
		var sDate = new Date(longTime).toLocaleDateString();
		if(fmtStr) return sDate.replace('年',fmtStr).replace('月',fmtStr).replace('日','');
		return sDate.replace('年','-').replace('月','-').replace('日','');
	},
	/* 字符串时间转long
	 * 参数
	 *  strTime 字符串格式时间
	 *  fmtStr 根据不同的分隔符转换时间  默认按照"-"分割  只能从以下选择:cn 
	 *  例如：2000*00*00 应传递分隔符"*" 否则无法正确转换
	 */
	dataStrToLong:function(strTime,fmtStr){
		if(fmtStr){
			if("cn".equals(fmtStr)) return Date.parse(strTime.replace(/年|月|日/g, "/"));
		} else return Date.parse(strTime.replace(/-/g, "/"));
	},
	getNextData:function(year, month, day){
		var myDate = new Date();
		if($.cus.isNotUndefined([year,month,day]) && $.cus.isNumber([year, month, day])){
			var nd = new Date();
			year = myDate.getFullYear()+year;
			month = myDate.getMonth()+month;
			day = myDate.getDate()+day;
			nd.setFullYear(year, month, day);
			return $.cus.formatterDateToStr(nd); 
		};
	},
	formatterDateToStr: function (date, dlink, mlink, type){
		var lk = "-";
		var mk = ":";
		if(dlink) lk = dlink;
		if(mlink) mk = mlink;
		var y = $.cus.fillDigits(date.getFullYear());
		var M = $.cus.fillDigits((date.getMonth()+1));
		var d = $.cus.fillDigits(date.getDate());
		var H = $.cus.fillDigits(date.getHours());
		var m = $.cus.fillDigits(date.getMinutes());
		var s = $.cus.fillDigits(date.getSeconds());
		if(type){
			if("all" == type) return y+lk+M+lk+d+" "+H+mk+m+mk+s;
		}
		return y+lk+M+lk+d;
	},
	fillDigits:function(value){ if(value && $.cus.isThanZero(value)) if(value < 10) return "0" + value; return value; },
	isNumber:function(nums){
		if($.isArray(nums)){
			var isTrue = true;
			$.each(nums,function(i,v){
				isTrue = /^[0-9]*$/.test(v) && isTrue;
			});
			return isTrue;
		}else return /^[0-9]*$/.test(nums);
	},
	isEmpty:function(values){
		if($.isArray(values)){
			var isTrue = true;
			$.each(values,function(i,v){
				isTrue = (typeof v == "undefined" || null == v) && isTrue;
			});
			return isTrue;
		}else return typeof values == "undefined" || null == values;
	},
	isNotUndefined:function(values){
		if($.isArray(values)){
			var isTrue = true;
			$.each(values,function(i,v){
				isTrue = (typeof v) != "undefined" && isTrue;
			});
			return isTrue;
		}else return (typeof values) != "undefined";
	},
	isThanZero:function(num){ return /^[1-9]*$/.test(num); },
	/**
	 * @example formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
	 * @returns 格式化后的字符串
	 */
	formatString:function(str) {
		for ( var i = 0; i < arguments.length - 1; i++) {
			str = str.replace("{" + i + "}", arguments[i + 1]);
		}
		return str;
	}
}