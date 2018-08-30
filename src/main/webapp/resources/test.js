function registerReply(){
	const BNO = 6;
	const REGIST_URL = "/replies";
	let $replyer = $('#newReplyWriter'),
		$replytext = $('#newReplyText'),
		$errorFocus = null;
	
	let replyer = $replyer.val(),
		replytext = $replytext.val(),
		errorMsg = "";
	
	if(!replyer){
		errorMsg = "작성자를 입력하세요.";
		$errorFocus = $replyer;
	}else if(!replytext){
		errorMsg = "내용을 입력하세요";
		$errorFocus = $replytext;
	}
	
	if(errorMsg){
		alert(errorMsg);
		$errorFocus.focus();
		return;
	}
	
	let jsonData = {
			bno      : BNO,
			replyer  : replyer,
			replytext: replytext
	};
	let sender = $.ajax({
		method: 'POST',
		url: REGIST_URL,
		contentType: "application/json",
		data: JSON.stringify(jsonData),
	})
	
	sender.always((responseText,statusText,ajaxResult) => {
		if(responseText === 'success'){
			alert("등록 되었습니다.");
		}else{
			alert("오류가 발생하였습니다!");
		}
	})
}

/*function listAll(){
var bno = 6,
listUrl = "/replies/all/" + bno;
$.getJSON(listUrl, function(data, b, c){ 
	console.log(">> data=", data, ", b=", b, ", c=", c);
	c.always(function(){
		console.log(c.status);
	})
});	
}
*/

function listAll(){
	const bno = 6, //상수
	listUrl = "/replies/all/" + bno;
	$.getJSON(listUrl, (data, b, c) => { 
		console.log(">> data=", data, ", b=", b, ", c=", c);
		//$(data).each((a,b) => {console.log(a,b)});
		
		let str = ""; //바뀔 수 있음
		data.forEach(
			(d) => {
				str += '<li data-rno="' + d.rno + '" class="replyLi">' + d.replytext + '</li>';
		    }
		);
		$('#replies').html(str);
	});	
}

//-------------------------------------------
$(function(){
	//$('#h2-title').on('click',listAll);
	listAll();
	$('#btnReplyAdd').click(function(){
		registerReply();
	})
	
});