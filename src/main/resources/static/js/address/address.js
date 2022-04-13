var option = '';
$(document).ready(function () {
    if (($(location).attr('href').split('/book')[1]).includes('/mypage/address/page')) {
        listAddress(nowPageNo);
    }
});
function listAddress(pageNo) {
    nowPageNo = pageNo;
    $.ajax({
        url: "/book/mypage/address/list",
        method: 'GET',
        dataType: "json",
        data: {
            pageNo: pageNo
        }
    }).done(function (data) {
        window.scrollTo(0,0);
        $("#addressCnt").text(data.addressCnt);
        paging(data, 'listAddress');

        $('#result')[0].innerHTML = '';

        if (data.addressList.length > 0) {
            var result = "<table class=\"table-list\">"
                + "<colgroup><col width=\"33.333333%\" /><col width=\"33.333333%\" /><col width=\"33.333333%\" /></colgroup><tbody>";

            for (var i = 0; i < data.addressList.length; i++) {
                if (i % 3 == 0) {
                    result += "</tr><tr>";
                } else if (i == 0) {
                    result += "<tr>";
                }

                result += "<td><ul class=\"list-style\">";

                //info

                result += "<li class=\"table-list-content\" style=\"width: 100%;\"><ul class=\"table-list-content-style\">"
                    + "<li class=\"table-list-content-list-style\"> 주소명 : " +  data.addressList[i].aname + "</li>"
                    + "<li class=\"table-list-content-list-style\"> 우편번호 : " + data.addressList[i].zipcode + "</li>"
                    + "<li class=\"table-list-content-list-style\">  주소 :"+ data.addressList[i].addr + "</li>"
                    + "<li class=\"table-list-content-btn-style\"><button type=\"button\" id=\"btnSale" + data.addressList[i].addrNo + "\" class=\"view-btn\" onClick=\"detailAddress(" + data.addressList[i].addrNo +")\" class=\"view-btn\" onClick=\"detailAddress("+ data.addressList[i].addrNo +")\">상세보기</button><li>"
                    + "</ul></li>";

                result += "</ul></td>";

                if (i == 0 && data.addressList.length == 1) {
                    result += "<td></td><td></td>";
                } else if (i == 1 && data.addressList.length == 2) {
                    result += "<td></td>";
                }

                if (i == data.addressList.length - 1) {
                    result += "</tr>";
                }
            }
            result += "</tbody></table>";
        } else {
			var result = "<p class=\"find-nothing\">결과가 없습니다.</p>";
		}


        $('#result').append(result);
    })
        .fail(function (textStatus) {
            alert("Request failed: " + textStatus);
        });
}
//추가 폼 세팅
function setAddressDefault() {
    $("#insertAName").val('');
    $("#insertAddress").val('');
    $("#insertZipcode").val('');
}
function createAddressForm() {
	$("#pop-mask-address-create").css("display","block");
	$("body").css("overflow","hidden");
	$("#pop-address-create").css({
        "top": (window.screen.height / 2) - ($("#pop-address-create").outerHeight() / 2)-50+"px",
        "left": (window.screen.width / 2) - ($("#pop-address-create").outerWidth() / 2)+"px"     
     }); 
	
	setAddressDefault();
	closeAddressDetailPopup();
	
	$("#pop-address-create").css("display", "block");
	
    var offset = $("#pop-address-create").offset().top;
	$("html").animate({scrollTop:offset},400);
}
function createAddress() {
    var aName = $("#insertAName").val();
    var address = $("#insertAddress").val();
    var zipcode = $("#insertZipcode").val();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    if(checkAName(aName)) return;
    if(checkAddress(address)) return;
    if(checkZipcode(zipcode)) return;

    if(confirm("주소를 등록하시겠습니까?")) {
        $.ajax({
            url: "/book/address/insert",
            method: 'POST',
            dataType: "json",
            data: {
                aName : aName,
                address : address,
                zipcode : zipcode
            },
            beforeSend: function (xhr) {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            }
        }).done(function (data) {
            if (data.result == 'success') {
                window.alert("새 주소를 등록했습니다.");

                closeAddressCreatePopup();
                window.location="/book/mypage/address/page";
            }
        })
            .fail(function (textStatus) {
                alert("Request failed: " + textStatus);
            });
    }
}
//상세 팝업 닫기
function closeAddressDetailPopup() {
    $("#pop-address-detail").css("display", "none");
    $("#pop-mask-address-detail").css("display","none");
    $("body").css("overflow","auto");
}

