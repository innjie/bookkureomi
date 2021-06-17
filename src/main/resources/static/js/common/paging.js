
$(document).ready(function(){
	//if(window.location.href != document.referrer){
	//performance.navigation.type - 1:reload / 0:new
	//if(performance.navigation.type != 1){
		nowPageNo = 1;
	//}
});

function paging(data){
	var start = 1;
	var end = 1;
	
	//set
	if(data.pageCnt <= 11){
		start = 1;
		end = data.pageCnt;
	} else if(data.pageCnt > 11 && nowPageNo <= 6){
		start = 1;
		end = 11;
	} else if(data.pageCnt > 11 && nowPageNo >= data.pageCnt - 5){
		start = data.pageCnt - 10;
		end = data.pageCnt;
	} else{
		start = nowPageNo - 5;
		end = nowPageNo + 5;
	}
	
	var page = '';
	
	if(nowPageNo == 1){
		page += "<a class=\"paging-num\"><<</a>";
		page += "<a class=\"paging-num\"><</a>";
	}else{
		page += "<a class=\"paging-num\" onclick=\"listSale("+1+")\"><<</a>";
		page += "<a class=\"paging-num\" onclick=\"listSale("+(nowPageNo-1)+")\"><</a>";
	}
	
	for(var i=start; i <= end; i++){
		if(nowPageNo == i){
			page += "<a class=\"paging-num paging-nowpage\" onclick=\"listSale("+i+")\">"+ i + "</a>";
		} else{
			page += "<a class=\"paging-num\" onclick=\"listSale("+i+")\">"+ i + "</a>";
		}
	}
	
	if(nowPageNo == data.pageCnt){
		page += "<a class=\"paging-num\">></a>";
		page += "<a class=\"paging-num\">>></a>";
	}else{
		page += "<a class=\"paging-num\" onclick=\"listSale("+(nowPageNo+1)+")\">></a>";
		page += "<a class=\"paging-num\" onclick=\"listSale("+data.pageCnt+")\">>></a>";
	}
	
	$("#paging").html(page);
}