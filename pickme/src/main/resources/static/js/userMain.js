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

document.addEventListener('DOMContentLoaded', function() {
    const slider = document.querySelector('#slider');
    const move = document.querySelector('#move');
    const slides = Array.from(document.querySelectorAll('.slide'));
    const forword = document.querySelector('#forword');
    const back = document.querySelector('#back');
    const dotsContainer = document.querySelector('#dots');

    let currentIndex = 0;
    const totalSlides = slides.length;

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

    let interval = setInterval(nextSlide, 5000);

    slider.addEventListener('mouseenter', () => clearInterval(interval));
    slider.addEventListener('mouseleave', () => interval = setInterval(nextSlide, 5000));

    updateDots();
});
