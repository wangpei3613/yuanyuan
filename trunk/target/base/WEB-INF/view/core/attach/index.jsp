<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
if(!StringUtil.notBlank(request.getAttribute("dicttype"))){
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
	<title>影像资料</title>
	<%@ include file="../../index/lib.jsp" %>
	 <link href="${path}/styles/js/kindeditor/themes/default/default.css" type="text/css" rel="stylesheet"></link>
	 <script type="text/javascript" src="${path}/styles/js/kindeditor/kindeditor-all-min.js"></script>
     <script type="text/javascript" src="${path}/styles/js/kindeditor/lang/zh-CN.js"></script>
     <script type="text/javascript" src="${path}/styles/js/CJL.0.1.min.js"></script>
     <script type="text/javascript" src="${path}/styles/js/ImageTrans.js"></script>
	<style type="text/css">
		.dicDiv {
		    cursor: pointer;
	        padding: 5px 0;
	        border-top: 0;
	        border-left: 0;
	        border-right: 0;
		}
		body .layui-layer-btn .layui-layer-btn0 {
		    padding: 0px 8px;
		    height: 25px;
		    line-height: 25px;
		}
		body .layui-layer-btn .layui-layer-btn0.disabled{
			opacity: 0.5;
			cursor: default;
		}
		.dicDiv span{
			display: block;
		}
		
		#currnum{
			position: absolute;
		    top: 10px;
		    right: 10px;
		    font-size: 16px;
		}
		
		
	</style>
	<script type="text/javascript">
	  var applyid = '${param.applyid}',crud='${param.crud}',dicData,imageTrans,currnum=1,container,dicttype = '${dicttype}';
	  var prevBtn,nextBtn,delBtn,uploadBtn,uploadHigBtn;
	  $(function(){
		  prevBtn = $('#prevBtn');nextBtn = $('#nextBtn');delBtn = $('#delBtn');uploadBtn = $('#uploadBtn');uploadHigBtn = $('#uploadHigB')
		  if(crud == '1'){
			  $('.sen-btn-warp .btn').show();
		  }
		  container = $('#imgDiv')[0];
		  imageTrans = new ImageTrans( container, {
				onPreLoad: function(){ container.style.backgroundImage = "url('${path}/styles/images/loading.gif')"; },
				onLoad: function(){ container.style.backgroundImage = ""; },
				onError: function(err){ container.style.backgroundImage = ""; sen.alert(err); }
		  });
		  sen.ajax({
				url:'/sys/getExtDictionary?typeCode='+dicttype,
				success:function(dict){
					sen.ajax({
						url:'/sen/core/attach/getAttach',
						data:{applyid:applyid,dicttype:dicttype},
						success:function(d){
							initAttach(d,dict);
							 $('.dicDiv').on('click',function(){
								  if($(this).hasClass("datagrid-row-selected"))  return;
								   $(".datagrid-row-selected").removeClass("datagrid-row-selected");
								   $(this).addClass("datagrid-row-selected"); 
								   var dict='dict_'+$(this).attr('id');
								   prevBtn.addClass('disabled');
								   nextBtn.addClass('disabled');
								   delBtn.addClass('disabled');
								   uploadBtn.removeClass('disabled');
								   uploadHigBtn.removeClass('disabled');
								   if(dicData[dict]&&dicData[dict].attachs.length>0){
									   	  currnum=1;
										  $('#currnum').html('(1/'+dicData[dict].attachs.length+')');
										  imageTrans.load(sen.imgPath + dicData[dict].attachs[currnum-1].filepath);
										  if(dicData[dict].attachs.length > 1){
											  nextBtn.removeClass('disabled');
										  }
										  delBtn.removeClass('disabled');
								   }else{
									   currnum=0;
									   $('#currnum').html('(0/0)');
									   imageTrans.load("${path}/styles/images/unknow.gif");
								   }
								   
							  }).on('mouseover',function(){
								  $(this).addClass("datagrid-row-over");
							  }).on('mouseout',function(){
								  $(this).removeClass("datagrid-row-over");
							  });
							  //默认选中第一个，显示第一个数量
							  $('.dicDiv:eq(0)').click();
						}
				  });
				}
		  });
	  });
	  
	  function initAttach(attachs, dicts){
			var obj = {},h=[];
			if(dicts && dicts.length>0){
				
				$.each(dicts, function(i,dict){
					dict.attachs = [];
					obj['dict_'+dict.code] = dict;
				});
				if(attachs && attachs.length>0){
					for(var e in attachs){
						obj['dict_'+attachs[e].dictcode] && obj['dict_'+attachs[e].dictcode].attachs.push(attachs[e]);
					}
				}else{
					currnum = 0;
				}
				dicData = obj;
				for(var i in dicts){
					h.push('<div class="dicDiv sen-border"  id="'+dicts[i].code+'"><span>'+dicts[i].name+'('+obj['dict_'+dicts[i].code].attachs.length+')</span></div>');
				}
				$('#dicContent').html(h.join(''));
			}else{
				sen.alert('页面异常，刷新重试!');
			}
		}
	  
	  
	  function uploadMultiimage(t){
		  if($(t).hasClass('disabled')){
			  return;
		  }
		  var dictcode = $('.datagrid-row-selected').attr('id');
		  var editor = KindEditor.editor($.extend({
	            uploadJson: '${path}/sen/core/attach/uploadAttach;jsessionid=<%=session.getId()%>', 
	            allowFileManager: true,
	            imageSizeLimit:'10MB'
	      }));
		  editor.loadPlugin('multiimage', function () {
				editor.plugin.multiImageDialog({
					clickFn: function (urlList) {
						if(urlList.length > 0){
							 var attachs=[],ids=[];
							 for(var e in urlList){
								 attachs.push(urlList[e].attach);
								 ids.push(urlList[e].attach.id);
							 }
							 sen.ajax({
									url:'/sen/core/attach/saveAttach',
									data:{applyid:applyid,dicttype:dicttype,dictcode:dictcode,ids:ids,checkAction:'${checkAction}'},
									success:function(){
										dicData['dict_'+dictcode].attachs = dicData['dict_'+dictcode].attachs.concat(attachs);
										$('.datagrid-row-selected span').html(dicData['dict_'+dictcode].name+'('+dicData['dict_'+dictcode].attachs.length+')');
										if(currnum==0){
											imageTrans.load(sen.imgPath + dicData['dict_'+dictcode].attachs[0].filepath);
											currnum=1;
										}
										if(dicData['dict_'+dictcode].attachs.length > 1){
											nextBtn.removeClass('disabled');
										}
										delBtn.removeClass('disabled');
										$('#currnum').html('('+currnum+'/'+dicData['dict_'+dictcode].attachs.length+')');
									}
							});
							this.hideDialog();
						}else{
							this.hideDialog();
						}
					}
				});
	      });
	  }
	  
	  function nextAttach(t){
		  if($(t).hasClass('disabled')){
			  return;
		  }
		  var dict='dict_'+$('.datagrid-row-selected').attr('id');
		  prevBtn.removeClass('disabled');
		  if(dicData[dict]&&dicData[dict].attachs.length>currnum){
			  currnum ++;
			  imageTrans.load(sen.imgPath + dicData[dict].attachs[currnum-1].filepath);
		  }
		  if(dicData[dict].attachs.length <= currnum){
				nextBtn.addClass('disabled');
			}
		  $('#currnum').html('('+currnum+'/'+dicData[dict].attachs.length+')');
	  }
	  
	  function prevAttach(t){
		  if($(t).hasClass('disabled')){
			  return;
		  }
		  var dict='dict_'+$('.datagrid-row-selected').attr('id');
		  nextBtn.removeClass('disabled');
		  if(currnum>1){
			  currnum --;
			  imageTrans.load(sen.imgPath + dicData[dict].attachs[currnum-1].filepath);
		  }
		  if(currnum <= 1){
			  prevBtn.addClass('disabled');
			}
		  $('#currnum').html('('+currnum+'/'+dicData[dict].attachs.length+')');
	  }
	  
	  function deleteAttach(t){
		  if($(t).hasClass('disabled')){
			  return;
		  }
		  var dict = 'dict_'+$('.datagrid-row-selected').attr('id');
		  var attach = dicData[dict].attachs[currnum-1];
		  if(currnum>0){
			  sen.ajax({
					url:'/sen/core/attach/delAttach',
					data:{applyid:applyid,ids:attach.id,checkAction:'${checkAction}'},
					success:function(){
						dicData[dict].attachs.splice(currnum-1,1);
						$('.datagrid-row-selected span').html(dicData[dict].name+'('+dicData[dict].attachs.length+')');
						if(currnum>dicData[dict].attachs.length){
							currnum--;
						}
						$('#currnum').html('('+(currnum)+'/'+dicData[dict].attachs.length+')');
						if(currnum>0){
							imageTrans.load(sen.imgPath + dicData[dict].attachs[currnum-1].filepath);
						}else{
							delBtn.addClass('disabled');
							imageTrans.load("${path}/styles/images/unknow.gif");
						}
						if(currnum > 1){
							prevBtn.removeClass('disabled');
						}else{
							prevBtn.addClass('disabled');
						}
						if(dicData[dict].attachs.length > currnum){
							nextBtn.removeClass('disabled');
						}else{
							nextBtn.addClass('disabled');
						}
					}
			  });
		  }
	  }
	  function uploadHig(t){
			 if($(t).hasClass('disabled')){
				  return;
			  }
			 var dictcode = $('.datagrid-row-selected').attr('id');
			 if(!dictcode){
				 alert("请选择上传类型");
				 return ;
			 }
			layer.win({
				  title: '高拍仪',
				  area: ['800px', '660px'],
				  content: '${path}//sen/core/attach/toHighMeter',
				  btn:['保存','关闭'],
				  yes:function(index, layero){
					  var win = layero.find('iframe')[0].contentWindow,
				  	  form = win.$('form');
					  var datas = win.data;
					  var urlList = win.attach;
					  var attachs=[] ;
					  var len = datas.length;
					  if(datas&&len>0){
						  console.log(datas);
						  for(var e in urlList){
							  attachs.push(urlList[e]);
						  }
						  sen.ajax({
									url:'/sen/core/attach/saveAttach',
									data:{applyid:applyid,dicttype:dicttype,dictcode:dictcode,ids:datas,checkAction:'${checkAction}'},
									success:function(){
										dicData['dict_'+dictcode].attachs = dicData['dict_'+dictcode].attachs.concat(attachs);
										$('.datagrid-row-selected span').html(dicData['dict_'+dictcode].name+'('+dicData['dict_'+dictcode].attachs.length+')');
										if(currnum==0){
											imageTrans.load(sen.imgPath + dicData['dict_'+dictcode].attachs[0].filepath);
											currnum=1;
										}
										if(dicData['dict_'+dictcode].attachs.length > 1){
											nextBtn.removeClass('disabled');
										}
										delBtn.removeClass('disabled');
										$('#currnum').html('('+currnum+'/'+dicData['dict_'+dictcode].attachs.length+')');
									}
								});	
					  }
					  top.layer.close(index);
					  return false;
				  }
			});
		}
	  
	  	function toLeft(){
			imageTrans.left();
		}
	  	
	  	
		function toRight(){
			imageTrans.right();
		}
		
		
		function toVertical(){
			imageTrans.vertical();
		}
		
		
		function toHorizontal(){
			imageTrans.horizontal();
		}
		
		function toReset(){
			imageTrans.reset();
		}
	</script>
