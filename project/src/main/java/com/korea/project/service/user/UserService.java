package com.korea.project.service.user;

import org.springframework.stereotype.Service;

import com.korea.project.dto.board.BoardListRequest;
import com.korea.project.dto.board.BoardResponse;
import com.korea.project.dto.board.PagingResponse;
import com.korea.project.dto.user.FindRequestDTO;
import com.korea.project.dto.user.RegisterRequestDTO;
import com.korea.project.dto.user.ResetPasswordRequestDTO;
import com.korea.project.dto.user.SessionUserDTO;
import com.korea.project.vo.user.UserVO;

@Service
public interface UserService {

	
	// 회원가입
	public void register(RegisterRequestDTO vo);
	// 세션에 유저 이름과 유저 닉네임을 저장하기 위한 조회
	public SessionUserDTO selectNicknameById(String id);
	
	// 아이디 중복체크
	public int checkDuplicateById(String id);
	
	// 이메일 중복체크
	public int checkDuplicateByEmail(String email);
	
	// 닉네임 중복체크
	public int checkDuplicateByNickname(String nickname);
	
	// 찾기
	public String find(FindRequestDTO dto);
	
	// 아이디로 찾기
	public UserVO selectById(String id);
	
	/**
	 * 비밀번호 변경
	 * @param ResetPasswordRequestDTO dto
	 */
	public void resetPwd(ResetPasswordRequestDTO dto);
	
	// 세션에 저장되어 있는 정보를 가지고 비밀번호 검사
	public String checkPwd(String pwd, SessionUserDTO user);

	/**
	 * 세션 정보로 유저 찾기
	 * @param SessionUserDTO
	 * @return userVO
	 */
	
	public UserVO selectBySession(SessionUserDTO user);
	
	/**
	 * 비밀번호 변경 서비스에서 하는것으로 새로 작성
	 * @param SessionUserDTO, ResetPasswordRequestDTO
	 * @return String message
	 */
	public String updatePwd(SessionUserDTO user, ResetPasswordRequestDTO dto);
	
	/**
	 * 탈퇴
	 * @param SessionUserDTo
	 * @return String result
	 */
	public String withdraw(SessionUserDTO user);
	
	/**
	 * 마이페이지 게시글 목록
	 * @params BoardListRequest, SessionUserDTO
	 * @return PaginResponse<BoardVO>
	 */
	
	public PagingResponse<BoardResponse> myPost(BoardListRequest params, SessionUserDTO user);
	
	/**
	 * 닉네임 등록(변경)
	 * @param String nickname, SessionUserDTO sessionUserDTO
	 */
	public void resetNickname(String nickname, SessionUserDTO sessionUserDTO);
	
	/**
	 * 인증번호 보내기
	 * @param userEmail
	 */
	boolean sendCertificationEmail(String userEmail);
	
	/**
	 * 이메일 인증검사
	 * @param string email, 랜덤변수
	 */
	boolean verifyEmail(String userEmail, String certificationNumber);
}
