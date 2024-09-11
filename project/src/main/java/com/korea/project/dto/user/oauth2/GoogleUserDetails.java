package com.korea.project.dto.user.oauth2;

import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GoogleUserDetails implements Oauth2UserInfo{

		
	   	private Map<String, Object> attributes;
	   	
	   	
	   	@Override
	   	public String getNickname() {
		   
		return (String) attributes.get("nickname");
	   	}	
	   
	    @Override
	    public String getProvider() {
	        return "google";
	    }

	    @Override
	    public String getProviderId() {
	        return (String) attributes.get("sub");
	    }

	    @Override
	    public String getEmail() {
	        return (String) attributes.get("email");
	    }

	    @Override
	    public String getName() {
	        return (String) attributes.get("name");
	    }
}
