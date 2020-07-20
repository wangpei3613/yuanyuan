<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>指标配置编辑</title>
	<%@ include file="../../../index/lib.jsp" %>
	<script type="text/javascript">
	$(function(){
		$('#category').combobox({
			onChange:categoryChange
		});
		categoryChange('${v.category }');
	});
	function categoryChange(v){
		if(!v || v == '1'){
			$('.formula').hide();
			$('#formula').textbox({required:false});
		}else{
			$('.formula').show();
			$('#formula').textbox({required:true});
		}
	}
	</script>
</head>
<body>
	<form action="">
		<input type="hidden" name="id" value="${v.id }"/>  
		<table class="sen-edit-table">
			<tr>
				<td style="width: 18%;">指标编码：<span class="sen-required">*</span></td>
				<td style="width: 32%;"><input type="text" class="sen-edit-element easyui-textbox" name="code" value="${v.code==null?'系统自动生成':v.code }" required readonly/></td>
				<td style="width: 18%;">指标名称：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" name="name" value="${v.name }" required/></td> 
			</tr>
			<tr>
				<td>指标级别：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" name="level" value="${v.level }"  required data-options="url:'${path}/sys/sysData?type=MODEL_INDEX_LIBRARY_LEVEL'" readonly/></td>
				<td>排序号：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-numberbox" value="${v.sort }" validType="integer" name="sort" required/></td> 
			</tr>
			<tr>
				<td>指标大类：<span class="sen-required">*</span></td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-combotree" value="${v.pid }" data-options="url:'${path }/model/index/config/getTreeGrid?level=1'" name="pid" required style="width: 555px"/></td>
			</tr>
			<tr>
				<td>状态：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-combobox" value="${v.status }" data-options="url:'${path}/sys/sysData?type=SEN_ENABLE_STATE'" name="status" required/></td>
				<td>指标满分：</td>
				<td><input type="text" class="sen-edit-element easyui-textbox" value="${v.maxscore }" name="maxscore" validType="checkNum"/></td> 
			</tr>
			<tr>
				<td>指标类别：<span class="sen-required">*</span></td>
				<td><input id="category" type="text" class="sen-edit-element easyui-combobox" value="${v.category }" data-options="url:'${path}/sys/sysData?type=MODEL_INDEX_LIBRARY_CATEGORY'" name="category" required/></td>
				<td>取值参数：</td>
				<td><input id="transfer_people_type" type="text" class="sen-edit-element easyui-textbox" value="${v.argument }" name="argument" /></td> 
			</tr>
			<tr>
				<td>指标内容：<span class="sen-required">*</span></td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="content" value="${v.content }" multiline="true" style="width: 555px;height: 50px;" required/></td>
			</tr>
			<tr class="formula">
				<td>指标公式：<span class="sen-required">*</span></td>
				<td colspan="3"><input id="formula" type="text" class="sen-edit-element easyui-textbox" name="formula" value="${v.formula }" multiline="true" style="width: 555px;height: 50px;"/></td>
			</tr>
			<tr>
				<td>备注：</td>
				<td colspan="3"><input type="text" class="sen-edit-element easyui-textbox" name="remark" value="${v.remark }" multiline="true" style="width: 555px;height: 50px;"/></td>
			</tr>
		</table>
	</form>
</body>
</html>