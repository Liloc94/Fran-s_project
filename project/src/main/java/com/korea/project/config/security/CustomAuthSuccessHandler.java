package com.korea.project.config.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.korea.project.dto.user.SessionUserDTO;
import com.korea.project.service.user.UserDetailServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserDetailServiceImpl userService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        HttpSession session = request.getSession();
        String userId = authentication.getName();
        SessionUserDTO dto = userService.selectNicknameById(userId);
        
        session.setAttribute("user", dto);
        session.setMaxInactiveInterval(60*30);
        
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            // 관리자인 경우 /admin/user/list로 리다이렉트
            getRedirectStrategy().sendRedirect(request, response, "/admin/user/list");
        } else {
            // 일반 사용자인 경우
            if (dto.getUserNickname() == null || dto.getUserNickname().isEmpty()) {
                getRedirectStrategy().sendRedirect(request, response, "/user/set-nickname");
            } else {
                getRedirectStrategy().sendRedirect(request, response, "/");
            }
        }
    }
}