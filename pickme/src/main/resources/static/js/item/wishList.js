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
            const hours = Math.floor((timeDifference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            const minutes = Math.floor((timeDifference % (1000 * 60 * 60)) / (1000 * 60));
            const seconds = Math.floor((timeDifference % (1000 * 60)) / 1000);
            
            // 시간 형식을 'HH:mm:ss'로 변환하고 빨간색 스타일 적용
            countdownElement.html(`${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}</span>`);
            
            // '이후 마감' 텍스트를 검정색으로 설정
            countdownTextElement.show();
        } else {
            // 마감 시, 타이머 요소에 '마감되었습니다' 문구 설정하고 '이후 마감' 텍스트 숨기기
            countdownTextElement.hide();
            countdownElement.html('<span style="color: #333;">마감되었습니다</span>');
        }
    });
}

$(document).ready(function() {
    // 페이지 로드 시 카운트다운 초기화 및 1초마다 업데이트
    updateCountdown();
    setInterval(updateCountdown, 1000);
});
