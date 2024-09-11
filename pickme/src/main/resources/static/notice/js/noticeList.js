document.addEventListener('DOMContentLoaded', function() {
    const noticeList = document.querySelector('.notice-list');
    const pageIndicators = document.getElementById('pageIndicators');
    const prevBtn = document.getElementById('prevBtn');
    const nextBtn = document.getElementById('nextBtn');

    let currentPage = 1;
    const itemsPerPage = 6;
    let allNotices = [];

    allNotices = Array.from(noticeList.children);

    const totalPages = Math.ceil(allNotices.length / itemsPerPage);

    function updatePagination() {
        pageIndicators.innerHTML = '';
        for (let i = 1; i <= totalPages; i++) {
            const dot = document.createElement('span');
            dot.classList.add('page-dot');
            if (i === currentPage) {
                dot.classList.add('active');
            }
            dot.addEventListener('click', () => goToPage(i));
            pageIndicators.appendChild(dot);
        }

        prevBtn.disabled = currentPage === 1;
        nextBtn.disabled = currentPage === totalPages;
    }

    function displayNotices(page) {
        const start = (page - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        noticeList.innerHTML = '';
        allNotices.slice(start, end).forEach(notice => {
            noticeList.appendChild(notice);
        });
    }

    function goToPage(page) {
        currentPage = page;
        displayNotices(currentPage);
        updatePagination();
    }

    prevBtn.addEventListener('click', () => {
        if (currentPage > 1) {
            goToPage(currentPage - 1);
        }
    });

    nextBtn.addEventListener('click', () => {
        if (currentPage < totalPages) {
            goToPage(currentPage + 1);
        }
    });

    goToPage(1);
});
