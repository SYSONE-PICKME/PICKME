document.addEventListener('DOMContentLoaded', function() {
    const prevBtn = document.querySelector('.prev-btn');
    const nextBtn = document.querySelector('.next-btn');
    const pageDots = document.querySelectorAll('.page-dot');
    let currentPage = 0;

    // 페이지네이션 업데이트 함수
    function updatePagination() {
        pageDots.forEach((dot, index) => {
            dot.classList.toggle('active', index === currentPage);
        });
    }

    // 이전 버튼 클릭 이벤트
    prevBtn.addEventListener('click', () => {
        if (currentPage > 0) {
            currentPage--;
            updatePagination();
            // 여기에 이전 페이지의 공지사항을 로드하는 로직을 추가해야 합니다
        }
    });

    // 다음 버튼 클릭 이벤트
    nextBtn.addEventListener('click', () => {
        if (currentPage < pageDots.length - 1) {
            currentPage++;
            updatePagination();
            // 여기에 다음 페이지의 공지사항을 로드하는 로직을 추가해야 합니다
        }
    });

    // 수정 버튼 클릭 이벤트
    document.querySelectorAll('.btn-primary').forEach(btn => {
        btn.addEventListener('click', function() {
            // 수정 동작 처리
            console.log('수정 버튼 클릭됨');
        });
    });

    // 삭제 버튼 클릭 이벤트
    document.querySelectorAll('.btn-secondary').forEach(btn => {
        btn.addEventListener('click', function() {
            // 삭제 동작 처리
            console.log('삭제 버튼 클릭됨');
        });
    });
});