function find1(){
	var t1id = $("#t1id").val();

	window.location = "/test1?id="+t1id;
}

//rest
function find2(){
	var t2id = $("#t2id").val();
	
	$.ajax({
		url: "/test2", 
		method: 'GET',
	    dataType: "json",
		data: {
			id : t2id
		}
	}).done(function( data ) {
		//$('#t2phone').var(data.test.phone);
		$('#t2phone').val(data.phone);
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}