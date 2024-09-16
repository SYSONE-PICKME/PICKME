import Modal from './modal.js';
import {addData} from './chart.js';
import {validBid, formatCurrency, updateMaxPrice } from './price.js';

let myPoint;

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
    myPoint = parseInt(myPointElement.textContent);
    myPointElement.textContent = formatCurrency(myPoint);
});

document.querySelector('.recharge-btn').addEventListener('click', function() {
    window.location.href = '/user/charge';
});

//웹소켓 부분
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
//변수 선언
let selectedPrice = null;
let selectedBid = null;

const handlers = {
    priceUpdate: function(data){
        if(data.maxPrice != undefined){
            updateMaxPrice(data.maxPrice);
            addData(data.maxPrice);
            selectedPrice = data.maxPrice;
            selectedBid = data.bidId;
        }
    },
    bidResult: function(data){
        if (data.result === 'success') {
            displayConfetti();
            modal.open("입찰에 성공하셨습니다~");
        }
        if (data.result === 'fail'){
            modal.open("입찰에 실패했습니다.");
        }
    }
};

function handleSocketMessage(event) {
    const data = JSON.parse(event.data);
    console.log("서버로부터 받음: ", data);

    const handler = handlers[data.type];
    if(handler) {
        handler(data);
    } else {
        console.warn("알 수 없는 메세지 타입", data.type);
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const modal = new Modal("modal", "modal-message", "modal-confirm");
    const itemName = document.querySelector('.item-name').textContent;
    const itemImage = document.querySelector('.item-image').textContent;

    let socket = new WebSocket(`ws://localhost:8099/connect/${itemId}/${userId}`);
    socket.onopen = () => console.log("웹 소켓 open");

    const createSocketConnection = () => {
        socket = new WebSocket(`ws://localhost:8099/connect/${itemId}/${userId}`);
        socket.onopen = () => console.log("웹 소켓 open");
        socket.onmessage = handleSocketMessage;
        socket.onerror = (error) => console.log("에러 발생:", error);
        socket.onclose = () => console.log("웹 소켓 닫힘");
    };

    const closeSocketConnection = () => {
        if (socket && socket.readyState === WebSocket.OPEN) {
            const message = {
                type: 'EXIT',
                itemId: itemId,
                userId: userId
            };
            socket.send(JSON.stringify(message));
            socket.close();
            console.log("화면 나감 메시지 전송", message);
        }
    };

    socket.onmessage = handleSocketMessage;

    document.addEventListener('visibilitychange', function () {
        if (document.visibilityState === 'visible') {
            createSocketConnection();
        } else if (document.visibilityState === 'hidden') {
            closeSocketConnection();
        }
    });

    window.addEventListener('bidEnded', (event) => {
        const message = {
            type: 'BID_END',
            itemId: itemId,
            bidId: selectedBid,
            userId: userId,
            price: selectedPrice,
            itemName: itemName,
            itemImage: itemImage
        };
        socket.send(JSON.stringify(message));
        console.log("경매 종료 메세지 전송", message);
    });

    document.querySelector('.bid-btn').addEventListener('click', function () {
        const price = document.getElementById('price').value;
        console.log("myPoint", myPoint);

        const maxPriceElement = document.querySelector('.max-price');
        const currentMaxPrice = parseInt(maxPriceElement.textContent.replace(/,/g, ''));

        const validationError = validBid(price, currentMaxPrice, myPoint);
        if (validationError) {
            modal.open(validationError);
        } else {
            sendBidToServer(socket, price, itemId, userId);
        }
    });
});
