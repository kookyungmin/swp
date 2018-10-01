<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
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
					<a href="{{getLink}}" class="mailbox-attachment-name">{{fileName}}</a>
					{{#if gIsEditing}}
						<a href="javascript:;" onclick="deleteFile('{{fullName}}')" class="btn btn-default btn-xs pull-right delbtn">
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