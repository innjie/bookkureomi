$(document).ready(function(){
	listSaleBook();
});

//판매 중고 서적 나열
function listSaleBook(){
	$.ajax({
		url: "/book/sale/saleList", 
		method: 'GET',
	    dataType: "json",
		data: {
		}
	}).done(function( data ) {
		$('#result')[0].innerHTML = '';
		
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
						+ "<img src=\"" + data.saleList[i].image + "\" class=\"img-fit\"/>"
						+ "</li>";
			
			//info
			result += "<li class=\"table-list-content\"><ul class=\"table-list-content-style\">"
						+ "<li class=\"table-list-content-list-style\"><strong>" + data.saleList[i].title + "</strong></li>"
						+ "<li class=\"table-list-content-list-style\">" + data.saleList[i].author + " / " + data.saleList[i].publisher + "</li>"
						+ "<li class=\"table-list-content-list-style\"> \\" + data.saleList[i].costPrice + " -> \\" + data.saleList[i].salePrice + "</li>"
						+ "<li class=\"table-list-content-list-style\">" + data.saleList[i].state + "</li>"
						+ "<li class=\"table-list-content-btn-style\"><button type=\"button\" id=\"btnSale"+ data.saleList[i].saleNo +"\" class=\"view-btn\" onClick=\"getSale("+ data.saleList[i].saleNo +")\">상세보기</button><li>"
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
			
			if (i==0 && data.saleList.length==1){
				result += "<td></td><td></td>";
			} else if (i==1 && data.saleList.length==2) {
				result += "<td></td>";
			}
			
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

//판매 중고 서적 찾기
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
		var result;
		
		if(data.saleList.length > 0) {
			result = 
				//	"<table class=\"table-list\">"; //style=\"width: 100%;\"
					"<table class=\"table-list\" style=\"width: 100%;\">"
					+ "<colgroup><col width=\"33.333333%\" /><col width=\"33.333333%\" /><col width=\"33.333333%\" /></colgroup><tbody>";
							
			for(var i=0; i<data.saleList.length; i++){
				if(i%3 == 0 && i != 0){
					result += "<tr>";
				}
				
				//result += "<td><ul class=\"list-style\">";
				result += "<td><ul class=\"list-style\" style=\"background-color: #ffffe3;border-color: #ffffe3;border-radius: 1rem; list-style:none; height: 250px;padding-inline-start: 0px; margin: 10px 10px 10px 10px;\">";
				
				//image
				result += "<li class=\"table-list-image\">"
							+ "<img src=\"" + data.saleList[i].image + "\" class=\"img-fit\"/>"
							+ "</li>";
				
				//info
				result += "<li class=\"table-list-content\"><ul class=\"table-list-content-style\">"
							+ "<li class=\"table-list-content-list-style\"><strong>" + data.saleList[i].title + "</strong></li>"
							+ "<li class=\"table-list-content-list-style\">" + data.saleList[i].author + " / " + data.saleList[i].publisher + "</li>"
							+ "<li class=\"table-list-content-list-style\"> \\" + data.saleList[i].costPrice + " -> \\" + data.saleList[i].salePrice + "</li>"
							+ "<li class=\"table-list-content-list-style\">" + data.saleList[i].state + "</li>"
							+ "<li class=\"table-list-content-btn-style\"><button type=\"button\" id=\"btnSale"+ data.saleList[i].saleNo +"\" class=\"view-btn\" onClick=\"getSale("+ data.saleList[i].saleNo +")\">상세보기</button><li>"
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
				
				if (i==0 && data.saleList.length==1){
					result += "<td></td><td></td>";
				} else if (i==1 && data.saleList.length==2) {
					result += "<td></td>";
				}
				
				if(i%3==0  && i != 0){
					result += "</tr>";
				}
			}
			result += "</tbody></table>";
		} else {
			result = "<p class=\"find-nothing\">검색결과가 없습니다.</p>";
		}
			
		$('#result').append(result);
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}

//판매 중고서적 상세보기
function getSale(saleNo) {	
	$.ajax({
		url: "/book/sale/getSale", 
		method: 'GET',
	    dataType: "json",
		data: {
			saleNo : saleNo
		}
	}).done(function( data ) {
		$("#pop-sale-view").css("display", "block");
		
		$("#viewImage").attr("src", data.sale.image);
		
		$("#viewSaleNo").val(data.sale.saleNo);
		//$("#viewUser").val(data.sale.user.id);
		$("#viewTitle").val(data.sale.title);
		$("#viewPublisher").val(data.sale.publisher);
		$("#viewAuthor").val(data.sale.author);
		$("#viewCostPrice").val(data.sale.costPrice);
		$("#viewSalePrice").val(data.sale.salePrice);
		$("#viewRegiDate").val(data.sale.regiDate);
		$("#viewGenre").val(data.sale.genre.genreType);
		$("#viewState").val(data.sale.state);
		$("#viewInfo").val(data.sale.info);
		
		//구매자만 readonly로 수정필요
    	//$('#viewSalePrice').prop('readonly', true);
    	//$('#viewInfo').prop('readonly', true);


    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}

function setDefault() {
	$("#insertTitle").val();
	$("#insertPublisher").val();
	$("#insertAuthor").val();
	$("#insertCostPrice").val();
	$("#insertSalePrice").val();
	$("#insertRegiDate").val();
	$("#insertGenre").val();
	$("#insertState").val();
	$("#insertInfo").val();
}

function closeView() {
	$("#pop-sale-view").css("display", "none");
	$("#pop-sale-insert").css("display", "none");
}


//중고 서적 판매
function saleBookForm(){
	setDefault();
	$("#pop-sale-insert").css("display", "block");
}
function saleBook() {
	var publisher = $("#insertPublisher").val();
	var title = $("#insertTitle").val();
	var costPrice = $("#insertCostPrice").val();
	var userNo = 1;
	var author = $("#insertAuthor").val();
	var genreType = $("#insertGenreType").val();
	//var image = ''; //폼처리?
	var salePrice = $("#insertSalePrice").val();
	var info = $("#insertInfo").val();
	
	$.ajax({
		url: "/book/sale/saleBook", 
		method: 'POST',
	    dataType: "json",
		data: {
			title : title,
			publisher : publisher,
			salePrice : salePrice,
			info : info, 
			costPrice : costPrice,
			//image : image,
			userNo : userNo,
			author : author,
			genreType : genreType
		}
	}).done(function( data ) {
		if(data.result == success){
			window.alert("중고 책을 등록하였습니다.");
	        
			getSale();
		}
	})
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}

//수정
function updateBook() {
	var saleNo = $("#viewSaleNo").val();
	var image = $("#viewImage").attr("src"); //폼처리?
	var salePrice = $("#viewSalePrice").val();
	var info = $("#viewInfo").val();
	
	$.ajax({
		url: "/book/sale/updateSale", 
		method: 'PUT',
	    dataType: "json",
		data: {
			saleNo : saleNo,
			image : image,
			salePrice : salePrice,
			info : info
		}
	}).done(function( data ) {
		if(data.result == success){
			window.alert("중고 책 판매를 수정하였습니다.");
	        
			//$("#pop-sale-view").css("display", "none");
		}
	})
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}

//구매
function buyBook() {
	
}
