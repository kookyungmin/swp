<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp" %>

<c:if test="${result eq 'saveOK'}">	 
	<div class="alert alert-warning" role="alert">글이 수정되었습니다.</div>	
</c:if>	
	<div class="box-body">
		<span><b>글번호:</b> ${boardVO.bno}</span>	
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
	<div>
		<a href="/board/update${cri.makeQuery()}&bno=${boardVO.bno}" class="btn btn-warning">update</a>
		<a href="/board/listPage${cri.makeQuery()}" class="btn btn-primary">LIST ALL</a>
		<button id="btn-remove-read" class="btn btn-danger">delete</button>
	</div>
	<script>
		$(function(){
			$('#btn-remove-read').click(function(){
				if(confirm("Are u sure?")){
					/* self.location.href = "/board/remove?bno=${boardVO.bno}"; */
					self.location.href = "/board/remove${cri.makeQuery()}&bno=${boardVO.bno}";
				}
			});
		});
		var result = '${result}';
	</script>
<%@include file="../include/footer.jsp" %>