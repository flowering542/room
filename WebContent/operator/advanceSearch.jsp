<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>管理页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
	var url;
	function openDialog() {
		var selectedRows = $("#dg").datagrid("getSelections");
		if(selectedRows.length != 1) {
			$.messager.alert("系统提示", "请选择一个要入住的客房");
			return;
		}
		var row = selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle", "增添入住客房信息");
		$("#fm").form("load", row);//会自动识别name属性，将row中对应的数据，填充到form表单对应的name属性中
		url = "${pageContext.request.contextPath}/AdvanceServlet?action=book";
	}
	function saveStudent() {
		$("#fm").form("submit",{
			url: url,
			onSubmit: function() {
				return $(this).form("validate");
			}, //进行验证，通过才让提交
			success: function(result) {
				var result = eval("(" + result + ")"); //将json格式的result转换成js对象
				if(result.success) {
					$.messager.alert("系统提示", "客房入住成功！");
					$("#dlg").dialog("close"); //关闭对话框
					searchRoom(); //刷新一下
				} else {
					$.messager.alert("系统提示", "客房入住定失败:"+result.msg);
					return;
				} 
			}
		});
	}
	function closeStudentDialog() {
		$("#dlg").dialog("close"); //关闭对话框
	}
	function searchRoom() {
		$.post("${pageContext.request.contextPath}/AdvanceServlet?action=search1", {
			   person:$("#person").val(),
			   phone:$("#phone").val()
			}, function(data, states) {
				$("#dg").datagrid("loadData", data);
			},"json");
	}
	function reload() {
		searchRoom();
	}
</script>
</head>
<body style="margin: 1px; font-family: microsoft yahei">
<table id="dg" title="预定查询" class="easyui-datagrid" fitColumns="true" pagination="true"
   data-options="rownumbers:true,toolbar:'#tb'">
	<thead>
		<tr>
			<th field="cb" checkbox="true" align="center"></th>
			<th field="rno" width="50" align="center">房间号</th>
			<th field="fno" width="50" align="center">楼层号</th> 
			<th field="standard" width="50" align="center">房间标准</th>
			<th field="cost" width="50" align="center">房间费用</th>
			<th field="position" width="50" align="center">地理位置</th>
			<th field="person" width="50" align="center">客户名</th>
			<th field="phone" width="50" align="center">联系电话</th>
			<th field="btime" width="50" align="center">预订开始时间</th>
			<th field="etime" width="50" align="center">预订结束时间</th>
		</tr>
	</thead>
</table>
<div id="tb"> 
	<div>
	    <span>&nbsp;客户名：&nbsp;</span>
	    <input type="text"  class="easyui-textbox" id="person" style="width:150px;">
	    <span>&nbsp;客户电话：&nbsp;</span>
	    <input type="text"  class="easyui-textbox" id="phone" style="width:150px;">
		<a href="javascript:searchRoom()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		<a href="javascript:openDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">入住</a>	
		<a href="javascript:reload()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新</a>		
	</div>
</div>
<div id="dlg" class="easyui-dialog" style="width:400px;  padding:10px 20px" 
	closed="true" buttons="#dlg-buttons">
	<form id="fm" method="post">
		<table cellspacing="8px">
		    <tr>
				<td>房间号</td>
				<td>
					 <input name="rno" class="textbox"  readonly="true">
				</td>
			</tr>
			<tr>
				<td>入住时间</td>
				<td>
					 <input name="btime"  class="textbox"  readonly="true">
				</td>
			</tr>
			<tr>
				<td>退房时间</td>
				<td>
					 <input name="etime"   class="textbox"  readonly="true">
				</td>
			</tr>
			 <tr>
				<td>客户名</td>
				<td>
					 <input name="person" class="textbox"  readonly="true">
				</td>
			</tr>
			 <tr>
				<td>电话</td>
				<td>
					 <input name="phone" class="textbox"  readonly="true">
				</td>
			</tr>
			<tr>
				<td>身份证号</td>
				<td>
					 <input name="cid" class="easyui-validatebox" data-options="required:true">
				</td>
			</tr>
			<tr>
				<td>预付金</td>
				<td>
					 <input name="prepay" class="easyui-numberbox">
				</td>
			</tr>
		</table>
	</form>
</div>

<div id="dlg-buttons">
	<div>
		<a href="javascript:saveStudent()" class="easyui-linkbutton" iconCls="icon-ok" plain="true">确认</a>
		<a href="javascript:closeStudentDialog()" class="easyui-linkbutton" iconCls="icon-cancel" plain="true">关闭</a>
	</div>
</div>
</body>
</html>
