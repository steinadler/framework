<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	// 拒绝直接访问JSP文件，所有动态请求必须经过过滤器和spring框架，然后才能把控制权转到JSP文件
	Object o = request.getAttribute("passFilter");
	if(o == null){
		//response.sendRedirect("/404.action");
	}
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" ></c:set>

<fmt:setLocale value="zh_CN" /> 
<%-- <fmt:setBundle basename="system" var="system"/> --%>
<fmt:setBundle basename="message" />
