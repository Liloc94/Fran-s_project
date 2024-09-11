package com.korea.project.dto.user.oauth2;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NaverUserDetails implements Oauth2UserInfo{

	 private Map<String, Object> attributes;
	 private String userId;
	 
    public NaverUserDetails(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.userId = (String) ((Map) attributes.get("response")).get("id"); // userId를 초기화
    }

	
    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return (String) ((Map) attributes.get("response")).get("id");
    }

    @Override
    public String getEmail() {
        return (String) ((Map) attributes.get("response")).get("email");
    }

    @Override
    public String getName() {
        return (String) ((Map) attributes.get("response")).get("name");
    }
    
    @Override
    public String getNickname() {
    	 return (String) ((Map) attributes.get("response")).get("nickname");
    }
}
