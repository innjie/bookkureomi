var pwCheck = false;
var cpwCheck = false;
var nameCheck = false;
var phoneCheck = false;

$(document).ready(function(){
	$("#id").on("propertychange change keyup paste input", function(){
		$("#overlapCheck").css("display","inline");
        $("#overlapCheck").text("아이디 중복 확인이 필요합니다.");
		$("#isOverlapCheck").val('notChecked');
	});
	
	$("#id").blur( function(){
		if($("#id").val().length == 0 || $("#id").val() == "" || $("#id").val() == null) {
	        $("#overlapCheck").css("display","inline");
	        $("#overlapCheck").text("Please input ID ");
	    } 
	});
	
	$("#pw").blur( function(){
		var reg=/^.{8,}$/;

	    if(reg.test($("#pw").val())){
	        $("#passwordLen").css("display","none");
	        pwCheck = true;
	    }else{
	        $("#passwordLen").css("display","inline");
	        pwCheck = false;
	    }
	});
	
	$("#confirmPw").blur( function(){
		if($("#pw").val() == $("#confirmPw").val()) {
	        $("#notSameError").css("display","none");
	        cpwCheck = true;
	    } else {
	        $("#notSameError").css("display","inline");
	        cpwCheck = false;
	    }
	});
	
	$("#name").blur( function(){
		if($("#name").val().length == 0 || $("#name").val() == "" || $("#name").val() == null) {
	        $("#checkNameNull").css("display","inline");
	        nameCheck = false;
	    } else {
	        $("#checkNameNull").css("display","none");
	        nameCheck = true;
	    }
	});
	
	$("#phone1").blur( function(){
		var reg1 = /^[0-9]{3}$/;
		var reg2 = /^[0-9]{3,4}$/;
		var reg3 = /^[0-9]{4}$/;
	    
	    if(!reg1.test($("#phone1").val()) || !reg2.test($("#phone2").val()) || !reg3.test($("#phone3").val())) {
	        $("#phoneFormatError").css("display","inline");
	        phoneCheck = false;
	    } else  {
	    	$("#phoneFormatError").css("display","none");
	    	phoneCheck = true;
	    }
	});
	$("#phone2").blur( function(){
		var reg1 = /^[0-9]{3}$/;
		var reg2 = /^[0-9]{3,4}$/;
		var reg3 = /^[0-9]{4}$/;
	    
	    if(!reg1.test($("#phone1").val()) || !reg2.test($("#phone2").val()) || !reg3.test($("#phone3").val())) {
	        $("#phoneFormatError").css("display","inline");
	        phoneCheck = false;
	    } else  {
	    	$("#phoneFormatError").css("display","none");
	    	phoneCheck = true;
	    }
	});
	$("#phone3").blur( function(){
		var reg1 = /^[0-9]{3}$/;
		var reg2 = /^[0-9]{3,4}$/;
		var reg3 = /^[0-9]{4}$/;
	    
	    if(!reg1.test($("#phone1").val()) || !reg2.test($("#phone2").val()) || !reg3.test($("#phone3").val())) {
	        $("#phoneFormatError").css("display","inline");
	        phoneCheck = false;
	    } else  {
	    	$("#phoneFormatError").css("display","none");
	    	phoneCheck = true;
	    }
	});
});

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

    //csrf
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
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
            window.location = "/book/user/login";
        }
    }).fail(function(textStatus) {
        alert("페이지 오류: " + textStatus);
    });

}
