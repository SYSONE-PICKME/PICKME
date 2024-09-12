function filterItems() {
    var toggle = document.getElementById("toggle").checked;
    var items = document.querySelectorAll('.product-card');

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
