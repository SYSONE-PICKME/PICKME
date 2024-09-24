$(document).ready(function() {
    $('.product-card, .wishlist-item').on('click', function(event) {
        event.stopPropagation();
        const itemId = $(this).data('item-id');
        const url = '/user/item/' + itemId;
        window.location.href = url;
    });
});
