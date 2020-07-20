<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>风险模型配置</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/sen/core/risk/queryRiskInfo',
			sortName:'addtime',
		    sortOrder:'desc',
			method:'get',
			columns:[[
				{field:'code',title:'模型编码',width:100,sortable:true},
				{field:'name',title:'模型名称',width:200,sortable:true},
				{field:'status',title:'状态',width:100,sortable:true,align:'center',dictType:'SEN_ENABLE_STATE'},
				{field:'remark',title:'备注',width:300}
			]],
			onSelect:function(index,row){
				parent.row = row;
				parent.enableTabs();
			}
		});
	});
	function openAdd(){
		layer.win({
		  title: '新增风险模型',
		  area: ['400px', '300px'],
		  content: '${path}/sen/core/risk/toRiskEdit',
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  form = win.$('form');
			  if(form.form('validate')){
				  sen.ajax({
					  url:'/sen/core/risk/addRiskInfo',
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
				  title: '修改风险模型',
				  area: ['400px', '300px'],
				  content: '${path}/sen/core/risk/toRiskEdit?id='+grid.grid('getSelected').id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/sen/core/risk/editRiskInfo',
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
					  url:'/sen/core/risk/delRiskInfo',
					  data:{data:JSON.stringify(grid.datagrid('getSelections'))},
					  success:function(){
						  sen.msg('删除成功');
						  parent.disableTabs();
						  grid.grid('clearSelections');
						  grid.grid('load');
					  }
			  	});
			});
		}
	}
	function setEnable(){
		var d = grid.grid('getSelected');
		if(grid.grid('selectOne')){
			if(d.status == '2'){
				sen.confirm('启用同时会禁用与该模型编码相同的其他模型，确认要启用吗?',function(index){
					sen.ajax({
						  url:'/sen/core/risk/setEnable',
						  data:{id:d.id},
						  success:function(){
							  sen.msg('启用成功');
							  grid.grid('load');
						  }
				  	});
				});
			}else{
				sen.alert('请选择处于禁用状态的模型');
			}
		}
	}
	function setDisable(){
		var d = grid.grid('getSelected');
		if(grid.grid('selectOne')){
			if(d.status == '1'){
				sen.ajax({
					  url:'/sen/core/risk/setDisable',
					  data:{id:d.id},
					  success:function(){
						  sen.msg('禁用成功');
						  grid.grid('load');
					  }
			  	});
			}else{
				sen.alert('请选择处于启用状态的模型');
			}
		}
	}
	</script>
</head>
<body class="easyui-layout sen-crud-page">
	<div class="sen-crud-page-header" data-options="region:'north',border:false">
    		<form class="sen-crud-page-form" id="sen-form">
    			<input type="hidden" name="@code" value="like_anywhere" /> 
    			<input type="hidden" name="@name" value="like_anywhere" /> 
    			<label>模型编码：</label><input type="text" name="code" class="easyui-textbox" style="width:120px">
    			<label>模型名称：</label><input type="text" name="name" class="easyui-textbox" style="width:120px">
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search">查询</a>
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
    		</form>
    </div>
    <div class="sen-crud-page-center" data-options="region:'center',border:false">
    		<c:if test="${not empty moduleAuths }">
    			<div class="sen-crud-page-toolbar" id="sen-toolbar">
    				<c:forEach items="${moduleAuths }" var="auth">  
		    			<a href="javascript:;" class="easyui-linkbutton" iconCls="${auth.icon }" onclick="${empty auth.action?auth.code:auth.action}()" plain="true">${auth.name }</a>
    				</c:forEach>
    			</div>
    		</c:if>
    		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
    </div>
</body>
</html>