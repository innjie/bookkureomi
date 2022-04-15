var option = '';
$(document).ready(function () {
    if (($(location).attr('href').split('/book')[1]).includes('/mypage/auction/page')) {
        listAuction(nowPageNo);
    }
});

function listAuction(pageNo) {
    nowPageNo = pageNo;
    $.ajax({
        url: "/book/mypage/auction/list",
        method: 'GET',
        dataType: "json",
        data: {
            pageNo: pageNo
        }
    }).done(function (data) {
        window.scrollTo(0,0);
        $("#auctionCnt").text(data.auctionCnt);
        paging(data, 'listAuction');

        $('#result')[0].innerHTML = '';

        if (data.auctionList.length > 0) {
            var result = "<table class=\"table-list\">"
                + "<colgroup><col width=\"33.333333%\" /><col width=\"33.333333%\" /><col width=\"33.333333%\" /></colgroup><tbody>";

            for (var i = 0; i < data.auctionList.length; i++) {
                if (i % 3 == 0) {
                    result += "</tr><tr>";
                } else if (i == 0) {
                    result += "<tr>";
                }

                result += "<td><ul class=\"list-style\">";

                //image
                result += "<li class=\"table-list-image\">"
                    + "<img src=\"" + data.auctionList[i].image + "\" class=\"img-fit\"/>"
                    + "</li>";

                //info
                var endDate = data.auctionList[i].endDate.split("T");

                result += "<li class=\"table-list-content\"><ul class=\"table-list-content-style\">"
                    + "<li class=\"table-list-content-list-style\"><strong>" + data.auctionList[i].title + "</strong></li>"
                    + "<li class=\"table-list-content-list-style\">" + endDate[0] + " / " + data.auctionList[i].publisher + "</li>"
                    + "<li class=\"table-list-content-list-style\"> 입찰가 : \\" +  data.auctionList[i].bidPrice + "</li>"
                    + "<li class=\"table-list-content-list-style\"> 즉시구매가 : \\" + data.auctionList[i].immediPrice + "</li>"
                    + "<li class=\"table-list-content-list-style\">  상태 :"+ data.auctionList[i].state + "</li>"
                    + "<li class=\"table-list-content-btn-style\"><button type=\"button\" id=\"btnSale" + data.auctionList[i].auctionNo + "\" class=\"view-btn\" onClick=\"detailAuction(" + data.auctionList[i].auctionNo +")\" class=\"view-btn\" onClick=\"detailAuction("+ data.auctionList[i].auctionNo +")\">상세보기</button><li>"
                    + "</ul></li>";

                result += "</ul></td>";

                if (i == 0 && data.auctionList.length == 1) {
                    result += "<td></td><td></td>";
                } else if (i == 1 && data.auctionList.length == 2) {
                    result += "<td></td>";
                }

                if (i == data.auctionList.length - 1) {
                    result += "</tr>";
                }
            }
            result += "</tbody></table>";
        }


        $('#result').append(result);
    })
        .fail(function (textStatus) {
            alert("Request failed: " + textStatus);
        });
}
//추가 폼 세팅
function setAuctionDefault() {
    $("#insertTitle").val('');
    $("#insertPublisher").val('');
    $("#insertEndDate").val('');
    $("#insertBidPrice").val('');
    $("#insertImmediPrice").val('');
    $("#insertRegiDate").val('');
    $("#insertGenreType").val('');
    $("#insertState").val('');
    $("#insertInfo").val('');
}


//중고 서적 판매
function createAuctionForm() {
    setAuctionDefault();
    closeAuctionDetailPopup();
    closeOrderPopup();
    setDefaultOrder();
    $("#pop-sale-create").css("display", "block");

    var offset = $("#pop-sale-create").offset().top;
    $("html").animate({scrollTop: offset}, 400);
}

