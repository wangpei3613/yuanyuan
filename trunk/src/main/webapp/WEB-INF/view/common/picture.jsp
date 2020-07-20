<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@ include file="../index/lib.jsp" %>
<script type="text/javascript">

var data = new Array();
var attach ={};
//选择摄像头
function JsSelDevName() { 	
	try { 
		var ret=0;
		testocx.StopPreview();		
		var i  = document.getElementById('s1').value;  
		ret= testocx.SetDevNameSel(i);
		var sel = document.getElementById('s2');
		$(sel).empty();
		JsRefreshResolution();
		jsStartPreviewProc();//普通预览
	} catch(e) {
		window.alert(e);	//"加载DLL错误！");
	}
}
function JsRefreshResolution(){
	var sel = document.getElementById('s2');
	var i  = document.getElementById('s1').value;
	var inum = testocx.RefreshResolution(i); 
	for (i=0; i<inum; i++) {
		var cstr = testocx.GetResolutionByIdx(i);				
		if (cstr != "") {
		    var opt = document.createElement("option"); 
		    $(opt).html(cstr).val(i);
		    $(sel).append(opt);
		    //if (cstr.indexOf("1024 * 768") != -1) {
		    if (cstr.indexOf("1280 * 1024") != -1) {
				opt.selected = true;
				ret = testocx.SetResolution(i);
			}
		}
	}
}
//选择分辨率
function JsSelResolution() { 	
	try { 
		var ret=0;
		testocx.StopPreview();		
		var i = document.getElementById('s2').value;  
		ret = testocx.SetResolution(i);
	} catch(e) {
		window.alert(e);	//"加载DLL错误！");
	}	
}
//选择图片输出格式
function JsSelFormat(){
	/*
	var sel = document.getElementById('s3');
	$(sel).empty();
	var format  = document.getElementById('s4').value;
	var inum = testocx.RefreshImgList("format");
	var cstr="";
	for (i=0; i<inum; i++) {
		cstr = testocx.GetImgNameByIdx(i);				
		if (cstr!="") {
		    var opt = document.createElement("OPTION"); 
		    $(opt).html(cstr).val(i);
		    $(sel).append(opt);
		}
	}*/
}
//普通预览(可旋转视频)
function jsStartPreviewProc() { 
	var ret;
	try { 	
		testocx.StopPreview();
		ret=testocx.StartPreviewProc();	
	} catch(e) {
		window.alert(e);	//"加载DLL错误！");
	}
	if (ret==0) {//ok		
		$("#setRotate90,#setRotate270,#SealCapBox,#ConfigBox").attr("disabled",false);
	} else {
		$("#s1,#SealCapBox").attr("disabled",false);
		$("#setRotate90,#setRotate270,#ConfigBox").attr("disabled",true);//,#PreviewBox
	}
}
//旋转
function SetRotateWeb(angle) {
	testocx.setRotate(angle);
	$("#SealCapBox").attr("disabled",false);
}

//手动寻边
function PandaCapPlay(){
	testocx.StopPreview();
	testocx.PandaCapturePlay();
	$("#SealCapBox,#ConfigBox").attr("disabled",false);
	$("#setRotate90,#setRotate270").attr("disabled",true);
}
//自动寻边
function jsMtoA(){
	PandaCapPlay();
	testocx.ManualToAuto();
}
//关闭寻边
function jsDisCrop(){
	testocx.DisableCrop();
	jsStartPreviewProc();
}

