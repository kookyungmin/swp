<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp" %>
<form role="form" method="post">
	<div class="box-body">
		<!-- page와 perPageNum  추가 -->
		<input type="hidden" name="page" value="${cri.page}" />
		<input type="hidden" name="perPageNum" value="${cri.perPageNum}" />
			
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
			<input type="text" id="writer" name="writer" class="form-control" placeholder="Enter Writer" readonly value="${ loginUser.uname }"/>		
		</div>
		
		<!-- 파일 첨부 -->
		<div class="form-group">
			<label for="">File Drop Here!</label>
			<div class="fileDrop text-right">
			</div>
			<div id="percent">0%</div>
			<div id="status">ready</div>
		</div>
	</div>
	
	<div class="box-footer">
		<!--  파일 첨부 -->
		<%@include file="uploadedFiles.jsp" %>
		
		<button type="submit" class="btn btn-primary">Submit</button>
		<a href="/board/listPage${cri.makeQuery()}" class="btn btn-danger">Cancel</a>
	</div>
</form>

<form action="/uploadAjax" id="form_attach" method="post" enctype="multipart/form-data">
	<input type="file" name="files" id="ajax-file" class="hidden"/>		
</form>

<%@include file="../include/footer.jsp" %>