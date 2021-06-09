/**
 * 
 */
 "use strict";
 $(function(){
	$("#get-address-button").on("click", function(){
		$.ajax({
			url: "http://zipcoda.net/api",
			dataType: "jsonp",
			data: {
				zipcode: $("#zipCode").val()
			},
			async: true
		}).done(function(data){
			console.dir(JSON.stringify(data));
			$("#address").val(data.items[0].address); // 住所欄に住所をセット
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("正しい結果を得られませんでした。");
			console.log("XMLHttpRequest：" + XMLHttpRequest.status);
			console.log("textStatus：" + textStatus);
			console.log("errorThrown：" + errorThrown.message);
		});
	});
	
	$("#upload-button").on("click", function(){
		// ここに画像アップロードの処理を書く
		let uploadUrl = "http://localhost:8080/employee/upload";
		let formData = new FormData($("#insertEmployeeForm").get(0));
		$.ajax({
			url: uploadUrl,
			type: "post",
			data: formData,
			processData: false,
			contentType: false,
			dataType: 'json',
			async: true
		}).done(function(data){
			alert(data.uploadFileMessage);
			console.dir(JSON.stringify(data));
			$("#image").val(data.uploadFileName);
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("正しい結果を得られませんでした。");
			console.log("XMLHttpRequest：" + XMLHttpRequest.status);
			console.log("textStatus：" + textStatus);
			console.log("errorThrown：" + errorThrown.message);
		});
	});
});