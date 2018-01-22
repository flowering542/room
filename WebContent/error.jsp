<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="url" value="${requestScope.url}"/>
<c:if test="${empty url}">
  <c:set var="url" value=""/>
</c:if>
<html>
  <head>
    <title>404错误友好提示页面</title>
    <!-- 3秒钟后自动跳转回首页 -->
    <meta http-equiv="refresh" content="3;url=${pageContext.request.contextPath}/${url}">
  </head>
  <body>
    <img src="${pageContext.request.contextPath}/img/500Error.png"/><br/>
    ${error}
    3秒钟后自动跳转回首页，如果没有跳转，请点击<a href="${pageContext.request.contextPath}/${url}">这里</a>
  </body>
</html>