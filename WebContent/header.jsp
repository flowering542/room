<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div region="north" style="height: 68px; background-color: #E0ECFF">
	<table width="100%">
		<tr>
			<td valign="bottom" width="50%">
				<h2 style="margin:13px 0px 0px 0px;">客房信息管理系统</h2>
			</td>
			<td valign="bottom" align="right" width="50%">
				<font size="3">&nbsp;&nbsp;<strong>欢迎：</strong>${currentUser.realname}</font>
				<a href="javascript:logout()" class="easyui-linkbutton" iconCls="icon-exit" plain="true">注销</a>
			</td>
		</tr>
	</table>
</div>	