var type = 'all';

$(document).ready(function(){
	if(($(location).attr('href').split('/book')[1]).includes('/point/view')){
		detailPoint();
		listRecharge(nowPageNo);
	}
	
	$("#listType").change(function(){
		type = $("#listType").val();
		listRecharge(1);
	});
});

//충전/사용 내역 나열
function listRecharge(pageNo){
	nowPageNo = pageNo;

	$.ajax({
		url: "/book/point/list", 
		method: 'GET',
	    dataType: "json",
		data: {
			pageNo: pageNo,
			type: type
		}
	}).done(function( data ) {
		window.scrollTo(0,0);
		$("#pointCnt").text(data.pointCnt);
		paging(data, 'listRecharge');
		var pointCnt = data.pointCnt
		
		$('#result')[0].innerHTML = '';
		
		var result;
		
		if(data.rechargeList.length > 0) {
			result =  "<table class=\"table-list\">"
					+ "<colgroup><col width=\"10%\" /><col width=\"15%\" /><col width=\"15%\" /><col width=\"15%\" /><col width=\"15%\" /><col width=\"15%\" /></colgroup>"
					+ "<thead><tr><th class=\"table-header\">No.</th><th class=\"table-header\">충전/사용 날짜</th><th class=\"table-header\">충전 포인트</th><th class=\"table-header\">사용 포인트</th><th class=\"table-header\">충전 방법</th><th class=\"table-header\">총 포인트</th></tr></thead><tbody>";
			
			for(var i=0; i<data.rechargeList.length; i++){
				result += "<tr>"
							+ "<td class=\"table-text\">" + ((pointCnt--) - (pageNo-1)*10)  + "</td>"
							//+ "<td class=\"table-text\">" + ((-1 * (i+1)) + data.rechargeList.length + 1)  + "</td>"
							+ "<td class=\"table-text\">" + data.rechargeList[i].rcDate + "</td>";
				
				//충전/사용 구분
				if(data.rechargeList[i].rcType == "recharging"){
					result += "<td class=\"table-text\">" + data.rechargeList[i].rcPoint + "</td>"
							+ "<td class=\"table-text\"></td>";
				} else if(data.rechargeList[i].rcType == "using") {
					result += "<td class=\"table-text\"></td>"
							+ "<td class=\"table-text\">" + (-1 * data.rechargeList[i].rcPoint) + "</td>";
				}

				
				if(data.rechargeList[i].rcMethod == 'kakao') {
					result += "<td class=\"table-text\">카카오페이충전</td>";
				} else if(data.rechargeList[i].rcMethod == 'join') {
					result += "<td class=\"table-text\">가입이벤트</td>";
				} else if(data.rechargeList[i].rcMethod == 'selling') {
					result += "<td class=\"table-text\">판매</td>";
				} else if(data.rechargeList[i].rcMethod == null) {
					result += "<td class=\"table-text\"></td>";
				}
				
					result += "<td class=\"table-text\">" + data.rechargeList[i].totalPoint + "</td>";
				
				result += "</tr>";
			}		
			result += "</tbody></table>";
			
		} else {
			result = "<p class=\"find-nothing\">포인트 충전 내역이 없습니다.</p>";
		}
		
		$('#result').append(result);
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}

//포인트 보기
function detailPoint() {	
	$.ajax({
		url: "/book/point/detail", 
		method: 'GET',
	    dataType: "json",
		data: {
		}
	}).done(function( data ) {
		$("#myPoint")[0].innerHTML = data.point + " 포인트";
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}


//추가 팝업 닫기
function closeRechargeCreatePopup() {
	$("#pop-recharge-create").css("display", "none");
	$("#pop-mask-recharge-create").css("display","none");
	$("body").css("overflow","auto");
}

//추가 폼 세팅
function setRechargeDefault() {
	$("#rechargeType").val('kakao');
	$("#rechargePrice").val('1000');
}


//충전
function createRechargeForm(){
	$("#pop-mask-recharge-create").css("display","block");
	$("body").css("overflow","hidden");
	$("#pop-recharge-create").css({
        "top": (window.screen.height / 2) - ($("#pop-recharge-create").outerHeight() / 2)-50+"px",
        "left": (window.screen.width / 2) - ($("#pop-recharge-create").outerWidth() / 2)+"px"     
     }); 
	
	setRechargeDefault();
	$("#pop-recharge-create").css("display", "block");
	
    var offset = $("#pop-recharge-create").offset().top;
	$("html").animate({scrollTop:offset},400);
}
function createRecharge(){
	var rcMethod = $("#rechargeMethod").val();
	var rcPoint = $("#rechargePrice").val();
	
//	window.open("/book/kakao/kakaoPay?rcPoint=" + rcPoint);
	if(rcMethod == 'kakao'){
		$.ajax({
			url: "/book/kakao/kakaoPay", 
			method: 'GET',
			dataType: "json",
			data: {
				rcPoint:rcPoint
			}
		}).done(function( data ) {
			//alert(data.result);
			window.open(data.result);
			detailPoint();
			listRecharge(1);
		})
	    .fail( function( textStatus ) {
	        alert( "Request failed: " + textStatus );
	    });
	} else if(rcMethod == 'card') {
		//alert("신용카드 결제는 도입 예정입니다.");
		$.ajax({
			url: "/book/point/create", 
			method: 'GET',
			dataType: "json",
			data: {
				rcPoint:rcPoint
			}
		}).done(function( data ) {		
			setPoint(data.totalPoint);
			
			alert(rcPoint + "포인트 충전 완료");
			closeRechargeCreatePopup();
			
			location.reload();
		})
	    .fail( function( textStatus ) {
	        alert( "Request failed: " + textStatus );
	    });
	} else if(rcMethod == 'deposit') {
		alert("무통장 입금은 도입 예정입니다.");		
	}
	
}


function setPoint(totalPoint){
	$.ajax({
		url: "/book/point/update", 
		method: 'GET',
		dataType: "json",
		data: {
			totalPoint:totalPoint
		}
	}).done(function( data ) {
		if(data.result == 'success'){
			
		} else{
			alert("로그인 후 이용이 가능합니다.");
		}
	})
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}