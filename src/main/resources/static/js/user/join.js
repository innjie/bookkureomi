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

    //csrf
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    // id 중복확인하고싶은데 post ajax처리가안돼서 일단 보류
    if(!checkId(id)) return false;
    // if(!overlapId(id, header, token)) return false;
    if(!checkName(name)) return false;
    if(!checkPwLen(pw)) return false;
    if(!checkConfirmPw(pw, confirmPw)) return false;
    if(!checkNameNull(name)) return false;
    if(!checkPhoneNum(phone)) return false;
    if(!checkPhoneLen(phone)) return false;
    if(!checkGenre(genreArray)) return false;



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

        }
    }).fail(function(textStatus) {
        alert("페이지 오류: " + textStatus);
    });

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
// function overlapId(id, header, token) {
//     $.ajax({
//         url:"/book/user/overlapId",
//         type: 'POST',
//         dataType: "json",
//         data: {
//             id:id,
//         },
//         beforeSend : function(xhr)
//         {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
//             xhr.setRequestHeader(header, token);
//         },
//         success:function(data){
//             alert(data);
//             if(data){//사용 가능한 아이디 라면
//                 $("#overlapErr").hide();
//                 // 성공한 상태로 바꾸는 함수 호출
//                 alert(data);
//                 return true;
//
//             }else{//사용 가능한 아이디가 아니라면
//                 $("#overlapErr").show();
//                 return false;
//             }
//         }
//     });
// }
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
function checkNameNull(name) {
    if(name == "") {
        $("#checkNameNull").show();
        return false;
    } else {
        $("#checkNameNull").hide();
        return true;
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
    if(genreArray.length != 3) {
        $("#overChecked").show();
        return false;
    } else {
        $("#overChecked").hide();
        return true;
    }
}
