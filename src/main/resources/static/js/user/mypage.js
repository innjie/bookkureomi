$(document).ready(function() {
    getUserInfo();
});
function getUserInfo() {
    $.ajax({
        url: "/book/mypage/info",
        method: 'GET',
        dataType: "json",
    }).done(function (data) {

    }).fail(function(textStatus) {
        alert("Request failed: " + textStatus);
    });
}