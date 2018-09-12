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
	$tmpl.replaceWith(`<${tag} id="${tid}" class="${cssClass}">` + html + `</${tag}>`)
};

Handlebars.registerHelper('eq', function(a, b){
	return a == b;
})

moment.locale('ko');
Handlebars.registerHelper('fromNow', function(dt){
	return moment(dt).fromNow();
})

Handlebars.registerHelper('fullTime', function(dt){
	return moment(dt).format('llll');
})