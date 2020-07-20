<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>档案类别配置</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
		var grid;
		$(function(){
			grid = $('#sen-datagrid').grid({
				url:'${path }/arch/reel/type/getPager',
				sortName:'addtime',
				sortOrder:'desc',
				columns:[[
					{field:'code',title:'类别编号',width:200,sortable:true,align:'center'},
					{field:'name',title:'类别名称',width:200,sortable:true},
					{field:'remark',title:'备注',width:200}
				]]
			});
		});
		function openAdd(){
			layer.win({
				title: '新增档案类别',
				area: ['400px', '300px'],
				content: '${path}/arch/reel/type/toEdit',
				btn:['保存','关闭'],
				yes:function(index, layero){
					var win = layero.find('iframe')[0].contentWindow,
							form = win.$('form');
					if(form.form('validate')){
						sen.ajax({
							url:'/arch/reel/type/save',
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
					title: '修改档案类别',
					area: ['400px', '300px'],
					content: '${path}/arch/reel/type/toEdit?id='+grid.grid('getSelected').id,
					btn:['保存','关闭'],
					yes:function(index, layero){
						var win = layero.find('iframe')[0].contentWindow,
								form = win.$('form');
						if(form.form('validate')){
							sen.ajax({
								url:'/arch/reel/type/save',
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
						url:'/arch/reel/type/del',
						data:{id: grid.grid('getSelected').id},
						success:function(){
							sen.msg('删除成功');
							grid.grid('clearSelections');
							grid.grid('load');
						}
					});
				});
			}
		}
		function allotRole(){
			var d = grid.grid('getSelected')
			if(grid.grid('selectOne')){
				layer.win({
					title: '请勾选要分配的角色',
					area: ['400px', '500px'],
					content: '${path}/arch/reel/type/toAllotRole?typeid='+d.id,
					btn:['确定','关闭'],
					yes:function(index, layero){
						var win = layero.find('iframe')[0].contentWindow,
								selecteds = win.grid.grid('getSelections'),
								ids = [];
						for(var i=0;i<selecteds.length;i++){
							ids.push(selecteds[i].id);
						}
						sen.ajax({
							url:'/arch/reel/type/addArchTypeRole',
							data:{roleIds:ids.join(','),typeid:d.id},
							loaddingEle:layero,
							success:function(){
								top.layer.close(index);
								sen.msg('角色分配成功');
							}
						});
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
		<input type="hidden" name="@code" value="like_anywhere" />
		<input type="hidden" name="@name" value="like_anywhere" />
		<label>类别编号：</label><input type="text" name="code" class="easyui-textbox" style="width:120px">
		<label>类别名称：</label><input type="text" name="name" class="easyui-textbox" style="width:120px">
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