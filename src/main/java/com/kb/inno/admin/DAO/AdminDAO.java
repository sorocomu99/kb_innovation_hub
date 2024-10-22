package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.AdminDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminDAO {
    List<AdminDTO> selectList();
}
