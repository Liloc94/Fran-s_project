package com.korea.project.dto.user;


import lombok.Data;

@Data
public class RegisterRequestDTO {
	private String userId;
	private String userNickname;
	private String userPwd;
	private String checkPwd;
	private String userName;
	private String userEmail;
	private String localEmail;
	private String domainEmail;
}
