<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>个人信息页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
</head>

<body style="margin: 10px; font-family: microsoft yahei">

	<div id="p" class="easyui-panel" title="个人信息" style="padding: 10px;">
		<form id="fm" method="post">
			<table cellspacing="15px">
				<tr>
					<td width="65px">用户名：</td>				
					<td>
						${currentUser.username}
					</td>
				</tr>
				<tr>
					<td width="65px">姓名：</td>				
					<td>
						${currentUser.realname}
					</td>
				</tr><tr>
					<td width="65px">性别：</td>				
					<td>
						${currentUser.sex}
					</td>
				</tr>
				<tr>
					<td width="65px">年龄：</td>				
					<td>
						${currentUser.age}
					</td>
				</tr>
				<tr>
					<td width="65px">类型：</td>				
					<td>
						<c:if test="${currentUser.usertype==1}">
						    <c:out value="前台操作员"></c:out>
						</c:if>
						<c:if test="${currentUser.usertype==2}">
						    <c:out value="系统管理员"></c:out>
						</c:if>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>

