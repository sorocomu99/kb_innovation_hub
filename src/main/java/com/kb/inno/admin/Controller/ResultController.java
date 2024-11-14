/**
 * 파일명     : ResultController.java
 * 화면명     : 주요 성과보고 관리
 * 설명       : 주요 성과보고 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.29
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.ResultDTO;
import com.kb.inno.admin.Service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/result")
public class ResultController {
    
    // 디렉토리 설정
    @Value("/admin/result")
    private String directory;

    // 서비스 연결
    private final ResultService resultService;
    
    // 주요 성과보고 관리 조회
    @RequestMapping("/info")
    public String select(Model model) {
        ResultDTO result = resultService.select();
        model.addAttribute("result", result);
        return directory + "/result";
    }

    // 주요 성과보고 저장
    @PostMapping("/save")
    public String save(RedirectAttributes redirectAttributes, ResultDTO resultDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result;

        // 추가, 수정 처리
        if (resultDTO.getRslt_sn() == 0) {
            result = resultService.insert(resultDTO, loginId);
        } else {
            result = resultService.modify(resultDTO, loginId);
        }

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "작업이 성공적으로 완료되었습니다.");
            return "redirect:" + directory + "/info";
        } else {
            redirectAttributes.addFlashAttribute("msg", "작업이 실패했습니다.");
            return directory + "/result";
        }
    }
}
