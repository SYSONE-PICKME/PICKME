//회원가입 시 사업자, 개인 선택하는 부분
document.addEventListener("DOMContentLoaded", function () {
    const tabs = document.querySelectorAll(".tab-btn");

    tabs.forEach(tab => {
        tab.addEventListener("click", function () {
            tabs.forEach(t => t.classList.remove('active'));
            this.classList.add('active');
        });
    });
});

//이메일 필드 다 입력한 경우 활성화 시키기
document.addEventListener('DOMContentLoaded', function () {
    const emailInput = document.getElementById('email');
    const emailDomainSelect = document.getElementById('emailDomain');
    const emailButton = document.getElementById('email-btn');

    function toggleEmailButton() {
        if (emailInput.value && emailDomainSelect.value) {
            emailButton.disabled = false;
        } else {
            emailButton.disabled = true;
        }
    }

    emailInput.addEventListener('input', toggleEmailButton);
    emailDomainSelect.addEventListener('change', toggleEmailButton);

    toggleEmailButton();
});

//아이디 필드 입력한 경우 버튼 활성화
document.addEventListener('DOMContentLoaded', function () {
    const idInput = document.getElementById('id');
    const checkButton = document.getElementById('check-btn');

    function toggleCheckButton() {
        if (idInput.value) {
            checkButton.disabled = false;
        } else {
            checkButton.disabled = true;
        }
    }

    idInput.addEventListener('input', toggleCheckButton);

    toggleCheckButton();
})

//사업자인 경우 사업자 등록번호 입력란 보여주기
function showForm(type) {
    const businessNumberGroup = document.getElementById('business-number-group');
    const personalTab = document.querySelector('.tab-btn.user');
    const businessTab = document.querySelector('.tab-btn.business');

    if (type == 'business') {
        businessNumberGroup.style.display = 'block';
        personalTab.classList.add('active');
        businessTab.classList.remove('active');
    } else {
        businessNumberGroup.style.display = 'none';
        personalTab.classList.remove('active');
        businessTab.classList.add('active');
    }

    // 사업자 폼 표시 여부 설정
    document.getElementById('business-number-group').style.display = type === 'business' ? 'block' : 'none';

    // 탭 버튼 클래스 업데이트
    document.querySelector('.tab-btn.user').classList.toggle('active', type === 'user');
    document.querySelector('.tab-btn.business').classList.toggle('active', type === 'business');

    document.querySelector('input[name="type"]').value = type;
}
