package hobbiedo.user.auth.user.dto.response;

import lombok.Getter;

@Getter
public class LoginResponseDTO {
	private final String uuid;
	private final String password;

	public LoginResponseDTO(String uuid, String password) {
		this.uuid = uuid;
		this.password = password;
	}
}
