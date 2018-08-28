<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>

<%@include file="include/header.jsp" %>
<!-- 예외 메시지 출력 -->
<h4>${exception.getMessage()}</h4>
	
<ul>
	<!-- 예외발생 당시의 호출스택(Call Stack)에 있었던 메서드의 정보와 예외 메시지를 화면에 출력 -->
	<c:forEach items="${exception.getStackTrace()}" var="stack">
		<li>${stack.toString()}</li>
	</c:forEach>
</ul>
<%@include file="include/footer.jsp" %>