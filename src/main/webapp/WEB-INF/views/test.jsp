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
	
	<div>
		<div>
			작성자 : <input type="text" name="replyer" id="newReplyWriter" />
		</div>
		<div>
			내용 : <textarea name="replytext" id="newReplyText" cols="30" rows="3"></textarea>
		</div>
		<button id="btnReplyAdd" class="btn btn-primary">등록</button>
		<button id="btnListAll" class="btn btn-warning">댓글전체JSON</button>
	</div>
	
	<div id="modDiv">
		<div class="modal-title">
			<div>
				<textarea id="replycontext" rows="3" placeholder="Enter"></textarea>	
			</div>
		</div>
		<div>
			<button id="btnModReply">수정</button>
			<button id="btnDelReply">삭제</button>
			<button id="btnCloseReply">닫기</button>
		</div>
	</div>
	
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script src="/resources/test.js?ver=3"></script>
<script>
	$(function(){
		//$('#h2-title').on('click',listAll);
		listAll();
		$('#btnReplyAdd').click(function(){
			registerReply();
		})
		$('#btnListAll').click(function(){
			let result = [];
			$('#replies li').each ( (idx, li) => {
				let $li = $(li),
					rno = $li.data('rno')
					replyer = $li.data('replyer')
					replytext = $li.text().replace(/[\n\r\t]/g,'').trim(); //정규식 /g를 안 붙이면 \n 만나는 첫번째 것만 바꿈
				result.push({
					rno: rno,
					replyer: replyer,
					replytext: replytext
			    })
			})
			result = JSON.stringify(result, null, '  ');
			console.log(result);
		})
		movCenterModDiv();
	});
</script>
</body>
</html>
