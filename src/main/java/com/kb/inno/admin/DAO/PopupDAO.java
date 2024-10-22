package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.PopupDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PopupDAO {
    // 팝업 리스트 조회
    List<PopupDTO> selectList();
    // 팝업 추가
    int popupAdd(PopupDTO popupDTO);
    // 팝업 수정 페이지
    PopupDTO select(int id);
    // 팝업 수정
    int modify(PopupDTO popupDTO);
    // 팝업 삭제
    int delete(String popupId);
}
