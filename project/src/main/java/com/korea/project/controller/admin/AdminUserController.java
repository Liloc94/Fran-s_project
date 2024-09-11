package com.korea.project.controller.admin;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.korea.project.service.admin.AdminUserService;
import com.korea.project.vo.user.UserVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public String listUsers(@RequestParam(name = "page", defaultValue = "1") int page,
                            @RequestParam(name = "searchName", required = false) String searchName,
                            Model model) {
        int pageSize = 20;
        int offset = (page - 1) * pageSize;

        List<UserVO> users;
        int totalUsers;

        if (searchName != null && !searchName.isEmpty()) {
            users = adminUserService.selectUserByName(searchName, offset, pageSize);
            totalUsers = adminUserService.countUserByName(searchName);
        } else {
            users = adminUserService.selectUserByPage(offset, pageSize);
            totalUsers = adminUserService.countAllUsers();
        }

        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchName", searchName);

        return "admin/adminUser/userlist";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable("id") int userIdx, RedirectAttributes redirectAttributes) {
        boolean success = adminUserService.deleteUser(userIdx);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "User deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("message", "Failed to delete user");
        }
        return "redirect:/admin/user/list";
    }
}