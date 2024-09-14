$(document).ready(function() {
    const likeButton = $('#likeButton');
    const heartIcon = likeButton.find('i');
    const isLiked = likeButton.data('liked');

    if (isLiked) {
        likeButton.addClass('active');
        heartIcon.removeClass('far').addClass('fas');
    }

    likeButton.on('click', function(event) {
        event.preventDefault();

        const actionUrl = $(this).data('action-url');
        const liked = likeButton.toggleClass('active').hasClass('active');

        if (liked) {
            heartIcon.removeClass('far').addClass('fas');
        } else {
            heartIcon.removeClass('fas').addClass('far');
        }

        // AJAX request
        $.ajax({
            url: actionUrl,
            method: 'POST',
            data: {},
            success: function(response) {
                console.log('서버로 좋아요 상태 전송 완료:', response);
            },
            error: function(xhr, status, error) {
                console.error('서버 요청 실패:', xhr.status, xhr.responseText);

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
