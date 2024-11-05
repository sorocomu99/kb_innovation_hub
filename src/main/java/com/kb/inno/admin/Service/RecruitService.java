/**
 * 파일명     : RecruitService.java
 * 화면명     : 채용 지원 관리
 * 설명       : 채용 지원 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.04
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.RecruitDAO;
import com.kb.inno.admin.DTO.RecruitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruitService {

    // DAO 연결
    private final RecruitDAO recruitDAO;

    // 채용 지원 조회
    public RecruitDTO select() {
        return recruitDAO.select();
    }

    // 채용 지원 추가
    public int insert(RecruitDTO recruitDTO, int loginId) {
        recruitDTO.setFrst_rgtr(loginId);
        recruitDTO.setLast_mdfr(loginId);

        return recruitDAO.insert(recruitDTO);
    }
    
    // 채용 지원 수정
    public int modify(RecruitDTO recruitDTO, int loginId) {
        recruitDTO.setLast_mdfr(loginId);

        return recruitDAO.modify(recruitDTO);
    }
}
