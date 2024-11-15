package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.NoticeDAO;
import com.kb.inno.admin.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoticeService {

    // DAO 연결
    private final NoticeDAO noticeDAO;

    // 파일 경로
    @Value("src/main/resources/static/")
    private String staticPath;

    // 공지사항 리스트 조회
    public void selectList(Model model, String type, String keyword, int page) {
        // Search DTO에 담기
        SearchDTO search = new SearchDTO();
        search.setType(type);
        search.setKeyword(keyword);

        // 페이지의 전체 글 갯수
        int allCount = noticeDAO.selectPageCount(search);

        // 한 페이지당 글 갯수
        int pageLetter = 10;

        // 전체 글 갯수 / 한 페이지에 나올 글 갯수
        int repeat = allCount / pageLetter;
        // 나머지가 0이 아니면
        if(allCount % pageLetter != 0){
            // 더하기
            repeat += 1;
        }

        // repeat이 0이면
        if(repeat == 0) {
            repeat = 1;
        }

        // 만약 가져온 페이지가 repeat 보다 크다면
        if(repeat < page) {
            page = repeat;
        }

        // 만약 가져온 페이지가 0이라면
        if(page < 1) {
            page = 1;
        }

        // 끝 페이지
        int end = page * pageLetter;
        search.setEnd(end);
        // 시작 페이지
        int start = end + 1 - pageLetter;

        search.setStart(start);

        // 리스트 조회
        List<FaqDTO> selectList = noticeDAO.selectList(search);

        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
    }

    // 파일 저장
    public FileDTO insertFile(NoticeDTO noticeDTO, int loginId) {
        // 파일 꺼내기
        MultipartFile file = noticeDTO.getNtc_file();

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
        int result = noticeDAO.insertFile(fileDTO);

        // 결과가 있으면 fileDTO return
        if(result == 1) {
            return fileDTO;
        }

        return null;
    }
    
    // 공지사항 등록
    public int insert(NoticeDTO noticeDTO, int loginId) {
        // 로그인 한 아이디 대입
        noticeDTO.setFrst_rgtr(loginId);
        noticeDTO.setLast_mdfr(loginId);

        int result = 0;

        // 파일을 등록했는 지 확인
        int fileYn = noticeDTO.getFile_yn();

        if(fileYn == 1) {
            // 파일 저장
            FileDTO fileSave = insertFile(noticeDTO, loginId);

            // 게시글 저장
            if(fileSave != null) {
                // 파일 일련번호 대입
                noticeDTO.setAtch_file_sn(fileSave.getFile_sn());
            }
        }

        // 최초 등록자, 최종 수정자 대입
        noticeDTO.setFrst_rgtr(loginId);
        noticeDTO.setLast_mdfr(loginId);
        result = noticeDAO.insert(noticeDTO);

        return result;
    }

    // 공지사항 상세 조회
    public NoticeDTO select(int ntc_sn) {
        return noticeDAO.select(ntc_sn);
    }
    
    // 공지사항 수정
    public int update(NoticeDTO noticeDTO, int loginId) {

        // 경로 설정
        Path path = Paths.get(System.getProperty("user.dir"), staticPath);

        // 만약 파일을 삭제 한다면
        if(noticeDTO.getDel_yn().equals("Y")) {
            File delete = new File(path + noticeDTO.getNtc_path() + noticeDTO.getNtc_file_name());
            int file_sn = noticeDTO.getAtch_file_sn();

            // 파일 삭제
            boolean removed = delete != null && delete.delete();

            // 2. 만약 경로에 파일이 지워졌다면
            if(removed) {
                // 테이블에 있는 파일 삭제
                noticeDAO.deleteFile(file_sn);
            }
        }

        // 파일을 새로 등록했는 지 확인
        int fileYn = noticeDTO.getFile_yn();

        // 만약 파일을 새로 등록했다면 파일 테이블 포함 저장
        if(fileYn != 0) {
            // 0. 기존 파일 재조회
            NoticeDTO basicFile = noticeDAO.select(noticeDTO.getNtc_sn());

            // 1. 기존 경로에 있는 파일 삭제
            File deleteFile = new File(path + basicFile.getNtc_path() + basicFile.getNtc_file_name());
            boolean removed = deleteFile.delete();

            // 2. 만약 경로에 파일이 지워졌다면
            if(removed) {
                // 테이블에 있는 파일 삭제
                noticeDAO.deleteFile(basicFile.getAtch_file_sn());
            }

            // 3. 새로운 파일 경로에 저장
            FileDTO fileSave = insertFile(noticeDTO, loginId);

            // 4. 새로운 파일 테이블에 저장
            if(fileSave != null) {
                // 파일 일련번호 대입
                noticeDTO.setAtch_file_sn(fileSave.getFile_sn());
            }
        }

        // 최종 수정자 대입
        noticeDTO.setLast_mdfr(loginId);

        return noticeDAO.update(noticeDTO);
    }
    
    // 공지사항 삭제
    public void delete(int ntc_sn) {
        // 메인 비주얼 상세 조회
        NoticeDTO selectInfo = noticeDAO.select(ntc_sn);
        // 조회한 것에서 file_id 꺼내기
        int file_sn = selectInfo.getAtch_file_sn();

        if(file_sn != 0){
            // 경로 내 파일 삭제
            Path path = Paths.get(System.getProperty("user.dir"), staticPath);
            File deleteFile = new File(path + selectInfo.getNtc_path() + selectInfo.getNtc_file_name());
            boolean removed = deleteFile.delete();
            // 만약 경로에 파일이 지워졌다면
            if(removed) {
                // 파일 삭제
                noticeDAO.deleteFile(file_sn);
            }
        }

        // 비주얼 삭제
        noticeDAO.delete(ntc_sn);
    }
}
