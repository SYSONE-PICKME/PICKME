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