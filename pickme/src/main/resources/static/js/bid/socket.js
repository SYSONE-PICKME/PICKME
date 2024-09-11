document.addEventListener("DOMContentLoaded", function () {
    //소켓 생성(웹소켓 메서드와 이벤트를 통해 서버와 통신 가능)
    const socket = new WebSocket(`ws://localhost:8099/connect/${itemId}/${userId}`);

    socket.onopen = () => {
        console.log("웹 소켓 open");
    };

    socket.onmessage = (event) => {
        const data = JSON.parse(event.data); // 서버로부터 받은 데이터를 JSON 객체로 파싱
        console.log("서버로부터 받음: ", data);

        if (data.maxPrice != undefined) {
            const maxPriceElement = document.querySelector('.max-price');
            console.log("최고가 업데이트: ", data.maxPrice);
            maxPriceElement.textContent = `${data.maxPrice} 원`;

            maxPriceElement.classList.add('updated');

            // 애니메이션이 끝난 후 클래스를 제거하여 다시 적용 가능하도록 구현
            setTimeout(() => {
                maxPriceElement.classList.remove('updated');
            }, 500);
        }
    };

    socket.onerror = (error) => {
        console.log("에러 발생:", error);
    };

    socket.onclose = () => {
        console.log("웹 소켓 닫힘");
    };

    //입찰하기 버튼 클릭 시 실시간 금액 전송
    document.querySelector('.bid-btn').addEventListener('click', function () {
        const price = document.getElementById('price').value;

        if(price){
            const message = {
                itemId: itemId,
                userId: userId,
                price: price
            };

            socket.send(JSON.stringify(message))
            console.log('입찰 금액 전송: ', message);
        } else{
            alert('금액을 입력해주세요.');
        }
    })
})
