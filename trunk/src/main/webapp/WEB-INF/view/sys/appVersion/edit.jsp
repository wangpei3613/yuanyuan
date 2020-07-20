<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>用户编辑</title>
	<%@ include file="../../index/lib.jsp" %>
	<style type="text/css">
	.div1{
float: left;
height: 41px;
background: #FFFFFF;
width: 160px;
position:relative;
margin-top: -10;
}

.div2{
text-align:center;

padding-top:12px;
font-size:15px;
font-weight:300
}
	.inputstyle{
    width: 160px;
    height: 41px;
    cursor: pointer;
    font-size: 25px;
    outline: medium none;
    position: absolute;
    filter:alpha(opacity=0);
    -moz-opacity:0;
    opacity:0; 
    left:0px;
    top: 0px;
}
	</style>
	<script type="text/javascript">
	$(function(){
		if('${v.id }'){
		}
	});
	
	function uploads(ins){
		 var currentV = $("#currentV").val();
		 if(!currentV){
			 alert("请输入当前版本");
			 return;
		 }
		 var url1 = "${path}/system/appversion/upload?filenam="+currentV;
		 $.messager.progress(); 
		 $("#form2").form({
				url : url1,//
				async:true,
				onSubmit : function() {
					if (document.getElementById("file1").value  == "") {
						$.messager.alert('警告', "请选择文件！", 'warning');
						$.messager.progress('close'); 
						return false;
					}
				},
				success : function(data) {
					data = eval('(' + data + ')');
					//console.log("edi="+data.success);
					if(data.success){
						 sen.ajax({
	 						  url:'/system/appversion/openAdd',
	 						  data:$("#form1").serializeObject(),
	 						  success:function(){
	 							  sen.msg('保存成功');
	 							 $.messager.progress('close'); 
	 							 top.layer.close(ins);
	 						  }
	 					  });
					}else{
						$.messager.progress('close'); 
					}
				},
				error: function(){
					console.log('错了');
					$.messager.progress('close'); 
				}
			});
		 $("#form2").submit();
	}
	</script>
</head>
<body>
	<form id="form1" action="">
		<input type="hidden" name="id" value="${v.id }"/>  
		<input type="hidden" name="type" value="1"/>  
		<table class="sen-edit-table">
			<tr>
				<td>当前版本：<span class="sen-required">*</span></td>
				<td><input type="text" class="sen-edit-element easyui-textbox" id="currentV" name="currentV" value="${v.currentV }" data-options="" onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" required/></td> 
			</tr>
		</table>
	</form>
	<form id="form2" method="post" enctype="multipart/form-data">
	   <table class="sen-edit-table">
	   		<tr>
	   			<td></td>
	   			<td style="width: 80%"> 
	   			<div class="div1">
					   <div class="div2">请上传新的版本</div>
					   <input id="file1" type="file"  name="file" class="inputstyle">
					 </div>
			   </td>
	   		</tr>
	   </table>
	   
	</form>
</body>
</html>