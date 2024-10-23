package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.LoginDTO;
import com.kb.inno.admin.Service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("")
    public String login(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "/admin/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginDTO") LoginDTO loginDTO, Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "/admin/member/account_admin";
    }
}
