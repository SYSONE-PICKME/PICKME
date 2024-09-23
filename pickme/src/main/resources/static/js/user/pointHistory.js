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
                updatePagination(data.data);
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

function updatePagination(pageData) {
    const paginationContainer = document.getElementById('pagination');
    paginationContainer.innerHTML = '';  // 기존 페이지네이션 삭제

    const totalPages = pageData.totalPages;  // 전체 페이지 수
    const currentPage = pageData.number;    // 현재 페이지 (0부터 시작)

    // 처음 페이지로 이동 버튼
    paginationContainer.innerHTML += `<a href="#" class="first-page ${currentPage === 0 ? 'disabled' : ''}" onclick="loadPointHistory(0, 6)">&laquo;&laquo;</a>`;

    // 이전 페이지로 이동 버튼
    paginationContainer.innerHTML += `<a href="#" class="prev-page ${currentPage === 0 ? 'disabled' : ''}" onclick="loadPointHistory(${currentPage - 1}, 6)">&laquo;</a>`;

    // 페이지 번호들
    for (let i = 0; i < totalPages; i++) {
        paginationContainer.innerHTML += `<a href="#" class="page-number ${i === currentPage ? 'active' : ''}" onclick="loadPointHistory(${i}, 6)">${i + 1}</a>`;
    }

    // 다음 페이지로 이동 버튼
    paginationContainer.innerHTML += `<a href="#" class="next-page ${currentPage === totalPages - 1 ? 'disabled' : ''}" onclick="loadPointHistory(${currentPage + 1}, 6)">&raquo;</a>`;

    // 마지막 페이지로 이동 버튼
    paginationContainer.innerHTML += `<a href="#" class="last-page ${currentPage === totalPages - 1 ? 'disabled' : ''}" onclick="loadPointHistory(${totalPages - 1}, 6)">&raquo;&raquo;</a>`;
}

function formatCurrency(value) {
    return value.toLocaleString('ko-KR');
}
