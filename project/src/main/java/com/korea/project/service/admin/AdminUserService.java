package com.korea.project.service.admin;

import com.korea.project.vo.franchise.FranchiseVO;
import com.korea.project.vo.user.UserVO;

import java.util.List;

public interface AdminUserService {
	//유저삭제
    boolean deleteUser(int userIdx);
    // 페이징 관련
    int countAllUsers();
    List<UserVO> selectUserByName(String name,int offset, int limit);
	List<UserVO> selectUserByPage(int offset, int limit);
    int countUserByName(String name);
}
