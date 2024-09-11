package com.korea.project.service.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.korea.project.dao.user.UserDAO;
import com.korea.project.dto.user.UserDetail;
import com.korea.project.dto.user.oauth2.GoogleUserDetails;
import com.korea.project.dto.user.oauth2.NaverUserDetails;
import com.korea.project.dto.user.oauth2.Oauth2UserInfo;
import com.korea.project.vo.user.UserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService{
	
	private final UserDAO userDAO;

	 	@Override
	    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
	        OAuth2User oAuth2User = super.loadUser(userRequest);
//	        log.info("getAttributes : {}",oAuth2User.getAttributes());

	        // 어느 소셜미디에어세 접근했는지 
	        String provider = userRequest.getClientRegistration().getRegistrationId();

	        // 빈 유저 정보 만들기
	        // 소셜미디어의 정보를 담아놓기 위해
	        Oauth2UserInfo oAuth2UserInfo = null;

	        // 뒤에 진행할 다른 소셜 서비스 로그인을 위해 구분 => 구글
	        if(provider.equals("naver")){
//	            log.info("네이버로그인");
	            oAuth2UserInfo = new NaverUserDetails(oAuth2User.getAttributes());
	        }else if(provider.equals("google")) {
	        	oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());
	        }
	        


	        String providerId = oAuth2UserInfo.getProviderId();
//	        log.info("providerId : " + providerId);
	        
	        String email = oAuth2UserInfo.getEmail();
//	        log.info("로그인 이메일 : " + email);
	        String loginId = provider + "_" + providerId;
//	        log.info("login_id : " + loginId);
	        String name = oAuth2UserInfo.getName();
//	        log.info("name : " + name);
	        
	        
	        // 유저정보 불러오기
	        UserVO findUser = userDAO.findByOAuth2UserInfo(oAuth2UserInfo);
	        log.info("로그인 유저 정보" + findUser);

	        
	        UserVO user = new UserVO();
	        // 디비에서 유저정보가 없으면 회원가입 시키기
	        if (findUser == null) {
	        	
	        	user.setProvider(provider);
	        	user.setProviderId(providerId);
	        	user.setUserName(name);
	        	user.setUserId(loginId);
	        	user.setUserNickname(oAuth2UserInfo.getNickname());
	        	user.setUserEmail(email);
	        	// 소셜미디어에서 넘어온 별명이 있는지 확인
	        	int checkNickname = userDAO.checkDuplicateByNickname(oAuth2UserInfo.getNickname());
	        	
	        	// 있으면 일단 빼놓고 회원가입을 시킨 후 닉네임 설정할 수 있는 페이지로 이동 시켜야함
		        if(checkNickname == 1) {
		        	user.setUserNickname(null);
		        }
		        
		        userDAO.signUp(user);

	    		
		        
	        } else{
	            user = findUser;
	        	if(user.getUserRole() == 1) {
	    			user.setRoles("ROLE_ADMIN");
	    		}else {
	    			user.setRoles("ROLE_USER");
	    		}
	    		
	        }
	        
	        if(user.getUserRole() == 1) {
    			user.setRoles("ROLE_ADMIN");
    		}else {
    			user.setRoles("ROLE_USER");
    		}
	        log.info("룰 : " + user.getRoles());

	        return new UserDetail(user, oAuth2User.getAttributes());
	    }
}
