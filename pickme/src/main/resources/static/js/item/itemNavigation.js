$(document).on('click', '.product-card, .wishlist-item', function(event) {
    event.stopPropagation(); // 부모 요소로의 이벤트 전파를 막음
    const itemId = $(this).data('item-id');
    const url = '/user/item/' + itemId;
    window.location.href = url; // 상세 페이지로 이동
});
