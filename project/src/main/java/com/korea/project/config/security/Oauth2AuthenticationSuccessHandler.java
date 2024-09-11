package com.korea.project.config.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.korea.project.vo.user.UserVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class Oauth2AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler  {

	  private final HttpSession httpSession;

	    @Override
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
	        if (authentication.isAuthenticated()) {
	            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

	            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	            // 기타 필요한 정보들을 설정
	            
	            log.info("userDetails" + userDetails);
	            httpSession.setAttribute("user", userDetails);
	        }

	        super.onAuthenticationSuccess(request, response, authentication);
	    }
}
