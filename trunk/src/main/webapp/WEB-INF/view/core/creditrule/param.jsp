<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>征信规则参数配置</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/sen/core/creditrule/queryCreditRuleParam',
			sortName:'id',
		    sortOrder:'asc',
		    queryParams:{ruleid:'${param.id}'},  
		    method:'get',
			columns:[[
				{field:'ruleid',title:'规则编码',width:80,sortable:true},
				{field:'code',title:'参数编码',width:80,sortable:true},
				{field:'name',title:'参数名称',width:150,sortable:true},
				{field:'content',title:'参数说明',width:250,sortable:true},
				{field:'value',title:'参数值',width:200,sortable:true},
				{field:'remark',title:'备注',width:200}
			]]
		});
	});
	function openEdit(){
		if(grid.grid('selectOne')){
			layer.win({
				  title: '修改征信规则参数',
				  area: ['500px', '400px'],
				  content: '${path}/sen/core/creditrule/toParamEdit?id='+grid.grid('getSelected').id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/sen/core/creditrule/editCreditRuleParam',
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
    <div class="sen-crud-page-center" data-options="region:'center',border:false">
         <c:forEach items="${authFunctionCode }" var="auth">
    		  <c:if test="${auth == 'creditRuleManage:param' }">
    		  	<div class="sen-crud-page-toolbar" id="sen-toolbar">
	    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>
	    		</div>
    		  </c:if>
   		 </c:forEach>
    		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
    </div>
</body>
</html>