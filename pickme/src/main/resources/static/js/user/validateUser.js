$(document).ready(function() {
    const userIdInput = $('#id');
    const checkButton = $('#check-btn');
    const idCheckResult = $('#id-check-result');

    userIdInput.on('input', function (){
        const isInputFilled = userIdInput.val().trim() != '';
        checkButton.prop('disabled', !isInputFilled);
    })

    checkButton.click(function(event) {
        event.preventDefault();
        const id = userIdInput.val().trim();

        $.ajax({
            url: `/user/check-id/${id}`,
            type: 'GET',
            success: function(response) {
                // 서버에서 받은 응답을 idCheckResult 요소에 표시
                idCheckResult.text(response).css('color', 'green');
            },
            error: function(xhr) {
                idCheckResult.text(xhr.responseText);
            }
        });
    });

    const emailButton = $('#email-btn');
    emailButton.checkIntersection(function(event){
        event.preventDefault();

        $.ajax({
            url: `/user/check-email/${email}`,
            type: 'GET',
            success: function(response) {
                //인증번호 반환 해줄 예정
            },
            error: function(xhr){
                console.log(xhr.responseText);
            }
        })
    })
});
