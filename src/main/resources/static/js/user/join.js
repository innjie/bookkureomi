function checkForm() {
    var id = $("#id").val();
    var pw = $("#pw").val();
    var name = $("#name").val();
    var confirmPw = $("#confirmPw").val();
    var phone = $("#phone").val();
    var genreArray = new Array();

    $('input:checkbox[name=genre]:checked').each(function() {
        genreArray.push(this.value);
    });

    // id 중복확인하고싶은데 post ajax처리가안돼서 일단 보류
    if(!checkId(id)) return false;
    if(!checkName(name)) return false;
    if(!checkPwLen(pw)) return false;
    if(!checkConfirmPw(pw, confirmPw)) return false;
    if(!checkPhoneNum(phone)) return false;
    if(!checkPhoneLen(phone)) return false;
    if(!checkGenre(genreArray)) return false;

    $("#form").submit();
}
function checkId(id) {
    if(id.length == 0) {
        $("#checkIdNull").show();
        return false;
    } else {
        $("#checkIdNull").hide();
        return true;
    }
}
function checkName(name) {
    if(name.length == 0) {
        $("#checkNameNull").show();
        return false;
    } else {
        $("#checkNameNull").show();
        return true;
    }
}
function checkPwLen(pw) {
    var reg=/^.{8,}$/;

    if(reg.test(pw)){
        $("#passwordLen").hide();
        return true;
    }else{
        $("#passwordLen").show();
        return false;
    }
}
function checkConfirmPw(pw, confirmPw) {
    if(pw == confirmPw) {
        $("#notSameError").hide();
        return true;
    } else {
        $("#notSameError").show();
        return false;
    }
}
function checkPhoneNum(phone) {
    var reg = /^[0-9]*$/;
    if(reg.test(phone)) {
        $("#phoneFormatError").hide();
        return true;
    } else  {
        $("#phoneFormatError").show();
        return false;
    }
}
function checkPhoneLen(phone) {
    if(phone.length != 11) {
        $("#phoneLengthError").show();
        return false;
    } else {
        $("#phoneLengthError").hide();
        return true;
    }
}
function checkGenre(genreArray) {
    if(genreArray.length > 3) {
        $("#overChecked").show();
        return false;
    } else {
        $("#overChecked").hide();
        return true;
    }
}
