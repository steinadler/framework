<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.companyname.projectname.system.Global"%>
<%@ page import="com.companyname.projectname.webpage.user.entry.UserItem"%>
<%@ include file="inc/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title><%=Global.Title%></title>
<link rel="stylesheet" type="text/css" href="${ctx}/resource/css/reset.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resource/css/boxy.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resource/css/style.css" />
<script type="text/javascript" src="${ctx}/resource/js/lang/message.<%=Global.Language%>.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/jquery.boxy.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/event.js"></script>
<script type="text/javascript" src="${ctx}/resource/js/init.js"></script>
<script type="text/javascript">
var Globals = {};
Globals.define = {};
Globals.ctx = "${ctx}";
</script>
</head>
<body>
<code id="pagename" style="display:none">c:index_a:index</code>
	<ul>
		<c:forEach items="${navigator}" var="item"><li><a href='${ctx}/${item.pageUrl}'><fmt:message key="menu_${item.menuName}" /></a></li></c:forEach>
	</ul>
	<% if(request.getSession().getAttribute(Global.SessionKey_LoginUser) != null){%>
		<% UserItem user = (UserItem)request.getSession().getAttribute(Global.SessionKey_LoginUser); %>
		welcome(<%=user.getShowName() %>)---
		<a href="javascript:void(0);" class="logout" ><fmt:message key="index_user_userlogout"/></a>
	<%}else{%>
		<a href="${ctx }/login.action" ><fmt:message key="index_user_userlogin"/></a>
	<%} %>
</body>
</html>