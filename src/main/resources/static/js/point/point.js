$(document).ready(function(){
	detailPoint();
	listRecharge();
});

//충전/사용 내역 나열
function listRecharge(){
	$.ajax({
		url: "/book/point/list", 
		method: 'GET',
	    dataType: "json"
	}).done(function( data ) {
		$('#result')[0].innerHTML = '';
		
		var result;
		
		if(data.rechargeList.length > 0) {
			result =  "<table class=\"table-list\">"
					+ "<colgroup><col width=\"10%\" /><col width=\"15%\" /><col width=\"15%\" /><col width=\"15%\" /><col width=\"15%\" /><col width=\"15%\" /></colgroup>"
					+ "<thead><tr><th class=\"table-header\">No.</th><th class=\"table-header\">충전/사용 날짜</th><th class=\"table-header\">충전 포인트</th><th class=\"table-header\">사용 포인트</th><th class=\"table-header\">충전 방법</th><th class=\"table-header\">총 포인트</th></tr></thead><tbody>";
			
			for(var i=0; i<data.rechargeList.length; i++){
				result += "<tr>"
							+ "<td class=\"table-text\">" + ((-1 * (i+1)) + data.rechargeList.length + 1)  + "</td>"
							+ "<td class=\"table-text\">" + data.rechargeList[i].rcDate + "</td>";
				
				//충전/사용 구분
				if(data.rechargeList[i].rcType == "충전"){
					result += "<td class=\"table-text\">" + data.rechargeList[i].rcPoint + "</td>"
							+ "<td class=\"table-text\"></td>";
				} else if(data.rechargeList[i].rcType == "사용") {
					result += "<td class=\"table-text\"></td>"
							+ "<td class=\"table-text\">" + data.rechargeList[i].rcPoint + "</td>";
				}

				
				if(data.rechargeList[i].rcMethod == 'kakao') {
					result += "<td class=\"table-text\">카카오페이</td>";
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
		$("#myPoint")[0].innerHTML = data.point + $("#myPoint")[0].innerHTML;
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}


//추가 팝업 닫기
function closeCreatePopup() {
	$("#pop-recharge-create").css("display", "none");
}

//추가 폼 세팅
function setDefault() {
	$("#rechargeType").val('kakao');
	$("#rechargePrice").val('1000');
}


//충전
function createRechargeForm(){
	setDefault();
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
			listRecharge();
		})
	    .fail( function( textStatus ) {
	        alert( "Request failed: " + textStatus );
	    });
	} else if(rcMethod == 'card') {
		alert("신용카드 결제는 도입 예정입니다.");
	} else if(rcMethod == 'deposit') {
		alert("무통장 입금은 도입 예정입니다.");		
	}
	
}

