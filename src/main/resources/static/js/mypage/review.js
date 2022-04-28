function insertReveiew() {
    closeOrderDetailPopup();

    $("#pop-mask-review-create").css("display","block");
    $("#pop-mask-review-create").css("overflow","auto");
    $("body").css("overflow","hidden");
    $("#pop-review-create").css({
        "top": (window.screen.height / 2) - ($("#pop-order-detail").outerHeight() / 2)-50+"px",
        "left": (window.screen.width / 2) - ($("#pop-order-detail").outerWidth() / 2)+"px"
    });
    createReviewForm();
}
function insertReviewProcess() {

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