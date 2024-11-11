/**
 * 파일명     : InterchangeController.java
 * 화면명     : 현지 교류 관리
 * 설명       : 현지 교류 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.11
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.InterchangeDTO;
import com.kb.inno.admin.Service.InterchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/interchange")
public class InterchangeController {

    // 서비스 연결
    private final InterchangeService interchangeService;

    // 공통 경로 설정
    @Value("/admin/interchange")
    private String directory;

    // 현지 교류 리스트 조회
    @RequestMapping("/list")
    public String list(Model model) {
        List<InterchangeDTO> selectList = interchangeService.selectList();
        model.addAttribute("selectList", selectList);
        return directory + "/interchange";
    }

    // 현지 교류 등록 화면 이동
    @RequestMapping("/insert")
    public String insert(Model model) {
        return directory + "/interchange_insert";
    }

    // 현지 교류 등록
    @PostMapping("/insert")
    public String insert(RedirectAttributes redirectAttributes,InterchangeDTO interchangeDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result = interchangeService.insert(interchangeDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/list";
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
            return directory + "/interchange_insert";
        }
    }

    // 현지 교류 수정 화면 이동
    @RequestMapping("/update/{exch_sn}")
    public String update(@PathVariable int exch_sn, Model model) {
        InterchangeDTO select = interchangeService.select(exch_sn);
        model.addAttribute("interchange", select);
        return directory + "/interchange_update";
    }

    // 현지 교류 수정
    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes,InterchangeDTO interchangeDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result = interchangeService.update(interchangeDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정이 완료되었습니다.");
            return "redirect:" + directory + "/list";
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패했습니다.");
            return "redirect:" + directory + "/place_update";
        }
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("exch_sn") int exch_sn) {
        interchangeService.delete(exch_sn);
        return "redirect:" + directory + "/list";
    }
}
