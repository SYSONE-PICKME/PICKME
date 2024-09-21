$(document).ready(function() {
    var IMP = window.IMP;
    IMP.init("imp12712062");
    var point = document.querySelector('#updatedPoints');
    var currentPoints = parseInt(point.getAttribute('data-point'));
    var userId = document.getElementById('userId').value;

    // 기본 충전 금액 설정
    const defaultAmount = 10000
    $('#chargeAmount').text(parseInt(defaultAmount).toLocaleString() + ' P');
    $('#updatedPoints').text((currentPoints + defaultAmount).toLocaleString() + ' P');

    // 라디오 버튼 클릭 시 충전할 금액과 충전 후 포인트 업데이트
    $('input[name="chargeAmount"]').on('change', function() {
        const chargeAmount = parseInt($(this).val());
        $('#chargeAmount').text(chargeAmount.toLocaleString() + ' P'); // 충전할 포인트 표시

        const updatedPoints = currentPoints + chargeAmount;
        $('#updatedPoints').text(updatedPoints.toLocaleString() + ' P'); // 충전 후 포인트 계산
    });

    // 충전하기 버튼 클릭 시 동작
    $('#chargeButton').on('click', function() {
        const selectedAmount = parseInt($('input[name="chargeAmount"]:checked').val());

        IMP.request_pay({
                pg: "kakaopay.TC0ONETIME",
                pay_method: "card",
                merchant_uid: 'merchant_' + new Date().getTime(),
                name: "Pick Me",
                amount: selectedAmount,
                buyer_email: 'test@naver.com',
                buyer_name: 'test',
                buyer_tel: '010-1234-5678',
                buyer_addr: '서울특별시',
                buyer_postcode: '123-456',
            },
            function(response) {
                if (response.success) {
                    const request = {
                        impUid: response.imp_uid,
                        point: selectedAmount,
                        userId: userId
                    };
                    $.ajax({
                        url: "/charge/complete", // 서버의 결제 완료 처리 URL
                        method: "POST",
                        data: JSON.stringify(request),
                        contentType: "application/json",

                        success: function(data) {
                            alert("결제가 성공적으로 완료되었습니다.");
                            location.href = '/user/charge';
                        },
                        error: function(error) {
                            alert("결제 처리 중 오류가 발생했습니다.");
                        }
                    });
                } else {
                    alert("결제에 실패했습니다: " + response.error_msg);
                }
            });
    });
});
