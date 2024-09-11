package com.korea.project.controller.user;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.korea.project.dto.user.FindRequestDTO;
import com.korea.project.dto.user.RegisterRequestDTO;
import com.korea.project.dto.user.ResetPasswordRequestDTO;
import com.korea.project.dto.user.SessionUserDTO;
import com.korea.project.service.user.UserServiceImpl;
import com.korea.project.vo.user.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserAuthController {
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserServiceImpl userService;
	private final HttpSession session;
	

	// 회원가입 페이지에서 회원가입 버튼을 눌렀을 때 작동하는 컨트롤러
    @PostMapping("/register")
    public HashMap<String, String> postRegister(RegisterRequestDTO vo) throws NoSuchAlgorithmException {
        HashMap<String, String> map = new HashMap<>();	
        vo.setUserEmail(vo.getLocalEmail()+vo.getDomainEmail());
        String reKey = "result";
        String meKey = "message";
        //System.out.println(vo.getUserPwd());
        
        // 유효성 검사
        if (vo.getUserId() == null || vo.getUserId().isEmpty()) {
            map.put(reKey, "empthIdError");
            map.put("message", "아이디를 입력해 주세요.");
            return map;
        }
        if(!isValid("id", vo.getUserId())) {
        	map.put(reKey, "idInvalidError");
        	map.put(meKey, "아이디는 한글 2자~6자 또는 영어 소문자 4자~12자입니다.");
        }
        if (vo.getUserEmail() == null || !isValid("email",vo.getUserEmail())) {
            map.put(reKey, "EmailInvalidError");
            map.put(meKey, "유효한 이메일을 입력해 주세요.");
            return map;
        }
        if (vo.getUserPwd() == null || vo.getUserPwd().isEmpty()) {
            map.put(reKey, "emtpyPwdError");
            map.put(meKey, "비밀번호를 입력해주세요.");
            return map;
        }
        if(!vo.getCheckPwd().equals(vo.getUserPwd())) {
        	map.put(reKey, "mismatchPwdError");
        	map.put(meKey, "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        	return map;
        }
        if(!isValid("pwd", vo.getUserPwd()) || !isValid("pwd", vo.getCheckPwd())) {
        	map.put(reKey, "pwdInvalidError");
        	map.put(meKey, "비밀번호는 8자~ 20자의 영어 소문자와 숫자의 조합만 가능합니다.");
        	return map;
        }
        if (vo.getUserNickname() == null || vo.getUserNickname().isEmpty()) {
            map.put(reKey, "emptyNicknameError");
            map.put(meKey, "닉네임을 입력해 주세요.");
            return map;
        }
        if(!isValid("nickname", vo.getUserNickname())) {
        	map.put(reKey, "nicknameInvalidError");
        	map.put(meKey, "이름은 2자~6자의 한글만 가능합니다.");
        	return map;
        }
        

        // 비밀번호 암호화
 	   String rawPassword = vo.getUserPwd();
 	   String encPassword = bCryptPasswordEncoder.encode(rawPassword);
	   vo.setUserPwd(encPassword);

	   
        // 회원가입 처리
        //System.out.println("서비스 시작전");
        userService.register(vo);
        map.put("result", "success");
        //System.out.println("서비스 이후");
        return map;
    }
	
	/**
	 * @author mod459
	 * @param type(id, email, nickname)
	 * @param value(real input value)
	 * @return map<"isDuplicate", boolean>
	 */
	@PostMapping("/api/check-duplicate")
	public HashMap<String, Boolean> checkDuplicate( String type, String value) {
//		log.info("중복검사 실행");
		HashMap<String, Boolean> map = new HashMap<>();	
		String key = "isDuplicate";
		// true = 중복되었다
		// false = 중복되어있지 않다
		// 1보다 작다 = 0이다 = 중복되어 있지않다
		Boolean isDuplicate = true;
		if(type.equals("id")) {
			if(userService.checkDuplicateById(value) < 1 ) {
				// 중복되지 않음
				map.put(key, isDuplicate); 
			}else {
				// 중복됨
				map.put(key, !isDuplicate); 
			}
			return map;
		}else if(type.equals("email")) {
//			System.out.println(value);
			
			if(userService.checkDuplicateByEmail(value) < 1) {
				map.put(key, isDuplicate);
			}else {
				map.put(key, !isDuplicate);
			}
			return map;
		}else if(type.equals("nickname")) {
			if(userService.checkDuplicateByNickname(value) < 1) {
				map.put(key, isDuplicate);
			}else {
				map.put(key, !isDuplicate);
			}
//			log.info("결과 :" + isDuplicate);
			return map;
		}else {
			if(userService.checkDuplicateById(value) < 1) {
				map.put(key, isDuplicate);
			}else {
				map.put(key, !isDuplicate);
			}
			return map;
		}
	}
	
	// 유효성 검사
    private boolean isValid(String type, String value) {
        String regex = ""; 
        switch(type) {
        case "id":
        	regex = "^(?!.*\\\\s)(?!.*admin)[a-z0-9]{5,20}$";
        	break;
        case "email":
        	regex =  "^(?!.*\\\\s)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        	break;
        case "nickname":
        	regex = "^(?!.*\\\\s)(?:(?:[가-힣]{2,6})|(?:[a-z]{4,12}))(?!.*\\b(?:관리자|운영자|admin)\\b).*$";
        	break;
        case "pwd":
        	regex = "^(?!.*\\\\s)(?=.*[a-z])(?=.*\\d)[a-z\\d]{8,20}$";
        	break;
        case "name":
        	regex = "^(?!.*\\\\s)[가-힣]{2,6}$";
        	break;
        }
       
        return value.matches(regex);
    }
    
    // 찾기
    @PostMapping("/find")
    /**
     * @param dto.type == "id" -> req1 = name, req2 = eamil
     * @param dto.type.equals("pwd") -> req1 = id, req2=email
     * @return
     */
    public Map<String, String> find(FindRequestDTO dto, Model model){
//    	System.out.println("dto :" + dto);
		String userId = userService.find(dto);
		String message = "message";

    	Map<String, String> map = new HashMap<>();
    	
    	// 타입이 id일 때
    	if(dto.getType().equals("id")) {
    		// 타입이 id인데 id가 비어있을 때
    		if(userId == null || userId.isEmpty()) {
    			// 에러메세지를 보낸다
    			map.put(message, "emptyIdError");
    			return map;
    		}else{
    			// id가 비어있지 않으면 성공이라는 메세지와 아이디를 보낸다
    			map.put(message,"success");
    			map.put("id", userId);
    			return map;
    		}
    	}else if(dto.getType().equals("pwd")){
    		if(userId == null || userId.isEmpty()) {
    			map.put(message, "emptyPwdError");
    			return map;
    		}else {
    			map.put(message, "success");
    			map.put("userId", userId);
    			return map;
    		}
    	}
    	model.addAttribute("userId", userId);
    	return	map;
    }
    
    // 비밀번호 변경
    @PostMapping("/reset-password")
    public HashMap<String, String> resetPwd(
    		@RequestBody ResetPasswordRequestDTO dto ){
    	HashMap<String, String> map = new HashMap<>();
    	String message = "message";
    	
    	UserVO savedUser = userService.selectById(dto.getUserId());
    	
    	if(savedUser == null) {
    		map.put(message, "Wrong Approach");
    		return map;
    	}
    	
    	String newPwd = dto.getNewPassword();
    	String conPwd = dto.getConfirmPassword();
    	
    	if(!newPwd.equals(conPwd)) {
    		map.put(message, "differentPwd");
    		return map;
    	}
    	
    	if(bCryptPasswordEncoder.matches(newPwd, savedUser.getUserPwd())) {
    		map.put(message, "samePwdError");
    		return map;
    	}

    	
        String encPassword = bCryptPasswordEncoder.encode(newPwd);
        dto.setEncodingPwd(encPassword);
    	
        
        
        userService.resetPwd(dto);
    	map.put(message, "success");
    	
    	return map;
    }
    
	
	// 비밀번호 검사 로직(버튼을 눌렀을 때)
	@PostMapping("/user/check-password")
	public HashMap<String, String> checkPwd(String password,
		@SessionAttribute(value = "user", required = false) SessionUserDTO user){
		HashMap <String, String> map = new HashMap<>();
		String result = userService.checkPwd(password, user);
		if(result.equals("success")) {
			map.put("message", result);
			return map;
		}else {
			map.put("message", result);
			return map;
		}
		
	}
	  // 비밀번호 변경
    @PostMapping("/user/mypage/reset-password")
    @ResponseBody
    public HashMap<String, String> resetPwd(
    		@SessionAttribute("user") SessionUserDTO user,
    		@RequestBody ResetPasswordRequestDTO dto){
    	HashMap<String, String> map = new HashMap<>();
    	
    	System.out.println(dto);
    	
    	if(!isValid("pwd", dto.getCurrentPassword())) {
    		map.put("message", "Not Valid cpwd");
    		return map;
    	}
    	if(!isValid("pwd", dto.getNewPassword())) {
    		map.put("message", "Not Valid npwd");
    		return map;
    	}
    	if(!isValid("pwd", dto.getConfirmPassword())) {
    		map.put("message", "Not Valid cfpwd");
    		return map;
    	}
    	
    	
    	String message = userService.updatePwd(user, dto);
    	System.out.println(message);
    	map.put("message", message);
    	
    	return map;
    }
    
    // 탈퇴
    @PostMapping("user/mypage/withdrawal")
    @ResponseBody
    public HashMap<String, String> withdrawal(
    		@SessionAttribute("user") SessionUserDTO user){
    	String result = "";
    	HashMap<String, String> map = new HashMap<>();
    	if(user == null) {
    		map.put("message", "Wrong Approach");
    		return map;
    	}
    	
    	result = userService.withdraw(user);
    	
    	return map;
    }
    
    // 닉네임 설정(변경)
    @PostMapping("/user/setNickname")
    public String setNickname(String nickname) {
    	
    	SessionUserDTO loginedUser = (SessionUserDTO) session.getAttribute("user");
    	if(loginedUser == null) {
    		return "redirect:/access-denied";
    	}
    	
    	if(userService.checkDuplicateByNickname(nickname) == 1) {
    		return "Exist";
    	}
    	
    	if(isValid("nickanem", nickname)) {
    		return "Not Valid";
    	}
    	
    	userService.resetNickname(nickname, loginedUser);
    	log.info("닉네임 변경 결과 : ");
    	
    	return "success";
    }
    
    // 이용 동의에 따른 에러메세지를 주기위한 컨트롤러
    @PostMapping("/agree")
    public HashMap<String, String> agree(@RequestParam boolean agreeTerms, @RequestParam boolean agreePrivacy){
    	HashMap<String, String> map = new HashMap<>();
    	
    	log.info("agreeTerms : " + agreeTerms);
    	
    	if(!agreeTerms) {
    		map.put("result", "Not AgreeTerms");
    		map.put("message", "이용약관에 동의해주세요.");
    		return map;
    	}
    	
    	if(!agreePrivacy) {
    		map.put("result", "Not AgreePrivacy");
    		map.put("message", "개인정보 처리방침에 동의해주세요.");
    		return map;
    	}
    	
    	map.put("result", "success");
    	return map;
    	
    }

}	

