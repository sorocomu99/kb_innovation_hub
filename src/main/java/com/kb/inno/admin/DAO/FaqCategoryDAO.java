package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.FaqDTO;
import com.kb.inno.admin.DTO.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FaqCategoryDAO {
    // FAQ 카테고리 리스트 갯수 조회
    int selectPageCount(SearchDTO search);
    // FAQ 카테고리 리스트 조회
    List<FaqDTO> selectList(SearchDTO search);
}
