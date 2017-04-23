<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务通知单</title>
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
	
	function doRepeat(){
		//追单
		var arr = $("#grid").datagrid('getSelections');
		if(arr.length==0){
			$.messager.alert('警告','请选择需要追单项','warning');	
		}else{
		$("#formgrid").submit();
		/* window.location.href="${pageContext.request.contextPath }/page_qupai_noticebill_add.action"; */
	}}
	
	function doCancel(){
		alert("销单...");
	}
	
	function doSearch(){
		$('#searchWindow').window("open");
	}
	
	//工具栏
	var toolbar = [ {
		id : 'button-search',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}, {
		id : 'button-repeat',
		text : '追单',
		iconCls : 'icon-redo',
		handler : doRepeat
	}, {
		id : 'button-cancel',	
		text : '销单',
		iconCls : 'icon-cancel',
		handler : doCancel
	}];
	// 定义列
	var columns = [ [  {
		field : 'id',
		title : '通知单号',
		width : 120,
		align : 'center',
		checkbox : true,
		
	},{
		field : 'type',
		title : '工单类型',
		width : 120,
		align : 'center',
		formatter : function(data,row,index){
			if(row.workBills!=null&&row.workBills[0]!=null){
				return row.workBills[0].type; 
			}
		return "";
		}
	}, {
		field : 'pickstate',
		title : '取件状态',
		width : 120,
		align : 'center',
		formatter : function(data,row,index){
			if(row.workBills!=null&& row.workBills[0]!=null){
				return row.workBills[0].pickstate; }
			
			return "";
		}
	}, {
		field : 'buildtime',
		title : '工单生成时间',
		width : 120,
		align : 'center' ,
		formatter : function(data,row,index){
			if(row.workBills!=null&& row.workBills[0]!=null){
				return row.workBills[0].buildtime.replace("T", "  "); 
			}
				return "";
		}
	}, {
		field : 'attachbilltimes',
		title : '追单次数',
		width : 120,
		align : 'center' ,
		formatter : function(data,row,index){
			if(row.workBills!=null&& row.workBills[0]!=null){
				return row.workBills[0].attachbilltimes; 
			}
				return "";
		}
	}, {
		field : 'staff.id',
		title : '取派员',
		width : 100,
		align : 'center' ,
		formatter : function(data,rows,index){
			if(rows.staff!=null){
				return rows.staff.name; 
			}
				return "";
		}  
	}, {
		field : 'staff.telephone',
		title : '联系方式',
		width : 100,
		align : 'center' ,
		formatter : function(data,rows,index){
			if(rows.staff!=null){
				return rows.staff.telephone;
			}
				return "";
		} 
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			pageList: [3,5,10],
			pagination : true,
			toolbar : toolbar,
			url :  "${pageContext.request.contextPath}/noticeBill_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 查询分区
		$('#searchWindow').window({
	        title: '查询分区',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		$("#btn").click(function(){
			alert("执行查询...");
			$("#searchForm").get(0).reset();// 重置查询表单
			$("#searchWindow").window("close"); // 关闭窗口
		});
	});

	function doDblClickRow(){
		alert("双击表格数据...");
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	
	<form id="formgrid" action="${pageContext.request.contextPath}/noticeBill_doRepeat.action" method="post">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	</form>
	
	<!-- 查询分区 -->
	<div class="easyui-window" title="查询窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="searchForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>客户电话</td>
						<td><input type="text" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>取派员</td>
						<td><input type="text" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>受理时间</td>
						<td><input type="text" class="easyui-datebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2"><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>