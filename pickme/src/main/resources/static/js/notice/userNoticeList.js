//$(document).ready(function() {
//    let page = 0;
//    const size = 10;
//
//    function loadNotices() {
//        $.ajax({
//            url: '/user/notices/list',
//            method: 'GET',
//            data: { page: page, size: size },
//            success: function(response) {
//                if (response.success) {
//                    const notices = response.data;
//                    let html = '';
//                    notices.forEach(function(notice) {
//                        html += `<tr>
//                            <td>${notice.title}</td>
//                            <td>${new Date(notice.createTime).toLocaleDateString()}</td>
//                            <td>${notice.customsName}</td>
//                        </tr>`;
//                    });
//                    $('#noticeTable tbody').append(html);
//                    page++;
//
//                    if (notices.length < size) {
//                        $('#loadMore').hide();
//                    }
//                } else {
//                    console.error('Failed to load notices');
//                }
//            },
//            error: function(xhr, status, error) {
//                console.error('Error loading notices:', error);
//            }
//        });
//    }
//    loadNotices();
//
//    $('#loadMore').click(function(e) {
//        e.preventDefault();
//        loadNotices();
//    });
//});

$(document).ready(function() {
    let page = 0;
    const size = 10;

    function loadNotices() {
        $.ajax({
            url: '/user/notices/list',
            method: 'GET',
            data: { page: page, size: size },
            success: function(response) {
                if (response.success) {
                    const notices = response.data;
                    let html = '';
                    notices.forEach(function(notice) {
                        html += `<tr class="notice-row" data-id="${notice.id}">
                            <td>${notice.title}</td>
                            <td>${new Date(notice.createTime).toLocaleDateString()}</td>
                            <td>${notice.customsName}</td>
                        </tr>`;
                    });
                    $('#noticeTable tbody').append(html);
                    page++;

                    if (notices.length < size) {
                        $('#loadMore').hide();
                    }
                } else {
                    console.error('Failed to load notices');
                }
            },
            error: function(xhr, status, error) {
                console.error('Error loading notices:', error);
            }
        });
    }

    loadNotices();

    $('#loadMore').click(function(e) {
        e.preventDefault();
        loadNotices();
    });

    $(document).on('click', '.notice-row', function() {
        const noticeId = $(this).data('id');
        window.location.href = `/user/notices/${noticeId}`;
    });
});