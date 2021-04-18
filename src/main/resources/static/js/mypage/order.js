$(document).ready(function(){
	listSaleOrder();
});

//주문 나열
function listSaleOrder(){
	var type = 'sale';
		
	listOrder(type);
}
function listAuctionOrder(){
	var type = 'auction';
		
	listOrder(type);
}
function listOrder(type) {
	$.ajax({
		url: "/book/mypage/order/list", 
		method: 'GET',
	    dataType: "json",
		data: {
			type: type
		}
	}).done(function( data ) {
		$('#result')[0].innerHTML = '';
		
		if(data.orderList.length > 0) {
			result =  "<table class=\"table-list\">"
					+ "<colgroup><col width=\"10%\" /><col width=\"30%\" /><col width=\"20%\" /><col width=\"20%\" /><col width=\"20%\" /></colgroup>"
					+ "<thead><tr><th class=\"table-header\">No.</th><th class=\"table-header\">주문 번호</th><th class=\"table-header\">책 제목</th><th class=\"table-header\">가격</th><th class=\"table-header\">주문 일자</th></tr></thead><tbody>";
			
			for(var i=0; i<data.orderList.length; i++){
				result += "<tr>"
							+ "<td class=\"table-text\">" + ((-1 * (i+1)) + data.orderList.length + 1)  + "</td>";

				
				
				//충전/사용 구분
				if(data.orderList[i].auction == null){
					result += "<td class=\"table-text\"><a onclick=\"saleOrder("+data.orderList[i].orderNo+");\">" + data.orderList[i].orderNo  + "</a></td>";
							+ "<td class=\"table-text\">" + data.orderList[i].sale.title + "</td>"
							+ "<td class=\"table-text\">" + data.orderList[i].sale.salePrice + "</td>";
				} else if(data.orderList[i].sale == null) {
					result += "<td class=\"table-text\"><a onclick=\"auctionOrder("+data.orderList[i].orderNo+");\">" + data.orderList[i].orderNo  + "</a></td>";
							+ "<td class=\"table-text\">" + data.orderList[i].auction.title + "</td>"
							+ "<td class=\"table-text\">" + data.orderList[i].auction.bidPrice + "</td>";
				}

				result += "<td class=\"table-text\">" + data.orderList[i].orderDate + "</td></tr>";
			}		
			result += "</tbody></table>";
			
		} else {
			result = "<p class=\"find-nothing\">주문 내역이 없습니다.</p>";
		}
		
		$('#result').append(result);
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}


//주문 상세보기
function saleOrder(orderNo){
	var type = 'sale';
		
	detailOrder(orderNo, type);
	detailDelivery(orderNo);
}
function auctionOrder(orderNo){
	var type = 'auction';
		
	detailOrder(orderNo, type);
	detailDelivery(orderNo);
}
function detailOrder(orderNo, type) {	
	$.ajax({
		url: "/book/mypage/order/detail", 
		method: 'GET',
	    dataType: "json",
		data: {
			orderNo : orderNo,
			type: type
		}
	}).done(function( data ) {
		$("#pop-order-detail").css("display", "block");
				
	    var offset = $("#pop-order-detail").offset().top;
		$("html").animate({scrollTop:offset},400);

		$("#viewOrderNo").val(data.order.orderNo);
		//$("#viewUserName").val(data.order.user.name);
		$("#viewUserName").val('im');
		$("#viewOrderDate").val(data.order.orderDate);
		$("#viewPAddress").val(data.order.paddress);
		$("#viewRName").val(data.order.rname);
		$("#viewRPhone").val(data.order.rphone);
		$("#viewRAddress").val(data.order.raddress);
		
		if(data.order.auction == null){
			$("#viewTitle").val(data.order.sale.title);
			$("#viewPrice").val(data.order.sale.salePrice);
		} else if(data.order.sale == null) {
			$("#viewTitle").val(data.order.auction.title);
			$("#viewPrice").val(data.order.auction.bidPrice);
		}
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}


//배송 정보
function detailDelivery (orderNo){
	$.ajax({
		url: "/book/delivery/detail", 
		method: 'GET',
	    dataType: "json",
		data: {
			orderNo : orderNo
		}
	}).done(function( data ) {
		$("#viewCompany").val(data.delivery.company);
		$("#viewWaybill").val(data.delivery.waybill);
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}

function closeDetailPopup() {
	$("#pop-order-detail").css("display", "none");
}

/*
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
		beforeSend : function(xhr){   
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
*/