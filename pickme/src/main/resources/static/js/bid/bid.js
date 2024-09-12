import Modal from './modal.js';
import {addData} from "./chart.js";

const bidUnits = [
    {min: 0, max: 100000, unit: 1000},
    {min: 100000, max: 1000000, unit: 10000},
    {min: 1000000, max: 10000000, unit: 30000},
    {min: 10000000, max: 20000000, unit: 50000},
    {min: 20000000, max: 30000000, unit: 100000},
    {min: 30000000, max: 50000000, unit: 200000},
    {min: 50000000, max: 100000000, unit: 300000},
    {min: 100000000, max: 200000000, unit: 500000},
    {min: 200000000, max: 500000000, unit: 700000},
    {min: 500000000, max: 1000000000, unit: 1000000},
    {min: 1000000000, max: 2000000000, unit: 1200000}
]

function findBidUnit(currentPrice){
    for(let i = 0; i < bidUnits.length; i++) {
        const { min, max, unit } = bidUnits[i];
        if (currentPrice >= min && currentPrice < max) {
            return unit;
        }
    }

    return bidUnits[bidUnits.length - 1].unit;
}

function validBid(price, currentMaxPrice, myPoint) {
    const bidUnit = findBidUnit(currentMaxPrice);

    if (!price) return "금액을 입력해주세요.";
    if (price <= currentMaxPrice) return "최고 금액보다 높은 가격을 제시해주세요."
    if (parseInt(price) > myPoint) return "보유 포인트가 부족합니다.";
    if (!((price - currentMaxPrice) % bidUnit === 0)) return `현재 호가 단위가 맞지 않습니다. \n호가 단위: ${bidUnit}원`;

    return null;
}

function formatCurrency(value) {
    return value.toLocaleString('ko-KR');
}

function updateMaxPrice(maxPrice){
    const maxPriceElement = document.querySelector('.max-price');
    console.log("최고가 업데이트: ", maxPrice);
    maxPriceElement.textContent = formatCurrency(maxPrice);

    maxPriceElement.classList.add('updated');

    setTimeout(() => {
        maxPriceElement.classList.remove('updated');
    }, 500);
}

function sendBidToServer(socket, price, itemId, userId){
    const message = {
        type: 'BID',
        itemId : itemId,
        userId : userId,
        price: price
    };

    socket.send(JSON.stringify(message));
    console.log("입찰 금액 전송: ", message);
}

document.addEventListener("DOMContentLoaded", function () {
    // 시작 가격
    const startPriceElement = document.querySelector('.price');
    const startPrice = parseInt(startPriceElement.textContent);
    startPriceElement.textContent = formatCurrency(startPrice);

    // 현재 최고가
    const maxPriceElement = document.querySelector('.max-price');
    const maxPrice = parseInt(maxPriceElement.textContent);
    maxPriceElement.textContent = formatCurrency(maxPrice);

    // 보유 포인트
    const myPointElement = document.querySelector('.my-point');
    const myPoint = parseInt(myPointElement.textContent);
    myPointElement.textContent = formatCurrency(myPoint);
});

//웹소켓 부분
document.addEventListener("DOMContentLoaded", function () {
    const modal = new Modal("modal", "modal-message", "modal-confirm");
    //소켓 생성(웹소켓 메서드와 이벤트를 통해 서버와 통신 가능)
    const socket = new WebSocket(`ws://localhost:8099/connect/${itemId}/${userId}`);

    //변수 선언
    let selectedPrice = null;
    let selectedBid = null;

    socket.onopen = () => console.log("웹 소켓 open");
    socket.onmessage = (event) => {
        const data = JSON.parse(event.data); // 서버로부터 받은 데이터를 JSON 객체로 파싱
        console.log("서버로부터 받음: ", data);

        if (data.maxPrice != undefined) {
            updateMaxPrice(data.maxPrice);
            addData(data.maxPrice);
            selectedPrice = data.maxPrice;
            selectedBid = data.bidId;
        }
    };

    socket.onerror = (error) => console.log("에러 발생:", error);
    socket.onclose = () => console.log("웹 소켓 닫힘");

    // bidEnded 이벤트를 감지
    window.addEventListener('bidEnded', (event) => {
        const message = {
            type: 'BID_END',
            itemId: itemId,
            bidId: selectedBid,
            price: selectedPrice
        };
        socket.send(JSON.stringify(message));
        console.log("경매 종료 메세지 전송", message);
    });

    //입찰하기 버튼 클릭 시 실시간 금액 전송
    document.querySelector('.bid-btn').addEventListener('click', function () {
        const price = document.getElementById('price').value;
        const maxPriceElement = document.querySelector('.max-price');
        const currentMaxPrice = parseInt(maxPriceElement.textContent.replace(/,/g, ''));

        if (validBid(price, currentMaxPrice, myPoint)) {
            modal.open(validBid(price, currentMaxPrice, myPoint));
        } else {
            sendBidToServer(socket, price, itemId, userId);
        }
    })
})