function createAuction() {
    var publisher = $("#insertPublisher").val();
    var title = $("#insertTitle").val();
    var bidPrice = $("#insertBidPrice").val();
    var immediPrice = $("#insertImmediPrice").val();
    var endDate = $("#insertEndDate").val();
    var userNo = 1;
    var genreType = $("#insertGenreType").val();
    var image = '/images/sale/0.png'; //폼처리?
    var info = $("#insertInfo").val();

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");


    if(checkTitle(title)) return;
    if(checkPublisher(publisher)) return;
    if(checkBidPrice(bidPrice)) return;
    if(checkImmediPrice(immediPrice)) return;
    if(checkPrices(bidPrice, immediPrice)) return;
    if(checkGenre(genreType)) return;
    if(checkInfo(info)) return;
    if(!checkEndDate(endDate)) return;

    if (confirm("경매를 등록하시겠습니까?")) {
        $.ajax({
            url: "/book/auction/insert",
            method: 'POST',
            dataType: "json",
            data: {
                title: title,
                publisher: publisher,
                endDate: endDate,
                info: info,
                bidPrice: bidPrice,
                immediPrice: immediPrice,
                image: image,
                userNo: userNo,
                genreType: genreType
            },
            beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            }
        }).done(function (data) {
            if (data.result == 'success') {
                window.alert("중고 책을 등록하였습니다.");

                $("#pop-sale-insert").css("display", "none");

                listAuction();
                detailAuction(data.AuctionNo);
            }
        })
            .fail(function (textStatus) {
                alert("Request failed: " + textStatus);
            });
    }


}
//findAuction
function findAuction() {
    var title = $("#findTitle").val();


    $.ajax({
        url: "/book/auction/find",
        method: 'GET',
        dataType: "json",
        data: {
            title : title
        }
    }).done(function(data) {
        $('#result')[0].innerHTML = '';
        var result;

        if(data.auctionList.length > 0) {
            var result = "<table class=\"table-list\">"
                + "<colgroup><col width=\"33.333333%\" /><col width=\"33.333333%\" /><col width=\"33.333333%\" /></colgroup><tbody>";

            for (var i = 0; i < data.auctionList.length; i++) {
                if (i % 3 == 0) {
                    result += "</tr><tr>";
                } else if (i == 0) {
                    result += "<tr>";
                }

                result += "<td><ul class=\"list-style\">";

                //image
                result += "<li class=\"table-list-image\">"
                    + "<img src=\"" + data.auctionList[i].image + "\" class=\"img-fit\"/>"
                    + "</li>";

                //info
                var endDate = data.auctionList[i].endDate.split("T");



                result += "<li class=\"table-list-content\"><ul class=\"table-list-content-style\">"
                    + "<li class=\"table-list-content-list-style\"><strong>" + data.auctionList[i].title + "</strong></li>"
                    + "<li class=\"table-list-content-list-style\">" + endDate[0] + " / " + data.auctionList[i].publisher + "</li>"
                    + "<li class=\"table-list-content-list-style\"> 입찰가 : \\" +  data.auctionList[i].bidPrice + "</li>"
                    + "<li class=\"table-list-content-list-style\"> 즉시구매가 : \\" + data.auctionList[i].immediPrice + "</li>"
                    + "<li class=\"table-list-content-list-style\">  상태 :"+ data.auctionList[i].state + "</li>"
                    + "<li class=\"table-list-content-btn-style\"><button type=\"button\" id=\"btnSale" + data.auctionList[i].auctionNo + "\" class=\"view-btn\" onClick=\"detailAuction(" + data.auctionList[i].auctionNo +")\" class=\"view-btn\" onClick=\"detailAuction("+ data.auctionList[i].auctionNo +")\">상세보기</button><li>"
                    + "</ul></li>";

                result += "</ul></td>";

                if (i == 0 && data.auctionList.length == 1) {
                    result += "<td></td><td></td>";
                } else if (i == 1 && data.auctionList.length == 2) {
                    result += "<td></td>";
                }

                if (i == data.auctionList.length - 1) {
                    result += "</tr>";
                }
            }
            result += "</tbody></table>";
        } else {
            result = "<p class=\"find-nothing\">결과가 없습니다.</p>";
        }

        $('#result').append(result);
    })
        .fail(function (textStatus) {
            alert("Request failed: " + textStatus);
        });

}
