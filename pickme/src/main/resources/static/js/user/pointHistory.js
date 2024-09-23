function formatCurrency(value) {
    return value.toLocaleString('ko-KR');
}

document.addEventListener("DOMContentLoaded", function () {
    const historyItems = document.querySelectorAll('.history-item');

    historyItems.forEach(item => {
        const priceAmountElement = item.querySelector('.price-amount');
        const currentPointAmountElement = item.querySelector('.current-point-amount');
        const type = item.getAttribute('data-type');

        if (type === '1') {
            priceAmountElement.style.color = '#007BFF';
            priceAmountElement.textContent = formatCurrency(parseFloat(priceAmountElement.textContent));
        } else if (type === '0') {
            priceAmountElement.style.color = '#FF0000';
            priceAmountElement.textContent = formatCurrency(parseFloat(priceAmountElement.textContent));
        }

        const dateElement = item.querySelector('.date');
        if (dateElement) {
            dateElement.textContent = dateElement.textContent.replace('T', ' ');
        }

        currentPointAmountElement.style.color = '#2EC44F';
        currentPointAmountElement.textContent = formatCurrency(parseFloat(currentPointAmountElement.textContent));
    });
});
