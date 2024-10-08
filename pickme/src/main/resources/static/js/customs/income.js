document.addEventListener("DOMContentLoaded", function() {
    loadIncomeHistory(0, 7);
});

function loadIncomeHistory(page, size) {
    $.ajax({
        url: '/customs/income',
        method: 'GET',
        data: { page: page, size: size },
        contentType: 'application/json',
        success: function(data) {
            if (data.success) {
                updateIncomeContent(data.data.content);
                updatePagination(data.data, 'loadIncomeHistory', size);
            } else {
                console.error('수입을 불러오는데 실패했습니다.');
            }
        },
        error: function(error) {
            console.error('에러 발생:', error);
        }
    });
}

function updateIncomeContent(incomeDtos) {
    const container = document.getElementById('income-content');
    container.innerHTML = '';  // 기존 내용 삭제

    const templateSource = document.getElementById('income-template').innerHTML;
    const template = Handlebars.compile(templateSource);

    incomeDtos.forEach(incomeDto => {
        const context = {
            itemImage: incomeDto.itemImage,
            income: formatCurrency(incomeDto.income ?? 0),
            totalIncome: formatCurrency(incomeDto.totalIncome),
            time: new Date(incomeDto.endTime).toLocaleString()
        };

        const html = template(context);
        container.innerHTML += html;
    });

    const lastTotalIncome = incomeDtos[0]?.totalIncome ?? 0;
    document.getElementById('points-amount').innerText = formatCurrency(lastTotalIncome) + ' P';
}

function formatCurrency(value) {
    return value.toLocaleString('ko-KR');
}
