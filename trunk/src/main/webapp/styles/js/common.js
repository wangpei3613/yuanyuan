;(function(){
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
	};
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
	};
	
	if(typeof JSON === 'undefined'){
		$('head').append('<script type="text/javascript" src="'+sen.path+'/styles/js/json2.js" />');
	}
	window.log = console.log
})();
//ajax拦截 权限校验
(function(){
	hookAjax({
	    onreadystatechange:function(xhr){
	    		if(xhr.xhr.readyState==4 && xhr.xhr.status==401){
	    			layer.win({
  					  title: '快捷登陆',
  					  area: ['800px', '600px'],
  					  content: sen.path,
	  					success:function(layero){
	  					  win = layero.find('iframe')[0].contentWindow;
	  					  win.flag = 1;
	  				  }
  				});
	    		}else if(xhr.xhr.readyState==4 && xhr.xhr.status==460){
	    			sen.alert('您没有权限操作，有问题请联系管理员！');
	    		}
	    }
	});
})();
//easyui扩展
(function(){
	$.extend($.fn.combobox.defaults,{panelHeight:'auto',editable:false});
	$.ajaxSetup({
		cache: false, 
		headers: {
			disposableToken:sen.disposableToken
		},
		xhrFields: {
			withCredentials: true 
		},
		complete:function(response){
			sen.refreshDisposableToken(response.getResponseHeader('disposableToken'));
		},
		error:function(response){
			switch (response.status) {
			case 0:
				sen.alert('请求超时或通信失败, 请检查网络');
				break;
			case 404:
				sen.alert('请求地址不存在');
				break;
			case 450:
				sen.alert(JSON.parse(response.responseText).error);
				break;
			case 460:
				break;
			case 401:
				break;
			case 402:
				break;	
			default:
				sen.alert('未知错误');
				break;
			}
		}
	});
		
	$.fn.datebox.defaults.editable = false;
	$.fn.combogrid.defaults.editable = false;
	$.extend($.fn.datagrid.methods,{
		selectOne:function(me){
			var num = me.datagrid('getSelections').length;
			if(num == 1){
				return true;
			}else{
				sen.alert('请选择一条数据!');
			}
			return false;
		},
		exportExcel:function(me,obj){
			//拼接查询条件
			var params = {};
			var options = me.data().datagrid.options;
			var queryParams = options.queryParams;
			var formQueryParams = $(options.formEl).serializeObject();
			params = $.extend(queryParams,formQueryParams);
			//获取排序
			var sortField = options.sortName;
			var sortOrder = options.sortOrder;
			//拼接列
			var columns =  options.columns[0];
			if(options.frozenColumns.length > 0){
				columns =  options.frozenColumns[0].concat(options.columns[0]);
			}
			//创建动态form并提交
			var form = $("<form></form>")
			form.attr("action",sen.path+"/excelExport/export");
			form.attr("method","post");
			var paramsInput = $("<input name='params' type='hidden'/>");
			paramsInput.attr("value",JSON.stringify(params));
			form.append(paramsInput);
			var sortFieldInput = $("<input name='sidx' type='hidden'/>");
			sortFieldInput.attr("value",sortField);
			form.append(sortFieldInput);
			var sortOrderInput = $("<input name='sord' type='hidden'/>");
			sortOrderInput.attr("value",sortOrder);
			form.append(sortOrderInput);
			var columnsInput = $("<input name='columns' type='hidden'/>");
			columnsInput.attr("value",JSON.stringify(columns));
			form.append(columnsInput);
			var excelNameInput = $("<input name='excelName' type='hidden'/>");
			excelNameInput.attr("value",obj.excelName);
			form.append(excelNameInput);
			var serviceNameInput = $("<input name='serviceName' type='hidden'/>");
			serviceNameInput.attr("value",obj.serviceName);
			form.append(serviceNameInput);
			var methodNameInput = $("<input name='methodName' type='hidden'/>");
			methodNameInput.attr("value",obj.methodName);
			form.append(methodNameInput);
			$(document.body).append(form);
			form.submit();
			form.remove();
		}
		
	});
	$.fn.grid = function(obj){
		if(arguments.length == 1 && obj && typeof obj == 'object'){
			obj = $.extend({
				toolbar:'#sen-toolbar',
				formEl:'#sen-form',
				initQueryEvent:true,
				rownumbers:true,
				pageSize:20, 
				pagination:true,
				fitColumns:true,
				striped:true,
				idField:'id',
				fit:true,
				loadFilterFn:function(data){
					return data;
				},
				loadFilter:function(d){
					if(d && d.data){
						d.data.total = d.data.records;
						return obj.loadFilterFn(d.data);
					}else{
						d = {pageSize:obj.pageSize,pageIndex:1,records:0,total:0,rows:[]}
					}
					return obj.loadFilterFn(d);
				},
				singleSelect:true,
				multiSort:false//是否多字段排序，使用true时确保排序字段中没有逗号
			},obj);
			var dictTypes = [];
			for(var i=0;i<obj.columns[0].length;i++){
				if(obj.columns[0][i].dictUrl){
					sen.ajax({
						url:obj.columns[0][i].dictUrl,
						async:false,
						success:function(_d){
							obj.columns[0][i].dictData = _d;
						}
					});
				}else if(obj.columns[0][i].dictType){
					dictTypes.push(obj.columns[0][i].dictType);
				}
			}
			if(dictTypes.length > 0){
				sen.ajax({
					url:'/sys/getDictApp',
					data:{dictCodes:dictTypes.join(',')},
					async:false,
					success:function(_d){
						for(var i=0;i<obj.columns[0].length;i++){
							if(obj.columns[0][i].dictType){
								obj.columns[0][i].dictData = _d[obj.columns[0][i].dictType];
							}
						}
					}
				});
			}
			for(var i=0;i<obj.columns[0].length;i++){
				if(obj.columns[0][i].dictData && !obj.columns[0][i].formatter){
					obj.columns[0][i].formatter = function(value){
						var _data = this.dictData, vals = [];
						for(var j=0;j<_data.length;j++){
							if(this.dictMulti){
								if((','+value+',').indexOf(','+_data[j].code+',') > -1){
									vals.push(_data[j].name);
								}
							}else{
								if(_data[j].code == value){
									vals.push(_data[j].name);
								}
							}
						}
						return vals.join(',');
					};
				}
			}
			var datagrid = $(this).datagrid(obj),formEl = $(obj.formEl);
			if(obj.initQueryEvent && formEl.length > 0){
				formEl.find('a[iconCls=icon-search]').on('click',function(){
					datagrid.datagrid('clearSelections');
					datagrid.datagrid('load');
				});
				formEl.find('a[iconCls=icon-undo]').on('click',function(){
					formEl.form('reset');
				});
			}
			return datagrid;
		}else{
			return $(this).datagrid.apply($(this),arguments);
		}
	};
	$.fn.tgrid = function(obj){
		if(arguments.length == 1 && obj && typeof obj == 'object'){
			obj = $.extend({
				toolbar:'#sen-toolbar',
				rownumbers:true,
				fitColumns:true,
				idField:'id',
				fit:true,
				lines:true,
				singleSelect:true,
				multiSort:false
			},obj);
			obj.multiSort = false;
			var dictTypes = [];
			for(var i=0;i<obj.columns[0].length;i++){
				if(obj.columns[0][i].dictUrl){
					sen.ajax({
						url:obj.columns[0][i].dictUrl,
						async:false,
						success:function(_d){
							obj.columns[0][i].dictData = _d;
						}
					});
				}else if(obj.columns[0][i].dictType){
					dictTypes.push(obj.columns[0][i].dictType);
				}
			}
			if(dictTypes.length > 0){
				sen.ajax({
					url:'/sys/getDictApp',
					data:{dictCodes:dictTypes.join(',')},
					async:false,
					success:function(_d){
						for(var i=0;i<obj.columns[0].length;i++){
							if(obj.columns[0][i].dictType){
								obj.columns[0][i].dictData = _d[obj.columns[0][i].dictType];
							}
						}
					}
				});
			}
			for(var i=0;i<obj.columns[0].length;i++){
				if(obj.columns[0][i].dictData && !obj.columns[0][i].formatter){
					obj.columns[0][i].formatter = function(value){
						var _data = this.dictData;
						for(var j=0;j<_data.length;j++){
							if(_data[j].code == value){
								return _data[j].name;
							}
						}
					};
				}
			}
			return $(this).treegrid(obj);
		}else{
			return $(this).treegrid.apply($(this),arguments);
		}
	};
})();
//layer 扩展
(function(){
	layer.config({
	  skin: 'sen-layer-skin'
	});
	layer.win = function(obj){
		var win;
		obj = $.extend({
			type:2,
			title:false,
			topWin:true,
			moveOut:true
		},obj);
		if(obj.topWin){
			win = top.layer.open(obj)
		}else{
			win = layer.open(obj)
		}
		return win;
	};
})();
//自定义sen工具
(function(){
	window.sen = $.extend(window.sen,{
		ajax:function(obj){
			var w,title;
			//需要禁用的按钮，防止重复点击，可以传选择器或jquery对象，可以传数组：第一个为选择器或jquery对象，第二个为禁用时显示的文本
			var button = obj.button,$button,buttonVal,disabledButtonVal;
			obj = $.extend({
				type:'post',
				loadding:true,//true显示等待对话框，为字符串则额外显示该字符串
				loaddingEle:null,//等待对话框所处元素，空则为body元素
				data:{},
				async:true,
				traditional: true,
				contentType:'application/x-www-form-urlencoded',
				processData:true,
				allData:false//success回调参数值，true返回所有数据，false只返回data数据
			},obj);
			if(button){
				if($.isArray(button)){
					if(button.length > 0){
						$button = $(button[0]);
						buttonVal = $button.text();
						if(button.length > 1){
							disabledButtonVal = String(button[1]);
						}
					}
				}else{
					$button = $(button);
					buttonVal = $button.text();
				}
				if($button){
					$button.prop('disabled',true);
					disabledButtonVal && $button.text(disabledButtonVal);
				}
			}
			if(obj.loadding){
				if(typeof obj.loadding === 'string'){
					title = obj.loadding;
				}
				w = sen.loading(title, obj.loaddingEle);
			}
			
			return $.ajax({
				url:sen.path+obj.url,
				type:obj.type,
				data:$.extend(obj.data,{_dc:$.now()}),
				async:obj.async,
				contentType:obj.contentType,
				traditional: obj.traditional,
				processData:obj.processData,
				headers:{
					disposableToken:sen.disposableToken
				},
				success:function(d){
					if(obj.success && $.isFunction(obj.success)){
						if(obj.allData){
							obj.success(d);
						}else{
							obj.success(d.data);
						}
					}
				},
				error:function(response){
					switch (response.status) {
					case 0:
						sen.alert('请求超时或通信失败, 请检查网络');
						break;
					case 404:
						sen.alert('请求地址不存在');
						break;
					case 450:
						if(obj.error && $.isFunction(obj.error)){
							obj.error(JSON.parse(response.responseText), response);
						}else{
							sen.alert(JSON.parse(response.responseText).error);
						}
						break;
					case 460:
						break;
					case 401:
						break;
					case 402:
						break;	
					default:
						if(obj.error && $.isFunction(obj.error)){
							obj.error(null,response);
						}else{
							sen.alert('未知错误');
						}
						break;
					}
				},
				complete:function(response){
					sen.refreshDisposableToken(response.getResponseHeader('disposableToken'));
					if($button){
						$button.prop('disabled',false).text(buttonVal);
					}
					if(obj.loadding){
						w();
					}
					if(obj.complete && $.isFunction(obj.complete)){
						obj.complete(response);
					}
				}
			});
		},
		refreshDisposableToken:function(disposableToken){
			if(disposableToken && disposableToken!='null'){
				sen.disposableToken = disposableToken;
			}
		},
		loading:function(str,ele){   
			$ele = $(ele || 'body');
			var str,$mask = $("<div class=\"datagrid-mask\"></div>"),$maskMsg = $("<div class=\"datagrid-mask-msg\"></div>"),fn = function(){
				$mask.remove();   
				$maskMsg.remove();
			};
			$mask.css({display:"block",width:"100%",height:$ele.height(),zIndex:99});
			$maskMsg.html(str||'处理中，请稍候...').css({display:"block",left:($ele.outerWidth(true) - 110)/2,top:($ele.height() - 45)/2,zIndex:99});
			$ele.append($mask);
			$ele.append($maskMsg);
			return fn;
		 },
		 alert:function(content, options, yes){
			 options = $.extend({icon: 2},options);
			 top.layer.alert.call(top,content, options, yes);
		 },
		 msg:function(content, options, end){
			 top.layer.msg.call(top,content, options, end);
		 },
		 confirm:function(content,fn,options){
			 options = $.extend({icon: 3, title:'提示'},options);
			 top.layer.confirm(content,options,function(index){
				 fn && $.isFunction(fn) && fn(index);
				 top.layer.close(index);
			 });
		 },
		 createFrame:function(url){
			 var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			return s;
		 },
		 signPad:function(fn,data){
			 if(sen.isformal){
				 layer.win({
				  title: '授权书签字',
				  area: ['508px', '300px'],
				  content: sen.path+'/ope/apply/creditQuery/toCreditSign',
				  btn:['保存','重签'],
				  yes:function(index, layero){
				    win = layero.find('iframe')[0].contentWindow;
				    if(win.getBase64()){
				    	fn && $.isFunction(fn) && fn(win.getBase64(),data);
				    	top.layer.close(index);
				    }else{
				    	sen.alert('请签名');
				    }
					return false;
				  },
				  btn2:function(index, layero){
					win = layero.find('iframe')[0].contentWindow,
				    win.doClear();
					return false;
				  }
				});
			 }else{
				 fn && $.isFunction(fn) && fn('data:image/gif;base64,R0lGODlhyABQAMQAAP///wAAAAAAAN/f35+fn19fXz8/P7+/v39/fx8fHy8vLxsbG2NjY4uLiw8PDxcXF1NTU6enpwsLC8PDwzc3N29vbycnJzs7O09PT0dHR3d3dwcHBwMDAxMTE4+PjyMjIywAAAAAyABQAAAF/yAgjmRpnmiqrmzrvnAsz3Rt33iu73zv/8CgcEgsGo/IZK1gUDqf0FtAQI1ar1jTtJrtepXb5ndMRk0ZNqqAUG671Wmue/7dHmpwwICAKCAIA10IdF1bAjQIaoqLVAGBUFuEWIZqAWwulIyMkHmSUZmKFS2gmp1Jhp5XpGoWLIoBq1SDYGoJqVEDioEHFIsNKQelh1gJirefarMjwZVmag6bVgW1x8hyJRvGWnLQ2k+UJwZT1UTUKBpqEiamid5JA6glsO7kPdNVBSqKysUCd+umjMAzJY5Rvno/tuy5BjBPpBQPGA4ZSCVQgVgIE+YJSEIXgDBeKAqIEKtixh79/P+J4CjiHpeHhV4ZBEDvJA5vEFjGyyXRmqZ/H1narBFvBL2UHYdlWdVQzFAcPJUmvVbp0hhQtrb1fDqjKAkGnTIFIMPIaYlkXHMoWJMCbKNYj7osOiiPitW05QymVAPBC4FXcYNuxQtkUUMqorowSyaScBJzJbpR6fBFWFbHRZCmWDS2S4WfmI1sUZZCsoAMXjIw6hw6SDupKz6rQb1Uk4bWQBTCiCrAQRZdHnH3EBo7OCeqVDwI37HlbgzZAhw9WXxi8HIYr282o8X2hPPrXYmr2DM1+inx4G+gH4EAVianvIHmFRA4PfNX7gMkMHCxJBW6Iqy1Xg5bXGYfD8IkqEn/fSKYNkR2B/bwmoKLGIDAAAf0oU9NPGwBYIQE2qXHAQTw0QcTpMmwCIM53PMhiDcwI98JiWxQA2+whZgjCwQUkEACsFjIIngwpVAkChftCIBkOuiXpJIltOcfXxHKwsJoK9iBwl/f0RANCgfMQ6Fh9vUzwjgneGWCS0aQaUKYYwrzYmsETCFGVCdQh0JBApglhCJ+CsZIiihAtlyRasy40n8n8HaEN/3NBIMagbZmCpNaLZAmWqI5pEmlrlChaGhaTvUdhCSAY8Reg3wpA5vgcaTmorCp2iaZA6pQIHj9BIqnViMAqUaXQYBiFKiY5PrUkUZZWcI9HAgqgIFEsKns/5XXnsQMqB2wVAqhQzw0bA5/WUcYs1OZUK6bR2AJwLo6Utvar85cU+cio86XqrktwHqdhyt00t4+T/Rj4D04zNoauiUsJqZJUHBExQM2GJDtSQofdrEP6DLcAr3/QmxkSfk5icABQ+7AUjs08ElsaOINHKcm+Rnw8gz9EEsFBe/CACe/eBX188xEc4ZsCzJvtlrNBjTttLAcLnzNlDan/OaErsYAcKFFl5IevOUJUIDVM7gEdL26dp21cKU+drbSKpi9SAJ+EIAyjGf1aWmieI/ydj0Z9y1CMSDhtnHIhx/z2ZyC0/p3445NSDbkuC0GJeXXRY155iJv7vnnoIcu+gXopHseAgA7',data);
			 }
		 },
		 //申请提交
		 applySubmit:function(applyid,fn){
			 sen.ajax({
				  url:'/act/apply/info/getApplySubmitData',
				  data:{applyid:applyid},
				  success:function(d){
					  handleActiviti(d,fn);
				  }
		  	});
		 },
		 //申请审批
		 applyAudit:function(applytacheid,fn){
			 sen.ajax({
				  url:'/act/apply/info/getApplyAuditData',
				  data:{applytacheid:applytacheid},
				  success:function(d){
					  handleActiviti(d,fn);
				  }
		  	});
		 },
		 //申请移交
		 applyTransfer:function(applytacheid,transferUserid,fn){
			 sen.ajax({
				  url:'/act/apply/info/getApplyTransferData',
				  data:{applytacheid:applytacheid,transferUserid:transferUserid},
				  success:function(d){
					  handleTransfer(d,fn);
				  }
		  	});
		 },
		 selectIcon:function(fn){//选择图标
			 layer.win({
 				  title: '请选择图标',
 				  area: ['800px', '600px'],
 				  content: sen.path+'/system/module/auth/toViewIcons',
 				  success:function(layero){
 					  win = layero.find('iframe')[0].contentWindow;
 					  win.$(function(){
 						  win.init(fn);
 					  });
 				  }
 			});
		 },
		 toNumber:function(str){//字符串转数字，空返回0
			 if(isNaN(str)){
					return 0;
				}
			return Number(str);
		 },
		 arabia_To_SimplifiedChinese:function(Num){//阿拉伯小写数字转大写金额
			    for (i = Num.length - 1; i >= 0; i--) {
			        Num = Num.replace(",", "")//替换Num中的“,”
			        Num = Num.replace(" ", "")//替换Num中的空格
			    }    
			    if (isNaN(Num)) { //验证输入的字符是否为数字
			        return;
			    }
			    //字符处理完毕后开始转换，采用前后两部分分别转换
			    part = String(Num).split(".");
			    newchar = "";
			    //小数点前进行转化
			    for (i = part[0].length - 1; i >= 0; i--) {
			        if (part[0].length > 10) {
			            return "";
			        }
			        tmpnewchar = ""
			        perchar = part[0].charAt(i);
			        switch (perchar) {
			            case "0":  tmpnewchar = "零" + tmpnewchar;break;
			            case "1": tmpnewchar = "一" + tmpnewchar; break;
			            case "2": tmpnewchar = "二" + tmpnewchar; break;
			            case "3": tmpnewchar = "三" + tmpnewchar; break;
			            case "4": tmpnewchar = "四" + tmpnewchar; break;
			            case "5": tmpnewchar = "五" + tmpnewchar; break;
			            case "6": tmpnewchar = "六" + tmpnewchar; break;
			            case "7": tmpnewchar = "七" + tmpnewchar; break;
			            case "8": tmpnewchar = "八" + tmpnewchar; break;
			            case "9": tmpnewchar = "九" + tmpnewchar; break;
			        }
			        switch (part[0].length - i - 1) {
			            case 0: tmpnewchar = tmpnewchar; break;
			            case 1: if (perchar != 0) tmpnewchar = tmpnewchar + "十"; break;
			            case 2: if (perchar != 0) tmpnewchar = tmpnewchar + "百"; break;
			            case 3: if (perchar != 0) tmpnewchar = tmpnewchar + "千"; break;
			            case 4: tmpnewchar = tmpnewchar + "万"; break;
			            case 5: if (perchar != 0) tmpnewchar = tmpnewchar + "十"; break;
			            case 6: if (perchar != 0) tmpnewchar = tmpnewchar + "百"; break;
			            case 7: if (perchar != 0) tmpnewchar = tmpnewchar + "千"; break;
			            case 8: tmpnewchar = tmpnewchar + "亿"; break;
			            case 9: tmpnewchar = tmpnewchar + "十"; break;
			        }
			        newchar = tmpnewchar + newchar;
			    }   
			    //替换所有无用汉字，直到没有此类无用的数字为止
			    while (newchar.search("零零") != -1 || newchar.search("零亿") != -1 || newchar.search("亿万") != -1 || newchar.search("零万") != -1) {
			        newchar = newchar.replace("零亿", "亿");
			        newchar = newchar.replace("亿万", "亿");
			        newchar = newchar.replace("零万", "万");
			        newchar = newchar.replace("零零", "零");      
			    }
			    //替换以“一十”开头的，为“十”
			    if (newchar.indexOf("一十") == 0) {
			        newchar = newchar.substr(1);
			    }
			    //替换以“零”结尾的，为“”
			    if (newchar.lastIndexOf("零") == newchar.length - 1) {
			        newchar = newchar.substr(0, newchar.length - 1);
			    }
			    return newchar;
		 },
		 zxapply:function(data, fn){
			 layer.win({
				  title: '新增征信查询申请',
				  area: ['400px', '440px'],
				  content: sen.path+'/ope/sync/zx/apply/toEdit',
				  btn:['保存','关闭'],
				  success:function(layero){
					  win = layero.find('iframe')[0].contentWindow;
					  win.$(function(){
						  win.init(data);
					  });
				  },
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/ope/sync/zx/apply/save',
							  data:form.serializeObject(),
							  loaddingEle:layero,
							  success:function(){
								  top.layer.close(index);
								  fn && $.isFunction(fn) && fn();
							  }
						  });
					  }
					  return false;
				  }
				}); 
		 },
		 //文件上传
		 uploadFile:function(opts){
			 opts = $.extend({},opts);
			 var ele = $(opts.ele),input;
			 if(ele.length > 0){
				 input = $('<input type="file" accept="'+opts.accept+'" />');
				 //opts.multiple && input.prop('multiple','multiple');
				 ele.parent().css('position','relative');
				 input.css({
					 top: 0,
				     position: 'absolute',
				     width: ele.outerWidth(),
				     height: ele.outerHeight(),
				     bottom: 0,
				     margin: 'auto',
				     left: ele.offset().left,
				     zIndex: 99999,
				     opacity: 0,
				     cursor: 'pointer'
				 }).on('change',function(){
					 var files = this.files,formData = new FormData();
					 if(opts.accept && opts.accept.split(',').indexOf(files[0].type)==-1){
						 sen.alert('请选择规定格式的文件');
					 }else{
						 formData.append('file',files[0]);
						 sen.ajax({
							 url:opts.uploadUrl,
							 data:formData,
							 processData:false, 
							 contentType:false,
							 success:function(d){
								 opts.success && opts.success(d);
							 }
						 });
					 }
				 });
				 ele.parent().append(input);
			 }
		 },
		 dict:{},
		 dictArray:{},
		 /**
		 * 获取数据字典
		 * @param dictCode 字典编码，多个用逗号分隔
		 */
		getDictData:function(dictCodes){
			if(dictCodes){
				sen.ajax({
					url:'/sys/getDictApp',
					data:{dictCodes:dictCodes},
					async:false,
					success:function(d){
						var obj;
						for(var e in d){
							sen.dictArray[e] = d[e];
							obj = {};
							for(var i=0;i<d[e].length;i++){
								obj[d[e][i].code] = d[e][i].name;
							}
							sen.dict[e] = obj;
						}
					}
				});
			}
		},
		/**
		 * 转换为字符串，去掉null、undefined等字符串
		 */
		toStr:function(str){
			if(!str || 'null' == str || 'undefined' == str){
				return '';
			}
			return str;
		}
	});
	$.parser.onComplete = function(){
		$('.sen-crud-page-toolbar a.easyui-linkbutton').on('click',function(){
			$(this).blur();
		});
	};
	//流程处理方法
	function handleActiviti(d,fn){
		var btn = ['提交','关闭'],win;
		if(d.tache.needsignature == '1'){
			btn = ['提交','签名','关闭'];
		}
		layer.win({
			  title: (d.apply.audit_state=='1' || d.apply.audit_state=='4')?'提交':'审批',
			  area: ['500px', '400px'],
			  content: sen.path+'/act/apply/info/toAudit',
			  btn:btn,
			  success:function(layero){
				  win = layero.find('iframe')[0].contentWindow;
				  win.$(function(){
					  win.init(d);
				  });
			  },
			  yes:function(index, layero){
				  var form = win.$('form'),
				      data = form.serializeObject();
				  if(form.form('validate')){
					  if(!data.sign_data && d.tache.needsignature == '1'){
							sen.alert('请签名');
							return;
					  }
					  data.applytacheid = d.applyTache.id;
					  sen.ajax({
							url:d.saveurl,
							data:data,
							loaddingEle:layero,
							success:function(){
								fn && $.isFunction(fn) && fn();
								top.layer.close(index);
							}
						});
				  }
				  return false;
			  },
			  btn2:function(index, layero){
				  if(d.tache.needsignature == '1'){
					  sen.signPad(function(base64){
						  win.$('#signData').val(base64);
						  win.$('.signImg').html('<img src="'+base64+'"/>');
					  });
				  }else{
					  top.layer.close(index); 
				  }
				  return false;
			  }
		});
	}
	//移交处理方法
	function handleTransfer(d,fn){
		var btn = ['提交','关闭'],win;
		if(d.tache.transfer_sign == '1'){
			btn = ['提交','签名','关闭'];
		}
		layer.win({
			  title: '移交',
			  area: ['500px', '400px'],
			  content: sen.path+'/act/apply/info/toTransfer',
			  btn:btn,
			  success:function(layero){
				  win = layero.find('iframe')[0].contentWindow;
				  win.$(function(){
					  win.init(d);
				  });
			  },
			  yes:function(index, layero){
				  var form = win.$('form'),
				      data = form.serializeObject();
				  if(form.form('validate')){
					  if(!data.sign_data && d.tache.transfer_sign == '1'){
							sen.alert('请签名');
							return;
					  }
					  data.applytacheid = d.applyTache.id;
					  sen.ajax({
							url:d.saveurl,
							data:data,
							loaddingEle:layero,
							success:function(){
								fn && $.isFunction(fn) && fn();
								top.layer.close(index);
							}
						});
				  }
				  return false;
			  },
			  btn2:function(index, layero){
				  if(d.tache.transfer_sign == '1'){
					  sen.signPad(function(base64){
						  win.$('#signData').val(base64);
						  win.$('.signImg').html('<img src="'+base64+'"/>');
					  });
				  }else{
					  top.layer.close(index); 
				  }
				  return false;
			  }
		});
	}
	
