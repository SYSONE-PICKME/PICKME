package project.pickme.bid.webSocket;

import static project.pickme.common.exception.ErrorCode.*;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.pickme.bid.dto.response.BidResult;
import project.pickme.bid.repository.BidMapper;
import project.pickme.common.exception.BusinessException;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketService {
	private final WebSocketSessionRepository webSocketSessionRepository;
	private final ObjectMapper objectMapper;
	private final BidMapper bidMapper;

	public void saveInItem(long itemId, String userId, WebSocketSession session) {
		webSocketSessionRepository.saveUserInItem(itemId, userId, session);
	}

	public void sendToClient(String userId, Object data) {
		try {
			WebSocketSession session = webSocketSessionRepository.findSessionByUserId(userId);
			if (session != null && session.isOpen()) {
				String jsonString = objectMapper.writeValueAsString(data);
				session.sendMessage(new TextMessage(jsonString));
			} else {
				log.info("웹 소켓 연결 안됨");
			}
		} catch (IOException e) {
			log.error("실시간 데이터 전송 에러", e.getMessage());
		}
	}

	public void sendToAllClient(Long itemId, Object data) {
		try {
			List<WebSocketSession> allUserSession = webSocketSessionRepository.findAllUserSession(itemId);
			String jsonString = objectMapper.writeValueAsString(data);
			for (WebSocketSession session : allUserSession) {
				if (session != null && session.isOpen()) {
					session.sendMessage(new TextMessage(jsonString));
				}
			}
		} catch (IOException e) {
			log.error("실시간 데이터 전송 에러");
		}
	}

	public void sendResultAllClient(Long itemId, Long bidId) {
		sendSuccessUser(itemId, bidId);
		sendToAllClient(itemId, BidResult.fail());
		closeAllConnection(itemId);
	}

	public void closeSessionByUserId(Long itemId, String userId){
		webSocketSessionRepository.closeUserSession(itemId, userId);
	}

	private void sendSuccessUser(Long itemId, Long bidId) {
		String selectedUserId = bidMapper.findBidById(bidId)
			.orElseThrow(() -> new BusinessException(NOT_FOUND_BID))
			.getUserId();

		sendToClient(selectedUserId, BidResult.success());
		closeSessionByUserId(itemId, selectedUserId);
	}

	private void closeAllConnection(Long itemId) {
		webSocketSessionRepository.closeAllSessionByItemId(itemId);
	}
}
