<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加业务受理单</title>
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
	$(function(){
		$("body").css({visibility:"visible"});
		
		$("#save").click(function(){
			if($("#_form").form('validate')){
				//校验通过
				$("#_form").submit();
			}else{
				$.messager.alert('警告','表单请填写完整','warning');
			}
		});
	});
</script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="north" style="height:31px;overflow:hidden;" split="false"
		border="false">
		<div class="datagrid-toolbar">
			<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
				plain="true">保存新单</a>
			<a id="edit" icon="icon-edit" href="${pageContext.request.contextPath }/page_qupai_noticebill.action" class="easyui-linkbutton"
				plain="true">工单操作</a>	
		</div>
	</div>
	<div region="center" style="overflow:auto;padding:5px;" border="false">
		<form id="_form" action="${pageContext.request.contextPath }/noticeBill_saveOrUpdate.action" method="post">
			<table class="table-edit" width="95%" align="center">
				<tr class="title">
					<td colspan="4">客户信息</td>
				</tr>
				<tr>
					<td>来电号码:</td>
					<td><input type="text" name="telephone" class="easyui-validatebox"
						required="true" /></td>
					<td>客户编号:</td>
					<td><input type="text" name="customerId" class="easyui-validatebox"
						required="true" /></td>
				</tr>
				<tr>
					<td>客户姓名:</td>
					<td><input type="text" name="customerName" class="easyui-validatebox"
						required="true" /></td>
					<td>联系人:</td>
					<td><input type="text" name="delegater" class="easyui-validatebox"
						required="true" /></td>
				</tr>
				<tr class="title">
					<td colspan="4">货物信息</td>
				</tr>
				<tr>
					<td>品名:</td>
					<td><input type="text" name="product" class="easyui-validatebox"
						required="true" /></td>
					<td>件数:</td>
					<td><input type="text" name="num" class="easyui-numberbox"
						required="true" /></td>
				</tr>
				<tr>
					<td>重量:</td>
					<td><input type="text" name="weight" class="easyui-validatebox"
						required="true" /></td>
					<td>体积:</td>
					<td><input type="text" name="volume" class="easyui-validatebox"
						required="true" /></td>
				</tr>
				<tr>
					<td>取件地址</td>
					<td colspan="3"><input type="text" name="pickaddress" class="easyui-validatebox"
						required="true" size="144"/></td>
				</tr>
				<tr>
					<td>到达城市:</td>
					<td><input type="text" name="arrivecity" class="easyui-validatebox"
						required="true" /></td>
					<td>预约取件时间:</td>
					<td><input type="text" name="pickdate" data-options="required:true,editable:false" class="easyui-datebox"
						 /></td>
				</tr>
				<tr>
					<td>备注:</td>
					<td colspan="3"><textarea rows="5" cols="80" type="text" name="remark" class="easyui-validatebox"
						required="true" ></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>