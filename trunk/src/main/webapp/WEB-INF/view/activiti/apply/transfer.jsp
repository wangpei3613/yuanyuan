<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>移交</title>
	<%@ include file="../../index/lib.jsp" %>
	<style type="text/css">
	.sen-edit-table .sen-edit-element{width: 250px;}
	.signImg{
		width: 200px;
		height: 80px;
		border-top-width: 1px;
	}
	.signImg img{
		width: 100%;
		height: 100%;
	}
	</style>
	<script type="text/javascript">
	var d;
	function init(data){
		d = data;
		var h = [],taches = {},auto = [{id:'auto',userName:'',nickName:'系统自动选择'}];
		//处理当前环节和移交人员
		h.push('<tr><td style="width: 30%;">当前环节：<span class="sen-required">*</span></td>');
		h.push('<td><input type="text" class="sen-edit-element easyui-textbox" value="'+d.tache.name+'" readonly/>');
		h.push('<input name="transferUserid" type="hidden" value="'+d.transferUser.id+'"/>');
		h.push('</td></tr>');
		h.push('<tr><td>移交人员：<span class="sen-required">*</span></td>');
		h.push('<td><input type="text" class="sen-edit-element easyui-textbox" value="'+d.transferUser.nickName+'" readonly/></td></tr>');
		//处理接收人员
		h.push('<tr class="next-tache next-tache-man"><td>接收人员：<span class="sen-required">*</span></td>');
		h.push('<td><select name="transferReceiveid" class="sen-edit-element easyui-combogrid" data-options="readonly:'+(d.tache.transfer_people_type=='1'?'false':'true')+',value:\''+((d.tache.transfer_people_type=='1'?'':'auto'))+'\',data:'+JSON.stringify(d.tache.transfer_people_type=='1'?d.tache.users:auto).replace(/\"/g,'\'')+',panelWidth: 300,idField: \'id\',textField:\'nickName\',columns:[[{field:\'userName\',title:\'账号\',width:100},{field:\'nickName\',title:\'昵称\',width:100},{field:\'deptName\',title:\'部门\',width:200}]],fitColumns: true"></select></td></tr>');
		//移交意见
		h.push('<tr><td>移交意见：<span class="sen-required">*</span></td>');
		h.push('<td><input type="text" class="sen-edit-element easyui-textbox" name="options" style="height: 60px;" multiline="true" required/></td></tr>');
		//处理签名
		if(d.tache.transfer_sign == '1'){
			h.push('<tr class="sign"><td>签名：<span class="sen-required">*</span></td>');
			h.push('<td><div class="panel-body signImg"></div></td></tr>');
		}
		$('.sen-edit-table').html(h.join(''));
		$.parser.parse('.sen-edit-table');
		if(d.tache.transfer_people_type == '1' && !d.tache.users){
			sen.alert("未找到接收人员，请联系管理员");
		}
	}
	</script>
</head>
<body>
	<form action="">
		<input id="signData" type="hidden" name="sign_data"/>  
		<table class="sen-edit-table">
			
		</table>
	</form>
</body>
</html>