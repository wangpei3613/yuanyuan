<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>版本指标配置</title>
	<%@ include file="../../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').tgrid({
			url:'${path }/model/index/version/getVersionIndexTree?versionid=${param.id}',
			treeField:'name',
			fitColumns:false,
			 frozenColumns:[[
				 {field:'name',title:'指标名称',width:300},
				 {field:'code',title:'指标编码',width:100},
		    ]],
			columns:[[
				{field:'status',title:'状态',width:80,align:'center',dictType:'SEN_ENABLE_STATE'},
				{field:'sort',title:'排列顺序',width:80},
				{field:'category',title:'指标类别',width:80,align:'center',dictType:'MODEL_INDEX_LIBRARY_CATEGORY'},
				{field:'content',title:'指标内容',width:200},
				{field:'maxscore',title:'指标满分',width:80},
				{field:'formula',title:'指标公式',width:200},
				{field:'argument',title:'取值参数',width:200},
				{field:'remark',title:'备注',width:400}
			]],
			onSelect:function(row){
				parent.row = row;
				parent.disableTabs();
				if(row.level == '2'){
					parent.enableTabs(1);
					if(row.category == '1'){
						parent.enableTabs(2);
					}
				}
			}
		});
	});
	function openAdd(){
		layer.win({
		  title: '请勾选要新增的指标',
		  area: ['600px', top.window.innerHeight-150+'px'],
		  content: '${path}/model/index/version/toAddVersionIndex?versionid=${param.id}',
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  selecteds = win.grid.treegrid('getCheckedNodes'),
			  	  ids = [];
			  if(selecteds.length > 0){
				  for(var i=0;i<selecteds.length;i++){
					  selecteds[i].level=='2' && ids.push(selecteds[i].id);
				  }
				  sen.ajax({
					  url:'/model/index/version/addVersionIndex',
					  data:{ids:ids.join(','),versionid:'${param.id}'},
					  loaddingEle:layero,
					  success:function(){
						  top.layer.close(index);
						  grid.tgrid('load');
						  sen.msg('新增指标成功');
					  }
			  	  });
			  }else{
				  sen.alert('请选择要新增的指标');
			  }
			  return false;
		  }
		}); 
	}
	function openEdit(){
		var d = grid.treegrid('getSelected');
		if(d.level=='1'){
			sen.alert('不能操作指标大类');
			return;
		}
		if(d){
		     layer.win({
				  title: '修改版本指标配置',
				  area: ['800px', '350px'],
				  content: '${path}/model/index/version/toIndexEdit?id='+d.indexversionid+'&category='+d.category,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/model/index/version/updateVersionIndex',
							  data:form.serializeObject(),
							  loaddingEle:layero,
							  success:function(){
								  top.layer.close(index);
								  sen.msg('修改成功');
								  grid.tgrid('load');
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
		if(d.level=='1'){
			sen.alert('不能操作指标大类');
			return;
		}
		if(d){
			sen.confirm('确定要删除吗?',function(index){
				sen.ajax({
					  url:'/model/index/version/delVersionIndex',
					  data:{id:d.indexversionid},
					  success:function(){
						  sen.msg('删除成功');
						  parent.disableTabs();
						  grid.tgrid('clearSelections');
						  grid.tgrid('load');
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