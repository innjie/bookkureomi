function checkAName(aName) {
    if(aName == '' || aName == undefined) {
        alert("주소명을 입력하세요.");
        return true;
    }
    return false;
}
function checkAddress(address) {
    if(address == '' || address == undefined) {
        alert("주소를 입력하세요.");
        return true;
    }
    return false;
}
function checkZipcode(zipcode) {
    if(zipcode == '' || zipcode == undefined) {
        alert("우편번호를 입력하세요.");
        return true;
    } else if(!Number.isInteger(parseInt(zipcode))) {
        alert("우편번호는 숫자로 입력하세요.");
        return true;
    } else if(zipcode.length != 5) {
        alert("우편번호는 5자리입니다.");
        return true;
    }
    return false;
}