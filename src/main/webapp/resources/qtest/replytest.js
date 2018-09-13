const pageMaker = {
		"displayPageCnt":10,
		"startPage":1,
		"endPage":10,
		"prev":false,
		"next":true,
		"cri":{
			"page":1,
			"perPageNum":10,
			"searchType":null,
			"keyword":null,
			"pageStart":0
		},
		"totalCount":267
};

const gTestMakePageResultExpected = {
		"prevPage":0,
		"nextPage":11,
		"pages":[1,2,3,4,5,6,7,8,9,10],
};


QUnit.test("replyListPage test", (assert)=>{
		listUrl = URL + "all/" + 6 + "/" + 1;
		
		const done = assert.async();
		sendAjax(listUrl, (isSuccess, res)=>{
			assert.ok(isSuccess, "AJAX 성공!!");
			if(isSuccess){
				res.pageData = makePageData(res.pageMaker);
				assert.equal(res.list.length, 10, "Pass List Count 10!");
				assert.deepEqual(res.pageData, gTestMakePageResultExpected, "makePageData 통과");
			}
			done();
		});
});