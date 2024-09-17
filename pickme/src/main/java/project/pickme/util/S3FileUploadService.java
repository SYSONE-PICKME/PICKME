package project.pickme.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;
import project.pickme.s3.config.S3Config;

@Service
@RequiredArgsConstructor
public class S3FileUploadService {

	private final AmazonS3Client amazonS3Client;
	private final S3Config s3Config;

	public String uploadFile(MultipartFile file) throws IOException {
		String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(file.getContentType());
		metadata.setContentLength(file.getSize());
		amazonS3Client.putObject(s3Config.getBucket(), fileName, file.getInputStream(), metadata);

		return amazonS3Client.getUrl(s3Config.getBucket(), fileName).toString();
	}

	public void deleteFile(String fileName) {
		amazonS3Client.deleteObject(s3Config.getBucket(), fileName);
	}
}
