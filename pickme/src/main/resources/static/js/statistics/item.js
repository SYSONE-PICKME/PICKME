//$(document).ready(function() {
//    $.ajax({
//        url: '/user/api/statistics/item',
//        method: 'GET',
//        dataType: 'json',
//        success: function(response) {
//            if (response.success) {
//                let data = response.data;
//                let totalAuctions = data.totalAuctions;
//                let totalCompetitionRate = data.totalCompetitionRate;
//                let categoryCompetitionRate = data.categoryCompetitionRate;
//                let monthlyBids = data.monthlyBids;
//
//                createTotalAuctionsChart(totalAuctions, totalCompetitionRate);
//                createMonthlyAuctionsChart(monthlyBids);
//                createCategoryCompetitionRateChart(categoryCompetitionRate);
//
//            } else {
//                console.error('Failed to fetch data');
//            }
//        },
//        error: function(xhr, status, error) {
//            console.error('Error fetching data:', error);
//        }
//    });
//});

function createCompetitiveAuctionChart(){
    Highcharts.chart('competitiveAuctionsChartContent', {
        chart: {
            type: 'bar'
        },
        title: {
            text: '가장 경쟁이 치열했던 경매 Top 10',
            align: 'left'
        },
        subtitle: {
            text: '<a ' +
                'href="https://en.wikipedia.org/wiki/List_of_continents_and_continental_subregions_by_population"' +
                'target="_blank">PICK-ME</a>',
            align: 'left'
        },
        xAxis: {
            categories: ['가나', '나다', '다라', '마바', '사아', '자차', '카타', '파하', '요호', '유후'],
            title: {
                text: null
            },
            gridLineWidth: 1,
            lineWidth: 0
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Population (millions)',
                align: 'high'
            },
            labels: {
                overflow: 'justify'
            },
            gridLineWidth: 0
        },
        tooltip: {
            valueSuffix: ' millions'
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
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            x: -40,
            y: 80,
            floating: true,
            borderWidth: 1,
            backgroundColor:
                Highcharts.defaultOptions.legend.backgroundColor || '#FFFFFF',
            shadow: true
        },
        credits: {
            enabled: false
        },
        series: [{
            name: 'Year 1990',
            data: [632, 727, 202, 721, 543, 453, 632, 426, 555, 444]
        }]
    });
}

function creatPpopularCategoriesChart() {
    Highcharts.chart('popularCategoriesChartContent', {
        chart: {
            type: 'pie'
        },
        title: {
            text: '카테고리 등록 빈도'
        },
        tooltip: {
            valueSuffix: '%'
        },
        subtitle: {
            text:
            '<a href="https://www.mdpi.com/2072-6643/11/3/684/htm" target="_default">PICK-ME</a>'
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
        series: [
            {
                name: 'Percentage',
                colorByPoint: true,
                data: [
                    {
                        name: '의류',
                        y: 55.02
                    },
                    {
                        name: '생활용품',
                        sliced: true,
                        selected: true,
                        y: 26.71
                    },
                    {
                        name: '디지털',
                        y: 1.09
                    },
                    {
                        name: '가구',
                        y: 15.5
                    },
                    {
                        name: '소모품',
                        y: 1.68
                    }
                ]
            }
        ]
    });
}

createCompetitiveAuctionChart();
creatPpopularCategoriesChart();
