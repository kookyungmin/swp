<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>Document</title>
<link rel="stylesheet" href="/resources/test.css" />
</head>
<body>
	<h2 id = "h2-title" class="point">Ajax Test Page</h2>
	<ul id="replies">	
	</ul>
	<ul id="pagination">
		
	</ul>
	<div>
		<div>
			작성자 : <input type="text" name="replyer" id="newReplyWriter" />
		</div>
		<div>
			내용 : <textarea name="replytext" id="newReplyText" cols="30" rows="3"></textarea>
		</div>
		<button id="btnReplyAdd" class="btn btn-primary">등록</button>
		<button id="btnShowJson" class="btn btn-warning">댓글전체JSON</button>
	</div>
	
	<div id="modDiv">
		<div class="modal-title">
			<div>
				<input id="replycontext" rows="3" placeholder="Enter" oninput="replyContextChange()"></input>	
			</div>
		</div>
		<div>
			<button onclick="editReply()" id="btnModReply">수정</button>
			<button onclick="removeReply()" id="btnDelReply">삭제</button>
			<button onclick="closeMod()" id="btnCloseReply">닫기</button>
		</div>
	</div>
	
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script src="/resources/test.js"></script>
<script>
	$(function(){
		//$('#h2-title').on('click',listAll);
		listPage(1);
		$('#btnReplyAdd').click(function(){
			registerReply();
		})
		
		$('#btnShowJson').click(function(){
			showJson();
		})
		
		movCenterModDiv();
	});
</script>
</body>
</html>
