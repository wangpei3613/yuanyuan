<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>环节配置编辑</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	$(function(){
		$('#tache_type').combobox({
			onChange:tacheTypeChange
		});
		$('#is_transfer').combobox({
			onChange:transferChange
		});
		tacheTypeChange('${v.tache_type }');
		transferChange('${v.is_transfer }');  
	});
	function tacheTypeChange(v){
		if(v == '3'){
			$('.sign').show();
			$('#sign_type').combobox({required:true});
			$('#sign_ratio').numberbox({required:true});
		}else{
			$('.sign').hide();
			$('#sign_type').combobox({required:false});
			$('#sign_ratio').numberbox({required:false});
		}
	}
	function transferChange(v){
		if(v == '1'){
			$('.transfer').show();
			$('#transfer_sign').combobox({required:true});
			$('#transfer_people_type').combobox({required:true});
		}else{
			$('.transfer').hide();
			$('#transfer_sign').combobox({required:false});
			$('#transfer_people_type').combobox({required:false});
		}
	}
	</script>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>  
		<input type="hidden" name="actid" value="${v.actid }"/>  
		<table class="sen-edit-table">
			<tr>
				<td style="width: 18%;">环节名称：<span class="sen-required">*</span></td>
				<td style="width: 32%;"><input type="text" class="sen-edit-element easyui-textbox" name="name" value="${v.name }" required/></td>
				<td style="width: 18%;">环节编码：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="tachecode" value="${v.tachecode }" required/></td> 
			</tr>
			<tr>
				<td>环节顺序：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-numberbox" name="ordernum" value="${v.ordernum }" validType="integer" required/></td>
				<td>环节类型：<span class="sen-required">*</span></td>
				<td><input id="tache_type" type="text" class="sen-edit-element easyui-combobox" value="${v.tache_type }" data-options="url:'${path}/sys/sysData?type=SEN_ACT_TACHE_TYPE'" name="tache_type" required/></td> 
			</tr>
			<tr>
				<td>是否需要签名：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.needsignature }" data-options="url:'${path}/sys/sysData?type=SEN_IF'" name="needsignature" required/></td>
				<td>是否允许复议：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.sign_return }" data-options="url:'${path}/sys/sysData?type=SEN_IF'" name="sign_return" required/></td> 
			</tr>
			<tr>
				<td>人员选择方式：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.people_select_type }" data-options="url:'${path}/sys/sysData?type=SEN_ACT_SELECT_TYPE'" name="people_select_type" required/></td>
				<td>是否允许移交：<span class="sen-required">*</span></td>
				<td><input id="is_transfer" type="text" class="sen-edit-element easyui-combobox" value="${v.is_transfer }" data-options="url:'${path}/sys/sysData?type=SEN_IF'" name="is_transfer" required/></td> 
			</tr>
			<tr class="transfer">
				<td>移交是否签名：<span class="sen-required">*</span></td>
				<td><input id="transfer_sign" type="text" class="sen-edit-element easyui-combobox" value="${v.transfer_sign }" data-options="url:'${path}/sys/sysData?type=SEN_IF'" name="transfer_sign" /></td>
				<td>移交人员选择方式：<span class="sen-required">*</span></td>
				<td><input id="transfer_people_type" type="text" class="sen-edit-element easyui-combobox" value="${v.transfer_people_type }" data-options="url:'${path}/sys/sysData?type=SEN_ACT_SELECT_TYPE'" name="transfer_people_type" /></td> 
			</tr>
			<tr class="sign">
				<td>会签通过比例(%)：<span class="sen-required">*</span></td>
				<td><input id="sign_ratio" type="text" class="sen-edit-element easyui-textbox" name="sign_ratio" value="${v.sign_ratio }" validType="intOrFloat" /></td>
				<td>会签是否全员审批：<span class="sen-required">*</span></td>
				<td><input id="sign_type" type="text" class="sen-edit-element easyui-combobox" value="${v.sign_type }" data-options="url:'${path}/sys/sysData?type=SEN_IF'" name="sign_type" /></td> 
			</tr>
			<tr class="sign">
				<td>会签人员偏移量：</td>
				<td colspan="3"><input id="sign_offset" type="text" class="sen-edit-element easyui-numberbox" name="sign_offset" value="${v.sign_offset }" validType="integer"/></td>
			</tr>
			<tr>
				<td>提交前回调：</td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="saveurl" value="${v.saveurl }" style="width: 570px"/></td>
			</tr>
			<tr>
				<td>提交后回调：</td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="redirect" value="${v.redirect }" style="width: 570px"/></td>
			</tr>
			<tr class="transfer">
				<td>移交前回调：</td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="transfer_saveurl" value="${v.transfer_saveurl }" style="width: 570px"/></td>
			</tr>
			<tr class="transfer">
				<td>移交后回调：</td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="transfer_redirect" value="${v.transfer_redirect }" style="width: 570px"/></td>
			</tr>
			<tr>
				<td>时效：</td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="prescription" value="${v.prescription }" multiline="true" style="width: 570px"/></td>
			</tr>
			<tr>
				<td>备注：</td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" value="${v.remark }" name="remark" multiline="true" style="width: 570px"	/></td> 
			</tr>
		</table>
	</form>
</body>
</html>