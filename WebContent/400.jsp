<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>

<head>
<title>Error 400</title>
<style type="text/css">
body {
	background-color: #f0ffff;
}

.adb {
	width: 500px;
	height: 500px;
	margin: 0 auto;
	background-color: #fff;
	border: 1px solid #ccc;
	margin-top: 50px;
}

.lock_access {
	text-align: center;
	padding-top: 50px;
}
</style>
</head>

<body>
	<div class="adb">
		<div class="lock_access">
			<img alt="Error" src="${contextPath}/static/image/oba.gif">
			<h2>请求无效</h2>
			<a href="${contextPath}/index">回到主页</a>
		</div>
	</div>
</body>
</html>