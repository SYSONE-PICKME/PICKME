document.addEventListener('DOMContentLoaded', function() {
    const productsGrid = document.querySelector('.products-grid');
    const pageDots = document.querySelector('.page-dots');
    const prevButton = document.querySelector('.prev');
    const nextButton = document.querySelector('.next');

    let currentPage = 1;
    const itemsPerPage = 4;
    const allProducts = Array.from(productsGrid.children);
    const totalPages = Math.ceil(allProducts.length / itemsPerPage);

    function updatePagination() {
        pageDots.innerHTML = '';
        for (let i = 1; i <= totalPages; i++) {
            const dot = document.createElement('span');
            dot.classList.add('dot');
            if (i === currentPage) {
                dot.classList.add('active');
            }
            dot.addEventListener('click', () => goToPage(i));
            pageDots.appendChild(dot);
        }
        prevButton.disabled = currentPage === 1;
        nextButton.disabled = currentPage === totalPages;
    }

    function displayProducts(page) {
        const start = (page - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        productsGrid.innerHTML = '';
        allProducts.slice(start, end).forEach(product => {
            productsGrid.appendChild(product);
        });
    }

    function goToPage(page) {
        currentPage = page;
        displayProducts(currentPage);
        updatePagination();
    }

    prevButton.addEventListener('click', () => {
        if (currentPage > 1) {
            goToPage(currentPage - 1);
        }
    });

    nextButton.addEventListener('click', () => {
        if (currentPage < totalPages) {
            goToPage(currentPage + 1);
        }
    });

    goToPage(1);
});

document.addEventListener('DOMContentLoaded', function() {
    const greenBackground = document.querySelector('.green-background');
    const productsGrid = document.querySelector('.products-grid');

    function updateBackgroundPosition() {
        const gridRect = productsGrid.getBoundingClientRect();
        const offset = gridRect.top + gridRect.height -100;
        greenBackground.style.transform = `translateY(calc(-50% + ${offset}px))`;
    }

    window.addEventListener('scroll', updateBackgroundPosition);
    window.addEventListener('resize', updateBackgroundPosition);
    updateBackgroundPosition();
});

//window.onload = function () {
//
//	let slider = document.querySelector('#slider');
//	let move = document.querySelector('#move');
//	let moveLi = Array.from(document.querySelectorAll('#slider #move li'));
//	let forword = document.querySelector('#slider #forword');
//	let back = document.querySelector('#slider #back');
//	let counter = 1;
//	let time = 5000
//	let line = document.querySelector('#slider #line');
//	let dots = document.querySelector('#slider #dots');
//	let dot;
//
//	for (i = 0; i < moveLi.length; i++) {
//
//		dot = document.createElement('li');
//		dots.appendChild(dot);
//		dot.value = i;
//	}
//
//	dot = dots.getElementsByTagName('li');
//
//	line.style.animation = 'line ' + (time / 1000) + 's linear infinite';
//	dot[0].classList.add('active');
//
//	function moveUP() {
//
//		if (counter == moveLi.length) {
//
//			moveLi[0].style.marginLeft = '0%';
//			counter = 1;
//
//		} else if (counter >= 1) {
//
//			moveLi[0].style.marginLeft = '-' + counter * 100 + '%';
//			counter++;
//		}
//
//		if (counter == 1) {
//
//			dot[moveLi.length - 1].classList.remove('active');
//			dot[0].classList.add('active');
//
//		} else if (counter > 1) {
//
//			dot[counter - 2].classList.remove('active');
//			dot[counter - 1].classList.add('active');
//
//		}
//
//	}
//
//	function moveDOWN() {
//
//		if (counter == 1) {
//
//			moveLi[0].style.marginLeft = '-' + (moveLi.length - 1) * 100 + '%';
//			counter = moveLi.length;
//			dot[0].classList.remove('active');
//			dot[moveLi.length - 1].classList.add('active');
//
//		} else if (counter <= moveLi.length) {
//
//			counter = counter - 2;
//			moveLi[0].style.marginLeft = '-' + counter * 100 + '%';
//			counter++;
//
//			dot[counter].classList.remove('active');
//			dot[counter - 1].classList.add('active');
//
//		}
//
//	}
//
//	for (i = 0; i < dot.length; i++) {
//
//		dot[i].addEventListener('click', function (e) {
//
//			dot[counter - 1].classList.remove('active');
//			counter = e.target.value + 1;
//			dot[e.target.value].classList.add('active');
//			moveLi[0].style.marginLeft = '-' + (counter - 1) * 100 + '%';
//
//		});
//
//	}
//
//	forword.onclick = moveUP;
//	back.onclick = moveDOWN;
//
//	let autoPlay = setInterval(moveUP, time);
//
//	slider.onmouseover = function () {
//
//		autoPlay = clearInterval(autoPlay);
//		line.style.animation = '';
//
//	}
//
//	slider.onmouseout = function () {
//
//		autoPlay = setInterval(moveUP, time);
//		line.style.animation = 'line ' + (time / 1000) + 's linear infinite';
//
//	}
//
//}

//window.onload = function () {
//    let slider = document.querySelector('#slider');
//    let move = document.querySelector('#move');
//    let moveLi = Array.from(document.querySelectorAll('#slider #move li'));
//    let forword = document.querySelector('#slider #forword');
//    let back = document.querySelector('#slider #back');
//    let counter = 0;
//    let itemWidth = slider.clientWidth;
//    let totalItems = moveLi.length;
//
//    function moveSlide() {
//        move.style.transform = `translateX(${-counter * itemWidth}px)`;
//    }
//
//    function updateDots() {
//        let dots = document.querySelectorAll('#dots li');
//        dots.forEach((dot, index) => {
//            dot.classList.toggle('active', index === counter);
//        });
//    }
//
//    function moveForward() {
//        if (counter >= totalItems - 1) {
//            counter = 0;
//        } else {
//            counter++;
//        }
//        moveSlide();
//        updateDots();
//    }
//
//    function moveBackward() {
//        if (counter <= 0) {
//            counter = totalItems - 1;
//        } else {
//            counter--;
//        }
//        moveSlide();
//        updateDots();
//    }
//
//    // 초기 도트 생성
//    let dotsContainer = document.querySelector('#dots');
//    for (let i = 0; i < totalItems; i++) {
//        let dot = document.createElement('li');
//        dot.addEventListener('click', () => {
//            counter = i;
//            moveSlide();
//            updateDots();
//        });
//        dotsContainer.appendChild(dot);
//    }
//    updateDots();
//
//    forword.addEventListener('click', moveForward);
//    back.addEventListener('click', moveBackward);
//
//    // 자동 슬라이드
//    setInterval(moveForward, 5000);
//};

document.addEventListener('DOMContentLoaded', function() {
    const slider = document.querySelector('#slider');
    const move = document.querySelector('#move');
    const slides = Array.from(document.querySelectorAll('.slide'));
    const forword = document.querySelector('#forword');
    const back = document.querySelector('#back');
    const dotsContainer = document.querySelector('#dots');

    let currentIndex = 0;
    const totalSlides = slides.length;

    // 도트 생성
    slides.forEach((_, index) => {
        const dot = document.createElement('li');
        dot.addEventListener('click', () => goToSlide(index));
        dotsContainer.appendChild(dot);
    });

    const dots = Array.from(dotsContainer.children);

    function updateDots() {
        dots.forEach((dot, index) => {
            dot.classList.toggle('active', index === currentIndex);
        });
    }

    function goToSlide(index) {
        currentIndex = index;
        move.style.transform = `translateX(${-100 * currentIndex}%)`;
        updateDots();
    }

    function nextSlide() {
        currentIndex = (currentIndex + 1) % totalSlides;
        goToSlide(currentIndex);
    }

    function prevSlide() {
        currentIndex = (currentIndex - 1 + totalSlides) % totalSlides;
        goToSlide(currentIndex);
    }

    forword.addEventListener('click', nextSlide);
    back.addEventListener('click', prevSlide);

    // 자동 슬라이드
    let interval = setInterval(nextSlide, 5000);

    // 마우스가 슬라이더 위에 있을 때 자동 전환 중지
    slider.addEventListener('mouseenter', () => clearInterval(interval));
    slider.addEventListener('mouseleave', () => interval = setInterval(nextSlide, 5000));

    // 초기 설정
    updateDots();
});
