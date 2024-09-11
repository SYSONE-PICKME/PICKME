function formatCurrency(value) {
    return value.toLocaleString('ko-KR');
}

document.addEventListener("DOMContentLoaded", function () {
    // 시작 가격
    const startPriceElement = document.querySelector('.price');
    const startPrice = parseInt(startPriceElement.textContent);
    startPriceElement.textContent = formatCurrency(startPrice);

    // 현재 최고가
    const maxPriceElement = document.querySelector('.max-price');
    const maxPrice = parseInt(maxPriceElement.textContent);
    maxPriceElement.textContent = formatCurrency(maxPrice);

    // 보유 포인트
    const myPointElement = document.querySelector('.my-point');
    const myPoint = parseInt(myPointElement.textContent);
    myPointElement.textContent = formatCurrency(myPoint);
});