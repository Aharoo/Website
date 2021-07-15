package com.aharoo.security;

import com.aharoo.auth.ApplicationUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


import javax.sql.DataSource;

import static com.aharoo.security.ApplicationUserPermission.*;
import static com.aharoo.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService userService;
	private final DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
					.antMatchers("/","/index","/css/**","/js/**","/images/**","/templatemo_style.css",
						"/registration/**","/confirm/**","/recover/**").permitAll()
					.antMatchers("/anime/**","/user-profile/**").hasAnyRole(USER.name(), ADMIN.name())
					.antMatchers("/admin/**").hasRole(ADMIN.name())
					.anyRequest()
					.authenticated()
					.and()
				.formLogin()
					.loginPage("/login")
					.permitAll()
					.defaultSuccessUrl("/")
					.usernameParameter("username")
					.passwordParameter("password")
					.and()
				.rememberMe()
					//.tokenRepository(persistentTokenRepository())
					//.key("rem-me-key")
					.rememberMeParameter("remember-me")
				//	.tokenValiditySeconds(10)
					.and()
				.logout()
					.logoutUrl("/logout")
					.clearAuthentication(true)
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID","remember-me")
					.logoutSuccessUrl("/login");

		http
				.sessionManagement().invalidSessionUrl("/login")
				.maximumSessions(1)
				.maxSessionsPreventsLogin(false)
				.expiredUrl("/index?invalid-session=true")
				.and()
				.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider(){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(userService);
		return provider;
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository(){
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

}
