/**
 * 파일명     : LoginController.java
 * 화면명     : 로그인
 * 설명       : 관리자 화면 접속시 로그인 및 세션 생성
 *              로그아웃 세션 삭제
 * 최초개발일 : 2024.10.31
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DAO.LoginDAO;
import com.kb.inno.admin.DTO.LoginDTO;
import com.kb.inno.admin.Service.LoginService;
import com.kb.inno.common.Sha256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/*")
public class LoginController {
    
    // Service 연결
    private final LoginService loginService;

    private Sha256 sha256;

    // 로그인 페이지 이동
    @RequestMapping("/")
    public String login() {
        return "admin/login";
    }
    /*
    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "/admin/login";
    }
    */

    /**
     * 로그인 처리
     * @param
     * @param
     * @return
     */
    @PostMapping("/loginProc")
    @ResponseBody
    public HashMap<String, String> loginProc(@RequestParam Map<String, String> param, HttpServletRequest request) throws Exception {
        HashMap<String, String> result = new HashMap<>();

        System.out.println("================"+param.get("mngr_id"));
        //넘겨온 변수 세팅
        //String mngr_id = loginDTO.getMngr_id();
        //String mngr_pswd = loginDTO.getMngr_pswd();

        //아이디가 없으면 바로 리턴
        if (param.get("mngr_id").equals("") || param.get("mngr_id") == null) {
            result.put("success", "fail");
            result.put("message", "아이디를 입력하세요.");

            return result;
        }

        //비밀번호가 없으면 바로 리턴
        if (param.get("mngr_pswd").equals("") || param.get("mngr_pswd") == null) {
            result.put("success", "fail");
            result.put("message", "비밀번호를 입력하세요.");

            return result;
        }

        //아이디로 해당 관리자 조회
        //LoginDTO login = loginService.memberSelect(param.get("mngr_id").toString());
        
        result.put("success", "success");
        result.put("message", "환영합니다. " + " 님");

        return result;
    }

    /**
     * 로그아웃
     * @param
     * @return
     */
    @PostMapping("/logout")
    public String logOut(HttpServletRequest request) {
        //session이 없으면 null return
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "/admin/";
    }
}
