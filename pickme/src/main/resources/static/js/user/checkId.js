$(document).ready(function () {
    const userIdInput = $('#id');
    const checkButton = $('#check-btn');
    const idCheckResult = $('#id-check-result');
    const signUpButton = $('#submit-btn');

    checkButton.click(function (event) {
        event.preventDefault();
        const id = userIdInput.val().trim();

        $.ajax({
            url: "/user/check-id",
            type: 'POST',
            contentType: 'text/plain',
            data: id,
            success: function (response) {
                // 서버에서 받은 응답을 idCheckResult 요소에 표시
                idCheckResult.text(response.data).css('color', 'green');
                signUpButton.prop('disabled', false);
            },
            error: function (xhr) {
                idCheckResult.text(xhr.responseText);
            }
        });
    });
});
