package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.LoginDTO;
import com.kb.inno.admin.Service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "/admin/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginDTO userInfo, HttpSession session) {
        LoginDTO loginResult = loginService.login(userInfo);
        if(loginResult != null) {
            session.setAttribute("loginResult", loginResult.getLoginId());
            return "index";
        }
        return "redirect:/login";
    }
}
