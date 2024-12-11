/**
 * 파일명     : ResultService.java
 * 화면명     : 육성 현황 관리
 * 설명       : 육성 현황 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.29
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.ResultDAO;
import com.kb.inno.admin.DTO.ResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResultService {

    // DAO 연결
    private final ResultDAO resultDAO;
    
    // 육성 현황 조회
    public ResultDTO select() {
        return resultDAO.select();
    }
    
    // 육성 현황 등록
    public int insert(ResultDTO resultDTO, int loginId) {
        // , 제거
        String ent_nocs = resultDTO.getEnt_nocs();
        String invest_nocs = resultDTO.getInvest_nocs();
        String affiliate_nocs = resultDTO.getAffiliate_nocs();

        String ent = ent_nocs.replaceAll(",", "");
        String invest = invest_nocs.replaceAll(",", "");
        String affiliate = affiliate_nocs.replaceAll(",", "");

        resultDTO.setEnt_nocs(ent);
        resultDTO.setInvest_nocs(invest);
        resultDTO.setAffiliate_nocs(affiliate);

        // 로그인한 사람 최초등록자, 수정자 대입
        resultDTO.setFrst_rgtr(loginId);
        resultDTO.setLast_mdfr(loginId);
        return resultDAO.insert(resultDTO);
    }
    
    // 육성 현황 수정
    public int update(ResultDTO resultDTO, int loginId) {
        // , 제거
        String ent_nocs = resultDTO.getEnt_nocs();
        String invest_nocs = resultDTO.getInvest_nocs();
        String affiliate_nocs = resultDTO.getAffiliate_nocs();

        String ent = ent_nocs.replaceAll(",", "");
        String invest = invest_nocs.replaceAll(",", "");
        String affiliate = affiliate_nocs.replaceAll(",", "");

        resultDTO.setEnt_nocs(ent);
        resultDTO.setInvest_nocs(invest);
        resultDTO.setAffiliate_nocs(affiliate);

        // 로그인한 사람 수정자 대입
        resultDTO.setLast_mdfr(loginId);
        return resultDAO.update(resultDTO);
    }
}
