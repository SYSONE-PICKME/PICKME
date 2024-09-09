package project.pickme.bid.webSocket;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketService {
	private final WebSocketSessionRepository webSocketSessionRepository;

	public void saveInItem(long itemId, String userId, org.springframework.web.socket.WebSocketSession session){
		webSocketSessionRepository.saveUserInItem(itemId, userId, session);
	}
}
