function filterItems(titleText) {
    var toggle = document.getElementById("toggle").checked;
    var items = document.querySelectorAll('.product-card');
    const title = document.getElementById("title");

    if (toggle) {
            title.innerText = '진행중인 경매';
        } else {
            title.innerText = titleText;
        }

    items.forEach(function(item) {
        var status = item.getAttribute('data-status');

        if (toggle) {
            // 토글이 체크되면 'OPEN' 상태인 아이템만 보여줌
            if (status === 'OPEN') {
                item.style.display = 'block';
            } else {
                item.style.display = 'none';
            }
        } else {
            item.style.display = 'block';
        }
    });
}

// 필터를 선택하거나 변경했을 때 아이템을 다시 로드하는 함수
function applyFilter(category) {
    cursor.itemId = null; // 커서 정보 초기화
    cursor.status = null;
    cursor.endTime = null;
    cursor.category = category; // 카테고리 필터 적용

    $('.product-grid').empty(); // 기존 아이템 목록 초기화
    noMoreData = false; // 데이터 없음 플래그 초기화
    loadItems(); // 필터 적용된 아이템 로드
}

// 카테고리 클릭 이벤트 처리
$(document).on('click', '.category-link', function(event) {
    event.preventDefault(); // 기본 링크 동작 방지
    const category = $(this).data('category');
    applyFilter(category); // 필터 적용
});
