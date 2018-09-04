function registerReply(){
	const BNO = 6;
	const REGIST_URL = "/replies";
	
	let jsonData = getValidData($('#newReplyWriter'),$('#newReplyText'));
	jsonData.bno = BNO;
	
	sendAjax(REGIST_URL, (isSuccess, responseText) => {
		if(isSuccess){
			alert("등록이 완료 되었습니다.");
			listAll();
		}else{
			if(!isSuccess){
				alert("오류가 발생하였습니다. (errorMessage:" + res + ")");
			}
		}
	} , jsonData, 'POST');
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

function sendAjax(url, fn, jsonData, method){
	let options = {
						method: method,
						url: url,
						contentType: "application/json"
				   };
	//jsonData가 있을 때만 data : JSON.stringify(jsonData) 추가
	if(jsonData){
		options.data = JSON.stringify(jsonData);
		console.log(options);
	}
	
	$.ajax(options).always((responseText, statusText, res) =>{
		let isSuccess = statusText === 'success'; //ajax 호출 성공 여부
		fn(isSuccess, responseText);
		if(!isSuccess){
			alert("오류가 발생하였습니다. (errorMessage:" + res + ")");
		}
	})
}


function listAll(){
	const bno = 6; 
	const page = 1;
	listUrl = "/replies/all/" + bno + "/" + page;
	$.getJSON(listUrl, (data, b, c) => { 
		console.log(">> data=", data, ", b=", b, ", c=", c);
		let str = ""; //바뀔 수 있음
		//$(data).each((a,b) => {console.log(a,b)});
		data.list.forEach(
			(d) => {
				//str += '<li data-rno="' + d.rno + '" class="replyLi">' + d.replytext + '<button>수정</button>' + '</li>';
				str += `<li data-rno= "${d.rno}" class= "replyLi">
							${d.replytext}
							<button onclick=modClicked(this) class="point">수정</button>
						</li>`;
		    }
		);
		$('#replies').html(str);
	});
}

function movCenterModDiv(){
	$modDiv = $('#modDiv');
	$modDiv.css({'margin-left':$modDiv.width()/2*(-1)});
	$modDiv.css({'margin-top':$modDiv.height()/2*(-1)});
}

function modClicked(btn){
	let $btn = $(btn),
		$reply = $btn.parent(),
		rno = $reply.data('rno');
	console.log(rno);
}


