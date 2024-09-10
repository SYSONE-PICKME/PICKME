package project.pickme.bid.webSocket;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.pickme.bid.dto.request.AddBidDto;
import project.pickme.bid.dto.request.MaxPriceDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
	private final WebSocketService webSocketService;
	private final ObjectMapper objectMapper;

	@Override	//클라이언트 메세지 송수신
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		try{
			String payload = message.getPayload();

			if(payload.contains("price")){
				AddBidDto addBidDto = objectMapper.readValue(payload, AddBidDto.class);
				MaxPriceDto data = new MaxPriceDto(addBidDto.getPrice() + 10000);
				webSocketService.sendBid2AllClient(addBidDto.getItemId(), data);
			}
		}catch (Exception e){
			log.error(e.getMessage());
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);

		Map<String, Object> attributes = session.getAttributes();
		Long itemId = (Long)attributes.get("itemId");
		String userId = (String)attributes.get("userId");

		webSocketService.saveInItem(itemId, userId, session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
	}
}
