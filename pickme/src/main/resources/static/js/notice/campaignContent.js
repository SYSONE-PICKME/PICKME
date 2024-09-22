document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById("deleteModal");
    const confirmBtn = document.getElementById("confirm-btn");
    const cancelBtn = document.getElementById("cancel-btn");

    let campaignIdToDelete = null;

    window.confirmDelete = function(campaignId) {
        if (campaignId) {
            campaignIdToDelete = campaignId;
            modal.style.display = "flex";
        } else {
            console.error("Invalid campaign ID");
        }
    }

    confirmBtn.addEventListener('click', function() {
        if (campaignIdToDelete) {
            $.ajax({
                url: `/customs/campaigns/delete/${campaignIdToDelete}`,
                type: 'DELETE',
                success: function(response) {
                    modal.style.display = "none";
                    window.location.href = "/customs/campaigns";
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
