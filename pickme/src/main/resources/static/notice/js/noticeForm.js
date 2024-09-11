document.addEventListener('DOMContentLoaded', function() {
    const tabs = document.querySelectorAll('.tab');
    const forms = document.querySelectorAll('.form-content');

    tabs.forEach(tab => {
        tab.addEventListener('click', function() {
            const tabName = this.getAttribute('data-tab');

            tabs.forEach(t => t.classList.remove('active'));
            forms.forEach(f => f.classList.remove('active'));

            this.classList.add('active');
            document.getElementById(tabName + 'Form').classList.add('active');
        });
    });

    const imageUpload = document.getElementById('campaignImage');
    const previewImage = document.getElementById('previewImage');
    const imageUploadContainer = document.querySelector('.image-upload-container');

    imageUploadContainer.addEventListener('click', function() {
        imageUpload.click();
    });

    imageUpload.addEventListener('change', function(e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                previewImage.src = e.target.result;
            }
            reader.readAsDataURL(file);
        }
    });
});

document.addEventListener('DOMContentLoaded', function() {
    const imageUploadArea = document.querySelector('.image-upload-area');
    const fileInput = document.getElementById('campaignImage');
    const previewImage = document.getElementById('previewImage');

    imageUploadArea.addEventListener('click', function() {
        fileInput.click();
    });

    fileInput.addEventListener('change', function(e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                previewImage.src = e.target.result;
            }
            reader.readAsDataURL(file);
        }
    });
});
