function checkGenre(genreArray) {
    if(genreArray.length != 3) {
        $("#overChecked").css("display","inline");
        return false;
    } else {
        $("#overChecked").css("display","none");
        return true;
    }
}
function checkId(id) {
    if(id.length == 0 || id == "" || id == null) {
        $("#overlapCheck").css("display","inline");
        $("#overlapCheck").text("Please input ID ");
        $("#isOverlapCheck").val('wrong');
        return false;
    } else {
        return true;
    }
}
function checkPwLen(pw) {
    var reg=/^.{8,}$/;

    if(reg.test(pw)){
        $("#passwordLen").css("display","none");
        return true;
    }else{
        $("#passwordLen").css("display","inline");
        return false;
    }
}
function checkConfirmPw(pw, confirmPw) {
    if(pw == confirmPw) {
        $("#notSameError").css("display","none");
        return true;
    } else {
        $("#notSameError").css("display","inline");
        return false;
    }
}
function checkName(name) {
    if(name.length == 0 || name == "" || name == null) {
        $("#checkNameNull").css("display","inline");
        return false;
    } else {
        $("#checkNameNull").css("display","none");
        return true;
    }
}
function checkPhoneNum(phone) {
    var reg = /^[0-9]{11}$/;

    if(!reg.test(phone)) {
        $("#phoneFormatError").css("display","inline");
        return false;
    } else  {
        $("#phoneFormatError").css("display","none");
        return true;
    }
}
function overlapIdCheck() {
    if($("#isOverlapCheck").val() == 'checked'){
        return true;
    } else{
        if($("#isOverlapCheck").val() == 'notChecked'){
            $("#overlapCheck").css("display","inline");
            $("#overlapCheck").text("아이디 중복 확인이 필요합니다.");
        } else if($("#isOverlapCheck").val() == 'wrong'){
            $("#overlapCheck").css("display","inline");
        }

        return false;
    }
}


function overlapId() {
    var id = $("#id").val();

    //csrf
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    if(!checkId(id)) return false;

    $.ajax({
        url:"/book/user/overlapId",
        type: 'POST',
        dataType: "json",
        data: {
            id:id,
        },
        beforeSend : function(xhr)
        {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
            xhr.setRequestHeader(header, token);
        }
    }).done(function( data ) {
        if(data.result == 'success'){//사용 가능한 아이디 라면
            $("#isOverlapCheck").val('checked');
            $("#overlapCheck").css("display","inline");
            $("#overlapCheck").text("확인 완료");
            // 성공한 상태로 바꾸는 함수 호출
        }else{//사용 가능한 아이디가 아니라면
            $("#isOverlapCheck").val('wrong');

            $("#overlapCheck").css("display","inline");
            $("#overlapCheck").text("This ID is already Using");
        }
    })
}