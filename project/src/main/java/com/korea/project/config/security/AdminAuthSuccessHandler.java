package com.korea.project.config.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AdminAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        // 관리자 권한 확인
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            // 관리자인 경우 /admin/user/list로 리다이렉트
            getRedirectStrategy().sendRedirect(request, response, "/admin/user/list");
        } else {
            // 관리자가 아닌 경우 기본 페이지로 리다이렉트 (예: 홈페이지)
            getRedirectStrategy().sendRedirect(request, response, "/");
        }
    }
}