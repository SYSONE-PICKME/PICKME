const ctx = document.querySelector('#myChart').getContext('2d');
let labels = [];
let data = [];
let xValueCounter = 1;

export function initChartData(allPrice, startPrice) {
    if (allPrice.length > 0) {
        labels = allPrice.map((_, index) => index + 1); // x축 라벨은 1부터 시작하여 allPrice 크기만큼 설정
        data = allPrice.slice(); // y축 데이터는 allPrice로 설정
    } else {
        labels = [1];
        data = [startPrice];
    }
    xValueCounter = labels.length + 1;

    // 차트 데이터 업데이트
    chart.data.labels = labels;
    chart.data.datasets[0].data = data;
    chart.update();
}

// Chart.js를 이용해 차트 생성
export const chart = new Chart(ctx, {
    type: 'line',
    data: {
        labels: [], // 초기에는 빈 배열로 설정
        datasets: [{
            label: '',
            data: [], // 초기에는 빈 배열로 설정
            backgroundColor: 'rgb(246,255,248)',
            borderColor: 'rgb(46,196,79)',
            borderWidth: 3,
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        animation: {
            duration: 500,
            easing: 'ease-in-out'
        },
        plugins: {
            legend: {
                display: false
            },
            title: {
                display: true,
                text: '현재 입찰 추이'
            },
        }
    }
});

export function addData(newPrice) {
    chart.data.labels.push(xValueCounter++); // 라벨 배열에 새로운 라벨을 추가
    chart.data.datasets[0].data.push(newPrice); // 데이터 배열에 새로운 데이터를 추가
    chart.update(); // 차트를 업데이트
}
