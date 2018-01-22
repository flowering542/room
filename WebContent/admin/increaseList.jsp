<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>房间消费管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
</head>
<script type="text/javascript">

	var url;
	
	function openIncreaseAddDialog() {
		$("#dlg").dialog("open").dialog("setTitle", "添加房间消费");
		doReset("fm");
		url = "${pageContext.request.contextPath}/IncreaseServlet?action=add";
	}
	
	function openIncreaseModifyDialog() {
		var selectedRows = $("#dg").datagrid("getSelections");
		if(selectedRows.length != 1) {
			$.messager.alert("系统提示", "请选择一个要修改的房间消费");
			return;
		}
		var row = selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle", "修改房间消费");
		$("#fm").form("load", row);//会自动识别name属性，将row中对应的数据，填充到form表单对应的name属性中
		url = "${pageContext.request.contextPath}/IncreaseServlet?action=save&id=" + row.ino;
	}
	
	function saveIncrease() {
		$("#fm").form("submit",{
			url: url,
			onSubmit: function() {
				return $(this).form("validate");
			}, //进行验证，通过才让提交
			success: function(result) {
				var result = eval("(" + result + ")"); //将json格式的result转换成js对象
				if(result.success) {
					$.messager.alert("系统提示", "房间消费保存成功");
					$("#dlg").dialog("close"); //关闭对话框
					$("#dg").datagrid("reload"); //刷新一下
				} else {
					$.messager.alert("系统提示", "房间消费保存失败:"+result.msg);
					return;
				} 
			}
		});
	}
	
	function closeIncreaseDialog() {
		$("#dlg").dialog("close"); //关闭对话框
	}
	
	
	function deleteIncrease() {
		var selectedRows = $("#dg").datagrid("getSelections");
		if(selectedRows.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的数据");
			return;
		}
		var idsStr = [];
		for(var i = 0; i < selectedRows.length; i++) {
			idsStr.push(selectedRows[i].ino);
		}
		var ids = idsStr.join(","); //1,2,3,4
		console.log(ids);
		$.messager.confirm("系统提示", "<font color=red>您确定要删除选中的"+selectedRows.length+"条数据么？</font>", function(r) {
			if(r) {
				$.post("${pageContext.request.contextPath}/IncreaseServlet?action=delete",
						{ids: ids}, function(result){
							 if(result.success) {
								$.messager.alert("系统提示", "数据删除成功！");
								$("#dg").datagrid("reload");
							} else {
								$.messager.alert("系统提示", "数据删除失败！");
							}
				}, "json");
			}
		});
	}
	function formatState(val,row) {
	    if(val == "" || val == null){
	    	return "无";
	    }
	    return val;
   }
 	function reload() {
		$("#dg").datagrid("reload");
	}
 	/*重置form表单*/
    function doReset(formId){
        $(':input','#'+formId)
         .not(':button, :submit, :reset, :hidden,#option')
         .val('')
         .removeAttr('checked')
         .removeAttr('selected');
        $("#option option:first").prop("selected", 'selected'); 
    }
</script>

</head>

<body style="margin: 1px; font-family: microsoft yahei">
 <table id="dg" title="房间消费管理" class="easyui-datagrid"
            url="${pageContext.request.contextPath}/IncreaseServlet?action=query"
            toolbar="#tb" pagination="true"
            rownumbers="true" fitColumns="true">
        <thead>
            <tr>
                <th field="cb" checkbox="true" align="center"></th>
                <th field="rno" width="50" align="center">房间号</th>
                <th field="itype" width="50" align="center">消费类型</th>
                <th field="icost" width="30" align="center">金额</th>
                <th field="itime" width="30" align="center">日期</th>
                <th field="bz" width="50" align="center" formatter="formatState">备注</th>
            </tr>
        </thead>
    </table>
<div id="tb"> 
	<div>
		<a href="javascript:openIncreaseAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:deleteIncrease()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		<a href="javascript:openIncreaseModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>		
		<a href="javascript:reload()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新</a>		
	</div>
</div>
<div id="dlg" class="easyui-dialog" style="width:400px;  padding:10px 20px" closed="true" buttons="#dlg-buttons">
	<form id="fm" method="post">
		<table cellspacing="8px">
		    <tr>
				<td>房间号</td>
				<td>
					<input id="rno" class="easyui-combobox" 
					name="rno" 
					data-options="
					url:'${pageContext.request.contextPath}/IncreaseServlet?action=option',
					valueField:'id',
					textField:'text',
					editable : false, 
					panelHeight:'auto'" />
				</td>
			</tr>
			<tr>
				<td>消费类型</td>
				<td>
					 <input name="itype" class="easyui-validatebox" required="true">
				</td>
			</tr>
			<tr>
				<td>金额</td>
				<td>
					 <input name="icost" class="easyui-numberbox" required="true">
				</td>
			</tr>
			<tr>
				<td>日期</td>
				<td>
					 <input name="itime" class="easyui-datebox" required="true">
				</td>
			</tr>
			<tr>
				<td>备注</td>
				<td>
					 <input name="bz" class="easyui-validatebox">
				</td>
			</tr>
		</table>
	</form>
</div>

<div id="dlg-buttons">
	<div>
		<a href="javascript:saveIncrease()" class="easyui-linkbutton" iconCls="icon-ok" plain="true">保存</a>
		<a href="javascript:closeIncreaseDialog()" class="easyui-linkbutton" iconCls="icon-cancel" plain="true">关闭</a>
	</div>
</div>
</body>
</html>
