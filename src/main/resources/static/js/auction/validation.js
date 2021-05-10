function checkTitle(title) {
    if(title == '' || title == undefined) {
        alert("제목을 입력하세요.");
        return true;
    }
    return false;
}
function checkPublisher(publisher) {
    if(publisher == '' || publisher == undefined) {
        alert("출판사를 입력하세요.");
        return true;
    }
    return false;
}
function checkBidPrice(bidPrice) {
    if(bidPrice == '' || bidPrice == undefined) {
        alert("입찰가를 입력하세요.");
        return true;
    } else if(!Number.isInteger(parseInt(bidPrice))) {
        alert("원가는 숫자로 입력하세요.");
        return true;
    }
    return false;
}
function checkImmediPrice(immediPrice) {
    if(immediPrice == '' || immediPrice == undefined) {
        alert("즉시구매가를 입력하세요.");
        return true;
    } else if(!Number.isInteger(parseInt(immediPrice))) {
        alert("원가는 숫자로 입력하세요.");
        return true;
    }
    return false;
}
function checkPrices(bidPrice, immediPrice) {
    if(bidPrice > immediPrice) {
        alert("즉시구매가는 입찰가보다 커야 합니다.");
        return true;
    }
    return false;
}
function checkGenre(genreType) {
    if (genreType == '' || genreType == undefined) {
        alert("장르를 입력하세요.");
        return true;
    }
    return false;
}
function checkInfo(info) {
    if (info == '' || info == undefined) {
        alert("정보를 입력하세요.");
        return true;
    }
    return false;
}
function checkEndDate(endDate) {
    var result = true;
    try {
        var date = endDate.split("-");
        var y = parseInt(date[0], 10),
            m = parseInt(date[1], 10),
            d = parseInt(date[2], 10);

        var dateRegex = /^(?=\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\x20|$))|(?:2[0-8]|1\d|0?[1-9]))([-.\/])(?:1[012]|0?[1-9])\1(?:1[6-9]|[2-9]\d)?\d\d(?:(?=\x20\d)\x20|$))?(((0?[1-9]|1[012])(:[0-5]\d){0,2}(\x20[AP]M))|([01]\d|2[0-3])(:[0-5]\d){1,2})?$/;
        result = dateRegex.test(d+'-'+m+'-'+y);
        if(result == false) {
            alert("날짜 형식은 yyyy-mm-dd 입니다.");
            return result;
        }
    } catch (err) {
        ;
    }

    var dateEnd = new Date(endDate);
    var today = new Date();

    if(dateEnd < today) {
        alert("마감 날짜는 오늘보다 늦어야 합니다.");
        result = false;
        return result;
    }


    return result;
}