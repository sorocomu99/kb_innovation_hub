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

    // 제휴 사례 리스트 조회
    @RequestMapping("/list")
    public String selectList(Model model) {
        List<AffiliateDTO> selectList = affiliateService.selectList();
        model.addAttribute("selectList", selectList);
        return directory + "/affiliate";
    }

    // 제휴 사례 추가 페이지 이동
    @RequestMapping("/insert")
    public String insert() {
        return directory + "/affiliate_insert";
    }
    
    // 제휴 사례 상세 페이지 이동
    @PostMapping("/detail")
    public String detail(@RequestParam int affiliate_sn, Model model) {
        AffiliateDTO affiliate = affiliateService.select(affiliate_sn);
        model.addAttribute("affiliate", affiliate);
        return directory + "/affiliate_update";
    }

    // 제휴 사례 추가
    @PostMapping("/insert")
    public String insert(RedirectAttributes redirectAttributes, AffiliateDTO affiliateDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result = affiliateService.insert(affiliateDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/list";
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
            return directory + "/affiliate_insert";
        }
    }

    // 제휴 사례 수정
    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes, AffiliateDTO affiliateDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result = affiliateService.update(affiliateDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정이 완료되었습니다.");
            return "redirect:" + directory + "/list";
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패했습니다.");
            return directory + "/affiliate_update";
        }
    }

    // 제휴 사례 삭제
    @ResponseBody
    @PostMapping("/delete")
    public String delete(@RequestParam("affiliate_sn") int affiliate_sn) {
        affiliateService.delete(affiliate_sn);
        return "success";
    }
}
