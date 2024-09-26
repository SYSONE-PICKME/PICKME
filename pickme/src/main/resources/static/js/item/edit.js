function setMinAuctionStartTime() {
    const auctionStartTimeInput = document.getElementById('auctionStartTime');
    document.getElementById('auctionStartTime').setAttribute('min', auctionStartTimeInput);
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

// Start Time Picker Initialization
const startTimePicker = flatpickr("#auctionStartTime", {
    enableTime: true,
    noCalendar: false,
    dateFormat: "Y-m-d H:i",
    time_24hr: true,
    minuteIncrement: 60,
    minDate: "today" // 오늘 이후의 시간만 선택 가능
});

// End Time Picker Initialization
const endTimePicker = flatpickr("#auctionEndTime", {
    enableTime: true,
    noCalendar: false,
    dateFormat: "Y-m-d H:i",
    time_24hr: true,
    minuteIncrement: 60,
    minDate: null // 초기에는 제한 없음
});

// Update endTimePicker minDate when startTimePicker changes
startTimePicker.config.onChange.push(function (selectedDates) {
    if (selectedDates.length > 0) {
        const startTime = selectedDates[0];
        endTimePicker.set('minDate', new Date(startTime.getTime() + 60 * 60 * 1000)); // 시작 시간 이후 1시간부터 선택 가능
    }
});
