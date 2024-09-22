document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById("deleteModal");
    const confirmBtn = document.getElementById("confirm-btn");
    const cancelBtn = document.getElementById("cancel-btn");

    let noticeIdToDelete = null;

    window.confirmDelete = function(noticeId) {
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
                url: `/customs/notices/delete/${noticeIdToDelete}`,
                type: 'DELETE',
                success: function(response) {
                    modal.style.display = "none";
                    window.location.href = "/customs/notices";
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
});
