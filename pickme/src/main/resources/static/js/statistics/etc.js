$(document).ready(function() {
    $.ajax({
        url: '/user/api/statistics/etc',
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            if (response.success) {
                let data = response.data;
                let customsItemCount = data.customsItemCount;
                let hourlyBidActivity = data.hourlyBidActivity;

                createCustomsItemCountChart(customsItemCount);
                createHourlyBidActivity(hourlyBidActivity);

            } else {
                console.error('Failed to fetch data');
            }
        },
        error: function(xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    });
});

function createCustomsItemCountChart(customsItemCount) {
    let seriesData = customsItemCount.map(item => ({
        name: item.customsName,   // 세관 이름
        value: item.itemCount || 0,    // 물품 수, 기본값 0
        colorValue: item.itemCount || 0 // colorValue도 물품 수에 따라 설정, 기본값 0
    }));

    Highcharts.chart('customsItemCount', {
        colorAxis: {
            minColor: '#FFFFFF',
            maxColor: Highcharts.getOptions().colors[0]
        },
        series: [{
            type: 'treemap',
            layoutAlgorithm: 'squarified',
            clip: false,
            data: seriesData // 변환된 데이터 적용
        }],
        title: {
            text: '세관별 물품 등록 수'
        }
    });
}


function createHourlyBidActivity(hourlyBidActivity) {
    // 차트에 넣을 데이터 생성
    let seriesData = hourlyBidActivity.map(item => item.bidCount);

    Highcharts.chart('hourlyBidActivity', {
        chart: {
            type: 'area'
        },
        title: {
            text: '시간대별 입찰 활성도'
        },
        xAxis: {
            categories: ['01시', '02시', '03시', '04시', '05시', '06시', '07시', '08시', '09시', '10시', '11시', '12시',
                         '13시', '14시', '15시', '16시', '17시', '18시', '19시', '20시', '21시', '22시', '23시', '24시'],
            title: {
                text: '시간대'
            },
            allowDecimals: false
        },
        yAxis: {
            title: {
                text: '입찰 횟수'
            },
            allowDecimals: false
        },
        tooltip: {
            pointFormat: '시간대 {point.x}시: <b>{point.y}건</b> 입찰 발생'
        },
        plotOptions: {
            area: {
                marker: {
                    enabled: false,
                    symbol: 'circle',
                    radius: 2,
                    states: {
                        hover: {
                            enabled: true
                        }
                    }
                }
            }
        },
        series: [{
            name: '입찰 활성도',
            data: seriesData // 서버에서 받은 데이터 적용
        }]
    });
}
