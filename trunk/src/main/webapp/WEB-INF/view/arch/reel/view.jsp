<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>档案管理</title>
	<%@ include file="../../index/lib.jsp" %>
	<link href="${path}/styles/js/fileupload/css/iconfont.css" rel="stylesheet" type="text/css"/>
	<link href="${path}/styles/js/fileupload/css/fileUpload.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${path}/styles/js/fileupload/js/fileUpload.js"></script>
	<style>
		.datagrid .panel-body {border: none;}
		#sen-datagrid {overflow-x:scroll;}
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
                        //如果点的是其他目录，目录id变化，则档案的选中清空，id清空，表置空，下面的文件也是选中清空，id清空，数置空
                        $('#arch_menu_id').val(node.id);
                        $("#arch_reel_id").val();
                        $('#arch_reel_files_id').val();
                        grid.grid('load');
                        grid.grid('clearSelections');
                        $("#sen-datagridFile").tgrid("options").url = '${path }/arch/reel/files/getTreeGrid';
                        gridFile.tgrid('load');
                        gridFile.treegrid('clearSelections');
                        getMaterialByFileId('','');
                    }
                },
                onLoadSuccess: function (node, data) {

                }
            });
            grid = $('#sen-datagrid').grid({
                url:'${path }/arch/reel/getPager',
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
                        $('#file_date').val(row.file_date);
                        $('#arch_reel_files_id').val();
                        $("#sen-datagridFile").tgrid("options").url = '${path }/arch/reel/files/getTreeGrid?arch_reel_id='+$('#arch_reel_id').val();
                        gridFile.tgrid('load');
                        gridFile.treegrid('clearSelections');
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
        function addReel() {
            var arch_menu_id = $("#arch_menu_id").val();
            if(arch_menu_id){
                layer.win({
                    title: '案件文件',
                    area: ['400px', '400px'],
                    content: '${path}/arch/reel/toEdit?arch_menu_id='+arch_menu_id,
                    btn:['保存','关闭'],
                    yes:function(index, layero){
                        var win = layero.find('iframe')[0].contentWindow,
                            form = win.$('form');
                        if(form.form('validate')){
                            sen.ajax({
                                url:'/arch/reel/save',
                                data:form.serializeObject(),
                                loaddingEle:layero,
                                success:function(){
                                    top.layer.close(index);
                                    sen.msg('保存成功');
                                    grid.grid('load');
                                }
                            });
                        }
                        return false;
                    }
                });
            }else{
                sen.alert('请先选择目录');
			}
        }
        function editReel() {
            if(grid.grid('selectOne')){
                //如果是已经归档不允许修改
                if (!$("#file_date").val()){
                    layer.win({
                        title: '修改案件',
                        area: ['400px', '300px'],
                        content: '${path}/arch/reel/toEdit?id='+grid.grid('getSelected').id,
                        btn:['保存','关闭'],
                        yes:function(index, layero){
                            var win = layero.find('iframe')[0].contentWindow,
                                form = win.$('form');
                            if(form.form('validate')){
                                sen.ajax({
                                    url:'/arch/reel/save',
                                    data:form.serializeObject(),
                                    loaddingEle:layero,
                                    success:function(){
                                        top.layer.close(index);
                                        sen.msg('修改成功');
                                        grid.grid('load');
                                    }
                                });
                            }
                            return false;
                        }
                    });
				}else{
                    sen.alert('档案已归档');
				}
            }
        }
        function delReel() {
            //需要判断下面是否有文件
            if(grid.grid('selectOne')){
                //如果是已经归档不允许修改
                if (!$("#file_date").val()){
                    sen.confirm('确定要删除吗?',function(index){
                        sen.ajax({
                            url:'/arch/reel/del',
                            data:{id: grid.grid('getSelected').id},
                            success:function(){
                                sen.msg('删除成功');
                                grid.grid('clearSelections');
                                $('#arch_reel_id').val('');
                                grid.grid('load');
                            }
                        });
                    });
                }else{
                    sen.alert('档案已归档');
                }
            }
        }
        function archiving() {
            if(grid.grid('selectOne')){
                if (!$("#file_date").val()){
                    sen.confirm('确定要归档吗?',function(index){
                        sen.ajax({
                            url:'/arch/reel/guiDang',
                            data:{id: grid.grid('getSelected').id},
                            success:function(){
                                sen.msg('归档成功');
                                grid.grid('clearSelections');
                                $('#arch_reel_id').val('');
                                grid.grid('load');
                                $(".status").css('display','none');
                                $("#sen-datagridFile").tgrid("options").url = '${path }/arch/reel/files/getTreeGrid';
                                gridFile.tgrid('load');
                                getMaterialByFileId('','');
                            }
                        });
                    });
                }else{
                    sen.alert('档案已归档');
                }
            }
        }
        function upperShelf() {
            if(grid.grid('selectOne')){
                if ($("#file_date").val()){
                    layer.win({
                        title: '选择要上架的档案盒',
                        area: ['800px', '600px'],
                        content: '${path}/arch/reel/toBoxView',
                        btn:['确定','关闭'],
                        yes:function(index, layero){
                            var win = layero.find('iframe')[0].contentWindow,
                                boxid = win.$('#boxid').val();
                            if(boxid==''){
                                sen.msg('请选择档案盒');
                                return;
                            }
                            sen.ajax({
                                url:'/arch/reel/upShelf',
                                data:{id: grid.grid('getSelected').id,boxid:boxid},
                                success:function(d){
                                    if(d=='上架成功'){
                                        top.layer.close(index);
                                    }
                                    sen.msg(d);
                                    grid.grid('clearSelections');
                                    $('#arch_reel_id').val('');
                                    grid.grid('load');
                                    $("#sen-datagridFile").tgrid("options").url = '${path }/arch/reel/files/getTreeGrid';
                                    gridFile.tgrid('load');
                                    getMaterialByFileId('','');
                                }
                            });
                            return false;
                        }
                    });
                }else{
                    sen.alert("请先归档");
				}
			}
        }
        function downShelf() {
            if(grid.grid('selectOne')){
                if(grid.grid('getSelected').reel_status == '3'){
                    sen.confirm('确定要下架吗?',function(index){
                        sen.ajax({
                            url:'/arch/reel/downShelf',
                            data:{id: grid.grid('getSelected').id},
                            success:function(){
                                sen.msg('下架成功');
                                grid.grid('clearSelections');
                                $('#arch_reel_id').val('');
                                grid.grid('load');
                                $("#sen-datagridFile").tgrid("options").url = '${path }/arch/reel/files/getTreeGrid';
                                gridFile.tgrid('load');
                                getMaterialByFileId('','');
                            }
                        });
                    });
                }else{
                    sen.alert("请选择上架的档案");
				}
            }
        }
        function addFile() {
            //归档之后不能新增
			if (!$("#file_date").val()){
                var did = $("#arch_reel_id").val();
                layer.win({
                    title: '案件文件',
                    area: ['400px', '400px'],
                    content: '${path}/arch/reel/files/toEdit?did='+did,
                    btn:['保存','关闭'],
                    yes:function(index, layero){
                        var win = layero.find('iframe')[0].contentWindow,
                            form = win.$('form');
                        if(form.form('validate')){
                            sen.ajax({
                                url:'/arch/reel/files/save',
                                data:form.serializeObject(),
                                loaddingEle:layero,
                                success:function(){
                                    top.layer.close(index);
                                    sen.msg('保存成功');
                                    gridFile.treegrid('load');
                                }
                            });
                        }
                        return false;
                    }
                });
			}else{
			    sen.alert("档案已归档，不能新增文件");
			}
        }
        function editFile() {
            //归档之后不能修改
            if (!$("#file_date").val()){
                if(gridFile.grid('selectOne')){
                    layer.win({
                        title: '修改案件',
                        area: ['400px', '300px'],
                        content: '${path}/arch/reel/files/toEdit?id='+gridFile.grid('getSelected').id,
                        btn:['保存','关闭'],
                        yes:function(index, layero){
                            var win = layero.find('iframe')[0].contentWindow,
                                form = win.$('form');
                            if(form.form('validate')){
                                sen.ajax({
                                    url:'/arch/reel/files/save',
                                    data:form.serializeObject(),
                                    loaddingEle:layero,
                                    success:function(){
                                        top.layer.close(index);
                                        sen.msg('修改成功');
                                        gridFile.treegrid('load');
                                    }
                                });
                            }
                            return false;
                        }
                    });
                }
            }else{
                sen.alert("档案已归档，不能修改文件");
            }
        }
        function delFile() {
            //归档之后不能删除
            if (!$("#file_date").val()){
                if(gridFile.grid('selectOne')){
                    sen.confirm('确定要删除吗?',function(index){
                        sen.ajax({
                            url:'/arch/reel/files/del',
                            data:{id: gridFile.grid('getSelected').id},
                            success:function(){
                                sen.msg('删除成功');
                                gridFile.treegrid('clearSelections');
                                $('#arch_reel_files_id').val('');
                                gridFile.treegrid('load');
                                getMaterialByFileId('','');
                            }
                        });
                    });
                }
            }else{
                sen.alert("档案已归档，不能删除文件");
            }
        }
        function destory() {
            if(grid.grid('selectOne')){
                if ($("#file_date").val()){
                    var expire_date;
                    expire_date = grid.grid('getSelected').expire_date.replace(/-/g,"/");//替换字符，变成标准格式
                    var d2=new Date();//取今天的日期
                    var d1 = new Date(Date.parse(expire_date));
                    if(d1<d2){
                        var arch_reel_id = $("#arch_reel_id").val();
                        layer.win({
                            title: '档案销毁',
                            area: ['400px', '400px'],
                            content: '${path}/arch/destroy/toEdit?arch_reel_id='+arch_reel_id,
                            btn:['保存','关闭'],
                            yes:function(index, layero){
                                var win = layero.find('iframe')[0].contentWindow,
                                    form = win.$('form');
                                if(form.form('validate')){
                                    sen.ajax({
                                        url:'/arch/destroy/save',
                                        data:form.serializeObject(),
                                        loaddingEle:layero,
                                        success:function(){
                                            top.layer.close(index);
                                            sen.msg('注销成功');
                                            grid.grid('load');
                                            $("#sen-datagridFile").tgrid("options").url = '${path }/arch/reel/files/getTreeGrid';
                                            gridFile.tgrid('load');
                                            getMaterialByFileId('','');
                                        }
                                    });
                                }
                                return false;
                            }
                        });
                    }else{
                        sen.alert("档案未过期");
                    }
                }else{
                    sen.alert("档案未归档");
				}
			}
        }
        function delay() {
            var arch_reel_id = $("#arch_reel_id").val();
            if(grid.grid('selectOne')){
                if ($("#file_date").val()){
                    var expire_date;
                    expire_date = grid.grid('getSelected').expire_date.replace(/-/g,"/");//替换字符，变成标准格式
                    var d2=new Date();//取今天的日期
                    var d1 = new Date(Date.parse(expire_date));
                    if(d1<d2){
                        layer.win({
                            title: '档案延期',
                            area: ['400px', '400px'],
                            content: '${path}/arch/reel/toDelayView?id='+arch_reel_id,
                            btn:['保存','关闭'],
                            yes:function(index, layero){
                                var win = layero.find('iframe')[0].contentWindow,
                                    form = win.$('form');
                                if(form.form('validate')){
                                    sen.ajax({
                                        url:'/arch/reel/delay',
                                        data:form.serializeObject(),
                                        loaddingEle:layero,
                                        success:function(){
                                            top.layer.close(index);
                                            sen.msg('延期成功');
                                            grid.grid('load');
                                        }
                                    });
                                }
                                return false;
                            }
                        });
                    }else{
                        sen.alert("档案未过期");
                    }
                }else{
                    sen.alert("档案未归档");
                }
            }
        }
        function upload(index) {
            //归档之后不能上传
            if (!$("#file_date").val()){
                if(gridFile.grid('selectOne')){
                    //如果是文件夹不能上传材料
					if(gridFile.grid('getSelected').file_type=='2'){
                        layer.win({
                            title: '上传材料',
                            area: ['800px', '600px'],
                            content: '${path}/arch/reel/file/material/toAttachIndex?fileid='+gridFile.grid('getSelected').id,
                            btn:['关闭'],
                            yes:function(index, layero){
                                top.layer.close(index);
                                getMaterialByFileId(gridFile.grid('getSelected').id,'1');
                                return false;
                            },
                            cancel: function(index){
                                top.layer.close(index);
                                getMaterialByFileId(gridFile.grid('getSelected').id,'1');
                                return false;
                            }
                        });
					}else{
					    sen.alert("这是文件夹，请选择文件上传材料");
					}
                }
            }else{
                sen.alert("档案已归档，不能上传文件材料");
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
                    if(status != '1'){
						$(".status").css('display','none');
                    }
                }
            });
		}
        function deletefile(id){
            sen.ajax({
                url:'/arch/reel/file/material/del?id='+id,
                success:function(){
                    getMaterialByFileId($("#arch_reel_files_id").val(),$('#reel_status').val());
                }
            });
        }
        function downloadfile(id,url,filename){
            $("#url").val(url);
            $("#filename").val(filename);
            $("#submit").click();
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
			<input id="file_date" type="hidden" name="file_date" value="" />
		</form>
		<c:forEach items="${authFunctionCode }" var="auth">
			<c:if test="${auth == 'archiveReel:fileManage' }">
				<div class="sen-crud-page-toolbar" id="sen-toolbar-file">
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="addFile()" plain="true">新增</a>
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="editFile()" plain="true">修改</a>
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="delFile()" plain="true">删除</a>
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="upload()" plain="true">上传</a>
				</div>
			</c:if>
		</c:forEach>
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