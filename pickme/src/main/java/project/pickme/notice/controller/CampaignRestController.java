package project.pickme.notice.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.pickme.common.annotation.CurrentUser;
import project.pickme.common.response.BaseResponse;
import project.pickme.notice.dto.CampaignDto;
import project.pickme.notice.service.CampaignService;
import project.pickme.user.domain.Customs;

@RestController
@RequestMapping("/customs/campaigns")
@RequiredArgsConstructor
public class CampaignRestController {

	private final CampaignService campaignService;

	/**
	 * 새로운 캠페인을 생성
	 *
	 * @param campaignDto 생성할 캠페인의 정보를 담은 DTO
	 * @param customs 현재 로그인한 사용자 정보
	 * @return 생성된 캠페인의 ID를 포함한 BaseResponse 객체를 반환
	 * @throws IOException 파일 업로드 등의 과정에서 발생할 수 있는 IO 예외를 처리
	 */
	@PostMapping("/create")
	public BaseResponse<?> createCampaign(@ModelAttribute CampaignDto campaignDto, @CurrentUser Customs customs) throws IOException {
		Long campaignId = campaignService.createCampaign(campaignDto, customs);
		return BaseResponse.ok(campaignId);
	}

	/**
	 * 기존 캠페인을 수정
	 *
	 * @param id 수정할 캠페인의 ID
	 * @param campaignDto 수정할 캠페인의 정보를 담은 DTO
	 * @param customs 현재 로그인한 사용자 정보
	 * @return 수정된 캠페인에 대한 성공 응답을 반환
	 * @throws IOException 파일 업로드 등의 과정에서 발생할 수 있는 IO 예외를 처리
	 */
	@PutMapping("/{id}")
	public BaseResponse<?> updateCampaign(@PathVariable("id") Long id,
		@ModelAttribute CampaignDto campaignDto,
		@CurrentUser Customs customs) throws IOException {
		campaignService.updateCampaign(campaignDto, customs);
		return BaseResponse.ok();
	}

	/**
	 * 캠페인을 삭제
	 *
	 * @param id 삭제할 캠페인의 ID
	 * @return 성공적인 삭제를 알리는 BaseResponse 객체를 반환
	 */
	@DeleteMapping("/{id}")
	public BaseResponse<Void> deleteCampaign(@PathVariable("id") Long id) {
		campaignService.deleteCampaign(id);
		return BaseResponse.ok();
	}
}
