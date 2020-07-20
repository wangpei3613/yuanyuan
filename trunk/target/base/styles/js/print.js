$(function(){
	$('.sen-print-page .print-ele').each(function(){
		var d = $(this).prop('class').match(/w\d{2}?/ig),cls = '';
		if(d && d.length>0){
			cls = 'm'+d[0];
		}
		$(this).after('<span class="print-ele-title '+cls+'"></span>')
	});
	$('.sen-print-page .print-table-ele').each(function(){
		if(this.nodeName.toLowerCase() == 'textarea'){
			$(this).after('<div class="print-table-ele-title" style="text-align:left;min-height:2em;"></div>');
		}else{
			$(this).after('<div class="print-table-ele-title"></div>');
		}
	});
});
function senPrint(){
	$('.sen-print-page .print-ele').each(function(){
		$(this).next('.print-ele-title:eq(0)').text($(this).val());
	});
	$('.sen-print-page .print-table-ele').each(function(){
		$(this).next('.print-table-ele-title:eq(0)').text($(this).val());
	});
	print();
}