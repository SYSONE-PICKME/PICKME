package project.pickme.item.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.pickme.item.repository.ItemMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemSchedulingService {
	private static final String EVERY_HOUR = "0 0 * * * *";

	private final ItemMapper itemMapper;

	@Scheduled(cron = EVERY_HOUR)
	public void updateStatus() {
		LocalDateTime now = LocalDateTime.now()
			.withSecond(0)
			.withNano(0);

		log.info("Starting scheduled status update at {}", now);
		itemMapper.updateStatus(now);
	}
}
