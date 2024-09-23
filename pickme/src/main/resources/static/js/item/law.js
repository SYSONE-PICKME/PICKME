document.addEventListener("DOMContentLoaded", function() {
    const modal = document.getElementById("law-modal");
    const openModalButton = document.getElementById("law-button");
    const closeButton = document.querySelector(".law-close-button");

    openModalButton.addEventListener("click", function() {
        modal.style.display = "block";
    });

    closeButton.addEventListener("click", function() {
        console.log("???")
        modal.style.display = "none";
    });

    window.addEventListener("click", function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
});
