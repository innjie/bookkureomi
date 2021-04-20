$(document).ready(function(){
	if(($(location).attr('href').split('/book')[1]).includes('/order/view')){
		listSaleOrder();
	}
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
		url: "/book/order/list", 
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
		url: "/book/order/detail", 
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

//주문 상세 팝업 닫기
function closeOrderDetailPopup() {
	$("#pop-order-detail").css("display", "none");
}


//sale.html
//주문 팝업 닫기
function closeOrderPopup() {
	$("#pop-order-create").css("display", "none");
}

//주문 추가 폼 세팅
function setDefaultOrder() {
	$("#orderPAddress").val('');
	$("#orderRName").val('');
	$("#orderRPhone").val('');
	$("#orderRAddress").val('');
}

//구매(주문)
function createSaleOrderForm(){
	setDefaultOrder();
	$("#pop-order-create").css("display", "block");
	
	$("#orderTitle").val($("#viewTitle").val());
	$("#orderPrice").val($("#viewSalePrice").val());
	
	var offset = $("#pop-order-create").offset().top;
	$("html").animate({scrollTop:offset},400);
}
function createSaleOrder() {
	var saleNo = $("#viewSaleNo").val();
	var pAddress = $("#orderPAddress").val();
	var rName = $("#orderRName").val();
	var rPhone = $("#orderRPhone").val();
	var rAddress = $("#orderRAddress").val();
	
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    if(confirm("중고 책을 구입하시겠습니까?")) {
    	$.ajax({
    		url: "/book/order/create", 
    		method: 'POST',
    	    dataType: "json",
    		data: {
    			saleNo : saleNo,
    			pAddress : pAddress,
    			rName : rName,
    			rPhone : rPhone,
    			rAddress : rAddress
    		},
    		beforeSend : function(xhr){   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
              xhr.setRequestHeader(header, token);
          }
    	}).done(function( data ) {
    		if(data.result == 'success'){
    			window.alert("중고 책을 구입하였습니다.");
    	        
    			window.location = "/book/order/view";
    		}
    	})
      .fail( function( textStatus ) {
          alert( "Request failed: " + textStatus );
      });
    }
}