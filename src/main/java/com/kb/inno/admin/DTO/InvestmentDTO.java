/**
 * 파일명     : InvestmentDTO.java
 * 화면명     : 협업 성과 - 투자 그래프 관리
 * 설명       : 투자 그래프 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.05
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class InvestmentDTO {
    private int graph_sn;
    private String crtr_ym;
    private String graph1_yr;
    private int graph1_nocs;
    private String graph2_yr;
    private int graph2_nocs;
    private String graph3_yr;
    private int graph3_nocs;
    private String graph4_yr;
    private int graph4_nocs;
    private String graph5_yr;
    private int graph5_nocs;
    private int frst_rgtr;
    private Date frst_reg_dt;
    private int last_mdfr;
    private Date last_mdfcn_dt;
}
