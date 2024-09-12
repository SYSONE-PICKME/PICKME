// 차트를 그릴 캔버스 가져오기
const ctx = document.querySelector('#myChart').getContext('2d');

// 초기 라벨과 데이터
let labels = [0], data = [startPrice];

// x축 값 증가를 위한 변수
let xValueCounter = 1;

// Chart.js를 이용해 차트 생성
export const chart = new Chart(ctx, {
    type: 'line', // 차트의 종류
    data: {
        labels, // x축에 해당하는 라벨을 빈 배열로 설정
        datasets: [{
            label: '', // 데이터셋의 라벨을 빈 문자열로 설정
            data, // y축 데이터를 빈 배열로 설정
            backgroundColor: 'rgb(246,255,248)', //그래프 색상을 설정
            borderColor: 'rgb(46,196,79)', // 테두리 색상을 설정
            borderWidth: 3,
        }]
    },
    options: {
        responsive: true, // 차트 크기 자동 조절
        maintainAspectRatio: false, // 차트의 가로 세로 비율을 유지하지 않음

        animation: {
            duration: 1000, // 애니메이션 지속 시간
            easing: 'ease-in-out' // 애니메이션의 변화 속도
        },
        plugins: {
            legend: {
                display: false // 범례 표시 안함
            },
            title: {
                display: true,
                text: '현재 입찰 추이'
            },
        }
    }
});
// Chart.js의 API를 이용해 차트에 새로운 데이터를 추가하는 함수
export function addData(data) {
    chart.data.labels.push(xValueCounter++); //라벨 배열에 새로운 라벨을 추가
    chart.data.datasets[0].data.push(data); // 데이터 배열에 새로운 데이터를 추가
    chart.update(); // 차트를 업데이트
}
