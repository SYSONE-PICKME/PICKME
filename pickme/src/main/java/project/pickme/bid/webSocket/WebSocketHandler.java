package project.pickme.bid.webSocket;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.pickme.bid.dto.AddBidDto;
import project.pickme.bid.dto.MaxPriceDto;
import project.pickme.bid.dto.SelectedBidDto;
import project.pickme.bid.service.BidService;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
	private final WebSocketService webSocketService;
	private final BidService bidService;
	private final ObjectMapper objectMapper;

	@Override    //클라이언트와 메세지 송수신
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		try {
			// 메시지를 JSON으로 파싱
			JsonNode receiveMessage = objectMapper.readTree(message.getPayload());
			String type = receiveMessage.get("type").asText();

			if("BID".equals(type)){
				String payload = message.getPayload();
				AddBidDto addBidDto = objectMapper.readValue(payload, AddBidDto.class);
				MaxPriceDto maxPriceDto = bidService.addBid(addBidDto);
				webSocketService.sendBidToAllClient(addBidDto.getItemId(), maxPriceDto);
			}
			if("BID_END".equals(type)){
				System.out.println("들어옴");
				String payload = message.getPayload();
				SelectedBidDto selectedBidDto = objectMapper.readValue(payload, SelectedBidDto.class);
				bidService.closeBid(selectedBidDto.getBidId());
			}
		} catch (Exception e) {
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
