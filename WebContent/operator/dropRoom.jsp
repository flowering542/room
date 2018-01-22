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

	function deleteRoom() {
		var selectedRows = $("#dg").datagrid("getSelections");
		if(selectedRows.length == 0) {
			$.messager.alert("系统提示", "请选择要退房的房间");
			return;
		}
		if(selectedRows.length != 1) {
			$.messager.alert("系统提示", "请选择一个要退房的客房");
			return;
		}
		var row = selectedRows[0];
		$.messager.confirm("系统提示", "您确定退房么？", function(r) {
			if(r) {
				$.post("${pageContext.request.contextPath}/BookServlet?action=search1",
						{  rno:row.rno,
					       cid:row.cid,
					       btime:row.btime,
					       etime:row.etime
					    }, function(result){
							 if(result.success) {
								$.messager.alert("系统提示", result.msg);
								searchRoom();
							} else {
								$.messager.alert("系统提示", "退房失败！");
							}
				}, "json");
			}
		});
	}
	function searchRoom() {
		$.post("${pageContext.request.contextPath}/BookServlet?action=drop", {
			  rno:$('#rno').val()
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
<table id="dg" title="退房管理" class="easyui-datagrid" fitColumns="true" pagination="true"
   data-options="rownumbers:true,toolbar:'#tb'">
	<thead>
		<tr>
			<th field="cb" checkbox="true" align="center"></th>
			<th field="rno" width="50" align="center">房间号</th>
			<th field="fno" width="50" align="center">楼层号</th> 
			<th field="standard" width="50" align="center">房间标准</th>
			<th field="cost" width="50" align="center">房间费用</th>
			<th field="position" width="50" align="center">地理位置</th>
			<th field="cid" width="50" align="center">身份证号</th>
			<th field="btime" width="50" align="center">入住时间</th>
			<th field="etime" width="50" align="center">退房时间</th>
		</tr>
	</thead>
</table>
<div id="tb"> 
	<div>
	    <span>&nbsp;房间号：&nbsp;</span>
	    <input type="text"  class="easyui-textbox" id="rno" style="width:150px;">
		<a href="javascript:searchRoom()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		<a href="javascript:deleteRoom()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">退房</a>	
		<a href="javascript:reload()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新</a>		
	</div>
</div>
</body>
</html>
