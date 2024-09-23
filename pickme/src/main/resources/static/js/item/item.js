document.addEventListener('DOMContentLoaded', function () {
    const fileInput = document.getElementById('AddImgs');
    const preview = document.getElementById('Preview');

    fileInput.addEventListener('change', function (e) {
        const files = e.target.files;
        handleFiles(files);
    });

    const form = document.querySelector('form');
    if (!form) {
        console.error('폼을 찾을 수 없습니다.');
        return;
    }

    form.addEventListener('submit', function (event) {
        event.preventDefault(); // 기본 폼 제출 동작 방지
        console.log('폼 제출 이벤트 발생');

        const formData = new FormData(form);
        const xhr = new XMLHttpRequest();

        xhr.open('POST', form.action, true);

        xhr.onload = function () {
            if (xhr.status === 200) {
                alert('등록 성공했습니다.');
                window.location.href = '/customs/items'; // 성공 후 이동할 페이지 URL
            } else {
                alert('등록 실패했습니다. 다시 시도해 주세요.');
            }
        };

        xhr.onerror = function () {
            alert('네트워크 오류가 발생했습니다. 나중에 다시 시도해 주세요.');
            console.error('네트워크 오류:', xhr.statusText);
        };

        console.log('폼 데이터 전송 시작');
        xhr.send(formData);
    });

    function handleFiles(files) {
        // 미리보기 영역 초기화
        preview.innerHTML = '';

        let validFiles = false; // 파일이 유효한지 여부를 체크할 변수

        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            if (!file.type.startsWith('image/')) {
                // 이미지가 아닌 파일은 무시
                continue;
            }

            validFiles = true; // 유효한 파일이 하나라도 있으면 true로 설정

            const reader = new FileReader();
            reader.onload = (function (file) {
                return function (e) {
                    const li = document.createElement('li');
                    li.innerHTML = `
                    <img src="${e.target.result}" alt="${file.name}">
                    <button type="button" class="delBtn" aria-label="삭제">x</button>
                `;
                    preview.appendChild(li);

                    li.querySelector('.delBtn').addEventListener('click', function () {
                        li.remove();
                    });
                };
            })(file);
            reader.readAsDataURL(file);
        }

        // 유효한 이미지 파일이 없으면 사용자에게 알림
        if (!validFiles) {
            alert('이미지 파일만 업로드할 수 있습니다. 올바른 파일을 선택해 주세요.');
        }
    }

// Sortable 기능 설정
    new Sortable(preview, {
        animation: 150,
        ghostClass: 'blue-background-class'
    });
});

function toggleDropdown() {
    document.getElementById('lawsDropdown').classList.toggle('open');
}

document.addEventListener('click', function(e) {
    const dropdown = document.getElementById('lawsDropdown');
    if (!dropdown.contains(e.target)) {
        dropdown.classList.remove('open');
    }
});

document.querySelectorAll('#dropdownContent input[type="checkbox"]').forEach(function(checkbox) {
    checkbox.addEventListener('change', function() {
        updateSelectedOptions();
    });
});

function updateSelectedOptions() {
    const selected = Array.from(document.querySelectorAll('#dropdownContent input[type="checkbox"]:checked'))
        .map(function(checkbox) {
            return checkbox.nextElementSibling.textContent;
        });

    const selectedText = selected.length > 0 ? selected.join(', ') : '선택된 법규 없음';
    document.getElementById('selectedOptions').textContent = selectedText;
}
