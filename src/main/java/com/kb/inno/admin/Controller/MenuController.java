/**
 * 파일명     : MenuController.java
 * 화면명     : 메뉴 관리
 * 설명       : 메뉴 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.04
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.MenuDTO;
import com.kb.inno.admin.Service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/menu")
public class MenuController {
    // 서비스 연결
    private final MenuService menuService;

    // 공통 경로 설정
    @Value("/admin/menu")
    private String directory;
    
    // 메뉴 리스트 조회
    @GetMapping("/list")
    public String select(Model model) {
        List<MenuDTO> selectList = menuService.selectList();
        model.addAttribute("selectList", selectList);
        return directory + "/menu";
    }
    
    // 메뉴 상세 조회
    @GetMapping("/info")
    public ResponseEntity<Object> detail(@RequestParam("menu_sn") int menu_sn, Model model) {
        MenuDTO select = menuService.select(menu_sn);
        return ResponseEntity.ok().body(select);
    }

    @PostMapping("/modify")
    public String modify(RedirectAttributes redirectAttributes, MenuDTO menuDTO) {
        int result = menuService.modify(menuDTO);
        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "작업이 성공적으로 완료되었습니다.");
            return "redirect:" + directory + "/list";
        } else {
            redirectAttributes.addFlashAttribute("msg", "작업이 실패했습니다.");
            return directory + "/menu";
        }
    }
}
