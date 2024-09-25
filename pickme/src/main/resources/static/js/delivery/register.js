let isLoading = false;
let hasNextPage = true;
let after = null;

function getCredentials() {
    return $.ajax({
        url: "/tracking/getAuthHeader",
        method: "GET",
        dataType: "json",
    }).then(response => {
        return response.authHeader;
    }).fail((jqXHR, textStatus, errorThrown) => {
        console.error('Credentials 가져오기 오류:', textStatus, errorThrown);
        throw new Error('Credentials 가져오기 오류');
    });
}

function fetchCarriers() {
    if (isLoading || !hasNextPage) return;
    isLoading = true;

    getCredentials().then(authHeader => {
        return $.ajax({
            url: "https://apis.tracker.delivery/graphql",
            method: "POST",
            contentType: "application/json", // 수정된 부분
            headers: {
                "Authorization": authHeader
            },
            data: JSON.stringify({
                query: `query CarrierList($after: String) {
                                carriers(first: 50, after: $after) {
                                    pageInfo {
                                        hasNextPage
                                        endCursor
                                    }
                                    edges {
                                        node {
                                            id
                                            name
                                        }
                                    }
                                }
                            }`,
                variables: {
                    after: after
                }
            }),
            success: function (response) {
                console.log('응답 데이터:', response);
                let carriers = response.data.carriers.edges;
                let pageInfo = response.data.carriers.pageInfo;
                hasNextPage = pageInfo.hasNextPage;
                after = pageInfo.endCursor;

                // id가 'kr'로 시작하는 항목만 필터링
                let filteredCarriers = carriers.filter(edge => edge.node.id.startsWith('kr'));
                populateDropdown(filteredCarriers);

                // 만약 다음 페이지가 있다면 계속 로드
                if (hasNextPage) {
                    fetchCarriers();
                }

            },
            error: function (xhr) {
                console.error('데이터 가져오기 오류:', xhr);
            },
            complete: function () {
                isLoading = false;
            }
        });
    }).catch(error => {
        console.error('Credentials 가져오기 오류:', error);
    });
}

function populateDropdown(carriers) {
    const dropdown = $('#carrierDropdown');
    if (dropdown.children().length === 1 && dropdown.children().text() === 'Loading...') {
        dropdown.empty(); // 초기 로딩 상태 제거
    }

    carriers.forEach(edge => {
        const carrier = edge.node;
        const option = $('<option></option>')
            .val(carrier.id)
            .text(carrier.name);
        dropdown.append(option);
    });

    console.log('Dropdown populated:', dropdown);
}

// 페이지 로드 시 데이터 로드
$(document).ready(function () {
    fetchCarriers();
});

$('#submitButton').on('click', function () {
    const itemId = $('#itemIdInput').val();
    const userId = $('#userIdInput').val();
    const invoiceNumber = $('#invoiceNumber').val();
    const selectedOption = $('#carrierDropdown option:selected');
    const code = selectedOption.val();
    const courier = selectedOption.text();

    if (!code || !invoiceNumber) {
        alert("모든 필드를 입력하세요.");
        return;
    }

    console.log({
        itemId: itemId,
        userId: userId,
        code: code,
        invoiceNumber: invoiceNumber,
        courier: courier
    });

    $.ajax({
        type: 'POST',
        url: '/customs/delivery',
        data: JSON.stringify({
            itemId: itemId,
            userId: userId,
            code: code,
            invoiceNumber: invoiceNumber,
            courier: courier
        }),
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            alert('송장이 성공적으로 등록되었습니다.');
            window.location.href = '/customs/delivery';
        },
        error: function (error) {
            alert('송장 등록 중 오류가 발생했습니다.');
            console.log(error);
        }
    });
});
