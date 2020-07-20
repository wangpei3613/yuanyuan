<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>分配模型配置</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var row = {};
	var menu = {
		rule:{path:'${path}/sen/core/allo/toAlloRule?id=',id:null}
	};
	$(function(){
		disableTabs();
		$('#tabs').tabs({
			onSelect:function(title,index){
				var tab = $('#tabs').tabs('getTab',index),
					iframe = tab.children('iframe'),
					menuId = iframe.attr('data-id');
				if(menuId && menu[menuId].id != row.id){
					menu[menuId].id = row.id;
					iframe.prop('src',menu[menuId].path+row.id);
				}
			}
		});
	});
	function enableTabs(indexs){
		doTabs(indexs,'enableTab');
	}
	function disableTabs(indexs){
		doTabs(indexs,'disableTab');
	}
	function doTabs(indexs, str){
		if(indexs){
			var ids = String(indexs).split(',');
			for(var i=0;i<ids.length;i++){
				$('#tabs').tabs(str,Number(ids[i]));
			}
		}else{
			var tabs = $('#tabs').tabs('tabs');
			for(var i=1;i<tabs.length;i++){
				$('#tabs').tabs(str,i);
			}
		}
	}
	</script>
</head>
<body id="tabs" class="easyui-tabs" style="width:100%;height:100%;">
	<div data-options="title:'分配模型配置',fit:true,cls:'pd3 sen-noscroll'">
		<iframe scrolling="auto" frameborder="0"  src="${path }/sen/core/allo/toAllo?__=${param.__}" style="width:100%;height:100%;"></iframe>
	</div>
	<div data-options="title:'分配模型规则配置',fit:true,cls:'pd3 sen-noscroll'">
		<iframe data-id="rule" scrolling="auto" frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
	</div>
</body>
</html>