<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>版本指标配置编辑</title>
	<%@ include file="../../../index/lib.jsp" %>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/> 
		<input type="hidden" name="versionid" value="${v.versionid }"/> 
		<input type="hidden" name="indexid" value="${v.indexid }"/>  
		<table class="sen-edit-table">
			<tr>
				<td style="width: 18%;">排序号：<span class="sen-required">*</span></td>
				<td style="width: 32%;"><input type="text" class="sen-edit-element easyui-numberbox" value="${v.sort }" validType="integer" name="sort" required/></td>
				<td style="width: 18%;">状态：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.status }" data-options="url:'${path}/sys/sysData?type=SEN_ENABLE_STATE'" name="status" required/></td> 
			</tr>
			<tr>
				<td>指标满分：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.maxscore }" name="maxscore" validType="checkNum"/></td>
				<td>取值参数：</td>
				<td><input id="transfer_people_type" type="text" class="sen-edit-element easyui-textbox" value="${v.argument }" name="argument" /></td> 
			</tr>
			<tr>
				<td>指标内容：<span class="sen-required">*</span></td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="content" value="${v.content }" multiline="true" style="width: 555px;height: 50px;" required/></td>
			</tr>
			<c:if test="${param.category == '2' }">
				<tr>
					<td>指标公式：<span class="sen-required">*</span></td>
					<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="formula" value="${v.formula }" multiline="true" style="width: 555px;height: 50px;" required/></td>
				</tr>
			</c:if>
			<tr>
				<td>备注：</td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="remark" value="${v.remark }" multiline="true" style="width: 555px;height: 50px;"/></td>
			</tr>
		</table>
	</form>
</body>
</html>