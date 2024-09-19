$(document).ready(function() {
    const startDate = new Date(startTime.toString()).getTime();
    const endDate = new Date(endTime.toString()).getTime();
    const $bidButton = $('#bid-button');
    const itemId = $('.action-sub-details p span').data('item-id');

    function disableButton() {
        $bidButton.prop('disabled', true)
            .removeClass('active')
            .addClass('disabled')
            .html('경매 마감')
            .off('click');
        console.log('Button disabled');
    }

    function enableButton() {
        $bidButton.prop('disabled', false)
            .addClass('active')
            .removeClass('disabled')
            .html('경매 참여하기')
            .off('click')
            .on('click', function(e) {
                e.preventDefault();
                window.location.href = '/user/item/bid/' + itemId;
            });
    }

    function updateCountdown() {
        const now = new Date().getTime();
        const timeDifference = startDate - now;
        const disableTimeDifference = endDate - now;

        if (disableTimeDifference <= 0) {
            disableButton();
            return;
        }

        const secondsLeft = Math.floor(timeDifference / 1000);

        if (secondsLeft > 0 && secondsLeft <= 86400) {
            const hours = Math.floor(secondsLeft / 3600);
            const minutes = Math.floor((secondsLeft % 3600) / 60);
            const seconds = secondsLeft % 60;

            $bidButton.html(
                `경매 시작 전 ${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`
            ).prop('disabled', true).removeClass('active');
        } else if (secondsLeft > 86400) {
            $bidButton.html('경매 시작 전').prop('disabled', true).removeClass('active');
        } else {
            enableButton();
        }
    }

    updateCountdown();
    setInterval(updateCountdown, 1000);
});