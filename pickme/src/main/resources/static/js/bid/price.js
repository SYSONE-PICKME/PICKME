const bidUnits = [
    {min: 0, max: 100000, unit: 1000},
    {min: 100000, max: 1000000, unit: 10000},
    {min: 1000000, max: 10000000, unit: 30000},
    {min: 10000000, max: 20000000, unit: 50000},
    {min: 20000000, max: 30000000, unit: 100000},
    {min: 30000000, max: 50000000, unit: 200000},
    {min: 50000000, max: 100000000, unit: 300000},
    {min: 100000000, max: 200000000, unit: 500000},
    {min: 200000000, max: 500000000, unit: 700000},
    {min: 500000000, max: 1000000000, unit: 1000000},
    {min: 1000000000, max: 2000000000, unit: 1200000}
];

function findBidUnit(currentPrice){
    for(let i = 0; i < bidUnits.length; i++) {
        const { min, max, unit } = bidUnits[i];
        if (currentPrice >= min && currentPrice < max) {
            return unit;
        }
    }
    return bidUnits[bidUnits.length - 1].unit;
}

export function validBid(price, currentMaxPrice, myPoint) {
    const bidUnit = findBidUnit(currentMaxPrice);

    if (!price) return "금액을 입력해주세요.";
    if (price <= currentMaxPrice) return "최고 금액보다 높은 가격을 제시해주세요.";
    if (parseInt(price) > myPoint) return "보유 포인트가 부족합니다.";
    if (!((price - currentMaxPrice) % bidUnit === 0)) return `현재 호가 단위가 맞지 않습니다. \n호가 단위: ${bidUnit}원`;

    return null;
}

export function formatCurrency(value) {
    return value.toLocaleString('ko-KR');
}

export function updateMaxPrice(maxPrice){
    const maxPriceElement = document.querySelector('.max-price');
    console.log("최고가 업데이트: ", maxPrice);
    maxPriceElement.textContent = formatCurrency(maxPrice);

    maxPriceElement.classList.add('updated');

    setTimeout(() => {
        maxPriceElement.classList.remove('updated');
    }, 500);
}
