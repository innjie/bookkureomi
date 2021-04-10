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
		var result = 
		//	"<table class=\"table-list\">"; //style=\"width: 100%;\"
			"<table class=\"table-list\" style=\"width: 100%;\">"
			+ "<colgroup><col width=\"33.333333%\" /><col width=\"33.333333%\" /><col width=\"33.333333%\" /></colgroup><tbody>";
		var sale;
		
		for(var i=0; i<data.saleList.length; i++){
			if(i%3 == 0 && i != 0){
				result += "<tr>";
			}
			
			//result += "<td><ul class=\"list-style\">";
			result += "<td><ul class=\"list-style\" style=\"background-color: #ffffe3;border-color: #ffffe3;border-radius: 1rem; list-style:none; height: 250px;padding-inline-start: 0px; margin: 10px 10px 10px 10px;\">";
			
			//image
			result += "<li class=\"table-list-image\">"
						+ "<img src=\"/images" + data.saleList[i].image + "\" class=\"img-fit\"/>"
						+ "</li>";
			
			//info
			result += "<li class=\"table-list-content\"><ul class=\"table-list-content-style\">"
						+ "<li class=\"table-list-content-list-style\"><strong>" + data.saleList[i].title + "</strong></li>"
						+ "<li class=\"table-list-content-list-style\">" + data.saleList[i].author + " / " + data.saleList[i].publisher + "</li>"
						+ "<li class=\"table-list-content-list-style\"> \\" + data.saleList[i].costPrice + " -> \\" + data.saleList[i].salePrice + "</li>"
						+ "<li class=\"table-list-content-list-style\">" + data.saleList[i].state + "</li>"
						+ "</ul></li>";
			
/*			sale = "<li><h3>" + data.saleList[i].saleNo + "</h3></li>"
					+"<li><img src=\"/images" + data.saleList[i].image + "\" /></li>"
					+"<li>" + data.saleList[i].publisher + "</li>"
					+"<li>" + data.saleList[i].author + "</li>"
					+"<li>" + data.saleList[i].title + "</li>"
					+"<li>" + data.saleList[i].costPrice + "</li>"
					+"<li>" + data.saleList[i].salePrice + "</li>"
					+"<li>" + data.saleList[i].info + "</li>"
				//	+"<li>" + data.saleList[i].user.name + "</li>"
					+"<li>" + data.saleList[i].regiDate + "</li>"
					+"<li>" + data.saleList[i].genre.genreType + "</li>"
					+"<li>" + data.saleList[i].state + "</li>";
			result += sale;*/
			
			result += "</ul></td>";
			
			if(i%3==0  && i != 0){
				result += "</tr>";
			}
		}
		result += "</tbody></table>";
		
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
					+"<li><img src=\"/images" + data.saleList[i].image + "\" /></li>"
					+"<li>" + data.saleList[i].publisher + "</li>"
					+"<li>" + data.saleList[i].author + "</li>"
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