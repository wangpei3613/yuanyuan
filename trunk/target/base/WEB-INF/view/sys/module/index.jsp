<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>功能菜单管理</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').tgrid({
			url:'${path }/system/module/getTreeGrid',
		    treeField:'moduleName',
		    fitColumns:false,
			columns:[[
				{field:'moduleName',title:'菜单名称',width:200},
				{field:'moduleno',title:'菜单编号',width:150},
				{field:'isuse',title:'是否使用',width:80,fixed:true,align:'center',dictType:'SEN_ENABLE_STATE'},
				{field:'orderNumber',title:'排列顺序',width:60,fixed:true,align:'center'},
				{field:'icons',title:'菜单图标',width:160,showTitle:false,formatter:function(v,row){
					return v?('<span class="sen-icon '+v+'"></span>'+v):'';
				}},
				{field:'weburl',title:'菜单路径',width:200},
				{field:'syscode',title:'所属系统',width:100,align:'center',dictType:'SEN_SYSCODE'},
				{field:'xxx',title:'权限',width:500,formatter:function(v,row){
					var d = row.auths,h = [];
					if(d && d.length>0){
						for(var i=0;i<d.length;i++){
							h.push(d[i].name);
						}
					}
					return h.join('、');
				}}
			]]
		});
	});
	function openAdd(){
		var d = grid.treegrid('getSelected'),pid = '';
		if(d && d.moduletype=='1'){
			pid = d.id;
		}
		layer.win({
		  title: '新增功能菜单',
		  area: ['400px', '500px'],
		  content: '${path}/system/module/edit?pid='+pid,
		  btn:['保存','关闭'],
		  yes:function(index, layero){
			  var win = layero.find('iframe')[0].contentWindow,
			  	  form = win.$('form');
			  if(form.form('validate')){
				  sen.ajax({
					  url:'/system/module/save',
					  data:form.serializeObject(),
					  loaddingEle:layero,
					  success:function(){
						  top.layer.close(index);
						  sen.msg('保存成功');
						  grid.treegrid('load');
					  }
				  });
			  }
			  return false;
		  }
		}); 
	}
	function openEdit(){
		var d = grid.treegrid('getSelected');
		if(d){
		     layer.win({
				  title: '修改功能菜单',
				  area: ['400px', '500px'],
				  content: '${path}/system/module/edit?id='+d.id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/system/module/save',
							  data:form.serializeObject(),
							  loaddingEle:layero,
							  success:function(){
								  top.layer.close(index);
								  sen.msg('修改成功');
								  grid.treegrid('load');
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
		if(d){
			sen.confirm('确定要删除吗?',function(index){
				sen.ajax({
				      url:'/system/module/del',
					  data:{id:d.id},
					  success:function(){
						  sen.msg('删除成功');
						  grid.treegrid('clearSelections');
						  grid.treegrid('load');
					  }
			  	});
			});
		}else{
			sen.alert('请选择一条数据');
		}
	}
	function auth(){
		var d = grid.treegrid('getSelected');
		if(d){
			if(d.moduletype == '2'){
				layer.win({
					  title: '菜单权限配置',
					  area: ['1000px', '600px'],
					  content: '${path}/system/module/auth/index?id='+d.id,
					  end:function(){
						  grid.treegrid('reload');
					  }
				}); 
			}else{
				sen.alert('请选择菜单');
			}
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