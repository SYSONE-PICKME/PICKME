package project.pickme.notice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.pickme.notice.constant.NoticeType;
import project.pickme.notice.converter.CampaignConverter;
import project.pickme.notice.converter.NoticeConverter;
import project.pickme.notice.domain.Notice;
import project.pickme.notice.dto.CampaignDto;
import project.pickme.notice.dto.NoticeDto;
import project.pickme.notice.repository.NoticeMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomsMainService {

	private final NoticeMapper noticeMapper;

	public Map<String, List<?>> getRecentAll() {
		List<Notice> recentAll = noticeMapper.selectRecentAll();
		List<NoticeDto> notices = new ArrayList<>();
		List<CampaignDto> campaigns = new ArrayList<>();

		for (Notice notice : recentAll) {
			if (notice.getType() == NoticeType.NOTICE) {
				notices.add(NoticeConverter.fromEntity(notice));
			} else if (notice.getType() == NoticeType.CAMPAIGN) {
				campaigns.add(CampaignConverter.fromEntity(notice));
			}
		}

		Map<String, List<?>> result = new HashMap<>();
		result.put("notices", notices);
		result.put("campaigns", campaigns);
		return result;
	}

	public List<NoticeDto> getRecentNotices() {
		return noticeMapper.selectRecentNotices().stream()
			.map(NoticeConverter::fromEntity)
			.toList();
	}

	public List<CampaignDto> getRecentCampaigns() {
		return noticeMapper.selectRecentCampaigns().stream()
			.map(CampaignConverter::fromEntity)
			.toList();
	}
}
