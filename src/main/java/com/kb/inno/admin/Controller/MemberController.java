/**
 * 파일명     : MemberController.java
 * 화면명     : 관리자 계정 관리
 * 설명       : 관리자 계정 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.10.24
 * 최초개발자  : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.MemberDTO;
import com.kb.inno.admin.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    // 디렉터리 공통
    @Value("/member")
    private String directory;
    
    // Service 연결
    private final MemberService memberService;

    // 관리자 계정 관리 리스트 조회
    @GetMapping("/list")
    public String adminList(Model model) {
        List<MemberDTO> selectList = memberService.selectList();
        model.addAttribute("selectList", selectList);
        return directory + "/account_admin";
    }

    // 관리자 계정 관리 신규 등록 페이지 이동
    @GetMapping("/add")
    public String adminAdd(Model model) {
        // 수정 페이지와 같이 쓰고 있으므로 빈 DTO를 만들어 보내어 사용
        model.addAttribute("member", new MemberDTO());
        return directory + "/account_admin_input";
    }

    // 관리자 수정 페이지 이동
    @GetMapping("/modify/{memberId}")
    public String adminModify(@PathVariable int memberId, Model model) {
        MemberDTO member = memberService.memberModify(memberId);
        model.addAttribute("member", member);
        return directory + "/account_admin_input";
    }

    // 관리자 추가, 수정
    @PostMapping("/save")
    public String adminSave(RedirectAttributes redirectAttributes, MemberDTO memberDTO) {
        // 로그인 기능 구현 전 : loginId 에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result;
        // 추가, 수정
        if(memberDTO.getMngr_sn() == 0) {
            result = memberService.memberAdd(memberDTO, loginId);
        } else {
            result = memberService.memberModify(memberDTO, loginId);
        }

        // 결과 메시지
        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "작업이 완료 되었습니다.");
            return "redirect:" + directory + "/list";
        } else if(result == 2) {
            redirectAttributes.addFlashAttribute("msg", "이미 존재하는 아이디입니다.");
            return directory + "/account_admin_input";
        } else {
            redirectAttributes.addFlashAttribute("msg", "작업이 실패하였습니다.");
            return directory + "/account_admin_input";
        }
    }

    // 관리자 삭제
    @PostMapping("/delete")
    public String popupDelete(@RequestParam("memberId") int memberId) {
        int result = memberService.delete(memberId);
        if (result == 1) {
            return "redirect:" + directory + "/list";
        }
        return directory + "/account_admin_input";
    }
}

