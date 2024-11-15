/**
 * 파일명     : PromoteService.java
 * 화면명     : 육성 프로그램 - 육성 그래프 관리
 * 설명       : 육성 프로그램 - 육성 그래프 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.31
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.PromoteDAO;
import com.kb.inno.admin.DTO.PromoteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromoteService {

    // DAO 연결
    private final PromoteDAO promoteDAO;

    // 육성 프로그램 - 육성 그래프 조회
    public PromoteDTO select() {
        return promoteDAO.select();
    }

    // 육성 프로그램 - 육성 그래프 등록
    public int insert(PromoteDTO promoteDTO, int loginId) {
        // 로그인 한 아이디 대입
        promoteDTO.setFrst_rgtr(loginId);
        promoteDTO.setLast_mdfr(loginId);

        return promoteDAO.insert(promoteDTO);
    }

    // 육성 프로그램 - 육성 그래프 수정
    public int update(PromoteDTO promoteDTO, int loginId) {
        // 로그인 한 아이디 대입
        promoteDTO.setLast_mdfr(loginId);

        return promoteDAO.update(promoteDTO);
    }
}
