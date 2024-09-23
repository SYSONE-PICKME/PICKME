document.addEventListener("DOMContentLoaded", function() {
    const modal = document.getElementById("myModal");
    const openModalButton = document.getElementById("unit-button");
    const closeButton = document.querySelector(".close-button");

    // 모달 열기
    openModalButton.addEventListener("click", function() {
        modal.style.display = "block";
    });

    // 모달 닫기
    closeButton.addEventListener("click", function() {
        modal.style.display = "none";
    });

    // 모달 외부 클릭 시 닫기
    window.addEventListener("click", function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
});
