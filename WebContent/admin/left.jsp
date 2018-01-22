<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div region="west" style="width: 200px" title="导航菜单" split="true">
	<div class="easyui-accordion" data-options="fit:true,border:false">
		<div title="常用操作" data-options="selected:true,iconCls:'icon-item'" style="padding: 10px">
			<a href="javascript:openTab('用户信息管理','admin/userList.jsp','icon-review')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-writeblog'" style="width: 150px">用户信息管理</a>
			<a href="javascript:openTab('会员类型管理','admin/memberList.jsp','icon-review')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-writeblog'" style="width: 150px">会员类型管理</a>
			<a href="javascript:openTab('房间消费管理','admin/increaseList.jsp','icon-review')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-writeblog'" style="width: 150px">房间消费管理</a>
			<a href="javascript:openTab('房间信息管理','admin/roomList.jsp','icon-review')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-writeblog'" style="width: 150px">房间信息管理</a>
		</div>
		<div title="个人信息管理"  data-options="iconCls:'icon-grxx'" style="padding:10px">
			<a href="javascript:openTab('个人信息','admin/personal.jsp','icon-grxxxg')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-grxxxg'" style="width: 150px;">个人信息</a>
		</div>
		<div title="系统管理"  data-options="iconCls:'icon-system'" style="padding:10px">
			<a href="javascript:openPasswordModifyDialog()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-modifyPassword'" style="width: 150px;">修改密码</a>
			<a href="javascript:refreshSystemCache()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-refresh'" style="width: 150px;">刷新系统缓存</a>
			<a href="javascript:logout()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-exit'" style="width: 150px;">安全退出</a>
		</div>
	</div>
</div>
