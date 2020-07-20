<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>征信规则配置</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/sen/core/creditrule/queryCreditRule',
			sortName:'id',
		    sortOrder:'asc',
			method:'get',
			columns:[[
				{field:'id',title:'规则编码',width:80,sortable:true},
				{field:'name',title:'规则名称',width:200,sortable:true},
				{field:'status',title:'状态',width:100,sortable:true,align:'center',dictType:'SEN_ENABLE_STATE'},
				{field:'content',title:'规则说明',width:300},
				{field:'title',title:'提示信息',width:300},
				{field:'remark',title:'备注',width:200}
			]],
			onSelect:function(index,row){
				parent.row = row;
				parent.enableTabs();
			}
		});
	});
	function openEdit(){
		if(grid.grid('selectOne')){
		     layer.win({
				  title: '修改征信规则',
				  area: ['500px', '400px'],
				  content: '${path}/sen/core/creditrule/toRuleEdit?id='+grid.grid('getSelected').id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/sen/core/creditrule/editCreditRule',
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
	</script>
</head>
<body class="easyui-layout sen-crud-page">
	<div class="sen-crud-page-header" data-options="region:'north',border:false">
    		<form class="sen-crud-page-form" id="sen-form">
    			<input type="hidden" name="@id" value="like_anywhere" /> 
    			<input type="hidden" name="@name" value="like_anywhere" /> 
    			<label>规则编码：</label><input type="text" name="id" class="easyui-textbox" style="width:120px">
    			<label>规则名称：</label><input type="text" name="name" class="easyui-textbox" style="width:120px">
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