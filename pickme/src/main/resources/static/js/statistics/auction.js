$(document).ready(function() {
    $.ajax({
        url: '/user/statistics/toAuction',
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            if (response.success) {
                let data = response.data;
                let totalAuctions = data.totalAuctions;
                let totalCompetitionRate = data.totalCompetitionRate;
                let categoryCompetitionRate = data.categoryCompetitionRate;
                let monthlyBids = data.monthlyBids;

                createTotalAuctionsChart(totalAuctions, totalCompetitionRate);
                createMonthlyAuctionsChart(monthlyBids);
                createCategoryCompetitionRateChart(categoryCompetitionRate);

            } else {
                console.error('Failed to fetch data');
            }
        },
        error: function(xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    });
});

function createTotalAuctionsChart(totalAuctions, totalCompetitionRate){
    Highcharts.chart('totalAuctionsChart', {

        chart: {
            type: 'column',
        },

        title: {
            text: '총 경매수/전체 경쟁률',
            align: 'left'
        },

        subtitle: {
            text: 'PICK-ME',
            align: 'left'
        },

        xAxis: {
            categories: ['총 경매수', '전체 경쟁률']
        },

        yAxis: [{
            className: 'highcharts-color-0',
            title: {
                text: '경매수'
            },
            allowDecimals: false
        }, {
            className: 'highcharts-color-1',
            opposite: true,
            title: {
                text: '경쟁률'
            }
        }],

        plotOptions: {
            column: {
                borderRadius: 5
            }
        },

        series: [{
            name: '총 경매수',
            data: [totalAuctions, 0],
            yAxis: 0
        }, {
            name: '전체 경쟁률',
            data: [0, totalCompetitionRate],
            yAxis: 1
        }]
    });
}

function createCategoryCompetitionRateChart(categoryCompetitionRate) {
    const categoryMap = {
            1: '의류',
            2: '생활용품',
            3: '디지털',
            4: '가구',
            10: '기타'
    };

    const chartData = Object.entries(categoryMap).map(([code, name]) => {       // todo: 현재 데이터가 많지 않아 값이 없는 null인 경우도 가정하여 코딩, 수정 예정
        const data = categoryCompetitionRate[code];
        console.log(`Category: ${name}, Data: ${JSON.stringify(data)}`);

        const competitionRate = data && data.competitionRate !== null && data.competitionRate !== undefined
            ? parseFloat(data.competitionRate)
            : 0;
        return [name, competitionRate];
    });

    Highcharts.chart('categoryCompetitionRateChart', {
        chart: {
            type: 'column'
        },
        title: {
            text: '카테고리별 경쟁률'
        },
        subtitle: {
            text: 'PICK-ME'
        },
        xAxis: {
            type: 'category',
            labels: {
                autoRotation: [-45, -90],
                style: {
                    fontSize: '13px',
                    fontFamily: 'IBM Plex Sans KR'
                }
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: '경쟁률'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat: '경쟁률'
        },
        series: [{
            name: '경쟁률',
            colors: [
                '#9b20d9', '#691af3', '#4551d5', '#1f88b7', '#00f194'
            ],
            colorByPoint: true,
            groupPadding: 0,
            data: chartData,
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                inside: true,
                verticalAlign: 'top',
                format: '{point.y:.1f}',
                y: 10,
                style: {
                    fontSize: '13px',
                    fontFamily: 'IBM Plex Sans KR'
                }
            }
        }]
    });
}

function createMonthlyAuctionsChart(monthlyBids){
    Highcharts.chart('monthlyAuctionsChart', {
        chart: {
            zooming: {
                type: 'xy'
            }
        },
        title: {
            text: '월별 경매 수/경쟁률',
            align: 'left'
        },
        credits: {
            text: 'PICK-ME'
        },
        xAxis: [{
            categories: [
                '1월', '2월', '3월', '4월', '5월', '6월',
                '7월', '8월', '9월', '10월', '11월', '12월'
            ],
            crosshair: true
        }],
        yAxis: [{
            labels: {
                format: '{value}건',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            title: {
                text: '월별 경매 수',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            }
        }, {
            title: {
                text: '월별 경쟁률',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            labels: {
                format: '{value}',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        legend: {
            align: 'left',
            verticalAlign: 'top',
            backgroundColor:
                Highcharts.defaultOptions.legend.backgroundColor ||
                'rgba(255,255,255,0.25)'
        },
        series: [{
            name: '월별 경매수',
            type: 'column',
            yAxis: 1,
            data: [
                8, 15, 12, 23, 9, 18, 17, 6, 21,
                24, 17, 16
            ],
            tooltip: {
                valueSuffix: ' 건'
            }

        }, {
            name: '월별 경쟁률',
            type: 'spline',
            data: [
                3.3, 9.5, 14.2, 5.2, 7.0, 12.1, 13.5, 13.6, 8.2,
                2.8, 12.0, 15.5
            ],
        }]
    });
}
