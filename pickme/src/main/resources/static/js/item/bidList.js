function filterItems() {
    var toggle = document.getElementById("toggle").checked;
    var items = document.querySelectorAll('.product-card');
    const title = document.getElementById("title");

    if (toggle) {
            title.innerText = '진행중인 경매';
        } else {
            title.innerText = '내 입찰 목록';
        }

    items.forEach(function(item) {
        var status = item.getAttribute('data-status');

        if (toggle) {
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
