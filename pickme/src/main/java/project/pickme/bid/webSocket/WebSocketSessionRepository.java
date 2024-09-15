package project.pickme.bid.webSocket;

import static java.util.Collections.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class WebSocketSessionRepository {
	private final Map<Long, Set<String>> itemRoom = new ConcurrentHashMap<>();
	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

	public WebSocketSession findSessionByUserId(String userId) {
		return sessions.get(userId);
	}

	public void saveUserInItem(Long itemId, String userId, WebSocketSession session) {
		Set<String> users = itemRoom.computeIfAbsent(itemId, k -> new HashSet<>());
		users.add(userId);
		itemRoom.put(itemId, users);

		addUserSession(userId, session);
		log.info("{}번 공매품에 유저{} 입장", itemId, userId);
		log.info("{}번 공매품에 입장한 유저 수: {}명", itemId, users.size());
	}

	public List<WebSocketSession> findAllUserSession(Long itemId) {
		return itemRoom.getOrDefault(itemId, emptySet()).stream()
			.map(sessions::get)
			.filter(Objects::nonNull)
			.toList();
	}

	private void addUserSession(String userId, WebSocketSession session) {
		if (sessions.get(userId) == null || !sessions.get(userId).isOpen()) {
			sessions.put(userId, session);
			log.info("유저: {} 웹소켓 세션 저장 성공", userId);
		} else {
			log.info("유저: {}는 이미 웹소켓 세션이 존재합니다.", userId);
		}
	}

	public void closeAllSessionByItemId(Long itemId) {    //마감된 공매품 세션 전부 삭제
		Set<String> userIds = itemRoom.get(itemId);
		itemRoom.remove(itemId);

		if (userIds == null) {
			return;
		}

		userIds.forEach(sessions::remove);
		log.info("공매품: {} 웹소켓 세션 삭제", itemId);
	}

	public void closeUserSession(Long itemId, String userId) {
		Set<String> userIds = itemRoom.get(itemId);

		if (userIds != null) {
			userIds.remove(userId);
		}

		sessions.remove(userId);

		log.info("공매품: {}, 유저: {} 웹소켓 세션 삭제", itemId, userId);
	}
}
