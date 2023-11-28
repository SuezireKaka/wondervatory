package www.wonder.vatory.security.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
public class DreamSecurityConfiguration implements WebMvcConfigurer {
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private CustomAuthenticationEntryPoint point;
	
	@Autowired
	private CustomAccessDeniedHandler denyHandler;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
		// configuration
		http.httpBasic().disable()
				.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/*/anonymous/**").permitAll())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .exceptionHandling(ex -> ex.accessDeniedHandler(denyHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), RequestCacheAwareFilter.class);
		//.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		//configuration.setAllowedOrigins(List.of("http://localhost:3000"));
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000","https://nasms018.github.io/wv_front2","https://nasms018.github.io", "http://ec2-52-78-208-249.ap-northeast-2.compute.amazonaws.com:3000", "http://52.78.208.249:3000", "http://52.78.208.249:8080"));// 주소변경 https://nasms018.github.io  // http://localhost:3000
		configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(
				List.of("Content-Type", "x-auth-token", "Access-Control-Allow-Origin", "Cache-control", "X-PINGOTHER"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
	/*
	 @Override
	    public void addCorsMappings(final CorsRegistry registry) {
	       registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("Authorization", "Content-Type")
        .exposedHeaders("Custom-Header")
        .allowCredentials(true)
        .maxAge(3600);

	    }
*/
}
