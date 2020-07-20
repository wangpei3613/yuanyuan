<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>档案目录管理</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').tgrid({
			url:'${path }/arch/menu/getTreeGrid',
		    treeField:'menu_name',
			columns:[[
				{field:'menu_name',title:'目录名称',width:200},
				{field:'menu_no',title:'目录编号',width:150},
				{field:'ordernumber',title:'排序号',width:60,fixed:true,align:'center'},
				{field:'remark',title:'备注',width:300}
			]]
		});
	});
	function openAdd(){
		var d = grid.treegrid('getSelected'),pid = '';
		if(d){
			pid = d.id;
		}
		layer.win({
		  title: '新增档案目录',
		  area: ['400px', '400px'],
		  content: '${path}/arch/menu/toEdit?pid='+pid,
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  form = win.$('form');
			  if(form.form('validate')){
				  sen.ajax({
					  url:'/arch/menu/save',
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
				  title: '修改档案目录',
				  area: ['400px', '400px'],
				  content: '${path}/arch/menu/toEdit?id='+d.id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/arch/menu/save',
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
				      url:'/arch/menu/del',
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