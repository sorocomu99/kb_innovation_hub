package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.FaqDTO;
import com.kb.inno.admin.Service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/faq")
public class FaqController {
    // 서비스 연결
    private final FaqService faqService;
    
    // 공통 경로 설정
    @Value("/faq")
    private String directory;
    
    // FAQ 리스트 조회
    @RequestMapping("/list")
    public String selectList(Model model, @RequestParam(value="type", required=false, defaultValue = "") String type, @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        faqService.selectList(model, type, keyword, page);
        return directory + "/faq";
    }

    // FAQ 등록 화면 이동
    @RequestMapping("/insert")
    public String insert() {
        return directory + "/faq_insert";
    }

    // FAQ 등록
    @PostMapping("/insert")
    public String insert(RedirectAttributes redirectAttributes, FaqDTO faqDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result = faqService.insert(faqDTO, loginId);

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/list";
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
            return directory + "/faq";
        }
    }

    // FAQ 수정 화면 이동
    @PostMapping("/detail")
    public String detail(@RequestParam int faq_sn, Model model) {
        FaqDTO faq = faqService.select(faq_sn);
        model.addAttribute("faq", faq);
        return directory + "/faq_update";
    }

    // FAQ 수정
    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes, FaqDTO faqDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result = faqService.update(faqDTO, loginId);

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정이 완료되었습니다.");
            return "redirect:" + directory + "/list";
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패했습니다.");
            return directory + "/faq";
        }
    }

    // FAQ 삭제
    @ResponseBody
    @PostMapping("/delete")
    public String delete(@RequestParam("faq_sn") int faq_sn, Model model) {
        faqService.delete(faq_sn);
        return "success";
    }
}
