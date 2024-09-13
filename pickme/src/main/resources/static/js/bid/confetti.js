function displayConfetti() {
    const confettiWrapper = document.getElementById('confetti-wrapper');

    // 기존 confetti 제거
    confettiWrapper.innerHTML = '';

    for (let i = 0; i < 100; i++) {
        var randomRotation = Math.floor(Math.random() * 360);
        var randomScale = Math.random();
        var randomXPosition = Math.floor(Math.random() * window.innerWidth); // X 좌표를 랜덤으로 설정하여 화면 전체를 커버
        var randomAnimationDelay = Math.random() * 3; // 애니메이션 지연 시간을 랜덤으로 설정
        var colors = ['#0CD977', '#FF1C1C', '#FF93DE', '#5767ED', '#FFC61C', '#8497B0'];
        var randomColor = colors[Math.floor(Math.random() * colors.length)];

        var confetti = document.createElement('div');
        confetti.className = 'confetti';
        confetti.style.backgroundColor = randomColor;
        confetti.style.opacity = randomScale;
        confetti.style.transform = `rotate(${randomRotation}deg)`;
        confetti.style.left = `${randomXPosition}px`; // 각 confetti의 시작 위치를 랜덤으로 설정
        confetti.style.top = `-50px`; // 화면 위쪽에서 시작
        confetti.style.animationDelay = `${randomAnimationDelay}s`;

        confettiWrapper.appendChild(confetti);

        setTimeout(() => {
            confetti.remove();
        }, 3000); // 3초 후 confetti 제거
    }

    // 화면 클릭 시 confetti 제거
    document.addEventListener('click', function() {
        confettiWrapper.innerHTML = '';
    });
}
