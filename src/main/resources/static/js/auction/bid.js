var option = '';
$(document).ready(function () {
    if (($(location).attr('href').split('/book')[1]).includes('/auction/page')) {
        listAuction(nowPageNo);
    }

});
function createImmediateForm() {
    $("#pop-mask-immediate-create").css("display","block");
    $("#pop-mask-immediate-create").css("background-color","rgba( 0, 0, 0, 0 )");
    $("#pop-immediate-create").css({
        "top": (window.screen.height / 2) - ($("#pop-immediate-create").outerHeight() / 2)-50+"px",
        "left": (window.screen.width / 2) - ($("#pop-immediate-create").outerWidth() / 2)+"px"
    });

    setDefaultImmediate();
    $("#pop-immediate-create").css("display", "block");

    $("#immediateTitle").val($("#viewTitle").val());
    $("#immediatePrice").val($("#viewImmediPrice").val());

    var offset = $("#pop-order-create").offset().top;
    $("html").animate({scrollTop:offset},400);
}
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
    $("#orderCurrBidPrice").val($("#viewBidPrice").val());

    var offset = $("#pop-order-create").offset().top;
    $("html").animate({scrollTop:offset},400);

}
function setDefaultImmediate() {
    $("#immediatePAddress").val('');
    $("#immediateRName").val('');
    $("#immediateRPhone").val('');
    $("#immediateRAddress").val('');

    //주소 가져오기
    $("#findAddress").html("<option value=\"\">-</option>");
    $.ajax({
        url: "/book/order/address/list",
        method: 'GET',
        dataType: "json",
        data: {
        }
    }).done(function( data ) {
        if(data.addressList != null) {
            for(var i = 0; i < data.addressList.length; i++){
                $("#findAddress").append("<option value=\""+data.addressList[i].addrNo+"\">"+data.addressList[i].aname+" : "+data.addressList[i].addr+"</option>");
            }
        }

    }).fail( function( textStatus ) {
        alert( "등록된 주소가 없습니다." );
    });
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
        if(data.addressList != null) {
            for(var i = 0; i < data.addressList.length; i++){
                $("#findAddress").append("<option value=\""+data.addressList[i].addrNo+"\">"+data.addressList[i].aname+" : "+data.addressList[i].addr+"</option>");
            }
        }

    }).fail( function( textStatus ) {
        alert( "등록된 주소가 없습니다." );
    });
}
function orderAuction() {
    var auctionNo = $("#viewAuctionNo").val();
    var pAddress = $("#immediatePAddress").val();
    var rName = $("#immediateRName").val();
    var rPhone = $("#immediateRPhone").val();
    var rAddress = $("#immediateRAddress").val();

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    if(confirm("구매하시겠습니까?")) {
        $.ajax({
            url: "/book/immediate/insert",
            method: 'POST',
            dataType: "json",
            data: {
                auctionNo : auctionNo,
                pAddress : pAddress,
                rName : rName,
                rPhone : rPhone,
                rAddress : rAddress
            },
            beforeSend : function(xhr){   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            }
        }).done(function( data ) {
            if(data.result == 'success'){
                setPoint(data.totalPoint);

                window.alert("※주문성공※\n중고 책을 구입하였습니다.");

                window.location = "/book/auction/page";
            } else if(data.result == 'fail'){
                window.alert(data.reason);
            }
        }).fail( function( textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    }
}
function bidAuction() {
    var auctionNo = $("#viewAuctionNo").val();
    var pAddress = $("#orderPAddress").val();
    var rName = $("#orderRName").val();
    var rPhone = $("#orderRPhone").val();
    var rAddress = $("#orderRAddress").val();
    var bidPrice = $("#orderBidPrice").val();
    var currBidPrice = $("#orderCurrBidPrice").val();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    if(currBidPrice >= bidPrice) {
        alert("현재 입찰된 가격보다 높아야 합니다.");
        return;
    }

    if(confirm("입찰하시겠습니까?")) {
        $.ajax({
            url: "/book/bid/insert",
            method: 'POST',
            dataType: "json",
            data: {
                auctionNo : auctionNo,
                pAddress : pAddress,
                rName : rName,
                rPhone : rPhone,
                rAddress : rAddress,
                bidPrice : bidPrice
            },
            beforeSend : function(xhr){   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            }
        }).done(function( data ) {
            if(data.result == 'success'){
                setPoint(data.totalPoint);

                window.alert("※주문성공※\n중고 책을 입찰하였습니다.");

                window.location = "/book/auction/page";
            } else if(data.result == 'fail'){
                window.alert(data.reason);
            }
        }).fail( function( textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    }
}