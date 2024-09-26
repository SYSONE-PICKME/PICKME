package project.pickme.bid.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.pickme.bid.dto.reqeust.SelectedBidDto;
import project.pickme.bid.repository.BidMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class BidSchedulingService {
	private static final String EVERY_HOUR = "0 0 * * * *";

	private final BidMapper bidMapper;
	private final MailService mailService;

	@Scheduled(cron = EVERY_HOUR)
	public void updateStatus() throws MessagingException {
		LocalDateTime now = LocalDateTime.now()
			.withSecond(0)
			.withNano(0);

		List<Long> bidIds = new ArrayList<>();
		List<SelectedBidDto> allSelectedBid = bidMapper.findAllSelectedBid();
		for (SelectedBidDto selectedBidDto : allSelectedBid) {
			bidIds.add(selectedBidDto.getBidId());
			mailService.sendSuccessfulBidMail(selectedBidDto);
		}

		bidMapper.updateAllBidSuccess(bidIds);

		log.info("Starting bid scheduled status update at {}", now);
	}
}
