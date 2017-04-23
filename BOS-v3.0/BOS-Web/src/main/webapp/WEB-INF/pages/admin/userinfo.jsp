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
$(function(){
	$("body").css({visibility:"visible"});
	$('#save').click(function(){
		$('#form').submit();
	});
	
	$.post("${pageContext.request.contextPath}/role_ajaxRoles",function(data){
		// data List<Role>
		 $(data).each(function(){
			 $("#roleIds").append("<input type='checkbox' name='roleIds' value='"+this.id+"'/>"+this.name+"&nbsp;&nbsp;");
		   });
		 });
	});

$.extend($.fn.validatebox.defaults.rules, {

	uniqueTel: {
		validator: function(value){
			var flag;
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath }/user_ajaxPhone.action",
				data: {telephone:value},
				async: false,/* 必须要指定异步为false */
				success: function(data){
					flag = data;
				 }
			});
		return flag;
	},
	message: "手机号码重复"
		},
	uniqueName: {
		validator: function(value){
			var flag;
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath }/user_ajaxName.action",
				data: {username:value},
				async: false,/* 必须要指定异步为false */
				success: function(data){
					flag = data;
				 }
			});
		return flag;
	},
	message: "用户名重复"
		}
	});
	
function _validatebox(){
	if($("#form").form('validate')){
		//校验通过
		$("#form").submit();
	}else{
		$.messager.alert('警告','表单数据异常','warning');
	}
}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
		<div class="datagrid-toolbar">
			<a id="save" icon="icon-save" href="javascript:_validatebox();" class="easyui-linkbutton" plain="true" >保存</a>
		</div>
	</div>
    <div region="center" style="overflow:auto;padding:5px;" border="false">
       <form id="form" action="${pageContext.request.contextPath }/user_save" method="post" >
           <table class="table-edit"  width="95%" align="center">
           		<tr class="title"><td colspan="4">基本信息</td></tr>
	           	<tr><td>用户名:</td><td><input type="text" name="username" id="username" class="easyui-validatebox" data-options="required:true,validType:['uniqueName']" /></td>
					<td>口令:</td><td><input type="password" name="password" id="password" class="easyui-validatebox" required="true" validType="minLength[5]" /></td></tr>
				<tr class="title"><td colspan="4">其他信息</td></tr>
	           	<tr><td>工资:</td><td><input type="text" name="salary" id="salary" class="easyui-numberbox" /></td>
					<td>生日:</td><td><input type="text" name="birthday" id="birthday" class="easyui-datebox" /></td></tr>
	           	<tr><td>性别:</td><td>
	           		<select name="gender" id="gender" class="easyui-combobox" style="width: 150px;">
	           			<option value="">请选择</option>
	           			<option value="男">男</option>
	           			<option value="女">女</option>
	           		</select>
	           	</td>
					<td>单位:</td><td>
					<select name="station" id="station" class="easyui-combobox" style="width: 150px;">
	           			<option value="">请选择</option>
	           			<option value="总公司">总公司</option>
	           			<option value="分公司">分公司</option>
	           			<option value="厅点">厅点</option>
	           			<option value="基地运转中心">基地运转中心</option>
	           			<option value="营业所">营业所</option>
	           		</select>
				</td></tr>
				<tr>
					<td>联系电话</td>
					<td colspan="3" >
						<input type="text" name="telephone" id="telephone" class="easyui-validatebox" data-options="required:true,validType:['uniqueTel']"/>
					</td>
				</tr>
				<tr>
						<td>选择角色</td>
						<td id="roleIds" colspan="3">
						<!-- <input type="checkbox" name="role.id" id="roleId"/>选择列表 -->
							<%-- <input class="easyui-combobox" name="role.id" id="roleId"
    							data-options="valueField:'id',textField:'name',url:'${pageContext.request.contextPath }/role_ajaxRoles'" /> --%>  
						</td>
					</tr>
	           	<tr><td>备注:</td><td colspan="3"><textarea name="remark" style="width:80%"></textarea></td></tr>
           </table>
       </form>
	</div>
</body>
</html>