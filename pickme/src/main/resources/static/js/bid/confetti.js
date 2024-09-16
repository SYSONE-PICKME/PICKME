function displayConfetti() {
    const confettiWrapper = document.getElementById('confetti-wrapper');

    confettiWrapper.innerHTML = '';

    for (let i = 0; i < 100; i++) {
        let randomRotation = Math.floor(Math.random() * 360);
        let randomScale = Math.random();
        let randomXPosition = Math.floor(Math.random() * window.innerWidth);
        let randomAnimationDelay = Math.random() * 3;
        let colors = ['#0CD977', '#FF1C1C', '#FF93DE', '#5767ED', '#FFC61C', '#8497B0'];
        let randomColor = colors[Math.floor(Math.random() * colors.length)];

        let confetti = document.createElement('div');
        confetti.className = 'confetti';
        confetti.style.backgroundColor = randomColor;
        confetti.style.opacity = randomScale;
        confetti.style.transform = `rotate(${randomRotation}deg)`;
        confetti.style.left = `${randomXPosition}px`;
        confetti.style.top = `-50px`;
        confetti.style.animationDelay = `${randomAnimationDelay}s`;

        confettiWrapper.appendChild(confetti);

        setTimeout(() => {
            confetti.remove();
        }, 3000);
    }

    // 화면 클릭 시 confetti 제거
    document.addEventListener('click', function () {
        confettiWrapper.innerHTML = '';
    });
}
