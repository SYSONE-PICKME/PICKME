Handlebars.registerHelper('formatPrice', function(price) {
    return price.toLocaleString();  // 숫자에 콤마 추가
});

document.addEventListener("DOMContentLoaded", () => {
    loadPaymentList()
});

function loadPaymentList(page, size) {
    $.ajax({
        url: '/user/bid/successful-list',
        method: 'GET',
        data: { page: page, size: size },
        success: function (response) {
            updatePaymentListContent(response.data.content)
            updatePagination(response.data, 'loadPaymentList', size);
        },
        error: function (error) {
            console.error("결제 목록을 불러오는 중 오류가 발생했습니다:", error);
        }
    });
}

function updatePaymentListContent(payments){
    let source = document.getElementById('payment-template').innerHTML;
    let template = Handlebars.compile(source);

    let data = { payments: payments };
    let html = template(data);

    $('#product-grid').html(html);
}
