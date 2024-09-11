package com.korea.project.service.admin;

import com.korea.project.mapper.admin.AdminUserMapper;
import com.korea.project.vo.user.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserMapper adminUserMapper;

    @Override
    public List<UserVO> selectUserByPage(int offset, int limit) {
        try {
            return adminUserMapper.selectUserByPage(offset, limit); // Call the method from AdminUserMapper
        } catch (DataAccessException e) {
            System.err.println("Failed to retrieve users: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public int countAllUsers() {
        return adminUserMapper.countAllUsers();
    }
    
    @Override
    @Transactional
    public boolean deleteUser(int userIdx) {
        int rowsAffected = adminUserMapper.updateUser(userIdx);
        return rowsAffected > 0;
    }

	@Override
	public List<UserVO> selectUserByName(String name, int offset, int limit) {
		try {
            return adminUserMapper.selectUserByName(name,offset, limit);
        } catch (DataAccessException e) {
            System.err.println("Failed to retrieve user by name: " + e.getMessage());
            throw e;
        }	}

	@Override
	public int countUserByName(String name) {
        return adminUserMapper.countUserByName(name);
	}
}
