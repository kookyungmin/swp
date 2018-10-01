<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp" %>
<form role="form" method="post">
	<div class="box-body">
		<!-- page와 perPageNum  추가 -->
		<input type="hidden" name="page" value="${cri.page}" />
		<input type="hidden" name="perPageNum" value="${cri.perPageNum}" />
			
		<div class="form-group">
			<label for="title">Title</label>
			<input type="text" id="title" name="title" class="form-control" placeholder="Enter Title"/>		
		</div>
		<div class="form-group">
			<label for="content">Content</label>
			<textarea name="content" id="content" class="form-control" rows="3" placeholder="Enter"></textarea>		
		</div>
		<div class="form-group">
			<label for="writer">Writer</label>
			<input type="text" id="writer" name="writer" class="form-control" placeholder="Enter Writer"/>		
		</div>
		
		<!-- 파일 첨부 -->
		<div class="form-group">
			<label for="">File Drop Here!</label>
			<div class="fileDrop text-right">
			</div>
			<div id="percent">0%</div>
			<div id="status">ready</div>
		</div>
	</div>
	
	<div class="box-footer">
		<!--  파일 첨부 -->
		<ul class="mailbox-attachments clearfix uploadedList">
			<script id="template" type="text/x-handlebars-template" class="well">
			{{#each upFiles}} 
				<li>
					<div class="mailbox-attachment-info">
						<span class="mailbox-attachments-icon has-img">
							<img src="{{imgsrc}}" alt="Attachement" />
						</span>
					</div>
					<div class="mailbox-attachment-info">
						<a href="{{getlink}}" class="mailbox-attachment-name">{{filename}}</a>
						<a href="{{fullName}}" class="btn btn-default btn-xs pull-right delbtn">
							<i class="fa fa-fw fa-remove"></i>
						</a>					
					</div>
				</li>
			{{else}}
				<li>첨부파일이 없습니다.</li>
			{{/each}}
			</script>
		</ul>
		<button type="submit" class="btn btn-primary">Submit</button>
		<a href="/board/listPage${cri.makeQuery()}" class="btn btn-danger">Cancel</a>
	</div>
</form>

<form action="/uploadAjax" id="form_attach" method="post" enctype="multipart/form-data">
	<input type="hidden" name="type" value="ajax" />
	<input type="file" name="file" id="ajax-file" class="hidden"/>		
</form>


<script>
const $fileDrop = $('div.fileDrop');

$fileDrop.on('dragover dragenter', (evt) =>{
	evt.preventDefault();
	$fileDrop.css("border", "5px dotted green");
});

$fileDrop.on('dragleave', (evt) =>{
	evt.preventDefault();
	$fileDrop.css("border", "1px dotted grey");
});

$fileDrop.on('drop', (evt) => {
	evt.preventDefault();
	let files = evt.originalEvent.dataTransfer.files;
	console.debug("drop>>",files);
	$fileDrop.css("border", "1px dotted grey");
	$fileDrop.html(files[0].name);
	$('#ajax-file').prop("files", files);
	$('#form_attach').submit();
});

//Ajax를 이용한 방법 
const $percent = $('#percent'),
	  $status = $('#status');

let upFiles = [];

$('#form_attach').ajaxForm({
	beforeSend : function(){
		let f = $('#ajax-file').val();
		console.debug("beforeSend!!", f);
		$status.empty();
		$percent.html('0%');
	},
	
	uploadProgress: function(event, position, total, percentComplete){
		$status.html('uploading...');
		$percent.html(percentComplete + '%');
	},
	
	complete: function(xhr) {
		let jsonData = getFileInfo(xhr.responseText);
		upFiles.push(jsonData);
		$status.html(jsonData.fileName + 'uploaded...!');
		renderHds('template', {upFiles: upFiles});
    }
});

function getFileInfo(fullName) {
	let fileName, imgsrc, getLink;
	
	if (checkImageType(fullName)){
		imgsrc = "/displayFile?fileName=" + fullName;
		fileLink = fullName.substring(14); // 원본파일명 (/2018/09/28/s_ 이후 파일명)
		let front = fullName.substring(0, 12), // /2018/09/28
			end = fullName.substring(14);
		getLink = "/dispayFile?fileName=" + front + end; //원본파일 보기 URI
	} else {
		imgsrc = "/resources/dist/img/file_icon.png";
		fileLink = fullName.substring(12);
		getLink = "/displayFile?fileName" + fullName;
	}
	// 실제 파일명
	fileName = fileLink.substring(fileLink.indexOf('_') + 1);
	
	return {
		fileName: fileName,
		imgsrc: imgsrc,
		getLink: getLink,
		fullName: fullName
	};
}

function checkImageType(fileName) {
	let pattern = /jpg$|png$|gif$/i; // ^시작 , $끝, i는 대소문자 구분 x
	return fileName.match(pattern);
}

</script>

<%@include file="../include/footer.jsp" %>