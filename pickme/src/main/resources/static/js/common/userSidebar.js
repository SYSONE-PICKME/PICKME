document.addEventListener('DOMContentLoaded', function() {
    const currentPath = window.location.pathname;
    console.log("Current path:", currentPath);

    const menuItems = {
        '/user/myPage': 'change-info',
        '/user/item/wish-list': 'wish-list',
        '/user/unpaid-bidForm': 'unpaid-bidForm',
        '/user/successful-bid-listForm': 'successful-bid-listForm',
        '/user/point/historyForm': 'historyForm',
    };

    let activeMenuFound = false;

    for (const [path, menuId] of Object.entries(menuItems)) {
        console.log(`Checking path: ${path} against current path: ${currentPath}`);
        if (currentPath.includes(path)) {
            const element = document.getElementById(menuId);
            if (element) {
                element.classList.add('active');
                activeMenuFound = true;
            } else {
                console.log(`Element not found: ${menuId}`);
            }
            break;
        }
    }
});
