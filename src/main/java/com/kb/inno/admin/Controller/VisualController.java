/**
 * 파일명     : VisualController.java
 * 화면명     : 메인 비주얼 관리
 * 설명       : 메인 비주얼 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.24
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.VisualDTO;
import com.kb.inno.admin.Service.VisualService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/visual")
public class VisualController {
    
    // Service 연결
    private final VisualService visualService;
    
    // 메인 디렉토리 설정
    @Value("/admin/visual")
    private String directory;
    
    // 메인 비주얼 조회
    @RequestMapping("/list")
    public String visualList(Model model) {
        List<VisualDTO> selectList = visualService.selectList();
        model.addAttribute("selectList", selectList);
        return directory + "/main";
    }

    // 메인 비주얼 등록 페이지 이동
    @RequestMapping("/insert")
    public String insert() {
        return directory + "/main_insert";
    }

    // 메인 비주얼 수정 페이지 이동
    @RequestMapping("/update/{visualId}")
    public String update(@PathVariable int visualId, Model model) {
        VisualDTO visual = visualService.select(visualId);
        model.addAttribute("visual", visual);
        return directory + "/main_update";
    }

    // 메인 비주얼 등록
    @PostMapping("/insert")
    public String insert(VisualDTO visualDTO, RedirectAttributes redirectAttributes) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result = visualService.insert(visualDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "작업이 성공적으로 완료되었습니다.");
            return "redirect:" + directory + "/list";
        } else {
            redirectAttributes.addFlashAttribute("msg", "작업이 실패했습니다.");
            return directory + "/main_insert";
        }
    }

    // 메인 비주얼 수정
    @PostMapping("/update")
    public String update(VisualDTO visualDTO, RedirectAttributes redirectAttributes) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result = visualService.update(visualDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "작업이 성공적으로 완료되었습니다.");
            return "redirect:" + directory + "/list";
        } else {
            redirectAttributes.addFlashAttribute("msg", "작업이 실패했습니다.");
            return directory + "/main_update";
        }
    }

    // 메인 비주얼 삭제
    @PostMapping("/delete")
    public String deleteVisual(@RequestParam("visualId") int visualId) {
        visualService.delete(visualId);
        return directory + "/main";
    }
}
