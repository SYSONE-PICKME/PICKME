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
        e.preventDefault();
        if (!fileInput.files.length && !previewImage.src.includes('data:image')) {
            alert('캠페인 이미지를 선택해주세요.');
            return;
        }

        const formData = new FormData(form);
        const url = form.getAttribute('action');
        const isEditing = url.includes('edit');

        $.ajax({
            url: url,
            type: isEditing ? 'PUT' : 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                if (response.success) {
                    const campaignId = isEditing ? url.split('/').pop() : response.data;
                    window.location.href = '/customs/campaigns/' + campaignId;
                } else {
                    alert('오류가 발생했습니다. 다시 시도해주세요.');
                }
            },
            error: function(xhr, status, error) {
                alert('서버 오류가 발생했습니다.');
            }
        });
    });
});
