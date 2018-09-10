//type이 text/x-handlebars-template인 script들
const Templates = {};
let $scripts = $('script[type="text/x-handlebars-template"]');	

//모두 컴파일해서 Template으로 만든다.
$scripts.each ((idx, h) =>{
	$h = $(h);
	let tid = $h.attr('id');
	Templates[tid] = Handlebars.compile($h.html());
});

//Template 에 Json 데이터를 넣어서 html로 바꿔주는 함수
let renderHds = (tid, jsonData, tag) => {
	tag = tag || 'div';
	let $tmpl = $('#'+tid);
	let html = Templates[tid](jsonData);
	let cssClass = $tmpl.attr('class') || "";
	$tmpl.replaceWith(`<${tag} class="${cssClass}">` + html + `</${tag}>`)
};