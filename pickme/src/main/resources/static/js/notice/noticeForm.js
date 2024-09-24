document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('noticeForm');
    form.addEventListener('submit', function(e) {
        e.preventDefault();

        const formData = new FormData(form);
        const url = form.getAttribute('action');
        const isEditing = form.getAttribute('action').match(/\/customs\/notices\/\d+$/);

        $.ajax({
            url: url,
            type: isEditing ? 'PUT' : 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                if (response.success) {
                    const noticeId = isEditing ? url.split('/').pop() : response.data;
                    window.location.href = '/customs/notices/' + noticeId;
                } else {
                    alert('오류가 발생했습니다: ' + response.message);
                }
            },
            error: function(xhr, status, error) {
                alert('서버 오류가 발생했습니다.');
                console.error(xhr.responseText);
            }
        });
    });
});
