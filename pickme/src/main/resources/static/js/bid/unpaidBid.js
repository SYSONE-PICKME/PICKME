Handlebars.registerHelper('formatPrice', function(price) {
    return price.toLocaleString();  // 숫자에 콤마 추가
});

document.addEventListener("DOMContentLoaded", function () {
    loadUnpaidBidList(0, 6);
});

function loadUnpaidBidList(page, size) {
    $.ajax({
        url: '/user/bid/unpaid',  // JSON 데이터를 가져올 URL
        method: 'GET',
        data: { page: page, size: size },
        success: function (response) {
            const unPaidList = response.data.content;  // 서버에서 받은 관심 상품 목록
            const templateSource = document.getElementById('unpaidBidList-template').innerHTML;
            const template = Handlebars.compile(templateSource);  // Handlebars 템플릿 컴파일

            const html = template({unPaidList: unPaidList});  // 데이터를 템플릿에 바인딩

            // 컨테이너에 생성된 HTML 삽입
            $('#unpaidBidList-container').html(html);

            updatePagination(response.data, 'loadUnpaidBidList', size);

            $(".payment-button").on("click", function() {
                let bidId = $(this).data("bid-id");  // data-bid-id에서 값을 가져옴
                let price = $(this).data("price-id");  // data-price-id에서 값을 가져옴

                // 결제 로직 호출
                handlePayment(bidId, price);
            });
        },
        error: function (xhr, status, error) {
            console.error('오류 발생', error);
        }
    });
}

// 결제 로직 처리 함수
function handlePayment(bidId, price) {
    let paymentDto = {
        bidId: bidId,
        price: price
    };

    console.log(paymentDto, "============================");

    // AJAX를 이용한 POST 요청
    $.ajax({
        url: '/user/payment',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(paymentDto),  // PaymentDto를 JSON 형식으로 변환하여 전송
        success: function(response) {
            if (response.success) {
                alert('결제가 성공적으로 처리되었습니다.');
                // 추가 로직: 결제 후 페이지 이동 또는 상태 업데이트
            } else {
                alert('결제 처리에 실패했습니다: ' + response.message);
            }
        },
        error: function(error) {
            alert('결제 요청 중 오류가 발생했습니다.');
            console.error("Error during payment:", error);
        }
    });
}
