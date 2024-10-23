package com.kb.inno.admin.Controller;

import com.kb.inno.admin.DTO.PopupDTO;
import com.kb.inno.admin.Service.PopupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public String popupAdd(Model model) {
        model.addAttribute("popup", new PopupDTO());
        return directory + "/popup_input";
    }

    // 팝업 수정 페이지 이동
    @GetMapping("/modify/{popupId}")
    public String popupModify(@PathVariable int popupId, Model model) {
        PopupDTO popup = popupService.popupModify(popupId);
        model.addAttribute("popup", popup);
        return "/popup/popup_input";
    }

    // 이미지 업로드 처리
    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file) throws IOException {
        try {
            // 오리지널 파일 이름
            String originalFileName = file.getOriginalFilename();

            // 파일 확장자 설정
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

            // 파일 이름 설정
            String fileName = UUID.randomUUID().toString() + fileExtension;

            // 파일 경로 설정
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/images/");
            String savePath = path + "/upload/";

            // 디렉토리 없으면 생성
            File directory = new File(savePath);
            if(!directory.exists()) {
                directory.mkdirs();
            }

            // 파일 저장
            file.transferTo(new File(savePath, fileName));

//            // 저장 파일 URL을 클라이언트에 반환
//            String imageUrl = "/upload/images/" + fileName;
//            Map<String, String> response = new HashMap<>();
//            response.put("imageUrl", imageUrl);

            return ResponseEntity.ok(fileName);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("업로드 에러");
        }
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
    public String popupDelete(@RequestParam("popupId") String popupId) {
        // 로그 추가
        System.out.println("Deleting popup with ID: " + popupId);

        int result = popupService.delete(popupId);
        if (result == 1) {
            return "redirect:" + directory + "/list";
        }
        return directory + "/popup_input";
    }
}
