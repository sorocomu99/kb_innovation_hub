package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.LoginDTO;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface LoginDAO {

    Optional<User> login(String loginId);
}
