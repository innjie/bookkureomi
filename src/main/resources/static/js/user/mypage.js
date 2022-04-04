var pwCheck = false;
var cpwCheck = false;
var nameCheck = false;
var phoneCheck = false;
$(document).ready(function() {
    getUserInfo();
    checkValue();
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
function updateInfo() {
    var pw = $("#pw").val();
    var confirmPw = $("#confirmPw").val();
    var phone = $("#phone1").val()+$("#phone2").val()+$("#phone3").val();
    var genreArray = new Array();

    $('input:checkbox[name=genre]:checked').each(function() {
        genreArray.push(this.value);
    });
    if(!pwCheck) {
        $("#pw").focus();
        return false;
    }
    if(!cpwCheck) {
        $("#confirmPw").focus();
        return false;
    }
    if(!phoneCheck) {
        $("#phone").focus();
        return false;
    }
    if(!checkGenre(genreArray)) return false;

    //csrf
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url:"/book/user/update",
        type: 'POST',
        dataType: "json",
        data: {
            pw:pw,
            phone:phone,
            genreArray:genreArray
        },
        beforeSend : function(xhr)
        {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
            xhr.setRequestHeader(header, token);
        }
    }).done(function(data) {
        if(data.result == 'success') {
            window.alert("정보 수정이 완료되었습니다.\n 다시 로그인해주세요.");
            window.location = "/book/user/login";
        }
    }).fail(function(textStatus) {
        alert("페이지 오류: " + textStatus);
    });
}
