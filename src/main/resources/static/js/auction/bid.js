var option = '';
$(document).ready(function () {
    if (($(location).attr('href').split('/book')[1]).includes('/auction/page')) {
        listAuction(nowPageNo);
    }

});

function createBidForm() {
    $("#pop-mask-order-create").css("display","block");
    $("#pop-mask-order-create").css("background-color","rgba( 0, 0, 0, 0 )");
    $("#pop-order-create").css({
        "top": (window.screen.height / 2) - ($("#pop-order-create").outerHeight() / 2)-50+"px",
        "left": (window.screen.width / 2) - ($("#pop-order-create").outerWidth() / 2)+"px"
    });

    setDefaultOrder();
    $("#pop-order-create").css("display", "block");

    $("#orderTitle").val($("#viewTitle").val());
    $("#orderPrice").val($("#viewSalePrice").val());

    var offset = $("#pop-order-create").offset().top;
    $("html").animate({scrollTop:offset},400);

}