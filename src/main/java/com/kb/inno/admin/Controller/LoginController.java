/**
 * 파일명     : LoginController.java
 * 화면명     : 로그인
 * 설명       : 관리자 로그인, 로그아웃
 * 최초개발일  : 2024.10.24
 * 최초개발자  : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
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
    
    // Service 연결
    private final LoginService loginService;
    
    // 임시로 memberController로 바로 연결되게 함 : 변경 요망
    private final MemberController memberController;
    
    // 로그인 페이지 이동
    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "/admin/login";
    }

    // 로그인
    @PostMapping("/login")
    public String login(@ModelAttribute("loginDTO") LoginDTO loginDTO, Model model) {
        // 로그인 로직 추가 요망
        return memberController.adminList(model);
    }
}
