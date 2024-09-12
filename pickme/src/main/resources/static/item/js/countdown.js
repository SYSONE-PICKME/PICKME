const startDate = new Date(startTime.toString()).getTime();
const endDate = new Date(endTime.toString()).getTime();

console.log(startDate);

function disableButton() {
    const bidButton = document.getElementById('bid-button');
    bidButton.disabled = true;
    bidButton.classList.remove('active');
    bidButton.classList.add('disabled');
    bidButton.innerHTML = '경매 마감';
}

function updateCountdown() {
    const now = new Date().getTime();
    const timeDifference = startDate - now;
    const disableTimeDifference = endDate - now;

    if (disableTimeDifference <= 0) {
        disableButton();
        return;
    }

    // 초 단위로 변환
    const secondsLeft = Math.floor(timeDifference / 1000);
    const bidButton = document.getElementById('bid-button');

    if (secondsLeft > 0 && secondsLeft <= 86400) {
        const hours = Math.floor(secondsLeft / 3600); // 남은 시간
        const minutes = Math.floor((secondsLeft % 3600) / 60); // 남은 분
        const seconds = secondsLeft % 60; // 남은 초

        // 시간, 분, 초 형식으로 표시 (00:00:00 형태)
        bidButton.innerHTML =
            `경매 시작 전 ${String(hours).padStart(2, '0')}: ${String(minutes).padStart(2, '0')}: ${String(seconds).padStart(2, '0')}`;
    } else if (secondsLeft > 86400) {
        document.getElementById('bid-button').innerHTML = '경매 시작 전';
    } else {
        bidButton.disabled = false;
        bidButton.classList.add('active');
        bidButton.innerHTML = '경매 참여하기';
    }
}

setInterval(updateCountdown, 1000);
updateCountdown();
