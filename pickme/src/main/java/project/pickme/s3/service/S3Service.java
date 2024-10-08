package project.pickme.s3.service;

import static project.pickme.item.exception.ItemErrorCode.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import project.pickme.common.exception.BusinessException;
import project.pickme.image.dto.ImageDto;
import project.pickme.image.repository.ImageMapper;
import project.pickme.item.dto.ItemDto;

import project.pickme.s3.config.S3Config;

@Service
@RequiredArgsConstructor
public class S3Service {
	private final AmazonS3 amazonS3;
	private final S3Config s3Config;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	private final ImageMapper imageMapper;

	public String uploadImage(MultipartFile file) {
		String fileName = createFileName(file.getOriginalFilename());

		ObjectMetadata objectMetadata = new ObjectMetadata();        // ObjectMetadata 를 통해 파일에 대한 정보를 추가
		objectMetadata.setContentLength(file.getSize());        // multipartFil 의 크기 설정 (byte)
		objectMetadata.setContentType(file.getContentType());    // multipartFil 의 컨텐츠 유형 설정

		try (InputStream inputStream = file.getInputStream()) {
			amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)        // 객체를 S3에 업로드
				.withCannedAcl(CannedAccessControlList.PublicRead));        // 업로드된 객체에 대한 공개 읽기 권한을 설정
		} catch (IOException e) {
			throw new BusinessException(IMAGE_UPLOAD_FAILED);
		}

		return amazonS3.getUrl(bucket, fileName).toString();
	}

	// 파일 이름 생성
	private String createFileName(String fileName) {
		return UUID.randomUUID().toString();
	}

	public void uploadImages(ItemDto itemDto, MultipartFile[] files) {
		int sequence = 0;
		List<ImageDto> images = new ArrayList<>();
		for (MultipartFile file : files) {
			images.add(new ImageDto(itemDto.getItemId(), file.getOriginalFilename(), uploadImage(file), sequence));
			sequence++;
		}
		imageMapper.insertImages(images);
	}

	public void deleteFile(String fileUrl) {
		String fileName = extractKeyFromUrl(fileUrl);
		amazonS3.deleteObject(s3Config.getBucket(), fileName);
	}

	private String extractKeyFromUrl(String url) {
		return url.substring(url.lastIndexOf("/") + 1);
	}
}
