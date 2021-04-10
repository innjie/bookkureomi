function checkForm() {
    var id = $("#id").val();
    var pw = $("#pw").val();
    var confirmPw = $("#confirmPw").val();
    var phone = $("#phone").val();
    var genreArray = new Array();

    $('input:checkbox[name=genre]:checked').each(function() {
        genreArray.push(this.value);
    });

    // id 중복확인하고싶은데 post ajax처리가안돼서 일단 보류
    checkPwLen(pw);
    checkConfirmPw(pw, confirmPw);
    checkPhoneNum(phone);
    checkGenre(genreArray);
}
function checkPwLen(pw) {
    var reg=/^.{8,}$/;

    if(reg.test(pw)){
        $("#passwordLen").hide();

    }else{
        $("#passwordLen").show();
    }
}
function checkConfirmPw(pw, confirmPw) {
    if(pw == confirmPw) {
        $("#notSameError").hide();
    } else {
        $("#notSameError").show();
    }
}
function checkPhoneNum(phone) {
    var reg = /^[0-9]*$/;
    if(reg.test(phone)) {
        $("#phoneFormatError").hide();
    } else  {
        $("#phoneFormatError").show();
    }
}
function checkGenre(genreArray) {
    alert(genreArray.length);
    if(genreArray.length > 3) {
        $("#overChecked").show();
    } else {
        $("#overChecked").hide();
    }
}