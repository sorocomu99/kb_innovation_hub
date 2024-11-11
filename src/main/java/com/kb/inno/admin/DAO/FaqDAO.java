package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.FaqDTO;
import com.kb.inno.admin.DTO.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FaqDAO {
    // FAQ 리스트 카운트
    int selectPageCount(SearchDTO search);
    // FAQ 리스트 조회
    List<FaqDTO> selectList(SearchDTO search);
    // FAQ 등록
    int insert(FaqDTO faqDTO);
    // FAQ 상세 조회
    FaqDTO select(int faq_sn);
    // FAQ 수정
    int update(FaqDTO faqDTO);
    // FAQ 삭제
    void delete(int faq_sn);
}
