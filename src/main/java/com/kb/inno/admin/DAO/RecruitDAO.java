/**
 * 파일명     : RecruitDAO.java
 * 화면명     : 채용 지원 관리
 * 설명       : 채용 지원 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.04
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.RecruitDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RecruitDAO {
    // 채용 지원 조회
    RecruitDTO select();
    // 채용 지원 추가
    int insert(RecruitDTO recruitDTO);
    // 채용 지원 수정
    int modify(RecruitDTO recruitDTO);
}
