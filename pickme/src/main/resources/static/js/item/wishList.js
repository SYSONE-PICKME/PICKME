Handlebars.registerHelper('formatPrice', function(price) {
    return price.toLocaleString();  // 숫자에 콤마 추가
});

document.addEventListener("DOMContentLoaded", function () {
    loadWishList(0, 5);
});

function loadWishList(page, size) {
    $.ajax({
        url: '/user/api/item/wish-list',  // JSON 데이터를 가져올 URL
        method: 'GET',
        data: {page: page, size: size},
        success: function (response) {
            const wishList = response.data.content;  // 서버에서 받은 관심 상품 목록
            const templateSource = document.getElementById('wishlist-template').innerHTML;
            const template = Handlebars.compile(templateSource);  // Handlebars 템플릿 컴파일

            const html = template({wishList: wishList});  // 데이터를 템플릿에 바인딩

            // 컨테이너에 생성된 HTML 삽입
            $('#wishlist-container').html(html);
            $.getScript('/js/item/request.js');
            $.getScript('/js/item/like.js');

            console.log(response.data);
            updatePagination(response.data, 'loadWishList', size);
        },
        error: function (xhr, status, error) {
            console.error('관심 상품 목록을 불러오는 중 오류가 발생했습니다:', error);
        }
    });
}

function updateCountdown() {
    $('.countdown').each(function() {
        const endTime = $(this).data('end-time'); // data-end-time 속성 가져오기
        const endDate = new Date(endTime).getTime(); // 문자열을 Date로 변환
        const now = new Date().getTime(); // 현재 시간
        const timeDifference = endDate - now;

        // Countdown 타이머 요소와 텍스트 요소 찾기
        const countdownElement = $(this);
        const countdownTextElement = $(this).next('.countdown-text');

        if (timeDifference > 0) {
            const days = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
            const hours = Math.floor((timeDifference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            const minutes = Math.floor((timeDifference % (1000 * 60 * 60)) / (1000 * 60));
            const seconds = Math.floor((timeDifference % (1000 * 60)) / 1000);

            if (days > 0) {
                countdownTextElement.hide();
                countdownElement.html(`
                    <span style="color: #333;">
                          <span style="color: red;">${days}일 후</span> 마감
                    </span>
                `);

                return;
            }

            // 시간 형식을 'HH:mm:ss'로 변환하고 빨간색 스타일 적용
            countdownElement.html(`${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}</span>`);
            countdownTextElement.html('이후 마감');
        } else {
            countdownElement.html('<span style="color: #333;">마감되었습니다</span>');
        }
    });
}

$(document).ready(function() {
    // 페이지 로드 시 카운트다운 초기화 및 1초마다 업데이트
    updateCountdown();
    setInterval(updateCountdown, 1000);
});
