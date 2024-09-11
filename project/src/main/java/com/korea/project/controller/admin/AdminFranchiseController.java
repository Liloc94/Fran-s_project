package com.korea.project.controller.admin;

import com.korea.project.service.admin.AdminFranchiseService;
import com.korea.project.vo.franchise.FranchiseVO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/franchise")
public class AdminFranchiseController {

    private final AdminFranchiseService adminFranchiseService;

    @Autowired
    public AdminFranchiseController(AdminFranchiseService adminFranchiseService) {
        this.adminFranchiseService = adminFranchiseService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public String listFranchises(@RequestParam(name = "page", defaultValue = "1") int page,
                                 @RequestParam(name = "searchName", required = false) String searchName,
                                 @RequestParam(name = "sector", required = false) Integer sector,
                                 Model model) {
        int pageSize = 20;
        int offset = (page - 1) * pageSize;

        List<FranchiseVO> franchises;
        int totalFranchises;

        if (searchName != null && !searchName.isEmpty()) {
            franchises = adminFranchiseService.searchFranchisesByName(searchName, offset, pageSize);
            totalFranchises = adminFranchiseService.countFranchisesByName(searchName);
        } else if (sector != null) {
            franchises = adminFranchiseService.getFranchisesBySectorPaged(sector, offset, pageSize);
            totalFranchises = adminFranchiseService.countFranchisesBySector(sector);
        } else {
            franchises = adminFranchiseService.selectAllFranchisesPaged(offset, pageSize);
            totalFranchises = adminFranchiseService.countAllFranchises();
        }

        int totalPages = (totalFranchises + pageSize - 1) / pageSize;

        model.addAttribute("franchises", franchises);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchName", searchName);
        model.addAttribute("sector", sector);

        return "admin/adminFranchise/franchiselist";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAddForm(Model model) {
        model.addAttribute("franchise", new FranchiseVO());
        return "admin/adminFranchise/franchiseadd";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addFranchise(@ModelAttribute FranchiseVO franchise) {
        adminFranchiseService.addFranchise(franchise);
        return "redirect:/admin/franchise/list";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        FranchiseVO franchise = adminFranchiseService.getFranchiseById(id);
        model.addAttribute("franchise", franchise);
        return "admin/adminFranchise/franchiseedit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateFranchise(@PathVariable("id") int id, @ModelAttribute FranchiseVO franchise) {
        franchise.setFranchiseIdx(id);
        adminFranchiseService.updateFranchise(franchise);
        return "redirect:/admin/franchise/list";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteFranchise(@PathVariable("id") int id) {
        adminFranchiseService.deleteFranchise(id);
        return "redirect:/admin/franchise/list";
    }
}
