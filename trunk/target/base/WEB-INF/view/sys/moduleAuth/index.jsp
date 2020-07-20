<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>菜单权限配置</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/system/module/auth/getPager',
			queryParams:{moduleid:'${param.id}'},  
			sortName:'sort',
		    sortOrder:'asc',
			columns:[[
				{field:'code',title:'编码',width:100,sortable:true,align:'center'},
				{field:'name',title:'名称',width:100,sortable:true,align:'center'},
				{field:'type',title:'类别',width:80,fixed:true,sortable:true,align:'center',dictType:'SEN_MODULE_AUTH_TYPE'},
				{field:'sort',title:'排列顺序',width:80,fixed:true,align:'center'},
				{field:'icon',title:'图标',width:150,sortable:true,showTitle:false,formatter:function(v,row){
					return v?('<span class="sen-icon '+v+'"></span>'+v):'';
				}},
				{field:'status',title:'状态',width:60,sortable:true,align:'center',dictType:'SEN_ENABLE_STATE'},
				{field:'action',title:'事件',width:100,align:'center',formatter:function(v,row){
					return row.type=='1'?(v||row.code):'';
				}},
				{field:'remark',title:'备注',width:200}
			]]
		});
	});
	function openAdd(){
		layer.win({
			  title: '新增菜单权限',
			  area: ['400px', '400px'],
			  content: '${path}/system/module/auth/toEdit?moduleid=${param.id}',
			  btn:['保存','关闭'],
			  yes:function(index, layero){
				  var win = layero.find('iframe')[0].contentWindow,
				  	  form = win.$('form');
				  if(form.form('validate')){
					  sen.ajax({
						  url:'/system/module/auth/save',
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
				  title: '修改菜单权限',
				  area: ['400px', '400px'],
				  content: '${path}/system/module/auth/toEdit?id='+grid.grid('getSelected').id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/system/module/auth/save',
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
					  url:'/system/module/auth/delete',
					  data:{id:grid.grid('getSelected').id},
					  success:function(){
						  sen.msg('删除成功');
						  grid.grid('clearSelections');
						  grid.grid('load');
					  }
			  	});
			});
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