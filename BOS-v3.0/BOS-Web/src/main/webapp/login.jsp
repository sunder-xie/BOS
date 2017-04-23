<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
    <%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆页面</title>
<script src="${pageContext.request.contextPath }/js/jquery-1.8.3.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/style.css" />
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style_grey.css" />
<style>
input[type=text] {
	width: 80%;
	height: 25px;
	font-size: 12pt;
	font-weight: bold;
	margin-left: 45px;
	padding: 3px;
	border-width: 0;
}

input[type=password] {
	width: 80%;
	height: 25px;
	font-size: 12pt;
	font-weight: bold;
	margin-left: 45px;
	padding: 3px;
	border-width: 0;
}

#loginform\:codeInput {
	margin-left: 1px;
	margin-top: 1px;
}

#loginform\:vCode {
	margin: 0px 0 0 60px;
	height: 34px;
}
</style>
<script type="text/javascript">

	 var flag = false;
		$(function(){
			
			$("input[name='checkcode']").blur(function(){
				$.ajax({
					type: "POST",
					url: "${pageContext.request.contextPath }/login_ajaxChecked",
					data: {"checkcode":this.value},
					async: false,
					success: function(data){
						flag = data;
						$("#_p").empty();
						if(data){
							$("#_p").append("<font color='green'>您的验证码✅️</font>");
						}else{
							$("#_p").append("<font color='red'>您的验证码❌</font>");
						}
					 }
				});
			});
			
	});
	function chenck() {
		if(flag==true)
		$('#loginform').submit();
	}
</script>



</head>
<body>
	<div
		style="width: 900px; height: 50px; position: absolute; text-align: left; left: 50%; top: 50%; margin-left: -450px;; margin-top: -280px;">
		<img src="${pageContext.request.contextPath }/images/logo.png" style="border-width: 0; margin-left: 0;" />
		<span style="float: right; margin-top: 35px; color: #488ED5;">新BOS系统以宅急送开发的ERP系统为基础，致力于便捷、安全、稳定等方面的客户体验</span>
	</div>
	<div class="main-inner" id="mainCnt"
		style="width: 900px; height: 440px; overflow: hidden; position: absolute; left: 50%; top: 50%; margin-left: -450px; margin-top: -220px; background-image: url('${pageContext.request.contextPath }/images/bg_login.jpg')">
		<div id="loginBlock" class="login"
			style="margin-top: 80px; height: 255px;">
			<div class="loginFunc">
				<div id="lbNormal" class="loginFuncMobile">员工登录</div>
			</div>
			<div class="loginForm">
					
					<div align="center" style=" size: 7px;height: 22px">
						    <font color="red"><s:actionerror/></font>
							<span id="_p"></span>
					</div>
					<s:form namespace="/" action="loginAction_login" id="loginform" name="loginform" method="post" cssClass="niceform" theme="simple" target="_top">
					<div id="idInputLine" class="loginFormIpt showPlaceholder"
						style="margin-top: 5px;">
						<label for="idInput" class="placeholder" id="idPlaceholder">帐号：</label>
							<s:textfield id="loginform:idInput" name="username" value="" cssClass="loginFormTdIpt" maxlength="50" tabindex="1" title="请输入账号"/>
					</div>
					<div class="forgetPwdLine"></div>
					<div id="pwdInputLine" class="loginFormIpt showPlaceholder">
						<label for="pwdInput" class="placeholder" id="pwdPlaceholder">密码：</label>
							<s:password id="loginform:pwdInput"   name="password" value="" cssClass="loginFormTdIpt" maxlength="50" tabindex="2" title="请输入密码"/>
					</div>
					<div class="loginFormIpt loginFormIptWiotTh"
						style="margin-top:58px;">
						<div id="codeInputLine" class="loginFormIpt showPlaceholder"
							style="margin-left:0px;margin-top:-40px;width:50px;">
							<s:textfield id="loginform:codeInput" cssClass="loginFormTdIpt" name="checkcode" title="请输入验证码" />
							<img id="loginform:vCode" src="${pageContext.request.contextPath }/validatecode.jsp"
								onclick="javascript:document.getElementById('loginform:vCode').src='${pageContext.request.contextPath }/validatecode.jsp?'+Math.random();" />
						</div>	<!-- $('#loginform').submit() -->
						<a href="javascript:void(0);" id="loginform:j_id19" name="loginform:j_id19" onclick="chenck();"
						<span
							id="loginform:loginBtn" class="btn btn-login"
							style="margin-top:-36px;">登录</span>
						</a>
						
					</div>
				</s:form>
			</div>
		</div>
	</div>
	<div
		style="width: 900px; height: 50px; position: absolute; text-align: left; left: 50%; top: 50%; margin-left: -450px;; margin-top: 220px;">
		<span style="color: #488ED5;">Powered By www.itcast.cn</span><span
			style="color: #488ED5;margin-left:10px;">推荐浏览器（右键链接-目标另存为）：<a
			href="http://download.firefox.com.cn/releases/full/23.0/zh-CN/Firefox-full-latest.exe">Firefox</a>
		</span><span style="float: right; color: #488ED5;">宅急送BOS系统</span>
	</div>
</body>
</html>