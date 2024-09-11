package com.korea.project.dto.mail;

import lombok.Data;

@Data
public class VerifyEmailRequest {
	 private String userEmail;
     private String certificationNumber;
}
