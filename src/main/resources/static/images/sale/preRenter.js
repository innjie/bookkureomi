/**
 * 임자인 정보관리 컨트롤 JS 파일.
 */
$(document).ready(function() {
	
    initDateTimePicker();
    initDayButtonListener();

    initPreRenterListButtonListener();

    // checkall
    $("#check-all").change(function (e) {
        var isChecked = $(this).prop("checked");
        if( isChecked) {
            $("#tb-preRenter-result input[type=checkbox]").prop("checked", true);
			$("#check-all-mobile").prop("checked", true);
            $("#tb-preRenter-result-mobile input[type=checkbox]").prop("checked", true);
        } else {
            $("#tb-preRenter-result input[type=checkbox]").prop("checked", false);
			$("#check-all-mobile").prop("checked", false);
            $("#tb-preRenter-result-mobile input[type=checkbox]").prop("checked", false);
        }
        updateTotalSum();
    });
    
    $("#check-all-mobile").change(function(e){
		var isChecked = $(this).prop("checked");
		if(isChecked){
			$("#tb-preRenter-result-mobile li p input[type=checkbox]").prop("checked", true);
			$("#check-all").prop("checked",true);
            $("#tb-preRenter-result input[type=checkbox]").prop("checked", true);
		} else {
			$("#tb-preRenter-result-mobile li p input[type=checkbox]").prop("checked", false);
			$("#check-all").prop("checked",false);
            $("#tb-preRenter-result input[type=checkbox]").prop("checked", false);
		}
		updateTotalSum();
	});

    $("#sel-count").change(function (e) {
        $("#preRenterlist").trigger("click");
    });
    dateInputCheck();
    postCodeEventListener();
//    initRegFormFileListener();
    
    //2020.09.03 한솔 추가 (정규화 작업 ' 안되게)
    $('#addr2').focusout(function() {
    	var value = $("#addr2").val();
    	var vx = value.replace(/'/g, "");
    	//alert("[\']사용불가");
    	$("#addr2").val(vx);
    });
    
	$("#fileSel").change(function(e){
    	var fileValue = $("#fileSel").val().split("\\");
        var fileName = fileValue[fileValue.length-1];
        $("#inputFile").val(fileName);
    });
	
	var oldValue = "";
	setInterval(function() {
	    var currentValue = $("#addr1").val();
	    if (currentValue != oldValue) {
	      $("#addr1").trigger("change");
	      oldValue = currentValue;
	    }
	}, 100);
	
	$("#addr1").change(function(){
		if($("#pop-type").val() == 'insert') {
			$("#pop-ins-addr1").val($("#addr1").val());
			$("#pop-ins-zip").val($("#zone").val());
		} else if($("#pop-type").val() == 'modify') {
			$("#pop-upd-addr1").val($("#addr1").val());
			$("#pop-upd-zip").val($("#zone").val());
		}
	});
	
	$("#preRenterlist").focus();
	
	$("#preRenterlist").bind("keydown", function(e) {
		if (e.keyCode == 13) { // enter key
			$("#preRenterlist").trigger("click");
			return false
		}
	});
	
});
    
    /**
     * @name : insertPreRenterPop
     * @description : 후불자동수납 등록 팝업표출
     * @date : 2021-03-09
     * @author :yoonsuk Lim
     */
    function insertPreRenterPop(){
    	$("#pop-insert").css("display", "block");
    	$("#pop-type").val("insert");
    	$('#pop-ins-zip').prop('readonly', true);
        $('#pop-ins-addr1').prop('readonly', true);
    }

    /**
     * @name : closePopUp
     * @description : 팝업닫기
     * @date : 2021-03-09
     * @author :yoonsuk Lim
     */
    function closePopUp(id) {
        $(id).css("display", "none");
        setDefaultValues();
    }
    
    /**
     * @name : setDefaultValues
     * @description : 처리후 값 초기화
     * @date : 2021-03-10
     * @author :yoonsuk Lim
     */
    function setDefaultValues(){
    	//$("#pop-ins-carno").val("");
    	$("#pop-ins-startdate").val("");
    	$("#pop-ins-enddate").val("");
    	$("#pop-ins-rntname").val("");
    	$("#pop-ins-zip").val("");
    	$("#pop-ins-addr1").val("");
    	$("#pop-ins-addr2").val("");
    	$("#apply-check").prop("checked", false);
    	
    	$("#zone").val("");
		$("#addr1").val("");
    }
    
    /**
     * @name : insertPreRenter
     * @description : 사전 임차인 등록
     * @date : 2021-03-09
     * @author :yoonsuk Lim
     */
    function insertPreRenter(){
    	var checked = $("input:checkbox[id='apply-check']").is(":checked");
		if(!checked){
			window.alert("약관에 동의하여야 합니다.");
			return;
		}
		
    	if(confirm("사전 임차인 정보를 등록 하시겠습니까?")) {
    		var hndDiv = "0"; //처리구분 (0:신규입력 1:수정)
    		var hndNo = "0"; //식별자, 신규추가일 경우 공백문자 입력, 수정시에는 기존 VIEW 의 값 입력
    		
    		var carNo = $("#pop-ins-carno").val();
    		var startDate = $("#pop-ins-startdate").val();
    		var startTm = $("#pop-ins-starttm").val();
    		var endDate = $("#pop-ins-enddate").val();
    		var endTm = $("#pop-ins-endtm").val();
    		var start = startDate.replace(/-/g,"").substring(0, 8).concat(startTm);
    		var end = endDate.replace(/-/g,"").substring(0, 8).concat(endTm);
    		var rntName = $("#pop-ins-rntname").val();
    		var rntAddr1 = $("#pop-ins-addr1").val();
    		var rntAddr2 = $("#pop-ins-addr2").val();
    		var rntZipcode = $("#pop-ins-zip").val();
    		var useYn = "F"; //신규추가/카드번호변경 시 F
    		
    		if( carNo == undefined || carNo == '' || carNo == 'non') {
    			window.alert("차량번호를 선택하세요.");
    			return;
    		}
    		if( startDate == undefined || startDate == '' || startTm == undefined || startTm == '' || endDate == undefined || endDate == '' || endTm == undefined || endTm == '' ) {
    			window.alert("임차 기간을 입력하세요.");
    			return;
    		}
    		if( start >= end ) {
    			window.alert("임차 기간을 정확히 입력하세요.");
    			return;
    		}
    		if( rntName == undefined || rntName == '' ) {
    			window.alert("임차인명을 입력하세요.");
    			return;
    		}
    		if( rntZipcode == undefined || rntZipcode == '' ) {
    			window.alert("우편번호를 입력하세요.");
    			return;
    		}
    		if( rntAddr1 == undefined || rntAddr1 == '' ) {
    			window.alert("기본주소를 입력하세요.");
    			return;
    		}
    		/*if( rntAddr2 == undefined || rntAddr2 == '' ) {
    			window.alert("상세주소를 입력하세요.");
    			return;
    		}*/
    		
    		$.ajax({
    			url: "/renter/preRenter/procPreRenter"
    			, method: 'POST'
    			, data: {
    				hndDiv: hndDiv
    				, carNo: carNo
    				, hndNo: hndNo
    				, startDate: start
    				, endDate: end
    				, rntName: rntName
    				, rntAddr1: rntAddr1
    				, rntAddr2: rntAddr2
    				, rntZipcode: rntZipcode
    				, useYn: useYn
    			}
    		})
    		.done(function( data, textStatus, jqXHR ) {
    			// 권한이 없을 경우
    			if( textStatus == '302' ) {
    				var redirect = jqXHR.getResponseHeader("Location");
    				movePage(redirect);
    				return;
    			}
    			if(data.resultCode != "0000"){
    				window.alert("사전 임차인 정보 등록이 실패하였습니다.\n" + data.resultMsg);
    			}else {
    				window.alert("사전 임차인 정보가 등록되었습니다.\n관리자 승인처리 후 가입 시 입력한 전화번호로 SMS 통지를 보내드립니다.");
    				
    				$("#pop-insert").css("display", "none");
        			
    	    		$("#carNo").val(carNo);
    	    		$("#rntName").val(rntName);
    	    		$("#preRenterlist").trigger("click");
    	    		    
    	    		setDefaultValues();
    			}
    		})
    		.fail( function(jqXHR, textStatus, errorThrown ) {
    			alert( "Request failed: " + textStatus );
    			setDefaultValues();
    		});
    		
    	}
    }

    /**
     * @name : updatePreRenter
     * @description : 사전 임차인 수정
     * @date : 2021-03-10
     * @author :yoonsuk Lim
     */
    function updatePreRenter(options) {

    	if(confirm("임차인 정보를 수정 하시겠습니까?")) {
    		var hndNo=$("#hndno").val();
    		//alert($("#hndno").val());
    		var hndDiv = "1"; //처리구분 (0:신규입력 1:수정);
    		
    		var carNo = $("#pop-upd-carno").val();
    		
    		var startDate = $("#pop-upd-startdate").val();
    		var startTm = $("#pop-upd-starttm").val();
    		var endDate = $("#pop-upd-enddate").val();
    		var endTm = $("#pop-upd-endtm").val();
    		var start = startDate.replace(/-/g,"").substring(0, 8).concat(startTm);
    		var end = endDate.replace(/-/g,"").substring(0, 8).concat(endTm);
    		var rntName = $("#pop-upd-rntname").val();
    		var rntAddr1 = $("#pop-upd-addr1").val();
    		var rntAddr2 = $("#pop-upd-addr2").val();
    		var rntZipcode = $("#pop-upd-zip").val();
    		var useYn = "F"; //신규추가/카드번호변경 시 F
    		
    		if( carNo == undefined || carNo == '' ) {
    			window.alert("차량번호를 입력하세요.");
    			return;
    		}
    		if( startDate == undefined || startDate == '' || startTm == undefined || startTm == '' || endDate == undefined || endDate == '' || endTm == undefined || endTm == '' ) {
    			window.alert("임차 기간을 입력하세요.");
    			return;
    		}
    		if( start >= end ) {
    			window.alert("임차 기간을 정확히 입력하세요.");
    			return;
    		}
    		if( rntName == undefined || rntName == '' ) {
    			window.alert("임차인명을 입력하세요.");
    			return;
    		}
    		if( rntZipcode == undefined || rntZipcode == '' ) {
    			window.alert("우편번호를 입력하세요.");
    			return;
    		}
    		if( rntAddr1 == undefined || rntAddr1 == '' ) {
    			window.alert("기본주소를 입력하세요.");
    			return;
    		}
    		/*if( rntAddr2 == undefined || rntAddr2 == '' ) {
    			window.alert("상세주소를 입력하세요.");
    			return;
    		}*/
    		
    	    // Ajax 요청으로 데이터를 가져온다.
    		$.ajax({
    			url: "/renter/preRenter/procPreRenter"
    			, method: 'POST'
    			, data: {
    				hndDiv: hndDiv
    				, carNo: carNo
    				, hndNo: hndNo
    				, startDate: start
    				, endDate: end
    				, rntName: rntName
    				, rntAddr1: rntAddr1
    				, rntAddr2: rntAddr2
    				, rntZipcode: rntZipcode
    				, useYn: useYn
    			}
    		}).done(function( data, textStatus, jqXHR ) {
    	
    	            // 권한이 없을 경우
    	            if( textStatus == '302' ) {
    	                var redirect = jqXHR.getResponseHeader("Location");
    	                movePage(redirect);
    	                return;
    	            }
    	
        			if(data.resultCode == "0000"){
    	                window.alert("임차인 정보를 수정하였습니다.");
    	                
    	                $("#pop-modify").css("display", "none");
            			
                		var carNo2 = $("#carNo").val();
                		var rntName2 = $("#rntName").val();    		    
                	    if( (carNo2 == undefined || carNo2 == "") && (rntName2 == undefined || rntName2 == "")) {
                	    	$("#carNo").val(carNo);
                		}
                		$("#preRenterlist").trigger("click");
                		    
                		setDefaultValues();
    	            } else {
    	                window.alert("임차인 정보 수정에 실패하였습니다.\n" + data.resultMsg);
    	            }
    	        })
    	        .fail( function(jqXHR, textStatus, errorThrown ) {
    	            alert( "Request failed: " + textStatus );
    	            setDefaultValues();
    	        });
    	}
    }
    
    /**
     * @name : deletePreRenter
     * @description : 사전임차인 삭제
     * @date : 2021-03-12
     * @author :yoonsuk Lim
     */
    function deletePreRenter() {
    	var total = checkTotal();
    	if(total == 0) {
    		alert("삭제하실 사전 임차인 정보를 선택하세요");
    		return;
    	}
    	
    	var chk = confirm(total + "개의 사전 임차인 정보를 삭제 하시겠습니까?");
    	if(chk){
    		
    		var success = false;
    		$("#tb-preRenter-result input:checked").each(function(idx, obj) {
    			var tdList = $(this).parent().siblings("td");

    			var hndDiv = "2"; //처리구분 (0:신규입력 1:수정 2:삭제);
    			var hndNo = $(tdList[0]).html().split("\"")[5];

    			var carNo = $(tdList[2]).html();
    			$.ajax({
        	        url: "/renter/preRenter/procPreRenter"
        	        , method: 'POST'
        	    	, data: {
        	        	hndDiv: hndDiv
        				, carNo: carNo
        				, hndNo: hndNo
        	    		}
        	    })
        	    .done(function( data, textStatus, jqXHR ) {
        			if(data.resultCode == "0000"){
        				if(idx == $("#tb-preRenter-result input:checked").length-1) {
                    		window.alert(idx+1+"개의 사전임차인 정보가 삭제 완료되었습니다.");
                            
                            var carNo2 = $("#carNo").val();
                			var rntName2 = $("#rntName").val();    		    
                	    	if( (carNo2 == undefined || carNo2 == "") && (rntName2 == undefined || rntName2 == "")) {
                	    		$("#carNo").val(carNo);
                			}
                			$("#preRenterlist").trigger("click");
                			//location.reload()
                			setDefaultValues();
                    	}
        	        } else {
        	        	window.alert("실패. 다시시도하세요.");
        	        	return false;
        	        }
        	    })
        	    .fail( function(jqXHR, textStatus, errorThrown ) {
        	        alert( "Request failed: " + textStatus );
        	        return false;
        	    });
    			
    			
    		});
    		
    	}
    	
    }
    
    
    function checkTotal() {
        var cnt = 0;
        $("#tb-preRenter-result input:checked").each(function(idx, obj) {
            cnt += 1;
        });

        return cnt;
    }

/*
 * 임차인 정보 파일 일괄업로드 
 * */

$(function(){
	$("#regformbtn").on("click", function(){
		uploadFile();
	});
});

function uploadFile(){
	var fileSelected = $("input[name=file]").val();
	if( fileSelected == ""){
		window.alert("파일을 선택 하세요.");
		event.preventDefault();
		return;
	}
	var formData = new FormData($("#regformfileForm")[0]);
	//formData.append("file", $("input[name=file]")[0].files[0]);
	
	var carNo = $("#carNo").val();
	var rntName = $("#rntName").val();
	
	$.ajax({
		url : $("#regformfileForm").attr('action'),
		type : "POST",
		data : formData,
		enctype : "multipart/form-data",
		contentType : false,
        processData : false,
        cache: false,
        success: function(result){
        	alert(result.uploadMessage);
        	$("#preRenterlist").trigger("click");
        	$("#inputFile").val("");
        	$("#fileSel").val("");
        	/*var htmlStr = "<input type='file' name='file' accept='.csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel' style='width: 305px;'/>" +
            			  "<button type='button' class='btn' onclick='uploadFile()'>일괄 업로드</button>";	
            
            $("#regformfileForm").html(htmlStr);*/
        },
        error: function(e){
        	window.alert("사전 임차인 일괄등록에 실패 하였습니다.\n작성한 양식파일을 확인하신 후 재시도 해주시기 바랍니다.");
        	$("#inputFile").val("");
        	$("#fileSel").val("");
        }
	});
}

/**
 * DateTimePicker 초기화.
 */
function initDateTimePicker() {
    // datetimepicker 초기화 및 설정
    var sDatePicker = $("input[name=stime]");
    var eDatePicker = $("input[name=etime]");
    sDatePicker.datepicker("option", "onSelect", function (dateText) {
        eDatePicker.datepicker("option", "minDate", dateText);
    });
    eDatePicker.datepicker("option", "onSelect", function (dateText)  {
        sDatePicker.datepicker("option", "maxDate", dateText);
    });
}

/**
 * 날짜 선택버튼 초기화.
 */
function initDayButtonListener() {
    $("button[data-type]").click(function(e) {
        var type = $(this).attr("data-type");
        var value = $(this).attr("data-val");

        var typeDp =  (type == 'days')? 'd' : (type == 'months') ? 'm' : 'd';

        var sDatePicker = $("input[name=stime]");
        var eDatePicker = $("input[name=etime]");

        sDatePicker.datepicker("setDate", new Date());
        eDatePicker.datepicker("setDate", new Date());

        sDatePicker.datepicker("setDate", "-" + value + typeDp);
        // var added = moment().add(value, type);
        // sDatePicker.data("DateTimePicker").date(moment());
        // eDatePicker.datepicker("options",.data("DateTimePicker").date(added);
    });
    // $("button[data-type='days'][data-val='1']").trigger("click");
    var sDatePicker = $("input[name=stime]");
    var eDatePicker = $("input[name=etime]");
    sDatePicker.datepicker("setDate", new Date());
    eDatePicker.datepicker("setDate", new Date());
    sDatePicker.datepicker("setDate", "-3w");
}

/**
 * 납부내역조회 버튼 초기화.
 */
function initPreRenterListButtonListener(options) {
	$("#preRenterlist").click(function(e) {
        var options = getParam();
        console.log(options)
        options.count = $("#sel-count option:selected").val();
        options.page = 1;
        options.mustacheTemplate =  preRenterMustacheTemplate;
        options.mustacheMobileTemplate =  preRenterMustacheTemplateMobile;
        options.gridId = "tb-preRenter-result";
        options.mobileGridId = "tb-preRenter-result-mobile";
        options.pageId = "pagenavi";
        
        selectPreRenterInfoList(options);
    });
}

function selectPreRenterInfoList(options) {
	var carNo = $("#carNo").val();
	var rntName = $("#rntName").val();
	
    // Ajax 요청으로 데이터를 가져온다.
    $.ajax({
        url: "./selectPreRenterInfoList"
        , method: 'POST'
        , data: {
            page: options.page
            , count: options.count
            , carNo: carNo
            , rntName: rntName
        }
        , statusCode : {
            302: function () {

            }
        }
    })
        .done(function( data, textStatus, jqXHR ) {

            // 권한이 없을 경우
            if( typeof data ==  "string" ) {
                if( data.indexOf("<!DOCTYPE html>")
                    || data.indexOf("<!doctype html>") ) {
                    movePage("/renter");
                }
            }

            // 수정 해야 함.
            if(data.totalCount == 0) {
                window.alert("조회결과가 없습니다.");
            }

            // 총건수
            // $("#totalcnt").html('총 ' + data.count + '건');

            // 조회 결과로 그리드 생성
            $("#" + options.gridId).upcgrid({
                // dataConvertFunc: function (data) {
                // },
                mustacheTemplate: options.mustacheTemplate,
                initEvent: function () {
                    var checkBoxAllCount = "#" + options.gridId +" input[type=checkbox]";
                    var checkBoxCheckedCount = "#" + options.gridId +" input:checked";

                    $("#" + options.gridId + " input[type=checkbox]").change(function (e) {
                    	var mid = "m"+$(this).attr("id");
                    	
                    	//웹에서 체크하면 모바일도 체크
                    	if($(this).prop("checked")) {
                    		$("#"+mid).prop("checked", true);
                    	} else {
                    		$("#"+mid).prop("checked", false);
                    	}
                    	
                    	//모두 체크되면 자동으로 all체크박스 체크
                        var lenA = $(checkBoxAllCount).length;
                        var lenC = $(checkBoxCheckedCount).length;
                        if( lenA == lenC ) {
                            $("#check-all").prop("checked", true);
                            $("#check-all-mobile").prop("checked", true);
                        } else {
                            $("#check-all").prop("checked", false);
                            $("#check-all-mobile").prop("checked", false);
                        }
                    });

                    //수정 버튼 클릭
                    $("#" + options.gridId + " button").click(function(e) {
                        var tdInfo = $(this).parent().prevAll("td");

                        $("#hndno").val($(tdInfo[6]).html().split("\"")[5]);
                        
                        $("#pop-upd-carno").val($(tdInfo[4]).html());
                        $("#pop-upd-startdate").val($(tdInfo[3]).html().substring(0, 10));
                        $("#pop-upd-enddate").val($(tdInfo[2]).html().substring(0, 10));
                        $("#pop-upd-rntname").val($(tdInfo[5]).html());
                        
                        $("#pop-upd-starttm").val($(tdInfo[3]).html().substring(11, 13)+"0000").prop("selected",true);
                        $("#pop-upd-endtm").val($(tdInfo[2]).html().substring(11, 13)+"0000").prop("selected",true);
                        
                        // 주소 관련된 정보가 필요함.
                        $("#pop-upd-zip").val($(tdInfo[7]).html().split("rntZipCode=")[1].split("&")[0]);
                        $("#pop-upd-addr1").val($(tdInfo[7]).html().split("rntAddress=")[1].split("&")[0].split(", ")[0]);
                        $("#pop-upd-addr2").val($(tdInfo[7]).html().split("rntAddress=")[1].split("&")[0].split(", ")[1]);
                        $('#pop-upd-zip').prop('readonly', true);
                        $('#pop-upd-addr1').prop('readonly', true);

                       $("#pop-modify").css("display", "block");
                       $("#pop-type").val("modify");
                       $('#pop-upd-carno').prop('readonly', true);

                        // 업데이트키 값
                       //$("key").val($(tdInfo[12]).children("input").val());
                       $("key").val($(tdInfo[6]).children("input").val());

                    })

                },
                resData: data.preRenterInfoList || [],
            });
            
            $("#" + options.mobileGridId).upcgrid({
                mustacheTemplate: options.mustacheMobileTemplate,
                initEvent: function () {
                	var checkBoxAllCount = "#" + options.mobileGridId +" input[type=checkbox]";
                    var checkBoxCheckedCount = "#" + options.mobileGridId +" input:checked";

                    $("#" + options.mobileGridId + " input[type=checkbox]").change(function (e) {
                    	var id = $(this).attr("id").replace("m","");
                    	
                    	//모바일에서 체크하면 웹도 체크
                    	if($(this).prop("checked")) {
                    		$("#"+id).prop("checked", true);
                    	} else {
                    		$("#"+id).prop("checked", false);
                    	}
                    	
                    	//모두 체크되면 자동으로 all체크박스 체크
                    	var lenA = $(checkBoxAllCount).length;
                        var lenC = $(checkBoxCheckedCount).length;
                        if( lenA == lenC ) {
                            $("#check-all-mobile").prop("checked", true);
                            $("#check-all").prop("checked", true);
                        } else {
                            $("#check-all-mobile").prop("checked", false);
                            $("#check-all").prop("checked", false);
                        }
                    });

                    //수정 버튼 클릭
                    $("#" + options.mobileGridId + " button").click(function(e) {
                    	var pInfo = $(this).parent().prevAll("p");
                        
                        $("#hndno").val($(pInfo[1]).html().split("hndNo=")[1].split("\"")[0]);
                        
                        $("#pop-upd-carno").val($(pInfo[1]).html().split("carNo=")[1].split("&")[0]);
                        $("#pop-upd-startdate").val($(pInfo[1]).html().split("startTm=")[1].split("&")[0].substring(0, 10).replace("-",""));
                        $("#pop-upd-enddate").val($(pInfo[1]).html().split("endTm=")[1].split("&")[0].substring(0, 10).replace("-",""));
                        $("#pop-upd-rntname").val($(pInfo[1]).html().split("rntName=")[1].split("&")[0]);
                        
                        $("#pop-upd-starttm").val($(pInfo[1]).html().split("startTm=")[1].split("&")[0].substring(11, 13)+"0000");
                        $("#pop-upd-endtm").val($(pInfo[1]).html().split("endTm=")[1].split("&")[0].substring(11, 13)+"0000");
                        
                        // 주소 관련된 정보가 필요함.
                        $("#pop-upd-zip").val($(pInfo[1]).html().split("rntZipCode=")[1].split("&")[0]);
                        $("#pop-upd-addr1").val($(pInfo[1]).html().split("rntAddress=")[1].split("&")[0].split(", ")[0]);
                        $("#pop-upd-addr2").val($(pInfo[1]).html().split("rntAddress=")[1].split("&")[0].split(", ")[1]);

                       $("#pop-modify").css("display", "block");
                       $("#pop-type").val("modify");
                       $('#pop-upd-carno').prop('readonly', true);
                    })

                },
                resData: data.preRenterInfoList || [],
            });

            $("#" + options.pageId).upcpage({
                totalCount: data.totalCount
                , pagePerCount: data.count
                , page: options.page
                , numPageFunc: function (currPage, pagePerCnt) {
                	
                	//페이지 변하면 all체크박스 해제
                    $("#check-all").prop("checked", false);
                    $("#check-all-mobile").prop("checked", false);

                    var param = getParam();
                    param.page = currPage;
                    param.count = pagePerCnt ;
                    param.mustacheTemplate = options.mustacheTemplate;
                    param.mustacheMobileTemplate = options.mustacheMobileTemplate;
                    param.pageId = options.pageId;
                    param.gridId = options.gridId;
                    param.mobileGridId = options.mobileGridId;
              	   
                    selectPreRenterInfoList(param);
                }
            });

            //all체크박스 해제를 디폴트로 
            if( $("#check-all").prop("checked")) {
                $("#check-all").prop("checked", false);
                $("#check-all-mobile").prop("checked", false);
            } else if( $("#check-all-mobile").prop("checked")) {
                $("#check-all").prop("checked", false);
                $("#check-all-mobile").prop("checked", false);
            }

			$("#check-all").trigger("click");
        })
        .fail( function(jqXHR, textStatus, errorThrown ) {
            alert( "Request failed: " + textStatus );
        });
}

/**
 * @name : preRenterMustacheTemplate
 * @description : 사전임차인 동적 그리드 생성
 * @date : 2021-03-02
 * @author :youngjoo won
 */

function preRenterMustacheTemplate() {
    var strMustache = "{{#.}}";
    strMustache += "<tr>";
    strMustache += "<td><input id='{{index}}' type='checkbox' value='plzId=1&carNo={{carNo}}&startTm={{startTm}}&endTm={{endTm}}&rntName={{rntName}}&rntAddress={{rntAddress}}&rntZipCode={{rntZipCode}}&useYnNm={{useYnNm}}&hndNo={{hndNo}}'/><label for='{{index}}'>선택</label></td>";
    strMustache += "<td>{{rownum}}";
//    strMustache += "<td>{{rownum}}</td>";
//    strMustache += "<td>";
//    strMustache += "{{hndNo}}";
    strMustache += "<input type='hidden' id='hndNo' th:value='{{hndNo}}'/>";
    strMustache += "</td>";
    strMustache += "<td style='display:none;'>";
    strMustache += "{{rntName}}";
    strMustache += "</td>";
    strMustache += "<td>";
    strMustache += "{{carNo}}";
    strMustache += "</td>";
    strMustache += "<td>";
    strMustache += "{{startTm}}";
    strMustache += "</td>";
    strMustache += "<td>";
    strMustache += "{{endTm}}";
    strMustache += "</td>";
    strMustache += "<td>";
    strMustache += "{{maskingRntName}}";
    strMustache += "</td>";
    strMustache += "<td>";
    strMustache += "{{useYnNm}}";
    strMustache += "</td>";
    strMustache += "<td>";
    strMustache += "<button class='btn01' type='button'>수정</button>";
    strMustache += "</td>";
    strMustache += "</tr>";
    strMustache += "{{/.}}";

    return strMustache;
}


//모바일 그리드
function preRenterMustacheTemplateMobile() {
	var strMustache = "{{#.}}";
	
	strMustache += "<li>"
	strMustache += "<p><input id='m{{index}}' type='checkbox' value='plzId=1&carNo={{carNo}}&startTm={{startTm}}&endTm={{endTm}}&rntName={{rntName}}&rntAddress={{rntAddress}}&rntZipCode={{rntZipCode}}&useYnNm={{useYnNm}}&hndNo={{hndNo}}' /><label for='m{{index}}'>{{rownum}}</label><span>{{carNo}}</span><span>{{maskingRntName}}</span></p>";
	strMustache += "<p><time class='start'>{{startTm}}</time><time class='end'>{{endTm}}</time><span>{{useYnNm}}</span></p>";
	strMustache += "<p><button>수정</button></p>";
	strMustache += "</li>"
	strMustache += "{{/.}}";
	
	return strMustache;
	
}

function preRenterMustacheTemplateMobileHeader() {
	var strMustache = "{{#.}}";
	strMustache += "<li>"
	strMustache += "<p><input type='checkbox' id='check-all-mobile'/><label for='check-all-mobile'>일련번호</label><span>차량번호</span><b>임차인명</b></p>"
	strMustache += "<p><time class='start'>시작시간</time><time class='end'>종료시간</time><b>승인상태</b></p>"
	strMustache += "</li>"
	strMustache += "{{/.}}";
	
	return strMustache;
}


//임차인 등록 엑셀 다운로드
function downloadExcel(url) {
    var inputChecked = $("#tb-preRenter-result input:checked");
//    if( inputChecked.length == 0 ) {
//        window.alert("사전임차인 정보가 조회되지 않았습니다.");
//        return;
//    }
    var requestUrl = url;
    var downForm = $("<form></form>");
    downForm.attr("action", requestUrl);
    downForm.attr("method", "post");

//    inputChecked.each(function(idx, obj) {
//        var param = $(this).val();
//        
//        var arrParam = param.split("&");
//        $.each(arrParam, function(idx, obj) {
//            var arrTmp = obj.split("=");
//            downForm.append("<input name='" + arrTmp[0] + "' value='" + arrTmp[1] + "' />");
//        });
//    });

    console.log("form: ", downForm.html());

    downForm.appendTo("body").submit().remove();
}

function getParam() {
    var options = {};

    // select
    $("div.tbl select > option:checked").each(function(idx, obj) {
        var key = $(this).parent("select").attr("name");
        var value = $(this).val();
        options[key] = value;
    });

    // input
    $("div.tbl input").each(function(idx, obj) {
        var key = $(this).attr("name");
        var value = $(this).val();
        options[key] = value;
    });

    return options;
    
}


function initRegFormFileListener() {
    $("form[action*=regfromfile]").submit(function(e) {
       var fileSelected = $("form[action*=regfromfile] > input").val();
       if( fileSelected == "" ) {
           window.alert("파일을 선택 하세요.");
           e.preventDefault();
       }
    });
}


//2020.06.08 한솔 : 조회 날자 inputbox 체크
function dateInputCheck(){
	var sflag = true;
	var eflag = true;
	
	$("input[id=stime]").on("focus", function(){
		var x = $(this).val();
		x = removeHipen(x);
		$(this).val(x);
	}).on("keydown", function(e){
		if(e.keyCode=="13"){
			$(this).blur();
		}
	}).on("focusout", function(){
		var x = $(this).val();
		if(x && x.length > 0){
			if(!$.isNumeric(x)){
				x = x.replace(/[^0-9]/g,"");
			}
			x = addDateHipen(x);
			$(this).val(x);
			sflag = true;
		} 
	}).on("keyup", function(){
		if(sflag){
			//$("input[id=stime]").val("");
			sflag=false;
		}
		$(this).val($(this).val().replace(/[^0-9]/g,""));
	});

	
	$("input[id=etime]").on("focus", function(){
		var x = $(this).val();
		x = removeHipen(x);
		$(this).val(x);
	}).on("keydown", function(e){
		if(e.keyCode=="13"){
			$(this).blur();
		}
	}).on("focusout", function(){
		var x = $(this).val();
		if(x && x.length > 0){
			if(!$.isNumeric(x)){
				x = x.replace(/[^0-9]/g,"");
			}
			x = addDateHipen(x);
			$(this).val(x);
			eflag = true;
		} 
	}).on("keyup", function(){
		if(eflag){
			//$("input[id=etime]").val("");
			sflag=false;
		}
		$(this).val($(this).val().replace(/[^0-9]/g,""));
	});
}

function addDateHipen(x){
	var date = x.toString();
	var output = date.slice(0,4) + "-" + date.slice(4,6) + "-" + date.slice(6);
	return output;
}

function removeHipen(x){
	if(!x || x.length == 0) return "";
    else return x.split("-").join("");
}
