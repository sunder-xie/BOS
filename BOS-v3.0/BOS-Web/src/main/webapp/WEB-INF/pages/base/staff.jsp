<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
		/* $("#id").val(null);
		$("#flagId").val(null);
		$("#name").val(null);
		$("#telephone").val(null);
		$("#station").val(null);
		$("#haspda").prop("checked",false); */
		$('#addStaffWindow').window("open");
	}
	
	function doView(){
		alert("查看...");
	}
	
	function doDelete(){
		var arr = $("#grid").datagrid('getSelections');
		if(arr.length==0){
			$.messager.alert('警告','请选择需要删除项','warning');	
		}else{
		$("#formgrid").submit();
	}}
	
	function doRestore(){
		alert("将取派员还原...");
	}
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
		text : '作废',
		iconCls : 'icon-cancel',
		handler : doDelete
	},{
		id : 'button-save',
		text : '还原',
		iconCls : 'icon-save',
		handler : doRestore
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'name',
		title : '姓名',
		width : 120,
		align : 'center',
		sortable : true
	}, {
		field : 'telephone',
		title : '手机号',
		width : 120,
		align : 'center',
		sortable : true
	},  {
		field : 'haspda',
		title : '是否有PDA',
		width : 120,
		sortable : true,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="1"){
				return "有";
			}else{
				return "无";
			}
		}
	}, {
		field : 'deltag',
		title : '是否作废',
		width : 120,
		align : 'center',
		sortable : true,
		formatter : function(data,row, index){
			if(data=="1"){
				return "正常使用"
			}else{
				return "已作废";
			}
		}
	},{
		field : 'standard.name',
		title : '取派标准',
		width : 120,
		align : 'center',
		sortable : true,
		formatter : function(data,row,index){
			if(row.standard!=null)
			return row.standard.name;
		}
		
	}, 
	{
		field : 'station',
		title : '所谓单位',
		width : 200,
		align : 'center',
		sortable : true
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 取派员信息表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [3,6,9],
			pagination : true,
			toolbar : toolbar,
/* 			url : "json/staff.json", */
			url : "${pageContext.request.contextPath }/staff_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加取派员窗口
		$('#addStaffWindow').window({
	        title: '添加取派员',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false,
	        onBeforeClose:function(){
	        	$("#_form")[0].reset();
	        	$("#id").prop("readonly",false);
	        }
	    });
		/* 加载取派标准的下拉列表，通用做法，使用ajax加载 
			$.post("${pageContext.request.contextPath }/staff_findStandard.action?", function(data){
			//回调函数 data为json数据
			$(data).each(function(){
				var option = $("<option value='"+this.id+"'>"+this.name+"</option>");
				$("#select").append(option);
			});
			$("#select").combobox();
			});
		*/
	
	});

	function doDblClickRow(rowIndex, rowData){
		
		$("#id").val(rowData.id);
		$("#id").prop("readonly",true);
		
		$("#flagId").val(rowData.flagId);
		$("#name").val(rowData.name);
		$("#telephone").val(rowData.telephone);
		$("#station").val(rowData.station);
		
		/* 数据回显问题 */
	  $('#standardId').combobox('setValue', rowData.standard.id);  
		if($("#haspda").val()=="1"){
			$("#haspda").prop("checked","checked");
		}
		
		$("#telephone").removeClass("easyui-validatebox");
		$("#id").removeClass("easyui-validatebox");
		$('#addStaffWindow').window("open");
	}
	function _validatebox(){
		if($("#_form").form('validate')){
			//校验通过
			$("#_form").submit();
		}else{
			$.messager.alert('警告','表单数据异常','warning');
		}
	}
	
	$.extend($.fn.validatebox.defaults.rules, {

		uniqueId: {
			validator: function(value){
				var flag;
				$.ajax({
					type: "POST",
					url: "${pageContext.request.contextPath }/staff_ajaxId.action",
					data: {id:value},
					async: false,/* 必须要指定异步为false */
					success: function(data){
						flag = data;
					 }
				});
			return flag;
		},
		message: '取派员编号重复,这是取派员唯一标识'
			},
		validatePhone: {
			validator: function(value){
				
			return /^1[3|4|5|7|8]\d{9}$/.test(value);
		},
		message: '手机号格式不正确'
			},
			uniquePhone: {
			validator: function(value){
				var  flag;
				$.ajax({
					type: "POST",
					url: "${pageContext.request.contextPath }/staff_ajaxPhone.action",
					data: {"telephone":value},
					async: false,/* 必须要指定异步为false */
					success: function(data){
						flag = data;
					 }
				});
			return flag;
		},
		message: '手机号码已经存在'
			}
		});
	
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<form id="formgrid" action="${pageContext.request.contextPath }/staff_delete.action" method="post">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	</form>
	<div class="easyui-window" title="对收派员进行添加或者修改" id="addStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="javascript:_validatebox();" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="_form" action="${pageContext.request.contextPath }/staff_saveOrUpdate.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
						<td><input type="hidden" id="flagId" name="flagId" value=""/></td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->
					<tr>
						<td>取派员编号</td>
						<td><input type="text" id="id" name="id" class="easyui-validatebox" data-options="required:true,validType:['uniqueId']" /></td>
					</tr>
					<tr>
						<td>姓名</td>
						<td><input type="text" id="name" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" id="telephone" name="telephone" class="easyui-validatebox" data-options="required:true,validType:['validatePhone','uniquePhone']" /></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" id="station"name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" id="haspda" name="haspda" value="1" checked="" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
						<!-- 使用下拉列表来生成 -->
						<!-- <select id="select">
							<option value=""></option>
						</select> -->
							<!-- url  发送一个ajax请求，返回json数据，用于回显下拉列表 -->
							<input class="easyui-combobox" name="standard.id" id="standardId" 
    							data-options="required:'true',valueField:'id',textField:'name',url:'${pageContext.request.contextPath }/standrad_findStandard.action?'" 
    							/>  
																		 
						 </td>
					</tr>
					</table>
			</form>
		</div>
	</div>
</body>
</html>	