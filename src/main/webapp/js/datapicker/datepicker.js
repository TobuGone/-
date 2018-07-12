;(function($){
	 var options = {
		 skin:'whyGreen',
		 dateFmt:'yyyy-MM-dd HH:mm:ss',
		 autoPickDate:true
	 };
	$(".WdatePicker").on("click" ,function(){
		alert(1);
			 WdatePicker(options);
	 });
	
/*	$.fn.extend({
	 "datetimepicker" : function(options){
         if(options.hasOwnProperty('format')){
        	 options.dateFmt = options.format;
         }

		 options = $.extend({
			 skin:'whyGreen',
			 dateFmt:'yyyy-MM-dd HH:mm:ss',
			 autoPickDate:true,
		 },options);

		 $(this).bind("click" ,function(){
			 if($(this).hasClass("cfbtn-dateImg")){
				 options.el = options.id;
			 }
				 WdatePicker(options);
		 });
	 }
	});*/
	
	
})(jQuery);