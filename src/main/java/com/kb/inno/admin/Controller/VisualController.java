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
@RequestMapping("/visual")
public class VisualController {
    
    // 서비스 연결
    private final VisualService visualService;
    
    // 메인 디렉토리 설정
    @Value("/visual")
    private String directory;
    
    // 메인 비주얼 리스트 조회
    @RequestMapping("/list/{menuId}")
    public String visualList(@PathVariable int menuId, Model model) {
        List<VisualDTO> selectList = visualService.selectList();
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
        return directory + "/visual";
    }

    // 메인 비주얼 등록 페이지 이동
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/visual_insert";
    }

    // 메인 비주얼 상세 페이지 이동
    @PostMapping("/detail")
    public String detail(@RequestParam int menuId, @RequestParam int main_sn, Model model) {
        VisualDTO visual = visualService.select(main_sn);
        model.addAttribute("visual", visual);
        model.addAttribute("menuId", menuId);
        return directory + "/visual_update";
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
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + visualDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
            return directory + "/visual_insert";
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
            redirectAttributes.addFlashAttribute("msg", "수정이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + visualDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패했습니다.");
            return directory + "/visual_update";
        }
    }

    // 메인 비주얼 삭제
    @ResponseBody
    @PostMapping("/delete")
    public String delete(@RequestParam("main_sn") int main_sn) {
        visualService.delete(main_sn);
        return "success";
    }
}
