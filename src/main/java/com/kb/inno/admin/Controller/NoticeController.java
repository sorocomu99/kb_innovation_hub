package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.NoticeDTO;
import com.kb.inno.admin.Service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    // 서비스 연결
    private final NoticeService noticeService;

    // 공통 경로 설정
    @Value("/notice")
    public String directory;

    // 공지사항 리스트 조회
    @RequestMapping("/list/{menuId}")
    public String selectList(@PathVariable int menuId, Model model,
                             @RequestParam(value = "type", required = false, defaultValue = "") String type,
                             @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        noticeService.selectList(menuId, model, type, keyword, page);
        return directory + "/notice";
    }

    // 공지사항 등록 페이지 이동
    @RequestMapping("/insert/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/notice_insert";
    }

    // 공지사항 등록
    @PostMapping("/insert")
    public String insert(RedirectAttributes redirectAttributes, NoticeDTO noticeDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        // 만약 ntc_yn이 null이면 N 값 강제 대입
        if(noticeDTO.getNtc_yn() == null || noticeDTO.getNtc_yn().equals("")) {
            noticeDTO.setNtc_yn("N");
        }

        int result = noticeService.insert(noticeDTO, loginId);

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + noticeDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
            return directory + "/notice_insert";
        }
    }

    // 공지사항 수정 페이지 이동
    @PostMapping("/detail")
    public String detail(@RequestParam int menuId, @RequestParam int ntc_sn, Model model) {
        NoticeDTO notice = noticeService.select(ntc_sn);
        model.addAttribute("notice", notice);
        model.addAttribute("menuId", menuId);
        return directory + "/notice_update";
    }

    // 공지사항 수정
    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes, NoticeDTO noticeDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        // 만약 ntc_yn이 null이면 N 값 강제 대입
        if(noticeDTO.getNtc_yn() == null || noticeDTO.getNtc_yn().equals("")) {
            noticeDTO.setNtc_yn("N");
        }

        // 만약 del_yn이 null이면 N 값 강제 대입
        if(noticeDTO.getDel_yn() == null || noticeDTO.getDel_yn().equals("")) {
            noticeDTO.setDel_yn("N");
        }

        int result = noticeService.update(noticeDTO, loginId);

        if(result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정이 완료되었습니다.");
            return "redirect:" + directory + "/list/" + noticeDTO.getMenu_id();
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패했습니다.");
            return directory + "/notice_update";
        }
    }
    
    // 공지사항 삭제
    @ResponseBody
    @PostMapping("/delete")
    public String delete(@RequestParam("ntc_sn") int ntc_sn) {
        noticeService.delete(ntc_sn);
        return "success";
    }
}
