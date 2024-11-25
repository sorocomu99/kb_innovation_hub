package com.kb.inno.admin.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class ResultDTO {
    private int rslt_sn;                // 성과 일련 번호
    private int ent_nocs;               // 기업 건수(육성기업)
    private String ent_crtr_ymd;        // 기준년월일(육성기업)
    private int invest_nocs;            // 투자 건수
    private String invest_crtr_ymd;     // 투자 기준년월일
    private int affiliate_nocs;         // 제휴 건수
    private String affiliate_crtr_ymd;  // 제휴 기준년월일
    private int frst_rgtr;              // 최초 등록자
    private Date frst_reg_dt;           // 최초 등록 일시
    private int last_mdfr;              // 최종 수정자
    private Date last_mdfcn_dt;         // 최종 수정 일시
    private int menu_id;                // 메뉴 ID
}
