package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.FaqDTO;
import com.kb.inno.admin.Service.FaqCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/faq/category")
public class FaqCategoryController {
    // 서비스 연결
    private final FaqCategoryService faqCategoryService;

    // 공통 경로 설정
    @Value("/faq")
    private String directory;

    // FAQ 카테고리 리스트 조회
    @RequestMapping("/list/{menuId}")
    public String selectList(Model model, @PathVariable int menuId,
                             @RequestParam(value = "type", required = false, defaultValue = "") String type,
                             @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        faqCategoryService.selectList(menuId, model, type, keyword, page);
        return directory + "/faq_category";
    }

    // FAQ 등록 페이지 이동
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/faq_category_insert";
    }

//    // FAQ 등록
//    @PostMapping("/insert")
//    public String insert(RedirectAttributes redirectAttributes, FaqDTO faqDTO) {
//        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
//        // 수정 요망 : 임시 아이디 값
//        int loginId = 1;
//
//        int result = faqCategoryService.insert(faqDTO, loginId);
//
//        if(result == 1) {
//            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
//            return "redirect:" + directory + "/list/" + faqDTO.getMenu_id();
//        } else {
//            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
//            return directory + "/faq";
//        }
//    }
}
