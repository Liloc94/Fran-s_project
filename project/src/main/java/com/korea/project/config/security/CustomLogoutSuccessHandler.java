package com.korea.project.config.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutSuccessHandler extends HttpStatusReturningLogoutSuccessHandler{
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		response.sendRedirect("/user/index"); // 로그아웃 성공 시 "/user/index"로 리다이렉트
        super.onLogoutSuccess(request, response, authentication);
	}
}
