document.addEventListener('DOMContentLoaded', function() {
    const currentPath = window.location.pathname;
    console.log("Current path:", currentPath);

    const menuItems = {
        '/user/notices': 'notices',
        '/user/item/list': 'item-list',
        '/user/item/bid-list': 'bid-list',
        '/user/charge': 'charge',
        '/user/myPage': 'my-page',
        '/user/statistics': 'statistics'
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
