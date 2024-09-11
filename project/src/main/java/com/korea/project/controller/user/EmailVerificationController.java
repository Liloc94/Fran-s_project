package com.korea.project.controller.user;
import java.util.HashMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.korea.project.dto.mail.SendCertificationEmailRequest;
import com.korea.project.dto.mail.VerifyEmailRequest;
import com.korea.project.service.user.UserServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationController {
	
    private final UserServiceImpl userService; // UserService는 인증 번호 검증에 필요한 서비스

    @PostMapping("/verify-email")
    public HashMap<String, String> verifyEmail(
    		@RequestBody VerifyEmailRequest request) {
        HashMap<String, String> map = new HashMap<>();
        log.info("들어온 값" + request);
        
        // 서비스를 통해 이메일 인증 번호를 검증하고 결과를 반환
        boolean verificationResult = userService.verifyEmail(request.getUserEmail(), request.getCertificationNumber());

        if (verificationResult) {
            map.put("result", "success");
        } else {
            map.put("result", "failure");
        }
        
        return map;
    }
    
    @PostMapping("/send-certification-email")
    public HashMap<String, String> sendCertificationEmail(@RequestBody SendCertificationEmailRequest request) {
        HashMap<String, String> map = new HashMap<>();
        
        boolean sendResult = userService.sendCertificationEmail(request.getUserEmail());

        if (sendResult) {
            map.put("result", "success");
            map.put("message", "인증 번호가 이메일로 전송되었습니다.");
        } else {
            map.put("result", "failure");
            map.put("message", "인증 번호 전송에 실패했습니다.");
        }

        return map;
    }
   
}