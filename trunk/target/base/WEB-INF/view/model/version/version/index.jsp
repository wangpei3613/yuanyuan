<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>指标版本管理</title>
	<%@ include file="../../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/model/index/version/getVersionPager',
			sortName:'createdate',
		    sortOrder:'desc',
			columns:[[
				{field:'code',title:'版本号',width:100,sortable:true},
				{field:'name',title:'版本名称',width:200,sortable:true},
				{field:'status',title:'状态',width:60,sortable:true,align:'center',dictType:'SEN_ENABLE_STATE'},
				{field:'remark',title:'备注',width:300,sortable:true},
			]]
		});
	});
	function openAdd(){
		layer.win({
		  title: '新增指标版本',
		  area: ['400px', '400px'],
		  content: '${path}/model/index/version/toVersionEdit',
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  form = win.$('form');
			  if(form.form('validate')){
				  sen.ajax({
					  url:'/model/index/version/addVersion',
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
				  title: '修改指标版本',
				  area: ['400px', '400px'],
				  content: '${path}/model/index/version/toVersionEdit?id='+grid.grid('getSelected').id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/model/index/version/updateVersion',
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
					  url:'/model/index/version/delVersion',
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
	function setting(){
		if(grid.grid('selectOne')){
			layer.win({
				  title: '版本指标配置',
				  area: [top.window.innerWidth-200+'px', top.window.innerHeight-100+'px'],
				  content: '${path}/model/index/version/toVesionDetail?id='+grid.grid('getSelected').id
			});
		}
	}
	function history(){
		if(grid.grid('selectOne')){
			layer.win({
				  title: '查看历史版本',
				  area: [top.window.innerWidth-200+'px', top.window.innerHeight-100+'px'],
				  content: '${path}/model/index/version/toVesionHistory?id='+grid.grid('getSelected').id
			});
		}
	}
	</script>
</head>
<body class="easyui-layout sen-crud-page">
	<div class="sen-crud-page-header" data-options="region:'north',border:false">
    		<form class="sen-crud-page-form" id="sen-form">
    			<input type="hidden" name="@code" value="like_anywhere" /> 
    			<input type="hidden" name="@name" value="like_anywhere" /> 
    			<label>版本号：</label><input type="text" name="code" class="easyui-textbox" style="width:120px">
    			<label>版本名称：</label><input type="text" name="name" class="easyui-textbox" style="width:120px">
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