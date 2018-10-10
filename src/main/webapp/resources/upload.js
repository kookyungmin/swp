const $fileDrop = $('div.fileDrop');
let gIsDirect = false;

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

let gUpFiles = [];

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
		console.debug("xhr>>>>", xhr);
		let resJson = xhr.responseJSON;
		console.debug(resJson);
		
		if (xhr.status !== 201){
			alert("Error on Upload!! (" + resJson + ")");
			return;
		}
	
		resJson.forEach( rj => {
			let jsonData = getFileInfo(rj);
			gUpFiles.push(jsonData);	
		});
		
		$status.html(gUpFiles.length + 'files uploaded...!');
		renderHds('template', {upFiles: gUpFiles});
    }
});

let gUri = window.location.pathname,
	gIsRegister = gUri.indexOf('/register') !== -1,
	gIsUpdate = gUri.indexOf('/update') !== -1,
	gIsEditing = gIsRegister || gIsUpdate;

function getFileInfo(fullName) {
	let fileName, imgsrc, getLink;
	let $isDirect = $('#isDirect'),
		isDirect = $isDirect && $isDirect.length && $isDirect.val() == "true";
	
	isDirect = isDirect ? true : gIsDirect;
	console.debug(isDirect);
	const uphost = window.location.protocol + "//" + window.location.hostname;
	
	if (checkImageType(fullName)){
		
		if(isDirect){
			imgsrc = uphost + "/uploads" + fullName;
		}else{
			imgsrc = "/displayFile?fileName=" + fullName;
		}
		fileLink = fullName.substring(14); // 원본파일명 (/2018/09/28/s_ 이후 파일명)
		let front = fullName.substring(0, 12), // /2018/09/28
			end = fullName.substring(14);
		getLink = "/displayFile?fileName=" + front + end; //원본파일 보기 URI
	} else {
		imgsrc = "/resources/dist/img/file_icon.png";
		fileLink = fullName.substring(12);
		getLink = "/displayFile?fileName=" + fullName;
	}
	
	if(isDirect){
		getLink += "&isDirect=true"
		console.debug(getLink);
	}
	// 실제 파일명
	fileName = fileLink.substring(fileLink.indexOf('_') + 1);
	let fileId = fileLink.substring(0, fileLink.indexOf('_'));
	
	return {
		fileName: fileName,
		imgsrc: imgsrc,
		getLink: getLink,
		fullName: fullName,
		fileId : fileId,
		gIsEditing : gIsEditing
	};
}

function deleteFile(fullName, bno) {
	let fileInfo = getFileInfo(fullName);
	
	if(!confirm("삭제된 파일은 다시 복구되지 않습니다. 그래도 삭제하시겠습니까??")) return;
	
	let url = "/deleteFile?fileName=" + fullName;
	if(bno){
		url += "&bno=" + bno;
	}
	sendAjax(url, (isSuccess, res) => {
		if(isSuccess){
			alert("삭제 성공");
			$('li#' + fileInfo.fileId).remove();
			
			let tmpIdx = -1;
			gUpFiles.forEach( (uf, idx)  => {
				if(uf.fullName === fullName){
					tmpIdx = idx;
				}
			})
			gUpFiles.splice(tmpIdx, 1);
			$status.html(gUpFiles.length + 'files uploaded...!');
		} else{
			console.debug("Error on deleteFile>>",res);
		}
	}, 'DELETE');
}

function checkImageType(fileName) {
	let pattern = /jpg$|png$|gif$/i; // ^시작 , $끝, i는 대소문자 구분 x
	return fileName.match(pattern);
}
