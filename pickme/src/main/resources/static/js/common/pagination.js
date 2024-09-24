function updatePagination(pageData) {
    const paginationContainer = document.getElementById('pagination');
    paginationContainer.innerHTML = '';  // 기존 페이지네이션 삭제

    const totalPages = pageData.totalPages;  // 전체 페이지 수
    const currentPage = pageData.number;    // 현재 페이지 (0부터 시작)

    // 처음 페이지로 이동 버튼
    paginationContainer.innerHTML += `<a href="#" class="first-page ${currentPage === 0 ? 'disabled' : ''}" onclick="loadPointHistory(0, 6)">&laquo;&laquo;</a>`;

    // 이전 페이지로 이동 버튼
    paginationContainer.innerHTML += `<a href="#" class="prev-page ${currentPage === 0 ? 'disabled' : ''}" onclick="loadPointHistory(${currentPage - 1}, 6)">&laquo;</a>`;

    // 페이지 번호들
    for (let i = 0; i < totalPages; i++) {
        paginationContainer.innerHTML += `<a href="#" class="page-number ${i === currentPage ? 'active' : ''}" onclick="loadPointHistory(${i}, 6)">${i + 1}</a>`;
    }

    // 다음 페이지로 이동 버튼
    paginationContainer.innerHTML += `<a href="#" class="next-page ${currentPage === totalPages - 1 ? 'disabled' : ''}" onclick="loadPointHistory(${currentPage + 1}, 6)">&raquo;</a>`;

    // 마지막 페이지로 이동 버튼
    paginationContainer.innerHTML += `<a href="#" class="last-page ${currentPage === totalPages - 1 ? 'disabled' : ''}" onclick="loadPointHistory(${totalPages - 1}, 6)">&raquo;&raquo;</a>`;
}
