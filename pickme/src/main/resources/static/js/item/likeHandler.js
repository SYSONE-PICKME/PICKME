$(document).on('click', '.heart', function(event) {
    event.stopPropagation(); // 부모 요소로의 이벤트 전파를 막음
    const likeButton = $(this);
    const heartIcon = likeButton.find('i');
    const actionUrl = likeButton.data('action-url');
    let isLiked = likeButton.data('liked');

    // 문자열을 Boolean 값으로 변환 (초기 로드 시)
    if (typeof isLiked === 'string') {
        isLiked = isLiked === 'true';
    }

    // 하트 아이콘 상태 변경 (현재 상태의 반대로 설정)
    isLiked = !isLiked;

    // 하트 아이콘의 클래스를 업데이트
    if (isLiked) {
        heartIcon.removeClass('far').addClass('fas');
    } else {
        heartIcon.removeClass('fas').addClass('far');
    }

    // AJAX 요청으로 좋아요 상태 변경
    $.ajax({
        url: actionUrl,
        method: 'POST',
        data: {},
        success: function(response) {
            console.log('서버로 좋아요 상태 전송 완료:', response);
            likeButton.data('liked', isLiked); // 상태 반전 후 업데이트
            likeButton.toggleClass('active', isLiked); // 좋아요 상태에 따라 active 클래스 적용
        },
        error: function(xhr, status, error) {
            console.error('서버 요청 실패:', xhr.status, xhr.responseText);
            // 오류 발생 시 원래 상태로 복구
            likeButton.toggleClass('active', !isLiked);
            if (!isLiked) {
                heartIcon.removeClass('fas').addClass('far');
            } else {
                heartIcon.removeClass('far').addClass('fas');
            }
        }
    });
});
