package com.kb.inno.admin.DTO;

import lombok.Data;

@Data
public class SearchDTO {
    private int start;      // 시작 페이지
    private int end;        // 끝 페이지
    private String type;    // 검색 타입
    private String keyword; // 검색 조건
}
