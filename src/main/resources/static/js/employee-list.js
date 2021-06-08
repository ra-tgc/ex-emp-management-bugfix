/**
 * 
 */
 "use strict";
 
 $(function(){
	let nameList = $("#search-name").on("keyup", function(){
		return document.getElementById("nameList").value;
	});
	$("#search-name").autocomplete({
		source: nameList
	});
});