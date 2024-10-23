package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.AdminDTO;
import com.kb.inno.admin.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/member")
public class AdminController {

    // 디렉터리 공통
    @Value("/admin/member")
    private String directory;

    private final AdminService adminService;

    @GetMapping("/list")
    public String adminList(Model model) {
        List<AdminDTO> selectList = adminService.selectList();
        model.addAttribute("selectList", selectList);
        return directory + "/account_admin";
    }
}

