<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../inc/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link rel="stylesheet" type="text/css" href="${ctx}/resource/css/reset.css" />
<script type="text/javascript" src="${ctx}/resource/js/jquery.min.js"></script>
<style>
table {width:1030px; border-collapse:collapse; margin:10px 0 0 5px;}
table tr {border:1px #CCC solid; height:32px; line-height:32px;}
table th {height:30px; text-align:center;line-height:32px; font-size:16px; color:#333; background:#e2eef8;}
table tr td {text-align:center; color:#666; border:1px #ccc solid; overflow:hidden;}
</style>
<script type="text/javascript">
$(document).ready(function() {

});
</script>
</head>
<body>
<div style="width:100%; height:126px; text-align:center; line-height:126px; border-bottom:solid 1px red;">
<h1>开发专用页面</h1>
</div>
<div style="width:100%; display:inline-block;">
<ul style="float:left; width:180px; height:768px; line-height:32px; text-align:center; border:solid 1px red;">
	<li><a href="/dev/roomStatus.action" target="main">会议室状态</a></li>
	<li><a href="/dev/dsStatus.action" target="main">数据源状态</a></li>
	<li><a href="/dev/job.action" target="main">任务计划状态</a></li>
	<li><a href="/dev/playCount.action" target="main">直播点播人数</a></li>
	<li><a href="/dev/sysUpdate.action" target="main">系统升级状态</a></li>
	<li><a href="/dev/proxool.action" target="main">proxool监控</a></li>
</ul>
<div style="float:left; border:solid 1px red;">
	<iframe id="iframeId" name="main" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto"  width="1280" height="768" src="/dev/roomStatus.action"></iframe>
</div>
</div>
</body>
</html>