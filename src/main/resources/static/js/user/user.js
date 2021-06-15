function login() {
    var username = $("#id").val();
    var password = $("#password").val();

    //csrf
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    if(!checkId(username)) return false;
    if(!checkPwLen(password)) return false;

    $.ajax( {
        url:"/book/user/login",
        type:'POST',
        dataType: "json",
        data: {
        	username : username,
        	password : password
        },
        beforeSend:function(xhr) {
            xhr.setRequestHeader(header, token);
        }
    }).done(function( data ) {
    	if(!data.response.error){
			alert("로그인 하였습니다.");
			window.location = "/";
		} else if(data.response.message == 'wrongid'){
			$("#errMsg").text("잘못된 아이디입니다.");
			$("#id").val('');
			$("#password").val('');
		} else if(data.response.message == 'wrongpw'){
			$("#errMsg").text("잘못된 비밀번호입니다.");
			$("#password").val('');
		}  
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });

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
    }).done(function( data ) {
		if(!data.error){
			alert("로그아웃 하였습니다.");
		} else{
			alert("로그아웃에 실패하였습니다.");
		}
		
    	window.location = "/";
    })
    .fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}

function checkId(id) {
    if(id.length == 0) {
    	$("#errMsg").text("아이디를 입력하세요.");
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
    	$("#errMsg").text("8자리 이상의 비밀번호를 입력하세요.");
    	alert("Passwords are up to 8 characters long");
        return false;
    }
}