/**
 * 파일명     : AffiliateController.java
 * 화면명     : 제휴 사례 관리
 * 설명       : 제휴 사례 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.10.31
 * 최초개발자  : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.AffiliateDTO;
import com.kb.inno.admin.Service.AffiliateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/affiliate")
public class AffiliateController {

    // 서비스 연결
    private final AffiliateService affiliateService;

    // 공통 경로 설정
    @Value("/admin/affiliate")
    private String directory;

    // 제휴 사례 조회
    @GetMapping("/list")
    public String selectList(Model model) {
        List<AffiliateDTO> selectList = affiliateService.selectList();
        model.addAttribute("selectList", selectList);
        return directory + "/affiliate";
    }

    // 제휴 사례 등록
    @GetMapping("/add")
    public String add(Model model) {
        return directory + "/affiliate_input";
    }

    @GetMapping("/modify/{affiliate_sn}")
    public String modify(@PathVariable int affiliate_sn, Model model) {
        AffiliateDTO select = affiliateService.select(affiliate_sn);
        model.addAttribute("affiliate", select);
        return directory + "/affiliate_input";
    }

    // 제휴 사례 저장
    @PostMapping("/save")
    public String save(RedirectAttributes redirectAttributes, AffiliateDTO affiliateDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;
        int result = 0;

        // 추가, 수정 처리
        if (affiliateDTO.getAffiliate_sn() == 0) {
            result = affiliateService.addVisual(affiliateDTO, loginId);
        } else {
            result = affiliateService.modifyVisual(affiliateDTO, loginId);
        }

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "작업이 성공적으로 완료되었습니다.");
            return "redirect:" + directory + "/list";
        } else {
            redirectAttributes.addFlashAttribute("msg", "작업이 실패했습니다.");
            return directory + "/affiliate_input";
        }
    }

    // 제휴 사례 삭제
    @PostMapping("/delete")
    public String delete(@RequestParam("affiliate_sn") int affiliate_sn) {
        affiliateService.delete(affiliate_sn);
        return directory + "/affiliate_input";
    }
}
