package com.korea.project.dao.user;

import org.springframework.stereotype.Repository;

import com.korea.project.dto.user.FindRequestDTO;
import com.korea.project.dto.user.FindResponseDTO;
import com.korea.project.dto.user.RegisterRequestDTO;
import com.korea.project.dto.user.ResetPasswordRequestDTO;
import com.korea.project.dto.user.SessionUserDTO;
import com.korea.project.dto.user.oauth2.Oauth2UserInfo;
import com.korea.project.mapper.user.UserMapper;
import com.korea.project.vo.user.UserVO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserDAO implements UserMapper{
	private final UserMapper userMapper;
	
	// 아이디 비번을 가지고 로그인 하기
	@Override
	public UserVO selectById(String userId) {
		return userMapper.selectById(userId);
	}
	
	// 사이트 자체 회원가입
	@Override
	public void signUp(UserVO vo) {
		//System.out.println("회언가입 DAO");
		userMapper.signUp(vo);
	}
	
	// 세션에 유저 이름과 아이디를 넣기 위한 조회
	@Override
	public SessionUserDTO selectNicknameById(String id) {
		return userMapper.selectNicknameById(id);
	}
	
	// 아이디 중복검사
	@Override
	public int checkDuplicateById(String id) {
		return userMapper.checkDuplicateById(id);
	}
	
	// 이메일 중복검사
	@Override
	public int checkDuplicateByEmail(String email) {
		return userMapper.checkDuplicateByEmail(email);
	}
	
	// 닉네임 중복검사
	@Override
	public int checkDuplicateByNickname(String nickname) {
		return userMapper.checkDuplicateByNickname(nickname);
	}
	
	// 찾기
	@Override
	public String find(FindRequestDTO dto) {
		return userMapper.find(dto);
	}
	
	/**
	 * 비밀번호 변경
	 * @param ResetPasswordRequestDTO dto
	 */
	@Override
	public void updatePwd(ResetPasswordRequestDTO dto) {
		userMapper.updatePwd(dto);
	}
	
	/**
	 * 세션 정보로 유저 정보찾기
	 * @param SessionUserDTO
	 */
	@Override
	public UserVO selectBySession(SessionUserDTO dto) {
		return userMapper.selectBySession(dto);
	}
	
	/**
	 * 탈퇴
	 * @param SessionUserDTO
	 */
	@Override
	public void updateUserDel(SessionUserDTO dto) {
		userMapper.updateUserDel(dto);
	}
	
	/**
	 * 소셜미디어 로그인 정보를 확인하기 위한 login_id로 찾기
	 * @param String loginId
	 * @return UserVO
	 */
	@Override
	public UserVO findByOAuth2UserInfo(Oauth2UserInfo oAuth2UserInfo) {
		return userMapper.findByOAuth2UserInfo(oAuth2UserInfo);
	}
	
	/**
	 * 닉네임 등록(변경)
	 * @parma SessionUserDTO
	 */
	@Override
	public void resetNickname(SessionUserDTO sessionUserDTO) {
		userMapper.resetNickname(sessionUserDTO);
	}
	
 	

}
