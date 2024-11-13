/**
 * 파일명     : AffiliateService.java
 * 화면명     : 제휴 사례 관리
 * 설명       : 제휴 사례 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.10.31
 * 최초개발자  : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.AffiliateDAO;
import com.kb.inno.admin.DTO.AffiliateDTO;
import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.VisualDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AffiliateService {

    // DAO 연결
    private final AffiliateDAO affiliateDAO;

    // 파일 경로
    @Value("src/main/resources/static/")
    private String staticPath;

    // 제휴 사례 리스트 조회
    public List<AffiliateDTO> selectList() {
        return affiliateDAO.selectList();
    }

    // 파일 저장
    public FileDTO insertFile(AffiliateDTO affiliateDTO, int loginId) {
        // 파일 꺼내기
        MultipartFile file = affiliateDTO.getAffiliate_file();

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
        int result = affiliateDAO.insertFile(fileDTO);

        // 결과가 있으면 fileDTO return
        if(result == 1) {
            return fileDTO;
        } else {
            return null;
        }
    }

    // 제휴 사례 등록
    public int insert(AffiliateDTO affiliateDTO, int loginId) {
        // 로그인 한 사람 대입
        affiliateDTO.setFrst_rgtr(loginId);
        affiliateDTO.setLast_mdfr(loginId);

        int result = 0;

        // 파일을 등록했는 지 확인
        int fileYn = affiliateDTO.getFile_yn();

        if(fileYn == 1) {
            // 파일 저장
            FileDTO fileSave = insertFile(affiliateDTO, loginId);

            // 게시글 저장
            if(fileSave != null) {
                // 파일 일련번호 대입
                affiliateDTO.setAtch_file_sn(fileSave.getFile_sn());
            }
        }

        // 로그인 한 사람 대입
        affiliateDTO.setFrst_rgtr(loginId);
        affiliateDTO.setLast_mdfr(loginId);

        result = affiliateDAO.insert(affiliateDTO);

        return result;
    }

    // 제휴 사례 상세 조회
    public AffiliateDTO select(int affiliate_sn) {
        return affiliateDAO.select(affiliate_sn);
    }

    // 제휴 사례 수정
    public int update(AffiliateDTO affiliateDTO, int loginId) {

        // 파일을 새로 등록했는 지 확인
        int fileYn = affiliateDTO.getFile_yn();

        // 만약 파일을 새로 등록했다면 파일 테이블 포함 저장
        if(fileYn != 0) {
            // 0. 기존 파일 재조회
            AffiliateDTO basicFile = affiliateDAO.select(affiliateDTO.getAffiliate_sn());

            // 1. 기존 경로에 있는 파일 삭제
            // 경로 설정
            Path path = Paths.get(System.getProperty("user.dir"), staticPath);
            File deleteFile = new File(path + basicFile.getAffiliate_path() + basicFile.getAffiliate_file_name());
            boolean removed = deleteFile.delete();

            // 2. 만약 경로에 파일이 지워졌다면
            if(removed) {
                // 테이블에 있는 파일 삭제
                affiliateDAO.deleteFile(basicFile.getAtch_file_sn());
            }

            // 3. 새로운 파일 경로에 저장
            FileDTO fileSave = insertFile(affiliateDTO, loginId);

            // 4. 새로운 파일 테이블에 저장
            if(fileSave != null) {
                // 파일 일련번호 대입
                affiliateDTO.setAtch_file_sn(fileSave.getFile_sn());
            }
        }

        // 최종 수정자 대입
        affiliateDTO.setLast_mdfr(loginId);

        return affiliateDAO.update(affiliateDTO);
    }

    // 제휴 사례 삭제
    public void delete(int affiliate_sn) {
        // 메인 비주얼 상세 조회
        AffiliateDTO selectInfo = affiliateDAO.select(affiliate_sn);

        // 조회한 것에서 file_id 꺼내기
        int file_sn = selectInfo.getAtch_file_sn();

        if(file_sn != 0) {
            // 경로 내 파일 삭제
            Path path = Paths.get(System.getProperty("user.dir"), staticPath);
            File deleteFile = new File(path + selectInfo.getAffiliate_path() + selectInfo.getAffiliate_file_name());
            boolean removed = deleteFile.delete();
            // 만약 경로에 파일이 지워졌다면
            if(removed) {
                // 파일 삭제
                affiliateDAO.deleteFile(file_sn);
            }
        }

        // 비주얼 삭제
        affiliateDAO.delete(affiliate_sn);
    }
}
