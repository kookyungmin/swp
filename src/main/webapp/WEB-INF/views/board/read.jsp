<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp" %>

<div id="saveOK" class="alert alert-warning hidden" role="alert">글이 수정되었습니다.</div>	
<section class="content">
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
		<a href="/board/listPage${cri.makeQuery()}" class="btn btn-primary">LIST ALL</a>
		<a href="/board/update${cri.makeQuery()}&bno=${boardVO.bno}" class="btn btn-warning">update</a>
		<button id="btn-remove" class="btn btn-danger">delete</button>
	</div>
</section>




<!-- 댓글 목록 -->
<script id="replies" class="well mt20" type="text/x-handlebars-template">
	<ul class="list-group">
		{{#each list}}
		  <li class="list-group-item">
		  	{{replytext}}
		    <small class="text-muted ml20"><i class="fa fa-user">{{replyer}}</i></small>
		    <small class="text-muted pull-right">{{fromNow regdate}}</small>
		  </li>
		{{/each}}
	</ul>

	<div class="text-center">
		<nav aria-label="pagination">
			<ul class="pagination">
				{{#if pageData.prevPage}}
					<li>
						<a href="#" onclick="replyListPage({{pageData.prevPage}})">
							<span>&lt;&lt;</span>
						</a>
					</li>
				{{/if}}
				{{pageData.currentPage}}
				{{#each pageData.pages as |page|}}
					<li class="{{#if (eq ../currentPage page)}}active{{/if}}">
						<a href="javascript:;" onclick="replyListPage({{page}})" 
							class=data-page="{{page}}">
							{{page}}
						</a>
					</li>
				{{/each}}
	
				{{#if pageData.nextPage}}
					<li>
						<a href="#" onclick="replyListPage({{pageData.nextPage}})">
							<span>&gt;&gt;</span>
						</a>
					</li>
				{{/if}}
			</ul>
		</nav>
	</div>
	
</script>
<button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal">
 댓글 등록
</button>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">댓글 수정</h4>
      </div>
      <div class="modal-body">
      	<div>
      		작성자 : <input type="text" name="replyer" id="newReplyWriter" class="form-control"/>
      	</div>
      	<div>
      		내용 : <textarea name="replytext" id="newReplyText" cols="30" rows="3" class="form-control"></textarea>
      	</div>	
      </div>
      <div class="modal-footer">
        <button onclick="editReply()" id="btnModReply" class="btn">수정</button>
		<button onclick="removeReply()" id="btnDelReply" class="btn">삭제</button>
		<button onclick="closeMod()" id="btnCloseReply" class="btn">닫기</button>
      </div>
    </div>
  </div>
</div>

<script src="/resources/handlebars-v4.0.12.js"></script>
<script src="/resources/moment-min.js"></script>
<script src="/resources/test/hbs1.js"></script>
<script src="/resources/reply.js"></script>
	
<script>
	var result = '${result}';
	$(function(){
		
		replyListPage(1, ${boardVO.bno});
		
		
		$('#btn-remove').click(function(){
			if(confirm("Are u sure?")){
				//삭제 버튼 링크 수정
				self.location.href = "/board/remove${cri.makeQuery()}&bno=${boardVO.bno}";
			}
		});
			
		if(result === 'saveOK'){
			$('#saveOK').removeClass('hidden');
			$('#saveOK').fadeOut(2000);
		}
	});	
</script>
<%@include file="../include/footer.jsp" %>