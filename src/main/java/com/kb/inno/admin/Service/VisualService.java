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

import com.kb.inno.admin.DAO.VisualDAO;
import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.VisualDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisualService {

    // DAO 연결
    private final VisualDAO visualDAO;

    // 메인 비주얼 리스트 조회
    public List<VisualDTO> selectList() {
        return visualDAO.selectList();
    }

    // 파일 경로
    @Value("src/main/resources/static/")
    private String staticPath;

    // 파일 저장
    public FileDTO saveFile(VisualDTO visualDTO, int loginId) {
        // 파일 꺼내기
        MultipartFile file = visualDTO.getMain_file();

        // 오리지널 파일 이름
        String originalFileName = file.getOriginalFilename();

        // 파일 확장자 설정
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 파일 이름 설정
        String fileName = UUID.randomUUID().toString() + fileExtension;

        // 파일 경로 설정
        Path path = Paths.get(System.getProperty("user.dir"), staticPath);
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
        int result = visualDAO.saveFile(fileDTO);

        // 결과가 있으면 fileDTO return
        if(result == 1) {
            return fileDTO;
        }

        return null;
    }
    
    // 메인 비주얼 등록
    public int insert(VisualDTO visualDTO, int loginId) {
        // 로그인 한 아이디 세팅
        visualDTO.setFrst_rgtr(loginId);
        visualDTO.setLast_mdfr(loginId);

        int result = 0;

        // 파일 저장
        FileDTO fileSave = saveFile(visualDTO, loginId);

        // 게시글 저장
        if(fileSave != null) {
            // 파일 일련번호 대입
            visualDTO.setAtch_file_sn(fileSave.getFile_sn());
            // 최초 등록자, 최종 수정자 대입
            visualDTO.setFrst_rgtr(loginId);
            visualDTO.setLast_mdfr(loginId);
            result = visualDAO.insert(visualDTO);
        }

        return result;
    }
    // 메인 비주얼 상세 조회
    public VisualDTO select(int visualId) {
        return visualDAO.select(visualId);
    }

    // 메인 비주얼 삭제
    public void delete(int visualId) {
        // 메인 비주얼 상세 조회
        VisualDTO selectInfo = visualDAO.select(visualId);
        // 조회한 것에서 file_id 꺼내기
        int fileId = selectInfo.getAtch_file_sn();
        // 경로 내 파일 삭제
        Path path = Paths.get(System.getProperty("user.dir"), staticPath);
        File deleteFile = new File(path + selectInfo.getMain_path() + selectInfo.getMain_file_name());
        boolean removed = deleteFile.delete();
        // 만약 경로에 파일이 지워졌다면
        if(removed) {
            // 파일 삭제
            visualDAO.deleteFile(fileId);
            // 비주얼 삭제
            visualDAO.delete(visualId);
        }
    }

    // 메인 비주얼 수정
    public int update(VisualDTO visualDTO, int loginId) {
        // 파일을 새로 등록했는 지 확인
        int fileYn = visualDTO.getFile_yn();

        // 만약 파일을 새로 등록했다면 파일 테이블 포함 저장
        if(fileYn != 0) {
            // 0. 기존 파일 재조회
            VisualDTO basicFile = visualDAO.select(visualDTO.getMain_sn());

            // 1. 기존 경로에 있는 파일 삭제
            // 경로 설정
            Path path = Paths.get(System.getProperty("user.dir"), staticPath);
            File deleteFile = new File(path + basicFile.getMain_path() + basicFile.getMain_file_name());
            boolean removed = deleteFile.delete();

            // 2. 만약 경로에 파일이 지워졌다면
            if(removed) {
                // 테이블에 있는 파일 삭제
                visualDAO.deleteFile(basicFile.getAtch_file_sn());
            }

            // 3. 새로운 파일 경로에 저장
            FileDTO fileSave = saveFile(visualDTO, loginId);

            // 4. 새로운 파일 테이블에 저장
            if(fileSave != null) {
                // 파일 일련번호 대입
                visualDTO.setAtch_file_sn(fileSave.getFile_sn());
            }
        }

        // 최종 수정자 대입
        visualDTO.setLast_mdfr(loginId);

        return visualDAO.update(visualDTO);
    }
}
