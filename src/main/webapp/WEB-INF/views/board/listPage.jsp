<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>

<%@include file="../include/header.jsp" %>
	<div class="alert alert-info" role="alert" style="display : none;">새 글이 등록되었습니다.</div>	
	<div class="alert alert-danger" role="alert" style="display : none;">글이 삭제되었습니다.</div>	

	
	
	<div class="col-md-1 col-md-offset-11">
		<select class="form-control" id="perPageSel">
  			<option value="10">10</option>
  			<option value="15">15</option>
  			<option value="20">20</option>
		</select>
	</div>
	
	
	
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
			<!-- <td><a href="/board/read?bno=${boardVO.bno}">${ boardVO.title }</a></td> -->
			<td><a href="/board/read${pageMaker.makeQuery(pageMaker.cri.page)}&bno=${boardVO.bno}">${boardVO.title}</a></td>
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
	
	<div class="text-center">
		<nav aria-label="pagination">
		  	<ul class="pagination">
		  	
			    <li id="page-prev">
			      	<a href="listPage${pageMaker.makeQuery(pageMaker.startPage - 1)}" onclickX="gogo(${pageMaker.startPage - 1})" aria-label="Prev"><span aria-hidden="true">«</span></a>
			    </li>
			    
			    <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
			    	<li id="page${idx}">
				    	<a href="listPage${pageMaker.makeQuery(idx)}" onclickX="gogo(${idx})">
				      		<span>${idx}<span class="sr-only">(current)</span></span>
				    	</a>
			    	</li>
			    </c:forEach>
			    
			    <li id="page-next">
			    	<a href="listPage${pageMaker.makeQuery(pageMaker.endPage + 1)}" onclickX="gogo(${pageMaker.endPage + 1})" aria-label="Next"><span aria-hidden="true">»</span></a>
			    </li>
		  	</ul>
		</nav>
	</div>
	
	
	<script>
		var result = '${result}';
		var thisPage = '${pageMaker.cri.page}';
		var canPrev = '${pageMaker.prev}';
		var canNext = '${pageMaker.next}';
		
		function setSelect(){
			var perPageNum = "${pageMaker.cri.perPageNum}";
			var $perPageSel = $('#perPageSel');
			var thisPage = '${pageMaker.cri.page}';
			$perPageSel.val(perPageNum).prop("selected",true);
			$perPageSel.on('change',function(){ //변경이 있을 때
				gogo(thisPage, $perPageSel.val());
				
			})
		}
		function gogo(page, perPageNum){
			//자바스크립트는 매개변수가 않아도 에러가 아니기에 추가해주는 부분
			perPageNum = perPageNum || $('#perPageSel').val();
			window.location.href = "listPage?page=" + page + "&perPageNum=" + perPageNum;
		}
		$(function(){
			setSelect();
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
			if(canPrev !== 'true'){
				$('#page-prev').addClass('disabled');
			}
			if(canNext !== 'true'){
				$('#page-next').addClass('disabled');
			}
			$('#page'+thisPage).addClass('active');
		})
	</script>
<%@include file="../include/footer.jsp" %>