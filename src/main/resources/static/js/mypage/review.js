$(document).ready(function () {
    if (($(location).attr('href').split('/book')[1]).includes('/review/page')) {
        listReview(nowPageNo);
    }
});

function listReview(pageNo) {
    nowPageNo = pageNo;

    $.ajax({
        url: "/book/review/list",
        method: 'GET',
        dataType: "json",
        data: {
            pageNo: pageNo
        }
    }).done(function (data) {
        window.scrollTo(0, 0);
        $("#reviewCnt").text(data.reviewCnt);
        paging(data, 'listReview');
        var reviewCnt = data.reviewCnt;

        var result = "";
        $('#result')[0].innerHTML = "";

        if (data.reviewList.length > 0) {
            result = "<table class=\"table-list\">"
                + "<colgroup><col width=\"10%\" /><col width=\"20%\" /><col width=\"60%\" /><col width='10%'/></colgroup>"
                + "<thead><tr><th class=\"table-header\">No.</th>"
                + "<th class=\"table-header\">주문 번호</th>"
                + "<th class=\"table-header\">내용</th>"
                + "<th class='table-header'>상세보기</th>"
                + "</tr></thead><tbody>";

            for (var i = 0; i < data.reviewList.length; i++) {
                result += "<tr>"
                    + "<td class='table-text'>" + ((reviewCnt--) - (pageNo - 1) * 10) + "</td>";
                result += "<td class='table-text'>" + data.reviewList[i].orderNo + "</td>";
                result += "<td class='table-text'>" + data.reviewList[i].reviewText + "</td>"
                result += "<td class='table-text'><input type='button' class='insert-btn' onclick='reviewDetail(" + data.reviewList[i].orderNo + ")' value='상세보기'></td></tr>"
            }
            result += "</tbody></table>";

        } else {
            result = "<p class=\"find-nothing\">주문 내역이 없습니다.</p>";
        }

        $('#result').append(result);
    })
        .fail(function (textStatus) {
            alert("Request failed: " + textStatus);
        });
}

function reviewDetail(orderNo) {
    $.ajax({
        url: "/book/review/detail",
        method: "GET",
        dataType: "json",
        data: {
            orderNo : orderNo
        }
    }).done(function(data) {

    })
}

function insertReview(orderNo) {
    closeOrderDetailPopup();

    // already exist
    $.ajax({
        url: "/book/review/detail",
        method: "GET",
        dataType: "json",
        data: {
            orderNo: orderNo
        }
    }).done(function (data, textStatus) {
        if (data.review != null) {
            alert("이미 등록되었습니다.");
        } else {
            $("#pop-mask-review-create").css("display", "block");
            $("#pop-mask-review-create").css("overflow", "auto");
            $("body").css("overflow", "hidden");
            $("#pop-review-create").css({
                "top": (window.screen.height / 2) - ($("#pop-order-detail").outerHeight() / 2) - 50 + "px",
                "left": (window.screen.width / 2) - ($("#pop-order-detail").outerWidth() / 2) + "px"
            });
            $("#insertOrderNo").val(orderNo);
            createReviewForm();
        }
    })
}

function insertReviewProcess() {
    var orderNo = $("#insertOrderNo").val();
    var score = parseInt($("#insertScore").val());
    var reviewtext = $("#insertReviewText").val();

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    alert(content);
    // check score
    if (!checkScore(score)) {
        alert("점수는 0~10 사이여야 합니다");
        return;
    }
    // check textarea
    if (!checkContent(reviewtext)) {
        alert("내용은 250자 이내여야 합니다.");
        return;
    }

    // ajax insert - post
    $.ajax({
        url: "/book/review/insert",
        method: "POST",
        dataType: "json",
        data: {
            orderNo: orderNo,
            score: score,
            reviewtext: reviewtext
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        }
    }).done(function (data, textStatus) {
        if (data.result == "success") {
            alert("리뷰 등록에 성공하였습니다.");
            window.location.href = "/book/review/page";
        } else if (data.result == "fail") {
            alert(data.message);
            return;
        }


    }).fail(function (textStatus) {
        alert("Request failed: " + textStatus);
    })
}

function closeReviewForm() {
    $("#pop-review-create").css("display", "none");
    $("#pop-mask-review-create").css("display", "none");
    $("#pop-mask-review-create").css("overflow", "hidden");
    $("body").css("overflow", "auto");

    $(".pop").css("height", "800px");
    $("#pop-style1-create").css("height", "500px");
}

function createReviewForm() {
    /*  closeOrderPopup();
      setDefaultOrder();*/
    $("#pop-review-create").css("display", "block");

    $("#pop-mask-review-create").css("display", "block");
    $("#pop-mask-review-create").css("background-color", "rgba( 0, 0, 0, 0 )");
    $("#pop-review-create").css({
        "top": (window.screen.height / 2) - ($("#pop-review-create").outerHeight() / 2) - 50 + "px",
        "left": (window.screen.width / 2) - ($("#pop-review-create").outerWidth() / 2) + "px"
    });

    $("#pop-review-create").css("display", "block");

    var offset = $("#pop-review-create").offset().top;
    $("html").animate({scrollTop: offset}, 400);
}

function checkScore(score) {
    if (score < 0 || score > 10) {
        return false;
    }
    return true;
}

function checkContent(content) {
    if (content.length > 250) {
        return false;
    }
    return true;
}