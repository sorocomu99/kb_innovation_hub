/**
 * 파일명     : CooperationService.java
 * 화면명     : 협력 기관 관리
 * 설명       : 협력 기관 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.05
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.CooperationDAO;
import com.kb.inno.admin.DTO.CooperationDTO;
import com.kb.inno.admin.DTO.FileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CooperationService {

    // DAO 연결
    private final CooperationDAO cooperationDAO;

    // 파일 경로
    @Value("src/main/resources/static/")
    private String staticPath;

    // 협력 기관 리스트 조회
    public List<CooperationDTO> selectList() {
         return cooperationDAO.selectList();
    }

    // 파일 저장
    public FileDTO insertFile(CooperationDTO cooperationDTO, int loginId) {
        // 파일 꺼내기
        MultipartFile file = cooperationDTO.getCoope_file();

        // 오리지널 파일 이름
        String originalFilename = file.getOriginalFilename();

        // 확장자 꺼내기
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 파일 이름 설정
        String fileName = UUID.randomUUID().toString() + fileExtension;

        // 파일 경로 설정
        Path path = Paths.get(System.getProperty("user.dir"), staticPath);
        String savePath = path + "\\upload\\";

        // 파일 디렉토리 생성(없으면)
        File directory = new File(savePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            file.transferTo(new File(savePath, fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 파일 사이즈 구하기
        int bytes = (int) file.getSize();

        // 빈 DTO 생성
        FileDTO fileDTO = new FileDTO();

        // DTO에 객체 담기
        fileDTO.setFile_nm(fileName);
        fileDTO.setOrgnl_file_nm(originalFilename);
        fileDTO.setFile_extn(fileExtension);
        fileDTO.setFile_path("\\upload\\");
        fileDTO.setFile_sz(bytes);

        // 파일 DTO에 로그인 한 사람 추가
        fileDTO.setFrst_rgtr(loginId);
        fileDTO.setLast_mdfr(loginId);

        // 파일 테이블 저장
        int result = cooperationDAO.insertFile(fileDTO);

        // 결과가 있으면 fileDTO return
        if(result == 1) {
            return fileDTO;
        }

        return null;
    }

    // 협력 기관 등록
    public int insert(CooperationDTO cooperationDTO, int loginId) {
        // 로그인 한 사람 대입
        cooperationDTO.setFrst_rgtr(loginId);
        cooperationDTO.setLast_mdfr(loginId);

        int result = 0;

        // 1. 파일 디렉토리 및 테이블에 저장
        FileDTO file = insertFile(cooperationDTO, loginId);

        // 2. 제휴 사례 저장
        if(file != null) {
            // 파일 일련 번호 대입
            cooperationDTO.setAtch_file_id(file.getFile_sn());

            // 로그인 한 사람 대입
            cooperationDTO.setFrst_rgtr(loginId);
            cooperationDTO.setLast_mdfr(loginId);

            result = cooperationDAO.insert(cooperationDTO);
        }

        return result;
    }

    // 협력 기관 상세 조회
    public CooperationDTO select(int coope_sn) {
        return cooperationDAO.select(coope_sn);
    }

    // 협력 기관 수정
    public int update(CooperationDTO cooperationDTO, int loginId) {
        // 파일을 새로 등록했는 지 확인
        int fileYn = cooperationDTO.getFile_yn();

        // 만약 파일을 새로 등록했다면 파일 테이블 포함 저장
        if(fileYn != 0) {
            // 0. 기존 파일 재조회
            CooperationDTO basicFile = cooperationDAO.select(cooperationDTO.getCoope_sn());

            // 1. 기존 경로에 있는 파일 삭제
            // 경로 설정
            Path path = Paths.get(System.getProperty("user.dir"), staticPath);
            File deleteFile = new File(path + basicFile.getCoope_path() + basicFile.getCoope_file_name());
            boolean removed = deleteFile.delete();

            // 2. 만약 경로에 파일이 지워졌다면
            if(removed) {
                // 테이블에 있는 파일 삭제
                cooperationDAO.deleteFile(basicFile.getAtch_file_id());
            }

            // 3. 새로운 파일 경로에 저장
            FileDTO fileSave = insertFile(cooperationDTO, loginId);

            // 4. 새로운 파일 테이블에 저장
            if(fileSave != null) {
                // 파일 일련번호 대입
                cooperationDTO.setAtch_file_id(fileSave.getFile_sn());
            }
        }

        // 최종 수정자 대입
        cooperationDTO.setLast_mdfr(loginId);

        return cooperationDAO.update(cooperationDTO);
    }

    // 협력 기관 삭제
    public void delete(int coope_sn) {
        // 메인 비주얼 상세 조회
        CooperationDTO selectInfo = cooperationDAO.select(coope_sn);
        // 조회한 것에서 file_id 꺼내기
        int fileId = selectInfo.getAtch_file_id();
        // 경로 내 파일 삭제
        Path path = Paths.get(System.getProperty("user.dir"), staticPath);
        File deleteFile = new File(path + selectInfo.getCoope_path() + selectInfo.getCoope_file_name());
        boolean removed = deleteFile.delete();
        // 만약 경로에 파일이 지워졌다면
        if(removed) {
            // 파일 삭제
            cooperationDAO.deleteFile(fileId);
            // 비주얼 삭제
            cooperationDAO.delete(coope_sn);
        }
    }
}