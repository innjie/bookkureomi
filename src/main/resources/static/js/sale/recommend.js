$(document).ready(function () {
    if (($(location).attr('href').split('/book')[1]).includes('/')) {
        getRecommendBySale();
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