$(document).ready(function () {
    $('#noticeForm').on('submit', function (event) {
        event.preventDefault();

        const noticeId = $('input[name="id"]').val();
        let title = $('input[name="title"]').val();
        let content = $('textarea[name="content"]').val();
        const customsId = $('input[name="customsId"]').val();

        const noticeData = {
            title: title,
            content: content,
            customsId: customsId
        };

        $.ajax({
            url: '/customs/notices/' + noticeId,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(noticeData),
            success: function () {
                window.location.href = '/customs/notices/' + noticeId;
            },
            error: function (xhr) {
                alert('공지사항 수정에 실패했습니다.'));
            }
        });
    });
});
