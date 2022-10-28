package eu.rutolo.recetario.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import eu.rutolo.recetario.Constants;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.userDetailsService(userDetailsService)
			.authorizeRequests(auth -> auth
				.antMatchers("/", "/login", "/logout").permitAll()
				.antMatchers("/admin**").hasAuthority(Constants.ROL_ADMIN)
				.antMatchers("/ingrediente**").hasAuthority(Constants.ROL_ADMIN)
				.anyRequest().authenticated()
			).formLogin(form -> form.permitAll()
			).logout(logout -> logout.permitAll()
			).build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
