<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>流程测试</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/act/apply/test/select',
			sortName:'addtime',
		    sortOrder:'desc',
		    method:'get',
			columns:[[
				{field:'name',title:'姓名',width:100,sortable:true},
				{field:'cardid',title:'证件号',width:200,sortable:true},
				{field:'audit_state',title:'流程状态',width:100,sortable:true,align:'center',dictType:'SEN_ACTICITI_STATE'},
				{field:'currtachenames',title:'当前环节名称',width:200,sortable:true},
				{field:'domanname',title:'当前处理人',width:100,sortable:true},
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
	function openAdd(){
		layer.win({
		  title: '新增申请',
		  area: ['400px', '250px'],
		  content: '${path}/act/apply/test/applyEdit',
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  form = win.$('form');
			  if(form.form('validate')){
				  sen.ajax({
					  url:'/act/apply/test/add',
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
	function openEdit(){
		if(grid.grid('selectOne')){
		     layer.win({
				  title: '修改申请',
				  area: ['400px', '250px'],
				  content: '${path}/act/apply/test/applyEdit?id='+grid.grid('getSelected').id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/act/apply/test/update',
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
	function remove(){
		if(grid.grid('selectOne')){
			sen.confirm('确定要删除吗?',function(index){
				sen.ajax({
					  url:'/act/apply/test/delete',
					  data:{data:JSON.stringify(grid.datagrid('getSelections'))},
					  success:function(){
						  sen.msg('删除成功');
						  grid.grid('clearSelections');
						  grid.grid('load');
					  }
			  	});
			});
		}
	}
	function cancelApply(){
		if(grid.grid('selectOne')){
			sen.confirm('确认取消选中的申请吗?',function(index){
				sen.ajax({
					  url:'/act/apply/test/cancelApply',
					  data:{id:grid.grid('getSelected').id},
					  success:function(){
						  sen.msg('取消申请成功');
						  grid.grid('load');
					  }
			  	});
			});
		}
	}
	function viewAuditRecord(){
		if(grid.grid('selectOne')){
			layer.win({
				  title: '审批记录',
				  area: [top.window.innerWidth-300+'px', top.window.innerHeight-100+'px'],
				  content: '${path}/act/apply/info/toViewAuditRecord?id='+grid.grid('getSelected').applyid
			});
		}
	}
	function submitApply(){
		if(grid.grid('selectOne')){
			sen.applySubmit(grid.grid('getSelected').applyid,function(){
				sen.msg('提交成功');
				grid.grid('load');
			});
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
    			<label>流程状态：</label><input type="text" class="easyui-combobox" data-options="url:'${path}/sys/sysData?type=SEN_ACTICITI_STATE'" name="audit_state" style="width: 120px;"/>
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search">查询</a>
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-undo">重置</a> 
    		</form>
    </div>
    <div class="sen-crud-page-center" data-options="region:'center',border:false">
    		<div class="sen-crud-page-toolbar" id="sen-toolbar">
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">新增</a>
            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>
            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitApply()" plain="true">提交申请</a>
            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancelApply()" plain="true">取消申请</a>
            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-information" onclick="viewAuditRecord()" plain="true">审批记录</a>
    		</div>
    		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
    </div>
</body>
</html>