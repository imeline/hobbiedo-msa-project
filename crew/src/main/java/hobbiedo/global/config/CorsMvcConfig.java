package hobbiedo.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsMvcConfig implements WebMvcConfigurer {
	@Value("${hobbie-do.front-url}")
	private static String FRONT_URL;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// 게이트웨이 충돌로 인한 주석 처리
		// registry.addMapping("/**").allowedOrigins(FRONT_URL);
	}
}
