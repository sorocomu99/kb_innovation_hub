package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.MenuDAO;
import com.kb.inno.admin.DTO.MenuDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    // DAO 연결
    private final MenuDAO menuDAO;

    // 메뉴 조회
    public List<MenuDTO> selectList() {
        return menuDAO.selectList();
    }

    // 메뉴 상세 조회
    public MenuDTO select(int menu_sn) {
        return menuDAO.select(menu_sn);
    }

    // 메뉴 수정
    public int modify(MenuDTO menuDTO) {
        return menuDAO.modify(menuDTO);
    }
}
