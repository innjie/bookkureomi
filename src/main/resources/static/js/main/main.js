$(document).ready(function() {
   getSession();
});
function getSession() {
    $.ajax({
        url:"/book/user/session",
        method: 'GET',
        dataType: "json",
        data: {
        }
    }).done(function(data) {

    })
}