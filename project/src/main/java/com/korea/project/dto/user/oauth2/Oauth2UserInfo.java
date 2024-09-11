package com.korea.project.dto.user.oauth2;

public interface Oauth2UserInfo {
	String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
    String getNickname();
}
