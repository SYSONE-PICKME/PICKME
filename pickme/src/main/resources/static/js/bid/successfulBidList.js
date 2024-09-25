document.addEventListener("DOMContentLoaded", () => {
    loadPaymentList()
});

function loadPaymentList() {
    $.ajax({
        url: '/user/bid/successful-list',
        method: 'GET',
        success: function (response) {
            let source = document.getElementById('payment-template').innerHTML;
            let template = Handlebars.compile(source);

            let data = {
                successfulBidList: response.data
            };

            let html = template(data);
            $('#product-grid').html(html);
        },
        error: function (error) {
            console.error("결제 목록을 불러오는 중 오류가 발생했습니다:", error);
        }
    });
}
