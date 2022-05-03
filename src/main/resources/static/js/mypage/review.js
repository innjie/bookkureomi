$(document).ready(function(){
    if(($(location).attr('href').split('/book')[1]).includes('/review/page')){
        listReview();
    }
});
function listReview() {
    nowPageNo = pageNo;

    $.ajax({
        url: "/book/review/list",
        method: 'GET',
        dataType: "json",
        data: {
            pageNo: pageNo
        }
    }).done(function( data ) {
        window.scrollTo(0,0);
        $("#orderCnt").text(data.orderCnt);
        paging(data, 'listOrder');
        var orderCnt = data.orderCnt

        $('#result')[0].innerHTML = '';

        if(data.orderList.length > 0) {
            result =  "<table class=\"table-list\">"
                + "<colgroup><col width=\"10%\" /><col width=\"20%\" /><col width=\"20%\" /><col width=\"20%\" /><col width=\"20%\" /><col width=\"10%\" /></colgroup>"
                + "<thead><tr><th class=\"table-header\">No.</th>"
                + "<th class=\"table-header\">주문 번호</th>"
                + "<th class=\"table-header\">주문정보</th>"
                + "<th class=\"table-header\">가격</th>"
                + "<th class=\"table-header\">주문 일자</th>"
                + "<th class='table-header'>리뷰 쓰기</th>"
                + "</tr></thead><tbody>";

            for(var i=0; i<data.orderList.length; i++){
                result += "<tr>"
                    + "<td class=\"table-text\">" + ((orderCnt--) - (pageNo-1)*10) + "</td>";

                if(type == 'sale') {
                    result += "<td class=\"table-text\"><a onclick=\"saleOrder("+data.orderList[i].orderNo+");\">" + data.orderList[i].orderNo  + "</a></td>"
                } else {

                    result += "<td class=\"table-text\"><a onclick=\"auctionOrder("+data.orderList[i].orderNo+");\">" + data.orderList[i].orderNo  + "</a></td>"
                }
                result += "<td class=\"table-text\">" + data.orderList[i].info + "</td>"
                    + "<td class=\"table-text\">" + data.orderList[i].total + "</td>";

                result += "<td class=\"table-text\">" + data.orderList[i].orderDate + "</td>";
                result += "<td class='table-text'><input type='button' class='insert-btn' onclick='insertReview(" + data.orderList[i].orderNo + ")' value='리뷰쓰기'></td></tr>"
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
function insertReview(orderNo) {
    closeOrderDetailPopup();

    // already exist
    $.ajax({
        url: "/book/review/detail",
        method: "GET",
        dataType: "json",
        data: {
            orderNo : orderNo
        }
    }).done (function(data, textStatus) {
        if(data.review != null) {
            alert("이미 등록되었습니다.");
        } else {
            $("#pop-mask-review-create").css("display","block");
            $("#pop-mask-review-create").css("overflow","auto");
            $("body").css("overflow","hidden");
            $("#pop-review-create").css({
                "top": (window.screen.height / 2) - ($("#pop-order-detail").outerHeight() / 2)-50+"px",
                "left": (window.screen.width / 2) - ($("#pop-order-detail").outerWidth() / 2)+"px"
            });
            $("#insertOrderNo").val(orderNo);
            createReviewForm();
        }
    })
}
function insertReviewProcess() {
    var orderNo = $("#insertOrderNo").val();
    var score = parseInt($("#insertScore").val());
    var content = $("#insertContent").val();

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    alert(content);
    // check score
    if(!checkScore(score)) {
        alert("점수는 0~10 사이여야 합니다");
        return;
    }
    // check textarea
    if(!checkContent(content)) {
        alert("내용은 250자 이내여야 합니다.");
        return;
    }

    // ajax insert - post
    $.ajax({
        url: "/book/review/insert",
        method : "POST",
        dataType: "json",
        data: {
            orderNo : orderNo,
            score : score,
            content : content
        },
        beforeSend : function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    }).done(function(data, textStatus) {
        if(data.result == "success") {
            alert("리뷰 등록에 성공하였습니다.");
            window.location.href = "/book/review/page";
        } else if (data.result == "fail") {
            alert(data.message);
            return;
        }


    }).fail(function (textStatus) {
        alert( "Request failed: " + textStatus );
    })
}
function closeReviewForm() {
    $("#pop-review-create").css("display", "none");
    $("#pop-mask-review-create").css("display","none");
    $("#pop-mask-review-create").css("overflow","hidden");
    $("body").css("overflow","auto");

    $(".pop").css("height", "800px");
    $("#pop-style1-create").css("height", "500px");
}
function createReviewForm() {
  /*  closeOrderPopup();
    setDefaultOrder();*/
    $("#pop-review-create").css("display", "block");

    $("#pop-mask-review-create").css("display","block");
    $("#pop-mask-review-create").css("background-color","rgba( 0, 0, 0, 0 )");
    $("#pop-review-create").css({
        "top": (window.screen.height / 2) - ($("#pop-review-create").outerHeight() / 2)-50+"px",
        "left": (window.screen.width / 2) - ($("#pop-review-create").outerWidth() / 2)+"px"
    });

    $("#pop-review-create").css("display", "block");

    var offset = $("#pop-review-create").offset().top;
    $("html").animate({scrollTop: offset}, 400);
}
function checkScore(score) {
    if(score < 0 || score > 10) {
        return false;
    }
    return true;
}
function checkContent(content) {
    if(content.length > 250) {
        return false;
    }
    return true;
}