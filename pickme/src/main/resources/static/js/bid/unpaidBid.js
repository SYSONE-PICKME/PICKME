Handlebars.registerHelper('formatPrice', function(price) {
    return price.toLocaleString();  // 숫자에 콤마 추가
});

document.addEventListener("DOMContentLoaded", function () {
    loadUnpaidBidList();
});

function loadUnpaidBidList() {
    $.ajax({
        url: '/user/bid/unpaid',  // JSON 데이터를 가져올 URL
        method: 'GET',
        success: function (response) {
            const unPaidList = response.data;  // 서버에서 받은 관심 상품 목록
            const templateSource = document.getElementById('unpaidBidList-template').innerHTML;
            const template = Handlebars.compile(templateSource);  // Handlebars 템플릿 컴파일

            const html = template({unPaidList: unPaidList});  // 데이터를 템플릿에 바인딩

            // 컨테이너에 생성된 HTML 삽입
            $('#unpaidBidList-container').html(html);

            console.log(response.data);
        },
        error: function (xhr, status, error) {
            console.error('관심 상품 목록을 불러오는 중 오류가 발생했습니다:', error);
        }
    });
}