</head>
<body  class="easyui-layout sen-crud-page" style="overflow:hidden;"> 
	<div data-options="region:'west'" style="width:200px;height:100%;overflow-x:hidden;overflow-y:auto;text-align: center;" >
	  <div id="dicContent">
	  </div>
	</div>
	<div data-options="region:'center',border:false">
		<div class="easyui-layout sen-crud-page">
			<div data-options="region:'north'" style="height:40px;overflow: hidden;">
				<div class="sen-btn-warp layui-layer-btn">
					<!-- <a id="uploadHigB" class="layui-layer-btn0 btn disabled" onclick="uploadHig(this)"  style="display: none;">高拍仪上传</a> -->
					<a id="uploadBtn" class="layui-layer-btn0 btn disabled" onclick="uploadMultiimage(this)"  style="display: none;">上传</a>
					<a id="delBtn" class="layui-layer-btn0 btn disabled" onclick="deleteAttach(this)"  style="display: none;">删除</a>
					<a id="prevBtn" class="layui-layer-btn0 disabled" onclick="prevAttach(this)"  >上一张</a>
					<a id="nextBtn" class="layui-layer-btn0 disabled" onclick="nextAttach(this)"  >下一张</a>
					<a class="layui-layer-btn0" onclick="toLeft()"  >向左旋转</a>
					<a class="layui-layer-btn0" onclick="toRight()"  >向右旋转</a>
					<a class="layui-layer-btn0" onclick="toVertical()"  >垂直翻转</a>
					<a class="layui-layer-btn0" onclick="toHorizontal()"  >水平翻转</a>
					<a class="layui-layer-btn0" onclick="toReset()"  >重置</a>
					<span id="currnum">(0/0)</span>
				</div>
			</div>
			<div data-options="region:'center',border:false" >
				<div id="imgDiv" style="width:100%;height:100%;background: #fff center no-repeat;">
	   			</div>
			</div>
		</div>
	</div>
</body>
</html>