//	/**
//	 * 设置form中的数据，根据name
//	 * @param {} formId
//	 * @param {} data
//	 */
//	function easyui_setFormValue(formId,data){
//		$("[name]",$("#"+formId)).each(function(_i){
//			var tmpName = $(this).attr('name');
//			if(data[tmpName] != null){
//				var type = $(this).attr('type');
//				var classs = $(this).attr('class');
//				if($(this).attr("class") == "combo-value"){//可能为select             日期控件还没有遇到
//					//alert($(this).parent().html());
//					//$("#"+tmpName).combobox('select',data[tmpName]);
//					try{
//						$("#"+tmpName).combobox('select',data[tmpName]);
//					}catch(e){}
//					try{
//						$("#"+tmpName).datebox('setValue',data[tmpName]);						
//					}catch(e){}
//					try{
//						$("#"+tmpName).datetimebox('setValue',data[tmpName]);
//					}catch(e){}
//					/*var tmpId = $(this).attr('name').replace(".","_");
//					if(jQuery.inArray(tmpId,datetimeboxArr) != -1){//确定是日期控件
//						$("#"+tmpId).datetimebox('setValue',eval("row."+tmpName));
//					}*/
//				}else if($(this).attr("type") == "checkbox"){//checkbox
//					if(data[tmpName] == "1"){
//						$(this).attr("checked",true);
//					} else{
//						$(this).attr("checked",false);
//					}
//				}else if(type == "radio"){//radio
//					var radios = $(this);
//					$.each(radios,function(){
//						if($(this).val() == data[tmpName]){
//							$(this).attr("checked",true);
//						} else{
//							$(this).attr("checked",false);
//						}
//					});											
//				}else if(type == "hidden"){//目前遇到easyui-numberbox
//					//alert($(this).parent().html());
//					var len = $(this).parent().find("[numberboxname='"+tmpName+"']").length;
////					if(classs){
////						if(classs.indexOf('textbox')>-1){
////							$("input[name="+tmpName+"]").textbox('setValue',data[tmpName]);
////						}else if(classs.indexOf('numberbox')>-1){
////							$("input[name="+tmpName+"]").numberbox('setValue',data[tmpName]);
////						}else if(classs.indexOf('combobox')>-1){
////							$("input[name="+tmpName+"]").combobox('setValue',data[tmpName]);
////						}else{
////							$(this).val(data[tmpName]);
////						}
////						
////					}else{
////						$(this).val(data[tmpName]);
////					}
//					if(len == 1){
//						$("#"+tmpName).numberbox('setValue',data[tmpName]);
//					}else{
//						$(this).val(data[tmpName]);
//					}
//				}else{
//					$(this).val(data[tmpName]);
//				}
//			}
//		});
//	}
})();