package com.korea.project.dto.user.oauth2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.korea.project.vo.user.UserVO;

public class Oauth2UserDetails implements OAuth2User, UserDetails{
	
	private final UserVO userVO;
	private Map<String, Object> attributes;

    public Oauth2UserDetails(UserVO userVO, Map<String, Object> attributes) {

        this.userVO = userVO;
        this.attributes = attributes;
    }



    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
            	if(userVO.getUserRole() == 0) {
					return "ROLE_USER";
				}else {
					return "ROLE_ADMIN";
				}
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return userVO.getUserPwd();
    }

    @Override
    public String getUsername() {
        return userVO.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

	    
}
