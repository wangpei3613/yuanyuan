(function() {
	var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
	Math.uuid = function(len, radix) {
		var chars = CHARS, uuid = [], i;
		radix = radix || chars.length;
		if (len) {
			for (i = 0; i < len; i++)
				uuid[i] = chars[0 | Math.random() * radix];
		} else {
			var r;
			uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
			uuid[14] = '4';
			for (i = 0; i < 36; i++) {
				if (!uuid[i]) {
					r = 0 | Math.random() * 16;
					uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
				}
			}
		}
		return uuid.join('');
	};

	Math.uuidFast = function() {
		var chars = CHARS, uuid = new Array(36), rnd = 0, r;
		for ( var i = 0; i < 36; i++) {
			if (i == 8 || i == 13 || i == 18 || i == 23) {
				uuid[i] = '-';
			} else if (i == 14) {
				uuid[i] = '4';
			} else {
				if (rnd <= 0x02)
					rnd = 0x2000000 + (Math.random() * 0x1000000) | 0;
				r = rnd & 0xf;
				rnd = rnd >> 4;
				uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
			}
		}
		return uuid.join('');
	};
	Math.uuidCompact = function() {
		return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g,
				function(c) {
					var r = Math.random() * 16 | 0, v = c == 'x' ? r
							: (r & 0x3 | 0x8);
					return v.toString(16);
				});
	};
})();
/* 序列化表单为对象格式,控件名称为属性名,值为属性值 */
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
}
/*序列化表单为json字符串,控件名称为key,控件值为value*/
$.fn.serializeJson=function(){
	var obj=this.serializeObject();
	return $.toJSON(obj);
}
$.fn.setForm = function(jsonValue) {  
    var obj=this;  
    $.each(jsonValue, function (name, ival) {  
        var $oinput = obj.find("input[name=" + name + "]");   
        if ($oinput.attr("type")== "radio" || $oinput.attr("type")== "checkbox"){  
             $oinput.each(function(){  
                 if(Object.prototype.toString.apply(ival) == '[object Array]'){//是复选框，并且是数组  
                      for(var i=0;i<ival.length;i++){  
                          if($(this).val()==ival[i])  
                             $(this).attr("checked", "checked");  
                      }  
                 }else{  
                     if($(this).val()==ival)  
                        $(this).attr("checked", "checked");  
                 }  
             });  
        }else if($oinput.attr("type")== "textarea"){//多行文本框  
            obj.find("[name="+name+"]").html(ival);  
        }else{  
             obj.find("[name="+name+"]").val(ival).attr('value',ival);   
        }  
   });
}