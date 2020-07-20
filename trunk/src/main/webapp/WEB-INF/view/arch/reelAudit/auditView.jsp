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
                url:'${path }/arch/reel/search/getAuditPager',
                sortName:'addtime',
                sortOrder:'desc',
                columns:[[
                    {field:'reel_no',title:'案卷编号',width:200,sortable:true,align:'center'},
                    {field:'reel_name',title:'案卷名称',width:200,sortable:true},
                    {field:'audit_state',title:'审批状态',width:200,dictType:'SEN_ACTICITI_STATE'},
                    {field:'brow_name',title:'借阅人',width:200},
                    {field:'brow_date',title:'借阅时间',width:200},
                    {field:'back_date',title:'归还时间',width:200},
                    {field:'apply_name',title:'申请人',width:200},
                    {field:'addtime',title:'提交时间',width:200},
                    {field:'currtachenames',title:'当前环节',width:200},
                    {field:'nextttachenames',title:'下一环节',width:200},
                    {field:'sendMan',title:'传来人',width:200},
                    {field:'sent_time',title:'传来时间',width:200}
                ]]
            });
        });
        function audit(){
            console.log(grid.grid('getSelected'));
            if(grid.grid('selectOne')){
                sen.applyAudit(grid.grid('getSelected').applytacheid,function(){
                    sen.msg('审批成功');
                    grid.grid('load');
                });
            }
        }
        function viewAuditRecord(){
            if(grid.grid('selectOne')){
                layer.win({
                    title: '审批记录',
                    area: [top.window.innerWidth-300+'px', top.window.innerHeight-100+'px'],
                    content: '${path}/act/apply/info/toViewAuditRecord?id='+grid.grid('getSelected').applyid
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
	<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="audit()" plain="true">审批</a>
	<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-information" onclick="viewAuditRecord()" plain="true">审批记录</a>
	<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
</div>
</body>
</html>