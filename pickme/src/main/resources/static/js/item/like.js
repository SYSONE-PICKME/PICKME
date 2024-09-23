$(document).ready(function() {
    // 모든 좋아요 버튼에 대해 처리
    $('.heart').each(function() {
        const likeButton = $(this);
        const heartIcon = likeButton.find('i');
        const isLiked = likeButton.data('liked');

        // 초기 상태 설정
        if (isLiked) {
            likeButton.addClass('active');
            heartIcon.removeClass('far').addClass('fas');
        }

        // 클릭 이벤트 처리
        likeButton.on('click', function(event) {
            event.stopPropagation();
            event.preventDefault();

            const actionUrl = likeButton.data('action-url');
            const liked = likeButton.toggleClass('active').hasClass('active');

            // 하트 아이콘 상태 변경
            if (liked) {
                heartIcon.removeClass('far').addClass('fas');
            } else {
                heartIcon.removeClass('fas').addClass('far');
            }

            // AJAX 요청
            $.ajax({
                url: actionUrl,
                method: 'POST',
                data: {},
                success: function(response) {
                    console.log('서버로 좋아요 상태 전송 완료:', response);
                },
                error: function(xhr, status, error) {
                    console.error('서버 요청 실패:', xhr.status, xhr.responseText);
                    // 오류 발생 시 원래 상태로 복구
                    likeButton.toggleClass('active');
                    if (liked) {
                        heartIcon.removeClass('fas').addClass('far');
                    } else {
                        heartIcon.removeClass('far').addClass('fas');
                    }
                }
            });
        });
    });
});
