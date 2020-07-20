<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>APP功能菜单管理</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').tgrid({
			url:'${path }/system/moduleApp/getTreeGrid',
		    treeField:'name',
			columns:[[
				{field:'name',title:'菜单名称',width:200},
				{field:'code',title:'菜单编号',width:100},
				{field:'status',title:'状态',width:80,fixed:true,align:'center',dictType:'SEN_ENABLE_STATE'},
				{field:'ordernum',title:'排列顺序',width:60,fixed:true,align:'center'},
				{field:'icons',title:'菜单图标',width:100},
				{field:'url',title:'菜单路径',width:200},
				{field:'indexshow',title:'是否首页展示',width:100,fixed:true,align:'center',dictType:'SEN_IF'},
			]]
		});
	});
	function openAdd(){
		var d = grid.treegrid('getSelected'),pid = '';
		if(d && d.level!='3'){
			pid = d.id;
		}
		layer.win({
		  title: '新增APP功能菜单',
		  area: ['400px', '500px'],
		  content: '${path}/system/moduleApp/edit?pid='+pid,
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  form = win.$('form');
			  if(form.form('validate')){
				  sen.ajax({
					  url:'/system/moduleApp/save',
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
			if(d.level == '1'){
				sen.alert('系统菜单不能修改');
				return;
			}
		     layer.win({
				  title: '修改APP功能菜单',
				  area: ['400px', '500px'],
				  content: '${path}/system/moduleApp/edit?id='+d.id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/system/moduleApp/save',
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
			if(d.level == '1'){
				sen.alert('系统菜单不能删除');
				return;
			}
			sen.confirm('确定要删除吗?',function(index){
				sen.ajax({
				      url:'/system/moduleApp/del',
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
    		<div class="sen-crud-page-toolbar" id="sen-toolbar">
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">新增</a>
            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>
            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
    		</div>
    		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
    </div>
</body>
</html>