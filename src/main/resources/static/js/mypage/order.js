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
					+ "<colgroup><col width=\"10%\" /><col width=\"20%\" /><col width=\"30%\" /><col width=\"20%\" /><col width=\"20%\" /></colgroup>"
					+ "<thead><tr><th class=\"table-header\">No.</th><th class=\"table-header\">주문 번호</th><th class=\"table-header\">주문정보</th><th class=\"table-header\">가격</th><th class=\"table-header\">주문 일자</th></tr></thead><tbody>";
			
			for(var i=0; i<data.orderList.length; i++){
				result += "<tr>"
							+ "<td class=\"table-text\">" + ((-1 * (i+1)) + data.orderList.length + 1)  + "</td>";

				result += "<td class=\"table-text\"><a onclick=\"saleOrder("+data.orderList[i].orderNo+");\">" + data.orderList[i].orderNo  + "</a></td>"
						+ "<td class=\"table-text\">" + data.orderList[i].info + "</td>"
						+ "<td class=\"table-text\">" + data.orderList[i].total + "</td>";

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
	//detailDelivery(orderNo);
}
function auctionOrder(orderNo){
	var type = 'auction';
		
	detailOrder(orderNo, type);
	//detailDelivery(orderNo);
}
function detailOrder(orderNo, type) {	
	$("#pop-mask-order-detail").css("display","block");
	$("#pop-mask-order-detail").css("overflow","auto");
	$("body").css("overflow","hidden");
	$("#pop-order-detail").css({
        "top": (window.screen.height / 2) - ($("#pop-order-detail").outerHeight() / 2)-50+"px",
        "left": (window.screen.width / 2) - ($("#pop-order-detail").outerWidth() / 2)+"px"     
     }); 
	
	
	$.ajax({
		url: "/book/order/detail", 
		method: 'GET',
	    dataType: "json",
		data: {
			orderNo : orderNo,
			type: type
		}
	}).done(function( data ) {		
		$('#resultDetail')[0].innerHTML = '';
		var result = '';
		
		if(data.orderDetailList[0].auction == null){
			if(data.orderDetailList.length > 3){
				$(".pop").css("height", (800+(70*(data.orderDetailList.length-3)))+"px");
				$("#pop-style1-order").css("height", (500+(70*(data.orderDetailList.length-3)))+"px");
			}
			
			for(var i=0; i<data.orderDetailList.length; i++){
				result += "<ul class=\"pop-style2\">"
						+ "<li class=\"pop-style2-list\">"
						+ "<ul class=\"pop-style3\">"
						+ "<li>제목<input type=\"text\" id=\"viewTitle"+ data.orderDetailList[i].sale.saleNo +"\" value=\""+ data.orderDetailList[i].sale.title +"\" readonly /></li>"
						+ "<li>가격<input type=\"number\" id=\"viewPrice"+ data.orderDetailList[i].sale.saleNo +"\" value=\""+ data.orderDetailList[i].sale.salePrice +"\" readonly /></li>"
						+ "</ul></li>"
						+ "<li class=\"pop-style2-list\">"
						+ "<ul class=\"pop-style3\">";
				
				if(data.deliveryList[i] != null){
					result += "<li>택배사<input type=\"text\" id=\"viewCompany"+ data.orderDetailList[i].sale.saleNo +"\" value=\""+ data.deliveryList[i].company +"\" readonly /></li>"
							+ "<li>송장번호<input type=\"number\" id=\"viewWaybill"+ data.orderDetailList[i].sale.saleNo +"\" value=\""+ data.deliveryList[i].waybill +"\" readonly /></li>"
							+ "</ul></li></ul><br/>";
				} else {
					result += "<li>택배사<input type=\"text\" id=\"viewCompany"+ data.orderDetailList[i].sale.saleNo +"\" readonly /></li>"
							+ "<li>송장번호<input type=\"number\" id=\"viewWaybill"+ data.orderDetailList[i].sale.saleNo +"\" readonly /></li>"
							+ "</ul></li></ul><br/>";
				}

			}	
		} else if(data.orderDetailList[0].sale == null){
			result += "<ul class=\"pop-style2\">"
					+ "<li class=\"pop-style2-list\">"
					+ "<ul class=\"pop-style3\">"
					+ "<li>제목<input type=\"text\" id=\"viewTitle"+ data.orderDetailList[i].auction.auctionNo +"\" value=\""+ data.orderDetailList[i].auction.title +"\" readonly /></li>"
					+ "<li>가격<input type=\"number\" id=\"viewPrice"+ data.orderDetailList[i].auction.auctionNo +"\" value=\""+ data.orderDetailList[i].auction.bidPrice +"\" readonly /></li>"
					+ "</ul></li>"
					+ "<li class=\"pop-style2-list\">"
					+ "<ul class=\"pop-style3\">"
					+ "<li>택배사<input type=\"text\" id=\"viewCompany"+ data.orderDetailList[i].auction.auctionNo +"\" readonly /></li>"
					+ "<li>송장번호<input type=\"text\" id=\"viewWaybill"+ data.orderDetailList[i].auction.auctionNo +"\" readonly /></li>"
					+ "</ul></li></ul>";
		}
		
		$('#resultDetail').append(result);
				
		$("#pop-order-detail").css("display", "block");
				
	    var offset = $("#pop-order-detail").offset().top;
		$("html").animate({scrollTop:offset},400);

		$("#viewOrderNo").val(data.orderDetailList[0].order.orderNo);
		$("#viewUserName").val(data.orderDetailList[0].order.user.name);
		$("#viewOrderInfo").val(data.orderDetailList[0].order.info);
		$("#viewOrderTotal").val(data.orderDetailList[0].order.total);
		$("#viewOrderDate").val(data.orderDetailList[0].order.orderDate);
		$("#viewPAddress").val(data.orderDetailList[0].order.paddress);
		$("#viewRName").val(data.orderDetailList[0].order.rname);
		$("#viewRPhone").val(data.orderDetailList[0].order.rphone);
		$("#viewRAddress").val(data.orderDetailList[0].order.raddress);
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}


//주문 상세 팝업 닫기
function closeOrderDetailPopup() {
	$("#pop-order-detail").css("display", "none");
	$("#pop-mask-order-detail").css("display","none");
	$("#pop-mask-order-detail").css("overflow","hidden");
	$("body").css("overflow","auto");
	
	$(".pop").css("height", "800px");
	$("#pop-style1-order").css("height", "500px");
}


var isCart = false;

//sale.html
//주문 팝업 닫기
function closeOrderPopup() {
	$("#pop-order-create").css("display", "none");
	$("#pop-mask-order-create").css("display","none");
	$("#pop-mask-order-create").css("background-color","rgba( 0, 0, 0, 0.8 )");
	if(isCart){
		$("body").css("overflow","auto");
		isCart = false;
	}
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
	$("#pop-mask-order-create").css("display","block");
	$("#pop-mask-order-create").css("background-color","rgba( 0, 0, 0, 0 )");
	$("#pop-order-create").css({
        "top": (window.screen.height / 2) - ($("#pop-order-create").outerHeight() / 2)-50+"px",
        "left": (window.screen.width / 2) - ($("#pop-order-create").outerWidth() / 2)+"px"     
     }); 
	
	setDefaultOrder();
	$("#pop-order-create").css("display", "block");
	
	$("#orderTitle").val($("#viewTitle").val());
	$("#orderPrice").val($("#viewSalePrice").val());
	
	var offset = $("#pop-order-create").offset().top;
	$("html").animate({scrollTop:offset},400);
}
function createSaleOrder() {
	var saleNo = $("#viewSaleNo").val();
	var saleNoList = [];
	saleNoList.push(saleNo);
	
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
    			saleNoList : saleNoList,
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
    			window.alert("※주문성공※\n중고 책을 구입하였습니다.");
    	        
    			window.location = "/book/order/view";
    		} else if(data.result == 'fail'){
    			window.alert(data.reason);
    		}
    	}).fail( function( textStatus ) {
          alert( "Request failed: " + textStatus );
      });
    }
}

