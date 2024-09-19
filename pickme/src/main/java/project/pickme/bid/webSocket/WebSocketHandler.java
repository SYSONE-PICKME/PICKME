package project.pickme.bid.webSocket;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.pickme.bid.dto.reqeust.AddBidDto;
import project.pickme.bid.dto.reqeust.ExitMemberDto;
import project.pickme.bid.dto.response.UpdatePriceBidDto;
import project.pickme.bid.service.BidService;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
	private final WebSocketService webSocketService;
	private final BidService bidService;
	private final ObjectMapper objectMapper;

	private final Map<String, Consumer<String>> commandMap = new HashMap<>();

	@PostConstruct
	public void init() {
		commandMap.put("BID", this::addBid);
		commandMap.put("BID_END", this::selectBid);
		commandMap.put("EXIT", this::closeConnection);
	}

	@Override    //클라이언트와 메세지 송수신
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		try {
			JsonNode receiveMessage = objectMapper.readTree(message.getPayload());
			String type = receiveMessage.get("type").asText();
			String payload = message.getPayload();

			Consumer<String> command = commandMap.get(type);
			if (command != null) {
				command.accept(payload);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private void addBid(String payload) {
		try {
			AddBidDto addBidDto = objectMapper.readValue(payload, AddBidDto.class);
			UpdatePriceBidDto updatePriceBidDto = bidService.addBid(addBidDto);
			webSocketService.sendToAllClient(addBidDto.getItemId(), updatePriceBidDto);
		} catch (JsonProcessingException e) {
			log.error("Error addBid", e);
		}
	}

	private void selectBid(String payload) {
		try {
			JsonNode jsonNode = objectMapper.readTree(payload);
			Long itemId = jsonNode.get("itemId").asLong();
			String userId = jsonNode.get("userId").asText();

			if(userId == null){
				return;
			}

			webSocketService.sendResultAllClient(itemId, userId);
		} catch (JsonProcessingException e) {
			log.error("Error selectBid", e);
		}
	}

	private void closeConnection(String payload) {
		try {
			ExitMemberDto exitMemberDto = objectMapper.readValue(payload, ExitMemberDto.class);
			webSocketService.closeSessionByUserId(exitMemberDto.getItemId(), exitMemberDto.getUserId());
		} catch (JsonProcessingException e) {
			log.error("Error closeConnection", e);
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
