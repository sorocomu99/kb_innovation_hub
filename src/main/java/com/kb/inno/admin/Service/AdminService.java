package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.AdminDAO;
import com.kb.inno.admin.DTO.AdminDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminDAO adminDAO;


    public List<AdminDTO> selectList() {
        List<AdminDTO> selectList = adminDAO.selectList();
        return selectList;
    }
}
