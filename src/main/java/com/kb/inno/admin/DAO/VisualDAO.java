/**
 * 파일명     : VisualDAO.java
 * 화면명     : 메인 비주얼 관리
 * 설명       : 메인 비주얼 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.24
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.VisualDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface VisualDAO {
    // 메인 비주얼 게시글 리스트 조회
    List<VisualDTO> selectList();
    // 메일 비주얼 파일 조회
    FileDTO selectFile();
    // 메인 비주얼 파일 추가
    int addFile(FileDTO fileDTO);
    // 메인 비주얼 게시글 추가
    int addVisual(VisualDTO visualDTO);
    // 메인 비주얼 상세 조회
    VisualDTO select(int visualId);
    // 메인 비주얼 삭제
    int deleteVisual(int visualId);
    // 메인 비주얼 파일 삭제
    int deleteFile(int fileId);
    // 메인 비주얼 수정
    int modifyVisual(VisualDTO visualDTO);
}
