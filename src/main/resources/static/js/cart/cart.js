$(document).ready(function(){
	if(($(location).attr('href').split('/book')[1]).includes('/cart/view')){
		listCartItem();
	}
});

//카트 내용물 나열
function listCartItem() {
	$.ajax({
		url: "/book/cart/list", 
		method: 'GET',
	    dataType: "json",
		data: {
		}
	}).done(function( data ) {
		$('#result')[0].innerHTML = '';
		
		var result = '';
		if(data.itemList.length > 0) {
			result +=  "<table class=\"table-list\">"
					+ "<colgroup><col width=\"10%\" /><col width=\"35%\" /><col width=\"25%\" /><col width=\"15%\" /><col width=\"15%\" /></colgroup>"
					+ "<thead><tr><th class=\"table-header\">No.</th><th class=\"table-header\">책 제목</th><th class=\"table-header\">가격</th><th class=\"table-header\"></th><th class=\"table-header\"></th></tr></thead><tbody>";
			
			for(var i=0; i<data.itemList.length; i++){
				if(data.itemList[i].state == 'open'){
					result += "<tr>";
				} else if(data.itemList[i].state == 'close'){
					result += "<tr class=\"text-close-item\">";
				}
				
				result += "<td class=\"table-text\">" + (i+1)  + "</td>"
						+ "<td class=\"table-text\">" + data.itemList[i].title + "</td>"
						+ "<td class=\"table-text\">" + data.itemList[i].salePrice + "</td>"
						+ "<td class=\"table-text\"><button type=\"button\" class=\"pop-btn\" onClick=\"deleteCartItem("+ data.itemList[i].saleNo +")\">삭제</button></td>"
						+ "<td class=\"table-text\"><button type=\"button\" class=\"pop-btn\" onClick=\"detailSale("+ data.itemList[i].saleNo +")\">상세보기</button></td></tr>";
			}		
			result += "</tbody></table>";
			
		} else {
			result += "<p class=\"find-nothing\">카트에 담긴 상품이 없습니다.</p>";
		}
		
		$('#result').append(result);
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}


//카트 담기
function createCartItem(saleNo){
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
	$.ajax({
		url: "/book/cart/create", 
		method: 'POST',
	    dataType: "json",
		data: {
			saleNo : saleNo
		},
		beforeSend : function(xhr){   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
            xhr.setRequestHeader(header, token);
        }
	}).done(function( data ) {
		if(data.result == 'success'){
			window.alert("카트 아이템을 추가하였습니다.");
			
			window.location = "/book/cart/view";
		} else if(data.result == 'fail') {
			alert("중복된 아이템이 이미 카트에 있습니다.");
		}
	})
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}


//카드 아이템 삭제
function deleteCartItem(saleNo){	
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
	$.ajax({
		url: "/book/cart/delete", 
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
			window.alert("카트 아이템을 삭제하였습니다.");
			
			listCartItem();
		}
	})
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}


//모든 카드 아이템 삭제
function deleteAllCartItem(){
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
	$.ajax({
		url: "/book/cart/delete", 
		method: 'DELETE',
	    dataType: "json",
		data: {
		},
		beforeSend : function(xhr){   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
            xhr.setRequestHeader(header, token);
        }
	}).done(function( data ) {
		if(data.result == 'success'){
			alert("모든 카트 아이템을 삭제하였습니다.");
			
			listCartItem();
		}
	})
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}