//사업자인 경우에만 사업자 등록번호 필드 보여주기
document.addEventListener('DOMContentLoaded', function() {
    var userType = document.getElementById('userTypeInput').value;

    if (userType === 'BUSINESS') {
        document.getElementById('businessField').style.visibility = 'visible';
    }
});