//拍摄
function SealCapWeb() { 	
	try { 	
		var format  = document.getElementById('s4').value;
		var DPI  = document.getElementById('s5').value;
		testocx.pDPI=DPI;
		testocx.nColorSpace=0;
		testocx.nRotateAngle=0;
		ret=testocx.SealCap(format);
		/*
		var sel = document.getElementById('s3');
		$(sel).empty();
		var inum = testocx.RefreshImgList(format);
		var cstr="";
		for (i=0; i<inum; i++) {
			cstr = testocx.GetImgNameByIdx(i);				
			if (cstr!="") {
	     		var opt = document.createElement("OPTION"); 
	     		$(opt).html(cstr).val(i);
	     		$(sel).append(opt);
			}
		}*/
		SendSealWeb();//http上传
	} catch(e) {
		window.alert(e);	//"加载DLL错误！");
	}
	if(ret == 0)  {		
		$("#VerifyBox").attr("disabled",false);//,#AddWater
		//FileNameCtrl.value      = "保存在本地文件名:"+testocx.cPicName;
	} else {}
}
//http上传
function SendSealWeb() { 	
	testocx.bUploadDel=true;//上传成功后删除本地图片
	try { 	
// 		ret=testocx.SendSeal("http://web.shyrcb.com/File/uploadImg");
		var paths = '<%=paths%>';
		var urls = paths+"${path}/sen/core/attach/uploadAttach";
		console.log(urls);
		ret=testocx.SendSeal(urls);
		
		//alert(ret);
		//alert(testocx.HttpResponse);		
	} catch(e) {
		window.alert("报错了"+e);	//"加载DLL错误！");
	}
	if(ret==1) {
		var response = testocx.HttpResponse;
		var d = eval("("+response+")");
		//alert(d.fileid);
		console.log(d);
		addImg(d);
		/*
		var sel = document.getElementById('s3');
		$(sel).empty();
		var format  = document.getElementById('s4').value;
		var inum = testocx.RefreshImgList(format);
		var cstr="";
		for (i=0; i<inum; i++) {
			cstr = testocx.GetImgNameByIdx(i);				
			if (cstr!="") {
		     	var opt = document.createElement("OPTION"); 
	     		$(opt).html(cstr).val(i);
	     		$(sel).append(opt);
			}
		}
		//FileNameCtrl.value="本地文件名:"    +testocx.cPicName;
		//MessageCtrl.value ="操作成功。";
		*/
	}else {
		//MessageCtrl.value ="操作失败。";
		//alert("上传失败");
	}
}
//初始化摄像头
function JsRefreshDevList() {	
	testocx.WriteLog = true;
	var sel = document.getElementById('s1');
	for (var i=0; i<testocx.RefreshDevList(); i++) {
		var cstr = testocx.GetDevNameByIdx(i);
		if (cstr != "") {
			var opt = document.createElement("option"); 
			$(opt).html(cstr).val(i);
		    $(sel).append(opt);
		    var flag = '${flag}';
		    if(flag == "2"){
		    	if (cstr.indexOf("辅摄像头") != -1) {//主摄像头(2M)
					opt.selected = true;
					testocx.SetDevNameSel(i);
				} else if(cstr.indexOf("主摄像头") != -1){
					opt.selected = true;
					testocx.SetDevNameSel(i);
				}
		    }else{
		    	if (cstr.indexOf("主摄像头") != -1) {//主摄像头(2M)
					opt.selected = true;
					testocx.SetDevNameSel(i);
				}
		    }		    
		}
	}
}
$(function(){
	JsRefreshDevList();
	JsRefreshResolution();
	//jsStartPreviewProc();//普通预览
	JsSelDevName();
});

