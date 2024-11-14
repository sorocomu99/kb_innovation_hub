/**
 * 파일명     : InvestmentService.java
 * 화면명     : 협업 성과 - 투자 그래프 관리
 * 설명       : 투자 그래프 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.05
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.InvestmentDAO;
import com.kb.inno.admin.DTO.InvestmentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvestmentService {

    // DAO 연결
    private final InvestmentDAO investmentDAO;

    // 협업 성과 - 투자 그래프 관리 조회
    public InvestmentDTO select() {
        return investmentDAO.select();
    }
    
    // 협업 성과 - 투자 그래프 관리 추가
    public int insert(InvestmentDTO investmentDTO, int loginId) {
        investmentDTO.setFrst_rgtr(loginId);
        investmentDTO.setLast_mdfr(loginId);

        return investmentDAO.insert(investmentDTO);
    }

    // 협업 성과 - 투자 그래프 관리 수정
    public int update(InvestmentDTO investmentDTO, int loginId) {
        investmentDTO.setLast_mdfr(loginId);

        return investmentDAO.update(investmentDTO);
    }
}
