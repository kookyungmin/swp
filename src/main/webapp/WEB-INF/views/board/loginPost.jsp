<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
	
<%@include file="../include/header.jsp" %>
<div>
	<div id="loginResult" class="center">${ loginResult }</div>
	
	<div class="box-footer">
		<a href="/board/login" class="btn btn-primary">다시 로그인창으로 이동</a>
	</div>
</div>

<%@include file="../include/footer.jsp" %>