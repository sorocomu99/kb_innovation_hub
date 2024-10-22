package com.kb.inno.admin.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class PopupDTO {
    long popup_sn;           // 팝업 일련번호
    String mngr_ttl;        // 팝업 제목
    String bgng_ymd;        // 시작일자
    String bgng_hr;         // 시작 시간
    String end_ymd;         // 종료일자
    String end_hr;          // 종료시간
    long popup_left_pstn;    // 팝업 좌측 위치
    long popup_top_pstn;     // 팝업 상단 위치
    String popup_cn;        // 팝업 내용
    long frst_rgtr;          // 최초 등록자
    Date frst_reg_dt;       // 최초 등록 일시
    long last_mdfr;          // 최종 수정자
    Date last_mdfcn_dt;     // 최종 수정 일시
}
