function toggleMenu() {
    let menu = document.getElementById("sidebarMenu");
    if (menu.style.display === "none" || menu.style.display === "") {
        menu.style.display = "block";
    } else {
        menu.style.display = "none";
    }
}

window.onload = function() {
    let currentPath = window.location.pathname;
    let menuList = document.querySelectorAll(".menu-list");

    menuList.forEach(function(item) {
        if (item.getAttribute("href") === currentPath) {
            item.classList.add("active");
        }
    });
};
