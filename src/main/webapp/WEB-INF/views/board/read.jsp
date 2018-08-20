<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp" %>
	<form role="form" method="post">
		<div class="box-body">
			<div class="form-group">
				<label for="title">Title</label>
				<input type="text" id="title" name="title" class="form-control" value="${boardVO.title}" readonly="readonly"/>		
			</div>
			<div class="form-group">
				<label for="content">Content</label>
				<textarea name="content" id="content" class="form-control" rows="3" readonly="readonly">${boardVO.content}</textarea>		
			</div>
			<div class="form-group">
				<label for="writer">Writer</label>
				<input type="text" id="writer" name="writer" class="form-control" value="${boardVO.writer}" readonly="readonly"/>		
			</div>
		</div>
		<div class="box-footer text-right">
			<a href="#" onclick="remove()" class="btn btn-danger">delete</a>
			<a href="/board/update" class="btn btn-warning">update</a>
			<a href="/board/listAll" class="btn btn-primary">LIST ALL</a>
		</div>
	</form>
<%@include file="../include/footer.jsp" %>