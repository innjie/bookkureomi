var option = '';

$(document).ready(function(){
	if(($(location).attr('href').split('/book')[1]).includes('/sale/view')){
		listSale();
	}
	
	$.ajax({
		url: "/book/genre/list", 
		method: 'GET',
	    dataType: "json"
	}).done(function( data ) {
		for(var i = 0; i < data.genreList.length; i++){
			option += "<option value=\""+data.genreList[i].genreType+"\">"+data.genreList[i].genreType+"</option>";
		}
	})
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });

	
	$("#findType").change(function () {
	    if($("#findType").val() == 'title'){
	    	$("#findTitle").css("display","inline");
	    	$("#findGenre").css("display","none");
	    } else if($("#findType").val() == 'genre') {
	    	$("#findTitle").css("display","none");
	    	$("#findGenre").css("display","inline");
	    	
	    	$("#findGenre").html(option);
	    }
	});
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
		
		if(data.saleList.length > 0) {
			var result = "<table class=\"table-list\">"
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
			result = "<p class=\"find-nothing\">결과가 없습니다.</p>";
		}
		
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
		title = $("#findTitle").val();
	}else if(type == "genre"){
		genre = $("#findGenre").val();
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
	closeSaleCreatePopup();

	$.ajax({
		url: "/book/sale/detail", 
		method: 'GET',
	    dataType: "json",
		data: {
			saleNo : saleNo
		}
	}).done(function( data ) {
		$("#pop-sale-detail").css("display", "block");
				
	    var offset = $("#pop-sale-detail").offset().top;
		$("html").animate({scrollTop:offset},400);
		
		$("#viewImage").attr("src", data.sale.image);
		
		$("#viewSaleNo").val(data.sale.saleNo);
		$("#viewUser").val(data.sale.user.id);
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
		if(!data.isSeller){
			$('#viewSalePrice').prop('readonly', true);
	    	$('#viewInfo').prop('readonly', true);
		} else{
			$('#viewSalePrice').prop('readonly', false);
	    	$('#viewInfo').prop('readonly', false);
		}
		
		//배송 세팅
		if(data.delivery != null){
			deliveryInfoUpdate(data.delivery.company, data.delivery.waybill);
		} else if(data.sale.state == 'close' && data.isSeller){ 
			$('#deliveryInfo')[0].innerHTML = '';
			var infoDelivery = '';
			
			infoDelivery = "<button type=\"button\" class=\"pop-btn\" onClick=\"deliveryInfo()\">배송입력</button>";
		
			$('#deliveryInfo').append(infoDelivery);
		} else {
			$('#deliveryInfo')[0].innerHTML = '';
		}
		
		
		//버튼 세팅
		$('#buttonResult')[0].innerHTML = '';
		var resultBtn = '';
		
		if(data.sale.state != 'close'){
			if(!data.isSeller){
				resultBtn += "<button type=\"button\" class=\"pop-btn\" onClick=\"createSaleOrderForm()\">구매하기</button>";
				resultBtn += "<button type=\"button\" class=\"pop-btn\" onClick=\"createCartItem("+ data.sale.saleNo +")\">카트담기</button>";
			} else {
				resultBtn += "<button type=\"button\" class=\"pop-btn\" onClick=\"updateSale()\">수정하기</button>";			
				resultBtn += "<button type=\"button\" class=\"pop-btn\" onClick=\"deleteSale()\">삭제하기</button>";
			}	
		}
		resultBtn += "<button type=\"button\" class=\"pop-btn\" onClick=\"closeSaleDetailPopup()\">닫기</button>";
		
		$('#buttonResult').append(resultBtn);
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}


//상세 팝업 닫기
function closeSaleDetailPopup() {
	$("#pop-sale-detail").css("display", "none");
}

//추가 팝업 닫기
function closeSaleCreatePopup() {
	$("#pop-sale-create").css("display", "none");
}

//추가 폼 세팅
function setSaleDefault() {
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
	setSaleDefault();
	closeSaleDetailPopup();
	closeOrderPopup();
	setDefaultOrder();
	$("#pop-sale-create").css("display", "block");
	
    var offset = $("#pop-sale-create").offset().top;
	$("html").animate({scrollTop:offset},400);
	
	$("#insertGenreType").html(option);
}
function createSale() {	
	var publisher = $("#insertPublisher").val();
	var title = $("#insertTitle").val();
	var costPrice = $("#insertCostPrice").val();
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
    
    if(confirm("판매 등록하시겠습니까?")) {
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
    		} else{
    			window.location = "/book/user/login";
    			window.alert(data.reason);
    		}
    	})
        .fail( function( textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    }
}


//판매 수정
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
    
    if(confirm("판매 정보를 수정하시겠습니까?")) {
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
    		} else{
    			window.location = "/book/user/login";
    			window.alert(data.reason);
    		}
    	})
        .fail( function( textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    }
}

//판매 삭제
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
    	        
    			closeSaleDetailPopup();
    			listSale();
    		} else{
    			window.location = "/book/user/login";
    			window.alert(data.reason);
    		}
    	})
        .fail( function( textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    }
}