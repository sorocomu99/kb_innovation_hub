package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.PopupDTO;
import com.kb.inno.admin.Service.PopupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/popup")
public class PopupController {

    private final PopupService popupService;

    // 팝업 리스트 페이지 이동
    @GetMapping("/")
    public String popupList(Model model) {
        List<PopupDTO> selectList = popupService.selectList();
        model.addAttribute("selectList", selectList);
        return "/popup/popup";
    }

    // 팝업 추가 페이지 이동
    @GetMapping("/add")
    public String popupAdd(Model model) {
        model.addAttribute("popup", new PopupDTO());
        return "/popup/popup_input";
    }

    // 팝업 추가
    @PostMapping("/add")
    public String popupAdd(RedirectAttributes redirectAttributes, PopupDTO popupDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;
        
        int result = popupService.popupAdd(popupDTO, loginId);
        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록을 완료 하였습니다.");
            return "redirect:/popup/";
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록을 실패 하였습니다.");
            return "/popup/popup_input";
        }
    }

    // 팝업 수정 화면 이동
    @GetMapping("/modify/{popupId}")
    public String popupModify(@PathVariable int popupId, Model model) {
        PopupDTO popup = popupService.popupModify(popupId);
        model.addAttribute("popup", popup);
        return "/popup/popup_input";
    }
    
    // 팝업 수정
    @PostMapping("/modify")
    public String popupModify(RedirectAttributes redirectAttributes, PopupDTO popupDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result = popupService.popupModify(popupDTO, loginId);

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정을 완료 하였습니다.");
            return "redirect:/popup/";
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정을 실패 하였습니다.");
            return "redirect:/popup/popup_input";
        }
    }
    
    // 팝업 삭제
    @PostMapping("/delete")
    public String popupDelete(@RequestParam("popupId") String popupId) {
        // 로그 추가
        System.out.println("Deleting popup with ID: " + popupId);

        int result = popupService.delete(popupId);
        if (result == 1) {
            return "redirect:/popup/";  // 성공 시 리디렉션
        }
        return "redirect:/popup/popup_input";  // 실패 시 리디렉션
    }
}
