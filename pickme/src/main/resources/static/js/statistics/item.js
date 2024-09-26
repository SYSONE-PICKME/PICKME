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
                        fontFamily: 'IBM Plex Sans KR'  // 전역 폰트 설정
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
                        fontFamily: 'IBM Plex Sans KR'  // 전역 폰트 설정
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
    const totalFailures = data.reduce((sum, item) => sum + item.failedAuctions, 0);

    Highcharts.chart('categoryFailureChartContent', {
        chart: {
            type: 'pie',
            style: {
                fontFamily: 'IBM Plex Sans KR'
            }
        },
        title: {
            text: '카테고리별 유찰율'
        },
        subtitle: {
            text: 'Source: PICK-ME'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    distance: -30,
                    style: {
                        fontWeight: 'normal',
                        color: 'white',
                        textOutline: '0px contrast'
                    }
                },
                innerSize: '60%'
            }
        },
        series: [{
            name: '유찰율',
            colorByPoint: true,
            data: data.map(item => ({
                name: item.categoryName,
                y: item.failRate
            }))
        }]
    }, function(chart) {
        chart.renderer.text('총 유찰 수<br><span style="font-size: 24px; font-weight: bold;">' + totalFailures.toLocaleString() + '</span>')
            .attr({
                align: 'center',
                zIndex: 999
            })
            .css({
                color: '#000000',
                fontSize: '14px'
            })
            .add()
            .align({
                align: 'center',
                verticalAlign: 'middle'
            }, false, 'plotBox');
    });
}

//function createCategoryFailureRateChart(data) {
//    const totalFailures = data.reduce((sum, item) => sum + item.failedAuctions, 0);
//
//    Highcharts.chart('categoryFailureChartContent', {
//        chart: {
//            type: 'pie',
//            style: {
//                fontFamily: 'IBM Plex Sans KR'
//            },
//            events: {
//                render() {
//                    const chart = this,
//                        series = chart.series[0];
//                    let customLabel = chart.customLabel;
//
//                    if (!customLabel) {
//                        customLabel = chart.customLabel = chart.renderer.label(
//                            '총 유찰 수<br/>' +
//                            `<strong>${totalFailures.toLocaleString()}</strong>`
//                        )
//                            .css({
//                                color: '#000',
//                                textAnchor: 'middle'
//                            })
//                            .add();
//                    }
//
//                    const x = series.center[0] + chart.plotLeft,
//                        y = series.center[1] + chart.plotTop;
//
//                    customLabel.attr({
//                        x,
//                        y,
//                        'text-anchor': 'middle'
//                    }).align(undefined, false, 'spacingBox');
//
//                    customLabel.css({
//                        fontSize: `${series.center[2] / 12}px`
//                    });
//                }
//            }
//        },
//        title: {
//            text: '카테고리별 유찰율'
//        },
//        subtitle: {
//            text: 'Source: PICK-ME'
//        },
//        tooltip: {
//            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
//        },
//        accessibility: {
//            point: {
//                valueSuffix: '%'
//            }
//        },
//        plotOptions: {
//            pie: {
//                allowPointSelect: true,
//                cursor: 'pointer',
//                dataLabels: {
//                    enabled: true,
//                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
//                    style: {
//                        fontWeight: 'normal'
//                    }
//                },
//                innerSize: '60%'
//            }
//        },
//        series: [{
//            name: '유찰율',
//            colorByPoint: true,
//            data: data.map(item => ({
//                name: item.categoryName,
//                y: item.failRate
//            }))
//        }]
//    });
//}
