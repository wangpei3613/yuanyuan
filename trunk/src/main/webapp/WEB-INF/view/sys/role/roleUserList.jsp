<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>角色用户管理</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/system/user/queryRoleUser',
			sortName:'t1.createdate',
		    sortOrder:'desc',
		    singleSelect:false,
			columns:[[
				{field:'ck',checkbox:true},
				{field:'userName',title:'帐号',width:120,sortable:true},
				{field:'nickName',title:'姓名',width:120,sortable:true},
				{field:'deptName',title:'所在部门',width:200,sortable:true},
				{field:'linkPhone',title:'联系电话',width:120,sortable:true},
				{field:'serialNo',title:'串号',width:130,sortable:true},
				{field:'status',title:'状态',width:60,sortable:true,align:'center',dictType:'SEN_ENABLE_STATE'}
			]]
		});
	});
	function openAdd(){
		layer.win({
			  title: '请选择用户',
			  area: [top.window.innerWidth-100+'px', top.window.innerHeight-100+'px'],
			  content: '${path}/system/user/toGetUser?multiple=1',
			  btn:['确定','关闭'],
			  yes:function(index, layero){
				  var win = layero.find('iframe')[0].contentWindow;
				  if(win.ids.length > 0){
					  sen.ajax({
						  url:'/system/user/addRoleUser',
						  data:{ids:win.ids.join(','),roleid:'${param.id}'},
						  loaddingEle:layero,
						  success:function(){
							  sen.msg('新增成功');
							  grid.grid('load');
							  top.layer.close(index);	
						  }
				  	   });
				  }else{
					  sen.alert('新添加要选择的用户');
				  }
				  return false;
			  }
			});
	}
	function remove(){
		var d = grid.grid('getSelections');
		if(d.length > 0){
			sen.confirm('确定要删除吗?',function(index){
				sen.ajax({
					  url:'/system/user/deleteRoleUser',
					  data:{data:JSON.stringify(d)},
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
	<div class="sen-crud-page-header" data-options="region:'north',border:false">
    		<form class="sen-crud-page-form" id="sen-form">
    			<input type="hidden" name="@t1.userName" value="like_anywhere" /> 
    			<input type="hidden" name="@t1.nickName" value="like_anywhere" /> 
    			<input type="hidden" name="@t1.deptId" value="in" /> 
    			<input type="hidden" name="m.roleid" value="${param.id }" /> 
    			<label>帐号：</label><input type="text" name="t1.userName" class="easyui-textbox" style="width:120px">
    			<label>姓名：</label><input type="text" name="t1.nickName" class="easyui-textbox" style="width:120px">
    			<label>所在部门：</label><input type="text" name="t1.deptId" data-options="url:'${path }/system/depart/getDeptTree',multiple:true,cascadeCheck:false" class="easyui-combotree" style="width:180px">
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search">查询</a>
    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
    		</form>
    </div>
    <div class="sen-crud-page-center" data-options="region:'center',border:false">
      <c:forEach items="${authFunctionCode }" var="auth">
    		<c:if test="${auth == 'roleManage:roleUser' }">
	    		<div class="sen-crud-page-toolbar" id="sen-toolbar">
	    			<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">新增</a>
	            <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
	    		</div>
    		</c:if>
      </c:forEach>
    		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
    </div>
</body>
</html>