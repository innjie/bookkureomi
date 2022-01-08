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

    setDefaultBid();
    $("#pop-order-create").css("display", "block");

    $("#orderTitle").val($("#viewTitle").val());
    $("#orderPrice").val($("#viewBidPrice").val());

    var offset = $("#pop-order-create").offset().top;
    $("html").animate({scrollTop:offset},400);

}
function setDefaultBid() {
    $("#orderPAddress").val('');
    $("#orderRName").val('');
    $("#orderRPhone").val('');
    $("#orderRAddress").val('');

    //주소 가져오기
    $("#findAddress").html("<option value=\"\">-</option>");
    $.ajax({
        url: "/book/order/address/list",
        method: 'GET',
        dataType: "json",
        data: {
        }
    }).done(function( data ) {
        for(var i = 0; i < data.addressList.length; i++){
            $("#findAddress").append("<option value=\""+data.addressList[i].addrNo+"\">"+data.addressList[i].aname+" : "+data.addressList[i].addr+"</option>");
        }
    }).fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}