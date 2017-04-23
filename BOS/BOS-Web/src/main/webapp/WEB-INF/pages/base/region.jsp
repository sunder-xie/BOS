<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域设置</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath }/js/ocupload/jquery.ocupload-1.1.2.js"
	type="text/javascript"></script>
	
	
<script type="text/javascript">
function _validatebox(){
	if($("#_form").form('validate')){
		//校验通过
		$("#_form").submit();
	}else{
		$.messager.alert('警告','表单数据异常','warning');
	}
}

	function doAdd(){
		$("#id").val(null);
		$("#id").prop("readonly","");
		$("#province").val(null);
		$("#city").val(null);
		$("#district").val(null);
		$("#postcode").val(null);
		$("#citycode").val(null);
		$("#shortcode").val(null);
		$('#addRegionWindow').window("open");
	}
	
	function doExport(){
		window.location.href="${pageContext.request.contextPath }/region_exportXLS.action";
	}
	
	function doImport(){
		/* alert("导入"); */
		$('#button-import').upload({
			name : 'upload' , // <input type="file" name="upload" 
			action : '${pageContext.request.contextPath}/region_importXls.action', // 表单提交路径
			enctype: 'multipart/form-data',
			onComplete : function(response){
				var info = eval('('+response+')');
				$.messager.alert('信息',info.msg,'info');
				$('#grid').datagrid('reload');
			}
		});
		
	}
	
 	function doView(){
		
		alert("修改...");
	} 
	
	function doDelete(){
		var arr = $("#grid").datagrid('getSelections');
		if(arr.length==0){
			$.messager.alert('警告','请选择需要删除项','warning');	
		}else{
		$("#formgrid").submit();
	}}
	
	//工具栏
	var toolbar = [ {
		id : 'button-edit',	
		text : '修改',
		iconCls : 'icon-edit',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, 
	{
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo',
		handler : doImport
	},{
		id : 'button-export',
		text : '导出',
		iconCls : 'icon-undo',
		handler : doExport
	}
	,{
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'province',
		title : '省',
		width : 120,
		align : 'center'
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center'
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center'
	}, {
		field : 'postcode',
		title : '邮编',
		width : 120,
		align : 'center'
	}, {
		field : 'shortcode',
		title : '简码',
		width : 120,
		align : 'center'
	}, {
		field : 'citycode',
		title : '城市编码',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [3,5,10],
			pagination : true,
			toolbar : toolbar,
			url : "${pageContext.request.contextPath }/region_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		
		// 添加、修改区域窗口
		$('#addRegionWindow').window({
	        title: '添加修改区域',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
	});
	
function doDblClickRow(rowIndex, rowData){
	
	
	
		/* alert(rowData.city); */
		$("#id").val(rowData.id);
		$("#id").prop("readonly","readonly");
		$("#province").val(rowData.province);
		$("#city").val(rowData.city);
		$("#district").val(rowData.district);
		$("#postcode").val(rowData.postcode);
		$("#citycode").val(rowData.citycode);
		$("#shortcode").val(rowData.shortcode);
		
		
		$('#addRegionWindow').window("open");
	}

</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
<form id="formgrid" action="${pageContext.request.contextPath }/region_delete.action" method="post">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	</form>
	<div class="easyui-window" title="区域添加修改" id="addRegionWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="javascript:_validatebox();" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="_form" action="${pageContext.request.contextPath }/region_saveOrUpdate.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>区域编码</td>
						<td><input type="text" id = "id" name="id" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" id="province" name="province" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" id="city" name="city" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" id="district" name="district" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" id="postcode" name="postcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" id="shortcode" name="shortcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" id="citycode" name="citycode" class="easyui-validatebox" required="true"/></td>
					</tr>
					</table>
			</form>
		</div>
	</div>
</body>
</html>