document.addEventListener("DOMContentLoaded", function() {
    function step() {
        var now = Date.now();
        var remaining = Math.max(0, endTime - now);

        // 남은 시간을 시, 분, 초로 계산
        var hours = Math.floor(remaining / (1000 * 60 * 60));
        var minutes = Math.floor((remaining % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((remaining % (1000 * 60)) / 1000);

        // 남은 시간을 화면에 표시
        document.getElementById("time-remaining").innerHTML =
            hours.toString().padStart(2, '0') + ":" +
            minutes.toString().padStart(2, '0') + ":" +
            seconds.toString().padStart(2, '0');

        if (remaining > 0) {
            requestAnimationFrame(step);
        } else {
            document.getElementById("time-remaining").innerHTML = "공매 마감";
        }
    }

    requestAnimationFrame(step);
});