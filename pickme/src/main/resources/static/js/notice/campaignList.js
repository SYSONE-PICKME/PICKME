document.addEventListener('DOMContentLoaded', function() {
    const campaignList = document.querySelector('.campaign-list');
    const pageIndicators = document.getElementById('pageIndicators');
    const prevBtn = document.getElementById('prevBtn');
    const nextBtn = document.getElementById('nextBtn');

    let currentPage = 1;
    const itemsPerPage = 6;
    let allCampaigns = [];

    allCampaigns = Array.from(campaignList.children);
    const totalPages = Math.ceil(allCampaigns.length / itemsPerPage);

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

    function displayCampaigns(page) {
        const start = (page - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        allCampaigns.forEach((campaign, index) => {
            if (index >= start && index < end) {
                campaign.style.display = '';
            } else {
                campaign.style.display = 'none';
            }
        });
    }

    function goToPage(page) {
        currentPage = page;
        displayCampaigns(currentPage);
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

    allCampaigns.forEach(item => {
        item.addEventListener('click', function(e) {
            if (e.target.classList.contains('btn')) {
                e.stopPropagation();
                return;
            }
            const campaignId = this.getAttribute('data-campaign-id');
            window.location.href = `/customs/campaigns/${campaignId}`;
        });
    });

    goToPage(1);
});
//axios에 대해 알아본 결과 RESTful하게 구현하려면 controller에 @Delete로 매핑한 뒤 .get이 아닌 .delete로 구현하는게 더 좋다고 기재되어 있음.
const modal = document.getElementById("deleteModal");
modal.style.display = "none";
const confirmBtn = document.getElementById("confirm-btn");
const cancelBtn = document.getElementById("cancel-btn");

let campaignIdToDelete = null;

function confirmDelete(campaignId) {
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
                $(`[data-campaign-id="${campaignIdToDelete}"]`).remove();
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
