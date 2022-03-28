$(document).ready(function() {
    getUserInfo();
});
function getUserInfo() {
    $.ajax({
        url: "/book/mypage/info",
        method: 'GET',
        dataType: "json",
    }).done(function (data) {
        $("#id").val(data.user.id);
        $("#phone").val(data.user.phone);
        
    }).fail(function(textStatus) {
        alert("Request failed: " + textStatus);
    });
}