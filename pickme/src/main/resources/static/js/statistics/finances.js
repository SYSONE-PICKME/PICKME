$(document).ready(function() {
    $.ajax({
        url: '/user/api/statistics/finances',
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            if (response.success) {
                let data = response.data;
                let categoryRevenue = data.categoryRevenue;
                let categoryBidPriceRange = data.categoryBidPriceRange;
                let categoryRevenueHistory = data.categoryRevenueHistory;

                createCategoryRevenueChart(categoryRevenue);
                createCategoryBidPriceRangeChart(categoryBidPriceRange);
                createCategoryRevenueHistoryChart(categoryRevenueHistory);
            } else {
                console.error('Failed to fetch data');
            }
        },
        error: function(xhr, status, error) {
            console.error('Error fetching data:', error);
        }
    });
});

function createCategoryRevenueChart(data) {
    let seriesData = [];
    let totalRevenue = 0;

    data.forEach(item => {
        seriesData.push({
            name: item.categoryName,
            y: item.totalRevenue
        });
        totalRevenue += item.totalRevenue;
    });

    Highcharts.chart('categoryRevenueChartContent', {
        chart: {
            type: 'column',
            height: 900, // 원하는 높이를 픽셀 단위로 지정 (예: 600px)
            animation: false, // 애니메이션 비활성화로 렌더링 속도 향상
            width: null
        },
        title: {
            text: '총 경매수익/카테고리별 경매 수익',
            align: 'left'
        },
        subtitle: {
            text: 'Source: PICK-ME',
            align: 'left'
        },
        xAxis: {
            categories: [`총 수익: ${totalRevenue.toLocaleString()} 원`]
        },
        yAxis: {
            min: 0,
            title: {
                text: '수익 (원)'
            }
        },
        tooltip: {
            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> 원 ({point.percentage:.1f}%)<br/>',
            shared: true
        },
        plotOptions: {
            column: {
                stacking: 'percent',
                dataLabels: {
                    enabled: true,
                    format: '{point.percentage:.1f}%'
                }
            }
        },
        series: seriesData.map(item => ({
            name: item.name,
            data: [item.y]
        }))
    });
}

function createCategoryBidPriceRangeChart(data) {
    let categories = [];
    let seriesData = [];

    data.forEach(item => {
        categories.push(item.categoryName);
        seriesData.push([item.minBidPrice, item.maxBidPrice]);
    });

    Highcharts.chart('categoryBidPriceRangeChartContent', {
        chart: {
            type: 'columnrange',
            inverted: true
        },

        title: {
            text: '카테고리별 최저-최고 낙찰가'
        },

        subtitle: {
            text: 'PICK-ME'
        },

        xAxis: {
            categories: categories
        },

        yAxis: {
            title: {
                text: '낙찰가 (원)'
            }
        },

        tooltip: {
            valueSuffix: '원'
        },

        plotOptions: {
            columnrange: {
                borderRadius: '50%',
                dataLabels: {
                    enabled: true,
                    format: '{y}원'
                }
            }
        },

        legend: {
            enabled: false
        },

        series: [{
            name: '낙찰가 범위',
            data: seriesData
        }]
    });
}

function createCategoryRevenueHistoryChart(data) {
    const colors = Highcharts.getOptions().colors;

    Highcharts.chart('categoryRevenueHistoryChartContent', {
        chart: {
            type: 'spline'
        },

        legend: {
            symbolWidth: 40
        },

        title: {
            text: 'Most common desktop screen readers',
            align: 'left'
        },

        subtitle: {
            text: 'Source: WebAIM. Click on points to visit official screen ' +
                'reader website',
            align: 'left'
        },

        yAxis: {
            title: {
                text: 'Percentage usage'
            },
            accessibility: {
                description: 'Percentage usage'
            }
        },

        xAxis: {
            title: {
                text: 'Time'
            },
            accessibility: {
                description: 'Time from December 2010 to September 2019'
            },
            categories: [
                'December 2010', 'May 2012', 'January 2014', 'July 2015',
                'October 2017', 'September 2019'
            ]
        },

        tooltip: {
            valueSuffix: '%',
            stickOnContact: true
        },

        plotOptions: {
            series: {
                point: {
                    events: {
                        click: function () {
                            window.location.href = this.series.options.website;
                        }
                    }
                },
                cursor: 'pointer',
                lineWidth: 2
            }
        },

        series: [
            {
                name: 'NVDA',
                data: [34.8, 43.0, 51.2, 41.4, 64.9, 72.4],
                website: 'https://www.nvaccess.org',
                color: colors[2],
                accessibility: {
                    description: 'This is the most used screen reader in 2019.'
                }
            }, {
                name: 'JAWS',
                data: [69.6, 63.7, 63.9, 43.7, 66.0, 61.7],
                website: 'https://www.freedomscientific.com/Products/Blindness/JAWS',
                dashStyle: 'ShortDashDot',
                color: colors[0]
            }, {
                name: 'VoiceOver',
                data: [20.2, 30.7, 36.8, 30.9, 39.6, 47.1],
                website: 'http://www.apple.com/accessibility/osx/voiceover',
                dashStyle: 'ShortDot',
                color: colors[1]
            }, {
                name: 'Narrator',
                data: [null, null, null, null, 21.4, 30.3],
                website: 'https://support.microsoft.com/en-us/help/22798/windows-10-complete-guide-to-narrator',
                dashStyle: 'Dash',
                color: colors[9]
            }, {
                name: 'ZoomText/Fusion',
                data: [6.1, 6.8, 5.3, 27.5, 6.0, 5.5],
                website: 'http://www.zoomtext.com/products/zoomtext-magnifierreader',
                dashStyle: 'ShortDot',
                color: colors[5]
            }, {
                name: 'Other',
                data: [42.6, 51.5, 54.2, 45.8, 20.2, 15.4],
                website: 'http://www.disabled-world.com/assistivedevices/computer/screen-readers.php',
                dashStyle: 'ShortDash',
                color: colors[3]
            }
        ],

        responsive: {
            rules: [{
                condition: {
                    maxWidth: 550
                },
                chartOptions: {
                    chart: {
                        spacingLeft: 3,
                        spacingRight: 3
                    },
                    legend: {
                        itemWidth: 150
                    },
                    xAxis: {
                        categories: [
                            'Dec. 2010', 'May 2012', 'Jan. 2014', 'July 2015',
                            'Oct. 2017', 'Sep. 2019'
                        ],
                        title: ''
                    },
                    yAxis: {
                        visible: false
                    }
                }
            }]
        }
    });

}

