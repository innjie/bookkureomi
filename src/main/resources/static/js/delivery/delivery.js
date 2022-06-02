//배송 세팅
function deliveryInfo() {
	$('#deliveryInfo')[0].innerHTML = '';
	var infoDelivery = '';
	infoDelivery += "<ul class=\"pop-style2\">"
					+ "<li class=\"pop-style2-list\">"
					+ "<p class=\"text-size-17 text-highlight\">배송 정보</p>"
					+ "<ul class=\"pop-style3\">"
					+ "<li>택배사 <input type=\"text\" id=\"viewCompany\" /></li>"
					+ "<li>송장번호 <input type=\"text\" id=\"viewWaybill\" /></li></ul></li>"
					+ "<li class=\"pop-style2-list\">"
					+ "<button type=\"button\" class=\"pop-btn\" onClick=\"createDelivery()\">배송추가</button></li></ul>";	
	$('#deliveryInfo').append(infoDelivery);
}

//배송 수정 세팅
function deliveryInfoUpdate(company, waybill) {
	$('#deliveryInfo')[0].innerHTML = '';
	var infoDelivery = '';
	infoDelivery += "<ul class=\"pop-style2\">"
					+ "<li class=\"pop-style2-list\">"
					+ "<p class=\"text-size-17 text-highlight\">배송 정보</p>"
					+ "<ul class=\"pop-style3\">"
					+ "<li>택배사 <input type=\"text\" id=\"viewCompany\" value=\"" + company + "\" /></li>"
					+ "<li>송장번호 <input type=\"text\" id=\"viewWaybill\" value=\"" + waybill + "\" /></li></ul></li>"
					+ "<li class=\"pop-style2-list\">"
					+ "<button type=\"button\" class=\"pop-btn\" onClick=\"updateDelivery()\">배송수정</button></li></ul>";	
	$('#deliveryInfo').append(infoDelivery);
}


//배송 추가
function createDelivery(){
	var saleNo = $("#viewSaleNo").val();
	var company = $("#viewCompany").val();
	var waybill = $("#viewWaybill").val();

	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    if(company == '' || company == undefined){
    	alert("택배사 입력하세요.");
    	return;
    } else if(waybill == '' || waybill == undefined){
    	alert("송장번호를 입력하세요.");
    	return;
    } 
    
    if(confirm("배송정보를 등록하시겠습니까?")) {
    	$.ajax({
    		url: "/book/delivery/create", 
    		method: 'POST',
    	    dataType: "json",
    		data: {
    			saleNo : saleNo,
    			company : company,
    			waybill : waybill
    		},
    		beforeSend : function(xhr){   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            }
    	}).done(function( data ) {
    		if(data.result == 'success'){
    			window.alert("배송정보를 등록하였습니다.");
    			
    			$('#deliveryInfo')[0].innerHTML = '';
    			var infoDelivery = '';
    			infoDelivery += "<ul class=\"pop-style2\">"
    							+ "<li class=\"pop-style2-list\">"
    							+ "<p class=\"text-size-17 text-highlight\">배송 정보</p>"
    							+ "<ul class=\"pop-style3\">"
    							+ "<li>택배사 <input type=\"text\" id=\"viewCompany\" value=\"" + company + "\" /></li>"
    							+ "<li>송장번호 <input type=\"text\" id=\"viewWaybill\" value=\"" + waybill + "\" /></li></ul></li>"
    							+ "<li class=\"pop-style2-list\">"
    							+ "<button type=\"button\" class=\"pop-btn\" onClick=\"updateDelivery()\">배송수정</button></li></ul>";	
    			$('#deliveryInfo').append(infoDelivery);			
    		} else {
    			window.alert("배송정보 등록에 실패하였습니다.");
    		}
    	})
        .fail( function( textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    }
}


//배송 수정
function updateDelivery(){
	var saleNo = $("#viewSaleNo").val();
	var company = $("#viewCompany").val();
	var waybill = $("#viewWaybill").val();

	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    if(company == '' || company == undefined){
    	alert("택배사 입력하세요.");
    	return;
    } else if(waybill == '' || waybill == undefined){
    	alert("송장번호를 입력하세요.");
    	return;
    } 
    
    if(company.length > 50){
    	alert("택배사는 50자까지 가능합니다.");
    	return;
    } else if(/[^0-9]/g.test(waybill)){
    	alert("송장번호는 숫자로만 입력해주세요.");
    	return;
    } else if(waybill.length > 50){
    	alert("송장번호는 100자까지 가능합니다.");
    	return;
    }
    
    if(confirm("배송정보를 수정하시겠습니까?")) {
    	$.ajax({
    		url: "/book/delivery/update", 
    		method: 'PUT',
    	    dataType: "json",
    		data: {
    			saleNo : saleNo,
    			company : company,
    			waybill : waybill
    		},
    		beforeSend : function(xhr){   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            }
    	}).done(function( data ) {
    		if(data.result == 'success'){
    			window.alert("배송정보를 수정하였습니다.");
    			
    			deliveryInfoUpdate(company, waybill);			
    		} else {
    			window.alert("배송정보 수정에 실패하였습니다.");
    		}
    	})
        .fail( function( textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    }
}