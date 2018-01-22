<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>    
<title>首页</title>   
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">

	function openTab(text,url,iconCls){
		if($("#tabs").tabs("exists",text)){
			$("#tabs").tabs("select",text);
		}else{
			var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='${pageContext.request.contextPath}/"+url+"'></iframe>";
			$("#tabs").tabs("add",{
				title:text,
				iconCls:iconCls,
				closable:true,
				content:content
			});
		}
	}
	
	function openPasswordModifyDialog() {
		$("#dlg").dialog("open").dialog("setTitle", "修改密码");
	}

	function modifyPassword() {
		$("#fm").form("submit",{
			url: "${pageContext.request.contextPath}/CommonServlet",
			onSubmit: function() {
				var newPassword1 = $("#password1").val();
				var newPassword2 = $("#password2").val();
				if(!$(this).form("validate")) {
					return false; //验证不通过直接false，即没填
				} 
				if(newPassword1 != newPassword2) {
					$.messager.alert("系统提示", "两次密码必须填写一致");
					return false
				}
				return true;
			}, //进行验证，通过才让提交
			success: function(result) {
				var result = eval("(" + result + ")"); //将json格式的result转换成js对象
				if(result.success) {
					$.messager.alert("系统提示", "密码修改成功，下一次登陆生效");
					closePasswordModifyDialog();
				} else {
					$.messager.alert("系统提示", "密码修改失败");
					return;
				} 
			}
		});
	}
	
	function closePasswordModifyDialog() {
		$("#password").val(""); //保存成功后将内容置空
		$("#password2").val("");
		$("#dlg").dialog("close"); //关闭对话框
	}

	function refreshSystemCache() {
		$.post("${pageContext.request.contextPath}/admin/system/refreshSystemCache.do",{},function(result){
			if(result.success){
				$.messager.alert("系统提示","已成功刷新系统缓存！");
			}else{
				$.messager.alert("系统提示","刷新系统缓存失败！");
			}
		},"json");
	}
	
	function logout() {
		$.messager.confirm("系统提示","您确定要退出系统吗？", function(r){
			if(r) {
				window.location.href = "${pageContext.request.contextPath}/LoginServlet?action=logout";
			}
		});
	}
</script>
<style type="text/css">
	body {
		font-family: microsoft yahei;
	}
</style>
</head>
  
<body class="easyui-layout">
<%@ include file="/header.jsp" %>
<div region="center">
	<div class="easyui-tabs" fit="true" border="false" id="tabs">
		<div title="首页" data-options="iconCls:'icon-home'">
			<div align="center" style="padding-top: 100px"><font color="red" size="10">欢迎使用</font></div>
		</div>
	</div>
</div>
<%@ include file="/admin/left.jsp" %>
<%@ include file="/footer.jsp" %>
<div id="dlg" class="easyui-dialog" style="width:400px; height:200px; padding:10px 20px" 
	closed="true" buttons="#dlg-buttons">
	<form id="fm" method="post">
		<table cellspacing="8px">
			<tr>
				<td>旧密码</td>
				<td>
					<input type="password" id="password" name="password" class="easyui-validatebox" 
						 required="true" style="width:200px">
				</td>
			</tr>
			<tr>
				<td>新密码</td>
				<td>
					<input type="password" id="password1" name="password1" class="easyui-validatebox" 
						 required="true" style="width:200px">
				</td>
			</tr>
			<tr>
				<td>确认新密码</td>
				<td>
					<input type="password" id="password2" name="password2" class="easyui-validatebox" 
						required="true" style="width:200px">
				</td>
			</tr>
		</table>
	</form>
</div>
<div id="dlg-buttons">
	<div>
		<a href="javascript:modifyPassword()" class="easyui-linkbutton" iconCls="icon-ok" plain="true">保存</a>
		<a href="javascript:closePasswordModifyDialog()" class="easyui-linkbutton" iconCls="icon-cancel" plain="true">关闭</a>
	</div>
</div>
</body>
</html>
