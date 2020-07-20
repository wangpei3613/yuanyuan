<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>档案室管理</title>
	<%@ include file="../../index/lib.jsp" %>
	<style>
		.datagrid .panel-body {border: none;}
		#sen-datagrid {overflow-x:scroll;}
	</style>
	<script type="text/javascript">
        var grid,gridFile;
        var autoSelectDa = true, autoSelectFile = false;
        $(function(){
            $('#easyui-tree').tree({
                url:'${path }/arch/menu/getTreeGrid',
                onClick: function(node){
                    autoSelectDa = true;
                    $('#sen-datagridFile').treegrid('loadData', []);
                    if(node.id != $('#arch_menu_id').val()){
                        $('#arch_menu_id').val(node.id);
                        initGrid(node.id);
                    }
                },
                onLoadSuccess: function (node, data) {
                    if (data.length > 0) {
						//找到第一个元素
                        var n = $('#easyui-tree').tree('find', data[0].id);
						//调用选中事件
                        $('#easyui-tree').tree('select', n.target);
                    }
                }
            });
            initGrid("");
        });
        function initGrid(mid){
            grid = $('#sen-datagrid').grid({
                url:'${path }/arch/reel/getPager?mid='+mid,
                sortName:'addtime',
                sortOrder:'desc',
                fitColumns:false,
                toolbar:'#sen-toolbar',
                formEl:'#sen-form-da',
                columns:[[
                    {field:'reel_no',title:'案件编号',width:100,sortable:true,align:'center'},
                    {field:'reel_name',title:'案件名称',width:00,sortable:true},
                    {field:'box_no',title:'所属档案盒',width:100,sortable:true},
                    {field:'file_date',title:'归档时间',width:100},
                    {field:'expire_date',title:'到期时间',width:100},
                    {field:'reel_status',title:'档案状态',width:100,dictType:'SEN_REEL_STATE'}
                ]],
                onSelect:function(index,row){
                    if(row.id != $('#arch_reel_id').val()) {
                        $('#arch_reel_id').val(row.id);
                        $('#reel_name').val(row.reel_name);
                        initGridFile(row.id);
                    }
                },
                onLoadSuccess:function(data){
                    if (autoSelectDa) {
                        autoSelectDa = false
                        if (data && data.rows && data.rows.length>0) {
                            autoSelectFile = true
                            grid.grid('selectRow', 0)
                        }
                    }
                }
            })
		}
        function initGridFile(arch_reel_id){
            gridFile = $('#sen-datagridFile').tgrid({
                url:'${path }/arch/reel/files/getTreeGrid?arch_reel_id='+arch_reel_id,
                treeField:'file_name',
                fitColumns:false,
                toolbar:'#sen-toolbar-file',
                formEl:'#sen-form-file',
                columns:[[
                    {field:'file_name',title:'文件名称',width:100},
                    {field:'file_type',title:'文件类型',width:100,sortable:true},
                    {field:'year',title:'年度',width:100},
                    {field:'serial',title:'文件顺序',width:100}
                ]],
                onSelect:function(index,row){

                },
                onLoadSuccess:function(data){
                    if (autoSelectFile) {
                        autoSelectFile = false
                        if (data && data.rows && data.rows.length>0) {
                            gridFile.treegrid('selectRow', 0)
                        }
                    }
                }
            })
        }
	</script>
</head>
<body class="easyui-layout sen-crud-page">
<input type="hidden" id="reel_name"></input>
<div data-options="region:'west',border:true,collapsible:false,cls:'pd3'" style="width: 15%;">
	<ul id="easyui-tree"></ul>
</div>
<div data-options="region:'center',border:true,collapsible:false,cls:'pd3'" style="width: 40%;">
	<div class="easyui-layout sen-crud-page">
		<form class="sen-crud-page-form" id="sen-form-da">
			<input id="arch_menu_id" type="hidden" name="arch_menu_id" value="" />
		</form>
		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
	</div>
</div>
<div data-options="region:'east',border:true,collapsible:false,cls:'pd3'" style="width:45%;">
	<div class="easyui-layout sen-crud-page">
		<form class="sen-crud-page-form" id="sen-form-file">
			<input id="arch_reel_id" type="hidden" name="arch_reel_id" value="" />
		</form>
		<table id="sen-datagridFile" class="sen-crud-page-datagrid"></table>
	</div>
</div>
</body>
</html>