document.addEventListener("DOMContentLoaded", function() {
    loadPointHistory(0, 6);
});

function loadPointHistory(page, size) {
    $.ajax({
        url: '/user/point/history',
        method: 'GET',
        data: { page: page, size: size },
        contentType: 'application/json',
        success: function(data) {
            if (data.success) {
                updatePointHistoryContent(data.data.content);
                updatePagination(data.data, 'loadPointHistory', size);
            } else {
                console.error('포인트 내역을 불러오는데 실패했습니다.');
            }
        },
        error: function(error) {
            console.error('에러 발생:', error);
        }
    });
}

function updatePointHistoryContent(pointHistories) {
    const container = document.getElementById('point-history-content');
    container.innerHTML = '';  // 기존 내용 삭제

    const templateSource = document.getElementById('history-template').innerHTML;
    const template = Handlebars.compile(templateSource);

    pointHistories.forEach(pointHistory => {
        const context = {
            type: pointHistory.type,
            iconSrc: pointHistory.type === 1 ? '/images/chargeCoin.png' : '/images/paymentCoin.png',
            typeText: pointHistory.type === 1 ? '충전 금액:' : '결제 금액:',
            price: formatCurrency(pointHistory.price),
            priceStyle: new Handlebars.SafeString(`color: ${pointHistory.type === 1 ? '#007BFF' : '#FF0000'};`),
            currentPoint: formatCurrency(pointHistory.currentPoint),
            time: new Date(pointHistory.time).toLocaleString()
        };

        const html = template(context);
        container.innerHTML += html;
    });
}

function formatCurrency(value) {
    return value.toLocaleString('ko-KR');
}
