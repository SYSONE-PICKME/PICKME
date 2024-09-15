package project.pickme.item.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.item.constant.Status;
import project.pickme.item.dto.ImageDto;
import project.pickme.item.dto.ItemDto;
import project.pickme.item.dto.ItemFormDto;
import project.pickme.item.dto.LawDto;
import project.pickme.item.dto.ItemLawDto;
import project.pickme.item.repository.ImageMapper;
import project.pickme.item.repository.ItemMapper;
import project.pickme.item.repository.LawMapper;
import project.pickme.item.repository.ItemLawMapper;
import project.pickme.user.domain.Customs;

@Slf4j
@Component
@Service
@RequiredArgsConstructor
public class ItemService {
	private final LawMapper lawMapper;
	private final ItemMapper itemMapper;
	private final ItemLawMapper itemLawMapper;
	private final ImageMapper imageMapper;

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public void save(ItemFormDto itemFormDto, MultipartFile[] files, @CurrentUser Customs customs) throws IOException {
		LocalDateTime start = LocalDateTime.of(itemFormDto.getStartDate(), itemFormDto.getStartTime());
		LocalDateTime end = LocalDateTime.of(itemFormDto.getEndDate(), itemFormDto.getEndTime());

		ItemDto itemDto = new ItemDto(itemFormDto.getName(), itemFormDto.getCode(), itemFormDto.getType(),
			itemFormDto.getPrice(), start, end,
			Status.NOT_OPEN, customs.getId());

		itemMapper.insertItem(itemDto);

		for (long id : itemFormDto.getLawId()) {
			ItemLawDto itemLawDto = new ItemLawDto(id, itemDto.getItemId());
			itemLawMapper.insertLaw(itemLawDto);
		}
		uploadImages(itemDto, files);
	}

	public List<LawDto> findAllLaws() {
		return lawMapper.findAllLaws();
	}

	public String uploadImage(MultipartFile file) {
		String fileName = createFileName(file.getOriginalFilename());

		ObjectMetadata objectMetadata = new ObjectMetadata();        // ObjectMetadata 를 통해 파일에 대한 정보를 추가
		objectMetadata.setContentLength(file.getSize());        // multipartFil 의 크기 설정 (byte)
		objectMetadata.setContentType(file.getContentType());    // multipartFil 의 컨텐츠 유형 설정

		try (InputStream inputStream = file.getInputStream()) {
			amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)        // 객체를 S3에 업로드
				.withCannedAcl(CannedAccessControlList.PublicRead));        // 업로드된 객체에 대한 공개 읽기 권한을 설정
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다.");
		}

		return amazonS3.getUrl(bucket, fileName).toString();
	}

	// 파일 이름 생성
	private String createFileName(String fileName) {
		return UUID.randomUUID().toString().concat(fileName);
	}

	public void uploadImages(ItemDto itemDto, MultipartFile[] files) {
		int sequence = 0;

		for (MultipartFile file : files) {
			long itemId = itemDto.getItemId();
			String name = file.getOriginalFilename();
			String url = uploadImage(file);
			int seq = sequence;
			imageMapper.insertImage(new ImageDto(itemId, name, url, seq));
			sequence++;
		}
	}

	public List<ItemDto> findItemsByCustomsId(String customsId) {
		return itemMapper.findItemsByCustomsId(customsId);
	}
}
