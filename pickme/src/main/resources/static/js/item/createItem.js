const endTimePicker = flatpickr("#endTime", {
    enableTime: true,
    noCalendar: false,
    dateFormat: "Y-m-d H:i",
    time_24hr: true,
    minuteIncrement: 60,
    disable: [],
    minDate: null,
});

const startTimePicker = flatpickr("#startTime", {
    enableTime: true,
    noCalendar: false,
    dateFormat: "Y-m-d H:i",
    time_24hr: true,
    minuteIncrement: 60,
    minDate: "today",
    onChange: function (selectedDates) {
        if (selectedDates.length > 0) {
            const startTime = selectedDates[0];

            if (startTime instanceof Date && !isNaN(startTime.getTime())) {
                // 시작 시간이 선택된 경우, 해당 시간 이후로만 endTime을 설정할 수 있게 minDate 변경
                const minEndTime = new Date(startTime.getTime() + 60 * 60 * 1000); // 1시간 이후로 설정 (원하는 대로 조정 가능)
                endTimePicker.set('minDate', minEndTime); // endTime의 최소값을 startTime 이후로 설정

                document.getElementById("endTime").disabled = false; // endTime 활성화
            }
        }
    }
});
