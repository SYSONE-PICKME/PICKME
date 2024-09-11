import Modal from './modal.js';

function updateMaxPrice(maxPrice){
    const maxPriceElement = document.querySelector('.max-price');
    console.log("최고가 업데이트: ", maxPrice);
    maxPriceElement.textContent = `${maxPrice}`;

    maxPriceElement.classList.add('updated');

    setTimeout(() => {
        maxPriceElement.classList.remove('updated');
    }, 500);
}

function sendBidToServer(socket, price, itemId, userId){
    const message = {
        itemId : itemId,
        userId : userId,
        price: price
    };

    socket.send(JSON.stringify(message));
    console.log("입찰 금액 전송: ", message);
}

document.addEventListener("DOMContentLoaded", function () {
    const modal = new Modal("modal", "modal-message", "modal-confirm");
    //소켓 생성(웹소켓 메서드와 이벤트를 통해 서버와 통신 가능)
    const socket = new WebSocket(`ws://localhost:8099/connect/${itemId}/${userId}`);

    socket.onopen = () => console.log("웹 소켓 open");
    socket.onmessage = (event) => {
        const data = JSON.parse(event.data); // 서버로부터 받은 데이터를 JSON 객체로 파싱
        console.log("서버로부터 받음: ", data);

        if (data.maxPrice != undefined) {
            updateMaxPrice(data.maxPrice);
        }
    };
    socket.onerror = (error) => console.log("에러 발생:", error);
    socket.onclose = () => console.log("웹 소켓 닫힘");

    //입찰하기 버튼 클릭 시 실시간 금액 전송
    document.querySelector('.bid-btn').addEventListener('click', function () {
        const price = document.getElementById('price').value;
        const maxPriceElement = document.querySelector('.max-price');
        const currentMaxPrice = parseInt(maxPriceElement.textContent);

        if (price) {
            if (parseInt(price) < currentMaxPrice) {
                modal.open("최고가 보다 높은 금액을 제시해야 합니다.")
            } else if(parseInt(price) > myPoint){
                modal.open("보유 포인트가 부족합니다.")
            } else {
                sendBidToServer(socket, price, itemId, userId);
            }
        } else {
            modal.open("금액을 입력해주세요.")
        }
    })
})
