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
            createReviewForm();
        }
    })
}
function insertReviewProcess() {
    var orderNo = $("#insertOrderNo").val();
    var score = $("#insertScore").val();
    var content = $("#insertContent").val();

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
        }
    }).done(function(data, textStatus) {
        if(data.result == "success") {
            alert("리뷰 등록에 성공하였습니다.");
            window.location.href = "/book/review/list";
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