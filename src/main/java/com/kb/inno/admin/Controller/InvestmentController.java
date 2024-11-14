/**
 * 파일명     : InvestmentController.java
 * 화면명     : 협업 성과 - 투자 그래프 관리
 * 설명       : 투자 그래프 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.05
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.InvestmentDTO;
import com.kb.inno.admin.Service.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/investment")
public class InvestmentController {

    // 서비스 연결
    private final InvestmentService investmentService;

    // 공통 경로 설정
    @Value("/investment")
    private String directory;

    // 협업 성과 - 투자 그래프 조회
    @RequestMapping("/info")
    public String select(Model model) {
        InvestmentDTO select = investmentService.select();
        model.addAttribute("select", select);
        return directory + "/investment";
    }

    // 협업 성과 - 투자 그래프 저장
    @PostMapping("/save")
    public String save(RedirectAttributes redirectAttributes, InvestmentDTO investmentDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result;

        // 추가, 수정 처리
        if (investmentDTO.getGraph_sn() == 0) {
            result = investmentService.insert(investmentDTO, loginId);
        } else {
            result = investmentService.update(investmentDTO, loginId);
        }

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "저장이 완료되었습니다.");
            return "redirect:" + directory + "/info";
        } else {
            redirectAttributes.addFlashAttribute("msg", "저장이 실패했습니다.");
            return directory + "/investment";
        }
    }
}
