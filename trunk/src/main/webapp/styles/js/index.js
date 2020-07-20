;(function(){
	$(function(){
		$('#sen-tabs').tabs({
			tools:[{
				iconCls:'icon-reload',
				text:'刷新',
				handler:function(){
					$('#sen-tabs').tabs('getSelected').children('iframe')[0].contentWindow.location.reload();
				}
			}]
		});
		$('#sen-tabs .tabs-header .tabs').on('contextmenu','.tabs-inner',function(e){
			var me = $(this),
				title = me.find('.tabs-title').html(),
				tab = $('#sen-tabs').tabs('getTab',title);
			$('#mm').off('click').on('click',function(ex){
				var item = $(ex.target).closest('.menu-item'),tabs = $('#sen-tabs').tabs('tabs'),titles = [];
				if(item.length > 0){
					var id = item.prop('id');
					if(id == 'mm-tabupdate'){//刷新
						tab.children('iframe')[0].contentWindow.location.reload();
					}else if(id == 'mm-tabclose'){//关闭
						title!='首页' && $('#sen-tabs').tabs('close',title);
					}else if(id == 'mm-tabcloseall'){//全部关闭
						for(var i=0;i<tabs.length;i++){
							var temp = tabs[i].panel('options').title;
							temp!='首页' && titles.push(temp);
						}
						for(var i=0;i<titles.length;i++){
							$('#sen-tabs').tabs('close',titles[i]);
						}
					}else if(id == 'mm-tabcloseother'){//除此之外全部关闭
						for(var i=0;i<tabs.length;i++){
							var temp = tabs[i].panel('options').title;
							temp!='首页' && temp!=title && titles.push(temp);
						}
						for(var i=0;i<titles.length;i++){
							$('#sen-tabs').tabs('close',titles[i]);
						}
					}
					$('#mm').menu('hide');
				}
			});
			$('#mm').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
			return false;
		});
		$('.act-loginout').on('click',function(){
			sen.confirm('您确认退出系统?',function(index){
				sen.ajax({
					  url:'/login/logout',
					  success:function(){
						  location.reload();
					  }
			  	});
			});
		});
		$('.act-changepass').on('click',function(){
			layer.win({
				  title: '修改密码',
				  area: ['400px', '300px'],
				  content: sen.path+'/master/toChangePass',
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
					  	  form = win.$('form');
					  if(form.form('validate')){
						  sen.ajax({
							  url:'/login/changePass',
							  data:form.serializeObject(),
							  success:function(){
								  top.layer.close(index);
								  sen.alert('密码修改成功，请重新登录！',{icon:1},function(){
					    				location.reload();
					    			  });
							  }
						  });
					  }
					  return false;
				  }
			});
		});
		
		$('.act-app').on('click',function(){
			sen.ajax({
				url:'/system/appversion/queryNewVersion',
				loadding:'加载菜单中...',
//				allData:true,
				success:function(d){
					if(d){
						var div = "<div id='codes' class='divcontainer'></div>";
						$('#dd').html(div);
//						$("#codes").qrcode(d.downUrls);
				        $("#codes").qrcode({
				            render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
				            text : d.downUrls,    //扫描二维码后显示的内容,可以直接填一个网址，扫描二维码后自动跳向该链接
				            width : "180",               //二维码的宽度
				            height : "180",              //二维码的高度
				            background : "#ffffff",       //二维码的后景色
				            foreground : "#000000",        //二维码的前景色
				            src: 'styles/images/TZRCB.jpg'             //二维码中间的图片
				        });
						$('#dd').dialog('open');
					}
				}
			});
			
		});
		initTheme();
		loadMenu();
	});
	var menuData = [];
	//加载菜单
	function loadMenu(){
		sen.ajax({
			url:'/master/menu',
			loadding:'加载菜单中...',
			allData:true,
			success:function(d){
				if(d && d.length>0){
					for(var i=0;i<d.length;i++){
						var h = [];
						h.push('<div class="easyui-accordion" data-options="border:false,fit:true">');
						if(d[i].children && d[i].children.length>0){
							var a = d[i].children;
							for(var j=0;j<a.length;j++){
								h.push('<div title="'+a[j].text+'" iconCls="'+a[j].icons+'" style="padding:5px;">');
								h.push('<ul class="easyui-tree sen-side-tree">');
								if(a[j].children && a[j].children.length>0){
									var b = a[j].children;
									for(var k=0;k<b.length;k++){
										var url = b[k].weburl;
										if(url){
											if(url.indexOf('?') > -1){
												url += '&__=' + b[k].moduleid;
											}else{
												url += '?__=' + b[k].moduleid;
											}
											url = location.origin+'/'+(sen.sysurl[b[k].syscode]||sen.sysurl['default'])+url;
										}
										h.push('<li iconCls="'+b[k].icons+'"><a href="javascript:;" data-icon="'+b[k].icons+'" data-link="'+url+'"><span class="sen-hidden">'+b[k].id+'</span>'+b[k].text+'</a></li>');
									}
								}
								h.push('</ul>');
								h.push('</div>');
							}
						}
						h.push('</div>');
						d[i].menu = h;
						menuData.push(d[i]);
					}
					initMenu();
				}else{
					sen.alert('当前未分配任何菜单，请联系管理员');
				}
			}
		});
	}
	//获取系统首页对于的菜单id
	function getHomepageModuleid(d){
		if(d.children && d.children.length>0){
			for(var j=0;j<d.children.length;j++){
				if(d.children[j].children && d.children[j].children.length>0){
					for(var k=0;k<d.children[j].children.length;k++){
						if(d.children[j].children[k].weburl == d.homepage){
							var url = d.homepage;
							if(url){
								if(url.indexOf('?') > -1){
									url += '&__=' + d.children[j].children[k].moduleid;
								}else{
									url += '?__=' + d.children[j].children[k].moduleid;
								}
							}
							return url;
						}
					}
				}
			}
		}
		return d.homepage;
	}
	//生成菜单
	function initMenu(){
		var h = [],defaultsys = 0;
		for(var i=0;i<menuData.length;i++){
			if(menuData[i].moduleid == menuData[i].defaultsys){
				defaultsys = i;
			}
			menuData[i].homepage = getHomepageModuleid(menuData[i]);
			h.push('<li data-id="'+menuData[i].id+'"><span class="'+menuData[i].icons+'"></span><span>'+menuData[i].text+'</span></li>');
		}
		$('.sen-header-center ul').html(h.join(''));
		$('.sen-header-center ul li').on('click',function(){
			var me=$(this),id = me.attr('data-id');
			if(!me.hasClass('active')){
				$('.sen-header-center .active').removeClass('active');
				me.addClass('active');
				$('#sen-tabs').tabs('select',0);
				for(var i=0;i<menuData.length;i++){
					if(menuData[i].id == id){
						if(menuData[i].homepage){
							$('#homepage').prop('src',location.origin+'/'+(sen.sysurl[menuData[i].syscode]||sen.sysurl['default'])+menuData[i].homepage);
						}else{
							$('#homepage').prop('src','about:blank');  
						}
						$('.sen-sidebar').html(menuData[i].menu.join(''));
						$.parser.parse('.sen-sidebar');
						$('.easyui-tree').tree({onSelect:menuLink});
						break;
					}
				}
			}
		});
		$('.sen-header-center ul li:eq('+defaultsys+')').trigger('click');
	}
	function menuLink(node){
		var a = $(node.target).find('a'),
			tabs = $('#sen-tabs'),
			title = a.html(),
			url = a.attr('data-link'),
			icon = a.attr('data-icon');
		if(url && url!='null'){
			if(!tabs.tabs('exists',title)){
				tabs.tabs('add',{
					title:title,
					content:sen.createFrame(url),
					iconCls:icon,
					fit:true,
					cls:'pd3 sen-noscroll',
					closable:true
				});
			}else{
				tabs.tabs('select',title);
			}
		}
	}
	function initTheme(){
		var themes = [
			{value:'default',text:'Default',group:'Base'},
			{value:'gray',text:'Gray',group:'Base'},
			{value:'metro',text:'Metro',group:'Base'},
			{value:'material',text:'Material',group:'Base'},
			{value:'bootstrap',text:'Bootstrap',group:'Base'},
			{value:'black',text:'Black',group:'Base'},
			{value:'metro-blue',text:'Metro Blue',group:'Metro'},
			{value:'metro-gray',text:'Metro Gray',group:'Metro'},
			{value:'metro-green',text:'Metro Green',group:'Metro'},
			{value:'metro-orange',text:'Metro Orange',group:'Metro'},
			{value:'metro-red',text:'Metro Red',group:'Metro'},
			{value:'ui-cupertino',text:'Cupertino',group:'UI'},
			{value:'ui-dark-hive',text:'Dark Hive',group:'UI'},
			{value:'ui-pepper-grinder',text:'Pepper Grinder',group:'UI'},
			{value:'ui-sunny',text:'Sunny',group:'UI'}
		];
		$('#cb-theme').combobox({
			groupField:'group',
			data: themes,
			editable:false,
			panelHeight:'auto',
			value:sen.easyuiThemes,
			onChange:function(value){
				$.cookie('easyuiThemes',value,{expires:365*100,path:'/'});
				location.reload();
			}
		});
	}
})();