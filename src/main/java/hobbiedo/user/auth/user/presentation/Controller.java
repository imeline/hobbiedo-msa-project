package hobbiedo.user.auth.user.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import hobbiedo.user.auth.user.application.CustomUserDetailService;
import hobbiedo.user.auth.user.vo.request.LoginRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {
	private final CustomUserDetailService customUserDetailService;

	@PostMapping("/login")
	public void loginApi(@RequestBody LoginRequestVO loginVO) {
	}
}
