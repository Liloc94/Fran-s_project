package com.korea.project.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.korea.project.service.user.CustomOAuth2UserService;
import com.korea.project.service.user.UserDetailServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
//	private UserDe
	private final UserDetailServiceImpl userDetailServiceImpl;
	private final CustomAuthFailureHandler customFailureHandler;
	private final CustomAuthSuccessHandler customSuccessHandler;
	private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
	private final CustomAccessDined customAccessDined;
	private final CustomOAuth2UserService customOAuth2UserService;
	private final Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler; 
	private final AdminAuthSuccessHandler adminSuccessHandler;
	private final AdminAuthFailureHandler adminFailureHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .userDetailsService(userDetailServiceImpl)
                   .passwordEncoder(passwordEncoder())
                   .and()
                   .build();
    }
    
    @Bean
	public RememberMeServices rememberMeServices() {
		TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("uniqueAndSecret", userDetailServiceImpl);
		rememberMeServices.setParameter("rememberMe");
		rememberMeServices.setAlwaysRemember(true); // 항상 자동 로그인 유지
		rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7); // 7일 유효 기간 설정
		return rememberMeServices;
	}
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailServiceImpl);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    

    @Bean
    public Oauth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new Oauth2AuthenticationFailureHandler();
    }
    
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
    	http
    	.csrf(AbstractHttpConfigurer::disable)
    	.sessionManagement(session -> session
                .sessionFixation().changeSessionId()
            )
//        .sessionManagement(sessionManagement ->
//                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        )
        .authorizeHttpRequests(authorize -> authorize
        		.requestMatchers("/css/**", "/js/**", "/categoryImages/**", "/data/**", "/img/**").permitAll() // CSS, JS, 이미지 폴더에 대해 접근 허용
        		.requestMatchers("/user/*").hasAnyRole("USER", "ADMIN")
                // /user : 인증만 되면 들어갈 수 있는 주소
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                //ADMIN을 가진 사용자만 /admin 접근 허용
                .anyRequest().permitAll()//다른 요청
        )
        .formLogin(formLogin -> formLogin
                .loginPage("/perform_login") // 관리자 로그인 페이지 URL
                .loginProcessingUrl("/admin-perform-login") // 관리자 로그인 form action URL
                .usernameParameter("userId") // 로그인 form의 userId 파라미터 이름
                .passwordParameter("userPwd") // 로그인 form의 userPwd 파라미터 이름
                .successHandler(adminSuccessHandler)
                .failureHandler(adminFailureHandler)
                .permitAll()
        )
        .formLogin(formLogin -> formLogin
                .loginPage("/login") // 커스텀 로그인 페이지 URL
                .loginProcessingUrl("/perform_login") // 로그인 form action URL
//                .defaultSuccessUrl("/") // 로그인 성공 시 리디렉션 URL successHandler로 대체
//                .failureUrl("/") // 로그인 실패 시 리디렉션 URL
                .usernameParameter("userId") // 로그인 form의 userId 파라미터 이름
                .passwordParameter("userPwd") // 로그인 form의 userPwd 파라미터 이름
                .successHandler(customSuccessHandler)
                .failureHandler(customFailureHandler)
                .permitAll()
        )
        .oauth2Login((auth) -> auth.loginPage("/oauth-login/login")
        		.userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                        .userService(customOAuth2UserService))
        		.successHandler(customSuccessHandler)
                .failureUrl("/login")
                .permitAll())
        .logout(logout -> logout
                .logoutUrl("/logout") // 로그아웃 처리 URL
                .logoutSuccessHandler(customLogoutSuccessHandler) // 로그아웃 성공 시 핸들러 설정
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID") // 로그아웃 후 삭제할 쿠키 이름 설정
                .deleteCookies("remember-me")
                .permitAll()
        ).rememberMe(remember -> remember
        		.rememberMeServices(rememberMeServices())
        		 .key("uniqueAndSecret")
                 .tokenValiditySeconds(60 * 60 * 24 * 7) // 7 days
                 .rememberMeParameter("rememberMe")
        )
        .exceptionHandling(exception-> exception
                    .accessDeniedHandler(customAccessDined)
                        
                        
        )
        ;

    	return http.build();
    }
    

    
    


    
}