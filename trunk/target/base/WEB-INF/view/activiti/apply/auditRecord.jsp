<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>审批记录</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript" src="${path}/styles/js/echarts.min.js"></script>
	<script type="text/javascript">
	$(function(){
		sen.ajax({
			url:'/act/apply/info/getActivitiRecords1',
			data:{applyid:'${param.id}'},
			success:function(d){
				initCharts(d);
			}
		});
	});
	function initCharts(d){
		var activitiRecordState = ['','待审','通过','复议','否决','取消'];
		var activitiOptionResult = ['','同意','复议','否决','取消'];
		var x1 = 210,//环节信息框宽度
			x2=250,//审批记录框宽度
			x3=70,//框间隔宽度
			y1=110,//环节信息框高度
			y2=90,//审批记录框高度（不含签名图片）
			y3=70,//框间隔高度
			y4=80,//签名图片格外高度
			x=50,//起始框x轴
			y=30,//起始框y轴
			n=0,//单环节对应审批记录框最大个数
			temp,//审批记录框是否含有签名
			scatterData=[],//环节信息框和审批记录框数据集合
			linesData=[],//框之间线条数据结婚
			imgCss = {},//签名路径样式
			imgObj = {},//审批记录id对应签名路径
			_x = 0,//签名路径样式名称序号（自增）
			myChart,//chart图形实例
			option;//chart图形配置属性
		for(var i=0;i<d.length;i++){
			temp = false;
			d[i].x = x;
			d[i].y = y;
			d[i].w = x1;
			d[i].h = y1;
			d[i].value = [d[i].x+d[i].w/2, d[i].y+d[i].h/2];
			d[i].type = 'record';
			scatterData.push(d[i]);
			for(var j=0;j<d[i].opts.length;j++){
				d[i].opts[j].x = x+x1+x3*(j+1)+x2*j;
				d[i].opts[j].y = y;
				d[i].opts[j].w = x2;
				d[i].opts[j].h = y2+(d[i].opts[j].sign_path?y4:0);
				n = d[i].opts.length>n?d[i].opts.length:n;
				if(d[i].opts[j].sign_path){
					temp = true;
					imgObj[d[i].opts[j].id] = 'img'+_x;
					imgCss['img'+_x] = {
						backgroundColor: {
				            image: sen.imgPath+d[i].opts[j].sign_path
				        },
				        height: 80,
				        width:200,
				        align: 'center'
					};
					_x++;
				}
				d[i].opts[j].value = [d[i].opts[j].x+d[i].opts[j].w/2, d[i].opts[j].y+d[i].opts[j].h/2];
				d[i].opts[j].type = 'option';
				if(d[i].opts[j].add_user == d[i].json){
					d[i].opts[j].leader = true;
				}
				scatterData.push(d[i].opts[j]);
				linesData.push(getLine([[x+x1+j*(x2+x3),y+y1/2],[x+x1+j*(x2+x3)+x3,y+y1/2]],2));
			}
			if(i+1 < d.length){
				linesData.push(getLine([[x+x1/2,y+y1],[x+x1/2,y+y1+(temp?y4:y3)]],1));
			}
			y = y+y1+(temp?y4:y3);
		}
		x = x+x1+(x2+x3)*n+30;
		if($('body').width() < x){
			$('.echarts').css({width: x});
		}else{
			$('.echarts').css({width: '100%'});
			x = $('body').width();
		}
		if($('body').height() < y){
			$('.echarts').css({height: y});
		}else{
			$('.echarts').css({height: '100%'});
			y = $('body').height();
		}
		myChart = echarts.init($('.echarts')[0]);
		option = {
			backgroundColor: '#F3F3F3',
			tooltip:{
				trigger:'item'
			},
			grid: {
				left: -1,
				right: 0,
				top: -1,
				bottom: 0
			},
			xAxis: {
				type: 'value',
				position: 'top',
				splitLine: {
					show: false
				},
				axisLabel: {
					show: false
				},
				max: x,
				min: 0
			},
			yAxis: {
				type: 'value',
				inverse: true,
				splitLine: {
					show: false
				},
				axisLabel: {
					show: false
				},
				max: y,
				min: 0
			},
			series:[{
				type: 'scatter',
				symbol: 'rect',
				symbolSize: function(value, params) {
					return [params.data.w, params.data.h];
				},
				itemStyle: {
					normal: {
						//shadowBlur: 10,
						//shadowColor: 'rgba(120, 36, 50, 0.5)',
						//shadowOffsetY: 5,
						color: function(params) {
							if(params.data.type == 'record' && params.data.auditing_result == '1') {
								return '#faa';
							}
							return '#6DC6F2';
						}
					}
				},
				tooltip:{
					formatter:function(params){
						if(params.data.type == 'option' && params.data.options){
							return '<div style="max-width:'+x2+'px;white-space: normal;">'+params.data.options+'</div>';
						}else if(params.data.type == 'record' && params.data.trs && params.data.trs.length>0){
							var d = params.data.trs,h = [];
							h.push('<div class="sen-apply-transfer-records"><div>移交记录</div>');
							h.push('<table>');
							h.push('<tr><td>序号</td><td>移交人员</td><td>接收人员</td><td>移交时间</td></tr>');
							for(var i=0;i<d.length;i++){
								h.push('<tr><td>'+(i+1)+'</td><td>'+d[i].transfer_nickname+'</td><td>'+d[i].receive_nickname+'</td><td>'+d[i].add_time+'</td></tr>');
							}
							h.push('</table></div>');
							return h.join('');
						}
					}
				},
				label: {
					normal: {
						show: true,
						position: 'insideTop',
						formatter: function(params) {
							var arr = [];
							if(params.data.type == 'record'){
								if(params.data.trs && params.data.trs.length>0){
									arr.push('{title|'+params.data.tachename+'(移)}{abg|}');
								}else{
									arr.push('{title|'+params.data.tachename+'}{abg|}');
								}
								arr.push('{text|开始时间：'+params.data.start_time+'}');
								if(params.data.end_date){
									arr.push('{text|结束时间：'+params.data.end_date+'}');
								}
								arr.push('{text|环节状态：'+activitiRecordState[params.data.auditing_result]+'}');
							}else{
								if(params.data.leader){
									arr.push('{option|审批人(牵头人)：'+params.data.nickname+'('+params.data.username+')}');
								}else{
									arr.push('{option|审批人：'+params.data.nickname+'('+params.data.username+')}');
								}
								arr.push('{option|审批时间：'+params.data.add_time+'}');
								arr.push('{option|审批结果：'+activitiOptionResult[params.data.result]+'}');
								arr.push('{option|审批意见：'+beautySub(iGetInnerText(params.data.options),10)+'}');
								if(params.data.sign_path){
									arr.push('{'+imgObj[params.data.id]+'|}');
								}
							}
							return arr.join('\n');
						},
						rich:$.extend({
							title: {
	                            color: '#fff',
	                            align: 'center',
	                            width:x1
	                        },
	                        abg: {
	                            backgroundColor: '#007CB8',
	                            width: '100%',
	                            align: 'right',
	                            height: 25
	                        },
	                        text:{
	                        		align: 'center',
	                        		color:'#000',
	                        		width:x1,
	                        		height: 25
	                        },
	                        option:{
	                        		align: 'left',
	                        		color:'#000',
	                        		width:x2,
	                        		padding:[0,0,0,20],
	                        		height: 20
	                        }
						},imgCss)
					}
				},
				data: scatterData,
			}].concat(linesData)
		};
		myChart.setOption(option, true);
		
		
		function iGetInnerText(testStr) {
			if(testStr){
				var resultStr = testStr.replace(/\ +/g, ""); //去掉空格
			    resultStr = testStr.replace(/[ ]/g, "");    //去掉空格
			    resultStr = testStr.replace(/[\r\n]/g, ""); //去掉回车换行
			    return resultStr;
			}
			return '';
		}
		function beautySub(str, len) {
		    var reg = /[\u4e00-\u9fa5]/g,    //专业匹配中文
		        slice = str.substring(0, len),
		        chineseCharNum = (~~(slice.match(reg) && slice.match(reg).length)),
		        realen = slice.length*2 - chineseCharNum;
		    return str.substr(0, realen) + (realen < str.length ? "..." : "");
		}
		function getLine(coords,type) {
			return {
				name: '',
				type: 'lines',
				coordinateSystem: 'cartesian2d',
				symbol: ['none', 'arrow'],
				symbolSize: 10,
				zlevel: 4,
				effect: {
					show: false,
					period: 6,
					trailLength: 0.7,
					color: type==1?'#007CB8':'#6DC6F2',
					symbolSize: 3
				},
				lineStyle: {
					normal: {
						color: type==1?'#007CB8':'#6DC6F2',
						width: 1,
						curveness: 0
					}
				},
				data: [{
					coords: coords
				}]
			};
		}
	}
	</script>
</head>
<body>
	<div class="echarts"></div>
</body>
</html>