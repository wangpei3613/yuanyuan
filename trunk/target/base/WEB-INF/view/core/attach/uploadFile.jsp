<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>影像资料</title>
	<%@ include file="../../index/lib.jsp" %>
	<link href="${path}/styles/js/fileupload/css/iconfont.css" rel="stylesheet" type="text/css"/>
	<link href="${path}/styles/js/fileupload/css/fileUpload.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${path}/styles/js/fileupload/js/fileUpload.js"></script>
	<style type="text/css">
		#lab-file {
			padding: 0 5px;
			font-size: 12px;
			border-radius: 5px;
			color: #333;
			border: 1px solid #ccc;
			background-color: #fff;
		}
		#accessory{
			display:none;
		}


	</style>
	<script type="text/javascript">
        var wfu = new WpFileUpload("fileUploadContent");
        var resultData;
        var applyid = '${applyid}';
        $(function() {
            wfu.initUpload({
                "uploadUrl":"${path}/arch/reel/file/material/uploadAttachArchive?applyid="+applyid,//上传文件信息地址
                "progressUrl":"#",//获取进度信息地址，可选，注意需要返回的data格式如下（{bytesRead: 102516060, contentLength: 102516060, items: 1, percent: 100, startTime: 1489223136317, useTime: 2767}）
                "scheduleStandard":false,
				"resultData":resultData,
				"showSummerProgress":false,
				"showFileItemProgress":false,
                onUpload:onUploadFun
            });
        })
		function onUploadFun() {
            wfu.initWithSelectFile(wfu);
            wfu.initWithCleanFile(wfu);
            wfu.initWithUpload(wfu);
            wfu.initFileList(wfu);
            wfu.cleanFileEvent(wfu);
		}
        //显示文件，设置删除事件
        wfu.showFileResult("img/a2.png","1",null,true,true,deleteFileByMySelf,downloadByMySelf);
        //如果不需要删除
        wfu.showFileResult("img/a3.png","2",null,false,false,null,null);
        //多文件需要自己进行循环
        function deleteFileByMySelf(fileId){
            alert("要删除文件了："+fileId);
            wfu.removeShowFileItem(fileId);
        }


        function success(){
            wfu.uploadSuccess();
        }

        function fail(){
            wfu.uploadError();
        }

        function downloadByMySelf(fileId, url) {
            alert(url)
        }
	</script>
</head>
<body  class="easyui-layout sen-crud-page" style="overflow:hidden;">
<div class="easyui-layout sen-crud-page">
	<!--创建一个文件上传的容器-->
	<div id="fileUploadContent" class="fileUploadContent"></div>
</div>
</body>
</html>