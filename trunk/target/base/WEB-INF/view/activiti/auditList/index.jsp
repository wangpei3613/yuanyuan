<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>流程测试审批</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/act/apply/test/auditList',
			sortName:'sent_time',
		    sortOrder:'desc',
		    method:'get',
		    singleSelect:false,
			columns:[[
				{field:'ck',checkbox:true},
				{field:'name',title:'姓名',width:100,sortable:true},
				{field:'cardid',title:'证件号',width:200,sortable:true},
				{field:'currtachenames',title:'当前环节',width:200,sortable:true},
				{field:'nextttachenames',title:'下环节',width:200,sortable:true},
				{field:'sent_time',title:'传送时间',width:200,sortable:true},
				{field:'sentusername',title:'传送人',width:100,sortable:true},
				{field:'addtime',title:'申请时间',width:200,sortable:true},
				{field:'applmanname',title:'申请人',width:100,sortable:true}
			]],
			rowStyler:function(index,d){
				if(d.isovertime == '1'){
					return 'color:#f00;';
				}
			}
		});
	});
	function viewAuditRecord(){
		if(grid.grid('selectOne')){
			layer.win({
				  title: '审批记录',
				  area: [top.window.innerWidth-300+'px', top.window.innerHeight-100+'px'],
				  content: '${path}/act/apply/info/toViewAuditRecord?id='+grid.grid('getSelected').applyid
			});
		}
	}
	function auditApply(){
		if(grid.grid('selectOne')){
			sen.applyAudit(grid.grid('getSelected').applytacheid,function(){
				sen.msg('审批成功');
				grid.grid('load');
			});
		}
	}
	function auditQuick(){
		var d = grid.datagrid('getSelections'), ids = [];
		if(d.length > 0){
			for(var i=0;i<d.length;i++){
				ids.push(d[i].applytacheid);
			}
			sen.confirm('确定要快捷审批吗?',function(index){
				sen.ajax({
					  url:'/act/apply/info/auditQuick',
					  data:{applytacheids:ids.join(',')},
					  success:function(d){
						  var num = d.length,succ = 0;
						  for(var i=0;i<d.length;i++){
							  d[i].success && succ++;
						  }
						  if(num == succ){
							  sen.alert('快捷审批通过'+succ+'条',{icon:1});
						  }else{
							  sen.alert('快捷审批通过'+succ+'条，失败'+(num-succ)+'条，失败数据建议使用普通审批按钮单条审批!',{icon:1});
						  }
						  grid.grid('clearSelections');
						  grid.grid('load');
					  }
			  	});
			});
		}else{
			sen.alert('请选择要删除的数据');
		}
	}
	</script>
</head>
<body class="easyui-layout sen-crud-page">
	<div class="sen-crud-page-header" data-options="region:'north',border:false">
    		<form class="sen-crud-page-form" id="sen-form">
    			<input type="hidden" name="@name" value="like_anywhere" /> 
    			<input type="hidden" name="@cardid" value="like_anywhere" /> 
    			<label>姓名：</label><input type="text" name="name" class="easyui-textbox" style="width:120px">
    			<label>证件号：</label><input type="text" name="cardid" class="easyui-textbox" style="width:120px">
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search">查询</a>
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-undo">重置</a> 
    		</form>
    </div>
    <div class="sen-crud-page-center" data-options="region:'center',border:false">
    		<div class="sen-crud-page-toolbar" id="sen-toolbar">
            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="auditApply()" plain="true">审批</a>
            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="auditQuick()" plain="true">快捷审批</a>
            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-information" onclick="viewAuditRecord()" plain="true">审批记录</a>
    		</div>
    		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
    </div>
</body>
</html>