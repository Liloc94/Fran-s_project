package com.korea.project.controller.user;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.korea.project.dto.board.BoardListRequest;
import com.korea.project.dto.board.BoardResponse;
import com.korea.project.dto.board.PagingResponse;
import com.korea.project.dto.user.SessionUserDTO;
import com.korea.project.service.board.BoardServiceImpl;
import com.korea.project.service.user.UserServiceImpl;
import com.korea.project.vo.board.BoardVO;
import com.korea.project.vo.user.UserVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserMappingController {

	private final UserServiceImpl userService;
	private final BoardServiceImpl boardService;
	private final HttpSession session;
	
	
//	@GetMapping("/user/index")
//	public String index() {
//		return "index";
//	}
	
	// 로그인 페이지에 점근하면 view 보여주기
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }
    
	
	// 회원가입 페이지에 접근하면 view보여주기
	@GetMapping("/register")
	public String registerPage() {
		return "user/register";
	}
	
    // 찾기 페이지로 이동
    @GetMapping("/find")
    public String find(@RequestParam(name = "type", required = false, defaultValue = "id") String type, Model model) {
        model.addAttribute("type", type);
        return "user/find";
    }
    
    // 마이페이지 들어가기 전 비밀번호 확인 페이지
    @GetMapping("user/pwd-check")
    public String pwdCheckPage(	) {
    	
    	SessionUserDTO user = (SessionUserDTO)session.getAttribute("user");
    	
    	if(user == null ) {
    		return "redirect:/access-denied";
    	}
    	if(userService.selectBySession(user).getProvider() != null) {
    		return "redirect:/user/mypage";
    	}
    	
    	return "user/pwdCheckPage";
    }
    
    // 마이페이지의 메인
    @GetMapping("/user/mypage")
	public String myPageMain(Model model) {
    	SessionUserDTO user = (SessionUserDTO)session.getAttribute("user");
    	
		if(user == null) {
			return "redirect:/access-denied"; 
		}
    		
    	UserVO vo = userService.selectBySession(user);
    	model.addAttribute("user", vo);
    	
		return "user/mypage/myPageMain";
    }
    
    // 마이페이지의 비밀번호 변경 페이지
    @GetMapping("/user/mypage/change-pwd")
    public String myPageResetPwd(Model model) {
    	
    	SessionUserDTO user = (SessionUserDTO)session.getAttribute("user");
    	
    	if(session.getAttribute("user") == null ) {
    		return "redirect:/access-denied";
    	}
    	UserVO vo = userService.selectBySession(user);
    	model.addAttribute("user", vo);
    	
    	return "user/mypage/changePwdPage";
    }
    
    // 마이페이지의 탈퇴 페이지
    @GetMapping("/user/mypage/withdrawal")
    public String myPageCacel(Model model) {
    	
    	SessionUserDTO user = (SessionUserDTO)session.getAttribute("user");
    	
    	if(user == null ) {
    		return "redirect:/access-denied";
    	}
    	UserVO vo = userService.selectBySession(user);
    	model.addAttribute("user", vo);
    	return "user/mypage/withdraw";
    }
    
    
    //게시글 목록 보여주기
  	@GetMapping("/user/mypage/my-post")
  	public String list(
  			@ModelAttribute("params") final BoardListRequest params,
  			Model model) {
  		SessionUserDTO user = (SessionUserDTO)session.getAttribute("user");
  		UserVO vo = userService.selectBySession(user);
  		if(user == null ) {
    		return "redirect:/access-denied";
    	}
  		
  		PagingResponse<BoardResponse> res = userService.myPost(params, user);
  		

  		model.addAttribute("response",res);
  		model.addAttribute("user", vo);
  		return "user/mypage/myPost";
   	}

  	// 닉네임 정하는 곳
  	@GetMapping("/user/set-nickname")
  	public String setNickname(@SessionAttribute("user") SessionUserDTO dto, Model model) {
  		if(dto == null) {
  			return "/access-denied";
  		}else {
  			return "user/setNickname";
  		}
  	}
  	
  	// 개인정보 이용 동의 체크 페이지
  	@GetMapping("/agreement")
  	public String getMethodName() {
  		return "user/agreement";
  	}
  	
	
}
