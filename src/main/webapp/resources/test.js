const BNO = 6;

let workingReplyText ="",
	$workingReply = null,
	workingRno = 0;

function registerReply(){
	const REGIST_URL = "/replies";
	
	let jsonData = getValidData($('#newReplyWriter'), $('#newReplyText'));
	if(!jsonData){
		return;
	}
	
	jsonData.bno = BNO;
	
	sendAjax(REGIST_URL, (isSuccess, res) => {
		if(isSuccess){
			alert("등록이 완료 되었습니다.");
			listPage();
		}else{
			console.debug("Error on registerReply>>",res);
		}
	} , 'POST', jsonData);
}

function editReply(){
	let editedReplyText = $('#replycontext').val();
	
	let jsonData = { replytext: editedReplyText };
	sendAjax("/replies/"+workingRno, (isSuccess, res) =>{
		if(isSuccess){
			alert(workingRno+"번 댓글이 수정되었습니다.");
			$workingReply.find('span').text(editedReplyText);
			closeMod();
		}else{
			console.debug("Error on updateReply>>", res);
		}
	},'PUT',jsonData);
}

function removeReply(){
	if(!confirm("Are u sure??")) return;
	
	sendAjax("/replies/"+workingRno, (isSuccess, res) => {
		if(isSuccess){
			alert(workingRno+"번 댓글이 삭제완료되었습니다.");
			listPage();
			closeMod();
		} else{
			console.debug("Error on removeReply>>",res);
		}
	}, 'DELETE');
}

function closeMod(){
	let $modDiv = $("#modDiv");
	workingRno = 0;
	$('#replytext').text('');
	$modDiv.hide('slow');
	$('#btnModReply').hide();
	
}

function replyContextChange(){
	if($('#replycontext').val() !== workingReplyText){
		$('#btnModReply').show();
	}else{
		$('#btnModReply').hide();
	}
}

function listPage(page){
	page = page || 1;
	listUrl = "/replies/all/" + BNO + "/" + page;
	
	sendAjax(listUrl, (isSuccess, res)=>{
		if(isSuccess){
			let data = res.list,
				pageMaker = res.pageMaker;
			let str = ""; //바뀔 수 있음
			//$(data).each((a,b) => {console.log(a,b)});
			data.forEach(
					(d) => {
						//str += '<li data-rno="' + d.rno + '" class="replyLi">' + d.replytext + '<button>수정</button>' + '</li>';
						str += `<li data-rno= "${d.rno}" class= "replyLi">
							<span>${d.replytext}</span>
							<button onclick=modClicked(this) class="point">수정</button>
							</li>`;
					}
			);
			$('#replies').html(str);
			printPage(pageMaker);
		}
		
	});
}



function printPage(pageMaker){
	let str = "";
		tempPage = 0;
	if(pageMaker.prev){
		tempPage = pageMaker.startPage - 1;
		str = `<li><a href="#" onclick="listPage(tempPage)" data-page="${tmpPage}">&lt;&lt;</a></li>`;
	}
	//현재 페이지
	let currentPage = pageMaker.cri.page;
	
	for(let i = pageMaker.startPage; i <= pageMaker.endPage; i++){
		str += `<li><a href="#" onclick="listPage(${i})" class="${currentPage === i ? "active" : ""}" data-page="${i}">${i}</a></li>`;
	}
	
	if(pageMaker.next){
		tempPage = pageMaker.nextPage +1;
		str += `<li><a href="#" onclick="listPage(tempPage)" data-page="${tempPage}">&gt;&gt;</a></li>`;
	}
	
	$('ul#pagination').html(str);
	
}
function getValidData($replyer, $replytext){
	let errorFocus = null,
		replyer = $replyer.val(),
		replytext = $replytext.val(),
		errorMsg = "";

	if(!replyer){
		errorMsg = "작성자를 입력하세요.";
		$errorFocus = $replyer;
	} else if(!replytext){
		errorMsg = "내용을 입력하세요";
		$errorFocus = $replytext;
	}
	
	if(errorMsg){
		alert(errorMsg);
		$errorFocus.focus();
		return;
	}
	
	return {replyer: replyer, replytext: replytext};
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

function modClicked(btn){
	let $btn = $(btn),
	$reply = $btn.parent(),
	rno = $reply.data('rno');
	replytext = truncSpace($reply.find('span').text());
	$('#replycontext').val(replytext);
	$('#modDiv').show('slow');
	workingRno = rno;
	workingReplyText = replytext;
	$workingReply = $reply;
}

function showJson(){
	let result = [];
	$('#replies li').each ( (idx, li) => {
		let $li = $(li),
			rno = $li.data('rno')
			replyer = $li.data('replyer')
			replytext = truncSpace($li.text()); //정규식 /g를 안 붙이면 \n 만나는 첫번째 것만 바꿈
		result.push({
			rno: rno,
			replyer: replyer,
			replytext: replytext
	    })
	})
	result = JSON.stringify(result, null, '  ');
	console.log(result);
}



function movCenterModDiv(){
	$modDiv = $('#modDiv');
	$modDiv.css({'margin-left':$modDiv.width()/2*(-1)});
	$modDiv.css({'margin-top':$modDiv.height()/2*(-1)});
}


var truncSpace = function(str){
	if(!str){
		return "";
	}
	return str.replace(/[\n\r\t]/g,'').trim();
};
