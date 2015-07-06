<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page import="com.baidu.ueditor.ActionEnter"%>

<%
	request.setCharacterEncoding("utf-8");
	response.setHeader("Content-Type", "text/html");

	response.addHeader("Access-Control-Allow-Origin", "*");
	response.setHeader("Access-Control-Allow-Origin", "*");

	response.addHeader("Access-Control-Allow-Headers", "X-Requested-With,X_Requested_With");
	response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,X_Requested_With");

	String rootPath = application.getRealPath("/");
	out.write(new ActionEnter(request, rootPath).exec());
%>