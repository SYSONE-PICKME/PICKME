document.addEventListener('DOMContentLoaded', function() {
    const fileInput = document.getElementById('campaignImage');
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

    const form = document.getElementById('campaignForm');
    form.addEventListener('submit', function(e) {
        if (!fileInput.files.length) {
            e.preventDefault();
            alert('캠페인 이미지를 선택해주세요.');
        }
    });
});
