//사업자인 경우에만 사업자 등록번호 필드 보여주기
document.addEventListener('DOMContentLoaded', function () {
    let userType = document.getElementById('userTypeInput').value;

    if (userType === 'BUSINESS') {
        document.getElementById('businessField').style.visibility = 'visible';
    }
});

//비밀번호 업데이트
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

        $.ajax({
            url: '/user/password',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({
                originPassword: originPassword,
                newPassword: newPassword.value
            }),
            success: function (response) {
                alert('비밀번호가 성공적으로 변경되었습니다.');
            },
            error: function (xhr) {
                alert(xhr.responseText);
            }
        });
    });
});

//내 정보 업데이트
document.addEventListener('DOMContentLoaded', () => {
    document.querySelector('.info-form').addEventListener('submit', function (e) {
        e.preventDefault();

        const name = document.getElementById('name').value;
        const addr = document.getElementById('addr').value;
        const email = document.getElementById('email').value;
        const phoneNum = document.getElementById('phoneNum').value;
        const businessNum = document.getElementById('businessNum').value;

        $.ajax({
            url: '/user/info',
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify({
                name: name,
                addr: addr,
                email: email,
                phoneNum: phoneNum,
                businessNum: businessNum
            }),
            success: function (response) {
                alert('내정보가 정상적으로 변경되었습니다.');
            },
            error: function (xhr) {
                alert(xhr.responseText);
            }
        })
    })
})

document.addEventListener('DOMContentLoaded', () => {
    const infoForm = document.querySelector('.info-form');
    const passwordForm = document.querySelector('.password-form');
    const infoCancelButton = document.querySelector('.info-form .cancel-btn');
    const passwordCancelButton = document.querySelector('.password-form .cancel-btn');
    const passwordSubmitButton = document.querySelector('.password-form .submit-btn');

    // 초기화 로직: 내 정보 폼
    infoCancelButton.addEventListener('click', () => {
        infoForm.reset();
    });

    // 초기화 로직: 비밀번호 폼
    passwordCancelButton.addEventListener('click', () => {
        passwordForm.reset();  // 비밀번호 폼을 초기 상태로 복원
        passwordSubmitButton.disabled = true;  // 버튼 비활성화
        document.getElementById('password-match-message').style.visibility = 'hidden';
    });
})
