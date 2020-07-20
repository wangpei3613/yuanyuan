<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>档案室管理</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript" src="${path}/styles/js/echarts-4.8.0.min.js"></script>
	<style>
	.datagrid .panel-body {border: none;}
	.sen-box-warp{}
	.sen-box-info{}
	.sen-box-info>div{}
	.sen-box-menu{
		margin: 0;
		padding-inline-start: 20px;
		border-top: 1px solid;
	}
	.sen-box-menu>li{
		cursor: pointer;
		margin: 3px 0;
		color: #efff00;
	}
	</style>
	<script type="text/javascript">
	var autoSelectFirst = true, autoSelectSec = false;
	var grid,gridChild;
	var myChart;
	var rackid = '';
	var _x, _y;
	var boxManage;
	$(function(){
		boxManage = $('#boxManage').val()
		myChart = echarts.init($('.echarts')[0])
		myChart.setOption({
			xAxis: {
				min: 0,
				max: 5,
				position: 'top',
				interval: 1,
				name: '列'
			},
			yAxis: {
				min: 0,
				max: 5,
				position: 'left',
				inverse: true,
				interval: 1,
				name: '层'
			}
		}, true);
		_x = myChart.convertToPixel({xAxisIndex: 0}, 5) - myChart.convertToPixel({xAxisIndex: 0}, 0)
		_y = myChart.convertToPixel({yAxisIndex: 0}, 5) - myChart.convertToPixel({yAxisIndex: 0}, 0)
		$('.echarts').hide();
		grid = $('#sen-datagrid').grid({
			url:'${path }/arch/room/getList',
			sortName:'addtime',
		    sortOrder:'desc',
			pagination: false,
			columns:[[
				{field:'room_no',title:'档案室编号',width:100,align:'center',sortable:true},
				{field:'room_name',title:'档案室名称',width:100},
				{field:'room_address',title:'档案室地址',width:100}
			]],
			onSelect:function(index,row){
				if(row.id != $('#roomid').val()){
					$('#roomid').val(row.id);
					gridChild.grid('clearSelections');
					gridChild.grid('load');
					$('.echarts').hide()
				}
			},
			onLoadSuccess:function(data){
				if (autoSelectFirst) {
					autoSelectFirst = false
					if (data && data.rows && data.rows.length>0) {
						autoSelectSec = true
						grid.grid('selectRow', 0)
					}
				}
			}
		});
		gridChild = $('#sen-datagrid-child').grid({
			url:'${path }/arch/room/rack/getList',
			sortName:'addtime',
		    sortOrder:'desc',
			pagination: false,
		    toolbar:'#sen-toolbar-child',
			formEl:'#sen-form-child',
			columns:[[
				{field:'rack_no',title:'档案架编号',width:100,align:'center',sortable:true},
				{field:'rack_layer',title:'层数',width:50,align:'center'},
				{field:'rack_layer_column',title:'每层盒数',width:50,align:'center'}
			]],
			onSelect:function(index,row){
				if(row.id != rackid){
					rackid = row.id;
					loadBox(row)
				}
			},
			onLoadSuccess:function(data){
				if (autoSelectSec) {
					autoSelectSec = false
					if (data && data.rows && data.rows.length>0) {
						gridChild.grid('selectRow', 0)
					}
				}
			}
		});
	});
	function addRoom() {
		layer.win({
			title: '新增档案室',
			area: ['400px', '300px'],
			content: '${path}/arch/room/toEdit',
			btn:['保存','关闭'],
			yes:function(index, layero){
				var win = layero.find('iframe')[0].contentWindow,
						form = win.$('form');
				if(form.form('validate')){
					sen.ajax({
						url:'/arch/room/save',
						data:form.serializeObject(),
						loaddingEle:layero,
						success:function(){
							top.layer.close(index);
							sen.msg('保存成功');
							grid.grid('load');
						}
					});
				}
				return false;
			}
		});
	}
	function editRoom() {
		if(grid.grid('selectOne')){
			layer.win({
				title: '修改档案室',
				area: ['400px', '300px'],
				content: '${path}/arch/room/toEdit?id='+grid.grid('getSelected').id,
				btn:['保存','关闭'],
				yes:function(index, layero){
					var win = layero.find('iframe')[0].contentWindow,
							form = win.$('form');
					if(form.form('validate')){
						sen.ajax({
							url:'/arch/room/save',
							data:form.serializeObject(),
							loaddingEle:layero,
							success:function(){
								top.layer.close(index);
								sen.msg('修改成功');
								grid.grid('load');
							}
						});
					}
					return false;
				}
			});
		}
	}
	function delRoom() {
		if(grid.grid('selectOne')){
			sen.confirm('确定要删除吗?',function(index){
				sen.ajax({
					url:'/arch/room/del',
					data:{id: grid.grid('getSelected').id},
					success:function(){
						sen.msg('删除成功');
						grid.grid('clearSelections');
						grid.grid('load');
					}
				});
			});
		}
	}
	function addRoomRack() {
		var roomid = $('#roomid').val();
		if(roomid){
			layer.win({
				title: '新增档案架',
				area: ['400px', '300px'],
				content: '${path}/arch/room/rack/toEdit?roomid='+roomid,
				btn:['保存','关闭'],
				yes:function(index, layero){
					var win = layero.find('iframe')[0].contentWindow,
							form = win.$('form');
					if(form.form('validate')){
						sen.ajax({
							url:'/arch/room/rack/save',
							data:form.serializeObject(),
							loaddingEle:layero,
							success:function(){
								top.layer.close(index);
								sen.msg('保存成功');
								gridChild.grid('load');
							}
						});
					}
					return false;
				}
			});
		}else{
			sen.alert('请先选择档案室');
		}
	}
	function editRoomRack() {
		if(gridChild.grid('selectOne')){
			layer.win({
				title: '修改档案架',
				area: ['400px', '300px'],
				content: '${path}/arch/room/rack/toEdit?id='+gridChild.grid('getSelected').id,
				btn:['保存','关闭'],
				yes:function(index, layero){
					var win = layero.find('iframe')[0].contentWindow,
							form = win.$('form');
					if(form.form('validate')){
						sen.ajax({
							url:'/arch/room/rack/save',
							data:form.serializeObject(),
							loaddingEle:layero,
							success:function(){
								top.layer.close(index);
								sen.msg('修改成功');
								gridChild.grid('load');
							}
						});
					}
					return false;
				}
			});
		}
	}
	function delRoomRack() {
		if(gridChild.grid('selectOne')){
			sen.confirm('确定要删除吗?',function(index){
				sen.ajax({
					url:'/arch/room/rack/del',
					data:{id: gridChild.grid('getSelected').id},
					success:function(){
						sen.msg('删除成功');
						gridChild.grid('clearSelections');
						gridChild.grid('load');
					}
				});
			});
		}
	}
	function loadBox(row) {
		sen.ajax({
			url:'/arch/room/rack/box/getListByRackid?rackid='+row.id,
			success:function(res){
				initCharts(row, res);
			}
		});
	}
	function initCharts(row, arr) {
		$('.echarts').show()
		arr = arr || []
		var option = {
			title: {
				text: row.rack_no
			},
			xAxis: {
				min: 0,
				max: row.rack_layer_column,
				position: 'top',
				interval: 1,
				name: '列'
			},
			axisLine: {
				symbolOffset: 100
			},
			yAxis: {
				min: 0,
				max: row.rack_layer,
				position: 'left',
				inverse: true,
				interval: 1,
				name: '层'
			},
			tooltip: {
				formatter: function(param) {
					var d = param.data, arr = []
					window.addBox = function() {
						layer.win({
							title: '新增档案盒',
							area: ['400px', '300px'],
							content: '${path}/arch/room/rack/box/toEdit?rackid='+row.id+'&layer='+d.layer+'&column='+d.column,
							btn:['保存','关闭'],
							yes:function(index, layero){
								var win = layero.find('iframe')[0].contentWindow,
										form = win.$('form');
								if(form.form('validate')){
									sen.ajax({
										url:'/arch/room/rack/box/save',
										data:form.serializeObject(),
										loaddingEle:layero,
										success:function(){
											top.layer.close(index);
											sen.msg('保存成功');
											loadBox(row)
										}
									});
								}
								return false;
							}
						});
					}
					window.editBox = function() {
						layer.win({
							title: '修改档案盒',
							area: ['400px', '300px'],
							content: '${path}/arch/room/rack/box/toEdit?id='+d.box.id,
							btn:['保存','关闭'],
							yes:function(index, layero){
								var win = layero.find('iframe')[0].contentWindow,
										form = win.$('form');
								if(form.form('validate')){
									sen.ajax({
										url:'/arch/room/rack/box/save',
										data:form.serializeObject(),
										loaddingEle:layero,
										success:function(){
											top.layer.close(index);
											sen.msg('保存成功');
											loadBox(row)
										}
									});
								}
								return false;
							}
						});
					}
					window.delBox = function() {
						sen.confirm('确定要删除吗?',function(index){
							sen.ajax({
								url:'/arch/room/rack/box/del',
								data:{id: d.box.id},
								success:function(){
									sen.msg('删除成功');
									loadBox(row)
								}
							});
						});
					}
					arr.push('<div class="sen-box-warp">')
					arr.push('<div class="sen-box-info">')
					if (d.box) {
						arr.push('<div>盒编号：'+d.box.box_no+'</div>')
						arr.push('<div>盒卷数：'+d.box.box_num+'</div>')
						arr.push('<div>档案数：'+d.box.reelnum+'</div>')
						// arr.push('<div>所在层：'+d.box.layer+'</div>')
						// arr.push('<div>所在列：'+d.box.column+'</div>')
					} else {
						arr.push('<div>无盒</div>')
					}
					arr.push('</div>')
					if (boxManage) {
						arr.push('<ul class="sen-box-menu">')
						if (d.box) {
							arr.push('<li onclick="editBox()">修改盒</li>')
							arr.push('<li onclick="delBox()">删除盒</li>')
						} else {
							arr.push('<li onclick="addBox()">添加盒</li>')
						}
						arr.push('</ul>')
					}
					arr.push('</div>')
					return arr.join('')
				},
				enterable: true
			},
			series: [{
				symbolSize: [_x/row.rack_layer_column - 10, _y/row.rack_layer - 10],
				symbol: 'rect',
				data: getScatterData(row.rack_layer_column, row.rack_layer, arr),
				type: 'scatter'
			}]
		};
		myChart.setOption(option, true);
	}
	function getScatterData(x, y, arr) {
		var data = []
		for (var j=0;j<y;j++) {
			for (var i=0;i<x;i++) {
				data.push({
					value: [i+0.5, j+0.5],
					layer: j+1,
					column: i+1,
					itemStyle: {
						color: '#cecece'
					}
				})
			}
		}
		for (var i=0;i<arr.length;i++) {
			var o = arr[i], c = data[(o.layer-1)*x+o.column-1]
			c.itemStyle.color = '#f09090'
			c.box = o
		}
		return data
	}
	</script>
