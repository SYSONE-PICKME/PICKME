document.addEventListener('DOMContentLoaded', function () {
    const fileInput = document.getElementById('AddImgs');
    const preview = document.getElementById('Preview');

    fileInput.addEventListener('change', function (e) {
        const files = e.target.files;
        handleFiles(files);
    });

    function handleFiles(files) {
        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            if (!file.type.startsWith('image/')) {
                continue;
            }

            const reader = new FileReader();
            reader.onload = (function (file) {
                return function (e) {
                    const li = document.createElement('li');
                    li.innerHTML = `
                                <img src="${e.target.result}" alt="${file.name}">
                                <button type="button" class="delBtn" aria-label="삭제">x</button>
                            `;
                    preview.appendChild(li);

                    li.querySelector('.delBtn').addEventListener('click', function () {
                        li.remove();
                    });
                };
            })(file);
            reader.readAsDataURL(file);
        }
    }

    new Sortable(preview, {
        animation: 150,
        ghostClass: 'blue-background-class'
    });
});
