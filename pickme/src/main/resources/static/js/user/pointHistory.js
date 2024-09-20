function formatCurrency(value) {
    return value.toLocaleString('ko-KR');
}

document.addEventListener("DOMContentLoaded", function () {
    const historyItems = document.querySelectorAll('.history-item');

    historyItems.forEach(item => {
        const statusElement = item.querySelector('.status');
        const priceTypeElement = item.querySelector('.price-type');
        const priceAmountElement = item.querySelector('.price-amount');

        if (statusElement.classList.contains('charge')) {
            priceTypeElement.textContent = '충전 금액: ';
            priceAmountElement.textContent = formatCurrency(parseFloat(priceAmountElement.textContent));
            priceAmountElement.style.color = '#007BFF';
        } else if (statusElement.classList.contains('payment')) {
            priceTypeElement.textContent = '결제 금액: ';
            priceAmountElement.textContent = formatCurrency(parseFloat(priceAmountElement.textContent));
            priceAmountElement.style.color = '#FF0000';
        }

        const dateElement = item.querySelector('.date');
        if (dateElement) {
            dateElement.textContent = dateElement.textContent.replace('T', ' ');
        }

        const currentPointAmountElement = item.querySelector('.current-point-amount');
        currentPointAmountElement.style.color = '#2EC44F';
        currentPointAmountElement.textContent = formatCurrency(parseFloat(currentPointAmountElement.textContent));
    });
});
