<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>档案借阅统计</title>
	<%@ include file="../../index/lib.jsp" %>
	<style>
		.tongjitable td{
			width:16.7%;
			text-align: center;
		}
	</style>
	<script type="text/javascript">
        var grid;
        $(function(){
            sen.ajax({
                url:'/arch/reel/search/getTongJiResult',
                success:function(d){
                    $("#guihuanTotal").html(d.guihuanTotal);
                    $("#anshiguihuanTotal").html(d.anshiguihuanTotal);
                    $("#yuqiguihuanTotal").html(d.yuqiguihuanTotal);
                    $("#weiguihuanTotal").html(d.weiguihuanTotal);
                    $("#qixianneiweiguihuanTotal").html(d.qixianneiweiguihuanTotal);
                    $("#yuqiweiguihuanTotal").html(d.yuqiweiguihuanTotal);
                }
            });
            grid = $('#sen-datagrid').grid({
                url:'${path }/arch/reel/search/getSearchTongJiPager',
                sortName:'addtime',
                sortOrder:'desc',
                columns:[[
                    {field:'reel_no',title:'案卷编号',width:200,sortable:true,align:'center'},
                    {field:'reel_name',title:'案卷名称',width:200,sortable:true},
                    {field:'brow_status',title:'档案状态',width:200,dictType:'SEN_REEL_BORROW_STATE'},
                    {field:'brow_name',title:'借阅人',width:200},
                    {field:'brow_date',title:'借阅时间',width:200},
                    {field:'back_date',title:'归还时间',width:200},
                    {field:'real_back_date',title:'实际归还时间',width:200},
                    {field:'apply_name',title:'申请人',width:200},
                    {field:'addtime',title:'提交时间',width:200}
                ]]
            });
        });
	</script>
</head>
<body class="easyui-layout sen-crud-page">
<div class="sen-crud-page-center" data-options="region:'center',border:false">
	<table class="tongjitable" style="border:1px solid #0094ff;">
		<tr>
			<td>已归还</td>
			<td>期限内归还</td>
			<td>逾期归还</td>
			<td>未归还</td>
			<td>期限内未归还</td>
			<td>逾期未归还</td>
		</tr>
		<tr>
			<td id="guihuanTotal"></td>
			<td id="anshiguihuanTotal"></td>
			<td id="yuqiguihuanTotal"></td>
			<td id="weiguihuanTotal"></td>
			<td id="qixianneiweiguihuanTotal"></td>
			<td id="yuqiweiguihuanTotal"></td>
		</tr>
	</table>
	<div class="sen-crud-page-header" data-options="region:'north',border:false">
	<form class="sen-crud-page-form" id="sen-form">
	<input type="hidden" name="@r.reel_no" value="like_anywhere" />
	<input type="hidden" name="@r.reel_name" value="like_anywhere" />
	<input type="hidden" name="@u1.nickname" value="like_anywhere" />
	<label>档案编号：</label><input type="text" name="r.reel_no" class="easyui-textbox" style="width:120px">
	<label>档案名称：</label><input type="text" name="r.reel_name" class="easyui-textbox" style="width:120px">
	<label>档案借阅状态：</label><input type="text" class="sen-edit-element easyui-combobox" name="b.brow_status" data-options="url:'${path}/sys/sysData?type=SEN_REEL_BORROW_STATE'"/>
	<label>借阅人：</label><input type="text" name="u1.nickname" class="easyui-textbox" style="width:120px">
	<label>档案归还时间从：</label><input type="text" name="begindate" class="easyui-datebox" style="width:120px">
	<label>至：</label><input type="text" name="enddate" class="easyui-datebox" style="width:120px">
	<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search">查询</a>
	<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-undo">重置</a>
	</form>
	</div>
	<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
</div>
</body>
</html>