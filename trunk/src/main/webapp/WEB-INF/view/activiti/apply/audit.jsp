<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>审批</title>
	<%@ include file="../../index/lib.jsp" %>
	<style type="text/css">
	.sen-edit-table .sen-edit-element{width: 250px;}
	.signImg{
		width: 200px;
		height: 80px;
		border-top-width: 1px;
		overflow: hidden;
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
		//处理审批结果
		h.push('<tr><td style="width: 30%;">审批结果：<span class="sen-required">*</span></td>');
		h.push('<td><input type="radio" name="result" value="1" checked/>同意');
		if(d.tache.tache_type != '1'){
			if(d.tache.sign_return == '1'){
				h.push('<input type="radio" name="result" value="2" />复议');
			}
			h.push('<input type="radio" name="result" value="3" />否决');
		}
		h.push('</td></tr>');
		//处理前进环节
		if(d.nextTache && d.nextTache.length>0){
			h.push('<tr class="next-tache"><td>下环节：<span class="sen-required">*</span></td>');
			h.push('<td><select id="nextTache" name="nextTache" class="sen-edit-element easyui-combobox" required>');
			for(var i=0;i<d.nextTache.length;i++){
				h.push('<option value="'+d.nextTache[i].id+'">'+d.nextTache[i].name+'</option>');
			}
			h.push('</select>');
			h.push('</td></tr>');
			for(var i=0;i<d.nextTache.length;i++){
				taches[d.nextTache[i].id] = d.nextTache[i];
				h.push('<tr class="next-tache next-tache-man"><td>下环节审批人：<span class="sen-required">*</span></td>');
				h.push('<td><select name="'+d.nextTache[i].id+'" id="'+d.nextTache[i].id+'" class="sen-edit-element easyui-combogrid" data-options="multiple:'+(d.nextTache[i].tache_type=='3'?'true':'false')+',readonly:'+(d.nextTache[i].people_select_type=='1'?'false':'true')+',value:\''+((d.nextTache[i].people_select_type=='1'?'':'auto'))+'\',data:'+JSON.stringify(d.nextTache[i].people_select_type=='1'?d.nextTache[i].users:auto).replace(/\"/g,'\'')+',panelWidth: 300,idField: \'id\',textField:\'nickName\',columns:[[{field:\'userName\',title:\'账号\',width:100},{field:\'nickName\',title:\'昵称\',width:100},{field:\'deptName\',title:\'部门\',width:200}]],fitColumns: true"></select></td></tr>');
			}
		}
		//处理复议环节
		if(d.prevTache && d.prevTache.length>0){
			h.push('<tr class="prev-tache"><td>复议环节：<span class="sen-required">*</span></td>');
			h.push('<td><select id="prevTache" name="prevTache" class="sen-edit-element easyui-combobox">');
			for(var i=0;i<d.prevTache.length;i++){
				h.push('<option value="'+d.prevTache[i].id+'">'+d.prevTache[i].name+'</option>');
			}
			h.push('</select>');
			h.push('</td></tr>');
			for(var i=0;i<d.prevTache.length;i++){
				taches[d.prevTache[i].id] = d.prevTache[i];
				h.push('<tr class="prev-tache prev-tache-man"><td>复议环节审批人：<span class="sen-required">*</span></td>');
				h.push('<td><select name="'+d.prevTache[i].id+'" id="'+d.prevTache[i].id+'" class="sen-edit-element easyui-combogrid" data-options="multiple:'+(d.prevTache[i].tache_type=='3'?'true':'false')+',readonly:'+(d.prevTache[i].people_select_type=='1'?'false':'true')+',value:\''+((d.prevTache[i].people_select_type=='1'?'':'auto'))+'\',data:'+JSON.stringify(d.prevTache[i].people_select_type=='1'?d.prevTache[i].users:auto).replace(/\"/g,'\'')+',panelWidth: 300,idField: \'id\',textField:\'nickName\',columns:[[{field:\'userName\',title:\'账号\',width:100},{field:\'nickName\',title:\'昵称\',width:100},{field:\'deptName\',title:\'部门\',width:200}]],fitColumns: true"></select></td></tr>');
			}
		}
		//审批意见
		h.push('<tr><td>审批意见：<span class="sen-required">*</span></td>');
		h.push('<td><input type="text" class="sen-edit-element easyui-textbox" id="options" name="options" value="同意" style="height: 60px;" multiline="true" required/></td></tr>');
		//处理签名
		if(d.tache.needsignature == '1'){
			h.push('<tr class="sign"><td>签名：<span class="sen-required">*</span></td>');
			h.push('<td><div class="panel-body signImg"></div></td></tr>');
		}
		$('.sen-edit-table').html(h.join(''));
		$.parser.parse('.sen-edit-table');
		$('.next-tache-man,.prev-tache,prev-tache-man').hide();
		//处理事件
		$('input[type=radio]').on('change',function(){
			var value = $(this).val();
			$('.next-tache,.prev-tache').hide();
			$('#nextTache').combobox({required:false});
			$('#prevTache').combobox({required:false});
			$('.next-tache-man select').combogrid({required:false});
			$('.prev-tache-man select').combogrid({required:false});
			if(value == '1'){
				$('.next-tache:not(.next-tache-man)').show();
				$('#nextTache').combobox({required:true});
				changeNextTache($('#nextTache').combobox('getValue') || d.nextTache[0].id);
			}else if(value == '2'){
				$('.prev-tache:not(.prev-tache-man)').show();
				$('#prevTache').combobox({required:true});
				changePrevTache($('#prevTache').combobox('getValue') || d.prevTache[0].id);
			}
		});
		$('#nextTache').combobox({onChange:changeNextTache});
		$('#prevTache').combobox({onChange:changePrevTache});
		//若下环节存在
		if(d.nextTache && d.nextTache.length > 0){
			changeNextTache(d.nextTache[0].id);
		}
		function changeNextTache(value){
			var sel = $('#'+value),tache = taches[value];
			$('.next-tache-man').hide();
			$('.next-tache-man select').combogrid({required:false});
			if(sel.length > 0){
				if(tache.tache_type != '4'){
					sel.closest('.next-tache-man').show();
					sel.combogrid({required:true});
					if(!tache.users || tache.users.length==0){
						sen.alert("环节【"+tache.name+"】未找到有权限审批人");
					}
				}
			}
		}
		function changePrevTache(value){
			var sel = $('#'+value),tache = taches[value];
			$('.prev-tache-man').hide();
			$('.prev-tache-man select').combogrid({required:false});
			if(sel.length > 0){
				if(tache.tache_type != '4'){
					sel.closest('.prev-tache-man').show();
					sel.combogrid({required:true});
					if(!tache.users || tache.users.length==0){
						sen.alert("环节【"+tache.name+"】未找到有权限审批人");
					}
				}
			}
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