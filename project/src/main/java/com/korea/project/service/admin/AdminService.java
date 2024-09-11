package com.korea.project.service.admin;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.korea.project.dao.user.UserDAO;
import com.korea.project.dto.user.UserDetail;
import com.korea.project.vo.user.UserVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService implements UserDetailsService {

    private final UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserVO user = userDAO.selectById(userId);
        if (user == null || user.getUserRole() != 1) {
            throw new UsernameNotFoundException("Admin not found with userId: " + userId);
        }
        user.setRoles("ROLE_ADMIN");
        return new UserDetail(user);
    }
}