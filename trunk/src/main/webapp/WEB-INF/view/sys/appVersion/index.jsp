<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>版本管理</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript" src="${path}/styles/js/jquery.qrcode.min.js"></script>
	<style type="text/css">
	.messager-window{
	  z-index: 9036
	}
	.window-shadow{
	 z-index: 9036
	}
	body>.layout-panel-center{
		
	}
	 .divcontainer{
      margin:0 auto;
      width:200px;
      height: 200px;  
    }
     canvas{  
        border:1px solid black;  
        margin-left: -10;
        margin-right: auto;
        width:200px;
        height: 200px;
     } 
	</style>
	<script type="text/javascript">
	var grid;
	$(function(){
		grid = $('#sen-datagrid').grid({
			url:'${path }/system/appversion/select',
			sortName:'opTime',
		    sortOrder:'desc',
			columns:[[
				{field:'id',hidden:true},
// 				{field:'downUrls',title:'下载地址',width:120,sortable:true,
// 					formatter:function(value,row,index){
// 						var url = row.downUrls;
// 						var aa ="<div id='code' class='divcontainer'></div>";
// 						$("#code").qrcode(url);
// 						return aa;
// 					}
// 				},
				{field:'currentV',title:'当前版本',width:120,sortable:true},
// 				{field:'opNo',title:'操作人编码',width:200,sortable:true},
				{field:'opName',title:'操作人姓名',width:120,sortable:true},
				{field:'opTime',title:'上传时间',width:130,sortable:true}
			]],
			loadFilterFn:function(data){
				return data;
			},
			onDblClickRow :function(rowIndex,rowData){
				console.log(rowData.downUrls);
				$("#code").qrcode(url);
			}
		});
		
	});
	
	function openAdd(){
		layer.win({
			  title: '新增用户',
			  area: ['400px', '400px'],
			  content: '${path}/system/appversion/toAdd',
			  btn:['保存','关闭'],
			  yes:function(index, layero){
				  var win = layero.find('iframe')[0].contentWindow;
				  var	  form = win.$('#form1');
				  var form2 = win.$('#form2');
				  if(form.form('validate')&&form2.form('validate')){
					  var flag =win.uploads(index,layero);
					  grid.grid('load');
				  }
				  return false;
			  }
			}); 
	}
	
	function openEdit(){
		if(grid.grid('selectOne')){
			layer.win({
				  title: '修改用户',
				  area: ['400px', '400px'],
				  content: '${path}/system/appversion/toAdd?id='+grid.grid('getSelected').id,
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow;
					  var	  form = win.$('#form1');
					  var form2 = win.$('#form2');
					  if(form.form('validate')&&form2.form('validate')){
						  var flag =win.uploads(index,layero);
						  grid.grid('load');
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
					  url:'/system/appversion/delAppVersion',
					  data:{data:grid.grid('getSelected').id},
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