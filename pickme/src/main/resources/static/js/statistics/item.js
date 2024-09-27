$(document).ready(function() {
    $.ajax({
        url: '/user/api/statistics/item',
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            if (response.success) {
                let data = response.data;
                let topCompetitiveAuctions = data.topCompetitiveAuctions;
                let categoryFrequency = data.categoryFrequency;
                let categoryFailureRate = data.categoryFailureRate;

                createCompetitiveAuctionChart(topCompetitiveAuctions);
                createPopularCategoriesChart(categoryFrequency);
                createCategoryFailureRateChart(categoryFailureRate);
            } else {
                console.error('Failed to fetch data');
            }
        },
        error: function(xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    });
});

function createCompetitiveAuctionChart(data){
    Highcharts.chart('competitiveAuctionsChartContent', {
        chart: {
            type: 'bar',
            style: {
                        fontFamily: 'IBM Plex Sans KR'
                    }
        },
        title: {
            text: null,
            align: 'left'
        },
        subtitle: {
            text: 'Source: PICK-ME',
            align: 'left'
        },
        xAxis: {
            categories: data.map(item => item.itemName),
            title: {
                text: null
            },
            gridLineWidth: 1,
            lineWidth: 0
        },
        yAxis: {
            min: 0,
            title: {
                text: '참여자 수',
                align: 'high'
            },
            labels: {
                overflow: 'justify'
            },
            gridLineWidth: 0
        },
        tooltip: {
            valueSuffix: ' 명'
        },
        plotOptions: {
            bar: {
                borderRadius: '50%',
                dataLabels: {
                    enabled: true
                },
                groupPadding: 0.1
            }
        },
        legend: {
            enabled: false
        },
        credits: {
            enabled: false
        },
        series: [{
            name: '참여자 수',
            data: data.map(item => item.participantCount)
        }]
    });
}

function createPopularCategoriesChart(data) {
    Highcharts.chart('popularCategoriesChartContent', {
        chart: {
            type: 'pie',
            style: {
                        fontFamily: 'IBM Plex Sans KR'
                    }
        },
        title: {
            text: null
        },
        tooltip: {
            valueSuffix: '%'
        },
        subtitle: {
            text:
            'Source: PICK-ME'
        },
        plotOptions: {
            series: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: [{
                    enabled: true,
                    distance: 20
                }, {
                    enabled: true,
                    distance: -40,
                    format: '{point.percentage:.1f}%',
                    style: {
                        fontFamily: 'IBM Plex Sans KR',
                        fontSize: '1.2em',
                        textOutline: 'none',
                        opacity: 0.7
                    },
                    filter: {
                        operator: '>',
                        property: 'percentage',
                        value: 10
                    }
                }]
            }
        },
        series:  [{
            name: '등록 비율',
            colorByPoint: true,
            data: data.map(item => ({
                name: item.categoryName,
                y: item.categoryPercentage
            }))
        }]
    });
}

function createCategoryFailureRateChart(data) {
    Highcharts.chart('categoryFailureChartContent', {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: 0,
            plotShadow: false
        },
        title: {
            text: 'Browser<br>shares<br>January<br>2022',
            align: 'center',
            verticalAlign: 'middle',
            y: 60,
            style: {
                fontSize: '1.1em'
            }
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        accessibility: {
            point: {
                valueSuffix: '%'
            }
        },
        plotOptions: {
            pie: {
                dataLabels: {
                    enabled: true,
                    distance: -50,
                    style: {
                        fontWeight: 'bold',
                        color: 'white'
                    }
                },
                startAngle: -90,
                endAngle: 90,
                center: ['50%', '75%'],
                size: '110%'
            }
        },
        series: [{
            type: 'pie',
            name: 'Browser share',
            innerSize: '50%',
            data: [
                ['Chrome', 73.86],
                ['Edge', 11.97],
                ['Firefox', 5.52],
                ['Safari', 2.98],
                ['Internet Explorer', 1.90],
                ['Other', 3.77]
            ]
        }]
    });
}
