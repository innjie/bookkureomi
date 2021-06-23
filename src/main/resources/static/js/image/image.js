
function updateImage(type){
	var filePath = $("#filePath").val();
	var imageNo = $("#imageNo").val();
	var saleNo = $("#viewSaleNo").val();
	
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
    var updateFile = $("#updateImageFile").val();
    if(updateFile == '' || updateFile == null || updateFile == undefined){
    	alert("수정할 파일이 없습니다.");
    	return false;
    }
    
    var form = $('#updateImage')[0];
    var formData = new FormData(form);
    
    formData.append("filePath", filePath);
    formData.append("imageNo", imageNo);
    formData.append("saleNo", saleNo);
    
    if($("#viewImage").attr("src") == '/images/sale/0.png'){//추가
    	$.ajax({
            url: "/book/image/create",
            method: 'POST',
    		enctype: 'multipart/form-data',
    		processData: false,
            contentType: false,
            cache: false,
            data: formData,
    		beforeSend : function(xhr){   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            }
        }).done(function(data) {
            if(data.result == 'success'){
            	alert("사진을 추가했습니다.");
            	if(type == 'sale'){
            		listSale(1);
            		detailSale(saleNo);
            	} else{
            		listMySale(1);
            		detailMySale(saleNo);
            	}
            } else{
            	alert(data.reason);
            }
        }).fail( function( textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    } else{ //수정
    	$.ajax({
            url: "/book/image/update",
            method: 'POST',
    		enctype: 'multipart/form-data',
    		processData: false,
            contentType: false,
            cache: false,
            data: formData,
    		beforeSend : function(xhr){   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
                xhr.setRequestHeader(header, token);
            }
        }).done(function(data) {
            if(data.result == 'success'){
            	alert("사진을 수정했습니다.");
            	if(type == 'sale'){
            		listSale(1);
            		detailSale(saleNo);
            	} else{
            		listMySale(1);
            		detailMySale(saleNo);
            	}
    		} else{
            	alert(data.reason);
            }
        }).fail( function( textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    }
    
    
}

function deleteImage(type){
	var filePath = $("#filePath").val();
	var imageNo = $("#imageNo").val();
	var saleNo = $("#viewSaleNo").val();
	
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
    if(filePath == '' || filePath == null || filePath == undefined
    		|| imageNo == '' ||imageNo == null || imageNo == undefined){
    	alert("삭제할 파일이 없습니다.");
    	return false;
    }
    
    $.ajax({
        url: "/book/image/delete",
        method: 'DELETE',
        dataType: "json",
        data: {
        	imageNo: imageNo,
        	filePath: filePath
		},
		beforeSend : function(xhr){   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
            xhr.setRequestHeader(header, token);
        }
    }).done(function(data) {
        if(data.result == 'success'){
        	alert("사진을 삭제했습니다.");
        	if(type == 'sale'){
        		listSale(1);
        		detailSale(saleNo);
        	} else{
        		listMySale(1);
        		detailMySale(saleNo);
        	}
        } else{
        	alert(data.reason);
        }
    }).fail( function( textStatus ) {
        alert( "Request failed: " + textStatus );
    });
}