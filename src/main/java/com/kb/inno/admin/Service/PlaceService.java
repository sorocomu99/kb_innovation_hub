/**
 * 파일명     : PlaceService.java
 * 화면명     : 육성 공간 관리
 * 설명       : 육성 공간 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.06
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.PlaceDAO;
import com.kb.inno.admin.DTO.AffiliateDTO;
import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.PlaceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaceService {

    // DAO 연결
    private final PlaceDAO placeDAO;

    // 파일 경로
    @Value("src/main/resources/static/")
    private String staticPath;

    // 육성 공간 리스트 조회
    public List<PlaceDTO> selectList() {
        return placeDAO.selectList();
    }

    // 파일 저장
    public List<FileDTO> insertFile(PlaceDTO placeDTO, int loginId) {

        // 파일을 등록했는 지 확인
        int fileYn1 = placeDTO.getFile_yn1();
        int fileYn2 = placeDTO.getFile_yn2();
        int fileYn3 = placeDTO.getFile_yn3();

        // 파일 꺼내기
        // 리스트에 담기
        MultipartFile file1, file2, file3;
        List<MultipartFile> files = new ArrayList<>();

        if(fileYn1 == 1) {
            file1 = placeDTO.getPlc_file1();
            files.add(file1);
        }

        if(fileYn2 == 1) {
            file2 = placeDTO.getPlc_file2();
            files.add(file2);
        }

        if(fileYn3 == 1) {
            file3 = placeDTO.getPlc_file3();
            files.add(file3);
        }

        // 파일 경로 설정
        Path path = Paths.get(System.getProperty("user.dir"), staticPath);
        String savePath = path + "\\upload\\";

        // 파일 디렉토리 생성(없으면)
        File directory = new File(savePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        List<FileDTO> fileList = new ArrayList<>();

        // 파일 Array 길이만큼 반복
        for(int i = 0; i < files.size(); i++) {
            // 파일이 null 이면 패스, 아니면 파일 저장 실행
            if(files.get(i).getSize() == 0) {
                continue;
            } else {
                // 오리지널 파일 이름
                String originalFilename = files.get(i).getOriginalFilename();
                // 확장자 꺼내기
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                // 파일 이름 설정
                String fileName = UUID.randomUUID().toString() + fileExtension;
                // 파일 저장
                try {
                    files.get(i).transferTo(new File(savePath, fileName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // 파일 사이즈 구하기
                int bytes = (int) files.get(i).getSize();

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
                int result = placeDAO.insertFile(fileDTO);

                // 결과가 있으면 fileDTO return
                if(result == 1) {
                    fileList.add(fileDTO);
                }
            }
        }

        return fileList;
    }

    // 육성 공간 등록
    public int insert(PlaceDTO placeDTO, int loginId) {
        // 로그인 한 사람 대입
        placeDTO.setFrst_rgtr(loginId);
        placeDTO.setLast_mdfr(loginId);

        int result = 0;

        // 1. 파일 디렉토리 및 테이블에 저장
        List<FileDTO> files = insertFile(placeDTO, loginId);

        // 2. 제휴 사례 저장
        if(!files.isEmpty()) {
            // 파일 일련 번호 대입
            for(int i = 0; i < files.size(); i++) {
                if(i == 0) {
                    placeDTO.setAtch_file_sn1(files.get(i).getFile_sn());
                }
                if(i == 1) {
                    placeDTO.setAtch_file_sn2(files.get(i).getFile_sn());
                }
                if(i == 2) {
                    placeDTO.setAtch_file_sn3(files.get(i).getFile_sn());
                }
            }

            // 로그인 한 사람 대입
            placeDTO.setFrst_rgtr(loginId);
            placeDTO.setLast_mdfr(loginId);


            result = placeDAO.insert(placeDTO);
        }

        return result;
    }

    // 육성 공간 상세 조회
    public PlaceDTO select(int plc_sn) {
        return placeDAO.select(plc_sn);
    }

    // 육성 공간 수정
    public int update(PlaceDTO placeDTO, int loginId) {
        // 파일을 새로 등록했는 지 확인
        List<Integer> fileYnList = new ArrayList<>();
        
        // 파일 등록 여부를 리스트에 담기
        fileYnList.add(placeDTO.getFile_yn1());
        fileYnList.add(placeDTO.getFile_yn2());
        fileYnList.add(placeDTO.getFile_yn3());

        // 파일 여부
        List<Integer> fileSnList = new ArrayList<>();

        // 만약 파일을 새로 등록했다면 파일 테이블 포함 저장
        for(int i = 0; i < fileYnList.size(); i++) {
            // 새로 등록한 파일이 아니면
            if(fileYnList.get(i) == 0) {
                if(i == 0) {
                    fileSnList.add(placeDTO.getAtch_file_sn1());
                }

                if(i == 1) {
                    fileSnList.add(placeDTO.getAtch_file_sn2());
                }

                if(i == 2) {
                    fileSnList.add(placeDTO.getAtch_file_sn3());
                }
            } else {
                // 0. 기존 파일 재조회
                PlaceDTO basicFile = placeDAO.select(placeDTO.getPlc_sn());

                // 1. 기존 경로에 있는 파일 삭제
                // 경로 설정
                Path path = Paths.get(System.getProperty("user.dir"), staticPath);

                int file_sn = 0;
                File deleteFile = null;
                
                // 만약 첫 번째라면
                if(i == 0) {
                    deleteFile = new File(path + basicFile.getPlc_path1() + basicFile.getPlc_file_name1());
                    file_sn = basicFile.getAtch_file_sn1();
                }

                // 만약 두 번째라면
                if(i == 1) {
                    deleteFile = new File(path + basicFile.getPlc_path2() + basicFile.getPlc_file_name2());
                    file_sn = basicFile.getAtch_file_sn2();
                }

                // 만약 세 번째라면
                if(i == 2) {
                    deleteFile = new File(path + basicFile.getPlc_path3() + basicFile.getPlc_file_name3());
                    file_sn = basicFile.getAtch_file_sn3();
                }
                
                // 파일 삭제
                boolean removed = deleteFile != null && deleteFile.delete();

                // 2. 만약 경로에 파일이 지워졌다면
                if(removed) {
                    // 테이블에 있는 파일 삭제
                    placeDAO.deleteFile(file_sn);
                }

                // 3. 새로운 파일 경로에 저장
                List<FileDTO> fileSave = insertFile(placeDTO, loginId);
                
                // 4. 저장된 파일의 PK 값을 SN LIST에 추가
                for(int j = 0; j < fileSave.size(); j++) {
                    fileSnList.add(fileSave.get(j).getFile_sn());
                }
            }
        }

        // 파일 순번 리스트에 담기
        for(int k = 0; k < fileSnList.size(); k++) {
            if(k == 0) {
                placeDTO.setAtch_file_sn1(fileSnList.get(k));
            }

            if(k == 1) {
                placeDTO.setAtch_file_sn2(fileSnList.get(k));
            }

            if(k == 2) {
                placeDTO.setAtch_file_sn3(fileSnList.get(k));
            }
        }

        // 최종 수정자 대입
        placeDTO.setLast_mdfr(loginId);

        return placeDAO.update(placeDTO);
    }

    // 육성 공간 삭제
    public void delete(int plc_sn) {
        // 0. 육성 공간 상세 조회
        PlaceDTO selectInfo = placeDAO.select(plc_sn);

        // 1. 조회한 것에서 file_id 꺼내기
        List<Integer> fileSnList = new ArrayList<>();
        fileSnList.add(selectInfo.getAtch_file_sn1());
        fileSnList.add(selectInfo.getAtch_file_sn2());
        fileSnList.add(selectInfo.getAtch_file_sn3());

        // 2. 경로 설정
        Path path = Paths.get(System.getProperty("user.dir"), staticPath);

        // 3. File_sn 만큼 반복
        for(int i = 0; i < fileSnList.size(); i++) {

            // 변수 생성
            File deleteFile = null;
            boolean removed = false;

            if(i == 0) {
                deleteFile = new File(path + selectInfo.getPlc_path1() + selectInfo.getPlc_file_name1());
            }
            
            if(i == 1) {
                deleteFile = new File(path + selectInfo.getPlc_path2() + selectInfo.getPlc_file_name2());
            }
            
            if(i == 2) {
                deleteFile = new File(path + selectInfo.getPlc_path3() + selectInfo.getPlc_file_name3());
            }
            
            // 파일 삭제
            removed = deleteFile.delete();

            // 만약 경로에 파일이 지워졌다면
            if(removed) {
                // 파일 삭제
                placeDAO.deleteFile(fileSnList.get(i));
            }
        }
        // 4. 육성 공간 삭제
        placeDAO.delete(plc_sn);
    }
}
