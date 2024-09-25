
$(document).ready(function() {
    $.ajax({
        url: '/api/statistics/auctionStatistics',
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            if (response.success) {
                let data = response.data;
                let totalAuctions = data.totalAuctions;
                let totalCompetitionRate = data.totalCompetitionRate;
                let categoryCompetition = data.categoryCompetition;
                let monthlyBids = data.monthlyBids;

                createTotalAuctionsChart(totalAuctions, totalCompetitionRate);
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
//            styledMode: true
        },

        title: {
            text: '총 경매수/전체 경쟁률',
            align: 'left'
        },

        subtitle: {
            text: 'Source: ' +
                '<a href="https://www.worlddata.info/average-bodyheight.php"' +
                'target="_blank">WorldData</a>',
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
    //        tooltip: {
    //            valueSuffix: ' 건'
    //        }
        }, {
            name: '전체 경쟁률',
            data: [0, totalCompetitionRate],
            yAxis: 1
        }]

    });
}

function createMonthlyAuctionsChart(monthlyBids){

}
