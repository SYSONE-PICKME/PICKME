import Modal from './modal.js';
import {initChartData, addData} from './chart.js';
import {validBid, formatCurrency, updateMaxPrice } from './price.js';

let myPoint;
let selectedBid = null;
let selectedUserId = null;
let modal;

function fetchBidDetails() {
    $.ajax({
        url: `/user/bid/details/${itemId}`,
        method: 'GET',
        success: function (response) {
            if (response.success) {
                const bidDetails = response.data;

                //TODO: 넘어온 데이터가 null인 경우 처리 해야함
                document.querySelector('.max-price').textContent = formatCurrency(parseInt(bidDetails.maxPrice));
                myPoint = parseInt(bidDetails.myPoint);
                document.querySelector('.my-point').textContent = formatCurrency(myPoint);

                selectedUserId = bidDetails.userId;
                selectedBid = bidDetails.bidId;

                initChartData(bidDetails.allPrice); //입찰 추이 차트 초기화

                console.log("데이터 초기화: ", bidDetails);
            }
        }
    })
}

function endBid() {
    const itemName = document.querySelector('.item-name').textContent;
    const itemImage = document.querySelector('.item-image').textContent;

    const selectedBidDto = {
        itemId: itemId,
        bidId: selectedBid,
        itemName: itemName,
        itemImage: itemImage
    };

    $.ajax({
        url: "/user/bid/end",
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(selectedBidDto),
        success: function (response) {
            console.log("공매 종료 요청 성공: ", response);
        },
        error: function (error) {
            console.error("공매 종료 요청 실패: ", error);
        }
    })
}

document.addEventListener("DOMContentLoaded", function () {
    modal = new Modal("modal", "modal-message", "modal-confirm");

    // 시작 가격
    const startPriceElement = document.querySelector('.price');
    const startPrice = parseInt(startPriceElement.textContent);
    startPriceElement.textContent = formatCurrency(startPrice);

    fetchBidDetails();  //입찰 정보 가져오기

    document.querySelector('.recharge-btn').addEventListener('click', function() {
        window.location.href = '/user/charge';
    });
});

//웹소켓 부분
function sendBidToServer(socket, price, itemId, userId){    //입찰 전송
    const message = {
        type: 'BID',
        itemId : itemId,
        userId : userId,
        price: price
    };

    socket.send(JSON.stringify(message));
    console.log("입찰 금액 전송: ", message);
}

function sendEndToServer(socket) {  //종료 메세지 전송
    const itemName = document.querySelector('.item-name').textContent;
    const itemImage = document.querySelector('.item-image').textContent;
    
    const message = {
        type: 'BID_END',
        itemId: itemId,
        bidId: selectedBid,
        userId: selectedUserId,
        itemName: itemName,
        itemImage: itemImage
    };
    socket.send(JSON.stringify(message));
    console.log("경매 종료 메세지 전송", message);
}

function sendExitToServer(socket) {
    const message = {
        type: 'EXIT',
        itemId: itemId,
        userId: userId
    };
    socket.send(JSON.stringify(message));
    socket.close();
    console.log("화면 나감 메시지 전송", message);
}

document.addEventListener("DOMContentLoaded", function () {
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
            sendExitToServer(socket);
        }
    };

    const handlers = {
        priceUpdate: function(data){
            if(data.price != undefined){
                updateMaxPrice(data.price);
                addData(data.price);    //입찰 추이 차트 업데이트

                selectedBid = data.bidId;
                selectedUserId = data.userId;
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

    socket.onmessage = handleSocketMessage;

    document.addEventListener('visibilitychange', function () {
        if (document.visibilityState === 'visible') {
            createSocketConnection();
            fetchBidDetails();
        } else if (document.visibilityState === 'hidden') {
            closeSocketConnection();
        }
    });

    window.addEventListener('bidEnded', (event) => {
        sendEndToServer(socket);
        endBid();
    });

    document.querySelector('.bid-btn').addEventListener('click', function () {
        const price = document.getElementById('price').value;
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
