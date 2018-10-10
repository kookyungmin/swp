<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
	
<script src="/resources/upload.js"></script>
<ul class="mailbox-attachments clearfix uploadedList">
	<script id="template" type="text/x-handlebars-template" class="well">
		{{#each upFiles}} 
			<li id={{fileId}}>
				<input type="hidden" name="files" value="{{fullName}}" />
				<div class="mailbox-attachment-info">
					<span class="mailbox-attachments-icon has-img">
						<img src="{{imgsrc}}" alt="Attachement" />
					</span>
				</div>
				<div class="mailbox-attachment-info">
					<a href="javascript:;" onclick="showOriginal('{{getLink}}')" class="mailbox-attachment-name">{{fileName}}</a>
					{{#if gIsEditing}}
						<a href="javascript:;" onclick="deleteFile('{{fullName}}', '${boardVO.bno}')" class="btn btn-default btn-xs pull-right delbtn">
							<i class="fa fa-fw fa-remove"></i>
						</a>
					{{/if}}					
				</div>
			</li>
		{{else}}
			<li>첨부파일이 없습니다.</li>
		{{/each}}
	</script>
</ul>

<div id="original-image-modal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-lg" role="document">
		<img src="" alt="" />
	</div>
</div>

<script>
	function showOriginal(link){
		let $originalImageModal = $('#original-image-modal');
		
		if(checkImageType(link)){
			$originalImageModal.find('img').attr('src', link );
			$originalImageModal.modal('show');
		}else{
			window.location.href = link;
		}
	}



	function showAttaches(bno){
		sendAjax("/board/getAttach/" + bno , (isSuccess, res) => {
			if(isSuccess){
				res.forEach( rj => {
					console.debug(rj);
					let jsonData = getFileInfo(rj);
					gUpFiles.push(jsonData);
				})
				renderHds('template', {upFiles: gUpFiles});
			}else{
				console.debug("Error on getAttach");
			}	
		})
	}
</script>
