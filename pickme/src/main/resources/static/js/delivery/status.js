document.addEventListener("DOMContentLoaded", async function () {
    let code = document.getElementById("data-code").value;
    let invoiceNumber = document.getElementById("data-invoiceNumber").textContent;

    console.log("택배사 code:", code);
    console.log("송장번호:", invoiceNumber);

    try {
        // 인증 헤더를 비동기적으로 가져옴
        const authHeader = await getCredentials();
        if (code && invoiceNumber && authHeader) {
            await fetchTrackingData(code, invoiceNumber, authHeader);
            await fetchLastData(code, invoiceNumber, authHeader); // 마지막 데이터 가져오기
        } else {
            console.error("필수 데이터가 누락되었습니다.");
        }
    } catch (error) {
        console.error("인증 정보를 가져오는 중 오류 발생:", error);
    }
});

async function getCredentials() {
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

async function fetchTrackingData(code, invoiceNumber, authHeader) {
    let trackResponse = await fetch("https://apis.tracker.delivery/graphql", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": authHeader,
        },
        body: JSON.stringify({
            "query": `query Track($carrierId: ID!, $trackingNumber: String!) {
                          track(carrierId: $carrierId, trackingNumber: $trackingNumber) {
                            lastEvent {
                              time
                              status {
                                code
                                name
                              }
                              description
                            }
                            events(last: 10) {
                              edges {
                                node {
                                  time
                                  status {
                                    code
                                    name
                                  }
                                  description
                                }
                              }
                            }
                          }
                        }`.trim(),
            "variables": {
                "carrierId": code,
                "trackingNumber": invoiceNumber
            },
        }),
    });

    let data = await trackResponse.json();

    if (data && data.data && data.data.track) {
        displayTrackingData(data);
    } else {
        console.error("유효한 추적 데이터를 찾을 수 없습니다:", data);
        alert("추적 정보를 찾을 수 없습니다. 송장 번호 또는 택배사를 확인하세요.");
    }
}

async function fetchLastData(code, invoiceNumber, authHeader) {
    return $.ajax({
        url: "https://apis.tracker.delivery/graphql",
        method: "POST",
        contentType: "application/json",
        headers: {
            "Authorization": authHeader,
        },
        data: JSON.stringify({
            query: `
    query Track($carrierId: ID!, $trackingNumber: String!) {
      track(carrierId: $carrierId, trackingNumber: $trackingNumber) {
        lastEvent {
          time
          status {
            code
          }
        }
      }
    }`.trim(),
            variables: {
                carrierId: code,
                trackingNumber: invoiceNumber
            },
        }),
        success: function (response) {
            const lastStatus = response.data.track.lastEvent.status.code;  // 상태 코드
            updateProgressBar(lastStatus);  // 상태 코드로 진행상태 업데이트
        },
        error: function (error) {
            console.error("Failed to fetch tracking data:", error);
        }
    });
}

function displayTrackingData(data) {
    if (!data.data.track.events || data.data.track.events.edges.length === 0) {
        console.error("이벤트 데이터가 없습니다.");
        alert("배송 이벤트 정보가 없습니다.");
        return;
    }

    let events = data.data.track.events.edges;
    let table = document.getElementById("trackingTable");
    table.innerHTML = "";

    let headerRow = document.createElement("tr");
    headerRow.innerHTML = `
        <th>시간</th>
        <th>상태</th>
        <th>설명</th>`;
    table.appendChild(headerRow);

    events.forEach(event => {
        let row = document.createElement("tr");

        row.innerHTML = `
            <td>${event.node.time.replace('T', ' ')
            .split('.')[0]
            .split('+')[0]}</td>
            <td>${event.node.status.name}</td>
            <td>${event.node.description}</td>`;
        table.appendChild(row);
    });
}

function updateProgressBar(statusCode) {
    // 각 상태에 따른 그래프 진행 표시
    const steps = [
        document.querySelector('.step-container:nth-child(1)'),
        document.querySelector('.step-container:nth-child(2)'),
        document.querySelector('.step-container:nth-child(3)'),
        document.querySelector('.step-container:nth-child(4)')
    ];

    // 기존 활성화 클래스 및 inactive 클래스 제거
    steps.forEach(step => {
        if (step) {
            step.classList.remove('active');
            step.classList.remove('inactive'); // 비활성화 클래스 제거
        }
    });

    console.log(statusCode)
    // 각 상태에 따른 활성화 처리
    if (statusCode === 'INFORMATION_RECEIVED') {
        steps[0].classList.add('active');
    } else if (statusCode === 'AT_PICKUP' || statusCode === 'IN_TRANSIT') {
        steps[0].classList.add('active');
        steps[1].classList.add('active');
    } else if (statusCode === 'OUT_FOR_DELIVERY') {
        steps[0].classList.add('active');
        steps[1].classList.add('active');
        steps[2].classList.add('active');
    } else if (statusCode === 'AVAILABLE_FOR_PICKUP' || statusCode === 'DELIVERED') {
        steps[0].classList.add('active');
        steps[1].classList.add('active');
        steps[2].classList.add('active');
        steps[3].classList.add('active');
    } else {

    }
}

function updateAddress(buttonElement) {
    document.getElementById('updateAddressModal').style.display = 'block';
}

// 모달 닫기 이벤트
$('.close').on('click', function () {
    $('#updateAddressModal').hide();
});

// 모달 외부 클릭 시 닫기
$(window).on('click', function (event) {
    if ($(event.target).is('#updateAddressModal')) {
        $('#updateAddressModal').hide();
    }
});

$('#updateAddressButton').on('click', function () {

    document.getElementById('updateAddressModal').style.display = 'block';

    const address = $('#modifiedAddress').val();
    const userId = $('#userId').val();
    const itemId = $('#itemId').val();


    $.ajax({
        type: 'PATCH',
        url: '/user/address',
        data: JSON.stringify({
            address: address,
            userId: userId
        }),
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            console.log({
                userId: userId,
                address: address
            });
            alert('배송지가 성공적으로 수정되었습니다.');

            window.location.href = '/user/delivery/status?itemId=' + itemId + '&userId=' + userId;
        },
        error: function (error) {
            alert('배송지 수정에 실패했습니다.');
            console.log(error);
        }
    });
});
