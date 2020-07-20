<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>部门管理</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').tgrid({
			url:'${path }/system/depart/getDeptTree',
		    treeField:'fullName',
			columns:[[
				{field:'id',hidden:true},
				{field:'fullName',title:'部门名称',width:2},
				{field:'deptCode',title:'部门编号',width:1},
				{field:'orderIndex',title:'排列顺序',width:60,fixed:true,align:'center'},
				{field:'phone',title:'部门电话',width:1},
				{field:'status',title:'状态',width:1,align:'center',dictType:'SEN_ENABLE_STATE'},
				{field:'remark',title:'备注',width:2}
			]]
		});
	});
	function openAdd(){
		var d = grid.treegrid('getSelected');
		layer.win({
		  title: '新增部门',
		  area: ['400px', '400px'],
		  content: '${path}/system/depart/edit?pid='+(d && d.id || ''),
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  form = win.$('form');
			  if(form.form('validate')){
				  sen.ajax({
					  url:'/system/depart/save',
					  data:form.serializeObject(),
					  loaddingEle:layero,
					  success:function(){
						  top.layer.close(index);
						  sen.msg('保存成功');
						  grid.treegrid('load');
					  }
				  });
			  }
			  return false;
		  }
		}); 
	}
	function openEdit(){
		var d = grid.treegrid('getSelected');
		if(d){
		     layer.win({
				  title: '修改部门',
				  area: ['400px', '400px'],
				  content: '${path}/system/depart/edit?id='+d.id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/system/depart/save',
							  data:form.serializeObject(),
							  loaddingEle:layero,
							  success:function(){
								  top.layer.close(index);
								  sen.msg('修改成功');
								  grid.treegrid('load');
							  }
						  });
					  }
					  return false;
				  }
			}); 
		}else{
			sen.alert('请选择一条数据');
		}
	}
	function remove(){
		var d = grid.treegrid('getSelected');
		if(d){
			sen.confirm('确定要删除吗?',function(index){
				sen.ajax({
				      url:'/system/depart/del',
					  data:{id:d.id},
					  success:function(){
						  sen.msg('删除成功');
						  grid.treegrid('clearSelections');
						  grid.treegrid('load');
					  }
			  	});
			});
		}else{
			sen.alert('请选择一条数据');
		}
	}
	</script>
</head>
<body class="easyui-layout sen-crud-page">
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