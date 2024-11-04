/**
 * 파일명     : PopupController.java
 * 화면명     : 팝업 관리
 * 설명       : 팝업 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.24
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Controller;

import ch.qos.logback.core.util.FileUtil;
import com.kb.inno.admin.DTO.PopupDTO;
import com.kb.inno.admin.Service.PopupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/popup")
public class PopupController {
    
    // 디렉터리 공통
    @Value("/admin/popup")
    private String directory;

    // 서비스 연결
    private final PopupService popupService;

    // 팝업 리스트 페이지 이동
    @GetMapping("/list")
    public String popupList(Model model) {
        List<PopupDTO> selectList = popupService.selectList();
        model.addAttribute("selectList", selectList);
        return directory + "/popup";
    }
    
    // 팝업 추가 페이지 이동
    @GetMapping("/add")
    public String popupAdd() {
        return directory + "/popup_input";
    }

    // 팝업 수정 페이지 이동
    @GetMapping("/modify/{popupId}")
    public String popupModify(@PathVariable int popupId, Model model) {
        PopupDTO popup = popupService.select(popupId);
        model.addAttribute("popup", popup);
        return "/admin/popup/popup_input";
    }

    // 이미지 업로드
    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file) {
        // 오리지널 파일 이름
        String originalFileName = file.getOriginalFilename();

        // 파일 확장자 설정
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 파일 이름 설정
        String fileName = UUID.randomUUID().toString() + fileExtension;

        // 파일 경로 설정
        Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/").toAbsolutePath().normalize();
        String savePath = path + "\\upload\\";

        // 디렉토리 없으면 생성
        File directory = new File(savePath);
        if(!directory.exists()) {
            directory.mkdirs();
        }

        // 파일 저장
        try {
            file.transferTo(new File(savePath, fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // fileName 반환
        return ResponseEntity.ok().body(fileName);
    }

    // 팝업 추가, 수정
    @PostMapping("/save")
    public String popupSave(RedirectAttributes redirectAttributes, PopupDTO popupDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result;
        // 추가, 수정 처리
        if (popupDTO.getPopup_sn() == 0) {
            result = popupService.popupAdd(popupDTO, loginId);
        } else {
            result = popupService.popupModify(popupDTO, loginId);
        }

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "작업이 성공적으로 완료되었습니다.");
            return "redirect:" + directory + "/list";
        } else {
            redirectAttributes.addFlashAttribute("msg", "작업이 실패했습니다.");
            return directory + "/popup_input";
        }
    }

    // 팝업 삭제
    @PostMapping("/delete")
    public String popupDelete(@RequestParam("popupId") int popupId) {
        int result = popupService.delete(popupId);
        if (result == 1) {
            return "redirect:" + directory + "/list";
        }
        return directory + "/popup_input";
    }
}
