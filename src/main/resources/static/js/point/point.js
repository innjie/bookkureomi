$(document).ready(function(){
	detailPoint();
	listRecharge();
});

//판매 중고 서적 나열
function listRecharge(){
	$.ajax({
		url: "/book/point/list", 
		method: 'GET',
	    dataType: "json"
	}).done(function( data ) {
		var result";
		var recharge;
		
		if(data.saleList.length > 0) {
			result = "<ul>";
/*			result = 
				//	"<table class=\"table-list\">"; //style=\"width: 100%;\"
					"<table class=\"table-list\" style=\"width: 100%;\">"
					+ "<colgroup><col width=\"10%\" /><col width=\"15%\" /><col width=\"15%\" /><col width=\"15%\" /><col width=\"15%\" /><col width=\"15%\" /></colgroup><tbody>"
					+ "<thead><tr><th>No.</th><th>충전 포인트</th><th>사용 포인트</th><th>충전/사용 날짜</th><th>충전 방법</th><th>총 포인트</th></tr></thead>";
*/			
			
			for(var i=0; i<data.rechargeList.length; i++){
				recharge = "<li><h3>" + data.rechargeList[i].rechargeNo + "</h3></li>"
					//	+"<li>" + data.rechargeList[i].user.name + "</li>"
						+"<li>" + data.rechargeList[i].rcPoint + "</li>"
						+"<li>" + data.rechargeList[i].rcDate + "</li>"
						+"<li>" + data.rechargeList[i].rcMethod + "</li>"
						+"<li>" + data.rechargeList[i].rcType + "</li>"
						+"<li>" + data.rechargeList[i].totalPoint + "</li>";
				result += recharge;
			}
			
			result += "</ul>";
			//result += "</tbody></table>";
		} else {
			result = "<p class=\"find-nothing\">검색결과가 없습니다.</p>";
		}
		
		$('#result').append(result);
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}

//판매 중고서적 상세보기
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
