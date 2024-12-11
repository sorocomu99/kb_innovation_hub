/**
 * 파일명     : PromoteService.java
 * 화면명     : 국내 프로그램 - 육성 그래프 관리
 * 설명       : 국내 프로그램 - 육성 그래프 조회 및 등록, 수정, 삭제 처리
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

    // 국내 프로그램 - 육성 그래프 조회
    public PromoteDTO select() {
        return promoteDAO.select();
    }

    // 국내 프로그램 - 육성 그래프 등록
    public int insert(PromoteDTO promoteDTO, int loginId) {
        // , 제거
        String graph1_nocs = promoteDTO.getGraph1_nocs();
        String graph2_nocs = promoteDTO.getGraph2_nocs();
        String graph3_nocs = promoteDTO.getGraph3_nocs();
        String graph4_nocs = promoteDTO.getGraph4_nocs();
        String graph5_nocs = promoteDTO.getGraph5_nocs();

        String graph1 = graph1_nocs.replaceAll(",", "");
        String graph2 = graph2_nocs.replaceAll(",", "");
        String graph3 = graph3_nocs.replaceAll(",", "");
        String graph4 = graph4_nocs.replaceAll(",", "");
        String graph5 = graph5_nocs.replaceAll(",", "");

        promoteDTO.setGraph1_nocs(graph1);
        promoteDTO.setGraph2_nocs(graph2);
        promoteDTO.setGraph3_nocs(graph3);
        promoteDTO.setGraph4_nocs(graph4);
        promoteDTO.setGraph5_nocs(graph5);

        // 로그인 한 아이디 대입
        promoteDTO.setFrst_rgtr(loginId);
        promoteDTO.setLast_mdfr(loginId);

        return promoteDAO.insert(promoteDTO);
    }

    // 국내 프로그램 - 육성 그래프 수정
    public int update(PromoteDTO promoteDTO, int loginId) {
        // , 제거
        String graph1_nocs = promoteDTO.getGraph1_nocs();
        String graph2_nocs = promoteDTO.getGraph2_nocs();
        String graph3_nocs = promoteDTO.getGraph3_nocs();
        String graph4_nocs = promoteDTO.getGraph4_nocs();
        String graph5_nocs = promoteDTO.getGraph5_nocs();

        String graph1 = graph1_nocs.replaceAll(",", "");
        String graph2 = graph2_nocs.replaceAll(",", "");
        String graph3 = graph3_nocs.replaceAll(",", "");
        String graph4 = graph4_nocs.replaceAll(",", "");
        String graph5 = graph5_nocs.replaceAll(",", "");

        promoteDTO.setGraph1_nocs(graph1);
        promoteDTO.setGraph2_nocs(graph2);
        promoteDTO.setGraph3_nocs(graph3);
        promoteDTO.setGraph4_nocs(graph4);
        promoteDTO.setGraph5_nocs(graph5);

        // 로그인 한 아이디 대입
        promoteDTO.setLast_mdfr(loginId);

        return promoteDAO.update(promoteDTO);
    }
}
