<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp" %>
	<form role="form" method="post">
		<div class="box-body">
			<div class="form-group">
				<label for="title">Title</label>
				<input type="text" id="title" name="title" class="form-control" placeholder="Enter Title"/>		
			</div>
			<div class="form-group">
				<label for="content">Content</label>
				<textarea name="content" id="content" class="form-control" rows="3" placeholder="Enter"></textarea>		
			</div>
			<div class="form-group">
				<label for="writer">Writer</label>
				<input type="text" id="writer" name="writer" class="form-control" placeholder="Enter Writer"/>		
			</div>
		</div>
		<div class="box-footer">
			<button type="submit" class="btn btn-primary">Submit</button>
		</div>
	</form>
<%@include file="../include/footer.jsp" %>