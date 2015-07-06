<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>

<head>
<title>访问超时</title>
<script>
	location.href = "${contextPath}";
</script>
</head>

<body>
	<h4>&nbsp;&nbsp;用户注销</h4>
</body>
</html>