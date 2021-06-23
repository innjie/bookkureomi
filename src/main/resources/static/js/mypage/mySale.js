var option = '';

$(document).ready(function(){
	if(($(location).attr('href').split('/book')[1]).includes('/mypage/sale/view')){
		listMySale(nowPageNo);
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
	
	$("#insertImageFile").change(function () {
		if(this.files && this.files[0]){
			var reader = new FileReader;
			reader.onload = function(data) {
				$("#selectImage").css("display","inline");
				$("#selectImage").attr("src", data.target.result).width(150).height(200);
			}
			reader.readAsDataURL(this.files[0]);
		} else{
			$("#selectImage").css("display","none");
			$("#selectImage").attr("src", "");
		}
	});
});

//판매 중고 서적 나열
function listMySale(pageNo){
	nowPageNo = pageNo;

	$.ajax({
		url: "/book/mypage/sale/list", 
		method: 'GET',
	    dataType: "json",
		data: {
			pageNo: pageNo
		}
	}).done(function( data ) {
		window.scrollTo(0,0);
		$("#saleCnt").text(data.saleCnt);
		paging(data, 'listMySale');
		
		$('#result')[0].innerHTML = '';
		
		var result = "<table class=\"table-list\">"
					+ "<colgroup><col width=\"33.333333%\" /><col width=\"33.333333%\" /><col width=\"33.333333%\" /></colgroup><tbody>";
		var sale;
		
		for(var i=0; i<data.saleList.length; i++){
			if(i%3 == 0){
				result += "</tr><tr>";
			} else if(i == 0){
				result += "<tr>";
			}
			
			result += "<td><ul class=\"list-style\">";
			
			//image
			if(data.saleList[i].imageList.length > 0){
				result += "<li class=\"table-list-image\">"
						+ "<img src=\"" + "/book/image?path="+encodeURI(data.saleList[i].imageList[0].filePath) + "\" class=\"img-fit\"/>"
						+ "</li>";
			} else{
				result += "<li class=\"table-list-image\">"
					+ "<img src=\"/images/sale/0.png\" class=\"img-fit\"/>"
					+ "</li>";
			}
			
			//info
			result += "<li class=\"table-list-content\"><ul class=\"table-list-content-style\">"
						+ "<li class=\"table-list-content-list-style\"><strong>" + data.saleList[i].title + "</strong></li>"
						+ "<li class=\"table-list-content-list-style\">" + data.saleList[i].author + " / " + data.saleList[i].publisher + "</li>"
						+ "<li class=\"table-list-content-list-style\"> \\" + data.saleList[i].costPrice + " -> \\" + data.saleList[i].salePrice + "</li>"
						+ "<li class=\"table-list-content-list-style\">" + data.saleList[i].state + "</li>"
						+ "<li class=\"table-list-content-btn-style\"><button type=\"button\" id=\"btnSale"+ data.saleList[i].saleNo +"\" class=\"view-btn\" onClick=\"detailMySale("+ data.saleList[i].saleNo +")\">상세보기</button><li>"
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

//판매 중고서적 상세보기
function detailMySale(saleNo) {	
	$("#updateImageFile").val('');

	$("#pop-mask-sale-detail").css("display","block");
	$("#pop-mask-sale-detail").css("overflow","auto");
	$("body").css("overflow","hidden");
	$("#pop-sale-detail").css({
        "top": (window.screen.height / 2) - ($("#pop-sale-detail").outerHeight() / 2)-50+"px",
        "left": (window.screen.width / 2) - ($("#pop-sale-detail").outerWidth() / 2)+"px"     
     }); 

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
			$('#imageForm').css("display","none");
	    	$('#imageBtn').css("display","none");
		} else{
			$('#viewSalePrice').prop('readonly', false);
	    	$('#viewInfo').prop('readonly', false);
	    	$('#imageForm').css("display","inline");
	    	$('#imageBtn').css("display","inline");
		}
		
		//사진 세팅
		if(data.sale.imageList.length > 0){
			$("#viewImage").attr("src", "/book/image?path="+encodeURI(data.sale.imageList[0].filePath));
			$("#imageNo").val(data.sale.imageList[0].imageNo);
			$("#filePath").val(data.sale.imageList[0].filePath);
		} else{
			$("#viewImage").attr("src", "/images/sale/0.png");
		}
		
		//주문 세팅
		if(data.orderDetail != null){ //판매자 추가
			$('#orderInfo')[0].innerHTML = '';
			var infoOrder = '';
			
			infoOrder += "<ul class=\"pop-style2\">"
							+ "<li class=\"pop-style2-list\">"
							+ "<p class=\"text-size-17 text-highlight\">주문 정보</p>"
							+ "<ul class=\"pop-style3\">"
							+ "<li>주문번호 <input type=\"text\" id=\"viewOrderNo\" value=\"" + data.orderDetail.order.orderNo + "\" /></li>"
						//	+ "<li>주문정보 <input type=\"text\" id=\"viewOrderInfo\" value=\"" + data.orderDetail.order.info + "\" /></li></li>"
							+ "<li>총액 <input type=\"text\" id=\"viewOrderTotal\" value=\"" + data.orderDetail.order.total + "\" /></li>"
							+ "<li>주문일 <input type=\"text\" id=\"viewOrderDate\" value=\"" + data.orderDetail.order.orderDate + "\" /></li>"
							+ "<li>주문자 <input type=\"text\" id=\"viewUserName\" value=\"" + data.orderDetail.order.user.name + "\" /></li>"
							+ "<li>주문자 주소 <input type=\"text\" id=\"viewPAddress\" value=\"" + data.orderDetail.order.paddress + "\" /></li>"
							+ "<li>받는이 <input type=\"text\" id=\"viewRName\" value=\"" + data.orderDetail.order.rname + "\" /></li>"
							+ "<li>받는이 전화번호 <input type=\"text\" id=\"viewRPhone\" value=\"" + data.orderDetail.order.rphone + "\" /></li>"
							+ "<li>배송지 주소 <input type=\"text\" id=\"viewRAddress\" value=\"" + data.orderDetail.order.raddress + "\" /></li>"
							+ "</ul>";
			
			$('#orderInfo').append(infoOrder);
			
			$("#pop-sale-detail").css("height","1000px");
			$("#detailUL").css("height","800px");
		} else {
			$('#orderInfo')[0].innerHTML = '';
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
				resultBtn += "<button type=\"button\" class=\"pop-btn\" onClick=\"updateMySale()\">수정하기</button>";			
				resultBtn += "<button type=\"button\" class=\"pop-btn\" onClick=\"deleteMySale()\">삭제하기</button>";
			}	
		}
		resultBtn += "<button type=\"button\" class=\"pop-btn\" onClick=\"closeMySaleDetailPopup()\">닫기</button>";
		
		$('#buttonResult').append(resultBtn);
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}


//상세 팝업 닫기
function closeMySaleDetailPopup() {
	$("#pop-sale-detail").css("display", "none");
	$("#pop-sale-detail").css("height","800px");
	$("#detailUL").css("height","500px");
	
	$("#pop-mask-sale-detail").css("display","none");
	$("#pop-mask-sale-detail").css("overflow","hidden");
	$("body").css("overflow","auto");
}

//추가 팝업 닫기
function closeMySaleCreatePopup() {
	$("#pop-sale-create").css("display", "none");
	$("#pop-mask-sale-create").css("display","none");
	$("body").css("overflow","auto");
}

//추가 폼 세팅
function setMySaleDefault() {
	$("#insertImageFile").val('');
	$("#selectImage").attr("src", '').width(0).height(0);
	
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
function createMySaleForm(){
	$("#pop-mask-sale-create").css("display","block");
	$("body").css("overflow","hidden");
	$("#pop-sale-create").css({
        "top": (window.screen.height / 2) - ($("#pop-sale-create").outerHeight() / 2)-50+"px",
        "left": (window.screen.width / 2) - ($("#pop-sale-create").outerWidth() / 2)+"px"     
     }); 
	
	setMySaleDefault();
	$("#pop-sale-create").css("display", "block");
	
    var offset = $("#pop-sale-create").offset().top;
	$("html").animate({scrollTop:offset},400);

	$("#insertGenreType").html(option);
}
function createMySale() {	
	var publisher = $("#insertPublisher").val();
	var title = $("#insertTitle").val();
	var costPrice = $("#insertCostPrice").val();
	var author = $("#insertAuthor").val();
	var genreType = $("#insertGenreType").val();
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
    
    
    var form = $('#insertImage')[0];
    var formData = new FormData(form);
    
    formData.append("title", title);
    formData.append("publisher", publisher);
    formData.append("salePrice", salePrice);
    formData.append("info", info);
    formData.append("costPrice", costPrice);
    formData.append("author", author);
    formData.append("genreType", genreType);
    
    if(confirm("판매 등록하시겠습니까?")) {
    	$.ajax({
    		url: "/book/sale/create", 
    		method: 'POST',
    		enctype: 'multipart/form-data',
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
    		beforeSend : function(xhr){   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            }
    	}).done(function( data ) {
    		if(data.result == 'success'){
    			window.alert("중고 책을 등록하였습니다.");
    	        
    			$("#pop-sale-insert").css("display", "none");
    			
    			closeMySaleCreatePopup();
    			listMySale(1);
    			detailMySale(data.saleNo);
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
function updateMySale() {	
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
function deleteMySale() {
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
    	        
    			closeMySaleDetailPopup();
    			listMySale();
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