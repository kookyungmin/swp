<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>

<%@include file="../include/header.jsp" %>
<div class="alert alert-info" role="alert" style="display : none;">새 글이 등록되었습니다.</div>	
	<div class="alert alert-danger" role="alert" style="display : none;">글이 삭제되었습니다.</div>	

	
	<table class="table table-bordered">
	
		<tr>
			<th style="width: 10px">BNO</th>
			<th>TITLE</th>
			<th>WRITER</th>
			<th>REGDATE</th>
			<th style="width: 40px">VIEWCNT</th>
		</tr>
	<c:forEach items="${list}" var="boardVO">
		<tr>
			<td>${ boardVO.bno }</td>
			<td><a href="/board/read?bno=${boardVO.bno}">${ boardVO.title }</a></td>
			<td>${ boardVO.writer}</td>
			<td><fmt:formatDate pattern="YYYY-MM-dd HH:mm:ss" value="${ boardVO.regdate}"/></td>
			<td>${ boardVO.viewcnt}</td>
		</tr>
	</c:forEach>
	</table>
	<div>
		<a href="/board/dummy"><button class="btn btn-danger">dummy생성</button></a>
    	<a href="/board/register"><button class="btn btn-primary">새글등록</button></a>
    </div>
	<script>
		var result = '${result}';
		$(function(){
			if(result === 'success'){
				$('.alert-info').fadeIn(2000,function(){
					$('.alert-info').fadeOut(1000);
				});
				
			}
			if(result === 'removeOK'){
				$('.alert-danger').fadeIn(2000,function(){
					$('.alert-danger').fadeOut(1000);
				});
				
			}
		})
	</script>
	
<%@include file="../include/footer.jsp" %>