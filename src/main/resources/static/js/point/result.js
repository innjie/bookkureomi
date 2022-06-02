$(document).ready(function(){
	createRecharge();
});

//결과
function createRecharge(){
	var rcPoint = ${info.amount.total};	
	
	$.ajax({
		url: "/book/point/create", 
		method: 'POST',
		dataType: "json",
		data: {
			rcPoint:rcPoint
			, rcMethod:"kakao"
		}
	}).done(function( data ) {
		alert(data.result);
	})
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}