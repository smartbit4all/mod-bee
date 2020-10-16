package org.smartbit4all.businessevent.ui.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	public class CustomFilter implements Filter {

		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
			// DO NOTHING
		}

	}

	private static final String LOGIN_PROCESSING_URL = "/login";
	private static final String LOGIN_FAILURE_URL = "/login?error";
	private static final String LOGIN_URL = "/login";
	private static final String LOGOUT_SUCCESS_URL = "/login";

	@Autowired
	private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private SystemAuthenticationProvider authenticationProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().requestCache().requestCache(new CustomRequestCache()).and().authorizeRequests()
				.requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

				.anyRequest().authenticated()

				.and().formLogin().loginPage(LOGIN_URL).permitAll().loginProcessingUrl(LOGIN_PROCESSING_URL)
				.failureUrl(LOGIN_FAILURE_URL).and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//          .antMatchers("/securityNone").permitAll()
//          .anyRequest().authenticated()
//          .and()
//          .httpBasic()
//          .authenticationEntryPoint(authenticationEntryPoint);
// 
//        http.addFilterAfter(new CustomFilter(),
//          BasicAuthenticationFilter.class);
//    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//    	UserDetails user =
//                User.withUsername("user")
//                        .password("{noop}password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//    	BorderSystemUser user = 
//                User.withUsername("user")
//                        .password("{noop}password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/VAADIN/**", "/favicon.ico", "/robots.txt", "/manifest.webmanifest", "/sw.js",
				"/offline.html", "/icons/**", "/images/**", "/styles/**", "/frontend/**", "/h2-console/**",
				"/frontend-es5/**", "/frontend-es6/**");
	}

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//          .withUser("user1").password(passwordEncoder().encode("user1Pass"))
//          .authorities("ROLE_USER");
//    }

}