package com.korea.project.controller.franchise;

import com.korea.project.service.franchise.FranchiseService;
import com.korea.project.vo.franchise.FranchiseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FranchiseController {

    private final FranchiseService franchiseService;

    @Autowired
    public FranchiseController(FranchiseService franchiseService) {
        this.franchiseService = franchiseService;
    }

    @GetMapping("/franchises")
    public String getAllFranchises(
            @RequestParam(name = "sector", required = false) Integer sector,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            Model model) {
        List<FranchiseVO> franchises;
        int totalItems;

        if (search != null && !search.isEmpty()) {
            franchises = franchiseService.searchFranchisesByName(search, (page - 1) * size, size);
            totalItems = franchiseService.countFranchisesByName(search);
        } else if (sector != null) {
            franchises = franchiseService.getFranchisesBySectorPaged(sector, (page - 1) * size, size);
            totalItems = franchiseService.countFranchisesBySector(sector);
        } else {
            franchises = franchiseService.getAllFranchisesPaged((page - 1) * size, size);
            totalItems = franchiseService.countAllFranchises();
        }

        int totalPages = (int) Math.ceil((double) totalItems / size);

        model.addAttribute("franchises", franchises);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sector", sector);
        model.addAttribute("search", search);
        return "franchise/franchiseList";
    }
    @GetMapping("/franchiseboard")
    public String showFranchiseDetail(@RequestParam("id") int id, Model model) {
        FranchiseVO franchise = franchiseService.getFranchiseById(id);
        model.addAttribute("franchise", franchise);
        return "franchise/franchiseboard";
    }

  

   
}