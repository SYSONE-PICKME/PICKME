$(document).ready(function() {
    $('.item-name').on('click', function() {
        const itemId = $(this).data('item-id');
        const url = '/user/item/' + itemId;
        window.location.href = url;
    });
});
