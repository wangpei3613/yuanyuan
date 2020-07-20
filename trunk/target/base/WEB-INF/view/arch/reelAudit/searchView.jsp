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
                url:'${path }/arch/reel/search/getSearchPagerAdmin',
                sortName:'addtime',
                sortOrder:'desc',
                columns:[[
                    {field:'reel_no',title:'案卷编号',width:200,sortable:true,align:'center'},
                    {field:'reel_name',title:'案卷名称',width:200,sortable:true},
                    {field:'brow_status',title:'借阅状态',width:200,dictType:'SEN_REEL_BORROW_STATE'},
                    {field:'audit_state',title:'审批状态',width:200,dictType:'SEN_ACTICITI_STATE'},
                    {field:'brow_name',title:'借阅人',width:200},
                    {field:'brow_date',title:'借阅时间',width:200},
                    {field:'back_date',title:'归还时间',width:200},
                    {field:'real_back_date',title:'实际归还时间',width:200},
                    {field:'apply_name',title:'申请人',width:200},
                    {field:'addtime',title:'提交时间',width:200}
                ]]
            });
        });
        function guiHuan(){
            if(grid.grid('selectOne')){
                if(grid.grid('getSelected').audit_state=='3'){
                    if(grid.grid('getSelected').brow_status=='1' || grid.grid('getSelected').brow_status=='3'){
                        sen.ajax({
                            url:'/arch/reel/search/guiHuan?id='+grid.grid('getSelected').id,
                            success:function(){
                                sen.msg('归还成功');
                                grid.grid('load');
                            }
                        });
                    }else{
                        sen.alert("已归还");
                    }
				}else{
                    sen.alert("未审核通过");
				}
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
		<input type="hidden" name="@r.reel_no" value="like_anywhere" />
		<input type="hidden" name="@r.reel_name" value="like_anywhere" />
		<label>档案编号：</label><input type="text" name="r.reel_no" class="easyui-textbox" style="width:120px">
		<label>档案名称：</label><input type="text" name="r.reel_name" class="easyui-textbox" style="width:120px">
		<label>档案借阅状态：</label><input type="text" class="sen-edit-element easyui-combobox" name="b.brow_status" data-options="url:'${path}/sys/sysData?type=SEN_REEL_BORROW_STATE'"/>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search">查询</a>
		<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
	</form>
</div>
<div class="sen-crud-page-center" data-options="region:'center',border:false">
	<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-information" onclick="guiHuan()" plain="true">归还</a>
	<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-information" onclick="viewAuditRecord()" plain="true">审批记录</a>
	<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
</div>
</body>
</html>