//구매(주문) - 카트
function createCartItemOrderForm(){
	if($("#table-result input:checked").length == 0) {
		alert("선택한 책이 없습니다.");
	} else{
		$("#pop-mask-order-create").css("display","block");
		$("#pop-mask-order-create").css("background-color","rgba( 0, 0, 0, 0.8 )");
		$("body").css("overflow","hidden");
		$("#pop-order-create").css({
	        "top": (window.screen.height / 2) - ($("#pop-order-create").outerHeight() / 2)-50+"px",
	        "left": (window.screen.width / 2) - ($("#pop-order-create").outerWidth() / 2)+"px"     
	     });
		isCart = true;
		
		setDefaultOrder();
		$("#pop-order-create").css("display", "block");
		
		$("#orderTitle").val($("#cart-info").text());
		$("#orderPrice").val(totalPrice);
		
		var offset = $("#pop-order-create").offset().top;
		$("html").animate({scrollTop:offset},400);
	}
}
function createCartItemOrder() {
	var saleNoList = totalSaleNoList;
	
	var pAddress = $("#orderPAddress").val();
	var rName = $("#orderRName").val();
	var rPhone = $("#orderRPhone").val();
	var rAddress = $("#orderRAddress").val();
	
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    if(confirm("선택한 책을 구입하시겠습니까?")) {
    	$.ajax({
    		url: "/book/order/create", 
    		method: 'POST',
    	    dataType: "json",
    		data: {
    			saleNoList : saleNoList,
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
    			window.alert("※주문성공※\n중고 책을 구입하였습니다.");
    	        
    			window.location = "/book/order/view";
    		} else if(data.result == 'fail'){
    			window.alert(data.reason);
    		}
    	}).fail( function( textStatus ) {
          alert( "Request failed: " + textStatus );
    	});
    }
}