//추가 팝업 닫기
function closeAddressCreatePopup() {
	$("#pop-address-create").css("display", "none");
	$("#pop-mask-address-create").css("display","none");
	$("body").css("overflow","auto");
}

//주소 상세보기
function detailAddress(addrNo) {
    $("#pop-mask-address-detail").css("display","block");
    $("body").css("overflow","hidden");
    $("#pop-address-detail").css({
        "top": (window.screen.height / 2) - ($("#pop-address-detail").outerHeight() / 2)-50+"px",
        "left": (window.screen.width / 2) - ($("#pop-address-detail").outerWidth() / 2)+"px"
    });
    $.ajax( {
        url: "/book/address/detail",
        method: 'GET',
        dataType: "json",
        data: {
            addrNo : addrNo
        }
    }).done(function(data) {
        $("#pop-address-detail").css("display", "block");
        var offset = $("#pop-address-detail").offset().top;
        $("html").animate({scrollTop:offset},400);


        $("#viewAddrNo").val(data.address.addrNo);
        $("#viewaName").val(data.address.aname);
        $("#viewAddr").val(data.address.addr);
        $("#viewZipcode").val(data.address.zipcode);
        //버튼 세팅
        $('#buttonResult')[0].innerHTML = '';
        var resultBtn = '';

        resultBtn += "<button type=\"button\" class=\"pop-btn\" onClick=\"updateAddress()\">수정하기</button>";
        resultBtn += "<button type=\"button\" class=\"pop-btn\" onClick=\"closeAddressDetailPopup()\">닫기</button>";
        resultBtn += "<button type=\"button\" class=\"pop-btn\" onClick=\"deleteAddress()\">삭제하기</button>";

        $('#buttonResult').append(resultBtn);
    })
        .fail( function( textStatus ) {
            alert( "Request failed: " + textStatus );
        });
}
function updateAddress() {
    var addrNo = $("#viewAddrNo").val();
    var aName = $("#viewaName").val();
    var addr = $("#viewAddr").val();
    var zipcode = $("#viewZipcode").val();

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    if(checkAName(aName)) return;
    if(checkAddress(addr)) return;
    if(checkZipcode(zipcode)) return;

    if(confirm("수정하시겠습니까?")) {
        $.ajax({
            url: "/book/address/update",
            method: 'PUT',
            dataType: "json",
            data: {
                addrNo : addrNo,
                aName : aName,
                addr : addr,
                zipcode : zipcode
            },
            beforeSend : function(xhr){   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            }
        }).done(function(data) {
            if(data.result == 'success') {
                window.alert("주소 수정 완료");
                window.location="/book/mypage/address/page";

            } else {
                window.location = "/book/user/login";
                window.alert(data.reason);
            }
        })
    }
}
function deleteAddress() {
    var addrNo = $("#viewAddrNo").val();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    if(confirm("주소를 삭제하시겠습니까?")) {
        $.ajax({
            url: "/book/address/delete",
            method: 'DELETE',
            dataType: "json",
            data: {
                addrNo : addrNo
            },
            beforeSend : function(xhr){   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            }
        }).done(function(data) {
            if(data.result =='success') {
                window.alert("삭제가 완료되었습니다.");
                closeAddressDetailPopup();
                listAddress(nowPageNo);
            } else {
                window.location = "/book/user/login";
                window.alert(data.reason);
            }
        }).fail(function(textStatus) {
            alert("Request failed: " + textStatus);
        });
    }
}