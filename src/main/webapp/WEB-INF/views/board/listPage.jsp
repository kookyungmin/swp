<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="../include/header.jsp" %>

<div id="registerOK" class="alert alert-info hidden" role="alert">새 글이 등록되었습니다.</div>	
<div id="removeOK" class="alert alert-danger hidden" role="alert">글이 삭제되었습니다.</div>

	
<div class="row">
	<!-- 검색 select 박스 추가 -->
	<div class="col-md-11">
		<div class="form-inline">
			<select id="searchTypeSel" name="searchType">
		  		<option value="">검색조건</option>
		  		<option value="t">제목</option> 
		  		<option value="c">내용</option>
		  		<option value="w">작성자</option>
		  		<option value="tc">제목+내용</option>
		  		<option value="all">전체조건</option>
			</select>
			<input class="form-control" type="text" id="keyword" name="keyword" 
				value="${pageMaker.cri.keyword}" placeholder="검색어를 입력하세요"/>
			<button id="searchBtn" class="btn btn-primary">Search</button>
		</div>
	</div>		
	<div class="col-md-1 text-right">
		<select class="form-control" id="perPageSel">
	  		<option value="10">10</option>
	  		<option value="15">15</option>
	  		<option value="20">20</option>
		</select>
	</div>
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
			<td>
				<a href="/board/read${pageMaker.makeQuery(pageMaker.cri.page)}&bno=${boardVO.bno}">
					${boardVO.title}
				</a>
				<small class="text-muted">
					[${boardVO.replycnt}]
				</small>
			</td>
			<td>${ boardVO.writer}</td>
			<td><fmt:formatDate pattern="YYYY-MM-dd HH:mm:ss" value="${ boardVO.regdate}"/></td>
			<td><span class="badge bg-green">${boardVO.viewcnt}</span></td>
		</tr>
	</c:forEach>
</table>

<div>
	<a href="/board/register${pageMaker.makeQuery(pageMaker.cri.page)}"><button class="btn btn-primary">새글등록</button></a>
	<!-- 처음 목록 버튼 추가 -->
	<a href="/board/listPage" class="btn btn-warning">처음목록</a>
	<a href="/board/dummy"><button class="btn btn-danger">dummy생성</button></a>
</div>

<!-- 페이지 번호 -->	
<div class="text-center">
	<nav aria-label="pagination">
		<ul class="pagination">
			<li id="page-prev">
				<a href="listPage${pageMaker.makeQuery(pageMaker.startPage-1)}" aria-label="Prev">
					<span aria-hidden="true">«</span>
				</a>
			</li>
			<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
			    <li id="page${idx}">
				    <a href="listPage${pageMaker.makeQuery(idx)}">
				      	<span>${idx}<span class="sr-only">(current)</span></span>
				    </a>
			    </li>
			</c:forEach>
			<li id="page-next">
			    <a href="listPage${pageMaker.makeQuery(pageMaker.endPage + 1)}" aria-label="Next">
			    	<span aria-hidden="true">»</span>
			    </a>
			</li>
			
		</ul>
	</nav>
</div>
	
<script>
	$(function(){
		//perPageNum select 박스 설정
		setPerPageNumSelect();
		//searchType select 박스 설정
		setSearchTypeSelect();
		
		//등록, 삭제 후 문구 처리
		var result = '${result}';
		$(function(){
			if(result === 'registerOK'){
				$('#registerOK').removeClass('hidden');
				$('#registerOK').fadeOut(2000);
			}
			if(result === 'removeOK'){
				$('#removeOK').removeClass('hidden');
				$('#removeOK').fadeOut(2000);
			}
		})
		
		//prev 버튼 활성화, 비활성화 처리
		var canPrev = '${pageMaker.prev}';
		if(canPrev !== 'true'){
			$('#page-prev').addClass('disabled');
		}
		
		//next 버튼 활성화, 비활성화 처리
		var canNext = '${pageMaker.next}';
		if(canNext !== 'true'){
			$('#page-next').addClass('disabled');
		}
		
		//현재 페이지 파란색으로 활성화
		var thisPage = '${pageMaker.cri.page}';
		//매번 refresh 되므로 다른 페이지 removeClass 할 필요는 없음->Ajax 이용시엔 해야함
		$('#page'+thisPage).addClass('active');
	})
	
	function setPerPageNumSelect(){
		var perPageNum = '${pageMaker.cri.perPageNum}';
		var $perPageSel = $('#perPageSel');
		var thisPage = '${pageMaker.cri.page}';
		
		$perPageSel.val(perPageNum).prop("selected",true);
		$perPageSel.on('change',function(){
			window.location.href = "listPage?page="+thisPage+"&perPageNum="+$perPageSel.val();
		})
	}
	function setSearchTypeSelect(){
		var $searchTypeSel = $('#searchTypeSel');
		var $keyword = $('#keyword');
		
		$searchTypeSel.val('${pageMaker.cri.searchType}').prop("selected",true);
		//검색 버튼이 눌리면
		$('#searchBtn').on('click',function(){
			var searchTypeVal = $searchTypeSel.val();
			var keywordVal = $keyword.val();
			//검색 조건 입력 안했으면 경고창 
			if(!searchTypeVal){
				alert("검색 조건을 선택하세요!");
				$searchTypeSel.focus();
				return;
			//검색어 입력 안했으면 검색창
			}else if(!keywordVal){
				alert("검색어를 입력하세요!");
				$('#keyword').focus();
				return;
			}
			var url = "listPage?page=1"
				+ "&perPageNum=" + "${pageMaker.cri.perPageNum}"
				+ "&searchType=" + searchTypeVal
				+ "&keyword=" + encodeURIComponent(keywordVal);
			window.location.href = url;
		})
	}
</script>
<%@include file="../include/footer.jsp" %>