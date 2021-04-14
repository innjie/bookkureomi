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
		var result;
		var recharge;
		
		if(data.rechargeList.length > 0) {
//			result = "<ul>";
			result =  "<table class=\"table-list\">"
					+ "<colgroup><col width=\"10%\" /><col width=\"15%\" /><col width=\"15%\" /><col width=\"15%\" /><col width=\"15%\" /><col width=\"15%\" /></colgroup>"
					+ "<thead><tr><th class=\"table-header\">No.</th><th class=\"table-header\">충전/사용 날짜</th><th class=\"table-header\">충전 포인트</th><th class=\"table-header\">사용 포인트</th><th class=\"table-header\">충전 방법</th><th class=\"table-header\">총 포인트</th></tr></thead><tbody>";
			
			
			for(var i=0; i<data.rechargeList.length; i++){
				result += "<tr>"
							+ "<td class=\"table-text\">" + (i+1) + "</td>"
							+ "<td class=\"table-text\">" + data.rechargeList[i].rcDate + "</td>";
				
				//충전/사용 구분
				if(data.rechargeList[i].rcType == "충전"){
					result += "<td class=\"table-text\">" + data.rechargeList[i].rcPoint + "</td>"
							+ "<td class=\"table-text\"></td>";
				} else if(data.rechargeList[i].rcType == "사용") {
					result += "<td class=\"table-text\"></td>"
							+ "<td class=\"table-text\">" + data.rechargeList[i].rcPoint + "</td>";
				}

				
				result += "<td class=\"table-text\">" + data.rechargeList[i].rcMethod + "</td>"
							+ "<td class=\"table-text\">" + data.rechargeList[i].totalPoint + "</td>";
				
				
	/*			recharge = "<li><h3>" + data.rechargeList[i].rechargeNo + "</h3></li>"
					//	+"<li>" + data.rechargeList[i].user.name + "</li>"
						+"<li>" + data.rechargeList[i].rcPoint + "</li>"
						+"<li>" + data.rechargeList[i].rcDate + "</li>"
						+"<li>" + data.rechargeList[i].rcMethod + "</li>"
						+"<li>" + data.rechargeList[i].rcType + "</li>"
						+"<li>" + data.rechargeList[i].totalPoint + "</li>";
				result += recharge;*/
				
				result += "</tr>";
			}
			
			//result += "</ul>";
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


function closeCreatePopup() {
	$("#pop-recharge-create").css("display", "none");
}

function setDefault() {
	$("#rechargeType").val('kakao');
	$("#rechargePrice").val('1000');
}

//충전
function createRechargeForm(){
	setDefault();
	$("#pop-recharge-create").css("display", "block");
}
function createRecharge(){
	var rechargeType = $("#rechargeType").val();
	var rechargePrice = $("#rechargePrice").val();
	alert(rechargeType +" "+ rechargePrice);
	
}