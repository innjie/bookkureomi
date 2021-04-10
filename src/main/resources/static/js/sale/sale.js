$(document).ready(function(){
	listSaleBook();
});

function listSaleBook(){
	$.ajax({
		url: "/book/sale/saleList", 
		method: 'GET',
	    dataType: "json",
		data: {
		}
	}).done(function( data ) {
		var result = "<ul>";
		var sale;
		
		for(var i=0; i<data.saleList.length; i++){
			sale = "<li><h3>" + data.saleList[i].saleNo + "</h3></li>"
					+"<li>" + data.saleList[i].publisher + "</li>"
					+"<li>" + data.saleList[i].title + "</li>"
					+"<li>" + data.saleList[i].costPrice + "</li>"
					+"<li>" + data.saleList[i].salePrice + "</li>"
					+"<li>" + data.saleList[i].info + "</li>"
				//	+"<li>" + data.saleList[i].user.name + "</li>"
					+"<li>" + data.saleList[i].regiDate + "</li>"
					+"<li>" + data.saleList[i].genre.genreType + "</li>"
					+"<li>" + data.saleList[i].state + "</li>";
			result += sale;
		}
		result += "</ul>";
		
		$('#result').append(result);
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}

function findSaleBook(){
	var title = '';
	var genre = '';
	var type = $("#findType").val();
	
	if(type == "title"){
		title = $("#findText").val();
	}else if(type == "genre"){
		genre = $("#findText").val();
	}
		
	$.ajax({
		url: "/book/sale/findSale", 
		method: 'GET',
	    dataType: "json",
		data: {
			title : title,
			genre : genre
		}
	}).done(function( data ) {
		$('#result')[0].innerHTML = '';
		
		var result = "<ul>";
		var sale;
		
		for(var i=0; i<data.saleList.length; i++){
			sale = "<li>" + data.saleList[i].saleNo + "</li>"
					+"<li>" + data.saleList[i].publisher + "</li>"
					+"<li>" + data.saleList[i].title + "</li>"
					+"<li>" + data.saleList[i].costPrice + "</li>"
					+"<li>" + data.saleList[i].salePrice + "</li>"
					+"<li>" + data.saleList[i].info + "</li>"
				//	+"<li>" + data.saleList[i].user.name + "</li>"
					+"<li>" + data.saleList[i].regiDate + "</li>"
					+"<li>" + data.saleList[i].genre.genreType + "</li>"
					+"<li>" + data.saleList[i].state + "</li>";
			result += sale;
		}
		result += "</ul>";
		
		$('#result').append(result);
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}