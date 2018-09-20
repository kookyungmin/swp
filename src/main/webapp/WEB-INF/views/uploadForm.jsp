<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>uploadForm</title>
</head>
<body>
<!--  
	<form action="uploadForm" id="form1" method="post" enctype="multipart/form-data">
		<input type="file", name="file" />
		<input type="submit" />		
	</form>
	<span>SavedFileName: ${ savedFileName }</span>
	
	<hr />
	 	//iframe을 이용한 화면전환 없이 내용 갱신하게 하는것
		//form 태그의 전송은 화면에 포함된 iframe으로 전송(iframe은 hidden 시키면 화면에 변화없음)
		//결과 페이지에서 다시 바깥쪽의 JavaScript 함수 호출
		
	<form action="uploadForm" id="form2" method="post" target="ifr" enctype="multipart/form-data">
		<input type="hidden" name="type" value="ifr" />
		<input type="file", name="file" />
		<input type="submit" value="iframe으로 submit"/>		
	</form>
	IFR-SavedFileName: <span id="upfile"></span>
	<br />
	<iframe width="500" height="600" name="ifr"></iframe>

	<hr />
-->
	
	<!-- 드래그 파일 올리기 -->
	<div class="fileDrop">Drop Hear!</div>
	<div class="uploadListed"></div>
	
	<!-- Ajax를 이용한 방법 (jQuery.form.min.js) -->
	
	<form action="uploadAjax" id="form3" method="post" enctype="multipart/form-data">
		<input type="hidden" name="type" value="ajax" />
		<input type="file", name="file" id="ajax-file" />
		<input type="submit" value="Ajax으로 submit"/>		
	</form>
	<div id="percent"></div>
	<div id="status">ready</div>
	





<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script src="/resources/plugins/jQuery/jQuery.form.min.js"></script>

<!-- iframe을 이용한 방법 -->
<script>
//어디서든 부를 수 있게 window 객체에 함수 등록(frame들을 관장할 수 있다. browser tab마다 다름)
window.setUploadedFile = (filename) => {
	document.getElementById('upfile').innerHTML = filename;
	document.getElementById('form2').reset();
}; 
</script>

<c:if test="${type eq 'ifr'}">
	<script>
		parent.setUploadedFile('${ savedFileName }');
	</script>
</c:if>
<!-- iframe을 이용한 방법 끝-->


<!-- Ajax를 이용한 방법 -->
<script>
//드래그 해서 올리기

const $fileDrop = $('div.fileDrop'),
	  $uploadedList = $('div.uploadedList');

$fileDrop.on('dragover dragenter', (evt) =>{
	evt.preventDefault();
	$fileDrop.css("border", "1px dotted green");
});

$fileDrop.on('drop', (evt) =>{
	evt.preventDefault();
	let files = evt.originalEvent.dataTransfer.files;
	console.debug("drop>>",files);
	$fileDrop.css("border", "none");
	$fileDrop.html(files[0].names);
	$('#ajax-file').prop("files", evt.originalEvent.dataTransfer.files);
	$('#form3').submit();
});

$fileDrop.on('dragleave', (evt) =>{
	evt.preventDefault();
	$fileDrop.css("border", "none");
});

// Ajax를 이용한 방법 
const $percent = $('#percent'),
	  $status = $('#status');
$('#form3').ajaxForm({
	beforeSend : function(){
		$status.empty();
		$percent.html('0%');
	},
	uploadProgress: function(event, position, total, percentComplete){
		$status.html('uploading...');
		$percent.html(percentComplete + '%');
	},
	complete: function(xhr) {
        $status.html('Ajax-SavedFileName:' + xhr.responseText);
    }
});

</script>
</body>
</html>