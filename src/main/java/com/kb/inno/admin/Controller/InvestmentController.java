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
import com.kb.inno.admin.DTO.PromoteDTO;
import com.kb.inno.admin.Service.InvestmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/investment")
public class InvestmentController {

    // 서비스 연결
    private final InvestmentService investmentService;

    // 공통 경로 설정
    @Value("/admin/investment")
    private String directory;

    // 협업 성과 - 투자 그래프 관리 조회
    @GetMapping("/info")
    public String select(Model model) {
        InvestmentDTO select = investmentService.select();
        model.addAttribute("select", select);
        return directory + "/investment";
    }

    // 협업 성과 - 투자 그래프 관리 저장
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
            result = investmentService.modify(investmentDTO, loginId);
        }

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "작업이 성공적으로 완료되었습니다.");
            return "redirect:" + directory + "/info";
        } else {
            redirectAttributes.addFlashAttribute("msg", "작업이 실패했습니다.");
            return directory + "/investment";
        }
    }
}
