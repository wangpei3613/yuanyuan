<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>版本指标参数配置</title>
	<%@ include file="../../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/model/index/version/getParameterPager',
			sortName:'sort',
		    sortOrder:'asc',
		    method:'get',
		    queryParams:{indexversionid:'${param.id}'},
		    pageSize:50,
			columns:[[
				{field:'code',title:'参数编码',width:100,sortable:true},
				{field:'name',title:'参数名称',width:100,sortable:true},
				{field:'valuetype',title:'值类别',width:100,sortable:true,align:'center',dictType:'MODEL_INDEX_PARAMETER_VALUE_TYPE'},
				{field:'sort',title:'排序号',width:60,sortable:true,fixed:true},
				{field:'type',title:'取值类别',width:100,sortable:true,align:'center',dictType:'MODEL_INDEX_PARAMETER_TYPE'},
				{field:'value',title:'参数值',width:200},
				{field:'datatype',title:'参数来源',width:100,sortable:true,align:'center',dictType:'MODEL_INDEX_PARAMETER_SOURCE'},
				{field:'remark',title:'备注',width:200}
			]]
		});
	});
	function openAdd(){
		layer.win({
		  title: '新增版本指标参数',
		  area: ['400px', '450px'],
		  content: '${path}/model/index/version/toParameterEdit',
		  success:function(layero){
			  win = layero.find('iframe')[0].contentWindow;
			  win.$(function(){
				  win.init({indexversionid:'${param.id}'});
			  });
		  },
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  form = win.$('form');
			  if(form.form('validate')){
				  sen.ajax({
					  url:'/model/index/version/addParameter',
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
				  title: '修改版本指标参数',
				  area: ['400px', '450px'],
				  content: '${path}/model/index/version/toParameterEdit',
				  success:function(layero){
					  win = layero.find('iframe')[0].contentWindow;
					  win.$(function(){
						  win.init(grid.grid('getSelected'));
					  });
				  },
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/model/index/version/updateParameter',
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
					  url:'/model/index/version/delParameter',
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
    		<div class="sen-crud-page-toolbar" id="sen-toolbar">
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">新增</a>
            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>
            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
    		</div>
    		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
    </div>
</body>
</html>