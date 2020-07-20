<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>档案检索</title>
	<%@ include file="../../index/lib.jsp" %>
	<link href="${path}/styles/js/fileupload/css/iconfont.css" rel="stylesheet" type="text/css"/>
	<link href="${path}/styles/js/fileupload/css/fileUpload.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${path}/styles/js/fileupload/js/fileUpload.js"></script>
	<style>
		.datagrid .panel-body {border: none;}
		#sen-datagrid {overflow-x:scroll;}
		.status{
			display: none;
		}
	</style>
	<script type="text/javascript">
        var grid,gridFile;
        var wfu = new WpFileUpload("fileUploadContent");
        $(function(){
            $('#easyui-tree').tree({
                url:'${path }/arch/menu/getTreeGrid',
                onClick: function(node){
                    $('#sen-datagridFile').treegrid('loadData', []);
                    if(node.id != $('#arch_menu_id').val()){
                        $('#arch_menu_id').val(node.id);
                        grid.grid('load');
                        $("#sen-datagridFile").tgrid("options").url = '${path }/arch/reel/files/getTreeGrid';
                        gridFile.tgrid('load');
                        getMaterialByFileId('','');
                    }
                },
                onLoadSuccess: function (node, data) {

                }
            });
            grid = $('#sen-datagrid').grid({
                url:'${path }/arch/reel/getPagerPart',
                sortName:'addtime',
                sortOrder:'desc',
                fitColumns:false,
                toolbar:'#sen-toolbar',
                formEl:'#sen-form-da',
                columns:[[
                    {field:'reel_no',title:'案件编号',width:100,sortable:true,align:'center'},
                    {field:'reel_name',title:'案件名称',width:00,sortable:true},
                    {field:'reel_status',title:'档案状态',width:100,dictType:'SEN_REEL_STATE'},
                    {field:'box_no',title:'所属档案盒',width:100,sortable:true},
                    {field:'file_date',title:'归档时间',width:100},
                    {field:'expire_date',title:'到期时间',width:100}
                ]],
                onSelect:function(index,row){
                    if(row.id != $('#arch_reel_id').val()) {
                        $('#arch_reel_id').val(row.id);
                        $('#reel_status').val(row.reel_status);
                        $("#sen-datagridFile").tgrid("options").url = '${path }/arch/reel/files/getTreeGrid?arch_reel_id='+$('#arch_reel_id').val();
                        gridFile.tgrid('load');
                        getMaterialByFileId('','');
                    }
                },
                onLoadSuccess:function(data){

                }
            })
            gridFile = $('#sen-datagridFile').tgrid({
                url:'${path }/arch/reel/files/getTreeGrid',
                treeField:'file_name',
                fitColumns:false,
                toolbar:'#sen-toolbar-file',
                formEl:'#sen-form-file',
                columns:[[
                    {field:'file_name',title:'文件名称',width:100},
                    {field:'file_type',title:'文件类型',width:100,dictType:'SEN_FILE_TYPE',align:'center'},
                    {field:'year',title:'年度',width:100,align:'center'},
                    {field:'serial',title:'文件顺序',width:100,align:'center'}
                ]],
                onSelect:function(index,row){
                    $("#arch_reel_files_id").val(index.id);
                    getMaterialByFileId(index.id,$('#reel_status').val());
                },
                onLoadSuccess:function(data){
                    $("a[name='opera']").linkbutton({text:'上传',plain:true,iconCls:'icon-add'});
                }
            })
        });
        function submit() {
            if(grid.grid('selectOne')){
                layer.win({
                    title: '新增借阅申请',
                    area: ['400px', '300px'],
                    content: '${path}/arch/reel/search/toEdit?arch_reel_id='+grid.grid('getSelected').id+'&reel_name='+grid.grid('getSelected').reel_name,
                    btn:['保存','关闭'],
                    yes:function(index, layero){
                        var win = layero.find('iframe')[0].contentWindow,
                            form = win.$('form');
                        if(form.form('validate')){
                            sen.ajax({
                                url:'/arch/reel/search/jieyue',
                                data:form.serializeObject(),
                                loaddingEle:layero,
                                success:function(){
                                    top.layer.close(index);
                                    sen.msg('借阅申请提交成功');
                                    grid.grid('load');
                                }
                            });
                        }
                        return false;
                    }
                });
            }
        }
        function getMaterialByFileId(fileid,status){
            sen.ajax({
                url:'/arch/reel/file/material/getAttach',
                data:{applyid:fileid},
                success:function(d){
                    var h=[];
                    if(d && d.length>0){
                        for(var e in d){
                            var isImg = WpFileUploadTools.isInArray(d[e].meta_type, WpFileUploadTools.imgArray);
                            var html = wfu.getFileItemResultModelArrive(d[e].meta_type, d[e].id, d[e].meta_name, isImg, sen.imgPath + d[e].meta_url, true, false,d[e].meta_url);
                            h.push(html);
                        }
                    }
                    $('#fileUploadContent').html(h.join(''));
                }
            });
        }
	</script>
</head>
<body class="easyui-layout sen-crud-page">
<form action="${path}/arch/reel/file/material/getFile" method="post">
	<input id="url" name="url" type="hidden"></input>
	<input id="filename" name="filename" type="hidden"></input>
	<input type="submit" id="submit" name="submit_button" value="提交" style="display: none"/>
</form>
<div data-options="region:'west',border:true,collapsible:false,split:true,cls:'pd1'" style="width: 15%;">
	<ul id="easyui-tree"></ul>
</div>
<div data-options="region:'center',border:true,collapsible:false,cls:'pd1'" style="width: 40%;">
	<div class="easyui-layout sen-crud-page">
		<form class="sen-crud-page-form" id="sen-form-da">
			<input id="arch_menu_id" type="hidden" name="arch_menu_id" value="" />
		</form>
		<c:if test="${not empty moduleAuths }">
			<div class="sen-crud-page-toolbar" id="sen-toolbar">
				<c:forEach items="${moduleAuths }" var="auth">
					<a href="javascript:;" class="easyui-linkbutton" iconCls="${auth.icon }" onclick="${empty auth.action?auth.code:auth.action}()" plain="true">${auth.name }</a>
				</c:forEach>
			</div>
		</c:if>
		<table id="sen-datagrid" class="sen-crud-page-datagrid"></table>
	</div>
</div>
<div data-options="region:'east',border:false,collapsible:false,split:true,cls:'pd1'" style="width:45%;">
	<div class="easyui-layout sen-crud-page">
		<div class="sen-crud-page-header" data-options="region:'north',border:true,title:'文件',collapsible:false,split:true" style="height: 40%;">
			<form class="sen-crud-page-form" id="sen-form-file">
				<input id="arch_reel_id" type="hidden" name="arch_reel_id" value="" />
				<input id="reel_status" type="hidden" name="reel_status" value="" />
			</form>
			<table id="sen-datagridFile" class="sen-crud-page-datagrid"></table>
		</div>
		<div class="sen-crud-page-center" id="materiallist" data-options="region:'center',order:true,title:'材料',collapsible:false" style="height:50%;">
			<!--创建一个文件上传的容器-->
			<input id="arch_reel_files_id" type="hidden" name="arch_reel_files_id" value="" />
			<div id="fileUploadContent" class="fileUploadContent"></div>
		</div>
	</div>
</div>
</body>
</html>