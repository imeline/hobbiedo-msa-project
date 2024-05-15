package hobbiedo.user.auth.global.api.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hobbiedo.user.auth.global.api.ApiResponse;
import hobbiedo.user.auth.global.api.code.status.ErrorStatus;
import hobbiedo.user.auth.global.api.exception.handler.ExampleHandler;
import hobbiedo.user.auth.global.api.sample.domain.Example;
import hobbiedo.user.auth.global.api.sample.dto.response.ExampleResponseDTO;
import hobbiedo.user.auth.global.api.sample.repository.ExampleRepository;
import lombok.RequiredArgsConstructor;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class ExampleController {
	private final ExampleRepository repository;

	@GetMapping("/example1")
	public ApiResponse<ExampleResponseDTO> testAPI1(@RequestParam(name = "flag") Integer flag) {
		System.out.println("flag = " + flag);
		if (flag == -1) {
			throw new ExampleHandler(ErrorStatus.EXAMPLE_EXCEPTION);
		}
		return ApiResponse.onSuccess(ExampleResponseDTO
				.builder()
				.flag(flag)
				.build());
	}

	@PostMapping("/example2")
	public ApiResponse<Void> testAPI2(@RequestParam(value = "value") String value) {
		repository.save(new Example(value));
		return ApiResponse.onSuccess(null);
	}
}
