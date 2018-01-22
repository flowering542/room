<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>客户信息管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
</head>
<script type="text/javascript">

	var url;
	
	function openCustomerAddDialog() {
		$("#dlg").dialog("open").dialog("setTitle", "添加客户信息");
		doReset("fm");
		url = "${pageContext.request.contextPath}/CustomerServlet?action=add";
	}
	
	function openCustomerModifyDialog() {
		var selectedRows = $("#dg").datagrid("getSelections");
		if(selectedRows.length != 1) {
			$.messager.alert("系统提示", "请选择一个要修改的客户信息");
			return;
		}
		var row = selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle", "修改客户信息");
		$("#fm").form("load", row);//会自动识别name属性，将row中对应的数据，填充到form表单对应的name属性中
		url = "${pageContext.request.contextPath}/CustomerServlet?action=save&id=" + row.stuid;
	}
	
	function saveCustomer() {
		$("#fm").form("submit",{
			url: url,
			onSubmit: function() {
				return $(this).form("validate");
			}, //进行验证，通过才让提交
			success: function(result) {
				var result = eval("(" + result + ")"); //将json格式的result转换成js对象
				if(result.success) {
					$.messager.alert("系统提示", "客户信息保存成功");
					$("#dlg").dialog("close"); //关闭对话框
					$("#dg").datagrid("reload"); //刷新一下
				} else {
					$.messager.alert("系统提示", "客户信息保存失败:"+result.msg);
					return;
				} 
			}
		});
	}
	
	function closeCustomerDialog() {
		$("#dlg").dialog("close"); //关闭对话框
	}
	
	
	function deleteCustomer() {
		var selectedRows = $("#dg").datagrid("getSelections");
		if(selectedRows.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的数据");
			return;
		}
		var idsStr = [];
		for(var i = 0; i < selectedRows.length; i++) {
			idsStr.push(selectedRows[i].cid);
		}
		var ids = idsStr.join(","); //1,2,3,4
		console.log(ids);
		$.messager.confirm("系统提示", "<font color=red>您确定要删除选中的"+selectedRows.length+"条数据么？</font>", function(r) {
			if(r) {
				$.post("${pageContext.request.contextPath}/CustomerServlet?action=delete",
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
		if(val == 1) {
			return "金卡";
		} else if(val == 2) {
			return "银卡";
		} else if(val == 3){
			return "无";
		}
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
 <table id="dg" title="客户信息管理" class="easyui-datagrid"
            url="${pageContext.request.contextPath}/CustomerServlet?action=query"
            toolbar="#tb" pagination="true"
            rownumbers="true" fitColumns="true">
        <thead>
            <tr>
                <th field="cb" checkbox="true" align="center"></th>
                <th field="cid" width="50" align="center">身份证号</th>
                <th field="cname" width="50" align="center">姓名</th>
                <th field="birthday" width="30" align="center">出生年月</th>
                <th field="sex" width="30" align="center">性别</th>
                <th field="phone" width="50" align="center">联系电话</th>
                <th field="email" width="50" align="center">邮箱</th>
                <th field="mid" width="50" align="center" formatter="formatState">会员等级</th>
            </tr>
        </thead>
    </table>
<div id="tb"> 
	<div>
		<a href="javascript:openCustomerAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:deleteCustomer()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		<a href="javascript:openCustomerModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>		
		<a href="javascript:reload()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新</a>		
	</div>
</div>
<div id="dlg" class="easyui-dialog" style="width:400px;  padding:10px 20px" 
	closed="true" buttons="#dlg-buttons">
	<form id="fm" method="post">
		<table cellspacing="8px">
		    <tr>
				<td>身份证号</td>
				<td>
					 <input name="cid" class="easyui-validatebox" required="true">
				</td>
			</tr>
			<tr>
				<td>姓名</td>
				<td>
					 <input name="cname" class="easyui-validatebox" required="true">
				</td>
			</tr>
			<tr>
				<td>性别</td>
				<td>
				   <select name="sex" class="easyui-textbox" id="option">
		                   <option value="男">男</option>
		                   <option value="女">女</option>
		            </select>
				</td>
			</tr>
			<tr>
				<td>出生日期</td>
				<td>
					 <input name="birthday" class="easyui-datebox" required="true">
				</td>
			</tr>
			<tr>
				<td>联系电话</td>
				<td>
					 <input name="phone" class="easyui-validatebox" required="true">
				</td>
			</tr>
			<tr>
				<td>邮箱</td>
				<td>
					 <input name="email" class="easyui-validatebox" required="true">
				</td>
			</tr>
			<tr>
				<td>会员等级</td>
				<td>
				   <select name="mid" class="easyui-textbox" id="option">
		                   <option value="3">无</option>
		                   <option value="2">银卡</option>
		                   <option value="1">金卡</option>
		            </select>
				</td>
			</tr>
		</table>
	</form>
</div>

<div id="dlg-buttons">
	<div>
		<a href="javascript:saveCustomer()" class="easyui-linkbutton" iconCls="icon-ok" plain="true">保存</a>
		<a href="javascript:closeCustomerDialog()" class="easyui-linkbutton" iconCls="icon-cancel" plain="true">关闭</a>
	</div>
</div>
</body>
</html>
