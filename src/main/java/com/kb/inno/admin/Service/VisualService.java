/**
 * 파일명     : VisualService.java
 * 화면명     : 메인 비주얼 관리
 * 설명       : 메인 비주얼 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.24
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import ch.qos.logback.core.util.FileUtil;
import com.kb.inno.admin.DAO.VisualDAO;
import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.VisualDTO;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.swing.ImageIconUIResource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VisualService {

    // DAO 연결
    private final VisualDAO visualDAO;

    // 메인 비주얼 리스트 조회
    public List<VisualDTO> selectList() {
        return visualDAO.selectList();
    }
    
    // 메인 비주얼 등록
    public int addVisual(VisualDTO visualDTO, int loginId) throws IOException {
        // 로그인 한 아이디 세팅
        visualDTO.setFrst_rgtr(loginId);
        visualDTO.setLast_mdfr(loginId);

        int result = 0;
        
        // 파일 저장
        try {
            // 파일 꺼내기
            MultipartFile file = visualDTO.getMain_file();
            
            // 오리지널 파일 이름
            String originalFileName = file.getOriginalFilename();

            // 파일 확장자 설정
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

            // 파일 이름 설정
            String fileName = UUID.randomUUID().toString() + fileExtension;

            // 파일 경로 설정
            Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/");
            String savePath = path + "\\upload\\";

            // 디렉토리 없으면 생성
            File directory = new File(savePath);
            if(!directory.exists()) {
                directory.mkdirs();
            }

            // 파일 저장
            file.transferTo(new File(savePath, fileName));

            // 파일 사이즈 구하기
            int bytes = (int) file.getSize();
                
            // 빈 DTO 생성
            FileDTO fileDTO = new FileDTO();
            
            // 파일 DTO에 값 저장
            fileDTO.setFile_nm(fileName);
            fileDTO.setOrgnl_file_nm(originalFileName);
            fileDTO.setFile_sz(bytes);
            fileDTO.setFile_extn(fileExtension);
            fileDTO.setFile_path("\\upload\\");
            
            // 파일 DTO에 로그인 한 사람 추가 (임시로 나중에 수정 요망!)
            fileDTO.setFrst_rgtr(loginId);
            fileDTO.setLast_mdfr(loginId);
            
            // 파일 테이블에 저장
            int fileSave = visualDAO.addFile(fileDTO);

            // 게시글 저장
            if(fileSave != 0) {
                // 파일 일련번호 대입
                visualDTO.setAtch_file_sn(fileDTO.getFile_sn());
                // 최초 등록자, 최종 수정자 대입
                visualDTO.setFrst_rgtr(loginId);
                visualDTO.setLast_mdfr(loginId);
                result = visualDAO.addVisual(visualDTO);
            }
            return result;

        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
    }
    // 메인 비주얼 상세 조회
    public VisualDTO select(int visualId) {
        return visualDAO.select(visualId);
    }

    // 메인 비주얼 삭제
    public int deleteVisual(int visualId) {
        // 메인 비주얼 상세 조회
        VisualDTO selectInfo = visualDAO.select(visualId);
        // 조회한 것에서 file_id 꺼내기
        int fileId = selectInfo.getAtch_file_sn();
        // 파일 삭제
        visualDAO.deleteFile(fileId);
        // 비주얼 삭제
        return visualDAO.deleteVisual(visualId);
    }
}
