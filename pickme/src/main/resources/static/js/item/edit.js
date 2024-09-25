function setMinAuctionStartTime() {
    const now = new Date();
    const isoNow = now.toISOString().slice(0, 16);
    document.getElementById('auctionStartTime').setAttribute('min', isoNow);
}

function setMinAuctionEndTime() {
    const auctionStartTimeInput = document.getElementById('auctionStartTime');
    const auctionEndTimeInput = document.getElementById('auctionEndTime');

    if (auctionStartTimeInput.value) {
        const auctionStartDate = new Date(auctionStartTimeInput.value);
        auctionStartDate.setMinutes(auctionStartDate.getMinutes() + 3);
        const isoEndTime = auctionStartDate.toISOString().slice(0, 16);
        auctionEndTimeInput.setAttribute('min', isoEndTime);
        auctionEndTimeInput.removeAttribute('disabled');
    } else {
        auctionEndTimeInput.setAttribute('disabled', 'disabled');
        auctionEndTimeInput.value = ''; // 초기화
    }
}

window.onload = function () {
    setMinAuctionStartTime();
    const auctionStartTimeInput = document.getElementById('auctionStartTime');
    const auctionEndTimeInput = document.getElementById('auctionEndTime');

    auctionStartTimeInput.addEventListener('change', setMinAuctionEndTime);
    auctionEndTimeInput.setAttribute('disabled', 'disabled'); // 초기 상태에서 비활성화
}

function submitUpdate() {
    const itemId = document.getElementById('itemId').value;
    const updateData = {
        startTime: document.getElementById('auctionStartTime').value,
        endTime: document.getElementById('auctionEndTime').value,
        lawId: Array.from(document.querySelectorAll('input[name="lawId[]"]:checked')).map(el => Number(el.value)) // long 배열로 변환
    };

    $.ajax({
        url: `/customs/edit/${itemId}`,
        type: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(updateData),
        success: function () {
            alert("수정완료");
            window.location.href = '/customs/items';
        },
        error: function () {
            console.log(itemId);
            console.log(updateData);
            alert('Failed to update item');
        }
    });
}
