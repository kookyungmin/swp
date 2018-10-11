<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@include file="include/header.jsp" %>

<form action="/loginPost" role="form" method="post">
	<div class="box-body">
		<div class="form-group">
			<label for="uid">id</label>
			<input type="text" id="uid" name="uid" class="form-control" placeholder="Enter id"/>		
		</div>
		<div class="form-group">
			<label for="upw">password</label>
			<input type="password" id="upw" name="upw" class="form-control" placeholder="Enter Password"/>		
		</div>
		<div class="form-group">
			<label for="useCookie">로그인 상태 유지</label>
			<input type="checkbox" id="useCookie" name="useCookie"/>
		</div>		
	</div>
	<button type="submit" class="btn btn-primary">Sign In</button>	
</form>
<div class="text-center">
	<a href="${naver_url}"><img src="/resources/img/naverLogin.png" alt="Naver_Login" height="50" width="100"/></a>
	<a href="${google_url}"><img src="/resources/img/googleLogin.png" alt="Google_Login" height="50" width="100"></a>
</div>
<%@include file="include/footer.jsp" %>
