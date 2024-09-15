document.addEventListener('DOMContentLoaded', function() {
    const tabs = document.querySelectorAll('.tab');
    const noticeContent = document.getElementById('noticeContent');
    const campaignContent = document.getElementById('campaignContent');
    const noticeTypeInput = document.getElementById('noticeType');
    const contentTextarea = document.getElementById('content');
    const fileInput = document.getElementById('campaignImage');

    function setTabState(isNotice) {
        noticeContent.style.display = isNotice ? 'block' : 'none';
        campaignContent.style.display = isNotice ? 'none' : 'block';
        noticeTypeInput.value = isNotice ? 'notice' : 'campaign';
        contentTextarea.required = isNotice;

        if (!isNotice) {
            contentTextarea.disabled = true;
            contentTextarea.value = '';
        } else {
            contentTextarea.disabled = false;
        }
    }

    // 초기 상태 설정
    setTabState(noticeTypeInput.value === 'notice');

    tabs.forEach(tab => {
        tab.addEventListener('click', function() {
            const tabName = this.getAttribute('data-tab');
            tabs.forEach(t => t.classList.remove('active'));
            this.classList.add('active');
            setTabState(tabName === 'notice');
        });
    });

    const imageUploadArea = document.querySelector('.image-upload-area');
    const previewImage = document.getElementById('previewImage');

    imageUploadArea.addEventListener('click', function() {
        fileInput.click();
    });

    fileInput.addEventListener('change', function(e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                previewImage.src = e.target.result;
            }
            reader.readAsDataURL(file);
        }
    });
});

const form = document.getElementById('noticeForm');
form.addEventListener('submit', function(e) {
    const isNotice = noticeTypeInput.value === 'notice';
    if (isNotice && !contentTextarea.value.trim()) {
        e.preventDefault();
        alert('공지사항 내용을 입력해주세요.');
    } else if (!isNotice && !fileInput.files.length) {
        e.preventDefault();
        alert('캠페인 이미지를 선택해주세요.');
    }
});
