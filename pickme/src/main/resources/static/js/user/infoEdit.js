//사업자인 경우에만 사업자 등록번호 필드 보여주기
document.addEventListener('DOMContentLoaded', function() {
    let userType = document.getElementById('userTypeInput').value;

    if (userType === 'BUSINESS') {
        document.getElementById('businessField').style.visibility = 'visible';
    }
});

document.addEventListener('DOMContentLoaded', () => {
    const newPassword = document.getElementById('new-password');
    const passwordConfirm = document.getElementById('confirm-password');
    const passwordMatchMessage = document.getElementById('password-match-message');
    const submitButton = document.querySelector('.password-form .submit-btn');

    // 비밀번호 일치 확인 함수
    const checkPasswordMatch = () => {
        const passwordMatch = newPassword.value === passwordConfirm.value;
        if (passwordMatch || !passwordConfirm.value) {
            passwordMatchMessage.style.visibility = 'hidden';
            passwordMatchMessage.style.opacity = '0';
        } else {
            passwordMatchMessage.style.visibility = 'visible';
            passwordMatchMessage.style.opacity = '1';
        }
        submitButton.disabled = !passwordMatch;
    };

    [newPassword, passwordConfirm].forEach(input => input.addEventListener('input', checkPasswordMatch));

    document.querySelector('.password-form').addEventListener('submit', function (e) {
        e.preventDefault(); // 기본 폼 제출 방지

        const originPassword = document.getElementById('origin-password').value;

        // 비밀번호 값 확인을 위해 로그 출력
        console.log('New Password:', newPassword.value);
        console.log('Current Password:', originPassword);

        $.ajax({
            url: '/user/password',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({
                originPassword: originPassword,
                newPassword: newPassword.value
            }),
            success: function(response) {
                alert('비밀번호가 성공적으로 변경되었습니다.');
            },
            error: function(xhr) {
                alert(xhr.responseText);
            }
        });
    });
});
