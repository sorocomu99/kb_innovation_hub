/**
 * 파일명     : InterchangeService.java
 * 화면명     : 현지 교류 관리
 * 설명       : 현지 교류 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.11
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.InterchangeDAO;
import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.InterchangeDTO;
import com.kb.inno.admin.DTO.PlaceDTO;
import com.kb.inno.common.FileUploader;
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
public class InterchangeService {
    
    // DAO 연결
    private final InterchangeDAO interchangeDAO;

    // 파일 경로
    @Value("src/main/resources/static/")
    private String staticPath;

    // 현지 교류 리스트 조회
    public List<InterchangeDTO> selectList() {
        return interchangeDAO.selectList();
    }
    
    // 현지 교류 등록
    public int insert(InterchangeDTO interchangeDTO, int loginId) {
        // 0. 파일 꺼내기
        List<MultipartFile> files = new ArrayList<>();

        MultipartFile file1 = interchangeDTO.getInter_file1();
        MultipartFile file2 = interchangeDTO.getInter_file2();
        MultipartFile file3 = interchangeDTO.getInter_file3();

        if(file1.getSize() > 0) {
            files.add(file1);
        }

        if(file2.getSize() > 0) {
            files.add(file2);
        }

        if(file3.getSize() > 0) {
            files.add(file3);
        }

        // 1. 파일 디렉토리에 저장
        FileUploader fileUploader = new FileUploader();
        List<FileDTO> fileSave = fileUploader.insertFiles(files, loginId);

        // 2. 현지 교류 저장
         if(!fileSave.isEmpty() || fileSave != null) {
            // 파일 일련 번호 대입
            for (int i = 0; i < fileSave.size(); i++) {
                if (i == 0 && fileSave.get(i) != null) {
                    // 2. 파일 테이블에 저장
                    interchangeDAO.insertFile(fileSave.get(i));
                    interchangeDTO.setAtch_file_sn1(fileSave.get(i).getFile_sn());
                }
                if (i == 1 && fileSave.get(i) != null) {
                    interchangeDAO.insertFile(fileSave.get(i));
                    interchangeDTO.setAtch_file_sn2(fileSave.get(i).getFile_sn());
                }
                if (i == 2 && fileSave.get(i) != null) {
                    interchangeDAO.insertFile(fileSave.get(i));
                    interchangeDTO.setAtch_file_sn3(fileSave.get(i).getFile_sn());
                }
            }
        }

        return interchangeDAO.insert(interchangeDTO);
    }

    // 현지 교류 상세 조회
    public InterchangeDTO select(int exch_sn) {
        return interchangeDAO.select(exch_sn);
    }

    // 현지 교류 수정
    public int update(InterchangeDTO interchangeDTO, int loginId) {
        // 파일을 새로 등록했는 지 확인
        List<Integer> fileYnList = new ArrayList<>();

        // 파일 등록 여부를 리스트에 담기
        fileYnList.add(interchangeDTO.getFile_yn1());
        fileYnList.add(interchangeDTO.getFile_yn2());
        fileYnList.add(interchangeDTO.getFile_yn3());

        // 파일 여부
        List<Integer> fileSnList = new ArrayList<>();

        // 만약 파일을 새로 등록했다면 파일 테이블 포함 저장
        for(int i = 0; i < fileYnList.size(); i++) {
            // 새로 등록한 파일이 아니면
            if(fileYnList.get(i) == 0) {
                if(i == 0) {
                    fileSnList.add(interchangeDTO.getAtch_file_sn1());
                }

                if(i == 1) {
                    fileSnList.add(interchangeDTO.getAtch_file_sn2());
                }

                if(i == 2) {
                    fileSnList.add(interchangeDTO.getAtch_file_sn3());
                }
            } else {
                // 0. 기존 파일 재조회
                InterchangeDTO basicFile = interchangeDAO.select(interchangeDTO.getExch_sn());

                // 1. 기존 경로에 있는 파일 삭제
                // 경로 설정
                Path path = Paths.get("D:\\").toAbsolutePath().normalize();

                int file_sn = 0;
                File deleteFile = null;

                // 만약 첫 번째라면
                if(i == 0) {
                    deleteFile = new File(path + basicFile.getInter_path1() + basicFile.getInter_file_name1());
                    file_sn = basicFile.getAtch_file_sn1();
                }

                // 만약 두 번째라면
                if(i == 1) {
                    deleteFile = new File(path + basicFile.getInter_path2() + basicFile.getInter_file_name2());
                    file_sn = basicFile.getAtch_file_sn2();
                }

                // 만약 세 번째라면
                if(i == 2) {
                    deleteFile = new File(path + basicFile.getInter_path3() + basicFile.getInter_file_name3());
                    file_sn = basicFile.getAtch_file_sn3();
                }

                // 파일 삭제
                boolean removed = deleteFile != null && deleteFile.delete();

                // 2. 만약 경로에 파일이 지워졌다면
                if(removed) {
                    // 테이블에 있는 파일 삭제
                    interchangeDAO.deleteFile(file_sn);
                }

                // 3. 파일 꺼내기
                List<MultipartFile> files = new ArrayList<>();

                MultipartFile file1 = interchangeDTO.getInter_file1();
                MultipartFile file2 = interchangeDTO.getInter_file2();
                MultipartFile file3 = interchangeDTO.getInter_file3();

                if(file1.getSize() > 0) {
                    files.add(file1);
                }

                if(file2.getSize() > 0) {
                    files.add(file2);
                }

                if(file3.getSize() > 0) {
                    files.add(file3);
                }

                // 4. 파일 디렉토리에 저장
                FileUploader fileUploader = new FileUploader();
                List<FileDTO> fileSave = fileUploader.insertFiles(files, loginId);

                // 5. 현지 교류 저장
                if(!fileSave.isEmpty() || fileSave != null) {
                    // 파일 일련 번호 대입
                    for (int j = 0; j < fileSave.size(); j++) {
                        if (j == 0 && fileSave.get(j) != null) {
                            // 2. 파일 테이블에 저장
                            interchangeDAO.insertFile(fileSave.get(j));
                            fileSnList.add(fileSave.get(j).getFile_sn());
                        }
                        if (j == 1 && fileSave.get(j) != null) {
                            interchangeDAO.insertFile(fileSave.get(j));
                            fileSnList.add(fileSave.get(j).getFile_sn());
                        }
                        if (j == 2 && fileSave.get(j) != null) {
                            interchangeDAO.insertFile(fileSave.get(j));
                            fileSnList.add(fileSave.get(j).getFile_sn());
                        }
                    }
                }
            }
        }

        // 파일 순번 리스트에 담기
        for(int k = 0; k < fileSnList.size(); k++) {
            if(k == 0) {
                interchangeDTO.setAtch_file_sn1(fileSnList.get(k));
            }

            if(k == 1) {
                interchangeDTO.setAtch_file_sn2(fileSnList.get(k));
            }

            if(k == 2) {
                interchangeDTO.setAtch_file_sn3(fileSnList.get(k));
            }
        }

        // 최종 수정자 대입
        interchangeDTO.setLast_mdfr(loginId);

        return interchangeDAO.update(interchangeDTO);
    }

    // 현지 교류 삭제
    public void delete(int exch_sn) {
        // 0. 현지 교류 상세 조회
        InterchangeDTO basicFile = interchangeDAO.select(exch_sn);

        // 1. 경로 설정
        Path path = Paths.get("D:\\").toAbsolutePath().normalize();

        // 2. File_sn 담기
        ArrayList<Integer> list = new ArrayList<>();

        int file_sn1 = basicFile.getAtch_file_sn1();
        int file_sn2 = basicFile.getAtch_file_sn2();
        int file_sn3 = basicFile.getAtch_file_sn3();

        list.add(file_sn1);
        list.add(file_sn2);
        list.add(file_sn3);

        // 3. File_sn 만큼 반복
        for(int i = 0; i < list.size(); i++) {

            // 1. file_sn이 있으면
            if(list.get(i) != 0) {

                // 변수 생성
                int file_sn = 0;
                File deleteFile = null;

                // 만약 첫 번째라면
                if(i == 0) {
                    deleteFile = new File(path + basicFile.getInter_path1() + basicFile.getInter_file_name1());
                    file_sn = basicFile.getAtch_file_sn1();
                }

                // 만약 두 번째라면
                if(i == 1) {
                    deleteFile = new File(path + basicFile.getInter_path2() + basicFile.getInter_file_name2());
                    file_sn = basicFile.getAtch_file_sn2();
                }

                // 만약 세 번째라면
                if(i == 2) {
                    deleteFile = new File(path + basicFile.getInter_path3() + basicFile.getInter_file_name3());
                    file_sn = basicFile.getAtch_file_sn3();
                }

                // 2. 파일 삭제
                boolean removed = deleteFile != null && deleteFile.delete();

                // 3. 만약 경로에 파일이 지워졌다면
                if(removed) {
                    // 테이블에 있는 파일 삭제
                    interchangeDAO.deleteFile(file_sn);
                }
            }
        }
        
        // 4. 현지 교류 삭제
        interchangeDAO.delete(exch_sn);
    }
}
