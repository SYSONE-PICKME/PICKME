$(document).ready(function() {
    loadRecentAll();
});

function setActiveTab(tabId) {
    $('.notice-buttons button').removeClass('active');
    $(`#${tabId}`).addClass('active');
}

function loadRecentAll() {
    setActiveTab('all-btn');
    $.ajax({
        url: '/customs/main/recent-all',
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            const notices = response.data.notices || [];
            const campaigns = response.data.campaigns || [];
            renderNotices(notices, campaigns);
        },
        error: function(error) {
            console.error('전체 데이터 로드 실패:', error);
        }
    });
}

function loadNotices(type) {
    setActiveTab(type === 'NOTICE' ? 'notice-btn' : 'campaign-btn');
    const apiUrl = type === 'NOTICE' ? '/customs/main/recent-notices' : '/customs/main/recent-campaigns';
    $.ajax({
        url: apiUrl,
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            const data = response.data || [];
            renderNotices(type === 'NOTICE' ? data : [], type === 'CAMPAIGN' ? data : []);
        },
        error: function(error) {
            console.error(type + ' 데이터 로드 실패:', error);
        }
    });
}

function renderNotices(notices, campaigns) {
    const noticeList = $('#notice-list');
    noticeList.empty();

    try {
        for (const notice of notices) {
            const noticeItem = `
                <div class="notice-item" data-type="NOTICE">
                    <div class="notice-content">
                        <strong>${notice.title}</strong>
                        <span>${new Date(notice.createTime).toLocaleDateString()}</span>
                    </div>
                    <a href="/customs/notices/${notice.id}" class="notice-link">→</a>
                </div>
            `;
            noticeList.append(noticeItem);
        }

        for (const campaign of campaigns) {
            const campaignItem = `
                <div class="notice-item" data-type="CAMPAIGN">
                    <div class="notice-content">
                        <strong>${campaign.title}</strong>
                        <span>${new Date(campaign.createTime).toLocaleDateString()}</span>
                    </div>
                    <a href="/customs/campaigns/${campaign.id}" class="notice-link">→</a>
                </div>
            `;
            noticeList.append(campaignItem);
        }
    } catch (error) {
        console.error('렌더링 중 오류 발생:', error);
    }
}