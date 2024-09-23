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

async function fetchTrackingData(code, invoiceNumber) {
    let authHeader = await getCredentials();
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
        <th>상태 코드</th>
        <th>상태 이름</th>
        <th>설명</th>`;
    table.appendChild(headerRow);

    events.forEach(event => {
        let row = document.createElement("tr");
        row.innerHTML = `
            <td>${event.node.time}</td>
            <td>${event.node.status.code}</td>
            <td>${event.node.status.name}</td>
            <td>${event.node.description}</td>`;
        table.appendChild(row);
    });
}

document.addEventListener("DOMContentLoaded", function () {
    let code = document.getElementById("data-code").value;
    let invoiceNumber = document.getElementById("data-invoiceNumber").textContent;

    console.log("택배사 code:", code);
    console.log("송장번호:", invoiceNumber);

    if (code && invoiceNumber) {
        fetchTrackingData(code, invoiceNumber)
    } else {
        console.error("필수 데이터가 누락되었습니다.");
    }
});
