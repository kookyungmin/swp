const URL = "/replies/"
let gBno = 0,
	gPage = 0, 
	gIsEdit = false, //댓글 수정 중인지 아닌 지 확인
	gRno = 0,
	gReplyText = "";


function replyListPage(page, bno){
	gBno = bno || gBno;
	gPage = page || gPage || 1;
	listUrl = URL + "all/" + gBno + "/" + gPage;
	sendAjax(listUrl, (isSuccess, res)=>{
		console.debug(res);
		if(isSuccess){
			res.pageData = makePageData(res.pageMaker);
			res.currentPage = gPage;
			renderHds("replies", res);
		}
		
	});
}

function makePageData(pageMaker){
	let pageData = {
			prevPage : 0,
			nextPage : 0,
			pages: [],
	};
	
	if(pageMaker.prev){
		pageData.prevPage = pageMaker.startPage - 1;
	}
	
	for(let i = pageMaker.startPage; i <= pageMaker.endPage; i++){
		pageData.pages.push(i);
	}
	
	if(pageMaker.next){
		pageData.nextPage = pageMaker.endPage +1;
	}
	return pageData;
	
}


function editReply(rno, replyer, replytext){
	event.preventDefault(); //a 태그의 화면전환을 막음
	gRno = rno;
	gIsEdit = !!rno; //rno 값이 들어 있으면 gIsEdit 를 true
	gReplyText = replytext;
	renderHds("myModal", {
			gIsEdit : gIsEdit,
			replyer : replyer,
			replytext : replytext
		}
	);
	$('#btnModReply').hide();
	$('#myModal').modal();
}

function save(){
	let jsonData = getValidData($('#replyer'), $('#replytext'));
	
	if(!gIsEdit)
		jsonData.bno = gBno;
	let url = gIsEdit ? URL + gRno :  URL,
		method = gIsEdit ? 'PATCH' : 'POST';
	
	sendAjax(url, (isSuccess, res) =>{
		if(isSuccess){
			let resultMsg = gIsEdit ? "댓글이 수정되었습니다." : "댓글이 등록되었습니다.";
			alert(resultMsg);
			replyListPage(gIsEdit ? gPage : 1);
			closeMod();
		}else{
			console.debug("Error on updateReply>>", res);
		}
	},method ,jsonData);
}

function removeReply(){
	if(!confirm("Are u sure??")) return;
	
	sendAjax(URL+gRno, (isSuccess, res) => {
		if(isSuccess){
			alert(gRno+"번 댓글이 삭제완료되었습니다.");
			replyListPage();
			closeMod();
		} else{
			console.debug("Error on removeReply>>",res);
		}
	}, 'DELETE');
}

function closeMod(){
	gRno = 0;
	gReplyText = null;
	$('#myModal').modal('hide');
	
}

function sendAjax(url, fn,  method, jsonData){
	let options = {
			method: method || 'GET',
			url: url,
			contentType: "application/json"
	};
	//jsonData가 있을 때만 data : JSON.stringify(jsonData) 추가
	if(jsonData){
		options.data = JSON.stringify(jsonData);
	}
	$.ajax(options).always((responseText, statusText, ajaxResult) =>{
		let isSuccess = statusText === 'success'; //ajax 호출 성공 여부
		fn(isSuccess,responseText);
		if(!isSuccess){
			alert("오류가 발생하였습니다. (errorMessage:" + responseText + ")");
		}
	})
}

function getValidData($replyer, $replytext){
	replyer = $replyer.val(),
	replytext = $replytext.val();
	
	return {replyer: replyer, replytext: replytext};
}


function checkEdit(){
	replyer = $('#replyer').val(),
	replytext = $('#replytext').val();
	if(!gIsEdit && (!replyer || !replytext)){ //댓글 등록 중이고 작성자가 입력 안되어 있으면
		$('#btnModReply').hide();
	}else if(gIsEdit && gReplyText === replytext){
		$('#btnModReply').hide();
	}else{
		$('#btnModReply').show();
	}
}

//추가 
function readReply(rno){
	let url = URL + rno;
	sendAjax(url, (isSuccess,res) => {
		if(isSuccess){
			console.debug("read success :",res);
		} else{
			console.debug("Error on removeReply>>",res);
		}
	}, 'GET');
}


