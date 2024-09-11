package project.pickme.bid.webSocket;

import java.net.URL;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebSocketHandShakeInterceptor implements HandshakeInterceptor {
	private static final int URL_LENGTH = 4;

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
		Map<String, Object> attributes) throws Exception {

		String[] parts = request.getURI().getPath().split("/");

		if (parts.length != URL_LENGTH) {
			log.error("잘못된 웹소켓 연결 요청" + request.getURI().getPath());
			response.setStatusCode(HttpStatus.FORBIDDEN);
			response.getBody().write("웹소켓 연결 실패".getBytes());
			return false;
		}

		attributes.put("itemId", Long.parseLong(parts[2]));
		attributes.put("userId", parts[3]);

		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
		Exception exception) {
		log.info("handshake success!");
	}
}
