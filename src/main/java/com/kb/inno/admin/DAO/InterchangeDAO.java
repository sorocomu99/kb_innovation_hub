/**
 * 파일명     : InterchangeDAO.java
 * 화면명     : 현지 교류 관리
 * 설명       : 현지 교류 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.11
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.InterchangeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InterchangeDAO {
    // 현지 교류 리스트 조회
    List<InterchangeDTO> selectList();
    // 현지 교류 파일 저장
    int insertFile(FileDTO fileDTO);
    // 현지 교류 등록
    int insert(InterchangeDTO interchangeDTO);
    // 현지 교류 상세 조회
    InterchangeDTO select(int exch_sn);
    // 현지 교류 파일 삭제
    void deleteFile(int fileSn);
    // 현지 교류 수정
    int update(InterchangeDTO interchangeDTO);
    // 현지 교류 삭제
    void delete(int exch_sn);
}