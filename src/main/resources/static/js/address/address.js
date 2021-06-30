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

                result += "<li class=\"table-list-content\"><ul class=\"table-list-content-style\">"
                    + "<li class=\"table-list-content-list-style\"><strong>" + data.addressList[i].addrNo + "</strong></li>"
                    + "<li class=\"table-list-content-list-style\"> 주소명 : \\" +  data.addressList[i].aName + "</li>"
                    + "<li class=\"table-list-content-list-style\"> 우편번호 : \\" + data.addressList[i].zipcode + "</li>"
                    + "<li class=\"table-list-content-list-style\">  주소 :"+ data.addressList[i].address + "</li>"
                    + "<li class=\"table-list-content-btn-style\"><button type=\"button\" id=\"btnSale" + data.addressList[i].addressNo + "\" class=\"view-btn\" onClick=\"detailAddress(" + data.addressList[i].addressNo +")\" class=\"view-btn\" onClick=\"detailAddress("+ data.addressList[i].addrNo +")\">상세보기</button><li>"
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
        }


        $('#result').append(result);
    })
        .fail(function (textStatus) {
            alert("Request failed: " + textStatus);
        });
}
//추가 폼 세팅
function setAddressDefault() {
    $("#aName").val('');
    $("#address").val('');
    $("#zipcode").val('');
}
function createAddressForm() {
    setAddressDefault();
    closeAddressDetailPopup();

    $("#pop-sale-create").css("display", "block");

    var offset = $("#pop-sale-create").offset().top;
    $("html").animate({scrollTop: offset}, 400);
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

                $("#pop-sale-insert").css("display", "none");

                listAddress();
                detailAddress(data.addrNo);
            }
        })
            .fail(function (textStatus) {
                alert("Request failed: " + textStatus);
            });
    }
}
//상세 팝업 닫기
function closeAddressDetailPopup() {
    $("#pop-sale-detail").css("display", "none");
}

//추가 팝업 닫기
function closeAddressCreatePopup() {
    $("#pop-sale-create").css("display", "none");
}