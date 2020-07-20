<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>环节走向配置</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/act/config/tache/trend/select',
			sortName:'ordernum',
		    sortOrder:'asc',
		    method:'get',
		    queryParams:{actid:'${param.id}'},
		    pageSize:50,
		    singleSelect:false,
			columns:[[
				{field:'ck',checkbox:true},
				{field:'actname',title:'流程名称',width:100,sortable:true},
				{field:'ordernum',title:'处理顺序',width:80,sortable:true},
				{field:'curr_tache_name',title:'当前环节',width:100,sortable:true},
				{field:'next_tache_name',title:'下环节',width:100,sortable:true},
				{field:'trend_type',title:'类别',width:80,sortable:true,align:'center',dictType:'SEN_ACT_TREND_TYPE'},
				{field:'state',title:'状态',width:100,sortable:true,align:'center',dictType:'SEN_ENABLE_STATE'},
				{field:'branch_condition',title:'分支条件',width:200},
				{field:'branch_condition_remark',title:'分支条件描述',width:200}
			]]
		});
	});
	function openAdd(){
		layer.win({
		  title: '新增环节走向配置',
		  area: ['400px', '450px'],
		  content: '${path}/act/config/tache/trend/edit?actid=${param.id}',
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  form = win.$('form');
			  if(form.form('validate')){
				  sen.ajax({
					  url:'/act/config/tache/trend/add',
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
				  title: '修改环节走向配置',
				  area: ['400px', '450px'],
				  content: '${path}/act/config/tache/trend/edit?id='+grid.grid('getSelected').id+'&actid=${param.id}',
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/act/config/tache/trend/update',
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
		var d = grid.grid('getSelections');
		if(d.length > 0){
			sen.confirm('确定要删除吗?',function(index){
				sen.ajax({
					  url:'/act/config/tache/trend/delete',
					  data:{data:JSON.stringify(grid.datagrid('getSelections'))},
					  success:function(){
						  sen.msg('删除成功');
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
    <div class="sen-crud-page-center" data-options="region:'center',border:false">
    		<c:forEach items="${authFunctionCode }" var="auth">
    			<c:if test="${auth == 'workFlow:tacheTrend' }">
    				<div class="sen-crud-page-toolbar" id="sen-toolbar">
		    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">新增</a>
		            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>
		            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
		    		</div>
    			</c:if>
    		</c:forEach>
    		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
    </div>
</body>
</html>