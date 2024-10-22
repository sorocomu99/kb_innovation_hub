package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.AdminDTO;
import com.kb.inno.admin.DTO.LoginDTO;
import com.kb.inno.admin.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/")
    public String adminList(Model model) {
        List<AdminDTO> selectList = adminService.selectList();
        model.addAttribute("selectList", selectList);
        return "/admin/adminList";
    }
}

