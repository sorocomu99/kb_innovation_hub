/**
 * 파일명     : RecruitController.java
 * 화면명     : 채용 지원 관리
 * 설명       : 채용 지원 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.04
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.RecruitDTO;
import com.kb.inno.admin.Service.RecruitService;
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
@RequestMapping("/admin/recruit")
public class RecruitController {

    // 서비스 연결
    private final RecruitService recruitService;

    // 공통 경로 설정
    @Value("/admin/recruit")
    private String directory;
    
    // 채용 지원 조회
    @GetMapping("/info")
    public String select(Model model) {
        RecruitDTO select = recruitService.select();
        model.addAttribute("select", select);
        return directory + "/recruit";
    }

    // 채용 지원 저장
    @PostMapping("/save")
    public String save(RedirectAttributes redirectAttributes, RecruitDTO recruitDTO) {
        // 로그인 한 아이디 session 추가 작업 필요
        int loginId = 1;

        int recruit_sn = recruitDTO.getEmploy_sn();

        int result = 0;

        if(recruit_sn == 0) {
            result = recruitService.insert(recruitDTO, loginId);
        } else {
            result = recruitService.modify(recruitDTO, loginId);
        }

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "작업이 완료 되었습니다.");
            return "redirect:" + directory + "/info";
        } else {
            redirectAttributes.addFlashAttribute("msg", "작업이 실패했습니다.");
            return directory + "/recruit";
        }
    }
}