<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>环节人员配置</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/act/config/tache/operator/select',
			sortName:'addtime',
		    sortOrder:'desc',
		    method:'get',
		    singleSelect:false,
		    queryParams:{tacheid:'${param.id}',category:'${param.category}'}, 
			columns:[[
				{field:'ck',checkbox:true},
				{field:'actname',title:'流程名称',width:100,sortable:true},
				{field:'tachename',title:'环节名称',width:100,sortable:true},
				{field:'type',title:'规则类别',width:80,sortable:true,align:'center',dictType:'SEN_ACT_TACHE_OPERATOR_TYPE'},
				{field:'values',title:'规则内容',width:200,sortable:true},
				{field:'remark',title:'备注',width:200}
			]]
		});
	});
	function openAdd(){
		layer.win({
		  title: '新增环节人员配置',
		  area: ['500px', '350px'],
		  content: '${path}/act/config/tache/operator/edit?tacheid=${param.id}&category=${param.category}',
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  form = win.$('form');
			  if(form.form('validate')){
				  sen.ajax({
					  url:'/act/config/tache/operator/add',
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
		var d = grid.grid('getSelected');
		if(grid.grid('selectOne')){
			if(d.type=='1' || d.type=='2' || d.type=='3'){
				sen.alert('选取规则为用户、角色、岗位、部门的不允许直接修改，请通过先删除再新建操作实现修改');
				return;
			}
		     layer.win({
				  title: '修改环节人员配置',
				  area: ['500px', '350px'],
				  content: '${path}/act/config/tache/operator/edit?id='+d.id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/act/config/tache/operator/update',
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
					  url:'/act/config/tache/operator/delete',
					  data:{data:JSON.stringify(d)},
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
    			<c:if test="${auth == 'workFlow:tacheOperator' }">
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