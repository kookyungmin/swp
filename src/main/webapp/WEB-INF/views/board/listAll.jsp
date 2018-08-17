<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>

<%@include file="../include/header.jsp" %>
	
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
    <a href="/board/register"><button class="btn btn-primary">새글등록</button></a>
	<script>
		var result = '${msg}';
		if (result === 'success'){
			alert("OK");
		}
	</script>
<%@include file="../include/footer.jsp" %>