package hobbiedo.gateway.filter;


import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hobbiedo.gateway.global.ApiResponse;
import hobbiedo.gateway.global.code.status.ErrorStatus;
import hobbiedo.gateway.global.exception.handler.ExampleHandler;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class JwtTokenFilter extends AbstractGatewayFilterFactory<JwtTokenFilter.Config> {

	private final Environment env;

	public JwtTokenFilter(Environment env) {
		super(Config.class);
		this.env = env;
	}
	@Value("${jwt.secret}")
	private String secret;

	public static class Config {
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {

			ServerHttpRequest request = exchange.getRequest();

			if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED,
						new ExampleHandler(ErrorStatus.NOT_FOUND_TOKEN));
			} // 헤더에 토큰이 있는지 없는지 확인하고 없으면 에러를 발생시킨다.

			String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

			String jwt = authorizationHeader.replace("Bearer", ""); // 헤더에서 Bearer를 제거하고 토큰만 가져온다.

			if (isJwtValid(jwt).equals("402")) {

				return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED,
						new ExampleHandler(ErrorStatus.INVALID_TOKEN)); // 토큰이 유효한지 확인하고 유효하지 않으면 에러를 발생시킨다.

			} else if (isJwtValid(jwt).equals("403")) {

				return onError(exchange, "JWT token is expired", HttpStatus.UNAUTHORIZED,
						new ExampleHandler(ErrorStatus.EXPIRED_TOKEN)); // 토큰의 만료 여부를 확인하고 만료되었으면 에러를 발생시킨다.

			}

			return chain.filter(exchange);
		};
	}

	private String isJwtValid(String jwt) {

		String returnValue = "";

		String subject = null;

		try {
			subject = Jwts
					.parserBuilder() // 토큰을 파싱한다.
					.setSigningKey(secret)
					.build()
					.parseClaimsJws(jwt)
					.getBody()
					.getSubject();
		} catch (MalformedJwtException ex) {
			log.error("Error while parsing JWT: ", ex);
			returnValue = "402";
		} catch (SignatureException ex) {
			log.error("Error while parsing JWT: ", ex);
			returnValue = "402";
		} catch (ExpiredJwtException ex) {
			log.error("Error while parsing JWT: ", ex);
			returnValue = "403";
		}

		return returnValue;
	}

	// Mono 타입은 비동기로 작업을 처리할 때 사용하는 리액티브 타입
	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus, ExampleHandler handler) {

		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);

		// ApiResponse 객체 생성
		ApiResponse<String> apiResponse = ApiResponse.onFailure(
				handler.getErrorCode().getReasonHttpStatus().getCode(),
				handler.getErrorCode().getReasonHttpStatus().getMessage(),
				null);

		try {
			// ApiResponse 객체를 JSON 문자열로 변환
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(apiResponse);

			// JSON 문자열을 바이트 배열로 변환
			byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

			// 바이트 배열을 DataBuffer로 변환
			DataBuffer buffer = response.bufferFactory().wrap(bytes);

			// HTTP 응답에 에러 메시지 설정
			response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

			log.error(err);

			return response.writeWith(Mono.just(buffer));

		} catch (JsonProcessingException e) { // JSON 변환 중 에러 발생 시

			log.error("Error while converting ApiResponse to JSON", e); // 에러 로그 출력

			return Mono.error(e); // 에러 반환
		}
	}
}
