package project.pickme.item.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class S3Service {
	private final AmazonS3 s3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;

	public S3Service(AmazonS3 s3Client) {
		this.s3Client = s3Client;
	}

	public String uploadFile(MultipartFile file) throws IOException {
		String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		metadata.setContentType(file.getContentType());

		try (InputStream inputStream = file.getInputStream()) {
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));
		}

		return s3Client.getUrl(bucketName, fileName).toString(); // S3 URL 리턴
	}
}
