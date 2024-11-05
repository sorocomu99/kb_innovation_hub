/**
 * 파일명     : AffiliateDAO.java
 * 화면명     : 제휴 사례 관리
 * 설명       : 제휴 사례 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.10.31
 * 최초개발자  : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.AffiliateDTO;
import com.kb.inno.admin.DTO.FileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AffiliateDAO {
    // 제휴 사례 조회
    List<AffiliateDTO> selectList();
    // 파일 추가
    int insertFile(FileDTO fileDTO);
    // 제휴 사례 추가
    int insert(AffiliateDTO affiliateDTO);
    // 제휴 사례 상세 조회
    AffiliateDTO select(int affiliate_sn);
    // 파일 삭제
    void deleteFile(int file_sn);
    // 제휴 사례 수정
    int update(AffiliateDTO affiliateDTO);
    // 제휴 사례 삭제
    void delete(int affiliate_sn);
}
