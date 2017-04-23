<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收排标准</title>
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
<script type="text/javascript">
	function doAdd(){
		//alert("增加...");
		$("#id").val("");
		$("#name").val('');
		$("#minweight").val(null);
		$("#maxweight").val(null);
		
		$('#addStandardWindow').window("open");
	}
	
	function doView(){
		/* 查看收派标准表数据，分页显示出来
		
		*/
		$.post("${pageContext.request.contextPath }/standrad_findAll.action",
				   function(data){
				     alert("返回数据："+data); 
				     console.log(data); 
				   }, "json");
		alert("查看...");
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
		id : 'button-view',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
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
		field : 'name',
		title : '标准名称',
		width : 120,
		align : 'center'
	}, {
		field : 'minweight',
		title : '最小重量',
		width : 120,
		align : 'center'
	}, {
		field : 'maxweight',
		title : '最大重量',
		width : 120,
		align : 'center'
	}, {
		field : 'user.username',
		title : '操作人',
		width : 120,
		align : 'center',
		formatter : function(data,row,index){
			if(row.user!=null)
			return row.user.username;
		}
		
	}, {
		field : 'updatetime',
		title : '操作时间',
		width : 160,
		align : 'center'
	}, {
		field : 'user.station',
		title : '操作单位',
		width : 200,
		align : 'center',
		/* 自定义控件去控制关联属性的显示 */
		formatter : function(data,row, index){
			if(row.user!=null)
			return row.user.station;
		}
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
			pageList: [4,6,10],
			pagination : true,
			toolbar : toolbar,
			/* url : "${pageContext.request.contextPath }/json/standard.json", */
		    url : "${pageContext.request.contextPath }/standrad_pageQuery.action", 
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加收派标准窗口
		$('#addStandardWindow').window({
            title: '收派标准',
            width: 400,
            modal: true,
            shadow: true,
            closed: true,
            height: 400,
            resizable:false
        });
		
	});
	
	function doDblClickRow(rowIndex, rowData){
		$("#name").val(rowData.name);
		$('#minweight').numberbox('setValue', rowData.minweight); 	
		$('#maxweight').numberbox('setValue', rowData.maxweight); 
		$("#id").val(rowData.id);
		
		$('#addStandardWindow').window("open");
		/* alert("双击表格数据..."); */
	}
	function _validatebox(){
		if($("#_form").form('validate')){
			//校验通过
			$("#_form").submit();
		}else{
			$.messager.alert('警告','表单数据异常','warning');
		}
	}
		
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
    <form id="formgrid" action="${pageContext.request.contextPath }/standrad_delete.action" method="post">
    <div region="center" border="false">
    	<table id="grid"></table>
	</div>
	</form>
	<div class="easyui-window" title="添加收派标准" id="addStandardWindow" collapsible="false" minimizable="false" maximizable="false" style="top:100px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="javascript:_validatebox();" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="_form" action="${pageContext.request.contextPath }/standrad_saveOrUpdate.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派标准信息</td>
						<td><input type="hidden" id="id" name="id" value=""/></td>
					</tr>
					<tr>
						<td>标准名称</td>
						<td><input id="name" name="name" type="text" class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>最小重量</td>
						<td><input id="minweight" name="minweight"  type="text" class="easyui-numberbox"  /></td>
					</tr>
					<tr>
						<td>最大重量</td>
						<td><input id="maxweight" name="maxweight" type="text" class="easyui-numberbox" /></td>
					</tr>
					</table>
			</form>
		</div>
	</div>
</body>
</html>