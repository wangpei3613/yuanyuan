<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>指标库管理</title>
	<%@ include file="../../index/lib.jsp" %>
	<script type="text/javascript">
	var row = {};
	var menu = {
		parameter:{path:'${path}/model/index/version/toParameter?id=',id:null},
		conversion:{path:'${path}/model/index/version/toConversion?id=',id:null},
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
					iframe.prop('src',menu[menuId].path+row.indexversionid);
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
	<div data-options="title:'版本指标配置',fit:true,cls:'pd3 sen-noscroll'">
		<iframe scrolling="auto" frameborder="0"  src="${path }/model/index/version/toIndex?id=${param.id}" style="width:100%;height:100%;"></iframe>
	</div>
	<div data-options="title:'版本指标参数配置',fit:true,cls:'pd3 sen-noscroll'">
		<iframe data-id="parameter" scrolling="auto" frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
	</div>
	<div data-options="title:'版本指标分值转换配置',fit:true,cls:'pd3 sen-noscroll'">
		<iframe data-id="conversion" scrolling="auto" frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
	</div>
</body>
</html>