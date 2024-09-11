package com.korea.project.dto.user;

import lombok.Data;

@Data
public class ResetPasswordRequestDTO {
    private String newPassword;
    private String confirmPassword, userId, encodingPwd;
    private String currentPassword;
    
}
