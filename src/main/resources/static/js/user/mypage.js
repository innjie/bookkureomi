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
function updateInfo() {
    if(checkForm() == false) {
        return;
    }
    //csrf
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url:"/book/user/join",
        type: 'POST',
        dataType: "json",
        data: {
            id:id,
            pw:pw,
            name:name,
            phone:phone,
            genreArray:genreArray
        },
        beforeSend : function(xhr)
        {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
            xhr.setRequestHeader(header, token);
        }
    }).done(function(data) {
        if(data.result == 'success') {
            window.alert("회원가입 완료");
            window.location = "/book/mypage/page";
        }
    }).fail(function(textStatus) {
        alert("페이지 오류: " + textStatus);
    });
}
function checkForm() {
    var id = $("#id").val();
    var pw = $("#pw").val();
    var name = $("#name").val();
    var confirmPw = $("#confirmPw").val();
    var phone = $("#phone1").val()+$("#phone2").val()+$("#phone3").val();
    var genreArray = new Array();

    $('input:checkbox[name=genre]:checked').each(function() {
        genreArray.push(this.value);
    });

    if(!overlapIdCheck()) {
        $("#id").focus();
        return false;
    }
    if(!pwCheck) {
        $("#pw").focus();
        return false;
    }
    if(!cpwCheck) {
        $("#confirmPw").focus();
        return false;
    }
    if(!nameCheck) {
        $("#name").focus();
        return false;
    }
    if(!phoneCheck) {
        $("#phone").focus();
        return false;
    }
    if(!checkGenre(genreArray)) return false;
}