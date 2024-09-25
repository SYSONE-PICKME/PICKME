// 데이터 로딩 중인지 확인하는 플래그
let isLoading = false;

// 데이터가 더 이상 없는지 확인하는 플래그
let noMoreData = false;

// 페이지네이션을 위한 커서 정보
let cursor = {
    itemId: null,
    status: null,
    endTime: null,
    category: null
};

function applyFilter(category) {
    cursor.itemId = null; // 커서 정보 초기화
    cursor.status = null;
    cursor.endTime = null;
    cursor.category = category; // 카테고리 필터 적용

    $('.product-grid').empty(); // 기존 아이템 목록 초기화
    noMoreData = false; // 데이터 없음 플래그 초기화
    loadItems(); // 필터 적용된 아이템 로드
}

// AJAX 요청으로 데이터를 가져오고 HTML에 표시하는 함수
function loadItems() {
    if (isLoading || noMoreData) return; // 로딩 중이거나 더 이상 데이터가 없으면 요청하지 않음

    isLoading = true; // 로딩 상태로 설정

    $.ajax({
        url: '/user/api/item/list',
        type: 'GET',
        data: cursor, // cursor 객체를 쿼리 파라미터로 보냄
        success: function(response) {
            const items = response.data; // API가 반환한 items 데이터

            console.log(items);
            // 데이터가 있는 경우 화면에 추가
            if (items.length > 0) {
                items.forEach(item => {
                const formattedStartTime = dayjs(item.startTime).format('YYYY-MM-DD HH:mm');
                const formattedEndTime = dayjs(item.endTime).format('YYYY-MM-DD HH:mm');

                    $('.product-grid').append(`
                        <div class="product-card" data-status="${item.status}" data-item-id="${item.id}">
                            <span class="status-label ${item.status == 'OPEN' ? 'status-open' : (item.status == 'NOT_OPEN' ? 'status-not-open' : 'status-closed')}">
                                ${item.status == 'OPEN' ? '진행중' : (item.status == 'NOT_OPEN' ? '진행 예정' : '마감')}
                            </span>
                            <button class="heart ${item.liked ? 'active' : ''}" type="button" data-liked="${item.liked}" data-item-id="${item.id}" data-action-url="/user/item/${item.id}/like">
                                <i class="${item.liked ? 'fas' : 'far'} fa-heart"></i>
                            </button>
                            <img src="${item.imgUrl}" alt="product image" class="product-image">
                            <div class="product-info">
                                <h3 class="item-name" data-item-id="${item.id}">${item.name}</h3>
                                <p class="item-info">공매예정가격: <span id="auction-price">${item.price.toLocaleString()}</span> 원</p>
                                <p class="item-info">공매시작일시: <span id="auction-date">${formattedStartTime}</span></p>
                                <p class="item-info">공매마감일시: <span id="auction-end_date">${formattedEndTime}</span></p>
                            </div>
                        </div>
                    `);
                });

                // 커서 정보 업데이트: 마지막 아이템의 정보를 커서로 설정
                const lastItem = items[items.length - 1];
                cursor.itemId = lastItem.id;
                cursor.status = lastItem.status;
                cursor.endTime = lastItem.endTime; // ISO 8601 형식으로 변환

                isLoading = false; // 로딩 상태 해제
            } else {
                // 더 이상 데이터가 없으면 noMoreData를 true로 설정
                noMoreData = true;
            }
        },
        error: function(xhr, status, error) {
            console.error('AJAX 호출 실패:', status, error);
            isLoading = false; // 오류 발생 시 로딩 상태 해제
        }
    });
}

// 스크롤 이벤트 감지하여 무한 스크롤 구현
$(window).scroll(function() {
    // 스크롤이 끝에 도달했을 때 추가 데이터 로드
    if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
        loadItems();
    }
});
