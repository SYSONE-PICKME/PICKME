var uploadCount = 0; // 업로드된 이미지 수를 추적
const maxUploads = 5; // 최대 업로드 가능 이미지 수

$(document).ready(function () {
    $('#filebox').on('change', function (e) {
        var files = e.target.files;

        // 업로드 수가 최대치를 초과했는지 확인
        if (uploadCount >= maxUploads) {
            alert('최대 5개의 이미지만 업로드할 수 있습니다.');
            return;
        }

        // 선택된 파일 각각에 대해 FileReader 실행
        for (let i = 0; i < files.length && uploadCount < maxUploads; i++) {
            var reader = new FileReader();

            reader.onload = function (e) {
                // 새 이미지 박스 생성 (처음에는 비워둠)
                var newImageBox = $("<div class='image'></div>");

                // 이미지 삭제 버튼 추가
                var deleteButton = $("<img src='/image/delete-button.png' alt='삭제' style='width: 20px; height: 20px;'>");

                // 이미지 박스에 이미지 설정
                newImageBox.css({
                    'background-image': 'url(' + e.target.result + ')',
                    'background-size': 'cover',
                    'background-position': 'center'
                });

                // 이미지 박스에 삭제 버튼 추가
                newImageBox.append(deleteButton);

                // 새로운 이미지 박스를 기존 업로드 박스 앞에 추가
                $('.image-upload').append(newImageBox);

                // 업로드 수 증가
                uploadCount++;

                // 삭제 버튼 클릭 시 이미지 삭제 기능 추가
                deleteButton.on('click', function () {
                    newImageBox.remove();
                    uploadCount--; // 삭제 시 업로드 수 감소
                });
            };

            reader.readAsDataURL(files[i]);  // 각 파일에 대해 개별적으로 FileReader 실행
        }

        // 파일 선택 후 리셋
        $(this).val('');
    });
});

