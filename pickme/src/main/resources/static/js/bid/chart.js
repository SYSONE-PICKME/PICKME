// 차트를 그릴 캔버스 가져오기
const ctx = document.querySelector('#myChart').getContext('2d');

let labels, data;

if (allPrice.length > 0) {
    labels = allPrice.map((_, index) => index + 1); // x축 라벨은 1부터 시작하여 allPrice 크기만큼 설정
    data = allPrice.slice(); // y축 데이터는 allPrice로 설정
} else {
    labels = [1];
    data = [startPrice];
}

let xValueCounter = labels.length + 1;

// Chart.js를 이용해 차트 생성
export const chart = new Chart(ctx, {
    type: 'line',
    data: {
        labels,
        datasets: [{
            label: '',
            data,
            backgroundColor: 'rgb(246,255,248)',
            borderColor: 'rgb(46,196,79)',
            borderWidth: 3,
        }]
    },
    options: {
        responsive: true, // 차트 크기 자동 조절
        maintainAspectRatio: false, // 차트의 가로 세로 비율을 유지하지 않음

        animation: {
            duration: 500, // 애니메이션 지속 시간
            easing: 'ease-in-out' // 애니메이션의 변화 속도
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

export function addData(data) {
    chart.data.labels.push(xValueCounter++); //라벨 배열에 새로운 라벨을 추가
    chart.data.datasets[0].data.push(data); // 데이터 배열에 새로운 데이터를 추가
    chart.update(); // 차트를 업데이트
}
