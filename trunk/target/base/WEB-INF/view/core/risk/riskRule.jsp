<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>风险模型规则配置</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/sen/core/risk/queryRiskRule',
			sortName:'sort',
		    sortOrder:'asc',
		    queryParams:{riskid:'${param.id}'},  
		    fitColumns:false,
		    method:'get',
			columns:[[
				{field:'code',title:'规则编码',width:80,sortable:true},
				{field:'name',title:'规则名称',width:150,sortable:true},
				{field:'status',title:'状态',width:60,sortable:true,align:'center',dictType:'SEN_ENABLE_STATE'},
				{field:'sort',title:'排序',width:60,sortable:true,align:'center'},
				{field:'content',title:'规则说明',width:200},
				{field:'condition',title:'规则条件',width:200},
				{field:'rule',title:'规则内容',width:200},
				{field:'title',title:'提示信息',width:200},
				{field:'remark',title:'备注',width:130}
			]]
		});
	});
	function openAdd(){
		layer.win({
		  title: '新增风险模型规则',
		  area: ['800px', '500px'],
		  content: '${path}/sen/core/risk/toRiskRuleEdit?riskid=${param.id}',
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  form = win.$('form');
			  if(form.form('validate')){
				  sen.ajax({
					  url:'/sen/core/risk/addRiskRule',
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
				  title: '修改风险模型规则',
				  area: ['800px', '500px'],
				  content: '${path}/sen/core/risk/toRiskRuleEdit?id='+grid.grid('getSelected').id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/sen/core/risk/editRiskRule',
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
					  url:'/sen/core/risk/delRiskRule',
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
	</script>
</head>
<body class="easyui-layout sen-crud-page">
	<div class="sen-crud-page-header" data-options="region:'north',border:false">
    		<form class="sen-crud-page-form" id="sen-form">
    			<input type="hidden" name="@code" value="like_anywhere" /> 
    			<input type="hidden" name="@name" value="like_anywhere" /> 
    			<label>规则编码：</label><input type="text" name="code" class="easyui-textbox" style="width:120px">
    			<label>规则名称：</label><input type="text" name="name" class="easyui-textbox" style="width:120px">
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search">查询</a>
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
    		</form>
    </div>
    <div class="sen-crud-page-center" data-options="region:'center',border:false">
    		<c:forEach items="${authFunctionCode }" var="auth">
    		  <c:if test="${auth == 'riskManage:riskRule' }">
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