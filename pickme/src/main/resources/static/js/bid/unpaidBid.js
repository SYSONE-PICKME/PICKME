Handlebars.registerHelper('formatPrice', function(price) {
    return price.toLocaleString();  // 숫자에 콤마 추가
});

document.addEventListener("DOMContentLoaded", function () {
    loadUnpaidBidList(0, 6);
});

function loadUnpaidBidList(page, size) {
    $.ajax({
        url: '/user/bid/unpaid',
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
                let bidId = $(this).data("bid-id");
                let price = $(this).data("price-id");

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

    $.ajax({
        url: '/user/payment',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(paymentDto),
        success: function(response) {
            if (response.success) {
                alert('결제가 성공적으로 처리되었습니다.');
            } else {
                alert('포인트가 부족합니다. \n포인트 충전 후 결제 해주세요 ');
            }
        },
        error: function(error) {
            alert('결제 요청 중 오류가 발생했습니다.');
            console.error("Error during payment:", error);
        }
    });
}
