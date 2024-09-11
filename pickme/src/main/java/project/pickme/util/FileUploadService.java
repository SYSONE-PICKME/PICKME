package project.pickme.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadService {

	private static final String UPLOAD_DIR = "/path/to/your/project/src/main/resources/static/images";

	public String uploadFile(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			throw new IllegalArgumentException("빈 파일을 저장할 수 없습니다");
		}

		String fileName = file.getOriginalFilename();
		Path uploadPath = Paths.get(UPLOAD_DIR);

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		try {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("파일이 저장된 경로: " + filePath.toString());
			return "/images/" + fileName; // 웹에서 접근 가능한 경로 반환
		} catch (IOException e) {
			System.err.println("파일 저장 중 오류 발생: " + e.getMessage());
			throw new IOException("파일 " + fileName + " 저장 실패", e);
		}
	}
}


// @Service
// public class FileUploadService {
//
// 	@Value("${spring.resources.static-locations}")
// 	private String staticLocations;
//
// 	public String uploadFile(MultipartFile file) throws IOException {
// 		if (file.isEmpty()) {
// 			throw new IllegalArgumentException("빈 파일을 저장할 수 없습니다");
// 		}
//
// 		String fileName = file.getOriginalFilename();
// 		String staticPath = staticLocations.split(":")[1].replace("file:", "");
// 		// Path uploadPath = Paths.get(staticPath, "images");
// 		Path uploadPath = Paths.get("/path/to/your/project/src/main/resources/static/images");
//
// 		if (!Files.exists(uploadPath)) {
// 			Files.createDirectories(uploadPath);
// 		}
//
// 		try {
// 			Path filePath = uploadPath.resolve(fileName);
//
// 			// 파일 이름 중복 처리
// 			int count = 1;
// 			while (Files.exists(filePath)) {
// 				String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
// 				String extension = fileName.substring(fileName.lastIndexOf('.'));
// 				fileName = nameWithoutExtension + "_" + count + extension;
// 				filePath = uploadPath.resolve(fileName);
// 				count++;
// 			}
//
// 			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
// 			System.out.println("파일이 저장된 경로: " + filePath.toString());  // 디버깅용 로그
// 			return "/images/" + fileName; // 웹에서 접근 가능한 경로 반환
// 		} catch (IOException e) {
// 			System.err.println("파일 저장 중 오류 발생: " + e.getMessage());  // 디버깅용 로그
// 			throw new IOException("파일 " + fileName + " 저장 실패", e);
// 		}
// 	}
// }

