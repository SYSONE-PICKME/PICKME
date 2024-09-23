function filterItems() {
    var toggle = document.getElementById("toggle").checked;
    var items = document.querySelectorAll('.product-card');
    const title = document.getElementById("title");

    if (toggle) {
            title.innerText = '진행중인 경매';
        } else {
            title.innerText = '모든 경매 물품';
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
