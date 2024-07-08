package hobbiedo.global.api.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorReasonDto {
	private String code;
	private HttpStatus httpStatus;
	private String message;
}
