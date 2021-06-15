$(document).ready(function () {
    if (($(location).attr('href').split('/book')[1]).includes('/mypage/auction/page')) {
        listAuction();
    }
});

function listAuction() {
    $.ajax({
        url: "/book/mypage/auction/list",
        method: 'GET',
        dataType: "json",
        data: {}
    }).done(function (data) {
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
        } else {
            result = "<p class=\"find-nothing\">결과가 없습니다.</p>";
        }

        $('#result').append(result);
    })
        .fail(function (textStatus) {
            alert("Request failed: " + textStatus);
        });
}

