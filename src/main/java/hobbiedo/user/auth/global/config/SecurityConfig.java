package hobbiedo.user.auth.global.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import hobbiedo.user.auth.global.config.jwt.JwtUtil;
import hobbiedo.user.auth.global.config.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	@Value("${hobbie-do.front-url}")
	private static String FRONT_URL;

	private final AuthenticationConfiguration authenticationConfiguration;
	private final JwtUtil jwtUtil;

	private static CorsConfigurationSource getCorsConfigurationSource() {
		return request -> {
			CorsConfiguration config = new CorsConfiguration();

			config.setAllowCredentials(true);
			config.setAllowedMethods(List.of("GET", "POST"));
			config.setAllowedHeaders(Collections.singletonList("*"));
			config.setAllowedOrigins(Collections.singletonList(FRONT_URL));
			config.setMaxAge(3600L);
			config.setExposedHeaders(Collections.singletonList("Authorization"));

			return config;
		};
	}

	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/login/**", "/sign-up/**").permitAll()
						.requestMatchers(
								"/swagger-ui/**",
								"/swagger-resources/**",
								"/api-docs/**").permitAll()
						.anyRequest().authenticated())

				/* JWT 토큰 방식을 위해 Session 방식 차단 */
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				/* 만들어둔 커스텀 필터 등록 */
				.addFilterAt(
						new LoginFilter(authenticationManager(), jwtUtil),
						UsernamePasswordAuthenticationFilter.class)

				/* CORS 설정 */
				.cors(cors ->
						cors.configurationSource(getCorsConfigurationSource()));

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