</head>
<body class="easyui-layout sen-crud-page">
	<div data-options="region:'west',border:true,collapsible:false,cls:'pd3'" style="width: 40%;">
		<div class="easyui-layout sen-crud-page">
			<div class="sen-crud-page-header" data-options="region:'north',border:false,title:'档案室',collapsible:false" style="height: 40%;">
				<c:forEach items="${authFunctionCode }" var="auth">
					<c:if test="${auth == 'archiveRoom:roomManage' }">
						<div class="sen-crud-page-toolbar" id="sen-toolbar">
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="addRoom()" plain="true">新增</a>
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="editRoom()" plain="true">修改</a>
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="delRoom()" plain="true">删除</a>
						</div>
					</c:if>
				</c:forEach>
				<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
		    </div>
		    <div class="sen-crud-page-center" data-options="region:'center',border:false,title:'档案架',collapsible:false" style="height: 60%;">
				<form class="sen-crud-page-form" id="sen-form-child">
					<input id="roomid" type="hidden" name="arch_room_id" value="" />
				</form>
				<c:forEach items="${authFunctionCode }" var="auth">
					<c:if test="${auth == 'archiveRoom:rackManage' }">
						<div class="sen-crud-page-toolbar" id="sen-toolbar-child">
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="addRoomRack()" plain="true">新增</a>
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="editRoomRack()" plain="true">修改</a>
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="delRoomRack()" plain="true">删除</a>
						</div>
					</c:if>
					<c:if test="${auth == 'archiveRoom:boxManage' }">
						<input id="boxManage" type="hidden" value="1" />
					</c:if>
				</c:forEach>
				<table id="sen-datagrid-child" class="sen-crud-page-datagrid"></table>
		    </div>
		</div>
	</div>
	<div data-options="region:'center',border:true,title:'档案盒',cls:'pd3'" style="width: 60%;">
		<div class="echarts" style="width: 100%;height: 100%;"></div>
	</div>
</body>
</html>