QUnit.test("hello test", (assert)=>{
	assert.ok(1 == '1',"pass OK");
	
	assert.strictEqual(1 == '1', "pass Strick OK");
	
	let arr1 = [1, 2, 3],
		arr2 = [1, 2, 3],
		arr3 = [1, 3, 2],
		arr4 = [3, 4, 5];
	
	assert.equal(arr1, arr2, "pass Equal OK");
	assert.strictEqual(arr1, arr2, "pass Strick Equal OK");
	assert.deepEqual(arr1, arr2, "pass Deep Equal OK");
	
	assert.deepEqual(arr3, arr2, "pass Deep Equal OK");
});