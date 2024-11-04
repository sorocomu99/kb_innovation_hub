package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.MenuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuDAO {
    // 메뉴 조회
    List<MenuDTO> selectList();
    // 메뉴 상세 조회
    MenuDTO select(int menu_sn);
    // 메뉴 수정
    int modify(MenuDTO menuDTO);
}
