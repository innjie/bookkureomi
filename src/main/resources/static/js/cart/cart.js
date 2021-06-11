$(document).ready(function(){
	totalTitle = '';
	totalPrice = 0;
	totalSaleNoList = [];
	totalSaleList = new Map();
	
	if(($(location).attr('href').split('/book')[1]).includes('/cart/view')){
		listCartItem();
	}
	
	$('#allCheck').change(function(){
		var isAllChecked = $(this).prop("checked");
		
		if(isAllChecked) {            
            $('#table-result input[name=checkItem]').each(function(idx, obj) {
            	var isChecked = $(this).prop("checked");
            	
            	if(!isChecked){
            		var title = $(this).val().split("-")[0];
            		var price = $(this).val().split("-")[1];
            		
            		if(totalTitle == ''){
                		totalTitle = title;
                	}
                	totalPrice += Number.parseInt(price);
                	totalSaleNoList.push($(this).prop("id"));
                	totalSaleList.set($(this).prop("id"), title);
            	}
    	    });
            
            $("#result input[type=checkbox]").prop("checked", true);
        } else {
            $("#result input[type=checkbox]").prop("checked", false);
            
            totalTitle = '';
            totalPrice = 0;
            totalSaleNoList = [];
            totalSaleList.clear();
        }
		
		if(totalSaleNoList.length == 1 || totalSaleNoList.length == 0){
			$('#cart-info').text(totalTitle);
		} else{
			$('#cart-info').text(totalTitle + ' 외 ' + (totalSaleNoList.length-1) + '권');
		}
		$('#cart-price').text(totalPrice.toLocaleString());
	});
	
	$(document).on('change', '#table-result input[name=checkItem]', function(){
		var isChecked = $(this).prop("checked");	
		var title = $(this).val().split("-")[0];
		var price = $(this).val().split("-")[1];
		
		if(isChecked) {
			if(totalTitle == ''){
        		totalTitle = title;
        	}
        	totalPrice += Number.parseInt(price);
        	totalSaleNoList.push($(this).prop("id"));
        	totalSaleList.set($(this).prop("id"), title);
		} else {
        	if($(this).prop("id") == totalSaleNoList[0]){
        		totalTitle = '';
        		
        		if(totalSaleNoList.length != 1){
        			totalTitle = totalSaleList.get(totalSaleNoList[1]);
        		}
        	}
        	totalPrice -= Number.parseInt(price);
        	
        	var idx = totalSaleNoList.indexOf($(this).prop("id"));
        	totalSaleNoList.splice(idx, 1);
        	totalSaleList.delete($(this).prop("id"));
        }
		
		if(totalSaleNoList.length == 1 || totalSaleNoList.length == 0){
			$('#cart-info').text(totalTitle);
		} else{
			$('#cart-info').text(totalTitle + ' 외 ' + (totalSaleNoList.length-1) + '권');
		}
		$('#cart-price').text(totalPrice.toLocaleString());
	});
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
		$('#table-result')[0].innerHTML = '';
		
		var result = '';
		
		if(data.itemList.length > 0) {
			for(var i=0; i<data.itemList.length; i++){
				if(data.itemList[i].state == 'open'){
					result += "<tr><td class=\"table-text\"><input type=\"checkbox\" name=\"checkItem\" value=\""+ data.itemList[i].title +"-"+ data.itemList[i].salePrice +"\" id=\""+ data.itemList[i].saleNo +"\"></input></td>";
				} else if(data.itemList[i].state == 'close'){
					result += "<tr class=\"text-close-item\"><td></td>";
				}
				
				result += "<td class=\"table-text\">" + (i+1)  + "</td>"
						+ "<td class=\"table-text\">" + data.itemList[i].title + "</td>"
						+ "<td class=\"table-text\">" + data.itemList[i].salePrice + "</td>"
						+ "<td class=\"table-text\"><button type=\"button\" class=\"btn-cart\" onClick=\"deleteCartItem("+ data.itemList[i].saleNo +")\">삭제</button></td>"
						+ "<td class=\"table-text\"><button type=\"button\" class=\"btn-cart\" onClick=\"detailSale("+ data.itemList[i].saleNo +")\">상세보기</button></td></tr>";
			}		
			
			$('#table-result').append(result);
			$('#table-result-nothing').css("margin-top", "0px");
		
			$('#table-result input[type=checkbox]').change(function(){
				var checkBoxAllCount = "#table-result input[type=checkbox]";
		        var checkBoxCheckedCount = "#table-result input:checked";
		        
		        var lenA = $(checkBoxAllCount).length;
                var lenC = $(checkBoxCheckedCount).length;
                if( lenA == lenC ) {
                    $("#allCheck").prop("checked", true);
                } else {
                    $("#allCheck").prop("checked", false);
                }
			});
		} else {
			result += "카트에 담긴 상품이 없습니다.";
		
			$('#table-result-nothing').append(result);
			$('#result').css("display", "none");
			$('#btn-result').css("margin-top", "200px");
		}
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}


//카트 담기
function createCartItem(saleNo){
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
    if(confirm("선택하신 책을 카트에 추가하시겠습니까?")) {
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
    			if(confirm('카트에 책을 추가하였습니다.\n카트로 이동하시겠습니까?')){
        			window.location = "/book/cart/view";
    			}
    		} else if(data.result == 'fail') {
    			alert("중복된 아이템이 이미 카트에 있습니다.");
    		}
    	})
        .fail( function( textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    }
}


//카트 아이템 삭제
function deleteSelectCartItem(){
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
	if($("#table-result input:checked").length == 0) {
		alert("선택한 책이 없습니다.");
	} else if($('#allCheck').prop("checked")){
		deleteAllCartItem(token, header);
	} else {
		deleteSelectCartItems(token, header);
	}
}


//카트 아이템 삭제
function deleteCartItem(saleNo){	
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
    if(confirm("선택하신 책을 카트에서 삭제하시겠습니까?")) {
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
    			window.alert("선택하신 책을 카트에서 삭제하였습니다.");
    			
    			listCartItem();
    		}
    	})
        .fail( function( textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    }
}


//선택 카트 아이템 삭제
function deleteSelectCartItems(token, header){
    var checkBoxCheckedCount = $("#table-result input:checked").length;
    var cnt = 0;

	if(confirm("선택한 책을 카트에서 삭제하시겠습니까?")) {
		$('#table-result input:checked').each(function(idx, obj) {
			var saleNo = $(this).prop("id");
			cnt++;
					
			$.ajax({
	    		url: "/book/cart/delete", 
	    		method: 'DELETE',
	    	    dataType: "json",
	    	    async: false,
	    		data: {
	    			saleNo : saleNo
	    		},
	    		beforeSend : function(xhr){   
	                xhr.setRequestHeader(header, token);
	            }
	    	}).done(function( data ) {
	    		if(data.result == 'success'){
	    			if(checkBoxCheckedCount == cnt){
	    				alert("선택하신 "+ cnt +"개의 책을 카트에서 삭제하였습니다.");
	    				
	    				listCartItem();
	    			}
	    		}
	    	})
	        .fail( function( textStatus ) {
	            alert( "Request failed: " + textStatus );
	        });
	    });
	}
}


//모든 카트 아이템 삭제
function deleteAllCartItem(token, header){    
    if(confirm("모든 책을 카트에서 삭제하시겠습니까?")) {
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
    			alert("모든 책을 카트에서 삭제하였습니다.");
    		
    			$('#table-result-nothing').css("margin-top", "200px");
    			listCartItem();
    		}
    	})
        .fail( function( textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    }
}