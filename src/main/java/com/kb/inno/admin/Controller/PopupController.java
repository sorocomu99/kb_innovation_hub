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

import com.kb.inno.admin.DTO.PopupDTO;
import com.kb.inno.admin.Service.PopupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/popup")
public class PopupController {
    
    // 서비스 연결
    private final PopupService popupService;

    // 디렉터리 공통
    @Value("/popup")
    private String directory;

    // 파일 경로
    @Value("src/main/resources/static/")
    private String staticPath;

    // 팝업 리스트 조회
    @RequestMapping("/list")
    public String selectList(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        popupService.selectList(model, page);
        return directory + "/popup";
    }
    
    // 팝업 등록 페이지 이동
    @RequestMapping("/insert")
    public String insert() {
        return directory + "/popup_insert";
    }

    // 팝업 상세 페이지 이동
    @PostMapping("/detail")
    public String detail(@RequestParam int popup_sn, Model model) {
        PopupDTO popup = popupService.select(popup_sn);
        model.addAttribute("popup", popup);
        return directory + "/popup_update";
    }

    // 이미지 업로드
    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file) {
        Map<String, String> responseMap = new HashMap<>();

        Path path = Paths.get(System.getProperty("user.dir"), staticPath).toAbsolutePath().normalize();
        String savePath = path + "\\upload\\";

        String originalFileName = file.getOriginalFilename();	// 원래 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	// 파일 확장자
        String savedFileName = UUID.randomUUID() + extension;	// 저장될 파일명

        File targetFile = new File(savePath + savedFileName);
        try {
            InputStream fileStream = file.getInputStream();
            Files.copy(fileStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            responseMap.put("url", "/upload/" + savedFileName);
            responseMap.put("responseCode", "success");

        } catch (IOException e) {
            targetFile.delete();  // 저장된 파일 삭제
            responseMap.put("responseCode", "error");
            e.printStackTrace();
        }

        // json으로 리턴하면 오류나기 때문에 string으로 리턴
        return ResponseEntity.ok(responseMap);
    }

    // 팝업 등록
    @PostMapping("/insert")
    public String insert(RedirectAttributes redirectAttributes, PopupDTO popupDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result = popupService.insert(popupDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "등록이 완료되었습니다.");
            return "redirect:" + directory + "/list";
        } else {
            redirectAttributes.addFlashAttribute("msg", "등록이 실패했습니다.");
            return directory + "/popup_insert";
        }
    }

    // 팝업 수정
    @PostMapping("/update")
    public String update(RedirectAttributes redirectAttributes, PopupDTO popupDTO) {
        // 로그인 기능 구현 전 : loginId에 session 값 추가 할 것
        // 수정 요망 : 임시 아이디 값
        int loginId = 1;

        int result = popupService.update(popupDTO, loginId);

        // 결과 메시지 설정
        if (result == 1) {
            redirectAttributes.addFlashAttribute("msg", "수정이 완료되었습니다.");
            return "redirect:" + directory + "/list";
        } else {
            redirectAttributes.addFlashAttribute("msg", "수정이 실패했습니다.");
            return directory + "/popup_update";
        }
    }

    // 팝업 삭제
    @ResponseBody
    @PostMapping("/delete")
    public String delete(@RequestParam("popupId") int popupId) {
        popupService.delete(popupId);
        return "success";
    }
}
