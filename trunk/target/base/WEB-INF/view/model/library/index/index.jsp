<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>指标配置</title>
	<%@ include file="../../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').tgrid({
			url:'${path }/model/index/config/getTreeGrid',
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
		var d = grid.tgrid('getSelected'),pid = '';
		if(d && d.level=='1'){
			pid = d.id;
		}
		layer.win({
		  title: '新增指标配置',
		  area: ['800px', '500px'],
		  content: '${path}/model/index/config/toIndexEdit?pid='+pid,
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  form = win.$('form');
			  if(form.form('validate')){
				  sen.ajax({
					  url:'/model/index/config/saveLibrary',
					  data:form.serializeObject(),
					  loaddingEle:layero,
					  success:function(){
						  top.layer.close(index);
						  sen.msg('保存成功');
						  grid.tgrid('load');
					  }
				  });
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
				  title: '修改指标配置',
				  area: ['800px', '500px'],
				  content: '${path}/model/index/config/toIndexEdit?id='+d.id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/model/index/config/saveLibrary',
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
					  url:'/model/index/config/delLibrary',
					  data:{id:d.id},
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