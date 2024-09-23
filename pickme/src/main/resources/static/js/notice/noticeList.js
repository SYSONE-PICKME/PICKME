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
        allNotices.forEach((notice, index) => {
            if (index >= start && index < end) {
                notice.style.display = '';
            } else {
                notice.style.display = 'none';
            }
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

    allNotices.forEach(item => {
        item.addEventListener('click', function(e) {
            if (e.target.classList.contains('btn')) {
                e.stopPropagation();
                return;
            }
            const noticeId = this.getAttribute('data-notice-id');
            window.location.href = `/customs/notices/${noticeId}`;
        });
    });
    goToPage(1);
});
//axios에 대해 알아본 결과 RESTful하게 구현하려면 controller에 @Delete로 매핑한 뒤 .get이 아닌 .delete로 구현하는게 더 좋다고 기재되어 있음.
const modal = document.getElementById("deleteModal");
modal.style.display = "none";
const confirmBtn = document.getElementById("confirm-btn");
const cancelBtn = document.getElementById("cancel-btn");


let noticeIdToDelete = null;

function confirmDelete(noticeId) {
    if (noticeId) {
        noticeIdToDelete = noticeId;
        modal.style.display = "flex";
    } else {
        console.error("Invalid notice ID");
    }
}

confirmBtn.addEventListener('click', function() {
    if (noticeIdToDelete) {
        $.ajax({
            url: `/customs/notices/${noticeIdToDelete}`,
            type: 'DELETE',
            success: function(response) {
                modal.style.display = "none";
                $(`[data-notice-id="${noticeIdToDelete}"]`).remove();
            },
            error: function() {
                alert('삭제에 실패하였습니다.');
            }
        });
    }
});

cancelBtn.addEventListener('click', function() {
    modal.style.display = "none";
});

window.addEventListener('click', function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
});
