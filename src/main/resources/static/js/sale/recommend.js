$(document).ready(function () {
    if (($(location).attr('href').split('/book')[1]).includes('/recommend/page')) {
        getRecommendByUser();
    }
});

//추천 가져오기 (사용자맞춤)
function getRecommendByUser() {
    $.ajax({
        url: "/book/recommend/user",
        method : "GET",
        dataType: "json",
        data: {
            
        }
    }).done(function(data) {
        window.scrollTo(0,0);
        $('#result')[0].innerHTML = '';


        if(data.saleList.length > 0) {
            var result = "<table class=\"table-list\">"
                + "<colgroup><col width=\"33.333333%\" /><col width=\"33.333333%\" /><col width=\"33.333333%\" /></colgroup><tbody>";

            for(var i=0; i<data.saleList.length; i++){
                if(i%3 == 0){
                    result += "</tr><tr>";
                } else if(i == 0){
                    result += "<tr>";
                }

                result += "<td><ul class=\"list-style\">";

                //image
                /*if(data.saleList[i].imageList.length > 0){
                    result += "<li class=\"table-list-image\">"
                        + "<img src=\"" + "/book/image?path="+encodeURI(data.saleList[i].imageList[0].filePath) + "\" class=\"img-fit\"/>"
                        + "</li>";
                } else{
                    result += "<li class=\"table-list-image\">"
                        + "<img src=\"/images/sale/0.png\" class=\"img-fit\"/>"
                        + "</li>";
                }*/

                //info
                result += "<li class=\"table-list-content\"><ul class=\"table-list-content-style\">"
                    + "<li class=\"table-list-content-list-style\"><strong>" + data.saleList[i].title + "</strong></li>"
                    + "<li class=\"table-list-content-list-style\">" + data.saleList[i].author + " / " + data.saleList[i].publisher + "</li>"
                    + "<li class=\"table-list-content-list-style\"> \\" + data.saleList[i].costPrice + " -> \\" + data.saleList[i].salePrice + "</li>"
                    + "<li class=\"table-list-content-list-style\">" + data.saleList[i].state + "</li>"
                    + "<li class=\"table-list-content-btn-style\"><button type=\"button\" id=\"btnSale"+ data.saleList[i].saleNo +"\" class=\"view-btn\" onClick=\"detailSale("+ data.saleList[i].saleNo +")\">상세보기</button><li>"
                    + "</ul></li>";

                result += "</ul></td>";

                if (i==0 && data.saleList.length==1){
                    result += "<td></td><td></td>";
                } else if (i==1 && data.saleList.length==2) {
                    result += "<td></td>";
                }

                if(i == data.saleList.length-1){
                    result += "</tr>";
                }
            }
            result += "</tbody></table>";
        } else {
            result = "<p class=\"find-nothing\">결과가 없습니다.</p>";
        }

        $('#result').append(result);
    })
}
//추천 가져오기 (판매순)
function getRecommendBySale() {
    $.ajax( {
        url: "/book/recommend/sale",
        method: "GET",
        dataType: "json",
        data: {

        }
    }).done(function(data) {

    })
}