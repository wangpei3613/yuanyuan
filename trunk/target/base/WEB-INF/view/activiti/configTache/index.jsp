<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>环节配置</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/act/config/tache/select',
			sortName:'ordernum',
		    sortOrder:'asc',
		    method:'get',
		    queryParams:{actid:'${param.id}'},
		    fitColumns:false,
			columns:[[
				{field:'actname',title:'流程名称',width:100,sortable:true},
				{field:'tachecode',title:'环节编码',width:80,sortable:true},
				{field:'name',title:'环节名称',width:100,sortable:true},
				{field:'ordernum',title:'环节顺序',width:80,sortable:true},
				{field:'tache_type',title:'环节类别',width:80,sortable:true,align:'center',dictType:'SEN_ACT_TACHE_TYPE'},
				{field:'needsignature',title:'是否需要签名',width:100,sortable:true,align:'center',dictType:'SEN_IF'},
				{field:'people_select_type',title:'人员选择方式',width:100,sortable:true,align:'center',dictType:'SEN_ACT_SELECT_TYPE'},
				{field:'prescription',title:'时效',width:100,sortable:true},
				{field:'sign_return',title:'是否允许复议',width:110,sortable:true,align:'center',dictType:'SEN_IF'},
				{field:'sign_ratio',title:'会签通过比例(%)',width:110,sortable:true},
				{field:'sign_type',title:'会签是否全员审批',width:120,sortable:true,align:'center',dictType:'SEN_IF'},
				{field:'sign_offset',title:'会签人员偏移量',width:110,sortable:true},
				{field:'saveurl',title:'提交前回调',width:100,sortable:true},
				{field:'redirect',title:'提交后回调',width:100,sortable:true},
				{field:'is_transfer',title:'是否允许移交',width:110,sortable:true,align:'center',dictType:'SEN_IF'},
				{field:'transfer_sign',title:'移交是否签名',width:110,sortable:true,align:'center',dictType:'SEN_IF'},
				{field:'transfer_people_type',title:'移交人员选择方式',width:110,sortable:true,align:'center',dictType:'SEN_ACT_TRANSFER_PEOPLE_TYPE'},
				{field:'transfer_saveurl',title:'移交前回调',width:100,sortable:true},
				{field:'transfer_redirect',title:'移交后回调',width:100,sortable:true},
				{field:'remark',title:'备注',width:200}
			]]
		});
	});
	function openAdd(){
		layer.win({
		  title: '新增环节配置',
		  area: ['800px', '600px'],
		  content: '${path}/act/config/tache/edit?actid=${param.id}',
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  form = win.$('form');
			  if(form.form('validate')){
				  sen.ajax({
					  url:'/act/config/tache/add',
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
				  title: '修改环节配置',
				  area: ['800px', '600px'],
				  content: '${path}/act/config/tache/edit?id='+grid.grid('getSelected').id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/act/config/tache/update',
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
					  url:'/act/config/tache/delete',
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
	function setOperator(category){
		if(grid.grid('selectOne')){
		     layer.win({
				  title: (category=='1'?'审批':'移交')+'人员选取规则配置',
				  area: ['800px', '600px'],
				  content: '${path}/act/config/tache/operator/index?id='+grid.grid('getSelected').id+'&category='+category
			}); 
		}
	}
	</script>
</head>
<body class="easyui-layout sen-crud-page">
    <div class="sen-crud-page-center" data-options="region:'center',border:false">
    		<c:forEach items="${authFunctionCode }" var="auth">
    			<c:if test="${auth == 'workFlow:tache' }">
    				<div class="sen-crud-page-toolbar" id="sen-toolbar">
		    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">新增</a>
		            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>
		            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
		            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cog" onclick="setOperator(1)" plain="true">审批人员配置</a>
		            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-cog" onclick="setOperator(2)" plain="true">移交人员选取规则配置</a>
		    		</div>
    			</c:if>
    		</c:forEach>  
    		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
    </div>
</body>
</html>