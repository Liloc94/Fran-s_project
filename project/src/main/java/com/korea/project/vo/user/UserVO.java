package com.korea.project.vo.user;

import lombok.Data;

@Data
public class UserVO {
	private String userId, userNickname, userPwd, userName, userEmail, regdate, roles, provider, providerId;
	private int userIdx, userRole, userDel;
}
