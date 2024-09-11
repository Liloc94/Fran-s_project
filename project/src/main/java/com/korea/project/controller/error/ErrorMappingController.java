package com.korea.project.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorMappingController {

    // 권한없을 때 페이지 매핑
    @GetMapping("/access-denied")
    public String accessDeinedPage() {
    	return "error/accessDenied";
    }
	
}
