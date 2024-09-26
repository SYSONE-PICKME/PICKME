package project.pickme.item.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.pickme.bid.dto.reqeust.SelectedBidDto;
import project.pickme.bid.repository.BidMapper;
import project.pickme.bid.service.MailService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemSchedulingService {
	private static final String EVERY_HOUR = "0 0 * * * *";
	private final BidMapper bidMapper;
	private final MailService mailService;

	@Scheduled(cron = EVERY_HOUR)
	public void updateStatus() throws MessagingException {
		LocalDateTime now = LocalDateTime.now()
			.withSecond(0)
			.withNano(0);

		List<SelectedBidDto> selectedBidDtos = bidMapper.findAllSuccessBid();

		for (SelectedBidDto selectedBidDto : selectedBidDtos) {	//낙찰된 사용자에게 메일 전송
			mailService.sendSuccessfulBidMail(selectedBidDto);
		}

		log.info("Starting item scheduled status update at {}", now);
	}
}
