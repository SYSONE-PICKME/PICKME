document.addEventListener('DOMContentLoaded', function () {
    let pointElement = document.getElementById('points-amount');
    let pointValue = parseInt(pointElement.textContent, 10);

    pointElement.textContent = pointValue.toLocaleString('ko-KR') + ' P';
})
