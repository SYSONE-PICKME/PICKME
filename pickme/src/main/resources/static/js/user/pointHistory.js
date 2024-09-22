document.addEventListener("DOMContentLoaded", function() {
    loadPointHistory(0, 6);  // 첫 번째 페이지, 페이지 크기 6
});

function loadPointHistory(page, size) {
    $.ajax({
        url: '/user/point/history',
        method: 'GET',
        data: { page: page, size: size },  // 페이지와 크기를 요청
        contentType: 'application/json',
        success: function(data) {
            if (data.success) {
                updatePointHistoryContent(data.data.content);  // 포인트 내역 데이터를 갱신
                updatePagination(data.data);  // 페이지네이션 갱신
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

    const totalPages = pageData.totalPages;
    const currentPage = pageData.number;

    // 이전 페이지 링크
    if (currentPage > 0) {
        paginationContainer.innerHTML += `<a href="#" onclick="loadPointHistory(${currentPage - 1}, 6)">&laquo; 이전</a>`;
    }

    // 페이지 번호들
    for (let i = 0; i < totalPages; i++) {
        paginationContainer.innerHTML += `<a href="#" onclick="loadPointHistory(${i}, 6)">${i + 1}</a>`;
    }

    // 다음 페이지 링크
    if (currentPage < totalPages - 1) {
        paginationContainer.innerHTML += `<a href="#" onclick="loadPointHistory(${currentPage + 1}, 6)">다음 &raquo;</a>`;
    }
}

function formatCurrency(value) {
    return value.toLocaleString('ko-KR');
}