function addImg(img){
	if(!img)return ;
	var span = document.createElement("span"); 	
	data.push(img.attach.id);
	attach[img.attach.id]=img.attach;
	var paths = '<%=paths%>'+"${path}/sen/core/attach/getImg?path="+img.attach.filepath;
	$(span).append("<img name='imgFile' onclick=easyui_showBigImg('"+img.attach.id+"') id="+img.attach.id+" src=\""+paths+"\" />");
	$(span).append("<img onclick=delImg(this,'"+img.attach.id+"') title=\"删除\" style='margin:5px' src=\"${path}/styles/style/icons/delete.png\" />");
	$(span).append("<span style='margin:10px' />");
	$("#imgDiv").append(span);
}
function delImg(obj,imgid){
	$("#"+imgid).parent().remove();
	var len = data.indexOf(imgid);
	if(len>-1){
		data.splice(len,1);
	}
	attach[imgid]='';
}
function back(){
	var imgId = [];
	$("#imgDiv img[name='imgFile']").each(function(_i){
		imgId.push(this.id);
	});
}
</script>
</head>
<body>
<div style="width:100%;float:left">	
   
	<div>
		摄像头：
		<select id="s1" name="SelectBox" size="1" onChange="JsSelDevName()"></select>
		分辨率：
		<select name="SelectResolutionBox" size="1" id="s2" onChange="JsSelResolution()"></select>
		图片格式：
		<select name="SelectFormatBox" size="1" id="s4" onChange="JsSelFormat()">
		  <option value="jpg" selected="selected">jpg</option>  
<!-- 		  <option value="bmp">bmp</option>   -->
<!-- 		  <option value="tif">tif</option> -->
<!-- 		  <option value="gif">gif</option> -->
		</select>
		图片DPI：
		 <select name="SelectDPIBox" size="1" id="s5">
		  <option value=0 selected="selected">默认值</option>  
		  <option value=72>72</option>  
		  <option value=200>200</option>
		  <option value=300>300</option>
		</select>
	</div>
	<div>
		<input  type="button" id="SealCapBox"  name="SealCapBox" disabled onClick="SealCapWeb()" value="拍摄 ">
<!-- 		<input  type="button" id="SealCapBox"  name="SealCapBox" disabled onClick="back()" value="提交"> -->
		<span>　</span>
		<!--  <input type="button" id="PreviewBox" name="PreviewBox" title="普通预览(可旋转视频)" value="普通预览" onClick="jsStartPreviewProc()">-->
		  <input type="button" id="setRotate90" name="setRotate90" title="顺时针方向旋转90°" value="顺时针旋转90°" disabled onClick="SetRotateWeb(90)">
		  <input type="button" id="setRotate270" name="setRotate270" title="逆时针方向旋转90°" value="逆时针旋转90°" disabled onClick="SetRotateWeb(360-90)">
		  <input  type="button" id="PandaPlay" name="PandaPlay" title="寻边模式预览(预览区拖动鼠标为手动寻边)" value="手动寻边" onClick="PandaCapPlay()">
		  <input  type="button" id="MtoA" name="MtoA" value=" 自动寻边 " onClick="jsMtoA()">
		  <input  type="button" id="StopCrop" name="StopCrop" title="关闭(自/手动)寻边 " value="关闭寻边 " onClick="jsDisCrop()">
		<!--  <input  type="button" id="VerifyBox" name="VerifyBox" disabled id="Verify" onClick="SendSealWeb()" value="HTTP上传">-->
	</div>
</div>
<div style="width:500px;float:left">	
	<!--[if IE]>
			<object classid="clsid:1C68DF21-EFEC-4623-85E5-0C369B95F15E" width=600 height=400 hspace="3" vspace="3" id="testocx" codebase="SealCapNtWeb.cab#version=2,7,0,0">
			<img src="nantian.JPG" width=600 height=400 hspace="3" vspace="3" />
			</object>
	    <![endif]-->	
	<!--[if !IE]> -->
		<object id="testocx" name="testocx"	TYPE="application/x-itst-activex"
				hspace="3" vspace="3" ALIGN="baseline" BORDER="0"
				WIDTH="600" HEIGHT="400" codebase="SealCapNtWeb.cab#version=2,7,0,0"
				clsid="{1C68DF21-EFEC-4623-85E5-0C369B95F15E}" >
			<img width=250 height=100 hspace="3" vspace="3" />
		</object>
	<!-- <![endif]-->
</div>
<div style="float:left;width:400px;height:380px;padding:10px;overflow:auto">
	<div id="imgDiv">
	</div>
</div>
</body>

</html>
