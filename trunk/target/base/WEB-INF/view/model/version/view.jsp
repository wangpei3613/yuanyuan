<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>指标数据查看</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	$(function(){
		getData('${param.id}'); 
	});
	//加载评级指标数据
	function getData(id){
		sen.ajax({
			url:'/model/index/version/getCalcData',
			data:{caseid:id},
			success:function(d){
				if(d && d.length>0){
					initIndex(d);
				}else{
					sen.alert('暂无评级指标数据');
				}
			}
		});
	}
	//处理评级指标数据
	function initIndex(d){
		var obj = {},h = [];
		for(var i=0;i<d.length;i++){
			obj[d[i].code] = d[i];
		}
		var index1 = obj['INDEX_1'],index2 = obj['INDEX_2'],index3 = obj['INDEX_3'],
			index4 = obj['INDEX_4'],index5 = obj['INDEX_5'],index6 = obj['INDEX_6'];
		if(index1){
			h.push('<div class="easyui-panel" title="'+index1.name+'" style="border: 0">');
			h.push('<table class="sen-view-table">');
			h.push('<tr><td style="width:3%;">大项</td>');
			h.push('<td style="width:3%;">序号</td>');
			h.push('<td style="width:10%;">项目</td>');
			h.push('<td style="width:60%;">内容</td>');
			h.push('<td style="width:5%;">满分</td>');
			h.push('<td style="width:14%;">指标</td>');
			h.push('<td style="width:5%;">得分</td></tr>');
			if(index1.children && index1.children.length>0){
				var t1,t2,num = sum1 = sum2 = 0;
				for(var i=0;i<index1.children.length;i++){
					t1 = index1.children[i];
					if(t1.children && t1.children.length>0){
						h.push('<tr><td rowspan="'+(t1.children.length+1)+'">'+t1.name+'</td>');
						var temp1 = temp2 = 0;
						for(var j=0;j<t1.children.length;j++){
							t2 = t1.children[j];
							if(j != 0){
								h.push('<tr>');
							}
							h.push('<td>'+(++num)+'</td>');
							h.push('<td>'+t2.name+'</td>');
							h.push('<td>'+t2.content+'</td>');
							h.push('<td>'+getNumberStr(t2.maxscore)+'</td>');
							h.push('<td>'+calcValue(t2)+'</td>');
							h.push('<td>'+getNumberStr(t2.calcValue)+'</td></tr>');
						   	sum1 += getNumber(t2.maxscore);
							sum2 += getNumber(t2.calcValue);
							temp1 += getNumber(t2.maxscore);
							temp2 += getNumber(t2.calcValue);		
						}
						h.push('<tr>');
						h.push('<td colspan="2">小计</td>');
						h.push('<td></td>');
						h.push('<td>'+temp1+'</td>');
						h.push('<td></td>');
						h.push('<td>'+temp2+'</td></tr></tr>');
					}
				}
			}
			h.push('<tr><td colspan="4">合计</td>');
			h.push('<td>'+sum1+'</td>');
			h.push('<td>'+(function(d1,d2){
					var temp = '';
					if(d1 && d1.children && d1.children.length>0){
						temp = d1.children[0].name+'：'+d1.children[0].calcValue;
					}
					if(d2 && d2.children && d2.children.length>0){
						if(temp){
							temp += '；';
						}
						temp += d2.children[0].name+'：'+d2.children[0].calcValue;
					}
					return temp;
   				})(index2,index3)+'</td>');
			h.push('<td>'+sum2+'</td></tr>');
			h.push('</table></div>');
		}
		if(index4){
			if(index4.children && index4.children.length>0){
				var t1,sum1 = sum2 = 0;
				h.push('<div class="easyui-panel" title="'+index4.name+'" style="border: 0">');
				h.push('<table class="sen-view-table">');
				h.push('<tr><td style="width:6%;">序号</td>');
				h.push('<td style="width:10%;">项目</td>');
				h.push('<td style="width:60%;">内容</td>');
				h.push('<td style="width:5%;">满分</td>');
				h.push('<td style="width:14%;">指标</td>');
				h.push('<td style="width:5%;">得分</td></tr>');
				for(var i=0;i<index4.children.length;i++){
					t1 = index4.children[i];
					h.push('<tr><td>'+(i+1)+'</td>');
					h.push('<td>'+t1.name+'</td>');
					h.push('<td>'+t1.content+'</td>');
					h.push('<td>'+getNumberStr(t1.maxscore)+'</td>');
					h.push('<td>'+calcValue(t1)+'</td>');
					h.push('<td>'+getNumberStr(t1.calcValue)+'</td></tr>');
				   	sum1 += getNumber(t1.maxscore);
					sum2 += getNumber(t1.calcValue);
				}
				h.push('<tr><td colspan="2">小计</td>');
				h.push('<td></td>');
				h.push('<td>'+sum1+'</td>');
				h.push('<td>'+(function(d){
					var temp = '';
					if(d && d.children && d.children.length>0){
						temp = d.children[0].name+'：'+d.children[0].calcValue;
					}
					return temp;
				})(index5)+'</td>');
				h.push('<td>'+sum2+'</td></tr>');
				h.push('</table></div>');
			}
		}
		$('body').html(h.join('')); 
		$.parser.parse();
	}
	function calcValue(d){
		var parameterValue = '',v = {},value = '';
		if(d.argument && $.trim(d.argument)){
			if(d.parameters && d.parameters.length>0){
				for(var i=0;i<d.parameters.length;i++){
					v[d.parameters[i].code] = getNumber(d.parameters[i].calcValue);
				}
			}
			with(v){
				parameterValue = toFixed(eval($.trim(d.argument)));
			}
		}else if(d.parameters && d.parameters.length>0){
			parameterValue = d.parameters[0].calcValue;
		}
		if(d.category == '1'){//定性
			if(parameterValue && d.conversions && d.conversions.length>0){
				for(var i=0;i<d.conversions.length;i++){
					if(d.conversions[i].code == parameterValue){
						value = d.conversions[i].name;
						break;
					}
				}
			}
		}else{
			value = parameterValue;
		}
		if(value=='null' || value=='undefined' || !value){
			value = '';
		}
		return value;
	}
	function getNumber(d){
		if(!isNaN(d)){
			return Number(d);
		}
		return 0;
	}
	function getNumberStr(d){
		if(!isNaN(d)){
			return String(Number(d));
		}
		return '0';
	}
	function toFixed(d){
		if(!isNaN(d)){
			return Number(d).toFixed(2);
		}
		return d;
	}
	</script>
</head>
<body>
</body>
</html>