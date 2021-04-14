$(document).ready(function(){
	listSale();
});

//판매 중고 서적 나열
function listSale(){
	$.ajax({
		url: "/book/sale/list", 
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
			if(i%3 == 0){
				result += "</tr><tr>";
			} else if(i == 0){
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
						+ "<li class=\"table-list-content-btn-style\"><button type=\"button\" id=\"btnSale"+ data.saleList[i].saleNo +"\" class=\"view-btn\" onClick=\"detailSale("+ data.saleList[i].saleNo +")\">상세보기</button><li>"
						+ "</ul></li>";
			
			result += "</ul></td>";
			
			if (i==0 && data.saleList.length==1){
				result += "<td></td><td></td>";
			} else if (i==1 && data.saleList.length==2) {
				result += "<td></td>";
			}
			
			if(i == data.saleList.length-1){
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
function findSale(){
	var title = '';
	var genre = '';
	var type = $("#findType").val();
	
	if(type == "title"){
		title = $("#findText").val();
	}else if(type == "genre"){
		genre = $("#findText").val();
	}
		
	$.ajax({
		url: "/book/sale/find", 
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
			result = "<table class=\"table-list\">"
					+ "<colgroup><col width=\"33.333333%\" /><col width=\"33.333333%\" /><col width=\"33.333333%\" /></colgroup><tbody>";
							
			for(var i=0; i<data.saleList.length; i++){
				if(i%3 == 0){
					result += "</tr><tr>";
				} else if(i == 0){
					result += "<tr>";
				}
				
				result += "<td><ul class=\"list-style\">";
				
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
							+ "<li class=\"table-list-content-btn-style\"><button type=\"button\" id=\"btnSale"+ data.saleList[i].saleNo +"\" class=\"view-btn\" onClick=\"detailSale("+ data.saleList[i].saleNo +")\">상세보기</button><li>"
							+ "</ul></li>";
				
				result += "</ul></td>";
				
				if (i==0 && data.saleList.length==1){
					result += "<td></td><td></td>";
				} else if (i==1 && data.saleList.length==2) {
					result += "<td></td>";
				}
				
				if(i == data.saleList.length-1){
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
function detailSale(saleNo) {	
	closeCreatePopup();

	$.ajax({
		url: "/book/sale/detail", 
		method: 'GET',
	    dataType: "json",
		data: {
			saleNo : saleNo
		}
	}).done(function( data ) {
		$("#pop-sale-detail").css("display", "block");
		
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

function closeDetailPopup() {
	$("#pop-sale-detail").css("display", "none");
}

function closeCreatePopup() {
	$("#pop-sale-create").css("display", "none");
}

function setDefault() {
	$("#insertTitle").val('');
	$("#insertPublisher").val('');
	$("#insertAuthor").val('');
	$("#insertCostPrice").val('');
	$("#insertSalePrice").val('');
	$("#insertRegiDate").val('');
	$("#insertGenreType").val('');
	$("#insertState").val('');
	$("#insertInfo").val('');
}

//중고 서적 판매
function createSaleForm(){
	setDefault();
	closeDetailPopup();
	$("#pop-sale-create").css("display", "block");
}
function createSale() {	
	var publisher = $("#insertPublisher").val();
	var title = $("#insertTitle").val();
	var costPrice = $("#insertCostPrice").val();
	var userNo = 1;
	var author = $("#insertAuthor").val();
	var genreType = $("#insertGenreType").val();
	var image = '/images/sale/0.png'; //폼처리?
	var salePrice = $("#insertSalePrice").val();
	var info = $("#insertInfo").val();	

	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
    if(title == '' || title == undefined){
    	alert("제목을 입력하세요.");
    	return;
    } else if(publisher == '' || publisher == undefined){
    	alert("출판사를 입력하세요.");
    	return;
    } else if(author == '' || author == undefined){
    	alert("저자를 입력하세요.");
    	return;
    } else if(costPrice == '' || costPrice == undefined){
    	alert("원가를 입력하세요.");
    	return;
    } else if(!Number.isInteger(parseInt(costPrice))){
    	alert("원가는 숫자로 입력하세요.");
    	return;
    } else if(salePrice == '' || salePrice == undefined){
    	alert("판매가를 입력하세요.");
    	return;
    } else if(!Number.isInteger(parseInt(salePrice))){
    	alert("판매가는 숫자로 입력하세요.");
    	return;
    } else if(genreType == '' || genreType == undefined){
    	alert("장르를 입력하세요.");
    	return;
    } else if(info == '' || info == undefined){
    	alert("정보를 입력하세요.");
    	return;
    }
    
	$.ajax({
		url: "/book/sale/create", 
		method: 'POST',
	    dataType: "json",
		data: {
			title : title,
			publisher : publisher,
			salePrice : salePrice,
			info : info, 
			costPrice : costPrice,
			image : image,
			userNo : userNo,
			author : author,
			genreType : genreType
		},
		beforeSend : function(xhr){   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
            xhr.setRequestHeader(header, token);
        }
	}).done(function( data ) {
		if(data.result == 'success'){
			window.alert("중고 책을 등록하였습니다.");
	        
			$("#pop-sale-insert").css("display", "none");
			
			listSale();
			detailSale(data.saleNo);
		}
	})
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}

//수정
function updateSale() {	
	var saleNo = $("#viewSaleNo").val();
	var image = $("#viewImage").attr("src"); //폼처리?
	var salePrice = $("#viewSalePrice").val();
	var info = $("#viewInfo").val();
	
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
	
    if(salePrice == '' || salePrice == undefined){
    	alert("판매가를 입력하세요.");
    	return;
    } else if(!Number.isInteger(parseInt(salePrice))){
    	alert("판매가는 숫자로 입력하세요.");
    	return;
    } else if(info == '' || info == undefined){
    	alert("정보를 입력하세요.");
    	return;
    }
    
	$.ajax({
		url: "/book/sale/update", 
		method: 'PUT',
	    dataType: "json",
		data: {
			saleNo : saleNo,
			image : image,
			salePrice : salePrice,
			info : info
		},
		beforeSend : function(xhr){   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
            xhr.setRequestHeader(header, token);
        }
	}).done(function( data ) {
		if(data.result == 'success'){
			window.alert("중고 책 판매를 수정하였습니다.");
		}
	})
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}

//삭제
function deleteSale() {
	var saleNo = $("#viewSaleNo").val();
	
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
	
    if(confirm("판매를 삭제하시겠습니까?")) {
    	$.ajax({
    		url: "/book/sale/delete", 
    		method: 'DELETE',
    	    dataType: "json",
    		data: {
    			saleNo : saleNo
    		},
    		beforeSend : function(xhr){   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            }
    	}).done(function( data ) {
    		if(data.result == 'success'){
    			window.alert("중고 책 판매를 삭제하였습니다.");
    	        
    			closeDetailPopup();
    			listSale();
    		}
    	})
        .fail( function( textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    }
}

//구매
function createOrder() {
	
}

