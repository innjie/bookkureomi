function login() {
    var id = $("#id").val();
    var pw = $("#pw").val();

    //csrf
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    if(!checkId(id)) return false;
    if(!checkPwLen(pw)) return false;

    $.ajax( {
        url:"/book/user/login",
        type:'POST',
        dataType: "json",
        data: {
            id : id,
            pw : pw
        },
        beforeSend:function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    });
    goMain(data.username);

}
function goMain(user) {
    $("#username").val(user);
    window.location.href("/");
}
function logout() {
    //csrf
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    alert("logout");
    $.ajax({
        url:"/book/user/logout",
        type:'POST',
        dataType: "json",
        data: {
        },
        beforeSend:function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    });
}
function checkId(id) {
    if(id.length == 0) {
        alert("Please input you ID");
        return false;
    } else {
        $("#checkIdNull").hide();
        return true;
    }
}
function checkPwLen(pw) {
    var reg=/^.{8,}$/;

    if(reg.test(pw)){
        $("#passwordLen").hide();
        return true;
    }else{
        alert("Passwords are up to 8 characters long");
        return false;
    }
}