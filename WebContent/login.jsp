<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>登录</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
   <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/Utils.js"></script>
      <script type="text/javascript">
          $(function(){
              //console.info(g_contextPath);
              //console.info(g_basePath);
              $('#loginAndRegisterForm').dialog({   
                  title: '用户登录',
                  width: 240,   
                  height: 155,   
                  closable: false,//设置dialog不允许被关闭
                  cache: false,   
                  modal: true,
                    buttons:[
                              {
                                text:'登录',
                                iconCls: 'icon-ok',
                                width:70,
                                height:30,
                                handler:function(){
                                    //console.info(g_contextPath+'/servlet/LoginHandleServlet');
                                    //console.info(g_basePath+'/servlet/LoginHandleServlet');
                                    //console.info($('#loginForm').serialize());//在火狐中打印的结果：userName=gacl&userPwd=123
                                    loginHandle();//处理用户登录
                                }
                            },
                            {
                                text:'重置',
                                iconCls: 'icon-cancel',
                                width:70,
                                height:30,
                                handler:function(){
                                    doReset('loginForm'); 
                                }
                            }
                        ]

              }); 
              
              /*重置form表单*/
              function doReset(formId){
                  $(':input','#'+formId)
                   .not(':button, :submit, :reset, :hidden,#option')
                   .val('')
                   .removeAttr('checked')
                   .removeAttr('selected');
              }
              
              /*处理用户登录*/
              function loginHandle(){
                  //使用EasyUI提供的form组件来提交表单
                  $('#loginForm').form('submit',{
                    //url:g_contextPath+'/servlet/LoginHandleServlet',
                    url:g_basePath+'/LoginServlet?action=login',//url表示服务器端处理用户登录的URL地址
                    success:function(r){
                        //注意：此时的r只是一个普通的Json字符串，因此需要手动把它变成Json对象
                       console.info(r);
                        //r = JSON.parse(r);//利用IE8支持的原生JSON对象的parse方法将json字符串转换成标准的JSON对象
                        //r=eval('('+r+')');//利用eval方法将将json字符串转换成标准的JSON对象
                        r = $.parseJSON(r);//利用Jquery的parseJSONfang将json字符串转换成标准的JSON对象
                        if(r && r.success){
                            //调用dialog的close方法关闭dialog
                            $('#loginAndRegisterForm').dialog('close');
                            $.messager.show({
                                title:'消息',
                                msg:r.msg
                            });
                            //登录成功后跳转到系统首页
                            //window.location.replace(g_basePath+'/index.jsp');
                            window.location.href = g_basePath+r.url;
                        }else{
                            $.messager.alert('消息',r.msg);
                        }
                    }
                });
              }
          });
      </script>
      
  </head>
  
  <body>
      <div id="loginAndRegisterForm">
          <form method="post" id="loginForm">
              <table>
                  <tr>
                      <th style="text-align:left;">用户名：</th>
                      <!-- class="easyui-textbox"表示使用EasyUI的textbox组件-->
                      <td><input type="text" id="userName" style="width:150px;" name="username" class="easyui-textbox"/></td>
                  </tr>
                  <tr>
                      <th style="text-align:left;">密码：</th>
                      <td><input type="password" id="userPwd" style="width:150px;" name="password" class="easyui-passwordbox"/></td>
                  </tr>
                  <tr>
                      <th style="text-align:left;">用户类型：</th>
                      <td>
                          <select name="usertype" class="easyui-textbox" id="option">
                              <option value="1">前台操作员</option>
                              <option value="2">系统管理员</option>
                          </select>
                      </td>
                  </tr>
              </table>
          </form>
      </div>
  </body>
</html>