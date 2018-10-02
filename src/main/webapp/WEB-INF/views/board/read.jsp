<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/header.jsp" %>

<c:set var = "isTest" scope = "page" value = "${false}"/> <!-- QQQ -->

<c:if test="${true eq isTest}"> <!-- QQQ -->
	<%@include file="../qunit.jsp" %>  <!-- QTest -->
</c:if> <!-- QQQ -->


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

<%@include file="uploadedFiles.jsp" %>


<!-- 댓글 목록 -->
<script id="replies" class="well mt20" type="text/x-handlebars-template">
	<ul class="list-group">
		{{#each list}}
		  <a href="#" class="list-group-item" onclick="editReply({{rno}},'{{replyer}}',`{{replytext}}`)">
		  	{{{transHtml replytext}}}
		  	<small class="text-muted pull-right">{{fromNow regdate}} <i class="fa fa-user ml20">{{replyer}}</i></small>
		  </a>
		{{/each}}
	</ul>
	<button type="button" onclick="editReply()" class="btn btn-primary btn-sm">
 		댓글 등록
	</button>
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
							data-page="{{page}}">
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

<script class="modal fade" id="myModal" type="text/x-handlebars-template">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        	<span aria-hidden="true">
        		&times;
        	</span>
        </button>
        <h4 class="modal-title" id="myModalLabel">댓글 수정</h4>
      </div>
      <div class="modal-body">
      	<div>
      		작성자 : <input type="text" name="replyer" id="replyer" value="{{replyer}}" class="form-control" oninput="checkEdit()" {{#if gIsEdit}}readonly{{/if}} />
      	</div>
      	<div>
      		내용 : <textarea name="replytext" id="replytext" cols="30" rows="3" class="form-control" oninput="checkEdit()">{{replytext}}</textarea>
      	</div>	
      </div>
      <div class="modal-footer">
        <button onclick="save()" id="btnModReply" class="btn">{{#if gIsEdit}}수정{{else}}등록{{/if}}</button>
        {{#if gIsEdit}}
			<button onclick="removeReply()" id="btnDelReply" class="btn">삭제</button>
		{{/if}}
		<button onclick="closeMod()" id="btnCloseReply" class="btn">닫기</button>
      </div>
    </div>
  </div>
</script>

<script src="/resources/reply.js"></script>
<c:if test="${true eq isTest}"> <!-- QQQ -->
	<script src="/resources/qtest/replytest.js"></script>
</c:if> <!-- QQQ -->

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
	showAttaches(${boardVO.bno});
</script>
<%@include file="../include/footer.jsp" %>